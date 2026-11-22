package hr.tecaj.dan2.objekt;

import java.util.Objects;

/**
 * Ispravno nadjačane metode klase Object: equals, hashCode i toString.
 * Ugovor: ako su dva objekta equals, MORAJU imati isti hashCode.
 */
public class Tocka {

    private final int x;
    private final int y;

    public Tocka(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // region equals i hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                       // ista referenca
        if (!(o instanceof Tocka druga)) return false;    // null ili drugi tip
        return x == druga.x && y == druga.y;              // usporedba stanja
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Tocka(" + x + ", " + y + ")";
    }
    // endregion

    public int getX() { return x; }
    public int getY() { return y; }
}
