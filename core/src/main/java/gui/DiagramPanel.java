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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static speedith.i18n.Translations.i18n;

public class DiagramPanel extends JPanel {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 400);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 450);

    private ArrowPanel arrowPanel;
    private Diagram diagram;
    private HashMap<String, Ellipse2D.Double> circleMap;
    private List<Arrow> arrows;
    private List<Equality> equalities;

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
            arrows = diagram.getArrows();
            equalities = new ArrayList<>();
            circleMap = new HashMap<>();
            HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> COPmapping;
            HashMap<DatatypeDiagram, ConcreteDatatypeDiagram> DTmapping;
            this.setBorder(new EmptyBorder(20, 20, 20, 20));
            this.setLayout(new GridLayout(1, 0));

            final ConcreteBaseDiagram d;
            if (diagram instanceof ConceptDiagram) {
                AbstractConceptDiagram adConcept = AbstractDescriptionTranslator.getAbstractDescription((ConceptDiagram) diagram);
                d = ConcreteConceptDiagram.makeConcreteDiagram(adConcept, 300);
                COPmapping = d.getCOPMapping();
                DTmapping = d.getDTMapping();
            } else if (diagram instanceof PropertyDiagram) {
                AbstractPropertyDiagram  adProperty = AbstractDescriptionTranslator.getAbstractDescription((PropertyDiagram) diagram);
                d = ConcretePropertyDiagram.makeConcreteDiagram(adProperty, 300);
                COPmapping = d.getCOPMapping();
                DTmapping = d.getDTMapping();
            } else {
                COPmapping = new HashMap<>();
                DTmapping = new HashMap<>();
            }

            for (ClassObjectPropertyDiagram cop: COPmapping.keySet()) {
                arrows.addAll(cop.getArrows());
                equalities.addAll(cop.getEqualities());
            }

            if (COPmapping.values().size() > 0 || DTmapping.values().size() > 0) {
                this.setLayout(new GridLayout(1, 0, 75, 25));
                for (ConcreteClassObjectPropertyDiagram concreteCOP : COPmapping.values()) {
                    final COPDiagramsDrawer panel = new COPDiagramsDrawer(concreteCOP, circleMap);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            circleMap.putAll(panel.getCircleMap());
                        }
                    });
                    circleMap.putAll(panel.getCircleMap());
                    panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    panel.setVisible(true);

                    add(panel);
                }

                for (ConcreteDatatypeDiagram concreteDT : DTmapping.values()) {
                    final COPDiagramsDrawer panel = new COPDiagramsDrawer(concreteDT, circleMap);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            circleMap.putAll(panel.getCircleMap());
                        }
                    });
                    circleMap.putAll(panel.getCircleMap());
                    panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    panel.setVisible(true);

                    add(panel);
                }
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    addArrows(circleMap, arrows, equalities);
                }
            });
        }
    }

    ArrowPanel getArrowGlassPanel() {
        return arrowPanel;
    }

    private void addArrows(final HashMap<String, Ellipse2D.Double> circleMap, final List<Arrow> arrows, final List<Equality> equalities) {
        if ((circleMap != null) && (circleMap.keySet().size() > 0)) {
            arrowPanel = new ArrowPanel(arrows, equalities, circleMap);
        }
    }
}
