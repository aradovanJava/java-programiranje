package hr.tecaj.dan5.virtualne;

/**
 * ScopedValue (Java 25, JEP 506): nepromjenjiv podatak dostupan unutar zadanog opsega
 * izvođenja – sigurnija i jeftinija zamjena za ThreadLocal, idealna uz virtualne niti.
 */
public class ScopedValueDemo {

    // region ScopedValue
    private static final ScopedValue<String> KORISNIK = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {
        Thread a = Thread.startVirtualThread(() ->
                ScopedValue.where(KORISNIK, "ana").run(ScopedValueDemo::obradiZahtjev));
        Thread b = Thread.startVirtualThread(() ->
                ScopedValue.where(KORISNIK, "ivo").run(ScopedValueDemo::obradiZahtjev));
        a.join();
        b.join();
        System.out.println("Izvan opsega, vezan? " + KORISNIK.isBound());   // false
    }

    static void obradiZahtjev() {
        provjeriPrava();                   // ne treba prosljeđivati korisnika kao parametar
        System.out.println("Obrađen zahtjev za: " + KORISNIK.get());
    }

    static void provjeriPrava() {
        System.out.println("Provjera prava za: " + KORISNIK.get());
    }
    // endregion
}
