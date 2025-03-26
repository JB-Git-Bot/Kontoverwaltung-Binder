import javax.swing.*;

/**
 * Ueberweisung.java
 *
 * Dieses Panel dient dazu, eine Überweisung durchzuführen.
 * Der Benutzer kann in zwei ComboBoxen (Absender/Empfänger) Konten auswählen
 * und einen Betrag eingeben.
 */
public class Ueberweisung {
    private JComboBox<Konto> absenderComboBoc;
    private JComboBox<Konto> empfaengerComboBox;
    private JButton ueberweisenButton;
    private JTextField betragTextField;
    private JPanel ueberweisungPanel;

    /**
     * Getter für das Haupt-Panel, damit wir es in einem JDialog verwenden können.
     * @return ueberweisungPanel
     */
    public JPanel getUeberweisungPanel() {
        return ueberweisungPanel;
    }

    /**
     * Getter, damit wir in Mainframe die ausgewählten Konten und den Betrag auslesen können.
     */
    public JComboBox<Konto> getAbsenderComboBox() {
        return absenderComboBoc;
    }

    public JComboBox<Konto> getEmpfaengerComboBox() {
        return empfaengerComboBox;
    }

    public JButton getUeberweisenButton() {
        return ueberweisenButton;
    }

    public JTextField getBetragTextField() {
        return betragTextField;
    }
}
