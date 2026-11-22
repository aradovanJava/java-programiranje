package hr.tecaj.dan2.vjezba;

/** Štedni račun: bez prekoračenja, s mjesečnim pripisom kamate. */
public class StedniRacun extends Racun implements Kamatonosan {

    private final double kamatnaStopa;

    public StedniRacun(String broj, String vlasnik, double kamatnaStopa) {
        super(broj, vlasnik);
        this.kamatnaStopa = kamatnaStopa;
    }

    @Override
    public double raspolozivo() {
        return stanje;                       // nema minusa
    }

    @Override
    public double godisnjaKamatnaStopa() {
        return kamatnaStopa;
    }

    @Override
    public void mjesecnaObrada() {
        stanje += mjesecnaKamata(stanje);    // default metoda iz sučelja
    }
}
