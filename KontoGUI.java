import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KontoGUI extends JFrame {
    private JComboBox<String> KontoartenDropdown;
    private JTextField kontonummerField, kontoinhaberField, gebuehrenField, kontostandField;
    private JTextArea vertragshistorieArea;
    private JButton closeButton;

    public KontoGUI() {
        setTitle("Kontoverwaltung");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // UI-Elemente
        add(new JLabel("Kontoart:"));
        KontoartenDropdown = new JComboBox<>(new String[]{"Girokonto", "Sparkonto", "Kreditkonto"});
        add(KontoartenDropdown);

        add(new JLabel("Kontonummer:"));
        kontonummerField = new JTextField();
        add(kontonummerField);

        add(new JLabel("Kontoinhaber:"));
        kontoinhaberField = new JTextField();
        add(kontoinhaberField);

        add(new JLabel("Gebühren/Zinsen:"));
        gebuehrenField = new JTextField();
        add(gebuehrenField);

        add(new JLabel("Kontostand:"));
        kontostandField = new JTextField();
        add(kontostandField);

        add(new JLabel("Vertragshistorie:"));
        vertragshistorieArea = new JTextArea();
        vertragshistorieArea.setEditable(false);
        add(new JScrollPane(vertragshistorieArea));

        closeButton = new JButton("Close");
        add(closeButton);

        // Button-Action
        closeButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}
