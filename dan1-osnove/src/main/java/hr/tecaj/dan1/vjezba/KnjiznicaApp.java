package hr.tecaj.dan1.vjezba;

/**
 * Pokretanje rješenja vježbe "Knjižnica".
 */
public class KnjiznicaApp {

    public static void main(String[] args) {
        Knjiznica k = new Knjiznica("Gradska knjižnica", 10);
        k.dodaj(new Knjiga("978-953-1", "Zlatarovo zlato", "August Šenoa", 1871, Zanr.ROMAN));
        k.dodaj(new Knjiga("978-953-2", "Čudnovate zgode", "Ivana Brlić-Mažuranić", 1913, Zanr.DJECJA));
        k.dodaj(new Knjiga("978-953-3", "Effective Java", "Joshua Bloch", 2018, Zanr.STRUCNA));
        k.dodaj(new Knjiga("978-953-4", "Zaklada", "Isaac Asimov", 1951, Zanr.ZNANSTVENA_FANTASTIKA));
        k.dodaj(new Knjiga("978-953-5", "Ja, robot", "Isaac Asimov", 1950, Zanr.ZNANSTVENA_FANTASTIKA));
        System.out.println("Duplikat dodan? " + k.dodaj(new Knjiga("978-953-5", "Ja, robot", "Isaac Asimov", 1950, Zanr.ZNANSTVENA_FANTASTIKA)));

        k.posudi("978-953-3");
        k.posudi("978-953-4");
        System.out.println("Ponovna posudba iste knjige? " + k.posudi("978-953-4"));
        k.vrati("978-953-4");

        k.ispisiFond();

        System.out.println("\nKnjige autora 'asimov':");
        for (Knjiga knjiga : k.pronadjiPoAutoru("asimov")) {
            System.out.println("  " + knjiga.naslov() + " (" + knjiga.godinaIzdanja() + ")");
        }

        System.out.println("\nBroj knjiga po žanru:");
        for (Zanr z : Zanr.values()) {
            System.out.printf("  %-22s %d%n", z, k.brojPoZanru(z));
        }
    }
}
