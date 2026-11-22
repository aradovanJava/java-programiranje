package hr.tecaj.dan3.zbirke;

import hr.tecaj.dan3.model.Zaposlenik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Prirodni poredak (Comparable) i vanjski poredak (Comparator) te klasa Collections.
 */
public class SortiranjeDemo {

    // region Comparable – prirodni poredak
    record Verzija(int major, int minor, int patch) implements Comparable<Verzija> {
        @Override
        public int compareTo(Verzija o) {
            if (major != o.major) return Integer.compare(major, o.major);
            if (minor != o.minor) return Integer.compare(minor, o.minor);
            return Integer.compare(patch, o.patch);
        }
    }
    // endregion

    public static void main(String[] args) {
        List<Verzija> verzije = new ArrayList<>(List.of(
                new Verzija(21, 0, 5), new Verzija(17, 0, 12), new Verzija(25, 0, 1), new Verzija(21, 0, 2)));
        Collections.sort(verzije);                         // koristi compareTo
        System.out.println(verzije);

        // region Comparator – vanjski poredak
        List<Zaposlenik> tim = new ArrayList<>(Zaposlenik.primjer());

        tim.sort(Comparator.comparing(Zaposlenik::ime));               // po imenu
        tim.sort(Comparator.comparingDouble(Zaposlenik::placa).reversed()); // plaća silazno

        tim.sort(Comparator.comparing(Zaposlenik::odjel)               // prvo odjel,
                .thenComparing(Zaposlenik::placa, Comparator.reverseOrder())); // pa plaća ↓
        tim.forEach(z -> System.out.println(z.odjel() + " " + z.ime() + " " + z.placa()));
        // endregion

        // region Klasa Collections
        List<Integer> brojevi = new ArrayList<>(List.of(5, 3, 9, 1, 7, 3));
        System.out.println(Collections.max(brojevi));          // 9
        System.out.println(Collections.frequency(brojevi, 3)); // 2
        Collections.reverse(brojevi);
        Collections.shuffle(brojevi);                          // nasumični poredak
        Collections.sort(brojevi, Collections.reverseOrder());  // [9, 7, 5, 3, 3, 1]
        List<Integer> samoCitanje = Collections.unmodifiableList(brojevi); // pogled bez izmjene
        // endregion
        System.out.println(brojevi + " " + samoCitanje.size());
    }
}
