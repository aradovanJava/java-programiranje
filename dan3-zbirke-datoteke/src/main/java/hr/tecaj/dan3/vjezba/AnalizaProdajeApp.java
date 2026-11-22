package hr.tecaj.dan3.vjezba;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Rješenje vježbe Dana 3: CSV → zbirke i streamovi → JSON izvještaj → ponovno čitanje.
 */
public class AnalizaProdajeApp {

    private static final Logger log = LoggerFactory.getLogger(AnalizaProdajeApp.class);

    public static void main(String[] args) throws IOException {
        List<Narudzba> narudzbe = ucitaj("/podaci/narudzbe.csv");
        log.info("Učitano {} narudžbi", narudzbe.size());

        // 1) prihod po gradu – sortirano po nazivu grada
        Map<String, Double> prihodPoGradu = narudzbe.stream()
                .collect(Collectors.groupingBy(Narudzba::grad, TreeMap::new,
                        Collectors.summingDouble(Narudzba::iznos)));

        // 2) broj prodanih komada po kategoriji
        Map<String, Long> komadaPoKategoriji = narudzbe.stream()
                .collect(Collectors.groupingBy(Narudzba::kategorija, TreeMap::new,
                        Collectors.summingLong(Narudzba::kolicina)));

        // 3) top 3 kupca po ukupnom iznosu
        List<String> top3 = narudzbe.stream()
                .collect(Collectors.groupingBy(Narudzba::kupac,
                        Collectors.summingDouble(Narudzba::iznos)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3)
                .map(e -> "%s (%.2f EUR)".formatted(e.getKey(), e.getValue()))
                .toList();

        // 4) skup različitih artikala (abecedno)
        Set<String> artikli = narudzbe.stream()
                .map(Narudzba::artikl)
                .collect(Collectors.toCollection(TreeSet::new));

        // 5) red za otpremu: najveće narudžbe prve
        PriorityQueue<Narudzba> otprema = new PriorityQueue<>(
                Comparator.comparingDouble(Narudzba::iznos).reversed());
        otprema.addAll(narudzbe);
        System.out.println("Prve tri za otpremu:");
        for (int i = 0; i < 3 && !otprema.isEmpty(); i++) {
            Narudzba n = otprema.poll();
            System.out.printf("  #%d %-22s %8.2f EUR%n", n.id(), n.artikl(), n.iznos());
        }

        LocalDate od = narudzbe.stream().map(Narudzba::datum).min(Comparator.naturalOrder()).orElseThrow();
        LocalDate doD = narudzbe.stream().map(Narudzba::datum).max(Comparator.naturalOrder()).orElseThrow();
        double ukupno = narudzbe.stream().mapToDouble(Narudzba::iznos).sum();

        IzvjestajProdaje izvjestaj = new IzvjestajProdaje(od + " – " + doD, narudzbe.size(),
                ukupno, prihodPoGradu, komadaPoKategoriji, top3, artikli);

        // 6) spremi JSON i ponovno ga pročitaj
        ObjectMapper mapper = new ObjectMapper();
        Path datoteka = Path.of("izlaz", "izvjestaj-prodaje.json");
        Files.createDirectories(datoteka.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(datoteka.toFile(), izvjestaj);
        log.info("Izvještaj zapisan u {}", datoteka.toAbsolutePath());

        IzvjestajProdaje procitan = mapper.readValue(datoteka.toFile(), IzvjestajProdaje.class);
        System.out.println(Files.readString(datoteka));
        System.out.println("Ponovno učitan, ukupni prihod: " + procitan.ukupniPrihod());
    }

    static List<Narudzba> ucitaj(String resurs) throws IOException {
        List<Narudzba> rezultat = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(AnalizaProdajeApp.class.getResourceAsStream(resurs)),
                StandardCharsets.UTF_8))) {
            r.readLine();                                   // zaglavlje
            String redak;
            while ((redak = r.readLine()) != null) {
                if (redak.isBlank()) continue;
                try {
                    rezultat.add(Narudzba.izCsv(redak));
                } catch (RuntimeException e) {
                    log.warn("Preskačem neispravan redak '{}': {}", redak, e.getMessage());
                }
            }
        }
        return rezultat;
    }
}
