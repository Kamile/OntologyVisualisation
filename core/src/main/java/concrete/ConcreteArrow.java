package concrete;

import abstractDescription.AbstractArrow;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class ConcreteArrow {

    static Logger logger = Logger.getLogger(ConcreteArrow.class.getName());

    private AbstractArrow arrow;
    private ConcreteArrowSource source;
    private ConcreteArrowTarget target;
    private ConcreteArrowShaft shaft;

    public ConcreteArrow(AbstractArrow arrow) {
        this.arrow = arrow;
    }

    public ConcreteArrowSource getSource() {
        return source;
    }

    public void setSource(ConcreteArrowSource source) {
        this.source = source;
    }

    public void setTarget(ConcreteArrowTarget target) {
        this.target = target;
    }

    public double checksum() {
        double result = 0.0;
        result += source.checksum();
        result += target.checksum();
        result += shaft.checksum();
        return result;
    }
}
