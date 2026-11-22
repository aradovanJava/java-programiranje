package hr.tecaj.dan2.kompozicija;

/**
 * Kompozicija ("has-a") i ubrizgavanje ovisnosti kroz konstruktor:
 * servis ne zna koji konkretni Posiljatelj se koristi.
 */
public class ServisNarudzbi {

    // region Kompozicija i DI
    private final Posiljatelj posiljatelj;      // servis IMA pošiljatelja

    public ServisNarudzbi(Posiljatelj posiljatelj) {   // ovisnost dolazi izvana
        this.posiljatelj = posiljatelj;
    }

    public void zaprimi(String kupac, String artikl) {
        // ... poslovna logika: provjera zalihe, spremanje narudžbe ...
        posiljatelj.posalji(kupac, "Zaprimljena narudžba: " + artikl);
    }
    // endregion

    public static void main(String[] args) {
        // region Zamjena ponašanja bez izmjene servisa
        new ServisNarudzbi(new EmailPosiljatelj()).zaprimi("ana@primjer.hr", "Tipkovnica");
        new ServisNarudzbi(new SmsPosiljatelj()).zaprimi("+385911234567", "Miš");

        // lambda kao "lažni" pošiljatelj – idealno za testiranje
        new ServisNarudzbi((kome, poruka) -> System.out.println("TEST: " + poruka))
                .zaprimi("x", "Monitor");
        // endregion
    }
}
