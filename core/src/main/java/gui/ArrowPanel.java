package gui;

import concrete.ConcreteArrow;
import concrete.ConcreteEquality;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;

public class ArrowPanel extends JComponent {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);
    private static float dash[] = {10.0f};
    private static final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

    private Set<ConcreteArrow> arrows;
    private Set<ConcreteEquality> equalities;
    private HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap;
    private HashMap<String, Integer> existingArrowCount;
    private Set<ConcreteArrow> toBeUpdated;
    boolean isPD;

    ArrowPanel() {
        this.arrows = new HashSet<>();
        this.equalities = new HashSet<>();
        this.circleMap = new HashMap<>();
        this.existingArrowCount = new HashMap<>();
        this.toBeUpdated = new HashSet<>();
        isPD = false;
    }

    ArrowPanel(Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities, HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap) {
        this.arrows = arrows;
        this.equalities = equalities;
        this.circleMap = circleMap;
        this.existingArrowCount = new HashMap<>();
        this.toBeUpdated = new HashSet<>();
        isPD = false;
        init();
    }

    private void init() {
        setMinimumSize(MINIMUM_SIZE);
        setPreferredSize(PREFERRED_SIZE);

        // loop through arrows and ensure concrete arrows are completely defined (outermost arrows won't be)
        if (circleMap.keySet().size() > 0) {
            for (ConcreteArrow a : arrows) {
                int parentId = a.getParentId();
                String source = a.getAbstractArrow().getSourceLabel();
                if (a.getSource() == null || a.getTarget() == null) {
                    toBeUpdated.add(a);
                    if (existingArrowCount.containsKey(source)) {
                        existingArrowCount.put(source + parentId, existingArrowCount.get(source + parentId) + 1);
                    } else {
                        existingArrowCount.put(source + parentId, 1);
                    }
                } else {
                    a.init();
                }
            }
            updateOutermostArrows();
        }

        if (circleMap.keySet().size() > 0) {
            for (ConcreteEquality equality : equalities) {
                if (equality.getSource() == null || equality.getTarget() == null) {
                    String source = equality.getAbstractEquality().getArg1();
                    String target = equality.getAbstractEquality().getArg2();
                    equality.setSource(circleMap.get(equality.getParentId()).get(source));
                    equality.setTarget(circleMap.get(equality.getParentId()).get(target));
                }
            }
        }
    }

    private void updateOutermostArrows() {
        if (circleMap.keySet().size() > 0) {
            for (ConcreteArrow a : toBeUpdated) {
                String source = a.getAbstractArrow().getSourceLabel();
                String target = a.getAbstractArrow().getTargetLabel();
                Ellipse2D.Double sourceEllipse = null;
                Ellipse2D.Double targetEllipse = null;

                // outermost arrows: here need to assign source and target such that there are no cycles, initial t is source only
                for (HashMap<String, Ellipse2D.Double> val : circleMap.values()) {
                    if (val.containsKey(source)) {
                        sourceEllipse = val.get(source);
                    }
                    if (val.containsKey(target)) {
                        targetEllipse = val.get(target);
                    }
                }

                // if pd, all t arrows sourced from one cop (id 0)
                if (isPD && source.equals("t")) {
                    System.out.println("setting pd t");
                    sourceEllipse = circleMap.get(0).get("t");
                }

                // check if source and target set by user
                if (a.getAbstractArrow().getSourceId() != 0) {
                    sourceEllipse = circleMap.get(a.getAbstractArrow().getSourceId()).get(source);
                }
                if (a.getAbstractArrow().getTargetId() != 0) {
                    targetEllipse = circleMap.get(a.getAbstractArrow().getTargetId()).get(target);
                }

                if (sourceEllipse != null & targetEllipse != null) {
                    a.setSource(sourceEllipse);
                    a.setTarget(targetEllipse);
                    a.init();
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.translate(0, 22);
        g.setColor(new Color(10, 86, 0, 255));
        g.setFont(new Font("Courier", Font.PLAIN, 12));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (arrows != null && circleMap != null && circleMap.keySet().size() > 0) {
            updateOutermostArrows();
            for (ConcreteArrow a : arrows) {
                int parentId = a.getParentId();
                String source = a.getAbstractArrow().getSourceLabel();
                a.init();
                if (existingArrowCount.containsKey(source + parentId)) {
                    a.shiftControl(30 * Math.pow(-1, existingArrowCount.get(source + parentId)));
                }

                if (a.getAbstractArrow().isAnon()) {
                    g2d.setStroke(dashed);
                }
                g2d.draw(a.getCurve());
                g2d.setStroke(new BasicStroke());

                // draw arrowheads for target
                g2d.draw(a.getArrowhead(1));
                g2d.draw(a.getArrowhead(-1));

                // draw label
                g2d.drawString(a.getLabel(), a.getCurveLabelX(), a.getCurveLabelY());
                g2d.drawString(a.getCardinality(), a.getCardinalityLabelX(), a.getCardinalityLabelY());
            }
        }

        if (circleMap != null && circleMap.keySet().size() > 0) {
            for (ConcreteEquality equality : equalities) {
                equality.init();
                g2d.drawString("=", equality.getX(), equality.getY());
                if (!equality.getAbstractEquality().isKnown()) {
                    g2d.drawString("?", equality.getX(), equality.getSecondaryY());
                }
            }
        }
    }

    double getScore() {
        double score = 0;
        for (ConcreteArrow a : arrows) {
            score += a.getScore();
        }
        return score;
    }
}
