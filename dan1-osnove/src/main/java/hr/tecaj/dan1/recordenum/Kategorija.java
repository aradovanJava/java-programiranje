package hr.tecaj.dan1.recordenum;

// region Enum s poljima
/**
 * Enum: tip s fiksnim skupom konstanti. Može imati polja, konstruktor i metode.
 */
public enum Kategorija {
    HRANA("Hrana", 0.05),
    KNJIGE("Knjige", 0.05),
    ELEKTRONIKA("Elektronika", 0.25),
    ODJECA("Odjeća", 0.25);

    private final String opis;
    private final double stopaPdv;

    Kategorija(String opis, double stopaPdv) {   // konstruktor je uvijek privatan
        this.opis = opis;
        this.stopaPdv = stopaPdv;
    }

    public String getOpis() { return opis; }
    public double getStopaPdv() { return stopaPdv; }
}
// endregion
