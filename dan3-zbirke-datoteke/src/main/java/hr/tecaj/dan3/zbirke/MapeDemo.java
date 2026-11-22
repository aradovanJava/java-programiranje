package hr.tecaj.dan3.zbirke;

import hr.tecaj.dan3.model.Zaposlenik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Map: parovi ključ → vrijednost; ključevi su jedinstveni.
 */
public class MapeDemo {

    public static void main(String[] args) {
        // region Osnovne operacije s mapom
        Map<String, Integer> zalihe = new HashMap<>();
        zalihe.put("jabuke", 120);
        zalihe.put("kruške", 45);
        zalihe.put("šljive", 0);
        zalihe.put("jabuke", 100);                    // isti ključ → zamjena vrijednosti

        System.out.println(zalihe.get("kruške"));      // 45
        System.out.println(zalihe.get("banane"));      // null – nema ključa
        System.out.println(zalihe.getOrDefault("banane", 0)); // 0
        System.out.println(zalihe.containsKey("šljive")); // true
        zalihe.putIfAbsent("kruške", 999);             // ne mijenja – ključ postoji
        zalihe.remove("šljive");

        for (Map.Entry<String, Integer> e : zalihe.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }
        // endregion

        // region Brojanje riječi s merge
        String tekst = "java je jezik java je platforma java je zajednica";
        Map<String, Integer> frekvencije = new TreeMap<>();   // sortirani ključevi
        for (String rijec : tekst.split(" ")) {
            frekvencije.merge(rijec, 1, Integer::sum);         // dodaj 1 ili postavi 1
        }
        // {java=3, je=3, jezik=1, platforma=1, zajednica=1}
        System.out.println(frekvencije);
        // endregion

        // region Grupiranje s computeIfAbsent
        Map<String, List<String>> poOdjelima = new LinkedHashMap<>();
        for (Zaposlenik z : Zaposlenik.primjer()) {
            poOdjelima.computeIfAbsent(z.odjel(), k -> new ArrayList<>())
                      .add(z.ime());
        }
        poOdjelima.forEach((odjel, imena) -> System.out.println(odjel + ": " + imena));
        // endregion

        // region Nepromjenjiva mapa i TreeMap
        Map<String, String> glavniGradovi = Map.of(
                "HR", "Zagreb", "SI", "Ljubljana", "AT", "Beč");
        TreeMap<String, String> sortirano = new TreeMap<>(glavniGradovi);
        // AT SI=Ljubljana
        System.out.println(sortirano.firstKey() + " " + sortirano.lastEntry());
        // endregion
    }
}
