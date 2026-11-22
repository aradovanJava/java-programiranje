package hr.tecaj.dan4.imenik.servis;

import hr.tecaj.dan4.imenik.dao.KontaktDao;
import hr.tecaj.dan4.imenik.model.Kontakt;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Servisni sloj: poslovna pravila i validacija između kontrolera i DAO-a.
 */
public class KontaktServis {

    public static class ValidacijaException extends RuntimeException {
        public ValidacijaException(String poruka) {
            super(poruka);
        }
    }

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");

    // region Servis s validacijom
    private final KontaktDao dao;

    public KontaktServis(KontaktDao dao) {
        this.dao = dao;
    }

    public Kontakt spremi(Kontakt k) {
        if (k.getIme() == null || k.getIme().isBlank()) {
            throw new ValidacijaException("Ime je obavezno.");
        }
        if (k.getPrezime() == null || k.getPrezime().isBlank()) {
            throw new ValidacijaException("Prezime je obavezno.");
        }
        if (k.getEmail() != null && !k.getEmail().isBlank()
                && !EMAIL.matcher(k.getEmail()).matches()) {
            throw new ValidacijaException("E-mail adresa nije ispravna: " + k.getEmail());
        }
        return dao.spremi(k);
    }
    // endregion

    public List<Kontakt> dohvatiSve() {
        return dao.dohvatiSve();
    }

    public boolean obrisi(Kontakt k) {
        return dao.obrisi(k.getId());
    }
}
