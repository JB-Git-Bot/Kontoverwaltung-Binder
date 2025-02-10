import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Basic> kontenListe = new ArrayList<>();

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
                            break;
                    }
                    if (neuesKonto != null) {
                        kontenListe.add(neuesKonto);
                    }
                    break;

                case 4:
                    if (kontenListe.isEmpty()) {
                        System.out.println("\nKein Konto vorhanden. Bitte zuerst ein Konto anlegen.");
                        break;
                    }
                    System.out.println("\n=============================");
                    System.out.println("Kontoauszug für alle Konten:");
                    for (Basic kontoInListe : kontenListe) {
                        System.out.println("Kontoart: " + kontoInListe.getKontoart());
                        System.out.println("Kontoinhaber: " + kontoInListe.kontoinhaber);
                        System.out.println("Kontonummer: " + kontoInListe.kontonummer);
                        System.out.println("Bankleitzahl: " + kontoInListe.bankleitzahl);
                        System.out.println("Kontostand: " + kontoInListe.getKontostand() + "€");
                        System.out.println("-----------------------------");
                    }
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
}
