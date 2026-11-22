package hr.tecaj.dan1.kontrola;

/**
 * Rješenja mini-zadataka iz bloka "Kontrola toka".
 */
public class MiniZadaci {

    public static void main(String[] args) {
        fizzBuzz(15);
        System.out.println("Zbroj znamenki 2026: " + zbrojZnamenki(2026));
        System.out.print("Prosti brojevi do 50: ");
        for (int n = 2; n <= 50; n++) {
            if (jeProst(n)) System.out.print(n + " ");
        }
        System.out.println();
    }

    // region FizzBuzz
    static void fizzBuzz(int n) {
        for (int i = 1; i <= n; i++) {
            String ispis = (i % 15 == 0) ? "FizzBuzz"
                    : (i % 3 == 0) ? "Fizz"
                    : (i % 5 == 0) ? "Buzz"
                    : String.valueOf(i);
            System.out.print(ispis + " ");
        }
        System.out.println();
    }
    // endregion

    // region Zbroj znamenki i prosti brojevi
    static int zbrojZnamenki(int broj) {
        int zbroj = 0;
        broj = Math.abs(broj);
        while (broj > 0) {
            zbroj += broj % 10;      // zadnja znamenka
            broj /= 10;              // "odreži" zadnju znamenku
        }
        return zbroj;
    }

    static boolean jeProst(int n) {
        if (n < 2) return false;
        for (int d = 2; d * d <= n; d++) {   // dovoljno do korijena iz n
            if (n % d == 0) return false;
        }
        return true;
    }
    // endregion
}
