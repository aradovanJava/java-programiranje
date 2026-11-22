package hr.tecaj.dan3.lambde;

import hr.tecaj.dan3.model.Zaposlenik;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream API: izvor → međuoperacije → završna operacija.
 */
public class StreamDemo {

    public static void main(String[] args) {
        List<Zaposlenik> tim = Zaposlenik.primjer();

        // region Prvi stream
        // Imena programera s plaćom > 2000, sortirano abecedno
        List<String> imena = tim.stream()                       // 1. izvor
                .filter(z -> z.odjel().equals("Razvoj"))        // 2. međuoperacije (lijene)
                .filter(z -> z.placa() > 2000)
                .map(Zaposlenik::ime)
                .sorted()
                .toList();                                      // 3. završna operacija
        System.out.println(imena);                              // [Ana, Ivo, Luka]
        // endregion

        // region Česte operacije
        long brojSeniora = tim.stream().filter(z -> z.godineIskustva() >= 5).count();
        double prosjek = tim.stream().mapToDouble(Zaposlenik::placa).average().orElse(0);
        double ukupno = tim.stream().mapToDouble(Zaposlenik::placa).sum();
        boolean imaJunior = tim.stream().anyMatch(z -> z.godineIskustva() < 2);
        boolean sviPlaceni = tim.stream().allMatch(z -> z.placa() > 1000);
        List<String> odjeli = tim.stream().map(Zaposlenik::odjel).distinct().toList();
        List<Zaposlenik> top3 = tim.stream()
                .sorted(Comparator.comparingDouble(Zaposlenik::placa).reversed())
                .limit(3)
                .toList();
        // endregion
        System.out.printf("seniora=%d, prosjek=%.2f, ukupno=%.0f, junior=%b, svi=%b%n",
                brojSeniora, prosjek, ukupno, imaJunior, sviPlaceni);
        System.out.println("Odjeli: " + odjeli);
        top3.forEach(z -> System.out.println("TOP: " + z.ime() + " " + z.placa()));

        // region Optional
        Optional<Zaposlenik> prviIzMarketinga = tim.stream()
                .filter(z -> z.odjel().equals("Marketing"))
                .findFirst();                          // možda ne postoji!
        String ime = prviIzMarketinga
                .map(Zaposlenik::ime)
                .orElse("nema nikoga");                 // zadana vrijednost
        System.out.println(ime);

        tim.stream()
           .max(Comparator.comparingInt(Zaposlenik::godineIskustva))
           .ifPresent(z -> System.out.println("Najiskusniji: " + z.ime()));
        // endregion

        // region reduce i primitivni streamovi
        int zbroj = IntStream.rangeClosed(1, 100).sum();                   // 5050
        int umnozak = IntStream.rangeClosed(1, 5).reduce(1, (a, b) -> a * b); // 120
        String inicijali = tim.stream()
                .map(z -> z.ime().substring(0, 1))
                .reduce("", String::concat);                               // AIMLPMIT
        List<Integer> kvadrati = Stream.iterate(1, n -> n + 1)
                .map(n -> n * n)
                .limit(5)
                // [1, 4, 9, 16, 25]
                .toList();
        // endregion
        System.out.println(zbroj + " " + umnozak + " " + inicijali + " " + kvadrati);
    }
}
