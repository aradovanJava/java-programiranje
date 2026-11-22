package hr.tecaj.dan2.vjezba;

import hr.tecaj.dan2.iznimke.NedovoljnoSredstavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Banka upravlja računima: otvaranje, prijenos i mjesečna obrada.
 */
public class Banka {

    private static final Logger log = LoggerFactory.getLogger(Banka.class);

    /** Unchecked iznimka – traženje nepostojećeg računa je greška pozivatelja. */
    public static class RacunNijePronadenException extends RuntimeException {
        public RacunNijePronadenException(String broj) {
            super("Račun ne postoji: " + broj);
        }
    }

    private Racun[] racuni = new Racun[2];
    private int broj;

    public void otvori(Racun racun) {
        if (broj == racuni.length) {
            racuni = Arrays.copyOf(racuni, racuni.length * 2);   // "rastući" niz
        }
        racuni[broj++] = racun;
        log.info("Otvoren račun {}", racun.getBroj());
    }

    public Racun pronadji(String brojRacuna) {
        for (int i = 0; i < broj; i++) {
            if (racuni[i].getBroj().equals(brojRacuna)) return racuni[i];
        }
        throw new RacunNijePronadenException(brojRacuna);
    }

    public void prijenos(String sa, String na, double iznos) throws NedovoljnoSredstavaException {
        Racun izvor = pronadji(sa);
        Racun cilj = pronadji(na);
        izvor.isplati(iznos);           // ako baci iznimku, uplata se ne izvodi
        cilj.uplati(iznos);
        log.info("Prijenos {} EUR: {} → {}", iznos, sa, na);
    }

    public void mjesecnaObrada() {
        for (int i = 0; i < broj; i++) {
            racuni[i].mjesecnaObrada(); // polimorfizam: svaki račun na svoj način
        }
        log.info("Mjesečna obrada završena za {} računa", broj);
    }

    public void ispis() {
        for (int i = 0; i < broj; i++) {
            System.out.println(racuni[i]);
        }
    }
}
