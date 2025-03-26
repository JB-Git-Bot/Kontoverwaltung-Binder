import javax.swing.*;
import java.awt.*;

/**
 * KontoErstellung.java
 *
 * Dieses Panel dient der Erstellung eines neuen Kontos.
 * Der Benutzer gibt Kontoinhaber, Kontonummer, Kontostand und Gebühren ein
 * und wählt die Kontoart. Bei "Kreditkonto" wird zusätzlich ein Pop-up angezeigt,
 * um Zinssatz und Überziehungsrahmen einzugeben.
 */
public class KontoErstellung {
    private JLabel kontoErstellunguebersichtLabel;
    private JTextField kontoinhaberTextField;
    private JTextField kontonummerTextField;
    private JTextField kontostandTextField;
    private JTextField gebuehrenTextField;
    private JPanel kontoErstellungPanel;
    private JLabel kontonummerLabel;
    private JLabel kontotandLabel;
    private JLabel gebuehrenLabel;
    private JButton erstellenButton;
    private JComboBox<String> kontoartComboBox;  // Hier wählt der Benutzer Giro-, Spar- oder Kreditkonto
    private JLabel kontoartLabel;

    /**
     * Getter für das Haupt-Panel, damit wir es in einem JDialog verwenden können.
     * @return kontoErstellungPanel
     */
    public JPanel getKontoErstellungPanel() {
        return kontoErstellungPanel;
    }

    public JTextField getKontoinhaberTextField() {
        return kontoinhaberTextField;
    }

    public JTextField getKontonummerTextField() {
        return kontonummerTextField;
    }

    public JTextField getKontostandTextField() {
        return kontostandTextField;
    }

    public JTextField getGebuehrenTextField() {
        return gebuehrenTextField;
    }

    public JButton getErstellenButton() {
        return erstellenButton;
    }

    public JComboBox<String> getKontoartComboBox() {
        return kontoartComboBox;
    }
}
