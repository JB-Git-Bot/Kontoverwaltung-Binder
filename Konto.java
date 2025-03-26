public class Konto {
    private String kontoinhaber;
    private String kontonummer;
    private double kontostand;
    private double gebuehren;
    private String kontoart;
    private double zinssatz;
    private double ueberziehungsrahmen;

    public Konto(String kontoinhaber, String kontonummer, double kontostand, double gebuehren,
                 String kontoart, double zinssatz, double ueberziehungsrahmen) {
        this.kontoinhaber = kontoinhaber;
        this.kontonummer = kontonummer;
        this.kontoart = kontoart;
        this.zinssatz = zinssatz;
        this.ueberziehungsrahmen = ueberziehungsrahmen;
        this.gebuehren = gebuehren;

        // Beim Erstellen schon prüfen, falls es ein Kreditkonto ist und man
        // (versehentlich) einen positiven Kontostand angegeben hat.
        if ("Kreditkonto".equals(kontoart) && kontostand > 0) {
            this.kontostand = -Math.abs(kontostand);
        } else {
            this.kontostand = kontostand;
        }
    }

    public String getKontoinhaber() {
        return kontoinhaber;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public double getKontostand() {
        return kontostand;
    }

    /**
     * Wenn es sich um ein Kreditkonto handelt und ein positiver Wert gesetzt werden soll,
     * wird dieser automatisch in einen negativen umgewandelt.
     */
    public void setKontostand(double neuerKontostand) {
        // Falls das Konto ein Kreditkonto ist und der neue Kontostand > 0,
        // zwingen wir ihn ins Negative.
        if ("Kreditkonto".equals(kontoart) && neuerKontostand > 0) {
            this.kontostand = -Math.abs(neuerKontostand);
        } else {
            this.kontostand = neuerKontostand;
        }
    }

    public double getGebuehren() {
        return gebuehren;
    }

    public String getKontoart() {
        return kontoart;
    }

    public double getZinssatz() {
        return zinssatz;
    }

    public double getUeberziehungsrahmen() {
        return ueberziehungsrahmen;
    }

    @Override
    public String toString() {
        return kontonummer + " (" + kontoinhaber + ")";
    }
}
