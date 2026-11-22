package hr.tecaj.dan1.tipovi;

/**
 * Aritmetički, relacijski, logički i ternarni operator te klasa Math.
 */
public class Operatori {

    public static void main(String[] args) {
        // region Aritmetički operatori
        int a = 17, b = 5;
        System.out.println(a + b);   // 22
        System.out.println(a - b);   // 12
        System.out.println(a * b);   // 85
        System.out.println(a / b);   // 3  – cjelobrojno
        System.out.println(a % b);   // 2  – ostatak (modulo)

        int i = 10;
        System.out.println(i++);     // 10 – pa tek onda poveća (postfiks)
        System.out.println(++i);     // 12 – prvo poveća (prefiks)
        i += 5;                      // isto kao i = i + 5
        // endregion
        System.out.println("i nakon += 5: " + i);

        // region Relacijski i logički operatori
        int bodovi = 78;
        boolean prolaz = bodovi >= 50;               // relacijski
        boolean vrlo = bodovi >= 75 && bodovi < 90;  // I (kratki spoj)
        boolean ekstremi = bodovi < 10 || bodovi > 95; // ILI
        boolean pao = !prolaz;                       // NE

        // ternarni operator: uvjet ? akoTrue : akoFalse
        String status = prolaz ? "položio" : "nije položio";
        // endregion
        System.out.println("prolaz=" + prolaz + ", vrlo dobar=" + vrlo
                + ", ekstremi=" + ekstremi + ", pao=" + pao + ", status=" + status);

        // region Klasa Math
        System.out.println(Math.max(3, 9));        // 9
        System.out.println(Math.abs(-4.5));        // 4.5
        System.out.println(Math.pow(2, 10));       // 1024.0
        System.out.println(Math.sqrt(144));        // 12.0
        System.out.println(Math.round(2.5));       // 3
        System.out.println(Math.floorMod(-7, 3));  // 2 (uvijek nenegativan)
        int kocka = (int) (Math.random() * 6) + 1; // 1..6
        // endregion
        System.out.println("Bačena kocka: " + kocka);
    }
}
