package hr.tecaj.dan2.sucelja;

// region Funkcijsko sučelje
/**
 * Funkcijsko sučelje: točno jedna apstraktna metoda → može se implementirati lambdom.
 */
@FunctionalInterface
public interface Popust {

    double primijeni(double cijena);

    default Popust zatim(Popust sljedeci) {     // kombiniranje ponašanja
        return cijena -> sljedeci.primijeni(primijeni(cijena));
    }
}
// endregion
