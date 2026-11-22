package hr.tecaj.dan5.projekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Završni projekt – sprema sažetke analiza u H2 bazu (povijest pokretanja).
 */
public class AnalizaRepozitorij {

    public record Zapis(long id, LocalDateTime vrijeme, long zapisa, double postotak5xx, long trajanjeAnalizeMs) { }

    private final String url;

    public AnalizaRepozitorij(String url) {
        this.url = url;
        try (Connection c = spoji(); Statement st = c.createStatement()) {
            st.execute("""
                    CREATE TABLE IF NOT EXISTS analiza (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        vrijeme TIMESTAMP NOT NULL,
                        zapisa BIGINT NOT NULL,
                        postotak_5xx DOUBLE,
                        trajanje_ms BIGINT,
                        izvjestaj_json CLOB)""");
        } catch (SQLException e) {
            throw new IllegalStateException("Ne mogu inicijalizirati bazu", e);
        }
    }

    public long spremi(AnalizatorLogova.Statistika s, long trajanjeMs, String json) {
        String sql = "INSERT INTO analiza (vrijeme, zapisa, postotak_5xx, trajanje_ms, izvjestaj_json) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection c = spoji();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(2, s.zapisa());
            ps.setDouble(3, s.postotak5xx());
            ps.setLong(4, trajanjeMs);
            ps.setString(5, json);
            ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) {
                return k.next() ? k.getLong(1) : -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Spremanje analize nije uspjelo", e);
        }
    }

    public List<Zapis> zadnje(int n) {
        String sql = "SELECT id, vrijeme, zapisa, postotak_5xx, trajanje_ms FROM analiza ORDER BY id DESC LIMIT ?";
        try (Connection c = spoji(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, n);
            try (ResultSet rs = ps.executeQuery()) {
                List<Zapis> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(new Zapis(rs.getLong(1), rs.getTimestamp(2).toLocalDateTime(),
                            rs.getLong(3), rs.getDouble(4), rs.getLong(5)));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Dohvat povijesti nije uspio", e);
        }
    }

    private Connection spoji() throws SQLException {
        return DriverManager.getConnection(url, "sa", "");
    }
}
