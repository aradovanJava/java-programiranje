package hr.tecaj.dan2.vjezba;

/** Tekući račun s dopuštenim prekoračenjem i mjesečnom naknadom. */
public class TekuciRacun extends Racun {

    private static final double MJESECNA_NAKNADA = 2.0;
    private final double dopusteniMinus;

    public TekuciRacun(String broj, String vlasnik, double dopusteniMinus) {
        super(broj, vlasnik);
        this.dopusteniMinus = dopusteniMinus;
    }

    @Override
    public double raspolozivo() {
        return stanje + dopusteniMinus;
    }

    @Override
    public void mjesecnaObrada() {
        stanje -= MJESECNA_NAKNADA;
    }
}
