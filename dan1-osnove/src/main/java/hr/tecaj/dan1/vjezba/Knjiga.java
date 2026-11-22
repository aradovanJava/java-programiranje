package hr.tecaj.dan1.vjezba;

/**
 * Rješenje vježbe – nepromjenjivi podaci o knjizi.
 */
public record Knjiga(String isbn, String naslov, String autor, int godinaIzdanja, Zanr zanr) {

    public Knjiga {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN je obavezan");
        }
        if (godinaIzdanja < 1450) {
            throw new IllegalArgumentException("Neispravna godina izdanja: " + godinaIzdanja);
        }
    }
}
