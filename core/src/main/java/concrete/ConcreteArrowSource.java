package concrete;

import org.apache.log4j.Logger;


public class ConcreteArrowSource extends ConcreteArrowEnd{
    Logger logger = Logger.getLogger(ConcreteArrowSource.class.getName());

    public ConcreteArrowSource(double x, double y) {
        super(x,y);
    }


    public double checksum() {
        logger.debug("build checksum for arrowhead from coords ("
                + super.getX() + ", " + super.getY() + ")\n");
        return super.getX() + 1.04 * super.getY();
    }
}
