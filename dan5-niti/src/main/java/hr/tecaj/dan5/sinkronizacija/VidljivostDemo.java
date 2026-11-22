package hr.tecaj.dan5.sinkronizacija;

/**
 * Vidljivost promjena između niti: volatile i synchronized blok.
 */
public class VidljivostDemo {

    // region volatile zastavica
    // Bez volatile radna nit možda NIKAD ne bi "vidjela" promjenu (JIT/cache optimizacije).
    private static volatile boolean radi = true;
    // endregion

    // region synchronized blok
    private final Object brava = new Object();       // privatni objekt za zaključavanje
    private final StringBuilder dnevnik = new StringBuilder();

    void zapisi(String poruka) {
        String obradeno = poruka.strip().toUpperCase();  // izvan kritičnog odsječka
        synchronized (brava) {                           // zaključavamo samo ono nužno
            dnevnik.append(obradeno).append('\n');
        }
    }
    // endregion

    public static void main(String[] args) throws InterruptedException {
        Thread radnik = new Thread(() -> {
            long brojac = 0;
            while (radi) {
                brojac++;
            }
            System.out.println("Radnik stao nakon " + brojac + " iteracija");
        });
        radnik.start();
        Thread.sleep(200);
        radi = false;                 // zahvaljujući volatile, radnik vidi promjenu
        radnik.join();

        VidljivostDemo d = new VidljivostDemo();
        Thread a = new Thread(() -> { for (int i = 0; i < 3; i++) d.zapisi(" nit a " + i); });
        Thread b = new Thread(() -> { for (int i = 0; i < 3; i++) d.zapisi(" nit b " + i); });
        a.start(); b.start(); a.join(); b.join();
        System.out.print(d.dnevnik);
    }
}
