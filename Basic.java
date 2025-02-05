abstract class Basic {
    protected String kontoinhaber;
    protected String bankleitzahl;
    protected String kontonummer;
    protected double kontostand;
    protected double kontofuehrungsgebuehren;
    protected String kontoart;
    protected double ueberziehungsrahmen;

    // Statische Zählervariable für Kontonummer
    private static int kontoNummerZaehler = 1000;

    // Konstruktor
    public Basic(String kontoinhaber, double kontostand, double kontofuehrungsgebuehren, String kontoart, double ueberziehungsrahmen) {
        this.kontoinhaber = kontoinhaber;
        this.bankleitzahl = "12345678";  // Feste Bankleitzahl
        this.kontonummer = String.valueOf(kontoNummerZaehler++);  // Kontonummer wird automatisch vergeben
        this.kontostand = kontostand;
        this.kontofuehrungsgebuehren = kontofuehrungsgebuehren;
        this.kontoart = kontoart;
        this.ueberziehungsrahmen = ueberziehungsrahmen;
    }

    // Einzahlen
    public void einzahlen(double betrag) {
        if (betrag > 0) {
            kontostand += betrag;
            System.out.println("Es wurden " + betrag + "€ eingezahlt.");
        } else {
            System.out.println("Einzahlungsbetrag muss positiv sein.");
        }
    }

    // Abheben
    public abstract void abheben(double betrag);

    // Kontoauszug
    public void kontoauszug() {
        System.out.println("Kontoauszug für " + kontoinhaber);
        System.out.println("Kontonummer: " + kontonummer);
        System.out.println("Bankleitzahl: " + bankleitzahl);
        System.out.println("Kontostand: " + kontostand + "€");
    }

    // Getter für Kontostand
    public double getKontostand() {
        return kontostand;
    }
}
