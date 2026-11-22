package hr.tecaj.dan2.nasljedivanje;

/**
 * Podklasa s dodatnim stanjem i Java 25 fleksibilnim tijelom konstruktora (JEP 513):
 * validacija argumenata prije poziva super(...).
 */
public class Menadzer extends Zaposlenik {

    // region Fleksibilno tijelo konstruktora
    private final double bonus;
    private final int brojPodredenih;

    public Menadzer(String ime, double osnovnaPlaca, double bonus, int brojPodredenih) {
        if (bonus < 0) {                   // Java 25: naredbe PRIJE super(...)
            throw new IllegalArgumentException("Bonus ne smije biti negativan");
        }
        super(ime, osnovnaPlaca);
        this.bonus = bonus;
        this.brojPodredenih = brojPodredenih;
    }
    // endregion

    @Override
    public double izracunajPlacu() {
        return super.izracunajPlacu() + bonus + brojPodredenih * 50;
    }

    public int getBrojPodredenih() {
        return brojPodredenih;
    }
}
