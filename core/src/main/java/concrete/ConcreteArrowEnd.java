package concrete;

public class ConcreteArrowEnd {
    private double x;
    private double y;
    private ConcreteArrow arrow;

    public ConcreteArrowEnd(double x, double y) {
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
}
