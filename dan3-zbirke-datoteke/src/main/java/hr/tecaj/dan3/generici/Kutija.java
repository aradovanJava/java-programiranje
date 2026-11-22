package hr.tecaj.dan3.generici;

import java.util.function.Function;

// region Generička klasa
/**
 * Generička klasa: T je parametar tipa koji se zadaje pri korištenju (Kutija&lt;String&gt;).
 */
public class Kutija<T> {

    private T sadrzaj;

    public Kutija(T sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public T get() {
        return sadrzaj;
    }

    public void set(T sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public boolean jePrazna() {
        return sadrzaj == null;
    }

    // generička metoda unutar generičke klase: novi parametar R
    public <R> Kutija<R> mapiraj(Function<T, R> funkcija) {
        return new Kutija<>(funkcija.apply(sadrzaj));
    }
}
// endregion
