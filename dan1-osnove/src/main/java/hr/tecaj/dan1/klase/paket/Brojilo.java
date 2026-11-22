package hr.tecaj.dan1.klase.paket;

/**
 * Modifikatori pristupa: public, protected, (paketni – bez modifikatora) i private.
 */
public class Brojilo {

    // region Modifikatori pristupa
    public String naziv = "Brojilo";   // vidljivo svima
    protected int korak = 1;           // paket + podklase
    int vrijednost = 0;                // paketni pristup (bez modifikatora)
    private int brojPoziva = 0;        // samo unutar ove klase

    public void povecaj() {
        vrijednost += korak;
        brojPoziva++;                  // privatno polje dostupno unutar klase
    }

    public int getBrojPoziva() {
        return brojPoziva;
    }
    // endregion

    public int getVrijednost() {
        return vrijednost;
    }
}
