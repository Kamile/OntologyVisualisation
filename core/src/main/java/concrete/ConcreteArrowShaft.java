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

    private ConcreteArrowSource source;
    private ConcreteArrowTarget target;
    private boolean isAnonymous;

    public ConcreteArrowShaft(ConcreteArrowSource source, ConcreteArrowTarget target) {
        this.source = source;
        this.target = target;
        isAnonymous = false;
    }

    public ConcreteArrowShaft(ConcreteArrowSource source, ConcreteArrowTarget target, boolean isAnon) {
        this.source = source;
        this.target = target;
        isAnonymous = isAnon;
    }

    public ConcreteArrowSource getSource() {
        return source;
    }

    public ConcreteArrowTarget getTarget() {
         return target;
    }

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    public double checksum() {
        return 1.2 * source.checksum() + 2.2 * target.checksum();
    }
}
