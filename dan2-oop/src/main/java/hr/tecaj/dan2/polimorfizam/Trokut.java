package hr.tecaj.dan2.polimorfizam;

public class Trokut extends Oblik {
    private final double a, b, c;

    public Trokut(String boja, double a, double b, double c) {
        super(boja);
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Stranice ne čine trokut");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double povrsina() {                 // Heronova formula
        double s = opseg() / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double opseg() {
        return a + b + c;
    }
}
