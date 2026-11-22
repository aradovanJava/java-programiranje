package hr.tecaj.dan1.nizovi;

/**
 * Rad sa Stringovima: nepromjenjivost, metode, usporedba, StringBuilder,
 * formatiranje i text blockovi.
 */
public class Stringovi {

    public static void main(String[] args) {
        // region Metode klase String
        String tecaj = "  Java Programiranje  ";
        String t = tecaj.strip();                       // "Java Programiranje"
        System.out.println(t.length());                 // 18
        System.out.println(t.toUpperCase());            // JAVA PROGRAMIRANJE
        System.out.println(t.charAt(0));                // J
        System.out.println(t.indexOf("Pro"));           // 5
        System.out.println(t.substring(5));             // Programiranje
        System.out.println(t.replace("Java", "Kotlin"));
        System.out.println(t.contains("gram"));         // true
        System.out.println(t.startsWith("Java"));       // true
        String[] dijelovi = "ana,ivo,maja".split(",");  // [ana, ivo, maja]
        System.out.println(String.join(" | ", dijelovi));
        System.out.println("".isEmpty() + " " + "  ".isBlank()); // true true
        // endregion

        // region Usporedba Stringova
        String a = "java";
        String b = "java";                         // isti literal iz "string poola"
        String c = new String("java");             // novi objekt na heapu
        System.out.println(a == b);                // true
        System.out.println(a == c);                // false – različite reference!
        System.out.println(a.equals(c));           // true – isti sadržaj
        System.out.println("JAVA".equalsIgnoreCase(a)); // true
        System.out.println("ana".compareTo("ivo"));     // < 0 (abecedno ispred)
        // endregion

        // region StringBuilder
        // String je nepromjenjiv – svaka "izmjena" stvara novi objekt.
        // Za gradnju teksta u petlji koristi StringBuilder.
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            sb.append(i);
            if (i < 5) sb.append(", ");
        }
        sb.insert(0, "[").append("]");
        System.out.println(sb);                  // [1, 2, 3, 4, 5]
        System.out.println(sb.reverse());        // ]5 ,4 ,3 ,2 ,1[
        // endregion

        // region Formatiranje i text block
        String ime = "Ana";
        double prosjek = 4.6666;
        System.out.println(String.format("%s ima prosjek %.2f", ime, prosjek));
        System.out.println("%-6s|%8.1f|".formatted(ime, prosjek)); // poravnanje

        String json = """
                {
                  "ime": "%s",
                  "prosjek": %.1f
                }
                """.formatted(ime, prosjek);       // text block (Java 15+)
        System.out.print(json);
        // endregion
    }
}
