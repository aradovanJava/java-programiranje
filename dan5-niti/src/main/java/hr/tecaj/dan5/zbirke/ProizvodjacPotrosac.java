package hr.tecaj.dan5.zbirke;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Proizvođač–potrošač s BlockingQueue: put() čeka kad je red pun, take() kad je prazan.
 */
public class ProizvodjacPotrosac {

    // region Proizvođač i potrošač
    record Narudzba(int broj) { }
    static final Narudzba KRAJ = new Narudzba(-1);          // "otrovna pilula" za završetak

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Narudzba> red = new ArrayBlockingQueue<>(3);   // kapacitet 3

        Thread proizvodjac = new Thread(() -> {
            try {
                for (int i = 1; i <= 8; i++) {
                    red.put(new Narudzba(i));               // blokira ako je red pun
                    System.out.println("→ zaprimljena " + i + "  (u redu: " + red.size() + ")");
                }
                red.put(KRAJ);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread potrosac = new Thread(() -> {
            try {
                while (true) {
                    Narudzba n = red.take();                // blokira ako je red prazan
                    if (n == KRAJ) break;
                    Thread.sleep(150);                      // sporija obrada
                    System.out.println("   ✓ obrađena " + n.broj());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        proizvodjac.start();
        potrosac.start();
        proizvodjac.join();
        potrosac.join();
        System.out.println("Sve narudžbe obrađene");
    }
    // endregion
}
