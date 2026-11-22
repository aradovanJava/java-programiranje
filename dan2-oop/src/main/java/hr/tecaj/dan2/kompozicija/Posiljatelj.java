package hr.tecaj.dan2.kompozicija;

// region Apstrakcija o kojoj ovisimo
public interface Posiljatelj {
    void posalji(String primatelj, String poruka);
}

class EmailPosiljatelj implements Posiljatelj {
    @Override
    public void posalji(String primatelj, String poruka) {
        System.out.println("[EMAIL → " + primatelj + "] " + poruka);
    }
}

class SmsPosiljatelj implements Posiljatelj {
    @Override
    public void posalji(String primatelj, String poruka) {
        System.out.println("[SMS → " + primatelj + "] " + poruka);
    }
}
// endregion
