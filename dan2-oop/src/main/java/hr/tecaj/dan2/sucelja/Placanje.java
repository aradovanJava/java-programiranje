package hr.tecaj.dan2.sucelja;

/**
 * Sučelje = ugovor: ŠTO klasa mora znati raditi, ne KAKO.
 */
public interface Placanje {

    // region Sučelje s default i static metodama
    double MAX_IZNOS = 10_000;              // implicitno public static final

    boolean plati(double iznos);            // implicitno public abstract
    String naziv();

    default double naknada(double iznos) {  // default: implementacija koju se može nadjačati
        return 0;
    }

    default String potvrda(double iznos) {
        return "%s: plaćeno %.2f EUR (naknada %.2f)".formatted(
                naziv(), iznos, naknada(iznos));
    }

    static boolean jeIspravanIznos(double iznos) {   // static: poziva se Placanje.jeIspravanIznos(...)
        return iznos > 0 && iznos <= MAX_IZNOS;
    }
    // endregion
}
