package hr.tecaj.dan5.virtualne;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Virtualne niti se NE stavljaju u bazen. Ako treba ograničiti istovremeni pristup
 * resursu (npr. API dopušta 10 istovremenih poziva), koristi se Semaphore.
 */
public class SemaforDemo {

    // region Ograničavanje semaforom
    private static final Semaphore DOZVOLE = new Semaphore(10);   // najviše 10 istovremeno
    private static final AtomicInteger trenutno = new AtomicInteger();
    private static final AtomicInteger maksimum = new AtomicInteger();

    static String pozoviApi(int zahtjev) throws InterruptedException {
        DOZVOLE.acquire();                    // čekaj slobodnu dozvolu
        try {
            int sada = trenutno.incrementAndGet();
            maksimum.accumulateAndGet(sada, Math::max);
            Thread.sleep(Duration.ofMillis(100));   // poziv vanjskog servisa
            return "odgovor " + zahtjev;
        } finally {
            trenutno.decrementAndGet();
            DOZVOLE.release();                // UVIJEK vrati dozvolu
        }
    }
    // endregion

    public static void main(String[] args) {
        long start = System.nanoTime();
        try (ExecutorService ex = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 200; i++) {
                int zahtjev = i;
                ex.submit(() -> pozoviApi(zahtjev));
            }
        }
        System.out.printf("200 poziva za %d ms, najviše istovremeno: %d%n",
                (System.nanoTime() - start) / 1_000_000, maksimum.get());
    }
}
