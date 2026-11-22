package hr.tecaj.dan2.polimorfizam;

/**
 * Dinamičko povezivanje (dynamic dispatch) te razlika overloading vs overriding.
 */
public class PolimorfizamDemo {

    public static void main(String[] args) {
        // region Polimorfizam u praksi
        Oblik[] oblici = {
                new Krug("crveni", 1.5),
                new Pravokutnik("plavi", 3, 4),
                new Trokut("žuti", 3, 4, 5)
        };
        // new Oblik("zeleni");  → ne kompajlira se: klasa je apstraktna

        double ukupno = 0;
        for (Oblik o : oblici) {
            System.out.println(o.opis());   // isti poziv, različito ponašanje
            ukupno += o.povrsina();
        }
        System.out.printf("Ukupna površina: %.2f%n", ukupno);
        // endregion

        // region Overloading vs overriding
        Oblik o = new Krug("zeleni", 1);
        ispisi(o);           // overloading se razrješava PRI KOMPAJLIRANJU (statički tip Oblik)
        ispisi((Krug) o);    // → poziva verziju za Krug
        System.out.println(o.povrsina()); // overriding se razrješava PRI IZVOĐENJU (Krug)
        // endregion
    }

    // region Preopterećene metode
    static void ispisi(Oblik o) { System.out.println("Neki oblik: " + o.getBoja()); }
    static void ispisi(Krug k)  { System.out.println("Baš krug: " + k.getBoja()); }
    // endregion
}
