public class Kreditkonto extends Basic {
    private double zinssatz;

    public Kreditkonto(int Kontonummer, String Kontoinhaber, int Bankleitzahl,
                       double Kontofuehrungsgebuehren, double Kontostand, double Ueberziehungsrahmen, double zinssatz) {
        super(Kontonummer, "Kreditkonto", Kontoinhaber, Bankleitzahl, Kontofuehrungsgebuehren, Kontostand, Ueberziehungsrahmen);
        this.zinssatz = zinssatz;
    }

    public void einzahlen(double amount) {
        if (Kontostand + amount > 0) {
            System.out.println("Kreditkonto kann nicht positiv sein");
            return;
        }
        this.Kontostand += amount;
    }

    public boolean abheben(double amount) {
        double nk = Kontostand - amount;
        if (nk < -Ueberziehungsrahmen) {
            System.out.printf("Abhebung nicht möglich! Kontostand: %.2f, Versuchter neuer Kontostand: %.2f (Überziehungsrahmen: %.2f)%n",
                    Kontostand, nk, Ueberziehungsrahmen);
            return false;
        }
        Kontostand = nk;
        return true;
    }
}