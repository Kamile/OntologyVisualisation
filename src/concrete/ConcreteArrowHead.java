package concrete;

import org.apache.log4j.Logger;

import java.awt.geom.Ellipse2D;

/**
 * Arrows sourced from individual or class and can has a class or individual
 * as target.
 * We need to know the direction of the arrow to know how to draw the arrow head.
 *
 */
public class ConcreteArrowHead {
    Logger logger = Logger.getLogger(ConcreteArrowHead.class.getName());
    static double theta = Math.PI/6;
    private double x;
    private double y;
    private ConcreteArrow arrow;

    public ConcreteArrowHead(double x, double y) {
        this.x = x;
        this.y = y;
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

    public ConcreteArrow getArrow() {
        return arrow;
    }

    void setArrow(ConcreteArrow arrow) {
        this.arrow = arrow;
    }

    // Return a polyline for the vee
    public void getArrowHead() {

    }

    /**
     * Set coordinates for given arrowHead
     */
    public void getArrowHead(Ellipse2D.Double arrowHead) {
        arrowHead.x = x;
        arrowHead.y = y;
    }

    public double checksum() {
        logger.debug("build checksum for arrowhead from coords ("
                + x + ", " + y + ")\n");
        return x + 1.03 * y;
    }
}
