package hr.tecaj.dan5.projekt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * ZAVRŠNI PROJEKT: "Analizator logova" – spaja gradivo svih pet dana:
 * recordi i klase (1), iznimke i logiranje (2), datoteke, zbirke, streamovi i JSON (3),
 * JDBC (4) te virtualne niti i konkurentne strukture (5).
 */
public class ProjektApp {

    private static final Logger log = LoggerFactory.getLogger(ProjektApp.class);

    public static void main(String[] args) throws Exception {
        // region Tijek završnog projekta
        // 1) priprema podataka
        Path mapa = Path.of("izlaz", "logovi");
        List<Path> datoteke = GeneratorLogova.generiraj(mapa, 12, 20_000);
        log.info("Generirano {} log datoteka", datoteke.size());

        // 2) paralelna analiza (virtualne niti)
        long start = System.nanoTime();
        AnalizatorLogova.Statistika stat = new AnalizatorLogova().analiziraj(datoteke);
        long trajanjeMs = (System.nanoTime() - start) / 1_000_000;

        // 3) JSON izvještaj
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(stat);
        Path izvjestaj = Path.of("izlaz", "izvjestaj-logova.json");
        Files.writeString(izvjestaj, json);

        // 4) povijest u bazi
        AnalizaRepozitorij repo = new AnalizaRepozitorij("jdbc:h2:./podaci/analize");
        long id = repo.spremi(stat, trajanjeMs, json);
        // endregion

        System.out.println(json);
        log.info("Analiza #{} gotova za {} ms, izvještaj: {}", id, trajanjeMs, izvjestaj.toAbsolutePath());
        System.out.println("Zadnje analize:");
        repo.zadnje(5).forEach(z -> System.out.printf("  #%d %s  %,d zapisa  5xx=%.2f %%  %d ms%n",
                z.id(), z.vrijeme().withNano(0), z.zapisa(), z.postotak5xx(), z.trajanjeAnalizeMs()));
    }
}
