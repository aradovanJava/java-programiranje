package hr.tecaj.dan2.vjezba;

import hr.tecaj.dan2.iznimke.NedovoljnoSredstavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Pokretanje rješenja vježbe "Banka".
 */
public class BankaApp {

    private static final Logger log = LoggerFactory.getLogger(BankaApp.class);

    public static void main(String[] args) {
        Banka banka = new Banka();
        banka.otvori(new TekuciRacun("T-001", "Ana", 500));
        banka.otvori(new StedniRacun("S-001", "Ana", 0.03));
        banka.otvori(new TekuciRacun("T-002", "Ivo", 200));

        banka.pronadji("T-001").uplati(1000);
        banka.pronadji("S-001").uplati(5000);

        izvrsiPrijenos(banka, "T-001", "T-002", 300);
        izvrsiPrijenos(banka, "T-002", "S-001", 600);   // Ivo nema dovoljno (300 + 200 minus)
        izvrsiPrijenos(banka, "T-001", "X-999", 10);    // nepostojeći račun

        banka.mjesecnaObrada();
        banka.ispis();
    }

    private static void izvrsiPrijenos(Banka banka, String sa, String na, double iznos) {
        try {
            banka.prijenos(sa, na, iznos);
        } catch (NedovoljnoSredstavaException e) {
            log.warn("Prijenos odbijen: {}", e.getMessage());
        } catch (Banka.RacunNijePronadenException e) {
            log.error("Greška: {}", e.getMessage());
        }
    }
}
