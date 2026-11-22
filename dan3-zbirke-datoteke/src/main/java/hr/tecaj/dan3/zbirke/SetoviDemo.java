package hr.tecaj.dan3.zbirke;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Set: zbirka BEZ duplikata. HashSet, LinkedHashSet i TreeSet razlikuju se u redoslijedu.
 */
public class SetoviDemo {

    public static void main(String[] args) {
        // region Tri implementacije skupa
        List<String> unosi = List.of("Zagreb", "Split", "Rijeka", "Split", "Osijek", "Zagreb");

        Set<String> hash = new HashSet<>(unosi);         // bez jamstva redoslijeda, O(1)
        Set<String> linked = new LinkedHashSet<>(unosi); // redoslijed umetanja
        Set<String> tree = new TreeSet<>(unosi);         // sortirano, O(log n)

        System.out.println(hash);    // redoslijed NIJE zajamčen
        System.out.println(linked);  // [Zagreb, Split, Rijeka, Osijek]
        System.out.println(tree);    // [Osijek, Rijeka, Split, Zagreb]

        boolean dodan = hash.add("Zagreb");      // false – već postoji
        System.out.println("Dodan duplikat? " + dodan);
        // endregion

        // region Skupovne operacije
        Set<String> javaDev = new TreeSet<>(Set.of("Ana", "Ivo", "Maja", "Luka"));
        Set<String> pythonDev = new TreeSet<>(Set.of("Maja", "Petra", "Luka"));

        Set<String> unija = new TreeSet<>(javaDev);
        unija.addAll(pythonDev);                 // ∪ [Ana, Ivo, Luka, Maja, Petra]

        Set<String> presjek = new TreeSet<>(javaDev);
        presjek.retainAll(pythonDev);            // ∩ [Luka, Maja]

        Set<String> razlika = new TreeSet<>(javaDev);
        razlika.removeAll(pythonDev);            // \ [Ana, Ivo]
        // endregion
        System.out.println("Unija: " + unija + "\nPresjek: " + presjek + "\nRazlika: " + razlika);

        // region TreeSet navigacija
        TreeSet<Integer> bodovi = new TreeSet<>(List.of(45, 78, 92, 61, 88, 53));
        System.out.println(bodovi.first() + " " + bodovi.last());   // 45 92
        System.out.println(bodovi.ceiling(80));     // 88 – najmanji >= 80
        System.out.println(bodovi.floor(60));       // 53 – najveći <= 60
        System.out.println(bodovi.headSet(60));     // [45, 53]
        System.out.println(bodovi.descendingSet()); // [92, 88, 78, 61, 53, 45]
        // endregion
    }
}
