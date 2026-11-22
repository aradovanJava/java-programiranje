package hr.tecaj.dan4.imenik.dao;

import hr.tecaj.dan4.imenik.model.Kontakt;

import java.util.List;
import java.util.Optional;

// region DAO sučelje
/**
 * DAO (Data Access Object): sučelje koje skriva KAKO se podaci spremaju.
 * Kontroler/servis ovisi samo o ovom sučelju – implementacija se može zamijeniti.
 */
public interface KontaktDao {

    List<Kontakt> dohvatiSve();

    Optional<Kontakt> dohvatiPoId(long id);

    /** Ako kontakt nema ID (0) – INSERT, inače UPDATE. */
    Kontakt spremi(Kontakt kontakt);

    boolean obrisi(long id);
}
// endregion
