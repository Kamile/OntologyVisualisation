package gui;

import concrete.ConcreteArrow;
import concrete.ConcreteEquality;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ArrowPanel extends JComponent {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);
    private static final double CHAR_WIDTH = 8;
    private static float dash[] = {10.0f};
    private static final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
    private static final int headLength = 15;
    private static final double phi = Math.PI/6;

    private Set<ConcreteArrow> arrows;
    private Set<ConcreteEquality> equalities;
    private HashMap<String, Ellipse2D.Double> circleMap;
    private HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap2;
    private HashMap<ConcreteArrow, Double> arrowOffsets;
    private HashMap<String, Integer> existingArrowCount;

    ArrowPanel() {
        this(null, null, null);
    }

    ArrowPanel(Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities, HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap) {
        this.arrows = arrows;
        this.equalities = equalities;
        this.circleMap = new HashMap<>();
        this.circleMap2 = circleMap;
        this.existingArrowCount = new HashMap<>();
        if (arrows != null) {
            arrowOffsets = new HashMap<>();
            for (ConcreteArrow a : arrows) {
                arrowOffsets.put(a, getRandomOffset());
            }
        }
        initComponents();
    }

    private void initComponents() {
        setMinimumSize(MINIMUM_SIZE);
        setPreferredSize(PREFERRED_SIZE);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.translate(0, 22);
        g.setColor(new Color(10, 86, 0, 255));
        g.setFont(new Font("Courier", Font.PLAIN, 12));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (arrows != null && circleMap2.keySet().size() > 0) {
            super.paintComponent(g);
            for (ConcreteArrow a: arrows) {
                String label = a.getLabel();
                String cardinality = a.getCardinality();
                String source = a.getAbstractArrow().getSourceLabel();
                String target = a.getAbstractArrow().getTargetLabel();

                List<Point2D.Double> intersections;
                if (a.getParentId() == -1) {
//                    break; // here need to assign source and target such that there are no cycles, initial t is source only
                    for (HashMap<String, Ellipse2D.Double> val: circleMap2.values()) {
                        circleMap.putAll(val);
                    }
                    intersections = getClosestPoints(circleMap.get(source), circleMap.get(target));

                } else {
                    intersections = getClosestPoints(circleMap2.get(a.getParentId()).get(source), circleMap2.get(a.getParentId()).get(target));
                }

                double x1 = intersections.get(0).x;
                double y1 = intersections.get(0).y;
                double x2 = intersections.get(1).x;
                double y2 = intersections.get(1).y;
                applyOffsets(getGradient(x1,y1,x1,y2), x1, y1, x2, y2);

                double midX = x1 + (x2-x1)/2;
                double midY = (Math.min(y1, y2) + Math.abs(y2 - y1)/2);

                //get random control point
                double cx = midX - arrowOffsets.get(a);
                double cy = midY - arrowOffsets.get(a);

                if (existingArrowCount.containsKey(source)) {
                    cy += 30*Math.pow(-1, existingArrowCount.get(source));
                }

                QuadCurve2D curve = new QuadCurve2D.Double();
                curve.setCurve(x1, y1, cx, cy, x2, y2);
                if (a.getAbstractArrow().isAnon()) {
                    g2d.setStroke(dashed);
                }
                g2d.draw(curve);
                g2d.setStroke(new BasicStroke());

                // draw arrowheads for target
                double theta = Math.atan2(y2 - cy, x2 - cx);

                double x = x2 - headLength * Math.cos(theta + phi);
                double y = y2 - headLength * Math.sin(theta + phi);
                g2d.drawLine((int) x2, (int) y2, (int) x, (int) y);

                x = x2 - headLength * Math.cos(theta - phi);
                y = y2 - headLength * Math.sin(theta - phi);
                g2d.drawLine((int) x2, (int) y2, (int) x, (int) y);

                if (theta < 5) {
                    g2d.drawString(label, (int) (midX), (int) (cy  + 7));
                    g.setFont(new Font("Courier", Font.PLAIN, 8));
                    g2d.drawString(cardinality, (int) (midX + label.length() * CHAR_WIDTH), (int) (cy  + 9));
                    g.setFont(new Font("Courier", Font.PLAIN, 12));
                } else {
                    g2d.drawString(label, (int) (midX), (int) (midY - 15));
                    g.setFont(new Font("Courier", Font.PLAIN, 8));
                    g2d.drawString(label, (int) (midX + label.length() * CHAR_WIDTH), (int) (midY - 12));
                    g.setFont(new Font("Courier", Font.PLAIN, 12));
                }

                if (existingArrowCount.containsKey(source)) {
                    existingArrowCount.put(source, existingArrowCount.get(source) + 1);
                } else {
                    existingArrowCount.put(source, 1);
                }
            }
        }

        if (equalities != null && circleMap2.keySet().size() > 0) {
            super.paintComponent(g);

            for(ConcreteEquality equality: equalities) {
                String source = equality.getAbstractEquality().getArg1();
                String target = equality.getAbstractEquality().getArg2();
                List<Point2D.Double> intersections = getClosestPoints(circleMap2.get(equality.getParentId()).get(source), circleMap2.get(equality.getParentId()).get(target));

                double x1 = intersections.get(0).x;
                double y1 = intersections.get(0).y;
                double x2 = intersections.get(1).x;
                double y2 = intersections.get(1).y;

                double midX = x1 + (x2-x1)/2;
                double midY = (Math.min(y1, y2) + Math.abs(y2 - y1)/2);
                g2d.drawString("=", (int) midX, (int) (midY + 6));
                if (!equality.getAbstractEquality().isKnown()) {
                    g2d.drawString("?", (int) midX, (int) (midY-5));
                }
            }
        }
    }

    private void applyOffsets(double gradient, double x1, double y1, double x2, double y2) {

    }

    private static double getRandomOffset() {
        return Math.floor(Math.random()*31 + 10);
    }

    private static List<Point2D.Double> getClosestPoints(Ellipse2D source, Ellipse2D target) {
        // array holds source intersection then target intersection
        List<Point2D.Double> intersections = new ArrayList<>();

        // define line
        double x1 = source.getX();
        double x2 = target.getX();
        double y1 = source.getY();
        double y2 = target.getY();
        double gradient = getGradient(x1, y1 , x2, y2);
        double c = getC(gradient, x1, y1);

        // circles defined by same centre points + radius
        double sourceRadius = source.getWidth()/2.0;
        double targetRadius = target.getWidth()/2.0;

        Point2D.Double sourcePositive;
        Point2D.Double sourceNegative;
        Point2D.Double targetPositive;
        Point2D.Double targetNegative;

        // find intersection between source circle and line
        if (gradient == Double.MAX_VALUE) {
            sourcePositive = findPoint(sourceRadius, x1, y1, true);
            sourceNegative = findPoint(sourceRadius, x1, y1, false);
            targetPositive = findPoint(targetRadius, x2, y2, true);
            targetNegative = findPoint(targetRadius, x2, y2, false);
        } else {
            sourcePositive = findPoint(gradient, c, sourceRadius,  x1, y1, true);
            sourceNegative = findPoint(gradient, c, sourceRadius, x1, y1, false);
            targetPositive = findPoint(gradient, c, targetRadius,  x2, y2, true);
            targetNegative = findPoint(gradient, c, targetRadius, x2, y2, false);
        }

        Point2D.Double closestSource;
        Point2D.Double closestTarget;

        // if the source or target is a spider
        if (sourceRadius < 5 && targetRadius < 5) {
            closestSource = new Point2D.Double(x1, y1);
            closestTarget = new Point2D.Double(x2, y2);
        } else if (sourceRadius < 5) {
            closestSource = new Point2D.Double(x1,y1);

            if (targetPositive == null & targetNegative == null) {
            }

            if (closestSource.distance(targetPositive) < closestSource.distance(targetNegative)) {
                closestTarget = targetPositive;
            } else {
                closestTarget = targetNegative;
            }
        } else if (targetRadius < 5) {
            if (targetPositive == null & targetNegative == null) {
            }
            closestTarget = new Point2D.Double(x2, y2);
            if (closestTarget.distance(sourcePositive) < closestTarget.distance(sourceNegative)) {
                closestSource = sourcePositive;
            } else {
                closestSource = sourceNegative;
            }

        } else {
            // return points that give the smallest distance between circles
            closestSource = sourcePositive;
            closestTarget = targetPositive;

            if (sourceNegative != null && targetPositive != null && sourceNegative.distance(targetPositive) < closestSource.distance(closestTarget)) {
                closestSource = sourceNegative;
                closestTarget = targetPositive;
            }

            if (sourcePositive != null && targetNegative != null && sourcePositive.distance(targetNegative) < closestSource.distance(closestTarget)) {
                closestSource = sourcePositive;
                closestTarget = targetNegative;
            }

            if (sourceNegative != null && targetNegative != null && sourceNegative.distance(targetNegative) < closestSource.distance(closestTarget)) {
                closestSource = sourceNegative;
                closestTarget = targetNegative;
            }
        }

        intersections.add(closestSource);
        intersections.add(closestTarget);
        return intersections;
    }

    private static double getC(double gradient, double x, double y) {
        if (gradient < 1 && gradient > -1) {
            return y;
        }
        return y - gradient*x;
    }

    private static double getGradient(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = -(y2 - y1); // y is 0 at top

        if (deltaX < 1 && deltaX > -1) {
            return Double.MAX_VALUE;
        } else if (deltaY < 1 && deltaY > -1) {
            return 0;
        }
        return deltaY/deltaX;
    }

    private static double solveForPositiveDiscriminant(double a, double b, double c) {
        if (b*b-4*a*c >= 0) {
            return (-b + Math.sqrt((b * b) - (4 * a * c))) / (2 * a);
        } else {
            throw new NumberFormatException();
        }
    }

    private static double solveForNegativeDiscriminant(double a, double b, double c) {
        if (b*b-4*a*c >= 0) {
            return (-b - Math.sqrt((b * b) - (4 * a * c))) / (2 * a);
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * Solve for Y where gradient and intercept are INFINITY
     * @param radius
     * @param x1
     * @param y1
     * @param positive
     * @return
     */
    private static Point2D.Double findPoint(double radius, double x1, double y1, boolean positive) {
        double a = 1;
        double b = -2 * y1;
        double c =  Math.pow((y1), 2) - Math.pow(radius, 2);

        double y;

        try {
            if (positive) {
                y = solveForPositiveDiscriminant(a, b, c);
            } else {
                y = solveForNegativeDiscriminant(a, b, c);
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return new Point2D.Double(x1, y);
    }

    // Find coordinates of intersection where we take either positive or negative root in quadratic equation
    private static Point2D.Double findPoint(double gradient, double intercept, double radius, double x1, double y1, boolean positive)  {
        double a = Math.pow(gradient, 2) + 1;
        double b = 2 * gradient * (intercept - y1) - 2 * x1;
        double c = Math.pow(x1, 2) + Math.pow((intercept - y1), 2) - Math.pow(radius, 2);

        double x;

        try {
            if (positive) {
                x = solveForPositiveDiscriminant(a, b, c);
            } else {
                x = solveForNegativeDiscriminant(a, b, c);
            }
        } catch (NumberFormatException e) {
            return null;
        }

        double y = gradient*x + intercept;
        return new Point2D.Double(x, y);
    }
}
