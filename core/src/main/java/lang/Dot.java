package lang;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Comparator;

public class Dot implements Comparator<Dot>, Serializable {
    private Ellipse2D.Double dot;
    private String label;
    private int parentHashCode;

    public Dot(Ellipse2D.Double dot, String label, int parentHashCode) {
        this.dot = dot;
        this.label = label;
        this.parentHashCode = parentHashCode;
    }

    public Point2D.Double closestIntersectionTo(Dot d) {
        return new Point2D.Double();
    }

    @Override
    public int compare(Dot o1, Dot o2) {
        if (o1.label.equals(o2.label)) {
            return o1.parentHashCode < o2.parentHashCode ? -1 : 1;
        } else {
            return o1.label.compareTo(o2.label);
        }
    }
}
