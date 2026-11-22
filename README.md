# Tečaj Java programiranja – demo primjeri

Demo primjeri za **petodnevni tečaj osnova Jave** (5 × 7 nastavnih sati). Svaki dan je zaseban
Maven modul, a svaki primjer s prezentacija nalazi se u ovom repozitoriju.

Isječci koda na slajdovima izvučeni su izravno iz ovih datoteka – označeni su komentarima
`// region Naziv` … `// endregion`, koje IntelliJ IDEA prikazuje kao sklopive blokove.

## Što treba instalirati

| Alat | Verzija | Napomena |
|---|---|---|
| JDK | **25** (LTS) | npr. Eclipse Temurin 25 ili Oracle JDK 25 |
| Maven | 3.9+ | ili Maven ugrađen u IntelliJ |
| IntelliJ IDEA | 2025.2+ | Community je dovoljan |
| Scene Builder | 23+ | opcionalno, za uređivanje FXML-a (Dan 4) |

Vanjske biblioteke (preuzima ih Maven): JavaFX 25.0.1, Jackson 2.18, H2 2.3, SLF4J 2 + Logback 1.5.

## Sadržaj po danima

| Modul | Teme | Vježba / projekt |
|---|---|---|
| `dan1-osnove` | JDK/JVM, prvi program, kompaktne izvorne datoteke (Java 25), tipovi i operatori, kontrola toka, nizovi i Stringovi, metode, **klase i objekti**, enkapsulacija, `static`, paketi i modifikatori, `record`, `enum` | `vjezba` – Knjižnica |
| `dan2-oop` | **Nasljeđivanje**, `super`, fleksibilni konstruktori (Java 25), klasa `Object` (`equals`/`hashCode`), polimorfizam, apstraktne klase, **sučelja** (default/static metode, funkcijska sučelja, lambde), `sealed` hijerarhije i record patterni, kompozicija i DI, ugniježđene klase, **iznimke**, try-with-resources, **logiranje** (SLF4J + Logback, MDC) | `vjezba` – Banka |
| `dan3-zbirke-datoteke` | Generici, **List, Set, Map, Queue, Deque, PriorityQueue**, Comparable/Comparator, lambde i **Stream API**, Collectors, Stream Gatherers, **datoteke** (NIO.2 `Path`/`Files`, CSV, Properties), **JSON** (Jackson, anotacije, `JsonNode`, `java.time`) | `vjezba` – Analiza prodaje (CSV → JSON) |
| `dan4-javafx-baza` | **JavaFX** (Stage/Scene, layouti, kontrole, CSS, properties i binding), **FXML i MVC**, **JDBC** (H2, CRUD, PreparedStatement, SQL injection, transakcije, batch), DAO i servisni sloj | aplikacija `imenik` + `vjezba` – Moji zadaci |
| `dan5-niti` | **Niti** (Thread, Runnable, join, interrupt), race condition, `synchronized`, `volatile`, deadlock i `ReentrantLock`, atomarne klase, **ExecutorService**, `CompletableFuture`, konkurentne zbirke, **virtualne niti**, `Semaphore`, `ScopedValue` (Java 25), **strukturirana konkurentnost** (preview), JavaFX `Task` | `projekt` – Analizator logova (završni projekt) |

## Pokretanje

### IntelliJ IDEA (preporučeno)
1. *File → Open…* i odaberi korijensku mapu repozitorija (`pom.xml`).
2. *Project Structure → SDK*: JDK 25.
3. Otvori bilo koju klasu s `main` metodom i pokreni je zelenom strelicom.

JavaFX aplikacije pokreću se preko klasa `*Launcher` (npr. `ImenikLauncher`) – one ne
nasljeđuju `Application`, pa JavaFX radi i bez `module-info.java`.

### Maven iz naredbenog retka
```bash
# prevođenje svih modula
mvn compile

# konzolni primjer (Dan 1–5)
mvn -q -pl dan1-osnove compile exec:java -Dexec.mainClass=hr.tecaj.dan1.uvod.HelloWorld
mvn -q -pl dan3-zbirke-datoteke compile exec:java -Dexec.mainClass=hr.tecaj.dan3.lambde.StreamDemo

# JavaFX aplikacije (Dan 4 i 5)
mvn -pl dan4-javafx-baza compile javafx:run                       # Imenik (zadano)
mvn -pl dan4-javafx-baza compile javafx:run -Dfx.main=hr.tecaj.dan4.fx.kontrole.KontroleLauncher
mvn -pl dan5-niti compile javafx:run                              # JavaFX Task demo

# kompaktna izvorna datoteka (Java 25) – bez Mavena i bez prevođenja
java dan1-osnove/skripte/Pozdrav.java
```

### Napomene
- **Dan 5 – preview API.** `StrukturiranaKonkurentnostDemo` koristi `StructuredTaskScope`, koji je u
  Javi 25 još *preview* (JEP 505). Modul se zato prevodi s `--enable-preview`; IntelliJ to preuzima iz
  Mavena. Za `exec:java` postavi `MAVEN_OPTS=--enable-preview`.
- **Datoteke koje primjeri stvaraju** (`izlaz/`, `logs/`, H2 baze `*.mv.db` u mapi `podaci/`) nastaju u
  radnom direktoriju i namjerno su u `.gitignore`.
- **Imenik bez baze:** pokreni `ImenikLauncher` s VM opcijom `-Dimenik.dao=memorija` pa ćeš vidjeti kako
  se DAO implementacija mijenja bez ijedne izmjene u kontroleru.

## Struktura paketa

```
hr.tecaj.dan1   uvod · tipovi · kontrola · nizovi · metode · klase · recordenum · vjezba
hr.tecaj.dan2   nasljedivanje · objekt · polimorfizam · sucelja · sealed · kompozicija · iznimke · logiranje · vjezba
hr.tecaj.dan3   generici · model · zbirke · lambde · datoteke · json · vjezba
hr.tecaj.dan4   fx.uvod · fx.layouti · fx.kontrole · fx.binding · jdbc · imenik · vjezba
hr.tecaj.dan5   osnove · sinkronizacija · executori · completable · zbirke · virtualne · fx · projekt
```
