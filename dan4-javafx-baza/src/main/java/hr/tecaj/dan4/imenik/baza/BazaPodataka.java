package hr.tecaj.dan4.imenik.baza;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Jedno mjesto za konfiguraciju veze na bazu i inicijalizaciju sheme.
 */
public class BazaPodataka {

    // region Konfiguracija veze
    private final String url;

    public BazaPodataka(String url) {
        this.url = url;
    }

    /** Datotečna baza u mapi "podaci" radnog direktorija (podaci/imenik.mv.db). */
    public static BazaPodataka datotecna() {
        return new BazaPodataka("jdbc:h2:./podaci/imenik");
    }

    public Connection spoji() throws SQLException {
        return DriverManager.getConnection(url, "sa", "");
    }
    // endregion

    // region Inicijalizacija sheme iz SQL skripte
    public void inicijaliziraj() {
        try (InputStream in = Objects.requireNonNull(
                getClass().getResourceAsStream("/sql/imenik-schema.sql"));
             Connection veza = spoji();
             Statement st = veza.createStatement()) {
            String skripta = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            for (String naredba : skripta.split(";")) {
                if (!naredba.isBlank()) st.execute(naredba);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (SQLException e) {
            throw new IllegalStateException("Inicijalizacija baze nije uspjela", e);
        }
    }
    // endregion
}
