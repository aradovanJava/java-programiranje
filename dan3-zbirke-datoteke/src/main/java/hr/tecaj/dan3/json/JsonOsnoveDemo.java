package hr.tecaj.dan3.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Jackson ObjectMapper: objekt → JSON (serijalizacija) i JSON → objekt (deserijalizacija).
 */
public class JsonOsnoveDemo {

    // region Model za JSON
    public record Adresa(String ulica, String grad, String postanskiBroj) { }

    public record Polaznik(String ime, String email, int bodovi,
                           boolean aktivan, List<String> vjestine, Adresa adresa) { }
    // endregion

    public static void main(String[] args) throws IOException {
        // region Serijalizacija i deserijalizacija
        // jedan mapper, koristi se ponovno (thread-safe)
        ObjectMapper mapper = new ObjectMapper();

        Polaznik ana = new Polaznik("Ana Horvat", "ana@primjer.hr", 87, true,
                List.of("Java", "SQL"), new Adresa("Ilica 1", "Zagreb", "10000"));

        String json = mapper.writeValueAsString(ana);         // objekt → JSON
        System.out.println(json);

        Polaznik procitan = mapper.readValue(json, Polaznik.class); // JSON → objekt
        System.out.println(procitan.adresa().grad());                // Zagreb
        System.out.println(procitan.equals(ana));                    // true
        // endregion

        // region Liste, datoteke i lijepi ispis
        List<Polaznik> grupa = List.of(ana,
                new Polaznik("Ivo Kovač", "ivo@primjer.hr", 64, false, List.of("Python"), null));

        Path datoteka = Path.of("izlaz", "polaznici.json");
        Files.createDirectories(datoteka.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(datoteka.toFile(), grupa);

        // generički tip: TypeReference čuva informaciju List<Polaznik>
        List<Polaznik> ucitani = mapper.readValue(datoteka.toFile(),
                new TypeReference<List<Polaznik>>() { });
        System.out.println("Učitano: " + ucitani.size() + ", drugi: " + ucitani.get(1).ime());
        // endregion

        // region JSON kao Map i obrada grešaka
        Map<String, Object> mapa = mapper.readValue(
                "{\"tecaj\":\"Java\",\"dana\":5,\"online\":false}",
                new TypeReference<Map<String, Object>>() { });
        System.out.println(mapa.get("dana"));                 // 5

        try {
            mapper.readValue("{\"ime\": \"Ana\", \"bodovi\": \"puno\"}", Polaznik.class);
        } catch (JsonProcessingException e) {                 // neispravan JSON ili tip
            System.out.println("Greška: " + e.getOriginalMessage());
        }
        // endregion

        System.out.println(Files.readString(datoteka));
    }
}
