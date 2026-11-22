package hr.tecaj.dan2.vjezba;

import hr.tecaj.dan2.iznimke.NedovoljnoSredstavaException;
import hr.tecaj.dan2.iznimke.NeispravanIznosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rješenje vježbe Dana 2 – apstraktni bankovni račun.
 */
public abstract class Racun {

    private static final Logger log = LoggerFactory.getLogger(Racun.class);

    private final String broj;
    private final String vlasnik;
    protected double stanje;

    protected Racun(String broj, String vlasnik) {
        this.broj = broj;
        this.vlasnik = vlasnik;
    }

    /** Koliko se najviše može isplatiti (podklase definiraju pravila). */
    public abstract double raspolozivo();

    /** Što se događa na kraju mjeseca (naknade, kamate...). */
    public abstract void mjesecnaObrada();

    public void uplati(double iznos) {
        if (iznos <= 0) throw new NeispravanIznosException(iznos);
        stanje += iznos;
        log.debug("Uplata {} na {}, novo stanje {}", iznos, broj, stanje);
    }

    public void isplati(double iznos) throws NedovoljnoSredstavaException {
        if (iznos <= 0) throw new NeispravanIznosException(iznos);
        if (iznos > raspolozivo()) {
            log.warn("Odbijena isplata {} s računa {} (raspoloživo {})", iznos, broj, raspolozivo());
            throw new NedovoljnoSredstavaException(iznos, raspolozivo());
        }
        stanje -= iznos;
        log.debug("Isplata {} s {}, novo stanje {}", iznos, broj, stanje);
    }

    public String getBroj() { return broj; }
    public String getVlasnik() { return vlasnik; }
    public double getStanje() { return stanje; }

    @Override
    public String toString() {
        return "%-12s %-8s %-12s %10.2f EUR".formatted(
                getClass().getSimpleName(), broj, vlasnik, stanje);
    }
}
