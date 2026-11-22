package hr.tecaj.dan4.imenik;

import hr.tecaj.dan4.imenik.baza.BazaPodataka;
import hr.tecaj.dan4.imenik.dao.JdbcKontaktDao;
import hr.tecaj.dan4.imenik.dao.KontaktDao;
import hr.tecaj.dan4.imenik.dao.MemorijskiKontaktDao;
import hr.tecaj.dan4.imenik.model.Kontakt;
import hr.tecaj.dan4.imenik.servis.KontaktServis;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * Aplikacija Imenik: JavaFX + FXML + MVC + DAO + H2 baza.
 * Pokretanje: ImenikLauncher. Rad bez baze: VM opcija -Dimenik.dao=memorija
 */
public class ImenikApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(ImenikApp.class);

    // region Učitavanje FXML-a i DI
    @Override
    public void start(Stage stage) throws IOException {
        KontaktServis servis = new KontaktServis(odaberiDao());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("imenik-view.fxml"));
        // DI u kontroler
        loader.setControllerFactory(tip -> new ImenikController(servis));
        Parent korijen = loader.load();

        Scene scena = new Scene(korijen);
        scena.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("imenik.css")).toExternalForm());
        stage.setTitle("Imenik – JavaFX + JDBC");
        stage.setScene(scena);
        stage.show();
    }
    // endregion

    // region Odabir DAO implementacije
    private KontaktDao odaberiDao() {
        if ("memorija".equals(System.getProperty("imenik.dao"))) {
            log.info("Koristim memorijski DAO");
            MemorijskiKontaktDao dao = new MemorijskiKontaktDao();
            dao.spremi(new Kontakt(0, "Test", "Korisnik", "000", "test@primjer.hr"));
            return dao;
        }
        BazaPodataka baza = BazaPodataka.datotecna();
        baza.inicijaliziraj();
        log.info("Koristim JDBC DAO (H2 baza u mapi ./podaci)");
        return new JdbcKontaktDao(baza);
    }
    // endregion
}
