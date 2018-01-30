package gui;

import lang.Arrow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArrowPanel extends JComponent {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);
    private static float dash[] = {10.0f};
    private static final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
    private static final int headLength = 15;
    private static final double phi = Math.PI/6;

    private List<Arrow> arrows;
    private HashMap<String, Ellipse2D.Double> circleMap;
    private HashMap<Arrow, Double> arrowOffsets;

    ArrowPanel() {
        this(null, null);
    }

    ArrowPanel(List<Arrow> arrows, HashMap<String, Ellipse2D.Double> circleMap) {
        this.arrows = arrows;
        this.circleMap = circleMap;
        if (arrows != null) {
            arrowOffsets = new HashMap<>();
            for (Arrow a : arrows) {
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
        g.setFont(new Font("Courier", Font.PLAIN, 16));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (arrows != null && circleMap.keySet().size() > 0) {
            super.paintComponent(g);
            for (Arrow a: arrows) {
                String label = getLabel(a);
                String source = a.getSource();
                String target = a.getTarget();
                List<Point2D.Double> intersections = getClosestPoints(circleMap.get(source), circleMap.get(target));

                double x1 = intersections.get(0).x;
                double y1 = intersections.get(0).y;
                double x2 = intersections.get(1).x;
                double y2 = intersections.get(1).y;
                double gradient = getGradient(x1, y1, x2, y2);

                double midX = x1 + (x2-x1)*0.65;
//                double midX = (Math.min(x1, x2) + Math.abs(x2 - x1)/2);
                double midY = (Math.min(y1, y2) + Math.abs(y2 - y1)/2);

                //get random control point
                double cx = midX - arrowOffsets.get(a);
                double cy = midY - arrowOffsets.get(a);

                QuadCurve2D curve = new QuadCurve2D.Double();
                curve.setCurve(x1, y1, cx, cy, x2, y2);
                if (a.isDashed()) {
                    g2d.setStroke(dashed);
                }
                g2d.draw(curve);

                // draw arrowheads for target
                double theta = Math.atan2(y2 - cy, x2 - cx);
                double x = x2 - headLength * Math.cos(theta + phi);
                double y = y2 - headLength * Math.sin(theta + phi);
                g2d.drawLine((int) x2, (int) y2, (int) x, (int) y);

                x = x2 - headLength * Math.cos(theta - phi);
                y = y2 - headLength * Math.sin(theta - phi);
                g2d.drawLine((int) x2, (int) y2, (int) x, (int) y);

                // add label
                double offset;
                if (curve.getFlatness() < 20 || Math.abs(gradient) < 2) {
                    offset = 15;
                } else {
                    offset = curve.getFlatness()/(gradient*2);
                }

                // TODO: ensure text doesn't cover any components using check over rectangular region.
                g2d.drawString(label, (int) midX, (int) (midY - 2*offset));
            }
        }
    }

    private static double getRandomOffset() {
        return Math.floor(Math.random()*31 + 10);
    }

    private String getLabel(Arrow a) {
        String label = a.getLabel();

        if (a.getCardinalityOperator() != null) {
            label += " " + getOperator(a.getCardinalityOperator());
        }

        if (a.getCardinalityArgument() != 0) {
            label += " " + a.getCardinalityArgument();
        }

        return label;
    }

    private String getOperator(String operator) {
        operator = operator.toLowerCase();
        switch (operator) {
            case "leq":
                return "<=";
            case "greq":
                return ">=";
            default:
                return "";
        }
    }

    public List<Point2D.Double> getClosestPoints(Ellipse2D source, Ellipse2D target) {
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
        System.out.println("src circle: (x - " + x1 + ")^2 + (y - " + y1 + ")^2 = " + sourceRadius + "^2");
        System.out.println("target circle: (x - " + x2 + ")^2 + (y - " + y2 + ")^2 = " + targetRadius + "^2");
        System.out.println("line: y = " + gradient +"x + " + c);

        // find intersection between source circle and line
        Point2D.Double sourcePositive = findPoint(gradient, c, sourceRadius,  x1, y1, true);
        Point2D.Double sourceNegative = findPoint(gradient, c, sourceRadius, x1, y1, false);

        // find intersection between target circle and line
        Point2D.Double targetPositive = findPoint(gradient, c, targetRadius,  x2, y2, true);
        Point2D.Double targetNegative = findPoint(gradient, c, targetRadius, x2, y2, false);

        Point2D.Double closestSource;
        Point2D.Double closestTarget;

        // if the source or target is a spider
        if (sourceRadius < 5 && targetRadius < 5) {
            closestSource = new Point2D.Double(x1, y1);
            closestTarget = new Point2D.Double(x2, y2);
        } else if (sourceRadius < 5) {
            closestSource = new Point2D.Double(x1,y1);
            if (closestSource.distance(targetPositive) < closestSource.distance(targetNegative)) {
                closestTarget = targetPositive;
            } else {
                closestTarget = targetNegative;
            }
        } else if (targetRadius < 5) {
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

            if (sourceNegative != null&& targetNegative != null && sourceNegative.distance(targetNegative) < closestSource.distance(closestTarget)) {
                closestSource = sourceNegative;
                closestTarget = targetNegative;
            }
        }

        System.out.println(closestSource);
        System.out.println(closestTarget);

        intersections.add(closestSource);
        intersections.add(closestTarget);
        return intersections;
    }

    private double getC(double gradient, double x, double y) {
        if (gradient < 1 && gradient > -1) {
            return y;
        }
        return y - gradient*x;
    }

    private double getGradient(double x1, double y1, double x2, double y2) {
        double gradient = (y2-y1)/(x2-x1);
        if (gradient < 1 && gradient > -1) {
            return 0;
        }
        return gradient;
    }

    private double solveForXPositive(double a, double b, double c) {
        if (b*b-4*a*c >= 0) {
            return (-b + Math.sqrt((b * b) - (4 * a * c))) / (2 * a);
        } else {
            throw new NumberFormatException();
        }
    }

    private double solveForXNegative(double a, double b, double c) {
        if (b*b-4*a*c >= 0) {
            return (-b - Math.sqrt((b * b) - (4 * a * c))) / (2 * a);
        } else {
            throw new NumberFormatException();
        }
    }

    // Find coordinates of intersection where we take either positive or negative root in quadratic equation
    private Point2D.Double findPoint(double gradient, double intercept, double radius, double x1, double y1, boolean positive)  {
        double a;
        double b;
        double c;

        a = Math.pow(gradient, 2) + 1;
        b = 2 * gradient * (intercept - y1) - 2 * x1;
        c = x1 * x1 + Math.pow((intercept - y1), 2) - Math.pow(radius, 2);

        double x;

        try {
            if (positive) {
                x = solveForXPositive(a, b, c);
            } else {
                x = solveForXNegative(a, b, c);
            }
        } catch (NumberFormatException e) {
            System.err.println("Solution for x is imaginary");
            return null;
        }

        double y = gradient*x + intercept;
        return new Point2D.Double(x, y);
    }
}