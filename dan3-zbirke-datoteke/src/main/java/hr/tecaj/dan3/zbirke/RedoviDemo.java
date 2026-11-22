package hr.tecaj.dan3.zbirke;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Queue (FIFO), Deque (s oba kraja, i kao stog – LIFO) i PriorityQueue.
 */
public class RedoviDemo {

    // region Zadatak s prioritetom
    record Zadatak(String opis, int prioritet) { }
    // endregion

    public static void main(String[] args) {
        // region Queue – red čekanja (FIFO)
        Queue<String> pisac = new ArrayDeque<>();
        pisac.offer("racun.pdf");                // dodaj na kraj
        pisac.offer("ugovor.docx");
        pisac.offer("slika.png");
        System.out.println(pisac.peek());        // racun.pdf – pogledaj prvi
        while (!pisac.isEmpty()) {
            System.out.println("Ispis: " + pisac.poll()); // uzmi i ukloni prvi
        }
        System.out.println(pisac.poll());        // null – prazan red (ne baca iznimku)
        // endregion

        // region Deque kao stog (LIFO) – undo
        Deque<String> undo = new ArrayDeque<>(); // preporuka umjesto stare klase Stack
        undo.push("upiši 'Bok'");
        undo.push("podebljaj");
        undo.push("promijeni boju");
        System.out.println("Poništi: " + undo.pop()); // promijeni boju
        System.out.println("Poništi: " + undo.pop()); // podebljaj
        System.out.println("Sljedeće: " + undo.peek());
        // endregion

        // region PriorityQueue
        PriorityQueue<Zadatak> hitno = new PriorityQueue<>(
                Comparator.comparingInt(Zadatak::prioritet));   // manji broj = važnije
        hitno.add(new Zadatak("Ažurirati dokumentaciju", 3));
        hitno.add(new Zadatak("Popraviti pad produkcije", 1));
        hitno.add(new Zadatak("Code review", 2));
        while (!hitno.isEmpty()) {
            System.out.println(hitno.poll());    // izlaze po prioritetu, ne po redu dodavanja
        }
        // endregion
    }
}
