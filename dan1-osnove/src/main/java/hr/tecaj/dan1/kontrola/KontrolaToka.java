package hr.tecaj.dan1.kontrola;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Grananje: if / else if / else, klasični switch, switch izraz i pattern matching.
 */
public class KontrolaToka {

    public static void main(String[] args) {
        System.out.println(ocjena(92));
        System.out.println(ocjena(47));
        System.out.println(opisDana(LocalDate.now().getDayOfWeek()));
        System.out.println(brojDana(2, 2028));
        System.out.println(opisi(42));
        System.out.println(opisi("Java"));
        System.out.println(opisi(3.14));
        klasicniSwitch(2);
    }

    // region if-else
    static String ocjena(int bodovi) {
        if (bodovi < 0 || bodovi > 100) {
            return "Neispravni bodovi";
        } else if (bodovi >= 90) {
            return "Izvrstan (5)";
        } else if (bodovi >= 75) {
            return "Vrlo dobar (4)";
        } else if (bodovi >= 60) {
            return "Dobar (3)";
        } else if (bodovi >= 50) {
            return "Dovoljan (2)";
        } else {
            return "Nedovoljan (1)";
        }
    }
    // endregion

    // region Switch izraz
    static String opisDana(DayOfWeek dan) {
        return switch (dan) {
            case SATURDAY, SUNDAY -> "Vikend – odmor!";
            case FRIDAY -> "Petak – skoro vikend";
            default -> "Radni dan";
        };
    }

    static int brojDana(int mjesec, int godina) {
        return switch (mjesec) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> {
                boolean prijestupna = (godina % 4 == 0 && godina % 100 != 0)
                        || godina % 400 == 0;
                yield prijestupna ? 29 : 28;   // yield vraća vrijednost iz bloka
            }
            default -> throw new IllegalArgumentException("Mjesec: " + mjesec);
        };
    }
    // endregion

    // region Pattern matching
    static String opisi(Object o) {
        return switch (o) {
            case Integer i when i > 10 -> "Veliki cijeli broj " + i;
            case Integer i -> "Cijeli broj " + i;
            case String s -> "Tekst duljine " + s.length();
            case null -> "null vrijednost";
            default -> "Nešto drugo: " + o.getClass().getSimpleName();
        };
    }
    // endregion

    // region Klasični switch
    static void klasicniSwitch(int opcija) {
        switch (opcija) {
            case 1:
                System.out.println("Novi unos");
                break;              // bez break-a izvođenje "propada" dalje!
            case 2:
                System.out.println("Pregled");
                break;
            default:
                System.out.println("Nepoznata opcija");
        }
    }
    // endregion
}
