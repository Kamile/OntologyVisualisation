package gui;

import abstractDescription.AbstractConceptDiagramDescription;
import concrete.ConcreteArrowEnd;
import concrete.ConcreteConceptDiagram;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.util.CannotDrawException;
import lang.Arrow;
import lang.BasicConceptDiagram;
import lang.ConceptDiagram;
import reader.ConceptDiagramsReader;
import speedith.core.lang.reader.ReadingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import static speedith.i18n.Translations.i18n;

/**
 * Bypass ProofPanel and SubgoalsPanel and implement version of SpiderDiagrams
 * Panel
 */
public class DiagramPanel extends JPanel {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);

    private JPanel contentPane;
    private ConceptDiagram conceptDiagram;

    /**
     * Create new diagram panel with nothing displayed in it.
     */
    public DiagramPanel() {
        this(null);
        initComponents();
        drawNoDiagramLabel();
    }

    /**
     * Create panel visualising given Concept Diagram
     *
     * @param cd
     */
    public DiagramPanel(ConceptDiagram cd) {
        initComponents();
        setDiagram(cd);
    }

    private void initComponents() {
        setMinimumSize(MINIMUM_SIZE);
        setPreferredSize(PREFERRED_SIZE);
        setBackground(Color.WHITE);

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public ConceptDiagram getDiagram() {
        return conceptDiagram;
    }

    public final void setDiagram(ConceptDiagram diagram) {
        if (conceptDiagram != diagram) {
            conceptDiagram = diagram;
            this.removeAll();
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

    private void drawErrorLabel() {
        JLabel errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLabel.setText(i18n("PSD_LABEL_DISPLAY_ERROR"));
        this.add(errorLabel);
    }

    private void drawNoDiagramLabel() {
        JLabel noDiagramLbl = new JLabel();
        noDiagramLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noDiagramLbl.setText(i18n("CSD_PANEL_NO_DIAGRAM"));
        this.add(noDiagramLbl);
    }

    private void addArrowLabel(Arrow a) {
        JLabel arrowLabel = new JLabel();
        arrowLabel.setHorizontalAlignment(SwingConstants.CENTER);
        arrowLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        arrowLabel.setText(a.toString());
        this.add(arrowLabel);
    }

    private void drawDiagram() throws CannotDrawException {
        if (conceptDiagram != null) {
            if (conceptDiagram instanceof BasicConceptDiagram) {
                final List<Arrow> arrows = ((BasicConceptDiagram) conceptDiagram).getArrows();
                this.setLayout(new GridLayout(1, 0));

                AbstractConceptDiagramDescription ad2 = AbstractDescriptionTranslator.getAbstractDescription((BasicConceptDiagram) conceptDiagram);
                final ConcreteConceptDiagram ccd = ConcreteConceptDiagram.makeConcreteDiagram(ad2, 300);
                final HashMap<String, ConcreteArrowEnd> targetMappings = new HashMap<>();

                for (ConcreteDiagram cd : ccd.getSpiderDiagrams()) {
                    ConceptDiagramsDrawer panel = new ConceptDiagramsDrawer(cd, targetMappings);
                    targetMappings.putAll(panel.getTargetMappings());
                    panel.setBorder(BorderFactory.createEmptyBorder());
                    panel.setVisible(true);
                    add(panel);
                }

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, ConcreteArrowEnd> copyTargets = (HashMap<String, ConcreteArrowEnd>) targetMappings.clone();
                        addArrows(copyTargets, arrows);
                    }
                });
            } else {
                throw new IllegalArgumentException(i18n("SD_PANEL_UNKNOWN_DIAGRAM_TYPE"));
            }
        }
    }

    private void addArrows(final HashMap<String, ConcreteArrowEnd> targetMappings, final List<Arrow> arrows) {
        System.out.println("In addArrows");
        System.out.println(targetMappings.keySet().size());
        ArrowDrawer arrowPanel = new ArrowDrawer(arrows, targetMappings);
        arrowPanel.setVisible(true);
        this.add(arrowPanel);
        this.repaint();
        System.out.println("At end of addArrows");
    }

    private void onCancel() {
    }

    public static void main(String[] args) {
        DiagramPanel dialog = new DiagramPanel();
        dialog.setVisible(true);
        System.exit(0);
    }
}
