package concrete;

import org.apache.log4j.Logger;

import java.awt.*;

public class ConcreteArrowTarget extends ConcreteArrowEnd {
    Logger logger = Logger.getLogger(ConcreteArrowTarget.class.getName());

    private static double ARROW_ANGLE = Math.PI/2;
    private static final double HEAD_LENGTH = 20;
    private static final BasicStroke stroke =  new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f);

    public ConcreteArrowTarget(double x, double y) {
        super(x,y);
    }

    public double checksum() {
        logger.debug("build checksum for arrowhead from coords ("
                + super.getX() + ", " + super.getY() + ")\n");
        return super.getX() + 1.03 * super.getY();
    }
}
