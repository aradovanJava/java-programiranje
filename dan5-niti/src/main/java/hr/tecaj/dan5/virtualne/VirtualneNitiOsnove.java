package hr.tecaj.dan5.virtualne;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Virtualne niti (Java 21+): lagane niti kojima upravlja JVM, a ne operacijski sustav.
 */
public class VirtualneNitiOsnove {

    public static void main(String[] args) throws InterruptedException {
        // region Stvaranje virtualnih niti
        Thread v1 = Thread.ofVirtual().name("virtualna-1")
                .start(() -> System.out.println("Pozdrav iz " + Thread.currentThread()));

        Thread v2 = Thread.startVirtualThread(            // najkraći način
                () -> System.out.println("isVirtual = " + Thread.currentThread().isVirtual()));

        // radnik-0, radnik-1...
        Thread.Builder graditelj = Thread.ofVirtual().name("radnik-", 0);
        Thread v3 = graditelj.start(() -> System.out.println(Thread.currentThread().getName()));

        v1.join();
        v2.join();
        v3.join();
        // endregion

        // region Executor s nitima po zadatku
        AtomicInteger obavljeno = new AtomicInteger();
        long start = System.nanoTime();
        try (ExecutorService ex = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100_000).forEach(i -> ex.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));      // blokirajući I/O (simulacija)
                obavljeno.incrementAndGet();
                return i;
            }));
        }                                                 // čeka da svih 100 000 završi
        long ms = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("100 000 zadataka po 1 s završeno za %d ms%n", ms);
        // endregion
        System.out.println("Obavljeno: " + obavljeno.get());
    }
}
