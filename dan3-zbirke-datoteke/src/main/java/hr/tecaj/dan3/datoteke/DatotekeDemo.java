package hr.tecaj.dan3.datoteke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

/**
 * NIO.2 (java.nio.file): Path i Files – stvaranje, pisanje, čitanje, kopiranje, brisanje.
 * Sve datoteke stvaraju se u mapi "izlaz" unutar radnog direktorija.
 */
public class DatotekeDemo {

    public static void main(String[] args) throws IOException {
        // region Path – putanje
        Path mapa = Path.of("izlaz", "dnevnik");          // relativna putanja
        Path datoteka = mapa.resolve("biljeske.txt");     // izlaz/dnevnik/biljeske.txt
        System.out.println(datoteka.getFileName());       // biljeske.txt
        System.out.println(datoteka.getParent());         // izlaz/dnevnik
        System.out.println(datoteka.toAbsolutePath());    // puna putanja na disku
        System.out.println(Files.exists(datoteka));       // postoji li?
        // endregion

        // region Pisanje i čitanje cijele datoteke
        Files.createDirectories(mapa);                    // stvara i sve roditelje
        Files.writeString(datoteka, "Dan 3: zbirke i datoteke\n", StandardCharsets.UTF_8);
        Files.writeString(datoteka, "Naučili smo Files i Path\n",
                StandardOpenOption.APPEND);               // dodavanje na kraj
        Files.write(datoteka, List.of("redak A", "redak B"), StandardOpenOption.APPEND);

        String sve = Files.readString(datoteka);          // cijela datoteka kao String
        List<String> retci = Files.readAllLines(datoteka); // lista redaka
        System.out.println(retci.size() + " retka, " + Files.size(datoteka) + " bajtova");
        // endregion
        System.out.print(sve);

        // region Velike datoteke – redak po redak
        Path velika = mapa.resolve("brojevi.txt");
        try (BufferedWriter w = Files.newBufferedWriter(velika)) {
            for (int i = 1; i <= 10_000; i++) {
                w.write("broj " + i);
                w.newLine();
            }
        }
        try (BufferedReader r = Files.newBufferedReader(velika)) {
            String redak;
            int n = 0;
            while ((redak = r.readLine()) != null) {
                if (redak.endsWith("000")) n++;
            }
            System.out.println("Redaka koji završavaju s 000: " + n);  // 10
        }
        try (Stream<String> linije = Files.lines(velika)) {   // lijeno, stream redaka
            System.out.println(linije.filter(l -> l.contains("77")).count());
        }
        // endregion

        // region Kopiranje, premještanje, popis i brisanje
        Path kopija = mapa.resolve("biljeske-kopija.txt");
        Files.copy(datoteka, kopija, StandardCopyOption.REPLACE_EXISTING);
        Path arhiva = Files.createDirectories(Path.of("izlaz", "arhiva"));
        Files.move(kopija, arhiva.resolve("biljeske-stare.txt"),
                StandardCopyOption.REPLACE_EXISTING);

        try (Stream<Path> stablo = Files.walk(Path.of("izlaz"))) {   // rekurzivni obilazak
            stablo.filter(Files::isRegularFile)
                  .forEach(p -> System.out.println("  " + p));
        }
        Files.deleteIfExists(velika);
        // endregion
    }
}
