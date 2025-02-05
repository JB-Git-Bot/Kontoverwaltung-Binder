class Girokonto extends Basic {

    public Girokonto(String kontoinhaber, double kontostand, double kontofuehrungsgebuehren, double ueberziehungsrahmen) {
        // Der Konstruktor der Basisklasse wird aufgerufen
        super(kontoinhaber, kontostand, kontofuehrungsgebuehren, "Girokonto", ueberziehungsrahmen);
    }

    @Override
    public void abheben(double betrag) {
        if (kontostand - betrag >= -ueberziehungsrahmen) {
            kontostand -= betrag;
            System.out.println("Es wurden " + betrag + "€ abgehoben.");
        } else {
            System.out.println("Abhebung nicht möglich. Überziehungsrahmen überschritten.");
        }
    }
}
