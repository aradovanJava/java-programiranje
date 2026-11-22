package hr.tecaj.dan5.completable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture: asinkroni lanci obrade bez blokiranja.
 */
public class CompletableFutureDemo {

    record Korisnik(int id, String ime) { }
    record Ponuda(String artikl, double cijena) { }

    public static void main(String[] args) {
        // region Lanac asinkronih koraka
        CompletableFuture<String> pozdrav = CompletableFuture
                .supplyAsync(() -> dohvatiKorisnika(7))           // u pozadinskoj niti
                // transformacija rezultata
                .thenApply(Korisnik::ime)
                .thenApply(ime -> "Dobro došla, " + ime + "!");
        // join = get bez checked iznimki
        System.out.println(pozdrav.join());
        // endregion

        // region Kombiniranje i paralelno čekanje
        CompletableFuture<Korisnik> korisnik = CompletableFuture.supplyAsync(() -> dohvatiKorisnika(3));
        CompletableFuture<Ponuda> ponuda = CompletableFuture.supplyAsync(() -> dohvatiPonudu("Laptop"));

        // oba rezultata
        CompletableFuture<String> poruka = korisnik.thenCombine(ponuda,
                (k, p) -> "%s, %s je danas %.2f EUR".formatted(k.ime(), p.artikl(), p.cijena()));
        System.out.println(poruka.join());

        List<CompletableFuture<Ponuda>> trgovine = List.of("Miš", "Tipkovnica", "Monitor").stream()
                .map(a -> CompletableFuture.supplyAsync(() -> dohvatiPonudu(a)))
                .toList();
        // čekaj sve
        CompletableFuture.allOf(trgovine.toArray(CompletableFuture[]::new)).join();
        trgovine.forEach(cf -> System.out.println("  " + cf.join()));
        // endregion

        // region Obrada grešaka i timeout
        String sigurno = CompletableFuture
                .supplyAsync(() -> { throw new IllegalStateException("servis nedostupan"); })
                .thenApply(Object::toString)
                .exceptionally(e -> "Zamjenski odgovor (" + e.getCause().getMessage() + ")")
                .join();
        System.out.println(sigurno);

        String brzo = CompletableFuture
                .supplyAsync(() -> { spavaj(2_000); return "spori odgovor"; })
                .completeOnTimeout("odgovor iz cachea", 300, TimeUnit.MILLISECONDS)
                .join();
        System.out.println(brzo);
        // endregion
    }

    static Korisnik dohvatiKorisnika(int id) {
        spavaj(200);
        return new Korisnik(id, id == 7 ? "Ana" : "Ivo");
    }

    static Ponuda dohvatiPonudu(String artikl) {
        spavaj(100 + ThreadLocalRandom.current().nextInt(200));
        return new Ponuda(artikl, 20 + ThreadLocalRandom.current().nextInt(900));
    }

    static void spavaj(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
