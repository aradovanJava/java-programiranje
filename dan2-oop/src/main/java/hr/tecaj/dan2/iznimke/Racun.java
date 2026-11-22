package hr.tecaj.dan2.iznimke;

/**
 * Klasa koja baca vlastite iznimke: throw (bacanje) i throws (deklaracija).
 */
public class Racun {

    private double stanje;

    public Racun(double pocetnoStanje) {
        this.stanje = pocetnoStanje;
    }

    // region throw i throws
    public void isplati(double iznos) throws NedovoljnoSredstavaException {
        if (iznos <= 0) {
            throw new NeispravanIznosException(iznos);              // unchecked – ne mora u throws
        }
        if (iznos > stanje) {
            throw new NedovoljnoSredstavaException(iznos, stanje);  // checked – MORA u throws
        }
        stanje -= iznos;
    }
    // endregion

    public double getStanje() {
        return stanje;
    }
}
