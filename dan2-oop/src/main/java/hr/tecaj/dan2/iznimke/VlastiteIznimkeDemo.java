package hr.tecaj.dan2.iznimke;

/**
 * Obrada vlastitih iznimki i ulančavanje (exception chaining).
 */
public class VlastiteIznimkeDemo {

    public static void main(String[] args) {
        // region Obrada vlastitih iznimki
        Racun racun = new Racun(100);
        double[] isplate = {30, 500, -5};
        for (double iznos : isplate) {
            try {
                racun.isplati(iznos);
                System.out.println("Isplaćeno " + iznos + ", stanje " + racun.getStanje());
            } catch (NedovoljnoSredstavaException e) {
                System.out.println("Odbijeno: " + e.getMessage()
                        + " (nedostaje " + e.getNedostaje() + ")");
            } catch (NeispravanIznosException e) {
                System.out.println("Greška u pozivu: " + e.getMessage());
            }
        }
        // endregion

        try {
            ucitajKonfiguraciju("port=abc");
        } catch (KonfiguracijaException e) {
            System.out.println(e.getMessage() + " | uzrok: " + e.getCause());
        }
    }

    // region Ulančavanje iznimki
    static class KonfiguracijaException extends RuntimeException {
        KonfiguracijaException(String poruka, Throwable uzrok) {
            super(poruka, uzrok);            // čuvamo izvornu iznimku kao uzrok
        }
    }

    static int ucitajKonfiguraciju(String redak) {
        try {
            return Integer.parseInt(redak.split("=")[1]);
        } catch (NumberFormatException e) {
            // prevodimo tehničku iznimku u iznimku našeg sloja, bez gubitka informacija
            throw new KonfiguracijaException("Neispravan port u konfiguraciji", e);
        }
    }
    // endregion
}
