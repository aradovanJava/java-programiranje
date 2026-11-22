package hr.tecaj.dan1.uvod;

/**
 * Prvi Java program.
 *
 * <p>Pokretanje iz terminala (iz mape dan1-osnove/src/main/java):
 * <pre>
 *   javac hr/tecaj/dan1/uvod/HelloWorld.java
 *   java hr.tecaj.dan1.uvod.HelloWorld Ana
 * </pre>
 */
public class HelloWorld {

    // region Prvi program
    public static void main(String[] args) {
        System.out.println("Pozdrav, svijete!");

        // args su argumenti naredbenog retka
        String ime = args.length > 0 ? args[0] : "polazniče";
        System.out.println("Dobro došli na tečaj Jave, " + ime + "!");

        // Informacije o okruženju u kojem program radi
        System.out.println("Java verzija: " + System.getProperty("java.version"));
        System.out.println("Operacijski sustav: " + System.getProperty("os.name"));
    }
    // endregion
}
