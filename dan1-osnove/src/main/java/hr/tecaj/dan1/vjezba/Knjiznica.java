package hr.tecaj.dan1.vjezba;

import java.util.Arrays;

/**
 * Rješenje vježbe Dana 1: knjižnica s fondom pohranjenim u nizu
 * (zbirke poput ArrayList dolaze tek 3. dan).
 */
public class Knjiznica {

    private final String naziv;
    private final Knjiga[] fond;
    private final boolean[] posudjena;
    private int brojKnjiga;

    public Knjiznica(String naziv, int kapacitet) {
        this.naziv = naziv;
        this.fond = new Knjiga[kapacitet];
        this.posudjena = new boolean[kapacitet];
    }

    public boolean dodaj(Knjiga knjiga) {
        if (brojKnjiga == fond.length || indeksOd(knjiga.isbn()) >= 0) {
            return false;                         // puna ili duplikat
        }
        fond[brojKnjiga++] = knjiga;
        return true;
    }

    public boolean posudi(String isbn) {
        int i = indeksOd(isbn);
        if (i < 0 || posudjena[i]) return false;
        posudjena[i] = true;
        return true;
    }

    public boolean vrati(String isbn) {
        int i = indeksOd(isbn);
        if (i < 0 || !posudjena[i]) return false;
        posudjena[i] = false;
        return true;
    }

    public Knjiga[] pronadjiPoAutoru(String autor) {
        Knjiga[] rezultat = new Knjiga[brojKnjiga];
        int n = 0;
        for (int i = 0; i < brojKnjiga; i++) {
            if (fond[i].autor().toLowerCase().contains(autor.toLowerCase())) {
                rezultat[n++] = fond[i];
            }
        }
        return Arrays.copyOf(rezultat, n);        // "odreži" prazna mjesta
    }

    public int brojPoZanru(Zanr zanr) {
        int n = 0;
        for (int i = 0; i < brojKnjiga; i++) {
            if (fond[i].zanr() == zanr) n++;      // enume smijemo uspoređivati s ==
        }
        return n;
    }

    public int brojDostupnih() {
        int n = 0;
        for (int i = 0; i < brojKnjiga; i++) {
            if (!posudjena[i]) n++;
        }
        return n;
    }

    public void ispisiFond() {
        System.out.println("=== " + naziv + " (" + brojKnjiga + " knjiga, dostupno "
                + brojDostupnih() + ") ===");
        for (int i = 0; i < brojKnjiga; i++) {
            Knjiga k = fond[i];
            System.out.printf("%-22s %-20s %4d  %s%n", k.naslov(), k.autor(),
                    k.godinaIzdanja(), posudjena[i] ? "[posuđena]" : "");
        }
    }

    private int indeksOd(String isbn) {
        for (int i = 0; i < brojKnjiga; i++) {
            if (fond[i].isbn().equals(isbn)) return i;
        }
        return -1;
    }
}
