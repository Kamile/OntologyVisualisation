package gui;

import lang.Diagram;
import reader.DiagramsReader;
import speedith.core.lang.reader.ReadingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CDInputDialog extends JDialog {

    private boolean cancelled = true;
    private Diagram conceptDiagram = null;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea txtInputArea;
    private JLabel lblInstruction;
    private JLabel lblError;

    CDInputDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Text representation");
        setMinimumSize(new Dimension(500, 200));
        setPreferredSize(new Dimension(750,300));

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String cdText = txtInputArea.getText();

        try {
            conceptDiagram = DiagramsReader.readConceptDiagram(cdText);
            cancelled = false;
            dispose();
        } catch (ReadingException e) {
            System.err.println(e.getLocalizedMessage());
            lblError.setText("Error parsing representation. ");
        }
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        CDInputDialog dialog = new CDInputDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    boolean isCancelled() {
        return cancelled;
    }

    Diagram getConceptDiagram() {
        return conceptDiagram;
    }

}
