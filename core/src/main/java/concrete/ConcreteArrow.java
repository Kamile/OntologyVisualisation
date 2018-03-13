package concrete;

import abstractDescription.AbstractArrow;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class ConcreteArrow {
    private AbstractArrow arrow;

    ConcreteArrow(AbstractArrow arrow) {
        this.arrow = arrow;
    }

    public double checksum() {
        double result = 0.0;
        return result;
    }
}
