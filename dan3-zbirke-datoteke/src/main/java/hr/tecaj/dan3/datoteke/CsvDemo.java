package hr.tecaj.dan3.datoteke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Čitanje CSV datoteke u listu recorda, obrada streamovima i zapis izvještaja.
 */
public class CsvDemo {

    // region Record za redak CSV-a
    public record Proizvod(String sifra, String naziv, String kategorija,
                           double cijena, int kolicina) {

        static Proizvod izCsv(String redak) {
            String[] p = redak.split(";");
            return new Proizvod(p[0], p[1], p[2],
                    Double.parseDouble(p[3]), Integer.parseInt(p[4]));
        }

        double vrijednost() {
            return cijena * kolicina;
        }
    }
    // endregion

    // region Učitavanje CSV-a
    public static List<Proizvod> ucitaj(String resurs) {
        List<Proizvod> proizvodi = new ArrayList<>();
        InputStream ulaz = CsvDemo.class.getResourceAsStream(resurs); // iz src/main/resources
        if (ulaz == null) throw new IllegalArgumentException("Nema resursa " + resurs);

        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(ulaz, StandardCharsets.UTF_8))) {
            String redak = r.readLine();                  // preskoči zaglavlje
            int broj = 1;
            while ((redak = r.readLine()) != null) {
                broj++;
                if (redak.isBlank()) continue;
                try {
                    proizvodi.add(Proizvod.izCsv(redak));
                } catch (RuntimeException e) {            // loš redak ne ruši cijeli uvoz
                    System.err.println("Preskačem redak " + broj + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return proizvodi;
    }
    // endregion

    public static void main(String[] args) throws IOException {
        List<Proizvod> proizvodi = ucitaj("/podaci/proizvodi.csv");
        System.out.println("Učitano proizvoda: " + proizvodi.size());

        // region Obrada i izvještaj
        Map<String, Double> vrijednostPoKategoriji = proizvodi.stream()
                .collect(Collectors.groupingBy(Proizvod::kategorija, TreeMap::new,
                        Collectors.summingDouble(Proizvod::vrijednost)));

        List<Proizvod> zaNarudzbu = proizvodi.stream()
                .filter(p -> p.kolicina() < 5)
                .sorted(Comparator.comparingInt(Proizvod::kolicina))
                .toList();

        StringBuilder izvjestaj = new StringBuilder("IZVJEŠTAJ O ZALIHAMA\n\n");
        vrijednostPoKategoriji.forEach((k, v) ->
                izvjestaj.append("%-12s %12.2f EUR%n".formatted(k, v)));
        izvjestaj.append("\nZa naručiti (< 5 kom):\n");
        zaNarudzbu.forEach(p ->
                izvjestaj.append("  %s %-22s %d kom%n".formatted(p.sifra(), p.naziv(), p.kolicina())));

        Path izlaz = Path.of("izlaz", "izvjestaj-zalihe.txt");
        Files.createDirectories(izlaz.getParent());
        Files.writeString(izlaz, izvjestaj);
        // endregion
        System.out.print(izvjestaj);
        System.out.println("\nZapisano u: " + izlaz.toAbsolutePath());
    }
}
