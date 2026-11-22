package hr.tecaj.dan2.sealed;

// region Sealed hijerarhija
/**
 * Zapečaćeno (sealed) sučelje: točno se zna koje ga klase smiju implementirati.
 * Kompajler zato može provjeriti da switch pokriva SVE slučajeve.
 */
public sealed interface Vozilo permits Automobil, Kamion, Motocikl {
}

record Automobil(String registracija, int brojPutnika) implements Vozilo { }

record Kamion(String registracija, double nosivostTona, int brojOsovina) implements Vozilo { }

record Motocikl(String registracija, int kubikaza) implements Vozilo { }
// endregion
