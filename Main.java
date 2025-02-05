import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Basic> kontenListe = new ArrayList<>();  // Eine Liste für alle Konten
        Basic aktuellesKonto = null;

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

            switch (auswahl) {
                case 1:
                    System.out.println("\nKonto anlegen - Bitte wählen Sie die Kontoart:");
                    System.out.println("1. Girokonto");
                    System.out.println("2. Sparkonto");
                    System.out.println("3. Kreditkonto");
                    System.out.print("Ihre Wahl: ");
                    int kontoart = scanner.nextInt();
                    scanner.nextLine();  // Den Rest der Zeile einlesen

                    System.out.println("\nBitte geben Sie folgende Informationen ein:");
                    System.out.print("Kontoinhaber: ");
                    String kontoinhaber = scanner.nextLine();
                    System.out.print("Kontostand: ");
                    double kontostand = scanner.nextDouble();
                    System.out.print("Kontoführungsgebühren: ");
                    double kontofuehrungsgebuehren = scanner.nextDouble();

                    Basic neuesKonto = null;
                    switch (kontoart) {
                        case 1:  // Girokonto
                            System.out.print("Überziehungsrahmen (nur für Girokonto und Kreditkonto): ");
                            double ueberziehungsrahmen = scanner.nextDouble();
                            neuesKonto = new Girokonto(kontoinhaber, kontostand, kontofuehrungsgebuehren, ueberziehungsrahmen);
                            System.out.println("\nGirokonto wurde erfolgreich angelegt!");
                            break;
                        case 2:  // Sparkonto
                            neuesKonto = new Sparkonto(kontoinhaber, kontostand, kontofuehrungsgebuehren);
                            System.out.println("\nSparkonto wurde erfolgreich angelegt!");
                            break;
                        case 3:  // Kreditkonto
                            System.out.print("Überziehungsrahmen (nur für Girokonto und Kreditkonto): ");
                            ueberziehungsrahmen = scanner.nextDouble();
                            neuesKonto = new Kreditkonto(kontoinhaber, kontostand, kontofuehrungsgebuehren, ueberziehungsrahmen);
                            System.out.println("\nKreditkonto wurde erfolgreich angelegt!");
                            break;
                        default:
                            System.out.println("\nUngültige Auswahl! Bitte wählen Sie eine gültige Kontoart.");
                            break;
                    }
                    if (neuesKonto != null) {
                        kontenListe.add(neuesKonto);  // Konto zur Liste hinzufügen
                        aktuellesKonto = neuesKonto;  // Das zuletzt angelegte Konto als aktives Konto setzen
                    }
                    break;

                case 2:  // Einzahlen
                    if (aktuellesKonto == null) {
                        System.out.println("\nKein Konto vorhanden. Bitte zuerst ein Konto anlegen.");
                        break;
                    }
                    System.out.print("\nBetrag zum Einzahlen: ");
                    double einzahlenBetrag = scanner.nextDouble();
                    aktuellesKonto.einzahlen(einzahlenBetrag);
                    break;

                case 3:  // Abheben
                    if (aktuellesKonto == null) {
                        System.out.println("\nKein Konto vorhanden. Bitte zuerst ein Konto anlegen.");
                        break;
                    }
                    System.out.print("\nBetrag zum Abheben: ");
                    double abhebenBetrag = scanner.nextDouble();
                    aktuellesKonto.abheben(abhebenBetrag);
                    break;

                case 4:  // Kontoauszug
                    if (kontenListe.isEmpty()) {
                        System.out.println("\nKein Konto vorhanden. Bitte zuerst ein Konto anlegen.");
                        break;
                    }
                    System.out.println("\n=============================");
                    System.out.println("Kontoauszug für alle Konten:");
                    for (Basic kontoInListe : kontenListe) {
                        System.out.println("Kontoinhaber: " + kontoInListe.kontoinhaber);
                        System.out.println("Kontonummer: " + kontoInListe.kontonummer);
                        System.out.println("Bankleitzahl: " + kontoInListe.bankleitzahl);
                        System.out.println("Kontostand: " + kontoInListe.getKontostand() + "€");
                        System.out.println("-----------------------------");
                    }
                    break;

                case 5:  // Überweisung
                    if (kontenListe.size() < 2) {
                        System.out.println("\nFür eine Überweisung müssen mindestens zwei Konten existieren.");
                        break;
                    }
                    System.out.println("\nÜberweisung - Wählen Sie das Konto, von dem Sie überweisen möchten:");

                    // Zeigt alle verfügbaren Konten an
                    for (int i = 0; i < kontenListe.size(); i++) {
                        System.out.println((i + 1) + ". " + kontenListe.get(i).kontoinhaber);
                    }

                    System.out.print("\nIhre Wahl (von welchem Konto): ");
                    int vonKontoIndex = scanner.nextInt() - 1;

                    if (vonKontoIndex < 0 || vonKontoIndex >= kontenListe.size()) {
                        System.out.println("\nUngültige Auswahl.");
                        break;
                    }

                    System.out.print("Betrag zur Überweisung: ");
                    double ueberweisungsBetrag = scanner.nextDouble();

                    Basic vonKonto = kontenListe.get(vonKontoIndex);
                    System.out.println("\nWählen Sie das Konto, auf das Sie überweisen möchten:");

                    // Zeigt erneut alle verfügbaren Konten an, außer das ausgewählte Konto
                    for (int i = 0; i < kontenListe.size(); i++) {
                        if (i != vonKontoIndex) {
                            System.out.println((i + 1) + ". " + kontenListe.get(i).kontoinhaber);
                        }
                    }

                    System.out.print("\nIhre Wahl (auf welches Konto): ");
                    int aufKontoIndex = scanner.nextInt() - 1;

                    if (aufKontoIndex < 0 || aufKontoIndex >= kontenListe.size() || aufKontoIndex == vonKontoIndex) {
                        System.out.println("\nUngültige Auswahl.");
                        break;
                    }

                    Basic aufKonto = kontenListe.get(aufKontoIndex);
                    vonKonto.abheben(ueberweisungsBetrag);
                    aufKonto.einzahlen(ueberweisungsBetrag);

                    System.out.println("\nÜberweisung von " + vonKonto.kontoinhaber + " zu " + aufKonto.kontoinhaber + " war erfolgreich.");
                    break;

                case 6:  // Beenden
                    System.out.println("\nProgramm wird beendet. Vielen Dank, dass Sie unser Bank System genutzt haben!");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nUngültige Auswahl! Bitte wählen Sie eine Zahl zwischen 1 und 6.");
            }
        }
    }
}
