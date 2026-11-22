package hr.tecaj.dan4.imenik.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * MODEL: domenski objekt s JavaFX svojstvima, pa ga TableView može automatski pratiti.
 */
public class Kontakt {

    // region Model s JavaFX svojstvima
    private final LongProperty id = new SimpleLongProperty(0);   // 0 = još nije u bazi
    private final StringProperty ime = new SimpleStringProperty();
    private final StringProperty prezime = new SimpleStringProperty();
    private final StringProperty telefon = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public Kontakt(long id, String ime, String prezime, String telefon, String email) {
        this.id.set(id);
        this.ime.set(ime);
        this.prezime.set(prezime);
        this.telefon.set(telefon);
        this.email.set(email);
    }

    public StringProperty imeProperty() { return ime; }   // za binding i TableView
    public String getIme() { return ime.get(); }
    public void setIme(String v) { ime.set(v); }
    // endregion

    public long getId() { return id.get(); }
    public void setId(long v) { id.set(v); }
    public LongProperty idProperty() { return id; }

    public StringProperty prezimeProperty() { return prezime; }
    public String getPrezime() { return prezime.get(); }
    public void setPrezime(String v) { prezime.set(v); }

    public StringProperty telefonProperty() { return telefon; }
    public String getTelefon() { return telefon.get(); }
    public void setTelefon(String v) { telefon.set(v); }

    public StringProperty emailProperty() { return email; }
    public String getEmail() { return email.get(); }
    public void setEmail(String v) { email.set(v); }

    /** Za pretragu: sadrži li ime, prezime, telefon ili email zadani tekst? */
    public boolean sadrzi(String tekst) {
        if (tekst == null || tekst.isBlank()) return true;
        String t = tekst.toLowerCase();
        return (getIme() + " " + getPrezime() + " " + getTelefon() + " " + getEmail())
                .toLowerCase().contains(t);
    }

    @Override
    public String toString() {
        return "Kontakt[%d, %s %s, %s, %s]".formatted(getId(), getIme(), getPrezime(), getTelefon(), getEmail());
    }
}
