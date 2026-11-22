package hr.tecaj.dan2.objekt;

import java.util.HashSet;
import java.util.Set;

/**
 * Zašto su equals i hashCode važni – usporedba s klasom koja ih NE nadjačava.
 */
public class ObjectMetodeDemo {

    /** Klasa bez equals/hashCode – nasljeđuje Object verzije (usporedba referenci). */
    static class LosaTocka {
        final int x, y;
        LosaTocka(int x, int y) { this.x = x; this.y = y; }
    }

    public static void main(String[] args) {
        // region Posljedice equals/hashCode
        Set<LosaTocka> lose = new HashSet<>();
        lose.add(new LosaTocka(1, 2));
        lose.add(new LosaTocka(1, 2));
        System.out.println(lose.size());                         // 2 – "duplikat"!
        System.out.println(lose.contains(new LosaTocka(1, 2)));  // false

        Set<Tocka> dobre = new HashSet<>();
        dobre.add(new Tocka(1, 2));
        dobre.add(new Tocka(1, 2));
        System.out.println(dobre.size());                        // 1
        System.out.println(dobre.contains(new Tocka(1, 2)));     // true
        // endregion

        System.out.println("toString bez nadjačavanja: " + new LosaTocka(1, 2));
        System.out.println("toString s nadjačavanjem:  " + new Tocka(1, 2));
        System.out.println("getClass(): " + new Tocka(0, 0).getClass().getName());
    }
}
