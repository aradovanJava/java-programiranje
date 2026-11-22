package hr.tecaj.dan5.sinkronizacija;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Potpuni zastoj (deadlock) i dva načina da ga izbjegnemo:
 * fiksni redoslijed zaključavanja i tryLock s vremenskim ograničenjem.
 */
public class DeadlockDemo {

    static class Racun {
        final int id;
        double stanje;
        final Lock brava = new ReentrantLock();

        Racun(int id, double stanje) {
            this.id = id;
            this.stanje = stanje;
        }
    }

    // region Opasno: različiti redoslijed zaključavanja
    // Nit 1: prijenos(A, B) zaključa A pa čeka B
    // Nit 2: prijenos(B, A) zaključa B pa čeka A   → obje čekaju zauvijek!
    static void prijenosOpasno(Racun sa, Racun na, double iznos) {
        synchronized (sa) {
            synchronized (na) {
                sa.stanje -= iznos;
                na.stanje += iznos;
            }
        }
    }
    // endregion

    // region Rješenje 1: uvijek isti redoslijed
    static void prijenosPoRedu(Racun sa, Racun na, double iznos) {
        Racun prvi = sa.id < na.id ? sa : na;      // npr. uvijek manji ID prvi
        Racun drugi = sa.id < na.id ? na : sa;
        synchronized (prvi) {
            synchronized (drugi) {
                sa.stanje -= iznos;
                na.stanje += iznos;
            }
        }
    }
    // endregion

    // region Rješenje 2: ReentrantLock.tryLock
    static boolean prijenosTryLock(Racun sa, Racun na, double iznos) throws InterruptedException {
        if (sa.brava.tryLock(100, TimeUnit.MILLISECONDS)) {
            try {
                if (na.brava.tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        sa.stanje -= iznos;
                        na.stanje += iznos;
                        return true;
                    } finally {
                        na.brava.unlock();          // otključavanje UVIJEK u finally
                    }
                }
            } finally {
                sa.brava.unlock();
            }
        }
        return false;                               // odustani i pokušaj kasnije
    }
    // endregion

    public static void main(String[] args) throws InterruptedException {
        Racun a = new Racun(1, 1000);
        Racun b = new Racun(2, 1000);

        Thread t1 = new Thread(() -> { for (int i = 0; i < 10_000; i++) prijenosPoRedu(a, b, 1); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 10_000; i++) prijenosPoRedu(b, a, 1); });
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println("Fiksni redoslijed: A=" + a.stanje + ", B=" + b.stanje + " (bez zastoja)");

        int[] neuspjeli = new int[1];
        Thread t3 = new Thread(() -> radiTryLock(a, b, neuspjeli));
        Thread t4 = new Thread(() -> radiTryLock(b, a, neuspjeli));
        t3.start(); t4.start(); t3.join(); t4.join();
        System.out.println("tryLock: A=" + a.stanje + ", B=" + b.stanje
                + ", odustajanja: " + neuspjeli[0]);
        System.out.println("(prijenosOpasno se namjerno ne poziva – program bi mogao zauvijek stati)");
    }

    private static void radiTryLock(Racun sa, Racun na, int[] neuspjeli) {
        for (int i = 0; i < 1_000; i++) {
            try {
                while (!prijenosTryLock(sa, na, 1)) {
                    synchronized (neuspjeli) { neuspjeli[0]++; }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
