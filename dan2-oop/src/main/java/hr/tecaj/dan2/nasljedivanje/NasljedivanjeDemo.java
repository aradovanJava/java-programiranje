package hr.tecaj.dan2.nasljedivanje;

/**
 * Upcasting, polimorfni poziv, instanceof s pattern matchingom i downcasting.
 */
public class NasljedivanjeDemo {

    public static void main(String[] args) {
        // region Upcasting i polimorfizam
        Programer ana = new Programer("Ana", 2400, "Java");
        ana.dodajPrekovremene(10);

        Zaposlenik[] tim = {                      // upcasting: podklasa → nadklasa
                new Zaposlenik("Ivo", 1500),
                ana,
                new Menadzer("Maja", 3000, 800, 6)
        };

        double ukupno = 0;
        for (Zaposlenik z : tim) {
            System.out.println(z);                // poziva se prava izracunajPlacu()!
            ukupno += z.izracunajPlacu();
        }
        System.out.printf("Ukupni trošak plaća: %.2f EUR%n", ukupno);
        // endregion

        // region instanceof i pattern matching
        for (Zaposlenik z : tim) {
            if (z instanceof Programer p) {       // provjera + cast u jednom koraku
                System.out.println(p.getIme() + " programira u " + p.getJezik());
            } else if (z instanceof Menadzer m && m.getBrojPodredenih() > 5) {
                System.out.println(m.getIme() + " vodi veliki tim");
            }
        }
        // Stari način (prije Jave 16):
        Zaposlenik z = tim[1];
        if (z instanceof Programer) {
            Programer p = (Programer) z;          // eksplicitni downcast
            System.out.println("Jezik: " + p.getJezik());
        }
        // endregion

        try {
            new Menadzer("Pero", 2000, -100, 2);
        } catch (IllegalArgumentException e) {
            System.out.println("Validacija prije super(): " + e.getMessage());
        }
    }
}
