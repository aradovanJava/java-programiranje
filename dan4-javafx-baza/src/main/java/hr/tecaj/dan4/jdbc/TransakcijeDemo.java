package hr.tecaj.dan4.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Transakcije: više naredbi koje uspiju ZAJEDNO ili se sve ponište (commit / rollback).
 */
public class TransakcijeDemo {

    public static void main(String[] args) throws SQLException {
        try (Connection veza = DriverManager.getConnection("jdbc:h2:mem:banka", "sa", "")) {
            try (Statement st = veza.createStatement()) {
                st.execute("CREATE TABLE racun (broj VARCHAR(10) PRIMARY KEY, "
                        + "stanje DECIMAL(12,2), "
                        + "CONSTRAINT stanje_nenegativno CHECK (stanje >= 0))"); // pravilo u bazi
                st.execute("INSERT INTO racun VALUES ('A', 500), ('B', 100)");
            }
            prijenos(veza, "A", "B", 200);    // uspije
            prijenos(veza, "B", "A", 1000);   // padne na CHECK → rollback
            ispisi(veza);
        }
    }

    // region Transakcija s commit i rollback
    static void prijenos(Connection veza, String sa, String na, double iznos)
            throws SQLException {
        String sql = "UPDATE racun SET stanje = stanje + ? WHERE broj = ?";
        veza.setAutoCommit(false);                       // počinje transakcija
        try (PreparedStatement ps = veza.prepareStatement(sql)) {
            ps.setDouble(1, iznos);                      // 1) uplata na odredište
            ps.setString(2, na);
            ps.executeUpdate();

            ps.setDouble(1, -iznos);                     // 2) isplata s izvora
            ps.setString(2, sa);
            ps.executeUpdate();                          // može baciti iznimku (CHECK)

            veza.commit();                               // obje promjene trajno
            System.out.println("Prijenos " + iznos + " " + sa + "→" + na + " uspješan");
        } catch (SQLException e) {
            veza.rollback();                             // poništi SVE iz transakcije
            System.out.println("Prijenos poništen (SQLState " + e.getSQLState() + ")");
        } finally {
            veza.setAutoCommit(true);
        }
    }
    // endregion

    static void ispisi(Connection veza) throws SQLException {
        try (Statement st = veza.createStatement();
             ResultSet rs = st.executeQuery("SELECT broj, stanje FROM racun ORDER BY broj")) {
            while (rs.next()) {
                System.out.println(rs.getString("broj") + ": " + rs.getBigDecimal("stanje"));
            }
        }
    }
}
