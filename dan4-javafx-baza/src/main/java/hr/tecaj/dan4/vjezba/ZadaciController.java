package hr.tecaj.dan4.vjezba;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Rješenje vježbe Dana 4 – kontroler za listu zadataka.
 */
public class ZadaciController {

    @FXML private TextField poljeNaslov;
    @FXML private ComboBox<Zadatak.Prioritet> odabirPrioriteta;
    @FXML private Button gumbDodaj;
    @FXML private CheckBox samoOtvoreni;
    @FXML private ListView<Zadatak> popis;
    @FXML private Label sazetak;

    private final ZadatakDao dao;
    private final ObservableList<Zadatak> zadaci = FXCollections.observableArrayList();

    public ZadaciController(ZadatakDao dao) {
        this.dao = dao;
    }

    @FXML
    private void initialize() {
        odabirPrioriteta.getItems().setAll(Zadatak.Prioritet.values());
        odabirPrioriteta.setValue(Zadatak.Prioritet.SREDNJI);
        gumbDodaj.disableProperty().bind(poljeNaslov.textProperty().isEmpty());

        FilteredList<Zadatak> filtrirani = new FilteredList<>(zadaci);
        filtrirani.predicateProperty().bind(Bindings.createObjectBinding(
                () -> samoOtvoreni.isSelected() ? z -> !z.gotov() : z -> true,
                samoOtvoreni.selectedProperty()));
        popis.setItems(filtrirani);

        // vlastita ćelija: CheckBox s naslovom i prioritetom
        popis.setCellFactory(lv -> new ListCell<>() {
            private final CheckBox cb = new CheckBox();
            @Override
            protected void updateItem(Zadatak z, boolean prazno) {
                super.updateItem(z, prazno);
                if (prazno || z == null) {
                    setGraphic(null);
                    return;
                }
                cb.setText("[" + z.prioritet() + "] " + z.naslov());
                cb.setSelected(z.gotov());
                cb.setOnAction(e -> {
                    dao.azuriraj(z.oznaciGotovim(cb.isSelected()));
                    osvjezi();
                });
                setGraphic(cb);
            }
        });
        osvjezi();
    }

    @FXML
    private void onDodaj() {
        dao.dodaj(poljeNaslov.getText().strip(), odabirPrioriteta.getValue());
        poljeNaslov.clear();
        osvjezi();
    }

    @FXML
    private void onObrisiGotove() {
        dao.obrisiGotove();
        osvjezi();
    }

    private void osvjezi() {
        zadaci.setAll(dao.dohvatiSve());
        long otvoreni = zadaci.stream().filter(z -> !z.gotov()).count();
        sazetak.setText("Otvoreno: " + otvoreni + " / ukupno: " + zadaci.size());
    }
}
