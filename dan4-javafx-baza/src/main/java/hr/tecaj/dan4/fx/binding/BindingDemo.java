package hr.tecaj.dan4.fx.binding;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Svojstva (properties), povezivanje (binding) i slušači promjena (listeners).
 */
public class BindingDemo extends Application {

    @Override
    public void start(Stage stage) {
        // region Jednosmjerni binding
        Slider celzij = new Slider(-30, 50, 20);
        Label prikaz = new Label();
        // label se AUTOMATSKI osvježava kad se slider pomakne
        prikaz.textProperty().bind(Bindings.createStringBinding(
                () -> "%.1f °C = %.1f °F".formatted(celzij.getValue(), celzij.getValue() * 9 / 5 + 32),
                celzij.valueProperty()));
        // endregion

        // region Dvosmjerni binding i uvjetno onemogućavanje
        StringProperty ime = new SimpleStringProperty("Ana");   // "model"
        TextField unos1 = new TextField();
        TextField unos2 = new TextField();
        unos1.textProperty().bindBidirectional(ime);            // promjena u bilo kojem
        unos2.textProperty().bindBidirectional(ime);            // polju mijenja oba

        Button pozdravi = new Button("Pozdravi");
        // gumb neaktivan dok je prazno
        pozdravi.disableProperty().bind(ime.isEmpty());
        Label pozdrav = new Label();
        pozdravi.setOnAction(e -> pozdrav.setText("Bok, " + ime.get() + "!"));
        // endregion

        // region Property i listener
        IntegerProperty brojac = new SimpleIntegerProperty(0);
        Label brojacLabel = new Label();
        brojacLabel.textProperty().bind(brojac.asString("Brojač: %d"));
        brojac.addListener((obs, staro, novo) -> {               // reakcija na promjenu
            if (novo.intValue() % 5 == 0) System.out.println("Višekratnik 5: " + novo);
        });
        Button plus = new Button("+1");
        plus.setOnAction(e -> brojac.set(brojac.get() + 1));
        // endregion

        VBox korijen = new VBox(14,
                new Label("Pretvorba temperature"), celzij, prikaz,
                new Label("Dvosmjerno povezana polja"), unos1, unos2, new HBox(10, pozdravi, pozdrav),
                new HBox(10, plus, brojacLabel));
        korijen.setPadding(new Insets(16));
        stage.setScene(new Scene(korijen, 380, 380));
        stage.setTitle("Binding demo");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
