package hr.tecaj.dan1.klase;

/**
 * Pomoćna (utility) klasa: samo static članovi, konstante i privatni konstruktor.
 * Uzor iz JDK-a: java.lang.Math.
 */
public final class Geometrija {

    // region Static final i utility klasa
    public static final double ZLATNI_REZ = 1.618_033_988_75; // konstanta: VELIKA_SLOVA

    private Geometrija() {
        // privatni konstruktor – klasu nije moguće instancirati
    }

    public static double povrsinaPravokutnika(double a, double b) {
        return a * b;
    }

    public static double udaljenost(double x1, double y1, double x2, double y2) {
        return Math.hypot(x2 - x1, y2 - y1);
    }
    // endregion

    public static void main(String[] args) {
        // poziv bez stvaranja objekta: ImeKlase.metoda(...)
        System.out.println(Geometrija.povrsinaPravokutnika(3, 4));
        System.out.println(Geometrija.udaljenost(0, 0, 3, 4));
        System.out.println("Zlatni rez: " + ZLATNI_REZ);
    }
}
