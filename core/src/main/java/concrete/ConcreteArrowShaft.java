package concrete;

import org.apache.log4j.Logger;

import java.awt.*;

/**
 * Arrows represent properties.
 * Where arrow targets are anonymous, properties can be represented
 * as anonymous by using a dashed line.
 */
public class ConcreteArrowShaft {
    final static float dash[] = {5.0f};
    final static BasicStroke dashed =  new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash, 0.0f);

    private ConcreteArrowHead from;
    private ConcreteArrowHead to;
    private boolean isAnonymous;

    public ConcreteArrowHead getFrom() {
        return from;
    }

    public ConcreteArrowHead getTo() {
         return to;
    }

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    public double checksum() {
        return 1.2 * from.checksum() + 2.2 * to.checksum();
    }
}
