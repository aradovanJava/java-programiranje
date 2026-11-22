package hr.tecaj.dan4.vjezba;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Rješenje vježbe Dana 4 – "Moji zadaci" (JavaFX + FXML + JDBC).
 */
public class ZadaciApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ZadatakDao dao = new ZadatakDao("jdbc:h2:./podaci/zadaci");
        if (dao.dohvatiSve().isEmpty()) {
            dao.dodaj("Pročitati poglavlje o JDBC-u", Zadatak.Prioritet.VISOK);
            dao.dodaj("Riješiti vježbu s JavaFX-om", Zadatak.Prioritet.SREDNJI);
            dao.dodaj("Instalirati Scene Builder", Zadatak.Prioritet.NIZAK);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("zadaci-view.fxml"));
        loader.setControllerFactory(tip -> new ZadaciController(dao));
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Moji zadaci");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
