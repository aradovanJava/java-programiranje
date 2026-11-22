package hr.tecaj.dan5.sinkronizacija;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Utrka za podacima (race condition) i tri ispravna rješenja:
 * synchronized, AtomicInteger i LongAdder.
 */
public class RaceConditionDemo {

    static final int NITI = 8;
    static final int KORAKA = 100_000;

    // region Problem: nesigurni brojač
    static class NesigurniBrojac {
        private int vrijednost = 0;

        void povecaj() {
            vrijednost++;       // NIJE atomarno: pročitaj → povećaj → zapiši
        }
    }
    // endregion

    // region Rješenje 1: synchronized
    static class SinkroniziraniBrojac {
        private int vrijednost = 0;

        synchronized void povecaj() {   // samo jedna nit istovremeno (monitor objekta)
            vrijednost++;
        }

        synchronized int get() {
            return vrijednost;
        }
    }
    // endregion

    public static void main(String[] args) throws InterruptedException {
        NesigurniBrojac nesigurni = new NesigurniBrojac();
        SinkroniziraniBrojac sinkronizirani = new SinkroniziraniBrojac();
        // region Rješenja 2 i 3: atomarne klase
        // compare-and-swap, bez zaključavanja
        AtomicInteger atomarni = new AtomicInteger();
        LongAdder zbrajalo = new LongAdder();           // najbrži kod puno niti koje pišu
        // endregion

        pokreni(nesigurni::povecaj);
        pokreni(sinkronizirani::povecaj);
        pokreni(atomarni::incrementAndGet);
        pokreni(zbrajalo::increment);

        int ocekivano = NITI * KORAKA;
        System.out.println("Očekivano:        " + ocekivano);
        System.out.println("Nesigurni:        " + nesigurni.vrijednost + "  ← izgubljena povećanja!");
        System.out.println("synchronized:     " + sinkronizirani.get());
        System.out.println("AtomicInteger:    " + atomarni.get());
        System.out.println("LongAdder:        " + zbrajalo.sum());
    }

    static void pokreni(Runnable korak) throws InterruptedException {
        List<Thread> niti = new ArrayList<>();
        for (int n = 0; n < NITI; n++) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < KORAKA; i++) korak.run();
            });
            niti.add(t);
            t.start();
        }
        for (Thread t : niti) t.join();
    }
}
