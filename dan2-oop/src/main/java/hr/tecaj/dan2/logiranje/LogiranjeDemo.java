package hr.tecaj.dan2.logiranje;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Logiranje sa SLF4J + Logback. Konfiguracija: src/main/resources/logback.xml
 * Logovi se pišu u konzolu i u datoteku logs/tecaj.log.
 */
public class LogiranjeDemo {

    // region Dohvat loggera
    // jedan logger po klasi, static final, ime = puno ime klase
    private static final Logger log = LoggerFactory.getLogger(LogiranjeDemo.class);
    // endregion

    public static void main(String[] args) {
        // region Razine logiranja
        log.trace("Najdetaljnije praćenje – obično isključeno");
        log.debug("Detalji za razvoj: učitano {} postavki", 12);
        log.info("Aplikacija pokrenuta, verzija {}", "1.0.0");
        log.warn("Disk je {} % pun", 91);
        log.error("Ne mogu se spojiti na {}:{}", "db.primjer.hr", 5432);
        // endregion

        // region Parametrizirane poruke
        String korisnik = "ana";
        int stavki = 3;
        // LOŠE: spajanje stringova se izvodi čak i kad je razina isključena
        log.debug("Korisnik " + korisnik + " ima " + stavki + " stavki");
        // DOBRO: {} rezervirana mjesta – poruka se gradi samo ako je potrebna
        log.debug("Korisnik {} ima {} stavki", korisnik, stavki);
        // endregion

        // region Logiranje iznimki
        try {
            Integer.parseInt("12x");
        } catch (NumberFormatException e) {
            // iznimka kao ZADNJI argument → ispisuje se cijeli stack trace
            log.error("Neispravan unos za korisnika {}", korisnik, e);
        }
        // endregion

        // region MDC kontekst
        // kontekst se dodaje svakoj poruci (%X{zahtjev})
        MDC.put("zahtjev", "REQ-7781");
        try {
            log.info("Obrada narudžbe započeta");
            log.info("Obrada narudžbe završena");
        } finally {
            MDC.remove("zahtjev");
        }
        // endregion
    }
}
