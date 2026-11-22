package hr.tecaj.dan2.sealed;

/**
 * Sealed tipovi + recordi + switch s record patternima = sigurno modeliranje domene.
 */
public class Cestarina {

    // region Switch s record patternima
    static double izracunaj(Vozilo v, int km) {
        double poKm = switch (v) {
            case Motocikl m -> 0.04;
            case Automobil a -> 0.08;
            case Kamion(var reg, var nosivost, var osovine) when osovine > 3 -> 0.30;
            case Kamion k -> 0.20;
        };  // nema default: kompajler zna da su pokriveni svi podtipovi
        return poKm * km;
    }
    // endregion

    public static void main(String[] args) {
        Vozilo[] vozila = {
                new Automobil("ZG-1234-AB", 5),
                new Kamion("ST-555-KA", 18, 4),
                new Kamion("RI-111-KK", 7.5, 2),
                new Motocikl("OS-77-M", 650)
        };
        for (Vozilo v : vozila) {
            System.out.printf("%-55s → %6.2f EUR%n", v, izracunaj(v, 280));
        }
    }
}
