public class Sparkonto extends Basic {

    public Sparkonto(int Kontonummer, String Kontoinhaber, int Bankleitzahl, double Kontofuehrungsgebuehren, double Kontostand) {
        super(Kontonummer, "Sparkonto", Kontoinhaber, Bankleitzahl, Kontofuehrungsgebuehren, Kontostand, 0);
    }

    public boolean einzahlen(double amount) {
        if (amount < 0) {
            System.out.println("Einzahlung muss positiv sein.");
            return false;
        }
        this.Kontostand += amount;
        return true;
    }
    public boolean auszahlen(double amount) {
        double nk = this.Kontostand - amount;
        if (nk < 0) {
            System.out.printf("Abhebung nicht möglich! Kontostand: %.2f, Versuchter neuer Kontostand: %.2f (Überziehungsrahmen: %.2f)%n", Kontostand, nk, Ueberziehungsrahmen);
            return false;
        }
        Kontostand = nk;
        return true;
    }
}