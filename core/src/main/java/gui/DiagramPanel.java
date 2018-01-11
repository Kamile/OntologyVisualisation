package gui;

import icircles.util.CannotDrawException;
import lang.BasicConceptDiagram;
import lang.ConceptDiagram;
import reader.ConceptDiagramsReader;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.reader.ReadingException;
import speedith.ui.SpiderDiagramPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static speedith.i18n.Translations.i18n;

/** Bypass ProofPanel and SubgoalsPanel and implement version of SpiderDiagrams
 * Panel
 */
public class DiagramPanel extends JPanel {
    private static final Dimension PrimaryDiagramSize = new Dimension(150, 150);

    private JPanel contentPane;
    private JPanel pnlDiagram;
    private JScrollPane scrlDiagram;
    private ConceptDiagram conceptDiagram;

    /**
     * Create new diagram panel with nothing displayed in it.
     */
    public DiagramPanel() {
        this(null);
    }

    /**
     * Create panel visualising given Concept Diagram
     * @param cd
     */
    public DiagramPanel(ConceptDiagram cd) {
        initComponents();
        setDiagram(cd);
    }

    public ConceptDiagram getDiagram() {
        return conceptDiagram;
    }

    public final void setDiagram(ConceptDiagram diagram) {
        if (conceptDiagram != diagram) {
            conceptDiagram = diagram;
            pnlDiagram.removeAll();
            if (conceptDiagram != null) {
                try {
                    drawDiagram();
                } catch (CannotDrawException e) {
                    drawErrorLabel();
                }
            } else {
                drawNoDiagramLabel();
            }
            validate();
            repaint();
        }
    }

    public String getDiagramString() {
        return conceptDiagram == null ? "" : conceptDiagram.toString();
    }

    public void setDiagramString(String diagram) throws ReadingException {
        if (diagram == null || diagram.isEmpty()) {
            setDiagram(null);
        } else {
            setDiagram(ConceptDiagramsReader.readConceptDiagram(diagram));
        }
    }

    private void initComponents() {
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void drawErrorLabel() {
        JLabel errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLabel.setText(i18n("PSD_LABEL_DISPLAY_ERROR"));
        pnlDiagram.add(errorLabel);
    }

    private void drawNoDiagramLabel() {
        JLabel noDiagramLbl = new JLabel();
        noDiagramLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noDiagramLbl.setText(i18n("CSD_PANEL_NO_DIAGRAM"));
        pnlDiagram.add(noDiagramLbl);
    }

    private void drawDiagram() throws CannotDrawException {
        if (conceptDiagram != null) {
            if (conceptDiagram instanceof BasicConceptDiagram) {
                java.util.List<SpiderDiagram> spiders = ((BasicConceptDiagram) conceptDiagram).getSpiderDiagrams();
                SpiderDiagramPanel panel = new SpiderDiagramPanel();
                panel.setDiagram(spiders.get(0));
                pnlDiagram.add(panel);
            } else {
                throw new IllegalArgumentException(i18n("SD_PANEL_UNKNOWN_DIAGRAM_TYPE"));
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary
    }

    public static void main(String[] args) {
        DiagramPanel dialog = new DiagramPanel();
        dialog.setVisible(true);
        System.exit(0);
    }
}
