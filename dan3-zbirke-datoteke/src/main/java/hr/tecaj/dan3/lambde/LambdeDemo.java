package hr.tecaj.dan3.lambde;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Lambda izrazi, standardna funkcijska sučelja i reference na metode.
 */
public class LambdeDemo {

    public static void main(String[] args) {
        // region Sintaksa lambda izraza
        // (parametri) -> izraz          ili     (parametri) -> { naredbe; return ...; }
        Runnable pozdrav = () -> System.out.println("Bok!");
        Function<Integer, Integer> kvadrat = x -> x * x;
        BiFunction<Integer, Integer, Integer> zbroj = (a, b) -> a + b;
        Function<String, String> naslov = s -> {
            if (s.isEmpty()) return s;
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        };
        pozdrav.run();
        System.out.println(kvadrat.apply(7) + " " + zbroj.apply(2, 3) + " " + naslov.apply("java"));
        // endregion

        // region Standardna funkcijska sučelja
        Predicate<String> dugacko = s -> s.length() > 5;          // T → boolean
        Function<String, Integer> duljina = String::length;       // T → R
        Consumer<String> ispis = s -> System.out.println("> " + s); // T → void
        Supplier<Double> slucajni = Math::random;                 // () → T
        UnaryOperator<String> vikni = s -> s.toUpperCase() + "!"; // T → T

        System.out.println(dugacko.test("Programiranje"));        // true
        // true
        System.out.println(dugacko.negate().and(s -> s.startsWith("J")).test("Java"));
        System.out.println(duljina.andThen(n -> n * 2).apply("abc")); // 6
        ispis.accept(vikni.apply("lambda"));                      // > LAMBDA!
        // endregion
        System.out.println("Slučajni broj: " + slucajni.get());

        // region Reference na metode
        List<String> imena = List.of("ana", "ivo", "maja");
        imena.forEach(System.out::println);           // statička/instanca: objekt::metoda
        imena.stream().map(String::toUpperCase)       // Klasa::metodaInstance
             .forEach(System.out::println);
        Function<String, Integer> parser = Integer::parseInt;  // Klasa::statičkaMetoda
        Supplier<StringBuilder> novi = StringBuilder::new;     // Klasa::new (konstruktor)
        // endregion
        System.out.println(parser.apply("2026") + 1);
        System.out.println(novi.get().append("StringBuilder iz konstruktorske reference"));

        // region Lambda i "effectively final"
        int prag = 3;                         // ne smije se kasnije mijenjati
        Predicate<String> kratko = s -> s.length() <= prag;
        // prag = 4;                         → greška: varijabla mora biti effectively final
        System.out.println(imena.stream().filter(kratko).toList()); // [ana, ivo]
        // endregion
    }
}
