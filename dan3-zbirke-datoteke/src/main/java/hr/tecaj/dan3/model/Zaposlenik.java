package hr.tecaj.dan3.model;

import java.util.List;

/**
 * Model za primjere zbirki i streamova.
 */
public record Zaposlenik(String ime, String odjel, double placa, int godineIskustva) {

    /** Primjer podataka koji se koristi u više demo klasa. */
    public static List<Zaposlenik> primjer() {
        return List.of(
                new Zaposlenik("Ana", "Razvoj", 2800, 6),
                new Zaposlenik("Ivo", "Razvoj", 2100, 2),
                new Zaposlenik("Maja", "Prodaja", 1900, 4),
                new Zaposlenik("Luka", "Razvoj", 3500, 11),
                new Zaposlenik("Petra", "Financije", 2300, 7),
                new Zaposlenik("Marko", "Prodaja", 1700, 1),
                new Zaposlenik("Iva", "Financije", 2600, 9),
                new Zaposlenik("Tomislav", "Podrška", 1600, 3)
        );
    }
}
