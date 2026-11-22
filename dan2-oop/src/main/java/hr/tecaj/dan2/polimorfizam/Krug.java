package hr.tecaj.dan2.polimorfizam;

// region Konkretne podklase
public class Krug extends Oblik {
    private final double r;

    public Krug(String boja, double r) {
        super(boja);
        this.r = r;
    }

    @Override public double povrsina() { return Math.PI * r * r; }
    @Override public double opseg()    { return 2 * Math.PI * r; }
}
// endregion
