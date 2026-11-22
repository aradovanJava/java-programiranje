package hr.tecaj.dan3.lambde;

import hr.tecaj.dan3.model.Zaposlenik;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;

/**
 * Collectors: grupiranje, particioniranje, spajanje teksta, statistika – i Stream Gatherers.
 */
public class KolektoriDemo {

    public static void main(String[] args) {
        List<Zaposlenik> tim = Zaposlenik.primjer();

        // region groupingBy i partitioningBy
        Map<String, List<Zaposlenik>> poOdjelu = tim.stream()
                .collect(Collectors.groupingBy(Zaposlenik::odjel));

        Map<String, Long> brojPoOdjelu = tim.stream()
                .collect(Collectors.groupingBy(Zaposlenik::odjel, TreeMap::new,
                        Collectors.counting()));
        // {Financije=2, Podrška=1, Prodaja=2, Razvoj=3}

        Map<String, Double> prosjekPoOdjelu = tim.stream()
                .collect(Collectors.groupingBy(Zaposlenik::odjel,
                        Collectors.averagingDouble(Zaposlenik::placa)));

        Map<Boolean, List<String>> seniori = tim.stream()
                .collect(Collectors.partitioningBy(z -> z.godineIskustva() >= 5,
                        Collectors.mapping(Zaposlenik::ime, Collectors.toList())));
        // {false=[Ivo, Maja, Marko, Tomislav], true=[Ana, Luka, Petra, Iva]}
        // endregion
        System.out.println("Po odjelu: " + poOdjelu.keySet());
        System.out.println("Broj po odjelu: " + brojPoOdjelu);
        prosjekPoOdjelu.forEach((o, p) -> System.out.printf("  %s: %.2f%n", o, p));
        System.out.println("Seniori: " + seniori);

        // region joining, toMap i statistika
        String popis = tim.stream()
                .map(Zaposlenik::ime)
                .collect(Collectors.joining(", ", "Tim: [", "]"));

        Map<String, Double> placaPoImenu = tim.stream()
                .collect(Collectors.toMap(Zaposlenik::ime, Zaposlenik::placa));

        DoubleSummaryStatistics stat = tim.stream()
                .collect(Collectors.summarizingDouble(Zaposlenik::placa));
        System.out.printf("min=%.0f max=%.0f prosjek=%.1f n=%d%n",
                stat.getMin(), stat.getMax(), stat.getAverage(), stat.getCount());
        // endregion
        System.out.println(popis);
        System.out.println("Ana: " + placaPoImenu.get("Ana"));

        // region Stream Gatherers (Java 24+)
        // windowFixed – dijeli stream u grupe zadane veličine (npr. za "batch" obradu)
        List<List<String>> grupe = tim.stream()
                .map(Zaposlenik::ime)
                .gather(Gatherers.windowFixed(3))
                .toList();
        System.out.println(grupe); // [[Ana, Ivo, Maja], [Luka, Petra, Marko], [Iva, Tomislav]]
        // endregion
    }
}
