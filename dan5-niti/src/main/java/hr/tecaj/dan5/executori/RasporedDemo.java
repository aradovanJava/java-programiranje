package hr.tecaj.dan5.executori;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ScheduledExecutorService: odgođeni i periodički zadaci.
 */
public class RasporedDemo {

    public static void main(String[] args) throws InterruptedException {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        // region Periodički zadaci
        try (ScheduledExecutorService raspored = Executors.newScheduledThreadPool(2)) {
            raspored.schedule(() -> System.out.println("Jednom, nakon 500 ms"),
                    500, TimeUnit.MILLISECONDS);

            AtomicInteger otkucaj = new AtomicInteger();
            ScheduledFuture<?> sat = raspored.scheduleAtFixedRate(
                    () -> System.out.println(LocalTime.now().format(f)
                            + " otkucaj " + otkucaj.incrementAndGet()),
                    0, 300, TimeUnit.MILLISECONDS);         // početno kašnjenje, period

            Thread.sleep(1_600);
            sat.cancel(false);                               // zaustavi periodički zadatak
        }
        // endregion
        System.out.println("Raspored ugašen");
    }
}
