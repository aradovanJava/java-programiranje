package hr.tecaj.dan1.klase;

/**
 * Klasa = nacrt (predložak) prema kojem stvaramo objekte.
 * Demonstrira polja, konstruktore, this, getere/setere, static i toString.
 */
public class Student {

    // region Polja i konstruktori
    // static polje – jedno za cijelu klasu, dijele ga svi objekti
    private static int brojStudenata = 0;

    // polja instance – svaki objekt ima svoje vrijednosti
    private final String jmbag;     // final: postavlja se samo jednom
    private String ime;
    private String prezime;
    private int godinaStudija;

    public Student(String jmbag, String ime, String prezime, int godinaStudija) {
        this.jmbag = jmbag;         // this razlikuje polje od parametra
        this.ime = ime;
        this.prezime = prezime;
        setGodinaStudija(godinaStudija); // validacija na jednom mjestu
        brojStudenata++;
    }

    public Student(String jmbag, String ime, String prezime) {
        this(jmbag, ime, prezime, 1);    // poziv drugog konstruktora
    }
    // endregion

    // region Geteri i seteri
    public String getJmbag() { return jmbag; }
    public String getIme() { return ime; }
    public String getPrezime() { return prezime; }
    public int getGodinaStudija() { return godinaStudija; }

    public void setGodinaStudija(int godinaStudija) {
        if (godinaStudija < 1 || godinaStudija > 5) {
            throw new IllegalArgumentException("Godina mora biti 1-5: " + godinaStudija);
        }
        this.godinaStudija = godinaStudija;
    }
    // endregion

    // region Metode instance i static
    public String punoIme() {                 // metoda instance
        return ime + " " + prezime;
    }

    public void upisiSljedecuGodinu() {
        setGodinaStudija(godinaStudija + 1);
    }

    public static int getBrojStudenata() {    // static metoda – poziva se nad klasom
        return brojStudenata;
    }

    @Override
    public String toString() {                // tekstualni prikaz objekta
        return "Student[" + jmbag + ", " + punoIme() + ", " + godinaStudija + ". god.]";
    }
    // endregion
}
