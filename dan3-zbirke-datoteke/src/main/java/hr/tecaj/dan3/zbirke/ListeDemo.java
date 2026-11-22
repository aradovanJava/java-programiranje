package hr.tecaj.dan3.zbirke;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * List: uređena zbirka s indeksima, dopušta duplikate.
 */
public class ListeDemo {

    public static void main(String[] args) {
        // region Osnovne operacije s listom
        List<String> zadaci = new ArrayList<>();      // tip varijable = sučelje
        zadaci.add("Instalirati JDK");
        zadaci.add("Postaviti IntelliJ");
        zadaci.add("Napisati Hello World");
        zadaci.add(1, "Klonirati repozitorij");       // umetanje na indeks
        zadaci.add("Napisati Hello World");           // duplikati su dopušteni

        System.out.println(zadaci.size());            // 5
        System.out.println(zadaci.get(0));            // Instalirati JDK
        System.out.println(zadaci.indexOf("Postaviti IntelliJ")); // 2
        System.out.println(zadaci.contains("Commit")); // false
        zadaci.set(0, "Instalirati JDK 25");          // zamjena
        zadaci.remove("Napisati Hello World");        // uklanja PRVO pojavljivanje
        zadaci.remove(zadaci.size() - 1);             // uklanja po indeksu
        System.out.println(zadaci);
        // endregion

        // region Iteracija i sigurno uklanjanje
        List<Integer> brojevi = new ArrayList<>(List.of(4, 7, 10, 13, 16));
        for (int b : brojevi) System.out.print(b + " ");   // for-each
        System.out.println();

        // for (Integer b : brojevi) if (b % 2 == 0) brojevi.remove(b);
        //   → ConcurrentModificationException!
        Iterator<Integer> it = brojevi.iterator();
        while (it.hasNext()) {
            if (it.next() % 2 == 0) it.remove();      // ispravno: kroz iterator
        }
        brojevi.removeIf(b -> b > 10);                // još kraće: removeIf + lambda
        System.out.println(brojevi);                  // [7]
        // endregion

        // region Nepromjenjive liste i SequencedCollection
        List<String> dani = List.of("Pon", "Uto", "Sri");   // nepromjenjiva
        // dani.add("Čet");                    → UnsupportedOperationException
        List<String> kopija = new ArrayList<>(dani);          // promjenjiva kopija
        kopija.addFirst("Ned");                // Java 21: SequencedCollection
        System.out.println(kopija.getFirst() + " … " + kopija.getLast()); // Ned … Sri
        System.out.println(kopija.reversed()); // [Sri, Uto, Pon, Ned]
        // endregion

        // region LinkedList kao red i stog
        LinkedList<String> povijest = new LinkedList<>();
        povijest.addLast("stranica1");
        povijest.addLast("stranica2");
        povijest.addFirst("početna");
        // početna [stranica1, stranica2]
        System.out.println(povijest.removeFirst() + " " + povijest);
        // endregion
    }
}
