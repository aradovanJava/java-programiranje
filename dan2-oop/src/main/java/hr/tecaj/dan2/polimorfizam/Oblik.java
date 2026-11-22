package hr.tecaj.dan2.polimorfizam;

/**
 * Apstraktna klasa: ne može se instancirati, može imati i apstraktne
 * i konkretne metode te stanje (polja).
 */
public abstract class Oblik {

    // region Apstraktna klasa
    private final String boja;

    protected Oblik(String boja) {
        this.boja = boja;
    }

    // nema tijelo – podklase MORAJU implementirati
    public abstract double povrsina();
    public abstract double opseg();

    // "Template method": konkretna metoda koja koristi apstraktne korake
    public final String opis() {
        return "%s %s: P=%.2f, O=%.2f".formatted(
                boja, getClass().getSimpleName(), povrsina(), opseg());
    }
    // endregion

    public String getBoja() {
        return boja;
    }
}
