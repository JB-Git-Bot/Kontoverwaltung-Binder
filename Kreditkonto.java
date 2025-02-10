class Kreditkonto extends Basic {

    public Kreditkonto(String kontoinhaber, double kontostand, double kontofuehrungsgebuehren, double ueberziehungsrahmen) {
        super(kontoinhaber, Math.min(0, kontostand), kontofuehrungsgebuehren, "Kreditkonto", ueberziehungsrahmen);
    }

    @Override
    public void abheben(double betrag) {
        kontostand -= betrag;
        System.out.println("Es wurden " + betrag + "€ abgehoben.");
    }

    @Override
    public void einzahlen(double betrag) {
        if (kontostand + betrag > 0) {
            System.out.println("Das Kreditkonto kann nicht positiv werden. " + (kontostand + betrag - 0) + "€ wurden an den Absender zurücküberwiesen.");
            kontostand = 0;
        } else {
            kontostand += betrag;
        }
        System.out.println("Es wurden " + betrag + "€ eingezahlt. Neuer Kontostand: " + kontostand + "€");
    }

    public void ueberweisungErhalten(double betrag, Basic absender) {
        if (kontostand + betrag > 0) {
            System.out.println("Das Kreditkonto kann nicht positiv werden. " + (kontostand + betrag - 0) + "€ wurden an " + absender.kontoinhaber + " zurücküberwiesen.");
            absender.einzahlen(kontostand + betrag);
            kontostand = 0;
        } else {
            kontostand += betrag;
            System.out.println("Überweisung erhalten: " + betrag + "€. Neuer Kontostand: " + kontostand + "€");
        }
    }
}
