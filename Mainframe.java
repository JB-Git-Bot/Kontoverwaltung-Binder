import javax.swing.*;
import java.awt.*;

/**
 * Mainframe.java
 *
 * Dies ist das Hauptfenster (bzw. das Haupt-Panel) der Anwendung.
 * Es enthält eine ComboBox zum Auswählen vorhandener Konten und Buttons,
 * um weitere Pop-up-Fenster (KontoErstellung, Ueberweisung, Kontoauszug) zu öffnen.
 */
public class Mainframe {
    private JPanel mainframePanel;
    private JLabel menuLabel;
    private JComboBox<Konto> comboBox1;
    private JLabel KontenlisteLabel;
    private JButton kontoErstellenButton;
    private JButton closeButton;
    private JButton ueberweisung;
    private JButton kontoauszugButton;

    /**
     * Model, das die Liste aller Konten verwaltet und in der ComboBox anzeigt.
     * Damit können wir jederzeit neue Konten hinzufügen.
     */
    private DefaultComboBoxModel<Konto> kontoModel = new DefaultComboBoxModel<>();

    /**
     * Konstruktor: Hier definieren wir das Verhalten der Buttons,
     * binden die ComboBox an unser Konto-Model usw.
     */
    public Mainframe() {
        // Die ComboBox soll unser Konto-Model verwenden
        comboBox1.setModel(kontoModel);

        // Button "Konto Erstellen" öffnet ein modales Fenster (JDialog)
        kontoErstellenButton.addActionListener(e -> {
            // JDialog mit modalem Verhalten erstellen
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(mainframePanel),
                    "Konto erstellen", true);

            // Unser Panel für die Kontoerstellung instanziieren
            KontoErstellung erstellungPanel = new KontoErstellung();

            // Den ActionListener für den "Erstellen"-Button setzen
            erstellungPanel.getErstellenButton().addActionListener(ev -> {
                // Werte aus den Textfeldern lesen
                String inhaber = erstellungPanel.getKontoinhaberTextField().getText();
                String nummer = erstellungPanel.getKontonummerTextField().getText();

                double stand = 0.0;
                double geb = 0.0;

                try {
                    stand = Double.parseDouble(erstellungPanel.getKontostandTextField().getText());
                } catch (NumberFormatException ignored) {}

                try {
                    geb = Double.parseDouble(erstellungPanel.getGebuehrenTextField().getText());
                } catch (NumberFormatException ignored) {}

                // Kontoart aus der ComboBox lesen
                String ausgewaehlteKontoart = (String) erstellungPanel.getKontoartComboBox().getSelectedItem();

                // Standardwerte für Zinssatz und Überziehungsrahmen (z. B. 0)
                double zinssatz = 0.0;
                double ueberziehungsrahmen = 0.0;

                // Falls es sich um ein Kreditkonto handelt, öffnen wir ein kleines Pop-up,
                // um Zinssatz und Überziehungsrahmen abzufragen
                if ("Kreditkonto".equals(ausgewaehlteKontoart)) {
                    JPanel kreditPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                    JTextField zinssatzField = new JTextField();
                    JTextField ueberziehungField = new JTextField();

                    kreditPanel.add(new JLabel("Zinssatz (%):"));
                    kreditPanel.add(zinssatzField);
                    kreditPanel.add(new JLabel("Überziehungsrahmen:"));
                    kreditPanel.add(ueberziehungField);

                    int result = JOptionPane.showConfirmDialog(dialog, kreditPanel,
                            "Kreditkonto-Einstellungen", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            zinssatz = Double.parseDouble(zinssatzField.getText());
                        } catch (NumberFormatException ignored) {}
                        try {
                            ueberziehungsrahmen = Double.parseDouble(ueberziehungField.getText());
                        } catch (NumberFormatException ignored) {}
                    } else {
                        // Wenn der User abbricht, wird das Konto nicht erstellt
                        return;
                    }
                }

                // Ganz einfache Plausibilitätsprüfung
                if (!inhaber.isEmpty() && !nummer.isEmpty()) {
                    // Neues Konto anlegen (mit allen Feldern)
                    Konto neuesKonto = new Konto(
                            inhaber,
                            nummer,
                            stand,
                            geb,
                            ausgewaehlteKontoart,    // kontoart
                            zinssatz,                // zinssatz
                            ueberziehungsrahmen      // ueberziehungsrahmen
                    );

                    // Konto dem Model hinzufügen, damit es in der ComboBox auftaucht
                    kontoModel.addElement(neuesKonto);

                    JOptionPane.showMessageDialog(dialog,
                            "Konto erfolgreich erstellt!",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Dialog schließen
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Bitte fülle alle relevanten Felder aus!",
                            "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            // Panel dem Dialog hinzufügen
            dialog.setContentPane(erstellungPanel.getKontoErstellungPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(mainframePanel);
            dialog.setVisible(true);
        });

        // Button "Überweisung" öffnet das entsprechende Pop-up
        ueberweisung.addActionListener(e -> {
            // 1) Vor dem Öffnen prüfen, ob >= 2 Konten vorhanden sind
            if (kontoModel.getSize() < 2) {
                JOptionPane.showMessageDialog(
                        mainframePanel,
                        "Es müssen mindestens 2 Konten existieren, um eine Überweisung durchzuführen!",
                        "Info",
                        JOptionPane.WARNING_MESSAGE
                );
                return; // Abbrechen, wenn weniger als 2 Konten vorhanden sind
            }

            // Modales JDialog-Fenster erstellen
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(mainframePanel),
                    "Überweisung", true);

            // Panel für die Überweisung instanziieren
            Ueberweisung ueberweisungPanel = new Ueberweisung();

            // Für Absender und Empfänger jeweils ein eigenes Model anlegen
            DefaultComboBoxModel<Konto> absenderModel = new DefaultComboBoxModel<>();
            DefaultComboBoxModel<Konto> empfaengerModel = new DefaultComboBoxModel<>();

            // Mit den gleichen Konten aus kontoModel befüllen
            for (int i = 0; i < kontoModel.getSize(); i++) {
                Konto k = kontoModel.getElementAt(i);
                absenderModel.addElement(k);
                empfaengerModel.addElement(k);
            }

            // Die ComboBoxen mit den separaten Models bestücken
            ueberweisungPanel.getAbsenderComboBox().setModel(absenderModel);
            ueberweisungPanel.getEmpfaengerComboBox().setModel(empfaengerModel);

            // ActionListener für den "Überweisen"-Button
            ueberweisungPanel.getUeberweisenButton().addActionListener(ev -> {
                // Betrag aus dem Textfeld lesen
                double betrag = 0.0;
                try {
                    betrag = Double.parseDouble(ueberweisungPanel.getBetragTextField().getText());
                } catch (NumberFormatException ignored) {}

                // Ausgewählte Konten aus den ComboBoxen holen
                Konto absender = (Konto) ueberweisungPanel.getAbsenderComboBox().getSelectedItem();
                Konto empfaenger = (Konto) ueberweisungPanel.getEmpfaengerComboBox().getSelectedItem();

                // Plausibilitätsprüfungen
                if (absender != null && empfaenger != null && absender != empfaenger && betrag > 0) {
                    // Hat der Absender genug Guthaben? (Für Kreditkonto könntest du hier
                    // zusätzlich den Überziehungsrahmen prüfen, falls gewünscht)
                    if (absender.getKontostand() >= betrag) {
                        // Kontostände anpassen
                        absender.setKontostand(absender.getKontostand() - betrag);
                        empfaenger.setKontostand(empfaenger.getKontostand() + betrag);

                        JOptionPane.showMessageDialog(dialog,
                                "Überweisung erfolgreich durchgeführt!",
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog,
                                "Absender hat nicht genug Guthaben!",
                                "Fehler",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Bitte einen gültigen Betrag und unterschiedliche Konten wählen!",
                            "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.setContentPane(ueberweisungPanel.getUeberweisungPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(mainframePanel);
            dialog.setVisible(true);
        });

        // Button "Kontoauszug" öffnet das entsprechende Pop-up
        kontoauszugButton.addActionListener(e -> {
            // Ausgewähltes Konto in der ComboBox holen
            Konto ausgewaehltesKonto = (Konto) comboBox1.getSelectedItem();

            // Wenn kein Konto ausgewählt wurde, Meldung anzeigen
            if (ausgewaehltesKonto == null) {
                JOptionPane.showMessageDialog(mainframePanel,
                        "Bitte zuerst ein Konto aus der Liste auswählen!",
                        "Info",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Modales JDialog-Fenster erstellen
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(mainframePanel),
                    "Kontoauszug", true);

            // Panel für den Kontoauszug instanziieren
            Kontoauszug kontoauszugPanel = new Kontoauszug();

            // Textfelder mit den Daten des ausgewählten Kontos füllen
            kontoauszugPanel.getKontoartTextField().setText(ausgewaehltesKonto.getKontoart());
            kontoauszugPanel.getKontoinhaberTextField().setText(ausgewaehltesKonto.getKontoinhaber());
            kontoauszugPanel.getKontonummerTextField().setText(ausgewaehltesKonto.getKontonummer());
            kontoauszugPanel.getKontostandTextField().setText(
                    String.valueOf(ausgewaehltesKonto.getKontostand()));
            kontoauszugPanel.getGebuehrenTextField().setText(
                    String.valueOf(ausgewaehltesKonto.getGebuehren()));

            dialog.setContentPane(kontoauszugPanel.getKontoauszugPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(mainframePanel);
            dialog.setVisible(true);
        });

        // Button "Close" beendet das Programm
        closeButton.addActionListener(e -> {
            System.exit(0);
        });
    }

    /**
     * Getter für das Haupt-Panel. Dieses Panel wird in der main-Methode gesetzt.
     * @return Panel mainframePanel
     */
    public JPanel getMainframePanel() {
        return mainframePanel;
    }

    /**
     * main-Methode, mit der du das Programm starten kannst.
     * So musst du keine separate Main-Klasse haben.
     */
    public static void main(String[] args) {
        // Optional: Look and Feel setzen (kannst du auch weglassen)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Falls nicht möglich, ignorieren wir es
        }

        // Hauptfenster erzeugen
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Kontoübersicht - Menü - Binder");
            // Mainframe-Objekt erzeugen
            Mainframe mainframe = new Mainframe();

            // Mainframe-Panel als ContentPane setzen
            frame.setContentPane(mainframe.getMainframePanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
