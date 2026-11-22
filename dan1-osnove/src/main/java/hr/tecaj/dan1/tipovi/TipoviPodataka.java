package hr.tecaj.dan1.tipovi;

/**
 * Primitivni tipovi, literali, var, konverzije i omotačke (wrapper) klase.
 */
public class TipoviPodataka {

    public static void main(String[] args) {
        primitivniTipovi();
        konverzije();
        omotackeKlase();
    }

    static void primitivniTipovi() {
        // region Primitivni tipovi
        byte b = 127;                 // 8 bita:  -128 .. 127
        short s = 32_000;             // 16 bita
        int godina = 2026;            // 32 bita – zadani cjelobrojni tip
        long stanovnici = 8_100_000_000L; // 64 bita – sufiks L
        float cijena = 19.99f;        // 32 bita – sufiks f
        double pi = 3.141592653589793; // 64 bita – zadani decimalni tip
        char slovo = 'Ž';             // 16-bitni Unicode znak
        boolean aktivan = true;       // true / false

        var poruka = "var zaključuje tip iz desne strane"; // String
        // endregion
        System.out.println("byte=" + b + ", short=" + s + ", int=" + godina + ", long=" + stanovnici);
        System.out.println("float=" + cijena + ", double=" + pi + ", char=" + slovo + ", boolean=" + aktivan);
        System.out.println(poruka);
        System.out.println("Raspon int: " + Integer.MIN_VALUE + " .. " + Integer.MAX_VALUE);
    }

    static void konverzije() {
        // region Konverzije tipova
        int cijeli = 42;
        double sirenje = cijeli;          // implicitno (widening) – sigurno
        double decimalni = 9.87;
        int suzavanje = (int) decimalni;  // eksplicitno (casting) – gubi decimale → 9

        int max = Integer.MAX_VALUE;
        int preljev = max + 1;            // overflow! → -2147483648

        System.out.println(7 / 2);        // 3   (cjelobrojno dijeljenje)
        System.out.println(7 / 2.0);      // 3.5
        System.out.println(0.1 + 0.2);    // 0.30000000000000004
        // endregion
        System.out.println("širenje=" + sirenje + ", sužavanje=" + suzavanje + ", preljev=" + preljev);
        System.out.println("Math.addExact baca iznimku kod preljeva umjesto tihe greške:");
        try {
            Math.addExact(max, 1);
        } catch (ArithmeticException e) {
            System.out.println("  ArithmeticException: " + e.getMessage());
        }
    }

    static void omotackeKlase() {
        // region Omotačke klase
        Integer a = 127;                  // autoboxing: int → Integer
        Integer b = 127;
        Integer c = 1000;
        Integer d = 1000;
        System.out.println(a == b);       // true  (cache -128..127)
        System.out.println(c == d);       // false (različiti objekti!)
        System.out.println(c.equals(d));  // true  – objekte uspoređuj s equals

        int broj = Integer.parseInt("2026");      // String → int
        double iznos = Double.parseDouble("12.5");
        String tekst = String.valueOf(broj);      // int → String
        // endregion
        System.out.println("parseInt=" + broj + ", parseDouble=" + iznos + ", valueOf=" + tekst);
    }
}
