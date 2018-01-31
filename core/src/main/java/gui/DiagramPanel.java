package gui;

import concrete.ConcreteClassObjectPropertyDiagram;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.util.CannotDrawException;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Set;

import static speedith.i18n.Translations.i18n;

/**
 * Bypass ProofPanel and SubgoalsPanel and implement version of SpiderDiagramsPanel
 */
public class DiagramPanel extends JPanel {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);

//    private Set<ConcreteDiagram> spiderDiagrams;
    private Set<ConcreteClassObjectPropertyDiagram> COPDiagrams;
    private HashMap<String, Ellipse2D.Double> circleMap;

//    public DiagramPanel(Set<ConcreteDiagram> spiderDiagrams) {
//        circleMap = new HashMap<>();
//        initComponents();
//        setDiagrams(spiderDiagrams);
//    }

    public DiagramPanel(Set<ConcreteClassObjectPropertyDiagram> COPDiagrams) {
        circleMap = new HashMap<>();
        initComponents();
        setDiagrams(COPDiagrams);
    }

    private void initComponents() {
        setMinimumSize(MINIMUM_SIZE);
        setPreferredSize(PREFERRED_SIZE);
        setBackground(Color.WHITE);
    }

    private void drawErrorLabel() {
        JLabel errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLabel.setText(i18n("PSD_LABEL_DISPLAY_ERROR"));
        this.add(errorLabel);
    }

//    public final void setDiagrams(Set<ConcreteDiagram> diagrams) {
//        if (spiderDiagrams != diagrams) {
//            spiderDiagrams = diagrams;
//            this.removeAll();
//            if (spiderDiagrams != null) {
//                try {
//                    drawDiagrams();
//                } catch (CannotDrawException e) {
//                    drawErrorLabel();
//                }
//            }
//            validate();
//            repaint();
//        }
//    }

    public final void setDiagrams(Set<ConcreteClassObjectPropertyDiagram> diagrams) {
        if (COPDiagrams != diagrams) {
            COPDiagrams = diagrams;
            this.removeAll();
            if (COPDiagrams != null) {
                try {
                    drawDiagrams();
                } catch (CannotDrawException e) {
                    drawErrorLabel();
                }
            }
            validate();
            repaint();
        }
    }

//    private void drawDiagrams() throws CannotDrawException {
//        if (spiderDiagrams != null) {
//            this.setLayout(new GridLayout(1, 0));
//            for (ConcreteDiagram cd : spiderDiagrams) {
//                COPDiagramsDrawer panel = new COPDiagramsDrawer(cd, circleMap);
//                circleMap.putAll(panel.getCircleMap());
//                panel.setBorder(BorderFactory.createEmptyBorder());
//                panel.setVisible(true);
//                add(panel);
//            }
//        }
//    }

    private void drawDiagrams() throws CannotDrawException {
        if (COPDiagrams != null) {
            this.setLayout(new GridLayout(1, 0));
            for (ConcreteClassObjectPropertyDiagram cop : COPDiagrams) {
                COPDiagramsDrawer panel = new COPDiagramsDrawer(cop, circleMap);
                circleMap.putAll(panel.getCircleMap());
                panel.setBorder(BorderFactory.createEmptyBorder());
                panel.setVisible(true);
                add(panel);
            }
        }
    }

    public HashMap<String, Ellipse2D.Double> getCircleMap() {
        return circleMap;
    }
}
