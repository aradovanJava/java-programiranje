package hr.tecaj.dan5.projekt;

import java.time.LocalDateTime;

/**
 * Završni projekt – jedan redak pristupnog loga web poslužitelja.
 * Format: 2026-11-20T10:15:30 192.168.1.20 GET /api/proizvodi 200 45
 */
public record LogZapis(LocalDateTime vrijeme, String ip, String metoda,
                       String putanja, int status, long trajanjeMs) {

    public static LogZapis parsiraj(String redak) {
        String[] d = redak.trim().split("\\s+");
        if (d.length != 6) {
            throw new IllegalArgumentException("Neispravan redak loga: " + redak);
        }
        return new LogZapis(LocalDateTime.parse(d[0]), d[1], d[2], d[3],
                Integer.parseInt(d[4]), Long.parseLong(d[5]));
    }

    public boolean jeGreskaPosluzitelja() {
        return status >= 500;
    }
}
