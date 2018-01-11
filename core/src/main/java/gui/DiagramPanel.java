package gui;

import lang.ConceptDiagram;
import speedith.core.reasoning.Goals;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DiagramPanel extends JDialog {
    private JPanel contentPane;
    private JPanel pnlDiagram;
    private JScrollPane scrlDiagram;

    public DiagramPanel() {
        this(null);
    }

    public DiagramPanel(ConceptDiagram cd) {
        initComponents();
    }

    private void initComponents() {
        setContentPane(contentPane);
        setModal(true);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void newProof(ArrayList<ConceptDiagram> cds) {

    }

    public static void main(String[] args) {
        DiagramPanel dialog = new DiagramPanel();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
