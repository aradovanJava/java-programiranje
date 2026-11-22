// Java 25: kompaktna izvorna datoteka (JEP 512) – bez deklaracije klase i bez "public static".
// Pokretanje bez prethodnog kompajliranja (source-file mode):
//     java Pozdrav.java
// region Kompaktna datoteka
void main() {
    IO.println("Pozdrav iz kompaktne Java datoteke!");
    String ime = IO.readln("Kako se zoveš? ");
    IO.println("Drago mi je, " + ime + "!");
    IO.println("2 + 3 = " + zbroj(2, 3));
}

int zbroj(int a, int b) {
    return a + b;
}
// endregion
