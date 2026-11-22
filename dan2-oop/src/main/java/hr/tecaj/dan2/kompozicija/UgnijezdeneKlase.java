package hr.tecaj.dan2.kompozicija;

/**
 * Četiri vrste ugniježđenih klasa: static nested, inner, lokalna i anonimna.
 */
public class UgnijezdeneKlase {

    private final String naziv = "Vanjska";

    // region Vrste ugniježđenih klasa
    // 1) static nested – ne treba objekt vanjske klase
    static class Konfiguracija {
        int timeout = 30;
    }

    // 2) inner – vezana uz objekt vanjske klase, vidi njegova polja
    class Ispis {
        void ispisi() {
            System.out.println("Inner klasa vidi: " + naziv);
        }
    }

    void pokreni() {
        // 3) lokalna klasa – definirana unutar metode
        class Brojac {
            int n;
            void korak() { n++; }
        }
        Brojac b = new Brojac();
        b.korak();
        b.korak();
        System.out.println("Lokalna klasa: " + b.n);

        // 4) anonimna klasa – jednokratna implementacija sučelja
        Runnable zadatak = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonimna klasa, naziv=" + naziv);
            }
        };
        zadatak.run();
    }
    // endregion

    public static void main(String[] args) {
        Konfiguracija k = new Konfiguracija();             // bez vanjskog objekta
        System.out.println("Static nested: timeout=" + k.timeout);

        UgnijezdeneKlase vanjska = new UgnijezdeneKlase();
        UgnijezdeneKlase.Ispis ispis = vanjska.new Ispis(); // treba vanjski objekt
        ispis.ispisi();
        vanjska.pokreni();
    }
}
