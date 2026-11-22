package hr.tecaj.dan3.vjezba;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Rješenje vježbe Dana 3 – izvještaj koji se sprema kao JSON.
 */
public record IzvjestajProdaje(
        String razdoblje,
        int brojNarudzbi,
        double ukupniPrihod,
        Map<String, Double> prihodPoGradu,
        Map<String, Long> komadaPoKategoriji,
        List<String> top3Kupca,
        Set<String> prodaniArtikli) {
}
