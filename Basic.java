public class Basic {
    String Kontoinhaber;
    String Kontoart;

    int Bankleitzahl;
    int Kontonummer;
    double Ueberziehungsrahmen;
    double Kontofuehrungsgebuehren;
    double Kontostand;

    static final int DEFAULT_BLZ = 12345;
    static final double DEFAULT_KONTOFUEHRUNGSGEBUEHREN = 5.0;
    static final double DEFAULT_KONTOSTAND = 0.0;

    public Basic(int Kontonummer, String Kontoart, String Kontoinhaber, int Bankleitzahl,
                 double Kontofuehrungsgebuehren, double Kontostand, double Ueberziehungsrahmen) {
        this.Kontoinhaber = Kontoinhaber;
        this.Bankleitzahl = Bankleitzahl;
        this.Kontonummer = Kontonummer;
        this.Kontofuehrungsgebuehren = Kontofuehrungsgebuehren;
        this.Kontostand = Kontostand;
        this.Kontoart = Kontoart;
        this.Ueberziehungsrahmen = Ueberziehungsrahmen;
    }

    public void kontoauszug() {
        System.out.println("===== Kontoauszug =====");
        System.out.println("Kontoinhaber: " + Kontoinhaber);
        System.out.println("Kontoart: " + Kontoart);
        System.out.println("Kontonummer: " + Kontonummer);
        System.out.println("Bankleitzahl: " + Bankleitzahl);
        System.out.printf("Kontostand: %.2f%n", Kontostand);
        System.out.printf("Überziehungsrahmen: %.2f%n", Ueberziehungsrahmen);
        System.out.printf("Kontoführungsgebühren: %.2f%n", Kontofuehrungsgebuehren);
        System.out.println("=======================");
    }
}