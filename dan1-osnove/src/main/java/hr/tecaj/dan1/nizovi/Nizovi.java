package hr.tecaj.dan1.nizovi;

import java.util.Arrays;

/**
 * Jednodimenzionalni i dvodimenzionalni nizovi te pomoćna klasa Arrays.
 */
public class Nizovi {

    public static void main(String[] args) {
        // region Deklaracija i pristup
        int[] ocjene = {5, 3, 4, 5, 2};         // inicijalizacija s vrijednostima
        double[] temperature = new double[7];  // 7 elemenata, sve 0.0
        String[] imena = new String[3];        // sve null

        temperature[0] = 12.5;                 // indeksi kreću od 0
        System.out.println(ocjene.length);     // 5
        System.out.println(ocjene[ocjene.length - 1]); // zadnji: 2

        int zbroj = 0;
        for (int o : ocjene) zbroj += o;
        System.out.println("Prosjek: " + (double) zbroj / ocjene.length);
        // ocjene[5] = 1; → ArrayIndexOutOfBoundsException
        // endregion
        System.out.println("temperature=" + Arrays.toString(temperature) + ", imena=" + Arrays.toString(imena));

        // region Klasa Arrays
        int[] brojevi = {42, 7, 19, 3, 25};
        System.out.println(Arrays.toString(brojevi)); // [42, 7, 19, 3, 25]

        Arrays.sort(brojevi);                          // [3, 7, 19, 25, 42]
        int idx = Arrays.binarySearch(brojevi, 19);    // 2 (niz mora biti sortiran)

        int[] kopija = Arrays.copyOf(brojevi, 7);      // [3, 7, 19, 25, 42, 0, 0]
        int[] dio = Arrays.copyOfRange(brojevi, 1, 3); // [7, 19]
        Arrays.fill(kopija, 5, 7, -1);                 // zadnja dva = -1
        boolean isti = Arrays.equals(brojevi, dio);    // false
        // endregion
        System.out.println("sortirano=" + Arrays.toString(brojevi) + ", idx(19)=" + idx
                + ", kopija=" + Arrays.toString(kopija) + ", dio=" + Arrays.toString(dio) + ", isti=" + isti);

        // region Dvodimenzionalni niz
        int[][] matrica = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int trag = 0;
        for (int r = 0; r < matrica.length; r++) {
            trag += matrica[r][r];                    // dijagonala
        }
        System.out.println("Trag matrice: " + trag);  // 15
        System.out.println(Arrays.deepToString(matrica));

        int[][] nazubljeni = new int[3][];            // "jagged" niz
        nazubljeni[0] = new int[]{1};
        nazubljeni[1] = new int[]{1, 2};
        nazubljeni[2] = new int[]{1, 2, 3};
        // endregion
        System.out.println(Arrays.deepToString(nazubljeni));
    }
}
