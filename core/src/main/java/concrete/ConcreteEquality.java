package concrete;

import abstractDescription.AbstractEquality;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

import static util.GraphicsHelper.getClosestPoints;

public class ConcreteEquality {
    private AbstractEquality equality;
    private int parentId;
    private Ellipse2D.Double source;
    private Ellipse2D.Double target;

    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double midX;
    private double midY;

    public ConcreteEquality(AbstractEquality equality, int parentId) {
        this.parentId = parentId;
        this.equality = equality;
    }

    public AbstractEquality getAbstractEquality() {
        return equality;
    }

    public int getParentId() {
        return parentId;
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

    public void init() {
        List<Point2D.Double> intersections = getClosestPoints(source, target);
        x1 = intersections.get(0).x;
        y1 = intersections.get(0).y;
        x2 = intersections.get(1).x;
        y2 = intersections.get(1).y;
        midX = x1 + (x2 - x1) / 2;
        midY = (Math.min(y1, y2) + Math.abs(y2 - y1) / 2);
    }

    public int getX() {
        return (int) midX - 5;
    }

    public int getY() {
        return (int) midY + 6;
    }

    public int getSecondaryY() {
        return (int) midY - 5;
    }
}
