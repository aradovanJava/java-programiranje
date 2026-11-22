package hr.tecaj.dan2.sucelja;

public class PayPalPlacanje implements Placanje {

    private final String email;

    public PayPalPlacanje(String email) {
        this.email = email;
    }

    @Override
    public boolean plati(double iznos) {
        if (!Placanje.jeIspravanIznos(iznos)) return false;
        System.out.println("PayPal račun " + email + " terećen za " + iznos);
        return true;
    }

    @Override
    public String naziv() {
        return "PayPal";
    }
    // naknada() se ne nadjačava – koristi se default implementacija (0)
}
