package hr.tecaj.dan2.nasljedivanje;

/**
 * Podklasa: Programer JE Zaposlenik (odnos "is-a").
 */
public class Programer extends Zaposlenik {

    // region Podklasa
    private final String jezik;
    private int prekovremeniSati;

    public Programer(String ime, double osnovnaPlaca, String jezik) {
        super(ime, osnovnaPlaca);         // poziv konstruktora nadklase
        this.jezik = jezik;
    }

    public void dodajPrekovremene(int sati) {
        prekovremeniSati += sati;
    }

    @Override                             // nadjačavanje (overriding)
    public double izracunajPlacu() {
        double poSatu = osnovnaPlaca / 160;
        return super.izracunajPlacu() + prekovremeniSati * poSatu * 1.5;
    }
    // endregion

    public String getJezik() {
        return jezik;
    }
}
