package hr.tecaj.dan4.fx.kontrole;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Najčešće kontrole, obrada događaja i stiliziranje CSS-om (kontrole.css).
 */
public class KontroleDemo extends Application {

    @Override
    public void start(Stage stage) {
        // region Kontrole obrasca
        TextField ime = new TextField();
        ime.setPromptText("Ime i prezime");

        ComboBox<String> tecaj = new ComboBox<>(
                FXCollections.observableArrayList("Java osnove", "JavaFX", "Spring Boot"));
        tecaj.getSelectionModel().selectFirst();

        ToggleGroup razina = new ToggleGroup();               // samo jedan odabran
        RadioButton pocetnik = new RadioButton("Početnik");
        RadioButton napredni = new RadioButton("Napredni");
        pocetnik.setToggleGroup(razina);
        napredni.setToggleGroup(razina);
        pocetnik.setSelected(true);

        DatePicker datum = new DatePicker();
        Slider predznanje = new Slider(0, 10, 5);
        predznanje.setShowTickLabels(true);
        CheckBox uvjeti = new CheckBox("Prihvaćam uvjete");
        ListView<String> teme = new ListView<>(FXCollections.observableArrayList(
                "Klase", "Nasljeđivanje", "Zbirke", "JSON", "JDBC", "Niti"));
        teme.setPrefHeight(110);
        // endregion

        // region Obrada događaja
        Button prijava = new Button("Prijavi se");
        prijava.getStyleClass().add("glavni-gumb");          // CSS klasa
        prijava.setOnAction(e -> {
            if (ime.getText().isBlank() || !uvjeti.isSelected()) {
                new Alert(Alert.AlertType.WARNING, "Unesi ime i prihvati uvjete.").showAndWait();
                return;
            }
            String odabranaRazina = ((RadioButton) razina.getSelectedToggle()).getText();
            new Alert(Alert.AlertType.INFORMATION, "%s, prijavljen/a si na \"%s\" (%s)."
                    .formatted(ime.getText(), tecaj.getValue(), odabranaRazina)).showAndWait();
        });
        teme.getSelectionModel().selectedItemProperty().addListener(
                (obs, staro, novo) -> stage.setTitle("Odabrana tema: " + novo));
        // endregion

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);
        grid.setPadding(new Insets(16));
        grid.addRow(0, new Label("Polaznik:"), ime);
        grid.addRow(1, new Label("Tečaj:"), tecaj);
        grid.addRow(2, new Label("Razina:"), new HBox(12, pocetnik, napredni));
        grid.addRow(3, new Label("Početak:"), datum);
        grid.addRow(4, new Label("Predznanje:"), predznanje);
        grid.addRow(5, new Label("Teme:"), teme);
        grid.addRow(6, uvjeti, prijava);

        // region Stiliziranje CSS-om
        Scene scena = new Scene(grid, 480, 470);
        scena.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("kontrole.css")).toExternalForm());
        // endregion
        stage.setScene(scena);
        stage.setTitle("JavaFX kontrole");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
