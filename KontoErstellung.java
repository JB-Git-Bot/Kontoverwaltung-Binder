import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class KontoErstellung extends JDialog {
    private JPanel panelDialog;
    private JButton kerstellen;
    private JButton cancelButton;
    private JTextField kontonummerInput;
    private JTextField kontoinhaberInput;
    private JTextField gebuehrenInput;
    private JTextField kontostandInput;
    private JLabel kontoart;
    private JLabel kontonummer;
    private JLabel kontoinhaber;
    private JLabel gebuehren;
    private JLabel kontostand;
    private JComboBox kontoartInput;
    private static List<Basic> kontenListe = new ArrayList<>();
    private static int nextKontonummer = 10000;
    public KontoErstellung(List<Basic> kontenListe, JComboBox kontoliste) {
        setContentPane(panelDialog);
        setModal(true);

        KontoErstellung.kontenListe = kontenListe;

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panelDialog);
                dialog.dispose();
            }
        });
        kerstellen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("TEST");
                // Auslesen der Eingaben aus den neuen Feldern
                String selectedKontoart = kontoartInput.getSelectedItem().toString();

                String kontonummerText = kontonummerInput.getText();
                String kontoinhaber = kontoinhaberInput.getText();
                String gebuehrenText = gebuehrenInput.getText();    // Für double Kontoführungsgebühren
                String kontostandText = kontostandInput.getText();    // Für double Kontostand

                int ktnr = Integer.parseInt(kontonummerText);
                double kontofuehrungsgebuehren = Double.parseDouble(gebuehrenText);
                double kontostand = Double.parseDouble(kontostandText);

                switch (selectedKontoart) {
                    case "Girokonto": {
                        Girokonto neuesKonto = new Girokonto(
                                ktnr,
                                kontoinhaber,
                                Basic.DEFAULT_BLZ,
                                kontofuehrungsgebuehren,
                                kontostand,
                                0
                        );
                        kontenListe.add(neuesKonto);
                        kontoliste.addItem(neuesKonto.Kontonummer + " " + neuesKonto.Kontoinhaber);
                        break;
                    }
                    case "Sparkonto": {
                        Sparkonto neuesKonto = new Sparkonto(
                                ktnr,
                                kontoinhaber,
                                Basic.DEFAULT_BLZ,
                                kontofuehrungsgebuehren,
                                kontostand
                        );
                        kontenListe.add(neuesKonto);
                        kontoliste.addItem(neuesKonto.Kontonummer + " " + neuesKonto.Kontoinhaber);
                        break;
                    }
                    case "Kreditkonto": {
                        String kredRahmenInput = JOptionPane.showInputDialog(
                                null, "Geben Sie den Überziehungsrahmen für das Kreditkonto ein:");
                        double kredRahmen;
                        try {
                            kredRahmen = Double.parseDouble(kredRahmenInput);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Ungültiger Wert für den Überziehungsrahmen.",
                                    "Eingabefehler",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String zinssatzInput = JOptionPane.showInputDialog(
                                null, "Geben Sie den Zinssatz an (z.B. 3.5):");
                        double zinssatz;
                        try {
                            zinssatz = Double.parseDouble(zinssatzInput);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Ungültiger Wert für den Zinssatz.",
                                    "Eingabefehler",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Kreditkonto neuesKonto = new Kreditkonto(
                                ktnr,
                                kontoinhaber,
                                Basic.DEFAULT_BLZ,
                                Basic.DEFAULT_KONTOFUEHRUNGSGEBUEHREN,
                                Basic.DEFAULT_KONTOSTAND,
                                kredRahmen,
                                zinssatz
                        );
                        kontenListe.add(neuesKonto);
                        kontoliste.addItem(neuesKonto.Kontonummer + " " + neuesKonto.Kontoinhaber);
                        break;
                    }
                    default: {
                        JOptionPane.showMessageDialog(null,
                                "Keine Kontoart gefunden.",
                                "Kontoart Fehler",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Nach erfolgreicher Erstellung kann das Konto z.B. weiterverarbeitet werden:
                JOptionPane.showMessageDialog(null,
                        "Konto wurde erfolgreich erstellt:\n" + kontonummerText);

            }

        });
        pack();
        setVisible(true);
    }

}