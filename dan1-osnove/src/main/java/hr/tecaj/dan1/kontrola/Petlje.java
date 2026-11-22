package hr.tecaj.dan1.kontrola;

/**
 * Petlje: for, while, do-while, for-each te break, continue i oznake.
 */
public class Petlje {

    public static void main(String[] args) {
        // region for i while
        // for – kad unaprijed znamo broj ponavljanja
        for (int i = 1; i <= 5; i++) {
            System.out.print(i * i + " ");      // 1 4 9 16 25
        }
        System.out.println();

        // while – dok je uvjet ispunjen (može se izvesti 0 puta)
        int stanje = 1000;
        int godine = 0;
        while (stanje < 2000) {
            stanje *= 1.07;                       // 7 % kamate godišnje
            godine++;
        }
        System.out.println("Udvostručenje za " + godine + " godina");

        // do-while – tijelo se izvede barem jednom
        int pokusaj = 0;
        do {
            pokusaj++;
        } while (pokusaj < 3);
        // endregion
        System.out.println("Broj pokušaja: " + pokusaj);

        // region for-each, break i continue
        String[] tecajevi = {"Java", "Python", "C#", "Kotlin", "Go"};
        for (String t : tecajevi) {               // for-each petlja
            if (t.equals("C#")) {
                continue;                         // preskoči ovu iteraciju
            }
            if (t.equals("Go")) {
                break;                            // prekini petlju
            }
            System.out.println("Tečaj: " + t);
        }

        // oznaka (label) za izlazak iz vanjske petlje
        vanjska:
        for (int r = 1; r <= 3; r++) {
            for (int s = 1; s <= 3; s++) {
                if (r * s == 4) break vanjska;
                System.out.print(r + "x" + s + " ");
            }
        }
        // endregion
        System.out.println();

        tablicaMnozenja(5);
    }

    // region Ugniježđene petlje
    static void tablicaMnozenja(int n) {
        for (int red = 1; red <= n; red++) {
            for (int stup = 1; stup <= n; stup++) {
                System.out.printf("%4d", red * stup);
            }
            System.out.println();
        }
    }
    // endregion
}
