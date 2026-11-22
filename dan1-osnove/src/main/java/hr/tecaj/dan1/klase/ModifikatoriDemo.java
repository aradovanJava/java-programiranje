package hr.tecaj.dan1.klase;

import hr.tecaj.dan1.klase.paket.Brojilo;   // klasa iz drugog paketa

/**
 * Što je dostupno iz drugog paketa? Odkomentiraj retke i pogledaj greške kompajlera.
 */
public class ModifikatoriDemo {

    public static void main(String[] args) {
        // region Pristup iz drugog paketa
        Brojilo b = new Brojilo();
        b.povecaj();                       // public metoda – OK
        System.out.println(b.naziv);       // public polje – OK
        // System.out.println(b.korak);    // protected – NIJE dostupno (drugi paket)
        // System.out.println(b.vrijednost); // paketni – NIJE dostupno
        // System.out.println(b.brojPoziva); // private – NIJE dostupno
        System.out.println(b.getBrojPoziva()); // kontrolirani pristup kroz metodu
        // endregion
        System.out.println("Vrijednost: " + b.getVrijednost());
    }
}
