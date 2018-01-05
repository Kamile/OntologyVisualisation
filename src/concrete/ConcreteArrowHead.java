package concrete;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Arrows sourced from individual or class and can has a class or individual
 * as target.
 * We need to know the direction of the arrow to know how to draw the arrow head.
 *
 */
public class ConcreteArrowHead {
    Logger logger = Logger.getLogger(ConcreteArrowHead.class.getName());

    private static double ARROW_ANGLE = Math.PI/2;
    private static final double HEAD_LENGTH = 20;
    private static final BasicStroke stroke =  new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f);

    private double x;
    private double y;
    private ConcreteArrow arrow;
    private boolean isTarget; // target has arrowhead, source doesn't

    public ConcreteArrowHead(double x, double y, boolean isTarget) {
        this.x = x;
        this.y = y;
        this.isTarget = isTarget;
    }

    public double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    void setY(double y) {
        this.y = y;
    }

    public boolean getIsTarget() {
        return isTarget;
    }

    void setIsTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }

    public ConcreteArrow getArrow() {
        return arrow;
    }

    void setArrow(ConcreteArrow arrow) {
        this.arrow = arrow;
    }

    // Return a polyline for the vee
    public Graphics2D getArrowHead() {
        if (isTarget) {

        }
        return null;

    }

    /**
     * Set coordinates for given arrowHead
     */
    public void getArrowHead(Graphics2D g2) {
        if (!isTarget) {
            return;
        }
    }

    public double checksum() {
        logger.debug("build checksum for arrowhead from coords ("
                + x + ", " + y + ")\n");
        return x + 1.03 * y;
    }
}
