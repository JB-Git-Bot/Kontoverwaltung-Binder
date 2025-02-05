class Sparkonto extends Basic {

    public Sparkonto(String kontoinhaber, double kontostand, double kontofuehrungsgebuehren) {
        // Der Konstruktor der Basisklasse wird aufgerufen
        super(kontoinhaber, kontostand, kontofuehrungsgebuehren, "Sparkonto", 0);
    }

    @Override
    public void abheben(double betrag) {
        if (kontostand - betrag >= 0) {
            kontostand -= betrag;
            System.out.println("Es wurden " + betrag + "€ abgehoben.");
        } else {
            System.out.println("Abhebung nicht möglich. Unzureichender Kontostand.");
        }
    }
}
