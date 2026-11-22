package hr.tecaj.dan5.virtualne;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Usporedba: isti I/O posao s bazenom platformskih niti i s virtualnim nitima.
 */
public class UsporedbaPlatformVirtual {

    static final int ZADATAKA = 2_000;
    static final Duration CEKANJE = Duration.ofMillis(200);   // npr. poziv REST servisa / baze

    // region Mjerenje
    static long izmjeri(ExecutorService executor) {
        long start = System.nanoTime();
        try (executor) {                                       // Java 9+: postojeća varijabla u try
            IntStream.range(0, ZADATAKA).forEach(i -> executor.submit(() -> {
                Thread.sleep(CEKANJE);                         // nit "čeka I/O"
                return i;
            }));
        }
        return (System.nanoTime() - start) / 1_000_000;
    }

    public static void main(String[] args) {
        long platformske = izmjeri(Executors.newFixedThreadPool(100));
        long virtualne = izmjeri(Executors.newVirtualThreadPerTaskExecutor());

        System.out.printf("%d zadataka × %d ms čekanja%n", ZADATAKA, CEKANJE.toMillis());
        System.out.printf("Bazen od 100 platformskih niti: %5d ms%n", platformske); // ≈ 4000 ms
        System.out.printf("Virtualne niti (jedna po zadatku): %5d ms%n", virtualne); // ≈ 200–400 ms
    }
    // endregion
}
