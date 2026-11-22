package hr.tecaj.dan4.vjezba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Rješenje vježbe Dana 4 – JDBC DAO za zadatke.
 */
public class ZadatakDao {

    private final String url;

    public ZadatakDao(String url) {
        this.url = url;
        izvedi("""
                CREATE TABLE IF NOT EXISTS zadatak (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    naslov VARCHAR(200) NOT NULL,
                    prioritet VARCHAR(10) NOT NULL,
                    gotov BOOLEAN DEFAULT FALSE)""");
    }

    public List<Zadatak> dohvatiSve() {
        String sql = """
                SELECT * FROM zadatak
                ORDER BY gotov,
                         CASE prioritet WHEN 'VISOK' THEN 1 WHEN 'SREDNJI' THEN 2 ELSE 3 END,
                         id""";
        try (Connection c = spoji(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            List<Zadatak> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new Zadatak(rs.getLong("id"), rs.getString("naslov"),
                        Zadatak.Prioritet.valueOf(rs.getString("prioritet")), rs.getBoolean("gotov")));
            }
            return lista;
        } catch (SQLException e) {
            throw new IllegalStateException("Dohvat zadataka nije uspio", e);
        }
    }

    public void dodaj(String naslov, Zadatak.Prioritet prioritet) {
        try (Connection c = spoji();
             PreparedStatement ps = c.prepareStatement("INSERT INTO zadatak (naslov, prioritet) VALUES (?, ?)")) {
            ps.setString(1, naslov);
            ps.setString(2, prioritet.name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Dodavanje nije uspjelo", e);
        }
    }

    public void azuriraj(Zadatak z) {
        try (Connection c = spoji();
             PreparedStatement ps = c.prepareStatement("UPDATE zadatak SET gotov = ? WHERE id = ?")) {
            ps.setBoolean(1, z.gotov());
            ps.setLong(2, z.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Ažuriranje nije uspjelo", e);
        }
    }

    public int obrisiGotove() {
        try (Connection c = spoji();
             PreparedStatement ps = c.prepareStatement("DELETE FROM zadatak WHERE gotov = TRUE")) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Brisanje nije uspjelo", e);
        }
    }

    private Connection spoji() throws SQLException {
        return DriverManager.getConnection(url, "sa", "");
    }

    private void izvedi(String sql) {
        try (Connection c = spoji(); Statement st = c.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
