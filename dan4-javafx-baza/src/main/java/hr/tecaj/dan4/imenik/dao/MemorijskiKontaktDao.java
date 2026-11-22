package hr.tecaj.dan4.imenik.dao;

import hr.tecaj.dan4.imenik.model.Kontakt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementacija DAO-a u memoriji – za prvu verziju aplikacije i za testiranje.
 */
public class MemorijskiKontaktDao implements KontaktDao {

    // region Implementacija u memoriji
    private final Map<Long, Kontakt> pohrana = new TreeMap<>();
    private final AtomicLong sljedeciId = new AtomicLong(1);

    @Override
    public List<Kontakt> dohvatiSve() {
        return new ArrayList<>(pohrana.values());
    }

    @Override
    public Optional<Kontakt> dohvatiPoId(long id) {
        return Optional.ofNullable(pohrana.get(id));
    }

    @Override
    public Kontakt spremi(Kontakt k) {
        if (k.getId() == 0) k.setId(sljedeciId.getAndIncrement());
        pohrana.put(k.getId(), k);
        return k;
    }

    @Override
    public boolean obrisi(long id) {
        return pohrana.remove(id) != null;
    }
    // endregion
}
