package hr.tecaj.dan2.iznimke;

/**
 * Neprovjerena (unchecked) iznimka: greška u korištenju API-ja (neispravan argument).
 */
// region Vlastita unchecked iznimka
public class NeispravanIznosException extends RuntimeException {

    public NeispravanIznosException(double iznos) {
        super("Iznos mora biti pozitivan, a zadan je: " + iznos);
    }
}
// endregion
