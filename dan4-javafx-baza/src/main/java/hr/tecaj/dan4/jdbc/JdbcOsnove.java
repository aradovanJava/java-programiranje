package hr.tecaj.dan4.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC osnove nad ugrađenom H2 bazom u memoriji: spajanje, DDL, CRUD i batch.
 */
public class JdbcOsnove {

    // region Spajanje na bazu
    // mem: baza živi u memoriji; DB_CLOSE_DELAY=-1 čuva je dok JVM radi
    private static final String URL = "jdbc:h2:mem:tecaj;DB_CLOSE_DELAY=-1";

    static Connection spoji() throws SQLException {
        return DriverManager.getConnection(URL, "sa", "");   // korisnik, lozinka
    }
    // endregion

    // region Record za redak tablice
    record Polaznik(long id, String ime, String email, int bodovi) { }
    // endregion

    public static void main(String[] args) throws SQLException {
        try (Connection veza = spoji()) {
            kreirajTablicu(veza);
            long anaId = dodaj(veza, "Ana Horvat", "ana@primjer.hr", 87);
            dodaj(veza, "Ivo Kovač", "ivo@primjer.hr", 64);
            dodaj(veza, "Maja Babić", "maja@primjer.hr", 92);
            dodajVise(veza, List.of("Luka", "Petra", "Iva"));
            System.out.println("Novi ID za Anu: " + anaId);

            System.out.println("Bodovi >= 80: " + pronadjiSBodovimaVecimOd(veza, 80));
            System.out.println("Obrisano redaka: " + obrisiBezBodova(veza));
            System.out.println("Ažurirano redaka: " + povecajBodove(veza, 5, 70));
            pronadjiSBodovimaVecimOd(veza, -1).forEach(System.out::println);
        }
    }

    // region DDL sa Statementom
    static void kreirajTablicu(Connection veza) throws SQLException {
        try (Statement st = veza.createStatement()) {
            st.execute("""
                    CREATE TABLE IF NOT EXISTS polaznik (
                        id     BIGINT AUTO_INCREMENT PRIMARY KEY,
                        ime    VARCHAR(100) NOT NULL,
                        email  VARCHAR(150) UNIQUE,
                        bodovi INT DEFAULT 0
                    )""");
        }
    }
    // endregion

    // region INSERT s PreparedStatementom
    static long dodaj(Connection veza, String ime, String email, int bodovi) throws SQLException {
        String sql = "INSERT INTO polaznik (ime, email, bodovi) VALUES (?, ?, ?)";
        try (PreparedStatement ps = veza.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ime);           // indeksi parametara kreću od 1!
            ps.setString(2, email);
            ps.setInt(3, bodovi);
            ps.executeUpdate();
            try (ResultSet kljucevi = ps.getGeneratedKeys()) {
                kljucevi.next();
                return kljucevi.getLong(1);  // ID koji je generirala baza
            }
        }
    }
    // endregion

    // region SELECT i ResultSet
    static List<Polaznik> pronadjiSBodovimaVecimOd(Connection veza, int prag) throws SQLException {
        String sql = "SELECT id, ime, email, bodovi FROM polaznik WHERE bodovi > ? ORDER BY bodovi DESC";
        List<Polaznik> rezultat = new ArrayList<>();
        try (PreparedStatement ps = veza.prepareStatement(sql)) {
            ps.setInt(1, prag);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {                       // kursor redak po redak
                    rezultat.add(new Polaznik(
                            rs.getLong("id"),
                            rs.getString("ime"),
                            rs.getString("email"),
                            rs.getInt("bodovi")));
                }
            }
        }
        return rezultat;
    }
    // endregion

    // region UPDATE i DELETE
    static int povecajBodove(Connection veza, int za, int ispod) throws SQLException {
        try (PreparedStatement ps = veza.prepareStatement(
                "UPDATE polaznik SET bodovi = bodovi + ? WHERE bodovi < ?")) {
            ps.setInt(1, za);
            ps.setInt(2, ispod);
            return ps.executeUpdate();                    // broj promijenjenih redaka
        }
    }

    static int obrisiBezBodova(Connection veza) throws SQLException {
        try (PreparedStatement ps = veza.prepareStatement(
                "DELETE FROM polaznik WHERE bodovi = 0 OR bodovi IS NULL")) {
            return ps.executeUpdate();
        }
    }
    // endregion

    // region Batch unos
    static void dodajVise(Connection veza, List<String> imena) throws SQLException {
        try (PreparedStatement ps = veza.prepareStatement(
                "INSERT INTO polaznik (ime, bodovi) VALUES (?, 0)")) {
            for (String ime : imena) {
                ps.setString(1, ime);
                ps.addBatch();                            // skupi naredbe...
            }
            int[] rezultati = ps.executeBatch();          // ...i pošalji odjednom
            System.out.println("Batch: dodano " + rezultati.length + " redaka");
        }
    }
    // endregion
}
