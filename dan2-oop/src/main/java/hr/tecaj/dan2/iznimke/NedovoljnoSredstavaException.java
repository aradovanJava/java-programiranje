package hr.tecaj.dan2.iznimke;

/**
 * Provjerena (checked) iznimka: poslovna situacija koju pozivatelj MORA obraditi.
 */
// region Vlastita checked iznimka
public class NedovoljnoSredstavaException extends Exception {

    private final double trazeno;
    private final double raspolozivo;

    public NedovoljnoSredstavaException(double trazeno, double raspolozivo) {
        super("Traženo %.2f EUR, raspoloživo %.2f EUR".formatted(trazeno, raspolozivo));
        this.trazeno = trazeno;
        this.raspolozivo = raspolozivo;
    }

    public double getNedostaje() {
        return trazeno - raspolozivo;
    }
}
// endregion
