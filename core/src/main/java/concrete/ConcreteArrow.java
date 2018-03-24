package concrete;

import abstractDescription.AbstractArrow;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class ConcreteArrow {
    private AbstractArrow abstractArrow;
    private int parentId;
    private Ellipse2D.Double source;
    private Ellipse2D.Double target;
    private Point2D.Double control;

    ConcreteArrow(AbstractArrow abstractArrow, int parentId) {
        this.abstractArrow = abstractArrow;
        this.parentId = parentId;
        this.source = new Ellipse2D.Double();
        this.target = new Ellipse2D.Double();
        this.control = new Point2D.Double();
        System.out.println(parentId);
    }

    public AbstractArrow getAbstractArrow() {
        return abstractArrow;
    }

    public String getId() {
        return abstractArrow.getLabel() + String.valueOf(parentId);
    }

    public int getParentId() {
        return parentId;
    }

    public void translateControlPoint(Point2D.Double inPoint, Point2D.Double outPoint) {
        outPoint.x = inPoint.x;
        outPoint.y = inPoint.y;
    }

    public String getLabel() {
        return abstractArrow.getLabel().getLabel();
    }

    public String getCardinality() {
        return abstractArrow.getLabel().getCardinalityOperator() + abstractArrow.getLabel().getCardinalityArgument();
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

    public Point2D.Double getControl() {
        return control;
    }

    public double checksum() {
        double result = 0.0;
        return result;
    }
}
