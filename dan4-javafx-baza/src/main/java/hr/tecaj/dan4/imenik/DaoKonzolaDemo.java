package hr.tecaj.dan4.imenik;

import hr.tecaj.dan4.imenik.baza.BazaPodataka;
import hr.tecaj.dan4.imenik.dao.JdbcKontaktDao;
import hr.tecaj.dan4.imenik.dao.KontaktDao;
import hr.tecaj.dan4.imenik.model.Kontakt;

/**
 * DAO bez grafičkog sučelja – provjera CRUD operacija prije spajanja na JavaFX.
 * Koristi bazu u memoriji, pa ne dira podatke aplikacije Imenik.
 */
public class DaoKonzolaDemo {

    public static void main(String[] args) {
        // region DAO u konzoli
        BazaPodataka baza = new BazaPodataka("jdbc:h2:mem:imenikTest;DB_CLOSE_DELAY=-1");
        baza.inicijaliziraj();                         // shema + početni podaci
        KontaktDao dao = new JdbcKontaktDao(baza);

        Kontakt novi = dao.spremi(new Kontakt(0, "Petra", "Jurić", "091 000 1111", "petra@primjer.hr"));
        System.out.println("Dodan s ID-em " + novi.getId());

        novi.setTelefon("099 999 9999");
        dao.spremi(novi);                              // UPDATE jer ID postoji
        System.out.println(dao.dohvatiPoId(novi.getId()).orElseThrow());

        dao.obrisi(1);
        dao.dohvatiSve().forEach(System.out::println);
        // endregion
    }
}
