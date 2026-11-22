package hr.tecaj.dan3.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDate;

/**
 * Prilagodba JSON-a anotacijama, stablasti model (JsonNode) i datumi (java.time).
 */
public class JsonAnotacijeDemo {

    // region Anotacije
    @JsonInclude(JsonInclude.Include.NON_NULL)        // ne zapisuj null polja
    @JsonIgnoreProperties(ignoreUnknown = true)        // ignoriraj nepoznata polja u ulazu
    public record Korisnik(
            @JsonProperty("korisnicko_ime") String korisnickoIme,  // drugo ime u JSON-u
            String email,
            @JsonIgnore String lozinka,                // nikad u JSON
            String napomena) { }
    // endregion

    // region Datumi
    public record Termin(String naziv, LocalDate datum) { }
    // endregion

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Korisnik k = new Korisnik("ana.h", "ana@primjer.hr", "tajna123", null);
        System.out.println(mapper.writeValueAsString(k));
        // {"email":"ana@primjer.hr","korisnicko_ime":"ana.h"}

        Korisnik izUlaza = mapper.readValue(
                "{\"korisnicko_ime\":\"ivo\",\"email\":\"ivo@x.hr\",\"uloga\":\"admin\"}",
                Korisnik.class);                         // "uloga" se ignorira
        System.out.println(izUlaza);

        // region Stablasti model – JsonNode
        String odgovor = """
                {"grad": "Zagreb",
                 "trenutno": {"temperatura": 7.5, "opis": "oblačno"},
                 "prognoza": [{"dan": "pon", "max": 9}, {"dan": "uto", "max": 12}]}
                """;
        JsonNode korijen = mapper.readTree(odgovor);       // bez posebne klase
        double temp = korijen.path("trenutno").path("temperatura").asDouble();
        for (JsonNode dan : korijen.get("prognoza")) {
            System.out.println(dan.get("dan").asText() + ": " + dan.get("max").asInt() + " °C");
        }
        String nema = korijen.path("vjetar").path("brzina").asText("n/a"); // path() ne baca NPE

        ObjectNode novi = mapper.createObjectNode();       // gradnja JSON-a ručno
        novi.put("tecaj", "Java").put("trenutnaTemp", temp);
        ArrayNode teme = novi.putArray("teme");
        teme.add("zbirke").add("JSON");
        // endregion
        System.out.println("Temp: " + temp + ", vjetar: " + nema + ", novi: " + novi);

        // region Datumi s JavaTimeModule
        ObjectMapper vremenski = new ObjectMapper()
                .findAndRegisterModules()                  // pronalazi jackson-datatype-jsr310
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String t = vremenski.writeValueAsString(new Termin("Ispit", LocalDate.of(2026, 12, 18)));
        System.out.println(t);                             // {"naziv":"Ispit","datum":"2026-12-18"}
        Termin natrag = vremenski.readValue(t, Termin.class);
        // endregion
        System.out.println(natrag.datum().getDayOfWeek());
    }
}
