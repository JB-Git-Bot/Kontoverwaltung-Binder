import javax.swing.*;

/**
 * Kontoauszug.java
 *
 * Dieses Panel dient dazu, Informationen über ein ausgewähltes Konto anzuzeigen:
 * - Kontoart
 * - Kontoinhaber
 * - Kontonummer
 * - Kontostand
 * - Gebühren
 */
public class Kontoauszug {
    private JPanel kontoauszugPanel;
    private JTextField kontoinhaberTextField;
    private JTextField kontonummerTextField;
    private JTextField kontostandTextField;
    private JTextField gebuehrenTextField;
    private JLabel kontoinhaberLabel;
    private JLabel kontonummerLabel;
    private JLabel kontostandLabel;
    private JLabel gebuehrenLabel;

    // Neu hinzugefügt:
    private JTextField kontoartTextField;
    private JLabel kontoartLabel;

    public JPanel getKontoauszugPanel() {
        return kontoauszugPanel;
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

    // Getter für das neue Kontoart-Feld
    public JTextField getKontoartTextField() {
        return kontoartTextField;
    }
}
