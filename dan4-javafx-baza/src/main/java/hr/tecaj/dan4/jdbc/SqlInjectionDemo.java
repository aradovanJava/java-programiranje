package hr.tecaj.dan4.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Zašto UVIJEK koristimo PreparedStatement: demonstracija SQL injectiona.
 */
public class SqlInjectionDemo {

    public static void main(String[] args) throws SQLException {
        try (Connection veza = DriverManager.getConnection("jdbc:h2:mem:sigurnost", "sa", "");
             Statement st = veza.createStatement()) {
            st.execute("CREATE TABLE korisnik (ime VARCHAR(50), lozinka VARCHAR(50))");
            st.execute("INSERT INTO korisnik VALUES ('admin', 'Tajna!2026')");

            String napad = "' OR '1'='1";             // zlonamjerni unos u polje za lozinku
            System.out.println("Ranjiva prijava: " + prijavaRanjiva(veza, "admin", napad));
            System.out.println("Sigurna prijava: " + prijavaSigurna(veza, "admin", napad));
            System.out.println("Ispravna lozinka: " + prijavaSigurna(veza, "admin", "Tajna!2026"));
        }
    }

    // region Ranjivo: spajanje stringova
    static boolean prijavaRanjiva(Connection veza, String ime, String lozinka) throws SQLException {
        String sql = "SELECT COUNT(*) FROM korisnik WHERE ime = '" + ime
                + "' AND lozinka = '" + lozinka + "'";
        // postaje: ... WHERE ime = 'admin' AND lozinka = '' OR '1'='1'  → uvijek istina!
        try (Statement st = veza.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1) > 0;
        }
    }
    // endregion

    // region Sigurno: PreparedStatement
    static boolean prijavaSigurna(Connection veza, String ime, String lozinka) throws SQLException {
        String sql = "SELECT COUNT(*) FROM korisnik WHERE ime = ? AND lozinka = ?";
        try (PreparedStatement ps = veza.prepareStatement(sql)) {
            ps.setString(1, ime);          // vrijednost se šalje odvojeno od SQL-a
            ps.setString(2, lozinka);      // → nikad se ne interpretira kao SQL kod
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
        // Napomena: u stvarnoj aplikaciji lozinke se NE spremaju kao čisti tekst,
        // nego kao hash (npr. bcrypt / Argon2).
    }
    // endregion
}
