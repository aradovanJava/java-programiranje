package hr.tecaj.dan1.kontrola;

import java.util.Scanner;

/**
 * Interaktivni unos s tipkovnice pomoću klase Scanner – igra "Pogodi broj".
 */
public class UnosKonzola {

    // region Scanner – pogodi broj
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tajni = (int) (Math.random() * 100) + 1;
        int pokusaji = 0;

        System.out.println("Zamislio sam broj od 1 do 100. Pogodi!");
        while (true) {
            System.out.print("Tvoj broj: ");
            if (!sc.hasNextInt()) {          // zaštita od neispravnog unosa
                System.out.println("Unesi cijeli broj.");
                sc.next();                   // odbaci neispravan token
                continue;
            }
            int broj = sc.nextInt();
            pokusaji++;
            if (broj < tajni) System.out.println("Više!");
            else if (broj > tajni) System.out.println("Manje!");
            else {
                System.out.println("Bravo! Pogodak iz " + pokusaji + ". pokušaja.");
                break;
            }
        }
        sc.close();
    }
    // endregion
}
