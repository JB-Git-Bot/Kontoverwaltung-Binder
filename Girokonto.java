public class Girokonto extends Basic {
    public Girokonto(int Kontonummer, String Kontoinhaber, int Bankleitzahl, double Kontofuehrungsgebuehren, double Kontostand, double Ueberziehungsrahmen) {
        super(Kontonummer, "Girokonto", Kontoinhaber, Bankleitzahl, Kontofuehrungsgebuehren, Kontostand, Ueberziehungsrahmen);
    }

    public boolean einzahlen(double amount) {
        if (amount < 0) {
            System.out.println("Einzahlung muss positiv sein.");
            return false;
        }
        this.Kontostand += amount;
        return true;
    }

    public boolean abheben(double amount) {
        double nk = Kontostand - amount;
        if (nk < -Ueberziehungsrahmen) {
            System.out.printf("Abhebung nicht möglich! Kontostand: %.2f, Versuchter neuer Kontostand: %.2f (Überziehungsrahmen: %.2f)%n", Kontostand, nk, Ueberziehungsrahmen);
            return false;
        }
        Kontostand = nk;
        return true;
    }
}