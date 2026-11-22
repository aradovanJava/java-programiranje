package hr.tecaj.dan4.imenik.dao;

import hr.tecaj.dan4.imenik.baza.BazaPodataka;
import hr.tecaj.dan4.imenik.model.Kontakt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO implementacija nad relacijskom bazom (JDBC).
 * SQLException se prevodi u neprovjerenu DaoException – gornji slojevi ne ovise o JDBC-u.
 */
public class JdbcKontaktDao implements KontaktDao {

    public static class DaoException extends RuntimeException {
        public DaoException(String poruka, Throwable uzrok) {
            super(poruka, uzrok);
        }
    }

    private final BazaPodataka baza;

    public JdbcKontaktDao(BazaPodataka baza) {
        this.baza = baza;
    }

    // region Dohvat svih kontakata
    @Override
    public List<Kontakt> dohvatiSve() {
        String sql = "SELECT id, ime, prezime, telefon, email "
                   + "FROM kontakt ORDER BY prezime, ime";
        try (Connection veza = baza.spoji();
             PreparedStatement ps = veza.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Kontakt> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapiraj(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new DaoException("Dohvat kontakata nije uspio", e);
        }
    }

    // jedan redak ResultSeta → jedan objekt
    private static Kontakt mapiraj(ResultSet rs) throws SQLException {
        return new Kontakt(rs.getLong("id"), rs.getString("ime"),
                rs.getString("prezime"), rs.getString("telefon"),
                rs.getString("email"));
    }
    // endregion

    @Override
    public Optional<Kontakt> dohvatiPoId(long id) {
        String sql = "SELECT id, ime, prezime, telefon, email FROM kontakt WHERE id = ?";
        try (Connection veza = baza.spoji(); PreparedStatement ps = veza.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapiraj(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("Dohvat kontakta " + id + " nije uspio", e);
        }
    }

    // region Spremanje: INSERT ili UPDATE
    @Override
    public Kontakt spremi(Kontakt k) {
        boolean novi = k.getId() == 0;
        String sql = novi
                ? "INSERT INTO kontakt (ime, prezime, telefon, email) "
                  + "VALUES (?, ?, ?, ?)"
                : "UPDATE kontakt SET ime = ?, prezime = ?, telefon = ?, "
                  + "email = ? WHERE id = ?";
        try (Connection veza = baza.spoji();
             PreparedStatement ps = veza.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, k.getIme());
            ps.setString(2, k.getPrezime());
            ps.setString(3, k.getTelefon());
            ps.setString(4, k.getEmail());
            if (!novi) ps.setLong(5, k.getId());
            ps.executeUpdate();
            try (ResultSet kljuc = ps.getGeneratedKeys()) {
                if (novi && kljuc.next()) k.setId(kljuc.getLong(1));
            }
            return k;
        } catch (SQLException e) {
            throw new DaoException("Spremanje kontakta nije uspjelo", e);
        }
    }
    // endregion

    @Override
    public boolean obrisi(long id) {
        try (Connection veza = baza.spoji();
             PreparedStatement ps = veza.prepareStatement("DELETE FROM kontakt WHERE id = ?")) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Brisanje kontakta " + id + " nije uspjelo", e);
        }
    }
}
