package concrete;

import abstractDescription.AbstractArrow;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.List;

import static util.GraphicsHelper.getClosestPoints;
import static util.GraphicsHelper.getGradient;
import static util.GraphicsHelper.getLength;

public class ConcreteArrow {
    private static final int headLength = 15;
    private static final double phi = Math.PI / 6;
    private static final double CHAR_WIDTH = 8;

    private AbstractArrow abstractArrow;
    private int parentId;
    private Ellipse2D.Double source;
    private Ellipse2D.Double target;
    private Point2D.Double control;

    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double midX;
    private double midY;
    private double cx;
    private double cy;
    private double theta;
    private double randomOffset;

    ConcreteArrow(AbstractArrow abstractArrow, int parentId) {
        this.abstractArrow = abstractArrow;
        this.parentId = parentId;
        this.control = new Point2D.Double();
    }

    public AbstractArrow getAbstractArrow() {
        return abstractArrow;
    }

    // ID consists of parent id, arrow full label , source and target labels
    public String getId() {
        return parentId + abstractArrow.getLabel().getFullLabel() + abstractArrow.getSourceLabel() + abstractArrow.getTargetLabel();
    }

    public int getParentId() {
        return parentId;
    }

    public String getLabel() {
        return abstractArrow.getLabel().getProperty();
    }

    public String getCardinality() {
        return abstractArrow.getLabel().getCardinality();
    }

    public void setSource(Ellipse2D.Double source) {
        this.source = source;
    }

    public void setTarget(Ellipse2D.Double target) {
        this.target = target;
    }

    public Ellipse2D.Double getSource() {
        return source;
    }

    public Ellipse2D.Double getTarget() {
        return target;
    }

    public QuadCurve2D getCurve() {
        QuadCurve2D curve = new QuadCurve2D.Double();
        curve.setCurve(x1, y1, cx, cy, x2, y2);
        return curve;
    }

    public Line2D getArrowhead(int sign) {
        double x = x2 - headLength * Math.cos(theta + phi*sign);
        double y = y2 - headLength * Math.sin(theta + phi*sign);
        Line2D line = new Line2D.Double();
        line.setLine(x2,y2,x,y);
        return line;
    }

    public int getCurveLabelX() {
        return (int) midX;
    }

    public int getCurveLabelY() {
        if (theta < 5) {
            return (int) (cy + 7);
        } else {
            return (int) (midY - 15);
        }
    }

    public int getCardinalityLabelX() {
        return (int) (midX + getLabel().length() * CHAR_WIDTH);
    }

    public int getCardinalityLabelY() {
        if (theta < 5) {
            return (int) (cy + 9);
        } else {
            return (int) (midY - 12);
        }
    }

    public void shiftControl(double amount) {
        cx += amount;
    }

    public void init() {
        List<Point2D.Double> intersections = getClosestPoints(source, target);
        x1 = intersections.get(0).x;
        y1 = intersections.get(0).y;
        x2 = intersections.get(1).x;
        y2 = intersections.get(1).y;
        midX = x1 + (x2 - x1) / 2;
        midY = (Math.min(y1, y2) + Math.abs(y2 - y1) / 2);

        //get random control point
        randomOffset = getRandomOffset();
        cx = midX - randomOffset;
        cy = midY - randomOffset;
        theta = Math.atan2(y2 - cy, x2 - cx);
    }

    private static double getRandomOffset() {
        return Math.floor(Math.random() * 31 + 10);
    }

    /**
     Score used to choose optimal ordering for diagram panels. Minimal score means better ordering.
     We want to
     - minimise edge lengths (prefer diagram panels with adjacent arrow source and targets to be closer)
     - minimise edge crossings
     - give preference to left-to-right arrows

     */
    public double getScore() {
        return getLength(x1, y1, x2, y2) + getGradient(x1, y1, x2, y2) * -1; // positive gradients preferred
    }

    public double checksum() {
        double result = 0.0;
        return result;
    }
}
