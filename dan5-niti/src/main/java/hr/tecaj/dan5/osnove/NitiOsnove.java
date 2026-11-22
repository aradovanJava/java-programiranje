package hr.tecaj.dan5.osnove;

/**
 * Stvaranje i pokretanje niti, join, sleep, interrupt i daemon niti.
 */
public class NitiOsnove {

    // region Nasljeđivanje klase Thread
    static class Preuzimanje extends Thread {
        private final String datoteka;

        Preuzimanje(String datoteka) {
            super("preuzimanje-" + datoteka);        // ime niti
            this.datoteka = datoteka;
        }

        @Override
        public void run() {                           // posao koji nit izvodi
            for (int i = 25; i <= 100; i += 25) {
                System.out.println(getName() + ": " + i + " %");
                spavaj(100);
            }
        }
    }
    // endregion

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Glavna nit: " + Thread.currentThread().getName());

        // region Načini stvaranja niti
        Thread t1 = new Preuzimanje("slika.png");           // 1) podklasa Thread

        Runnable posao = () -> System.out.println("Runnable u niti "
                + Thread.currentThread().getName());
        Thread t2 = new Thread(posao, "radnik-1");           // 2) Runnable (preporučeno)

        Thread t3 = Thread.ofPlatform()                      // 3) builder (Java 21+)
                .name("izvjestaj")
                .unstarted(() -> System.out.println("Builder nit radi"));

        t1.start();          // start() stvara NOVU nit; run() bi se izveo u trenutnoj!
        t2.start();
        t3.start();

        t1.join();           // čekaj da t1 završi
        t2.join();
        t3.join();
        System.out.println("Sve niti su završile");
        // endregion

        // region Prekidanje niti (interrupt)
        Thread brojac = new Thread(() -> {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {   // provjera zastavice
                i++;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("Prekinut nakon " + i + " koraka");
                    Thread.currentThread().interrupt();          // vrati zastavicu i izađi
                }
            }
        });
        brojac.start();
        Thread.sleep(300);
        brojac.interrupt();                // ljubazan zahtjev za zaustavljanje
        brojac.join();
        // endregion

        // region Daemon nit i stanja niti
        Thread pozadinska = new Thread(() -> {
            while (true) spavaj(1000);     // vječna petlja
        });
        pozadinska.setDaemon(true);        // JVM ne čeka daemon niti pri izlasku
        System.out.println(pozadinska.getState());   // NEW
        pozadinska.start();
        spavaj(50);
        System.out.println(pozadinska.getState());   // TIMED_WAITING (spava)
        System.out.println(t1.getState());           // TERMINATED
        // endregion
    }

    static void spavaj(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
