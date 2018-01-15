package concrete;

import org.apache.log4j.Logger;


public class ConcreteArrowSource {
    Logger logger = Logger.getLogger(ConcreteArrowSource.class.getName());

    private double x;
    private double y;
    private ConcreteArrow arrow;

    public ConcreteArrowSource(double x, double y) {
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

    public double checksum() {
        logger.debug("build checksum for arrowhead from coords ("
                + x + ", " + y + ")\n");
        return x + 1.04 * y;
    }
}
