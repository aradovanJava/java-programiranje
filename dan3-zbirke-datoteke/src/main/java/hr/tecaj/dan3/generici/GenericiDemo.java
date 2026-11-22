package hr.tecaj.dan3.generici;

import java.util.ArrayList;
import java.util.List;

/**
 * Generičke klase i metode, ograničeni tipovi i wildcardi (PECS).
 */
public class GenericiDemo {

    // region Generički record s dva parametra
    record Par<K, V>(K kljuc, V vrijednost) { }
    // endregion

    public static void main(String[] args) {
        // region Zašto generici
        List sirovaLista = new ArrayList();          // "raw" tip – izbjegavati!
        sirovaLista.add("tekst");
        sirovaLista.add(42);                          // kompajler ne prigovara...
        // String s = (String) sirovaLista.get(1);   // ...a ovo puca tek pri izvođenju

        List<String> imena = new ArrayList<>();       // diamond operator <>
        imena.add("Ana");
        // imena.add(42);                             // greška PRI KOMPAJLIRANJU – dobro!
        String prvo = imena.get(0);                   // nema castanja
        // endregion
        System.out.println("Raw lista: " + sirovaLista + ", prvo ime: " + prvo);

        // region Korištenje generičkih tipova
        Kutija<String> kutija = new Kutija<>("Java");
        Kutija<Integer> duljina = kutija.mapiraj(String::length);
        System.out.println(duljina.get());                    // 4

        Par<String, Double> cijena = new Par<>("Laptop", 899.99);
        System.out.println(cijena.kljuc() + " = " + cijena.vrijednost());
        // endregion

        System.out.println(najveci(List.of(3, 17, 8)));         // 17
        System.out.println(najveci(List.of("kruška", "jabuka", "šljiva")));
        System.out.println(zbroj(List.of(1, 2, 3)) + " " + zbroj(List.of(1.5, 2.5)));

        List<Number> brojevi = new ArrayList<>();
        dodajCijele(brojevi, 3);
        System.out.println(brojevi);                           // [1, 2, 3]
    }

    // region Generičke metode i ograničenja
    // <T extends Comparable<T>> – T mora biti usporediv sam sa sobom
    static <T extends Comparable<T>> T najveci(List<T> lista) {
        T max = lista.get(0);
        for (T element : lista) {
            if (element.compareTo(max) > 0) max = element;
        }
        return max;
    }
    // endregion

    // region Wildcardi – PECS
    // Producer Extends: iz liste samo ČITAMO brojeve (List<Integer>, List<Double>...)
    static double zbroj(List<? extends Number> brojevi) {
        double suma = 0;
        for (Number n : brojevi) suma += n.doubleValue();
        return suma;
    }

    // Consumer Super: u listu samo PIŠEMO Integere (List<Integer>, List<Number>, List<Object>)
    static void dodajCijele(List<? super Integer> odrediste, int n) {
        for (int i = 1; i <= n; i++) odrediste.add(i);
    }
    // endregion
}
