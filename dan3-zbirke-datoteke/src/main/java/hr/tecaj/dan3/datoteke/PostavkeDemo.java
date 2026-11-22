package hr.tecaj.dan3.datoteke;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Properties datoteke: jednostavna konfiguracija u obliku kljuc=vrijednost.
 */
public class PostavkeDemo {

    public static void main(String[] args) throws IOException {
        Path datoteka = Path.of("izlaz", "aplikacija.properties");
        Files.createDirectories(datoteka.getParent());

        // region Zapis i čitanje Properties
        Properties postavke = new Properties();
        postavke.setProperty("db.url", "jdbc:h2:./podaci/tecaj");
        postavke.setProperty("db.korisnik", "sa");
        postavke.setProperty("ui.tema", "tamna");
        try (Writer w = Files.newBufferedWriter(datoteka)) {
            postavke.store(w, "Postavke aplikacije");
        }

        Properties ucitane = new Properties();
        try (Reader r = Files.newBufferedReader(datoteka)) {
            ucitane.load(r);
        }
        String tema = ucitane.getProperty("ui.tema");
        int velicinaPoola = Integer.parseInt(ucitane.getProperty("pool.velicina", "4")); // zadano
        // endregion
        System.out.println("Tema: " + tema + ", veličina poola: " + velicinaPoola);
        System.out.println(Files.readString(datoteka));
    }
}
