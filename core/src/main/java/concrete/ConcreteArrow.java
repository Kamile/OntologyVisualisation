package concrete;

import abstractDescription.AbstractArrow;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class ConcreteArrow {
    private AbstractArrow arrow;
    private double sourceX;
    private double sourceY;
    private double targetX;
    private double targetY;
    private double controlX;
    private double controlY;

    ConcreteArrow(AbstractArrow arrow) {
        this.arrow = arrow;
    }

    public void shiftTarget(double amount) {

    }

    public double checksum() {
        double result = 0.0;
        return result;
    }
}
