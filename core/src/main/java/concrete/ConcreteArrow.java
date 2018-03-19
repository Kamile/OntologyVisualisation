package concrete;

import abstractDescription.AbstractArrow;

import java.awt.geom.Point2D;

public class ConcreteArrow {
    private AbstractArrow abstractArrow;
    private long parentHashCode;
    private Point2D.Double source;
    private Point2D.Double target;
    private Point2D.Double control;

    ConcreteArrow(AbstractArrow abstractArrow, long parentHashCode) {
        this.abstractArrow = abstractArrow;
        this.parentHashCode = parentHashCode;
        this.source = new Point2D.Double();
        this.target = new Point2D.Double();
        this.control = new Point2D.Double();
    }

    public AbstractArrow getAbstractArrow() {
        return abstractArrow;
    }

    public long getParentHashCode() {
        return parentHashCode;
    }

    public void translatePoint(Point2D.Double inPoint, Point2D.Double outPoint) {
        outPoint.x = inPoint.x;
        outPoint.y = inPoint.y;
    }

    public String getLabel() {
        return abstractArrow.getLabel().getLabel();
    }

    public String getCardinality() {
        return abstractArrow.getLabel().getCardinalityOperator() + abstractArrow.getLabel().getCardinalityArgument();
    }

    public Point2D.Double getSource() {
        return source;
    }

    public Point2D.Double getTarget() {
        return target;
    }

    public Point2D.Double getControl() {
        return control;
    }

    public double checksum() {
        double result = 0.0;
        return result;
    }
}
