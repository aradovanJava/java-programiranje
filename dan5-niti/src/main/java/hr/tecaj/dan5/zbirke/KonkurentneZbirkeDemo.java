package hr.tecaj.dan5.zbirke;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * Zbirke sigurne za rad s više niti i CountDownLatch za koordinaciju.
 */
public class KonkurentneZbirkeDemo {

    public static void main(String[] args) throws InterruptedException {
        List<String> rijeci = List.of("java", "nit", "java", "lock", "nit", "java", "pool");

        // region ConcurrentHashMap i CountDownLatch
        Map<String, Integer> obicna = new HashMap<>();             // NIJE thread-safe
        Map<String, Integer> sigurna = new ConcurrentHashMap<>();  // thread-safe
        int poslova = 200;
        CountDownLatch gotovo = new CountDownLatch(poslova);        // brojač unatrag
        LongAdder greske = new LongAdder();

        try (ExecutorService pool = Executors.newFixedThreadPool(8)) {
            for (int i = 0; i < poslova; i++) {
                pool.submit(() -> {
                    try {
                        for (String r : rijeci) {
                            sigurna.merge(r, 1, Integer::sum);     // atomarno
                            try {
                                obicna.merge(r, 1, Integer::sum);  // utrka – gubi zapise ili puca
                            } catch (RuntimeException e) {
                                greske.increment();
                            }
                        }
                    } finally {
                        gotovo.countDown();                         // jedan posao manje – UVIJEK
                    }
                });
            }
            gotovo.await();                                         // čekaj da brojač dođe do 0
        }
        System.out.println("ConcurrentHashMap: " + new TreeMap<>(sigurna));
        System.out.println("HashMap (možda krivo): " + obicna + ", iznimki: " + greske.sum());
        // endregion

        // region CopyOnWriteArrayList
        // Idealna za "puno čitanja, malo pisanja" – npr. popis slušača događaja
        List<Runnable> slusaci = new CopyOnWriteArrayList<>();
        slusaci.add(() -> System.out.println("Slušač 1 obaviješten"));
        slusaci.add(() -> System.out.println("Slušač 2 obaviješten"));
        for (Runnable s : slusaci) {
            s.run();
            slusaci.add(() -> { });   // izmjena tijekom iteracije – BEZ ConcurrentModificationException
        }
        System.out.println("Broj slušača: " + slusaci.size());
        // endregion
    }
}
