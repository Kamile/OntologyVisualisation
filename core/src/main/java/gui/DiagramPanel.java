package gui;

import abstractDescription.AbstractConceptDiagram;
import abstractDescription.AbstractPropertyDiagram;
import concrete.*;
import icircles.util.CannotDrawException;
import lang.*;
import util.Permutation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

import static speedith.i18n.Translations.i18n;

public class DiagramPanel extends JPanel {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 400);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 450);
    private ArrowPanel arrowPanel;
    private Diagram diagram;
    private HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap;
    private Set<ConcreteArrow> arrows;
    private Set<ConcreteEquality> equalities;

    /**
     * Create new diagram panel with nothing displayed in it.
     */
    public DiagramPanel() {
        initComponents();
        drawNoDiagramLabel();
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
            Set<ConcreteCOP> COPs;
            Set<ConcreteDT> DTs;
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

            for (ConcreteCOP cop: COPs) {
                arrows.addAll(cop.arrows);
                equalities.addAll(cop.equalities);
            }
            for (ConcreteDT dt: DTs) {
                equalities.addAll(dt.equalities);
            }

            int numRows = (int) Math.ceil((double)(COPs.size() + DTs.size())/5);
            this.setLayout(new GridLayout(numRows, 0, 75, 25));

            // calculate the score for each, then draw most optimal
            int height = (this.getHeight() - 40);
            if (COPs.size() > 0) {
                List<ConcreteCOP> copList = new ArrayList<>();
                copList.addAll(COPs);
                List<ConcreteCOP> optimalPermutation = copList;
                if (getArrowsBetweenCOPs() > 0) {
                    List<List<ConcreteCOP>> copPermutations = Permutation.generatePermutations(copList);
                    optimalPermutation = copPermutations.get(0);
                    double currentMinScore = Double.MAX_VALUE;
                    for (List<ConcreteCOP> permutation : copPermutations) {
                        circleMap.clear();
                        // 20 border on each side, x gap 75 between panels
                        int width = (this.getWidth() - 40 - (permutation.size() - 1) * 75) / permutation.size();
                        int offset = 20;
                        for (ConcreteCOP cop : permutation) {
                            final SubDiagramPanel panel = new SubDiagramPanel(cop, width, height, offset);
                            offset += 75 + width;
                            circleMap.put(cop.getId(), panel.getCircleMap());
                        }

                        addArrows(circleMap, getArrowsClone(), equalities);
                        double score = arrowPanel.getScore();
                        if (diagram instanceof PropertyDiagram && permutation.get(0).isSingleVariableTInstance) {
                            score -= 100000; // prefer to have single t variable first
                        }
                        if (score < currentMinScore) {
                            currentMinScore = score;
                            optimalPermutation = permutation;
                        }
                    }
                }

                circleMap.clear();
                int width = (this.getWidth() - 40 - (COPs.size() - 1) * 75) / COPs.size();
                int offset = 20;
                for (final ConcreteCOP concreteCOP : optimalPermutation) {
                    final SubDiagramPanel panel = new SubDiagramPanel(concreteCOP, width, height, offset);
                    offset += 75 + width;
                    circleMap.put(concreteCOP.getId(), panel.getCircleMap());
                    panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    panel.setVisible(true);
                    add(panel);
                }
            }

            for (final ConcreteDT concreteDT : DTs) {
                final SubDiagramPanel panel = new SubDiagramPanel(concreteDT,0, height, 0);
                circleMap.put(concreteDT.getId(), panel.getCircleMap());
                panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                panel.setVisible(true);
                add(panel);
            }

            addArrows(circleMap, arrows, equalities);
        }
    }

    ArrowPanel getArrowGlassPanel() {
        return arrowPanel;
    }

    private void addArrows(HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap, Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities) {
        if ((circleMap != null) && (circleMap.keySet().size() > 0)) {
            arrowPanel = new ArrowPanel(arrows, equalities, circleMap);
            if (diagram instanceof PropertyDiagram) {
                arrowPanel.isPD = true;
            }
        }
    }

    private Set<ConcreteArrow> getArrowsClone() {
        Set<ConcreteArrow> clone = new HashSet<>(arrows.size());
        for (ConcreteArrow arrow : arrows) {
            clone.add(arrow.clone());
        }
        return clone;
    }

    private int getArrowsBetweenCOPs() {
        int n = 0;
        for (ConcreteArrow arrow: arrows) {
            if (arrow.getParentId() == 0) {
                n++;
            }
        }
        return n;
    }
}
