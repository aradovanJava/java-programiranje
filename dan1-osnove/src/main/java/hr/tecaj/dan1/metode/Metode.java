package hr.tecaj.dan1.metode;

import java.util.Arrays;

/**
 * Metode: parametri, povratna vrijednost, preopterećenje, varargs,
 * rekurzija i prijenos argumenata po vrijednosti.
 */
public class Metode {

    public static void main(String[] args) {
        System.out.println(povrsinaKruga(2.0));
        System.out.println(zbroj(2, 3) + " " + zbroj(2.5, 3.5) + " " + zbroj(1, 2, 3));
        System.out.println(prosjek(5, 4, 3, 5));
        System.out.println(prosjek());
        System.out.println("5! = " + faktorijel(5));
        System.out.println("fib(10) = " + fibonacci(10));
        prijenosPoVrijednosti();
    }

    // region Definicija metode
    //  modifikatori  povratni tip  ime        parametri
    public static     double        povrsinaKruga(double r) {
        return Math.PI * r * r;      // return vraća vrijednost pozivatelju
    }

    static void pozdravi(String ime) { // void = ne vraća ništa
        System.out.println("Bok, " + ime);
    }
    // endregion

    // region Preopterećenje i varargs
    // Preopterećenje (overloading): isto ime, različiti parametri
    static int zbroj(int a, int b)          { return a + b; }
    static double zbroj(double a, double b) { return a + b; }
    static int zbroj(int a, int b, int c)   { return a + b + c; }

    // Varargs: proizvoljan broj argumenata (unutar metode to je niz)
    static double prosjek(int... ocjene) {
        if (ocjene.length == 0) return 0;
        int suma = 0;
        for (int o : ocjene) suma += o;
        return (double) suma / ocjene.length;
    }
    // endregion

    // region Rekurzija
    static long faktorijel(int n) {
        if (n <= 1) return 1;              // bazni slučaj – zaustavlja rekurziju
        return n * faktorijel(n - 1);      // rekurzivni poziv
    }

    static int fibonacci(int n) {
        return n < 2 ? n : fibonacci(n - 1) + fibonacci(n - 2);
    }
    // endregion

    // region Prijenos po vrijednosti
    static void prijenosPoVrijednosti() {
        int broj = 10;
        int[] niz = {1, 2, 3};
        promijeni(broj, niz);
        System.out.println(broj);                  // 10 – kopija vrijednosti
        System.out.println(Arrays.toString(niz));  // [99, 2, 3] – kopija reference!
    }

    static void promijeni(int x, int[] arr) {
        x = 500;          // mijenja samo lokalnu kopiju
        arr[0] = 99;      // mijenja objekt na koji referenca pokazuje
        arr = new int[]{7, 7, 7}; // preusmjerava samo lokalnu kopiju reference
    }
    // endregion
}
