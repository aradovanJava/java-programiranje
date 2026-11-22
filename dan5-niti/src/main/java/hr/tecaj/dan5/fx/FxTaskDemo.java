package hr.tecaj.dan5.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * JavaFX i niti: dugotrajan posao NIKAD na JavaFX Application Threadu.
 * Task radi u pozadini, a napredak i rezultat sigurno javlja sučelju.
 */
public class FxTaskDemo extends Application {

    private final ExecutorService pozadina = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void start(Stage stage) {
        ProgressBar traka = new ProgressBar(0);
        traka.setMaxWidth(Double.MAX_VALUE);           // traka zauzima cijelu širinu
        Label status = new Label("Spremno");
        ListView<String> rezultati = new ListView<>();
        rezultati.setPrefHeight(200);                  // lista ne "istiskuje" traku napretka
        VBox.setVgrow(rezultati, Priority.ALWAYS);
        Button pokreni = new Button("Obradi datoteke");
        Button odustani = new Button("Odustani");
        odustani.setDisable(true);

        pokreni.setOnAction(e -> {
            // region Task u pozadini
            Task<List<String>> obrada = new Task<>() {
                @Override
                protected List<String> call() throws Exception { // POZADINSKA nit
                    List<String> gotovo = new ArrayList<>();
                    int ukupno = 20;
                    for (int i = 1; i <= ukupno; i++) {
                        if (isCancelled()) break;
                        Thread.sleep(150);                         // "teški" posao
                        gotovo.add("datoteka-" + i + ".csv obrađena");
                        updateProgress(i, ukupno);                 // sigurno ažuriranje UI-ja
                        updateMessage("Obrađeno " + i + " / " + ukupno);
                    }
                    return gotovo;
                }
            };
            // endregion
            // region Povezivanje Taska sa sučeljem
            traka.progressProperty().bind(obrada.progressProperty());   // binding na Task
            status.textProperty().bind(obrada.messageProperty());
            pokreni.disableProperty().bind(obrada.runningProperty());
            odustani.disableProperty().bind(obrada.runningProperty().not());

            // ovi se rukovatelji izvode na JavaFX niti
            obrada.setOnSucceeded(ev -> rezultati.getItems().setAll(obrada.getValue()));
            obrada.setOnFailed(ev -> rezultati.getItems()
                    .setAll("Greška: " + obrada.getException().getMessage()));
            odustani.setOnAction(ev -> obrada.cancel());

            pozadina.submit(obrada);                               // pokreni u pozadini
            // endregion
        });

        // region Platform.runLater
        Thread.ofVirtual().start(() -> {
            // posao izvan FX niti
            String poruka = "Pozadinska nit javlja: veza na bazu OK";
            // izmjena UI-ja NA FX niti
            Platform.runLater(() -> stage.setTitle(poruka));
        });
        // endregion

        VBox korijen = new VBox(12, new HBox(10, pokreni, odustani), traka, status, rezultati);
        korijen.setPadding(new Insets(16));
        stage.setScene(new Scene(korijen, 420, 380));
        stage.setTitle("JavaFX Task");
        stage.show();
    }

    @Override
    public void stop() {
        pozadina.shutdownNow();                                    // ne ostavljaj niti iza sebe
    }
}
