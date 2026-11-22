package hr.tecaj.dan4.fx.layouti;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Najčešći layouti: BorderPane, HBox, VBox, GridPane, FlowPane i StackPane.
 */
public class LayoutiDemo extends Application {

    @Override
    public void start(Stage stage) {
        // region BorderPane kao okvir aplikacije
        BorderPane okvir = new BorderPane();
        okvir.setTop(alatnaTraka());      // gore
        okvir.setLeft(izbornik());        // lijevo
        okvir.setCenter(obrazac());       // sredina – zauzima preostali prostor
        okvir.setRight(znacka());         // desno
        okvir.setBottom(oznake());        // dolje
        // endregion

        stage.setScene(new Scene(okvir, 760, 420));
        stage.setTitle("JavaFX layouti");
        stage.show();
    }

    // region HBox i VBox
    private HBox alatnaTraka() {
        HBox traka = new HBox(8, new Button("Novo"), new Button("Otvori"), new Button("Spremi"));
        traka.setPadding(new Insets(10));
        traka.setStyle("-fx-background-color: #2A3340;");
        return traka;                            // elementi VODORAVNO
    }

    private VBox izbornik() {
        VBox meni = new VBox(6, new Label("Kontakti"), new Label("Grupe"), new Label("Postavke"));
        meni.setPadding(new Insets(12));
        meni.setPrefWidth(120);
        return meni;                             // elementi OKOMITO
    }
    // endregion

    // region GridPane – obrasci
    private GridPane obrazac() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));
        grid.add(new Label("Ime:"), 0, 0);              // (stupac, redak)
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("E-mail:"), 0, 1);
        grid.add(new TextField(), 1, 1);
        grid.add(new Label("Napomena:"), 0, 2);
        TextArea napomena = new TextArea();
        grid.add(napomena, 1, 2);
        GridPane.setVgrow(napomena, Priority.ALWAYS);    // raste s prozorom
        return grid;
    }
    // endregion

    // region StackPane i FlowPane
    private StackPane znacka() {
        Circle krug = new Circle(40, Color.web("#FAA818"));
        Label tekst = new Label("JFX");
        StackPane stog = new StackPane(krug, tekst);    // čvorovi jedan PREKO drugoga
        stog.setPadding(new Insets(12));
        stog.setAlignment(Pos.TOP_CENTER);
        return stog;
    }

    private FlowPane oznake() {
        FlowPane tok = new FlowPane(6, 6);              // prelama u novi red kad nema mjesta
        tok.setPadding(new Insets(10));
        for (String o : new String[]{"#java", "#javafx", "#mvc", "#fxml", "#css", "#jdbc"}) {
            tok.getChildren().add(new Button(o));
        }
        return tok;
    }
    // endregion

    public static void main(String[] args) {
        launch(args);
    }
}
