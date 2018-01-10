package gui;

import lang.ConceptDiagram;
import reader.ConceptDiagramsReader;
import speedith.core.lang.reader.ReadingException;

import javax.swing.*;
import java.awt.event.*;

public class CDInputDialog extends JDialog {

    private boolean cancelled = true;
    private ConceptDiagram conceptDiagram = null;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea txtInputArea;
    private JLabel lblInstruction;
    private JLabel lblError;

    public CDInputDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Text representation");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

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

    private void onOK() {
        String cdText = txtInputArea.getText();

        try {
            conceptDiagram = ConceptDiagramsReader.readConceptDiagram(cdText);
            cancelled = false;
            dispose();
        } catch (ReadingException e) {
            System.err.println("Error parsing representation.");
            lblError.setText("Error parsing representation.");
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

    public boolean isCancelled() {
        return cancelled;
    }

    public void setConceptDiagramText(ConceptDiagram cd) {
        if (cd == null) {
            setConceptDiagramText("");
        } else {
            setConceptDiagramText(cd.toString());
        }
    }

    public void setConceptDiagramText(String cd) {
        txtInputArea.setText(cd);
    }

    public ConceptDiagram getConceptDiagrma() {
        return conceptDiagram;
    }

}
