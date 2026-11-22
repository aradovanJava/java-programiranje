package hr.tecaj.dan4.fx.uvod;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Prva JavaFX aplikacija: Stage → Scene → graf scene (čvorovi).
 * Pokretanje: HelloFxLauncher (ili mvn javafx:run -Dfx.main=hr.tecaj.dan4.fx.uvod.HelloFxLauncher).
 */
public class HelloFx extends Application {

    // region Prva JavaFX aplikacija
    private int brojKlikova = 0;

    // stage = prozor koji dobijemo od platforme

    @Override
    public void start(Stage stage) {
        Label poruka = new Label("Pozdrav iz JavaFX-a!");
        Button gumb = new Button("Klikni me");
        gumb.setOnAction(e -> {                       // obrada događaja lambdom
            brojKlikova++;
            poruka.setText("Kliknuto " + brojKlikova + " puta");
        });

        VBox korijen = new VBox(15, poruka, gumb);    // layout s razmakom 15 px
        korijen.setAlignment(Pos.CENTER);

        Scene scena = new Scene(korijen, 360, 200);   // scena = sadržaj prozora
        stage.setTitle("Hello JavaFX");
        stage.setScene(scena);
        stage.show();
    }
    // endregion

    // region Životni ciklus aplikacije
    // 1. prije start(), NIJE na JavaFX niti – bez rada s UI-jem
    @Override
    public void init() {
        System.out.println("init() – učitavanje postavki");
    }

    // 3. pri zatvaranju aplikacije – spremanje, zatvaranje resursa

    @Override
    public void stop() {
        System.out.println("stop() – aplikacija se zatvara");
    }
    // endregion
}
