package gui;

import abstractDescription.AbstractConceptDiagram;
import abstractDescription.AbstractPropertyDiagram;
import concrete.*;
import icircles.util.CannotDrawException;
import lang.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;

import static speedith.i18n.Translations.i18n;

public class DiagramPanel extends JPanel {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 400);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 450);
    private ArrowPanel arrowPanel;
    private Diagram diagram;
    private HashMap<String, Ellipse2D.Double> circleMap;
    private HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap2;
    private Set<ConcreteArrow> arrows;
    private Set<ConcreteEquality> equalities;

    /**
     * Create new diagram panel with nothing displayed in it.
     */
    public DiagramPanel() {
        this(null);
        drawNoDiagramLabel();
    }

    /**
     * Create panel visualising given Concept Diagram
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
        arrowPanel = new ArrowPanel();
    }

    public final void setDiagram(Diagram diagram) {
        if (this.diagram != diagram && (diagram instanceof ConceptDiagram || diagram instanceof PropertyDiagram)) {
            this.diagram = diagram;
            this.removeAll();
            if (this.diagram != null) {
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

    private void drawDiagram() throws CannotDrawException {
        if (diagram != null) {
            arrows = new HashSet<>();
            equalities = new HashSet<>();
            circleMap = new HashMap<>();
            circleMap2 = new HashMap<>();
            Set<ConcreteClassObjectPropertyDiagram> COPs;
            Set<ConcreteDatatypeDiagram> DTs;
            this.setBorder(new EmptyBorder(20, 20, 20, 20));
            this.setLayout(new GridLayout(1, 0));

            final ConcreteBaseDiagram d;
            if (diagram instanceof ConceptDiagram) {
                AbstractConceptDiagram adConcept = AbstractDescriptionTranslator.getAbstractDescription((ConceptDiagram) diagram);
                d = ConcreteConceptDiagram.makeConcreteDiagram(adConcept, 300);
                arrows.addAll(d.getArrows());
                COPs = d.getCOPs();
                DTs = d.getDTs();
            } else if (diagram instanceof PropertyDiagram) {
                AbstractPropertyDiagram  adProperty = AbstractDescriptionTranslator.getAbstractDescription((PropertyDiagram) diagram);
                d = ConcretePropertyDiagram.makeConcreteDiagram(adProperty, 300);
                arrows.addAll(d.getArrows());
                COPs = d.getCOPs();
                DTs = d.getDTs();
            } else {
                COPs = new HashSet<>();
                DTs = new HashSet<>();
            }

            for (ConcreteClassObjectPropertyDiagram cop: COPs) {
                arrows.addAll(cop.arrows);
                equalities.addAll(cop.equalities);
            }


            this.setLayout(new GridLayout(1, 0, 75, 25));
            for (final ConcreteClassObjectPropertyDiagram concreteCOP : COPs) {
//                final COPDiagramsDrawer panel = new COPDiagramsDrawer(concreteCOP, circleMap);
                final COPDiagramsDrawer panel = new COPDiagramsDrawer(concreteCOP);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
//                        circleMap.putAll(panel.getCircleMap());
                        circleMap2.put(concreteCOP.getId(), panel.getCircleMap());
                    }
                });
                panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                panel.setVisible(true);
                add(panel);
            }

            for (ConcreteDatatypeDiagram concreteDT : DTs) {
//                final COPDiagramsDrawer panel = new COPDiagramsDrawer(concreteDT, circleMap);
                final COPDiagramsDrawer panel = new COPDiagramsDrawer(concreteDT);
//                SwingUtilities.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
////                        circleMap.putAll(panel.getCircleMap());
//                    }
//                });
                panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                panel.setVisible(true);
                add(panel);
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    addArrows(circleMap2, arrows, equalities);
                }
            });
        }
    }

    ArrowPanel getArrowGlassPanel() {
        return arrowPanel;
    }

    private void addArrows(final HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap, final Set<ConcreteArrow> arrows, final Set<ConcreteEquality> equalities) {
        if ((circleMap != null) && (circleMap.keySet().size() > 0)) {
            arrowPanel = new ArrowPanel(arrows, equalities, circleMap);
        }
    }
}
