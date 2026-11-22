package hr.tecaj.dan2.iznimke;

/**
 * try / catch / finally, višestruki catch i najčešće iznimke tijekom izvođenja.
 */
public class IznimkeOsnove {

    public static void main(String[] args) {
        System.out.println(podijeli("10", "2"));
        System.out.println(podijeli("10", "0"));
        System.out.println(podijeli("deset", "2"));
        najcesceIznimke();
    }

    // region try-catch-finally
    static String podijeli(String a, String b) {
        try {
            int x = Integer.parseInt(a);          // može baciti NumberFormatException
            int y = Integer.parseInt(b);
            return "Rezultat: " + (x / y);        // može baciti ArithmeticException
        } catch (ArithmeticException e) {
            return "Dijeljenje s nulom!";
        } catch (NumberFormatException e) {
            return "Nije broj: " + e.getMessage();
        } finally {
            System.out.println("finally se izvodi UVIJEK (" + a + "/" + b + ")");
        }
    }
    // endregion

    // region Česte runtime iznimke
    static void najcesceIznimke() {
        Object[] pokusi = new Object[5];
        for (int i = 0; i < pokusi.length; i++) {
            try {
                switch (i) {
                    case 0 -> { String s = null; s.length(); }          // NullPointerException
                    case 1 -> { int[] n = new int[2]; n[5] = 1; }       // ArrayIndexOutOfBounds
                    case 2 -> { Object o = "tekst"; Integer x = (Integer) o; } // ClassCastException
                    case 3 -> Integer.parseInt("12a");                  // NumberFormatException
                    case 4 -> java.util.List.of(1, 2).add(3);           // UnsupportedOperation
                }
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) { // multi-catch
                System.out.println("Programerska greška: " + e.getClass().getSimpleName());
            } catch (RuntimeException e) {                // općenitiji tip ide NA KRAJ
                System.out.println("Runtime iznimka: " + e.getClass().getSimpleName());
            }
        }
    }
    // endregion
}
