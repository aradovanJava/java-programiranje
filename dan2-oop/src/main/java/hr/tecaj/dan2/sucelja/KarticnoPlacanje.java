package hr.tecaj.dan2.sucelja;

// region Implementacija više sučelja
public class KarticnoPlacanje implements Placanje, Povratno {   // više sučelja!

    private final String brojKartice;

    public KarticnoPlacanje(String brojKartice) {
        this.brojKartice = brojKartice;
    }

    @Override
    public boolean plati(double iznos) {
        if (!Placanje.jeIspravanIznos(iznos)) return false;
        System.out.println("Terećenje kartice " + maskiraj() + " za " + iznos);
        return true;
    }

    @Override public String naziv() { return "Kartica"; }

    @Override public double naknada(double iznos) { return iznos * 0.015; }

    @Override
    public void vratiNovac(double iznos) {
        System.out.println("Povrat " + iznos + " EUR na karticu " + maskiraj());
    }

    private String maskiraj() {
        return "**** " + brojKartice.substring(brojKartice.length() - 4);
    }
}
// endregion
