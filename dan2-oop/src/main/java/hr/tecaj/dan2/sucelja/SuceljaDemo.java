package hr.tecaj.dan2.sucelja;

/**
 * Programiranje prema sučelju, default metode, anonimne klase i lambde.
 */
public class SuceljaDemo {

    public static void main(String[] args) {
        // region Programiranje prema sučelju
        Placanje[] nacini = {
                new KarticnoPlacanje("4111111111111111"),
                new PayPalPlacanje("ana@primjer.hr")
        };
        for (Placanje p : nacini) {               // tip varijable je sučelje
            if (p.plati(100)) {
                System.out.println(p.potvrda(100));
            }
            if (p instanceof Povratno povratno) {  // ima li i ovu sposobnost?
                povratno.vratiNovac(20);
            }
        }
        // endregion

        // region Lambda umjesto anonimne klase
        // 1) anonimna klasa
        Popust crniPetak = new Popust() {
            @Override
            public double primijeni(double cijena) {
                return cijena * 0.7;
            }
        };
        // 2) lambda – isto, ali kraće
        Popust clanski = cijena -> cijena * 0.9;
        Popust kupon = cijena -> Math.max(0, cijena - 10);

        Popust kombinirani = crniPetak.zatim(clanski).zatim(kupon);
        System.out.printf("%.2f%n", kombinirani.primijeni(200)); // 116.00
        // endregion
    }
}
