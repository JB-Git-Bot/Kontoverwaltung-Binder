class Kreditkonto extends Basic {

    public Kreditkonto(String kontoinhaber, double kontostand, double kontofuehrungsgebuehren, double ueberziehungsrahmen) {
        // Der Konstruktor der Basisklasse wird aufgerufen
        super(kontoinhaber, kontostand, kontofuehrungsgebuehren, "Kreditkonto", ueberziehungsrahmen);
    }

    @Override
    public void abheben(double betrag) {
        kontostand -= betrag; // Ein Kreditkonto kann ins Minus gehen
        System.out.println("Es wurden " + betrag + "€ abgehoben.");
    }
}
