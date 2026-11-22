package hr.tecaj.dan1.klase;

/**
 * Enkapsulacija: stanje je privatno, mijenja se isključivo kroz metode
 * koje čuvaju pravila (invarijante) – stanje nikad ne smije biti negativno.
 */
public class BankovniRacun {

    // region Enkapsulacija
    private final String iban;
    private final String vlasnik;
    private double stanje;               // nema settera – izvana se ne može "podmetnuti"

    public BankovniRacun(String iban, String vlasnik) {
        this.iban = iban;
        this.vlasnik = vlasnik;
    }

    public void uplati(double iznos) {
        if (iznos <= 0) {
            throw new IllegalArgumentException("Iznos uplate mora biti pozitivan");
        }
        stanje += iznos;
    }

    public boolean isplati(double iznos) {
        if (iznos <= 0 || iznos > stanje) {
            return false;                // pravilo: stanje ne smije biti < 0
        }
        stanje -= iznos;
        return true;
    }

    public double getStanje() { return stanje; }
    // endregion

    public String getIban() { return iban; }
    public String getVlasnik() { return vlasnik; }

    @Override
    public String toString() {
        return "%s (%s): %.2f EUR".formatted(iban, vlasnik, stanje);
    }

    public static void main(String[] args) {
        // region Korištenje računa
        BankovniRacun racun = new BankovniRacun("HR1210010051863000160", "Ana Horvat");
        racun.uplati(500);
        boolean ok = racun.isplati(120.50);
        boolean previse = racun.isplati(10_000);
        System.out.println(ok + " " + previse);   // true false
        System.out.println(racun);                // HR12... (Ana Horvat): 379.50 EUR
        // racun.stanje = 1_000_000;              → ne kompajlira se: private!
        // endregion
    }
}
