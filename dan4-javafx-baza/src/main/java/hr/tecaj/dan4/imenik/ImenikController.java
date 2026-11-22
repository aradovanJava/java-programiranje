package hr.tecaj.dan4.imenik;

import hr.tecaj.dan4.imenik.model.Kontakt;
import hr.tecaj.dan4.imenik.servis.KontaktServis;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * CONTROLLER: povezuje View (FXML) i Model (servis/DAO). Ne sadrži SQL ni izgled.
 */
public class ImenikController {

    // region @FXML polja
    @FXML private TableView<Kontakt> tablica;            // ime polja = fx:id u FXML-u
    @FXML private TableColumn<Kontakt, String> stupacIme;
    @FXML private TableColumn<Kontakt, String> stupacPrezime;
    @FXML private TableColumn<Kontakt, String> stupacTelefon;
    @FXML private TableColumn<Kontakt, String> stupacEmail;
    @FXML private TextField poljePretraga;
    @FXML private TextField poljeIme;
    @FXML private TextField poljePrezime;
    @FXML private TextField poljeTelefon;
    @FXML private TextField poljeEmail;
    @FXML private Button gumbSpremi;
    @FXML private Button gumbObrisi;
    @FXML private Label status;
    // endregion

    private final KontaktServis servis;
    private final ObservableList<Kontakt> kontakti = FXCollections.observableArrayList();

    public ImenikController(KontaktServis servis) {     // ovisnost ubrizgana kroz konstruktor
        this.servis = servis;
    }

    // region initialize – povezivanje
    @FXML  // poziva se automatski nakon učitavanja FXML-a
    private void initialize() {
        stupacIme.setCellValueFactory(c -> c.getValue().imeProperty());
        stupacPrezime.setCellValueFactory(c -> c.getValue().prezimeProperty());
        stupacTelefon.setCellValueFactory(c -> c.getValue().telefonProperty());
        stupacEmail.setCellValueFactory(c -> c.getValue().emailProperty());

        FilteredList<Kontakt> filtrirani = new FilteredList<>(kontakti, k -> true);
        poljePretraga.textProperty().addListener(
                (obs, staro, novo) -> filtrirani.setPredicate(k -> k.sadrzi(novo)));
        SortedList<Kontakt> sortirani = new SortedList<>(filtrirani);
        // sortiranje klikom na zaglavlje stupca
        sortirani.comparatorProperty().bind(tablica.comparatorProperty());
        tablica.setItems(sortirani);

        tablica.getSelectionModel().selectedItemProperty()
               .addListener((obs, stari, novi) -> prikazi(novi));
        gumbObrisi.disableProperty().bind(
                tablica.getSelectionModel().selectedItemProperty().isNull());
        BooleanBinding nepotpuno = poljeIme.textProperty().isEmpty()
                .or(poljePrezime.textProperty().isEmpty());
        gumbSpremi.disableProperty().bind(nepotpuno);
        osvjezi();
    }
    // endregion

    @FXML
    private void onNovi() {
        tablica.getSelectionModel().clearSelection();
        prikazi(null);
        poljeIme.requestFocus();
    }

    // region Akcija spremanja
    @FXML
    private void onSpremi() {
        Kontakt odabrani = tablica.getSelectionModel().getSelectedItem();
        Kontakt k = (odabrani != null) ? odabrani
                                       : new Kontakt(0, null, null, null, null);
        k.setIme(poljeIme.getText().strip());
        k.setPrezime(poljePrezime.getText().strip());
        k.setTelefon(poljeTelefon.getText().strip());
        k.setEmail(poljeEmail.getText().strip());
        try {
            servis.spremi(k);
            osvjezi();
            status.setText("Spremljeno: " + k.getIme() + " " + k.getPrezime());
        } catch (KontaktServis.ValidacijaException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        } catch (RuntimeException e) {
            String poruka = "Greška pri spremanju: " + e.getMessage();
            new Alert(Alert.AlertType.ERROR, poruka).showAndWait();
        }
    }
    // endregion

    // region Brisanje s potvrdom
    @FXML
    private void onObrisi() {
        Kontakt k = tablica.getSelectionModel().getSelectedItem();
        Alert potvrda = new Alert(Alert.AlertType.CONFIRMATION,
                "Obrisati kontakt " + k.getIme() + " " + k.getPrezime() + "?");
        potvrda.showAndWait()
               .filter(odgovor -> odgovor == ButtonType.OK)
               .ifPresent(odgovor -> {
                   servis.obrisi(k);
                   osvjezi();
                   status.setText("Obrisano.");
               });
    }
    // endregion

    private void osvjezi() {
        kontakti.setAll(servis.dohvatiSve());
        status.setText("Kontakata: " + kontakti.size());
    }

    private void prikazi(Kontakt k) {
        poljeIme.setText(k == null ? "" : k.getIme());
        poljePrezime.setText(k == null ? "" : k.getPrezime());
        poljeTelefon.setText(k == null ? "" : k.getTelefon());
        poljeEmail.setText(k == null ? "" : k.getEmail());
    }
}
