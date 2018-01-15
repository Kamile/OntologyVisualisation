package concrete;

import org.apache.log4j.Logger;

import java.awt.*;

public class ConcreteArrowTarget {
    Logger logger = Logger.getLogger(ConcreteArrowTarget.class.getName());

    private static double ARROW_ANGLE = Math.PI/2;
    private static final double HEAD_LENGTH = 20;
    private static final BasicStroke stroke =  new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f);

    private double x;
    private double y;
    private ConcreteArrow arrow;

    public ConcreteArrowTarget(double x, double y) {
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

    public void setArrow(ConcreteArrow arrow) {
        this.arrow = arrow;
    }

    public double checksum() {
        logger.debug("build checksum for arrowhead from coords ("
                + x + ", " + y + ")\n");
        return x + 1.03 * y;
    }
}
