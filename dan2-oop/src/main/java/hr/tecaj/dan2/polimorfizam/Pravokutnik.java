package hr.tecaj.dan2.polimorfizam;

public class Pravokutnik extends Oblik {
    private final double a;
    private final double b;

    public Pravokutnik(String boja, double a, double b) {
        super(boja);
        this.a = a;
        this.b = b;
    }

    @Override public double povrsina() { return a * b; }
    @Override public double opseg()    { return 2 * (a + b); }
}
