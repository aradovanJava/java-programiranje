package hr.tecaj.dan4.fx.uvod;

import javafx.application.Application;

/**
 * "Launcher" klasa koja NE nasljeđuje Application.
 * Omogućuje pokretanje JavaFX-a s classpatha (bez module-info.java) iz IntelliJ-a ili Mavena.
 */
public class HelloFxLauncher {
    // region Launcher
    public static void main(String[] args) {
        Application.launch(HelloFx.class, args);
    }
    // endregion
}
