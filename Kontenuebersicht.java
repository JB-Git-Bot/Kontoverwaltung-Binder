import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kontenuebersicht{
    private JTextField kontonummerField;
    private JTextField kontoinhaberField;
    private JTextField gebuehrenZinsenField;
    private JTextField kontostandField;
    private JButton closeButton;
    private JTextPane kontoauszuege;
    private JComboBox kontoListe;
    private JLabel kontonummerLabel;
    private JLabel kontonummern;
    private JLabel gebührenZinsenLabel;
    private JLabel kontoinhaberLabel;
    private JLabel kontostandLabel;
    private JPanel Kontenuebersicht;
    private JButton kontoErstellenButton;
    private JTextField kontoartField;
    private JLabel kontoart;


    private static List<Basic> kontenListe = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    // Statische Variable, um Kontonummern automatisch hochzuzählen
    private static int nextKontonummer = 1000;



    public Kontenuebersicht() {
        kontoErstellenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        kontoErstellenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KontoErstellung ke = new KontoErstellung(kontenListe, kontoListe);
            }
        });


        kontoListe.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getItem().toString());
                String kontoNummer = e.getItem().toString().split(" ")[0];
                int ktNr = Integer.parseInt(kontoNummer);

                for (Basic konto : kontenListe) {
                    // If using a public field
                    if (konto.Kontonummer == ktNr) {
                        System.out.println("Found konto with Kontonummer 101: " + konto);
                        kontonummerField.setText(String.valueOf(konto.Kontonummer));
                        kontoinhaberField.setText(konto.Kontoinhaber);
                        gebuehrenZinsenField.setText(String.valueOf(konto.Kontofuehrungsgebuehren));
                        kontostandField.setText(String.valueOf(konto.Kontostand));
                        kontoartField.setText(String.valueOf(konto.Kontoart));
                        break; // exit loop if found
                    }

                    // Alternatively, if using a getter method:
                    // if (konto.getKontonummer() == 101) { ... }
                }
            }
        });
    }






    public static void main(String[] args) {
        JFrame frame = new JFrame("Kontoverwaltung");
        // Assumes that the panel and all Swing components have been created and linked.
        frame.setContentPane(new Kontenuebersicht().Kontenuebersicht);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        boolean running = true;
        while (running) {
            System.out.println("\nWelche Aktion möchten Sie durchführen?");
            System.out.println("1 - Konto anlegen");
            System.out.println("2 - Einzahlen");
            System.out.println("3 - Abheben");
            System.out.println("4 - Kontoauszug");
            System.out.println("5 - Konto auflösen");
            System.out.println("6 - Überweisen");
            System.out.println("7 - Programm beenden");
            String auswahl = scanner.nextLine();
            switch (auswahl) {
                case "1":
                    kontoAnlegen();
                    break;
                case "2":
                    einzahlen();
                    break;
                case "3":
                    abheben();
                    break;
                case "4":
                    kontoauszug();
                    break;
                case "5":
                    kontoAufloesen();
                    break;
                case "6":
                    ueberweisen();
                    break;
                case "7":
                    running = false;
                    System.out.println("Programm wird beendet.");
                    break;
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
        scanner.close();
    }

    private static void kontoAnlegen() {
        System.out.println("\n--- Konto anlegen ---");

        System.out.print("Geben Sie den Kontoinhaber ein: ");
        String kontoinhaber = scanner.nextLine();

        // Hier wird die Kontonummer automatisch generiert, anstatt sie einzulesen
        int ktnr = nextKontonummer++;
        System.out.printf("Automatisch generierte Kontonummer: %d%n", ktnr);

        System.out.println("Wählen Sie den Kontotyp:");
        System.out.println("1 - Girokonto");
        System.out.println("2 - Sparkonto");
        System.out.println("3 - Kreditkonto");
        String typAuswahl = scanner.nextLine();

        Basic neuesKonto = null;
        switch (typAuswahl) {
            case "1":
                System.out.print("Geben Sie den Überziehungsrahmen für das Girokonto ein: ");
                double ueberziehung = readDouble();
                neuesKonto = new Girokonto(
                        ktnr,
                        kontoinhaber,
                        Basic.DEFAULT_BLZ,
                        Basic.DEFAULT_KONTOFUEHRUNGSGEBUEHREN,
                        Basic.DEFAULT_KONTOSTAND,
                        ueberziehung
                );
                break;
            case "2":
                neuesKonto = new Sparkonto(
                        ktnr,
                        kontoinhaber,
                        Basic.DEFAULT_BLZ,
                        Basic.DEFAULT_KONTOFUEHRUNGSGEBUEHREN,
                        Basic.DEFAULT_KONTOSTAND
                );
                break;
            case "3":
                System.out.print("Geben Sie einen Überziehungsrahmen ein: ");
                double kredRahmen = readDouble();
                System.out.print("Geben Sie den Zinssatz an (z.B. 3.5): ");
                double zinssatz = readDouble();
                neuesKonto = new Kreditkonto(
                        ktnr,
                        kontoinhaber,
                        Basic.DEFAULT_BLZ,
                        Basic.DEFAULT_KONTOFUEHRUNGSGEBUEHREN,
                        Basic.DEFAULT_KONTOSTAND,
                        kredRahmen,
                        zinssatz
                );
                break;
            default:
                System.out.println("Ungültiger Kontotyp. Konto wird nicht angelegt.");
                return;
        }
        if (neuesKonto != null) {
            kontenListe.add(neuesKonto);
            System.out.println("Konto wurde erfolgreich angelegt und der Liste hinzugefügt.");
        }
    }

    private static void einzahlen() {
        if (kontenListe.isEmpty()) {
            System.out.println("Es sind keine Konten vorhanden.");
            return;
        }
        Basic k = kontoAuswaehlen();
        if (k == null) return;
        System.out.print("Geben Sie den Einzahlungsbetrag ein: ");
        double betrag = readDouble();
        boolean erfolgreich = false;
        if (k instanceof Girokonto) {
            erfolgreich = ((Girokonto) k).einzahlen(betrag);
        } else if (k instanceof Sparkonto) {
            erfolgreich = ((Sparkonto) k).einzahlen(betrag);
        } else if (k instanceof Kreditkonto) {
            ((Kreditkonto) k).einzahlen(betrag);
            erfolgreich = true;
        } else {
            System.out.println("Unbekannter Kontotyp. Einzahlung abgebrochen.");
        }
        if (erfolgreich) {
            System.out.println("Einzahlung war erfolgreich.");
        }
    }

    private static void abheben() {
        if (kontenListe.isEmpty()) {
            System.out.println("Es sind keine Konten vorhanden.");
            return;
        }
        Basic k = kontoAuswaehlen();
        if (k == null) return;
        System.out.print("Geben Sie den Abhebungsbetrag ein: ");
        double betrag = readDouble();
        boolean erfolgreich = false;
        if (k instanceof Girokonto) {
            erfolgreich = ((Girokonto) k).abheben(betrag);
        } else if (k instanceof Sparkonto) {
            erfolgreich = ((Sparkonto) k).auszahlen(betrag);
        } else if (k instanceof Kreditkonto) {
            erfolgreich = ((Kreditkonto) k).abheben(betrag);
        } else {
            System.out.println("Unbekannter Kontotyp. Abhebung abgebrochen.");
        }
        if (erfolgreich) {
            System.out.println("Abhebung war erfolgreich.");
        }
    }

    private static void kontoauszug() {
        if (kontenListe.isEmpty()) {
            System.out.println("Es sind keine Konten vorhanden.");
            return;
        }
        Basic k = kontoAuswaehlen();
        if (k == null) return;
        k.kontoauszug();
    }

    private static void kontoAufloesen() {
        if (kontenListe.isEmpty()) {
            System.out.println("Es sind keine Konten vorhanden.");
            return;
        }
        Basic k = kontoAuswaehlen();
        if (k == null) return;
        System.out.println("Wollen Sie Ihr Konto wirklich auflösen? (y/n)");
        String bestaetigung = scanner.nextLine();
        if (bestaetigung.equalsIgnoreCase("y")) {
            kontenListe.remove(k);
            System.out.println("Konto wurde aufgelöst und aus der Liste entfernt.");
        } else {
            System.out.println("Vorgang abgebrochen. Konto wurde nicht aufgelöst.");
        }
    }

    private static void ueberweisen() {
        if (kontenListe.size() < 2) {
            System.out.println("Für eine Überweisung benötigen Sie mindestens zwei Konten.");
            return;
        }
        System.out.println("Von welchem Konto möchten Sie überweisen?");
        Basic quellKonto = kontoAuswaehlen();
        if (quellKonto == null) return;
        System.out.println("Auf welches Konto möchten Sie überweisen?");
        Basic zielKonto = kontoAuswaehlen();
        if (zielKonto == null) return;
        if (quellKonto == zielKonto) {
            System.out.println("Quell- und Zielkonto sind identisch. Überweisung abgebrochen.");
            return;
        }
        System.out.print("Geben Sie den Überweisungsbetrag ein: ");
        double betrag = readDouble();
        boolean erfolgreichAbhebung = false;
        if (quellKonto instanceof Girokonto) {
            erfolgreichAbhebung = ((Girokonto) quellKonto).abheben(betrag);
        } else if (quellKonto instanceof Sparkonto) {
            erfolgreichAbhebung = ((Sparkonto) quellKonto).auszahlen(betrag);
        } else if (quellKonto instanceof Kreditkonto) {
            erfolgreichAbhebung = ((Kreditkonto) quellKonto).abheben(betrag);
        }
        if (!erfolgreichAbhebung) {
            System.out.println("Überweisung fehlgeschlagen (Abhebung nicht möglich).");
            return;
        }
        if (zielKonto instanceof Girokonto) {
            ((Girokonto) zielKonto).einzahlen(betrag);
        } else if (zielKonto instanceof Sparkonto) {
            ((Sparkonto) zielKonto).einzahlen(betrag);
        } else if (zielKonto instanceof Kreditkonto) {
            ((Kreditkonto) zielKonto).einzahlen(betrag);
        }
        System.out.println("Überweisung erfolgreich abgeschlossen!");
    }

    private static Basic kontoAuswaehlen() {
        for (int i = 0; i < kontenListe.size(); i++) {
            Basic b = kontenListe.get(i);
            System.out.printf("%d - %s (%s), Kontonr: %d%n", i, b.Kontoinhaber, b.Kontoart, b.Kontonummer);
        }
        System.out.print("Bitte Index des gewünschten Kontos eingeben (oder -1 zum Abbrechen): ");
        int index = readInt();
        if (index < 0 || index >= kontenListe.size()) {
            System.out.println("Abbruch oder ungültige Eingabe.");
            return null;
        }
        return kontenListe.get(index);
    }

    private static int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe, bitte eine Ganzzahl eingeben:");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe, bitte eine Kommazahl eingeben:");
            }
        }
    }
}