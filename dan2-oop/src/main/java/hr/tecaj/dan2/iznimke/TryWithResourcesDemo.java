package hr.tecaj.dan2.iznimke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * try-with-resources: automatsko zatvaranje resursa koji implementiraju AutoCloseable.
 */
public class TryWithResourcesDemo {

    // region Vlastiti AutoCloseable resurs
    static class VezaNaBazu implements AutoCloseable {
        private final String naziv;

        VezaNaBazu(String naziv) {
            this.naziv = naziv;
            System.out.println("Otvorena veza: " + naziv);
        }

        void upit(String sql) {
            if (sql.isBlank()) throw new IllegalStateException("Prazan upit");
            System.out.println("[" + naziv + "] izvodim: " + sql);
        }

        @Override
        public void close() {
            System.out.println("Zatvorena veza: " + naziv);
        }
    }
    // endregion

    public static void main(String[] args) throws IOException {
        // region try-with-resources
        try (VezaNaBazu a = new VezaNaBazu("A");
             VezaNaBazu b = new VezaNaBazu("B")) {
            a.upit("SELECT 1");
            b.upit("");                          // baca iznimku...
        } catch (IllegalStateException e) {
            System.out.println("Uhvaćeno: " + e.getMessage());
        }                                        // ...ali B i A su ipak zatvoreni (obrnutim redom)
        // endregion

        // region Čitanje teksta s try-with-resources
        String tekst = "prvi redak\ndrugi redak\ntreći redak";
        try (BufferedReader reader = new BufferedReader(new StringReader(tekst))) {
            String redak;
            int broj = 1;
            while ((redak = reader.readLine()) != null) {
                System.out.println(broj++ + ": " + redak);
            }
        }
        // endregion
    }
}
