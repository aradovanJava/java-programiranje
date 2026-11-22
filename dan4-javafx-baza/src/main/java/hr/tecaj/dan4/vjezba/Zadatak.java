package hr.tecaj.dan4.vjezba;

/**
 * Rješenje vježbe Dana 4 – model zadatka (nepromjenjiv record).
 */
public record Zadatak(long id, String naslov, Prioritet prioritet, boolean gotov) {

    public enum Prioritet { VISOK, SREDNJI, NIZAK }

    public Zadatak oznaciGotovim(boolean g) {
        return new Zadatak(id, naslov, prioritet, g);    // record je nepromjenjiv → nova kopija
    }
}
