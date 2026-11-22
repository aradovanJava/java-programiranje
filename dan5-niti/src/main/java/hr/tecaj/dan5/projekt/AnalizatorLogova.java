package hr.tecaj.dan5.projekt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Završni projekt – paralelna analiza log datoteka: jedna virtualna nit po datoteci,
 * rezultati se skupljaju u konkurentne strukture.
 */
public class AnalizatorLogova {

    private static final Logger log = LoggerFactory.getLogger(AnalizatorLogova.class);

    /** Nepromjenjivi rezultat analize – spreman za JSON. */
    public record Statistika(int datoteka, long zapisa, long neispravnih,
                             Map<Integer, Long> poStatusu, Map<String, Long> top5Putanja,
                             double prosjecnoTrajanjeMs, long najsporijiMs, double postotak5xx) { }

    // region Dijeljeno stanje sigurno za niti
    private final ConcurrentHashMap<Integer, LongAdder> statusi = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LongAdder> putanje = new ConcurrentHashMap<>();
    private final LongAdder zapisa = new LongAdder();
    private final LongAdder neispravnih = new LongAdder();
    private final LongAdder greske5xx = new LongAdder();
    private final LongAdder ukupnoTrajanje = new LongAdder();
    private final AtomicLong najsporiji = new AtomicLong();
    // endregion

    // region Jedna virtualna nit po datoteci
    public Statistika analiziraj(List<Path> datoteke) throws InterruptedException {
        List<Future<Long>> rezultati = new ArrayList<>();
        ThreadFactory tvornica = Thread.ofVirtual().name("analiza-", 1).factory(); // imena u logu
        try (ExecutorService ex = Executors.newThreadPerTaskExecutor(tvornica)) {
            for (Path p : datoteke) {
                rezultati.add(ex.submit(() -> obradiDatoteku(p)));
            }
        }                                                  // čeka sve zadatke
        for (Future<Long> f : rezultati) {
            try {
                f.get();
            } catch (ExecutionException e) {
                log.error("Obrada datoteke nije uspjela", e.getCause());
            }
        }
        return sazmi(datoteke.size());
    }

    private long obradiDatoteku(Path datoteka) {
        long n = 0;
        try (Stream<String> retci = Files.lines(datoteka)) {
            for (String redak : (Iterable<String>) retci::iterator) {
                try {
                    zabiljezi(LogZapis.parsiraj(redak));
                    n++;
                } catch (RuntimeException e) {
                    neispravnih.increment();
                    log.debug("{}: preskačem redak ({})", datoteka.getFileName(), e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        log.info("{} obrađena ({} zapisa, virtualna={})", datoteka.getFileName(), n,
                Thread.currentThread().isVirtual());
        return n;
    }
    // endregion

    // region Atomarno ažuriranje statistike
    private void zabiljezi(LogZapis z) {
        zapisa.increment();
        statusi.computeIfAbsent(z.status(), k -> new LongAdder()).increment();
        putanje.computeIfAbsent(z.putanja(), k -> new LongAdder()).increment();
        ukupnoTrajanje.add(z.trajanjeMs());
        najsporiji.accumulateAndGet(z.trajanjeMs(), Math::max);
        if (z.jeGreskaPosluzitelja()) greske5xx.increment();
    }
    // endregion

    private Statistika sazmi(int brojDatoteka) {
        long n = zapisa.sum();
        Map<Integer, Long> poStatusu = new TreeMap<>();
        statusi.forEach((k, v) -> poStatusu.put(k, v.sum()));
        Map<String, Long> top5 = putanje.entrySet().stream()
                .sorted(Comparator.comparingLong((Map.Entry<String, LongAdder> e) -> e.getValue().sum()).reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().sum(),
                        (a, b) -> a, LinkedHashMap::new));
        return new Statistika(brojDatoteka, n, neispravnih.sum(), poStatusu, top5,
                n == 0 ? 0 : (double) ukupnoTrajanje.sum() / n, najsporiji.get(),
                n == 0 ? 0 : 100.0 * greske5xx.sum() / n);
    }
}
