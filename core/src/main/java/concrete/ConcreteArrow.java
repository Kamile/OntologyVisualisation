package concrete;

import abstractDescription.AbstractArrow;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class ConcreteArrow {

    static Logger logger = Logger.getLogger(ConcreteArrow.class.getName());

    private AbstractArrow arrow;
    private ArrayList<ConcreteArrowShaft> shafts;
    private ArrayList<ConcreteArrowHead> arrowHeads;

    public ConcreteArrow(AbstractArrow arrow) {
        this.arrow = arrow;
        shafts = new ArrayList<ConcreteArrowShaft>();
        arrowHeads = new ArrayList<ConcreteArrowHead>();
    }

    public double checksum() {
        double result = 0.0;

        for (ConcreteArrowHead head: arrowHeads) {
            logger.debug("build checksum for arrow head\n");
            result += head.checksum();
        }

        for (ConcreteArrowShaft shaft: shafts) {
            logger.debug("build checksum for shafts\n");
            result += shaft.checksum();
        }
        return result;
    }

}
