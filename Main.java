import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Basic> kontenListe = new ArrayList<>();

        System.out.println("Möchten Sie die GUI oder die Konsole nutzen?");
        System.out.println("1. GUI starten");
        System.out.println("2. Konsolenanwendung starten");
        int auswahl = scanner.nextInt();

        if (auswahl == 1) {
            javax.swing.SwingUtilities.invokeLater(() -> new KontoGUI());
        } else {
            startKonsolenAnwendung(scanner, kontenListe);
        }
    }

    private static void startKonsolenAnwendung(Scanner scanner, List<Basic> kontenListe) {
        while (true) {
            System.out.println("\n=============================");
            System.out.println("    Bank System Menü");
            System.out.println("=============================");
            System.out.println("Bitte wählen Sie eine Option:");
            System.out.println("1. Konto anlegen");
            System.out.println("2. Einzahlen");
            System.out.println("3. Abheben");
            System.out.println("4. Kontoauszug");
            System.out.println("5. Überweisung");
            System.out.println("6. Beenden");
            System.out.println("=============================");

            int auswahl = scanner.nextInt();
            scanner.nextLine();

            switch (auswahl) {
                case 1:
                    kontoAnlegen(scanner, kontenListe);
                    break;
                case 2:
                    einzahlen(scanner, kontenListe);
                    break;
                case 3:
                    abheben(scanner, kontenListe);
                    break;
                case 4:
                    kontoauszug(kontenListe);
                    break;
                case 5:
                    ueberweisung(scanner, kontenListe);
                    break;
                case 6:
                    System.out.println("\nProgramm wird beendet.");
                    scanner.close();
                    return;
                default:
                    System.out.println("\nUngültige Auswahl! Bitte wählen Sie eine Zahl zwischen 1 und 6.");
            }
        }
    }

    private static void kontoAnlegen(Scanner scanner, List<Basic> kontenListe) {
        System.out.println("\nKonto anlegen - Bitte wählen Sie die Kontoart:");
        System.out.println("1. Girokonto");
        System.out.println("2. Sparkonto");
        System.out.println("3. Kreditkonto");
        System.out.print("Ihre Wahl: ");
        int kontoart = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\nBitte geben Sie folgende Informationen ein:");
        System.out.print("Kontoinhaber: ");
        String kontoinhaber = scanner.nextLine();

        double kontostand;
        while (true) {
            System.out.print("Kontostand: ");
            kontostand = scanner.nextDouble();
            if (kontoart == 3 && kontostand > 0) {
                System.out.println("Fehler: Der Kontostand eines Kreditkontos kann nicht positiv sein.");
            } else {
                break;
            }
        }

        System.out.print("Kontoführungsgebühren: ");
        double kontofuehrungsgebuehren = scanner.nextDouble();

        Basic neuesKonto = null;
        switch (kontoart) {
            case 1:
                System.out.print("Überziehungsrahmen: ");
                double ueberziehungsrahmen = scanner.nextDouble();
                neuesKonto = new Girokonto(kontoinhaber, kontostand, kontofuehrungsgebuehren, ueberziehungsrahmen);
                System.out.println("\nGirokonto wurde erfolgreich angelegt!");
                break;
            case 2:
                neuesKonto = new Sparkonto(kontoinhaber, kontostand, kontofuehrungsgebuehren);
                System.out.println("\nSparkonto wurde erfolgreich angelegt!");
                break;
            case 3:
                System.out.print("Überziehungsrahmen: ");
                ueberziehungsrahmen = scanner.nextDouble();
                neuesKonto = new Kreditkonto(kontoinhaber, kontostand, kontofuehrungsgebuehren, ueberziehungsrahmen);
                System.out.println("\nKreditkonto wurde erfolgreich angelegt!");
                break;
            default:
                System.out.println("\nUngültige Auswahl!");
                return;
        }
        if (neuesKonto != null) {
            kontenListe.add(neuesKonto);
        }
    }

    private static void einzahlen(Scanner scanner, List<Basic> kontenListe) {
        if (kontenListe.isEmpty()) {
            System.out.println("\nKein Konto vorhanden. Bitte zuerst ein Konto anlegen.");
            return;
        }
        Basic konto = kontoWaehlen(scanner, kontenListe);
        if (konto == null) return;

        System.out.print("Betrag zum Einzahlen: ");
        double betrag = scanner.nextDouble();
        konto.einzahlen(betrag);
    }

    private static void abheben(Scanner scanner, List<Basic> kontenListe) {
        if (kontenListe.isEmpty()) {
            System.out.println("\nKein Konto vorhanden. Bitte zuerst ein Konto anlegen.");
            return;
        }
        Basic konto = kontoWaehlen(scanner, kontenListe);
        if (konto == null) return;

        System.out.print("Betrag zum Abheben: ");
        double betrag = scanner.nextDouble();
        konto.abheben(betrag);
    }

    private static void kontoauszug(List<Basic> kontenListe) {
        if (kontenListe.isEmpty()) {
            System.out.println("\nKein Konto vorhanden. Bitte zuerst ein Konto anlegen.");
            return;
        }
        System.out.println("\n=============================");
        System.out.println("Kontoauszug für alle Konten:");
        for (Basic konto : kontenListe) {
            konto.kontoauszug();
            System.out.println("-----------------------------");
        }
    }

    private static void ueberweisung(Scanner scanner, List<Basic> kontenListe) {
        if (kontenListe.size() < 2) {
            System.out.println("\nMindestens zwei Konten erforderlich für eine Überweisung.");
            return;
        }
        System.out.println("\nWählen Sie das Absenderkonto:");
        Basic absender = kontoWaehlen(scanner, kontenListe);
        if (absender == null) return;

        System.out.println("\nWählen Sie das Empfängerkonto:");
        Basic empfaenger = kontoWaehlen(scanner, kontenListe);
        if (empfaenger == null || empfaenger == absender) {
            System.out.println("Ungültige Auswahl! Absender und Empfänger dürfen nicht gleich sein.");
            return;
        }

        System.out.print("Betrag für die Überweisung: ");
        double betrag = scanner.nextDouble();

        if (absender.getKontostand() - betrag >= -absender.ueberziehungsrahmen) {
            absender.abheben(betrag);
            empfaenger.einzahlen(betrag);
            System.out.println("Überweisung erfolgreich: " + betrag + "€ von " + absender.kontoinhaber + " an " + empfaenger.kontoinhaber);
        } else {
            System.out.println("Überweisung fehlgeschlagen: Nicht genügend Guthaben oder Überziehungsrahmen überschritten.");
        }
    }

    private static Basic kontoWaehlen(Scanner scanner, List<Basic> kontenListe) {
        System.out.println("\nVerfügbare Konten:");
        for (int i = 0; i < kontenListe.size(); i++) {
            System.out.println((i + 1) + ". " + kontenListe.get(i).getKontoart() + " - " + kontenListe.get(i).kontoinhaber);
        }
        System.out.print("Bitte wählen Sie ein Konto (Zahl eingeben): ");
        int kontoIndex = scanner.nextInt() - 1;

        if (kontoIndex < 0 || kontoIndex >= kontenListe.size()) {
            System.out.println("Ungültige Auswahl!");
            return null;
        }
        return kontenListe.get(kontoIndex);
    }
}
