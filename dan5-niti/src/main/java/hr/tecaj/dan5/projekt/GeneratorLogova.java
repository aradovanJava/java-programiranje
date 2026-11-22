package hr.tecaj.dan5.projekt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Završni projekt – generira testne log datoteke (da projekt radi bez vanjskih podataka).
 */
public class GeneratorLogova {

    private static final String[] PUTANJE = {"/", "/api/proizvodi", "/api/kosarica", "/api/narudzbe",
            "/api/prijava", "/slike/logo.png", "/api/pretraga", "/admin"};
    private static final String[] METODE = {"GET", "GET", "GET", "POST", "PUT", "DELETE"};

    public static List<Path> generiraj(Path mapa, int brojDatoteka, int redakaPoDatoteci) throws IOException {
        Files.createDirectories(mapa);
        Random r = new Random(42);                       // fiksno sjeme → ponovljivi podaci
        List<Path> datoteke = new ArrayList<>();
        LocalDateTime pocetak = LocalDateTime.of(2026, 11, 20, 0, 0);
        for (int d = 1; d <= brojDatoteka; d++) {
            Path datoteka = mapa.resolve("server-%02d.log".formatted(d));
            try (BufferedWriter w = Files.newBufferedWriter(datoteka)) {
                for (int i = 0; i < redakaPoDatoteci; i++) {
                    int status = statusZa(r);
                    w.write("%s 10.0.%d.%d %s %s %d %d".formatted(
                            pocetak.plusSeconds(i * 3L + d), r.nextInt(5), r.nextInt(255),
                            METODE[r.nextInt(METODE.length)], PUTANJE[r.nextInt(PUTANJE.length)],
                            status, 5 + r.nextInt(status >= 500 ? 2_000 : 300)));
                    w.newLine();
                }
                if (d % 4 == 0) w.write("neispravan redak koji treba preskočiti\n");
            }
            datoteke.add(datoteka);
        }
        return datoteke;
    }

    private static int statusZa(Random r) {
        int p = r.nextInt(100);
        if (p < 80) return 200;
        if (p < 88) return 304;
        if (p < 94) return 404;
        if (p < 97) return 401;
        return 500 + r.nextInt(4);
    }
}
