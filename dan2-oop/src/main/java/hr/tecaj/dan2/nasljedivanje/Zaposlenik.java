package hr.tecaj.dan2.nasljedivanje;

/**
 * Bazna (nadređena) klasa hijerarhije zaposlenika.
 */
public class Zaposlenik {

    // region Bazna klasa
    private final String ime;
    protected double osnovnaPlaca;        // protected: vidljivo podklasama

    public Zaposlenik(String ime, double osnovnaPlaca) {
        this.ime = ime;
        this.osnovnaPlaca = osnovnaPlaca;
    }

    public double izracunajPlacu() {
        return osnovnaPlaca;
    }

    public String getIme() {
        return ime;
    }

    @Override
    public String toString() {
        String vrsta = getClass().getSimpleName();      // ime stvarne klase objekta
        return vrsta + "[" + ime + ", plaća=" + izracunajPlacu() + "]";
    }
    // endregion
}
