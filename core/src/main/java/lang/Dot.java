package lang;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;

import static speedith.i18n.Translations.i18n;

/**
 * Class encapsulating contours and dots (literals and individuals)
 * - @dot member defines ellipse
 * - @label defines name for contour/literal/individual
 * - @parentId defines id given to COP or DT in which this dot/contour resides
 * - @initialT defines whether this dot is the single variable t for property diagrams. This means it will be
 * the source of all arrows
 */
public class Dot implements Comparator<Dot>, Serializable {
    private Ellipse2D.Double dot;
    private String label;
    private int parentId;
    private boolean initialT;

    public Dot(Ellipse2D.Double dot, String label, int parentId) {
        this.dot = dot;
        this.label = label;
        this.parentId = parentId;
        this.initialT = false;
    }

    public void setAsInitialT() {
        initialT = true;
    }

    public Point2D.Double closestIntersectionTo(Dot d) {
        return new Point2D.Double();
    }

    @Override
    public int compare(Dot o1, Dot o2) {
        if (o1.label.equals(o2.label)) {
            return o1.parentId < o2.parentId ? -1 : 1;
        } else {
            return o1.label.compareTo(o2.label);
        }
    }

    private void toString(Appendable sb) {
        try {
            if (sb == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
            }
            sb.append(label);
            sb.append(" in dot with id ");
            sb.append(String.valueOf(parentId));
            sb.append("\n Ellipse: \n\tcx = ");
            sb.append(String.valueOf(dot.x));
            sb.append("\n\tcy = ");
            sb.append(String.valueOf(dot.y));
            sb.append("\n\tradius = ");
            sb.append(String.valueOf(dot.getHeight()/2));
            sb.append(initialT ? "\n and represents single variable T" : "");
            sb.append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }
}
