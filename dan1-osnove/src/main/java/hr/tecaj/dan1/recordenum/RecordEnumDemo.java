package hr.tecaj.dan1.recordenum;

/**
 * Korištenje recorda i enuma.
 */
public class RecordEnumDemo {

    // region Jednostavni record
    record Tocka(int x, int y) { }      // cijela klasa u jednom retku!
    // endregion

    public static void main(String[] args) {
        // region Rad s recordima
        Tocka t1 = new Tocka(3, 4);
        Tocka t2 = new Tocka(3, 4);
        System.out.println(t1);               // Tocka[x=3, y=4]
        System.out.println(t1.x());           // 3 – pristupna metoda, ne getX()
        System.out.println(t1.equals(t2));    // true – uspoređuje vrijednosti

        Proizvod p = new Proizvod("P-001", " Laptop ", 899.0, Kategorija.ELEKTRONIKA);
        System.out.println(p.naziv());        // "Laptop" (kompaktni konstruktor)
        System.out.printf("S PDV-om: %.2f EUR%n", p.cijenaSPdv()); // 1123.75
        // endregion

        // region Rad s enumima
        Kategorija k = Kategorija.valueOf("KNJIGE");   // String → enum
        System.out.println(k.getOpis() + ", PDV " + k.getStopaPdv() * 100 + " %");
        System.out.println(k.ordinal() + " " + k.name()); // 1 KNJIGE

        for (Kategorija kat : Kategorija.values()) {    // sve konstante
            String grupa = switch (kat) {
                case HRANA, KNJIGE -> "snižena stopa";
                case ELEKTRONIKA, ODJECA -> "puna stopa";
            // nema default – kompajler zna sve slučajeve
            };
            System.out.println(kat + " → " + grupa);
        }
        // endregion

        try {
            new Proizvod("P-002", "Greška", -5, Kategorija.HRANA);
        } catch (IllegalArgumentException e) {
            System.out.println("Validacija: " + e.getMessage());
        }
    }
}
