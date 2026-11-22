package hr.tecaj.dan2.vjezba;

/** Sposobnost obračuna kamate. */
public interface Kamatonosan {

    double godisnjaKamatnaStopa();

    default double mjesecnaKamata(double iznos) {
        return iznos * godisnjaKamatnaStopa() / 12;
    }
}
