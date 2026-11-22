package hr.tecaj.dan5.virtualne;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

/**
 * Strukturirana konkurentnost (Java 25 – PREVIEW, JEP 505).
 * Podzadaci žive unutar bloka: ako jedan padne, ostali se otkazuju; nema "izgubljenih" niti.
 *
 * <p>Zahtijeva --enable-preview (postavljeno u pom.xml; u IntelliJ-u: Language level "25 (Preview)").
 */
public class StrukturiranaKonkurentnostDemo {

    record Profil(String korisnik, List<String> narudzbe, int bodovi) { }

    // region StructuredTaskScope
    static Profil dohvatiProfil(int id) throws InterruptedException {
        try (var scope = StructuredTaskScope.open()) {        // svi moraju uspjeti
            Subtask<String> korisnik = scope.fork(() -> dohvatiKorisnika(id));
            Subtask<List<String>> narudzbe = scope.fork(() -> dohvatiNarudzbe(id));
            Subtask<Integer> bodovi = scope.fork(() -> dohvatiBodove(id));

            // čeka sve; ako jedan baci iznimku, ostali se prekidaju
            scope.join();
            return new Profil(korisnik.get(), narudzbe.get(), bodovi.get());
        }
    }
    // endregion

    public static void main(String[] args) throws InterruptedException {
        long start = System.nanoTime();
        System.out.println(dohvatiProfil(7));
        System.out.printf("Trajalo %d ms (paralelno, a ne 300 + 200 + 250)%n",
                (System.nanoTime() - start) / 1_000_000);

        try {
            dohvatiProfil(-1);                                  // jedan podzadatak pada
        } catch (StructuredTaskScope.FailedException e) {
            System.out.println("Profil nije dohvaćen: " + e.getCause().getMessage());
        }
    }

    static String dohvatiKorisnika(int id) throws InterruptedException {
        Thread.sleep(Duration.ofMillis(300));
        if (id < 0) throw new IllegalArgumentException("Nepostojeći korisnik " + id);
        return "Ana (id " + id + ")";
    }

    static List<String> dohvatiNarudzbe(int id) throws InterruptedException {
        Thread.sleep(Duration.ofMillis(200));
        return List.of("#1001 Laptop", "#1004 SSD");
    }

    static int dohvatiBodove(int id) throws InterruptedException {
        Thread.sleep(Duration.ofMillis(250));
        return 1_250;
    }
}
