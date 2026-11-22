package hr.tecaj.dan3.vjezba;

import java.time.LocalDate;

/**
 * Rješenje vježbe Dana 3 – jedna stavka narudžbe iz CSV-a.
 */
public record Narudzba(int id, LocalDate datum, String kupac, String grad,
                       String artikl, String kategorija, int kolicina, double cijena) {

    public static Narudzba izCsv(String redak) {
        String[] p = redak.split(";");
        if (p.length != 8) {
            throw new IllegalArgumentException("Očekivano 8 polja, a ima " + p.length);
        }
        return new Narudzba(Integer.parseInt(p[0]), LocalDate.parse(p[1]), p[2], p[3],
                p[4], p[5], Integer.parseInt(p[6]), Double.parseDouble(p[7]));
    }

    public double iznos() {
        return kolicina * cijena;
    }
}
