package hr.tecaj.dan2.iznimke;

// region Vlastita unchecked iznimka
/**
 * Neprovjerena (unchecked) iznimka: greška u korištenju API-ja (neispravan argument).
 */
public class NeispravanIznosException extends RuntimeException {

    public NeispravanIznosException(double iznos) {
        super("Iznos mora biti pozitivan, a zadan je: " + iznos);
    }
}
// endregion
