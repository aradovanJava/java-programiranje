package hr.tecaj.dan5.executori;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ExecutorService: bazen (pool) niti koji izvršava zadatke – Runnable i Callable.
 */
public class ExecutorDemo {

    public static void main(String[] args) throws Exception {
        // region ExecutorService i Future
        ExecutorService pool = Executors.newFixedThreadPool(4);   // 4 niti za sve zadatke

        Callable<Integer> zbroj = () -> {                         // Callable VRAĆA rezultat
            Thread.sleep(300);
            return 1 + 2 + 3;
        };
        Future<Integer> buduci = pool.submit(zbroj);               // odmah vraća Future
        System.out.println("Zadatak predan, radimo nešto drugo...");
        Integer rezultat = buduci.get();          // blokira dok rezultat ne stigne
        System.out.println("Rezultat: " + rezultat);

        pool.shutdown();                                           // ne prima nove zadatke
        boolean gotovo = pool.awaitTermination(5, TimeUnit.SECONDS);
        // endregion
        System.out.println("Pool ugašen: " + gotovo);

        // region invokeAll i try-with-resources
        List<Callable<String>> provjere = List.of(
                () -> provjeri("baza", 400),
                () -> provjeri("disk", 150),
                () -> provjeri("mreža", 250));

        // Java 19+: ExecutorService je AutoCloseable
        try (ExecutorService ex = Executors.newFixedThreadPool(3)) {
            List<Future<String>> rezultati = ex.invokeAll(provjere); // čeka SVE zadatke
            for (Future<String> f : rezultati) {
                System.out.println(f.get());
            }
        }   // close() = shutdown + čekanje na završetak
        // endregion

        // region Vremensko ograničenje i iznimke
        try (ExecutorService ex = Executors.newSingleThreadExecutor()) {
            Future<String> spori = ex.submit(() -> provjeri("vanjski API", 2_000));
            try {
                System.out.println(spori.get(500, TimeUnit.MILLISECONDS));
            } catch (TimeoutException e) {
                System.out.println("Isteklo vrijeme – otkazujem zadatak");
                spori.cancel(true);                                   // interrupt niti
            }

            Future<Integer> neuspjeh = ex.submit(() -> 10 / 0);
            try {
                neuspjeh.get();
            } catch (ExecutionException e) {                           // iznimka iz zadatka
                System.out.println("Zadatak pao: " + e.getCause());
            }
        }
        // endregion
    }

    static String provjeri(String sustav, long ms) throws InterruptedException {
        Thread.sleep(ms);
        return "%-11s OK (%d ms, %s)".formatted(sustav, ms, Thread.currentThread().getName());
    }
}
