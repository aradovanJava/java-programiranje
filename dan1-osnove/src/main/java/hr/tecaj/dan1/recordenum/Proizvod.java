package hr.tecaj.dan1.recordenum;

// region Record s validacijom
/**
 * Record: nepromjenjivi nositelj podataka. Kompajler automatski generira
 * konstruktor, pristupne metode (naziv(), cijena()...), equals, hashCode i toString.
 */
public record Proizvod(String sifra, String naziv, double cijena, Kategorija kategorija) {

    // kompaktni konstruktor – validacija prije dodjele polja
    public Proizvod {
        if (cijena < 0) {
            throw new IllegalArgumentException("Cijena ne može biti negativna");
        }
        naziv = naziv.strip();
    }

    // record može imati i vlastite metode
    public double cijenaSPdv() {
        return cijena * (1 + kategorija.getStopaPdv());
    }
}
// endregion
