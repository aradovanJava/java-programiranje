package hr.tecaj.dan1.klase;

/**
 * Stvaranje objekata, reference, null i usporedba objekata.
 */
public class StudentDemo {

    public static void main(String[] args) {
        // region Stvaranje objekata
        Student ana = new Student("0036123456", "Ana", "Horvat", 2);
        Student ivo = new Student("0036654321", "Ivo", "Kovač");   // godina = 1

        System.out.println(ana);                 // poziva toString()
        System.out.println(ivo.punoIme());
        ivo.upisiSljedecuGodinu();
        System.out.println(ivo.getGodinaStudija());          // 2
        System.out.println(Student.getBrojStudenata());      // 2 – static
        // endregion

        // region Reference i null
        Student isti = ana;           // NE kopira objekt – kopira referencu
        System.out.println(isti == ana);         // true – ista adresa
        Student ana2 = new Student("0036123456", "Ana", "Horvat", 2);
        System.out.println(ana2 == ana);         // false – različiti objekti

        Student nitko = null;         // referenca koja ne pokazuje ni na što
        // nitko.punoIme();           → NullPointerException
        if (nitko != null) {
            System.out.println(nitko.punoIme());
        }
        // endregion

        // region Validacija u setteru
        try {
            ana.setGodinaStudija(7);
        } catch (IllegalArgumentException e) {
            System.out.println("Greška: " + e.getMessage());
        }
        // endregion
        System.out.println("Ukupno stvoreno studenata: " + Student.getBrojStudenata());
    }
}
