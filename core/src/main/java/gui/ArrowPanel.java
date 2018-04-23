package gui;

import concrete.ConcreteArrow;
import concrete.ConcreteEquality;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import static util.GraphicsHelper.*;

public class ArrowPanel extends JComponent {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);
    private static float dash[] = {5.0f};
    private static final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
    private static final Font basicFont =  new Font("Courier", Font.PLAIN, 18);
    private static final Font smallFont = new Font("Courier", Font.PLAIN, 15);
    private static final int PENALTY = 1000;

    private Set<ConcreteArrow> arrows;
    private Set<ConcreteEquality> equalities;
    private HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap;
    private List<Ellipse2D.Double> dotList;
    private HashMap<String, Integer> existingArrowCount;
    private HashMap<String, Integer> offsets;
    private Set<ConcreteArrow> toBeUpdated;
    boolean isPD;

    ArrowPanel() {
        this.arrows = new HashSet<>();
        this.equalities = new HashSet<>();
        this.circleMap = new HashMap<>();
        this.existingArrowCount = new HashMap<>();
        this.offsets = new HashMap<>();
        this.toBeUpdated = new HashSet<>();
        isPD = false;
    }

    ArrowPanel(Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities, HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap) {
        this.arrows = arrows;
        this.equalities = equalities;
        this.circleMap = circleMap;
        this.existingArrowCount = new HashMap<>();
        this.offsets = new HashMap<>();
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
                    if (existingArrowCount.containsKey(source+parentId)) {
                        existingArrowCount.put(source + parentId, existingArrowCount.get(source + parentId) + 1);
                    } else {
                        existingArrowCount.put(source + parentId, 1);
                    }
                } else {
                    a.init();
                }
            }
            updateOutermostArrows();
            calculateOffsets();
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

    private void calculateOffsets() {
        for (String src: existingArrowCount.keySet()) {
            int offset = (int) Math.floor(existingArrowCount.get(src)/2)*-1;
            offsets.put(src, offset);
        }
    }

    private void generateDotList() {
        dotList = new ArrayList<>();
        for (HashMap<String, Ellipse2D.Double> map: circleMap.values()) {
            dotList.addAll(map.values());
        }
    }

    private void shiftControl(ConcreteArrow arrow, Ellipse2D.Double ellipse) {
        /* Force-directed method: repulsive force between arrow's
         control point and the contour. For two point u, v
        Fx(u,v) = -delta^2/d(u,v)^2 * (x(v) - x(u)).
        where  - d(u,v) is the Euclidean distance between points u and v
               - delta is the length we would like to obtain
               - x(u) is the x position of a point u.
        */
        QuadCurve2D curve = arrow.getCurve();
        double radius = ellipse.getHeight()/2;
        double euclideanDist, Fx, Fy, lambda, delta;

//        lambda = radius/2; // want edge at least beyond circle perimeter.
//        // find projection of ellipse center onto line
//        // first define line ax + bx + c = 0
//        double a = getGradient(curve.getX1(), curve.getY1(), curve.getX2(), curve.getY2());
//        if (curve.intersects(ellipse.getX() - radius, ellipse.getY() - radius, radius*2, radius*2)) {
//            double b = -1;
//            double c = getC(a, curve.getX1(), curve.getY1());
//            // projected point
//            double x = (b * (b * ellipse.getX() - a * ellipse.getY()) - (a * c)) / (Math.pow(a, 2) + Math.pow(b, 2));
//            double y = (a * (-b * ellipse.getX() + a * ellipse.getY()) - (b * c)) / (Math.pow(a, 2) + Math.pow(b, 2));
//            // calculate forces
//            euclideanDist = new Point((int) x, (int) y).distance(ellipse.getX(), ellipse.getY());
//            if (euclideanDist < lambda) {
//                Fx = ((-Math.pow(lambda - euclideanDist, 2))* (x - ellipse.x)) / euclideanDist ;
//                Fy = ((-Math.pow(lambda - euclideanDist, 2))* (-y + ellipse.y)) / euclideanDist ;
//                arrow.shiftControl((int)Fx, (int)Fy);
//            }
//        }

        delta = 3*ellipse.getHeight()/4; // want control point at least beyond circle perimeter.
        euclideanDist = new Point((int) curve.getCtrlX(), (int) curve.getCtrlY()).distance(ellipse.getX(), ellipse.getY());
        Fx = - Math.pow(delta, 2) / Math.pow(euclideanDist, 2) * (curve.getCtrlX() - ellipse.getX());
        Fy = - Math.pow(delta, 2) / Math.pow(euclideanDist, 2) * (-curve.getCtrlY() + ellipse.getY());

        // then apply the force to the control point
        // work done = force * distance. We fix work done to 20J
        arrow.shiftControl((int) Fx, (int) Fy);

        // force directed method helps when ctrl point is near to ellipse, if between two, correct for this:
        if (curve.intersects(ellipse.getX() - radius, ellipse.getY() - radius, radius*2, radius*2)) {
            double amountX, amountY;
            if (curve.getCtrlY() > ellipse.getY()) {
                amountY = radius;
            } else {
                amountY = radius * -1;
            }

            if (curve.getCtrlX() > ellipse.getX()) {
                amountX = radius;

            } else {
                amountX = radius * -1;
            }
            arrow.shiftControl((int)amountX, (int)amountY);
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
        g.setFont(basicFont);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (arrows != null && circleMap != null && circleMap.keySet().size() > 0) {
            generateDotList();
            updateOutermostArrows();
            for (ConcreteArrow a : arrows) {
                int parentId = a.getParentId();
                String source = a.getAbstractArrow().getSourceLabel();
                a.init();
                if (existingArrowCount.containsKey(source + parentId)) {
                    a.shiftY(offsets.get(source + parentId));
                    offsets.put(source + parentId, offsets.get(source + parentId) + 1);
                }

                // check for edge crossings with contours and correct
                for (Ellipse2D.Double dot: dotList) {
                    Rectangle2D.Double dotBoundary = new Rectangle2D.Double(dot.getX() - dot.getHeight()/2, dot.getY() - dot.getHeight(), dot.getHeight(), dot.getHeight());
                    Rectangle2D.Double targetBoundary = new Rectangle.Double(a.getTarget().getX() - a.getTarget().getHeight()/2, a.getTarget().getY() - a.getTarget().getHeight(), a.getTarget().getHeight(), a.getTarget().getHeight());

                    // if the target is within the contour, intersection is necessary
                    // otherwise shift control of arrow
                    if (dot != a.getTarget() && dot != a.getSource() && !dotBoundary.contains(targetBoundary)) { // allowed to touch src + target
                        shiftControl(a, dot);
                  }
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
                g.setFont(smallFont);
                g2d.drawString(a.getCardinality(), a.getCardinalityLabelX(), a.getCardinalityLabelY());
                g.setFont(basicFont);
            }
            calculateOffsets();
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

        // then penalise (quadcurve) arrows crossing each other
        for (ConcreteArrow a1: arrows) {
            for(ConcreteArrow a2: arrows) {
                if (curvesIntersect(a1.getCurve(), a2.getCurve())) {
                    score += PENALTY;
                }

                // also add extra penalty for straight lines intersecting to encourage linear diagrams
                QuadCurve2D c1 = a1.getCurve();
                QuadCurve2D c2 = a2.getCurve();
                Line2D.Double l1 = new Line2D.Double(c1.getX1(), c1.getY1(), c1.getX2(), c1.getY2());
                Line2D.Double l2 = new Line2D.Double(c2.getX1(), c2.getY1(), c2.getX2(), c2.getY2());
                if (a1 != a2 && l1.intersectsLine(l2)) {
                    score += PENALTY*10;
                }
            }
        }
        return score;
    }
}
