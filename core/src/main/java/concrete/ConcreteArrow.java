package concrete;

import abstractDescription.AbstractArrow;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class ConcreteArrow {
    static Logger logger = Logger.getLogger(ConcreteArrow.class.getName());

    private AbstractArrow arrow;
    private ConcreteArrowSource source;
    private ConcreteArrowTarget target;

    public ConcreteArrow(AbstractArrow arrow) {
        this.arrow = arrow;
    }

    public ConcreteArrowSource getSource() {
        return source;
    }

    public void setSource(ConcreteArrowSource source) {
        this.source = source;
    }

    public ConcreteArrowTarget getTarget() {
        return target;
    }

    public void setTarget(ConcreteArrowTarget target) {
        this.target = target;
    }

    public AbstractArrow getAbstractArrow() {
        return arrow;
    }

    public double checksum() {
        double result = 0.0;
        result += source.checksum();
        result += target.checksum();
        return result;
    }
}
