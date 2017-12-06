package concrete;

import icircles.abstractDescription.AbstractCurve;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class BoxContour {

    static Logger logger = Logger.getLogger(BoxContour.class.getName());

    Rectangle2D.Double rectangle;
    double cx;
    double cy;
    double height;
    double width;
    double nudge = 0.1;

    Color colour;
    Stroke stroke;

    public AbstractCurve ac; //TODO: Do I need a repr for AbstractBox?

    public BoxContour(double cx, double cy, double height,
                      double width, AbstractCurve ac) {
        this.cx = cx;
        this.cy = cy;
        this.height = height;
        this.width = width;
        this.ac = ac;
        rectangle = makeRectangle(cx, cy, height, width);
    }

    public BoxContour(BoxContour bc) {
        this.cx = bc.cx;
        this.cy = bc.cy;
        this.height = bc.height;
        this.width = bc.width;
        this.colour = bc.colour;
        rectangle = makeRectangle(cx, cy, height, width);
    }

    public void shift(double x, double y) {
        cx += x;
        cy += y;
        rectangle = makeRectangle(cx, cy, height, width);
    }

    public void scaleAboutZero(double scale) {
        cx *= scale;
        cy *= scale;
        height *= scale;
        width *= scale;
        rectangle = makeRectangle(cx, cy, height, width);
    }

    private Rectangle2D.Double makeRectangle(double x, double y, double h, double w) {
        return new Rectangle2D.Double(x, y, h, w);
    }

    public Rectangle2D.Double getRectangle() {
        return rectangle;
    }

    public Area getBigInterior() {
        return new Area(makeRectangle(cx, cy, height + nudge, width + nudge));
    }

    public Area getSmallInterior() {
        return new Area(makeRectangle(cx, cy, height - nudge, width - nudge));
    }

    public String debug() {
        if (logger.getEffectiveLevel().isGreaterOrEqual(Level.DEBUG)) {
            return "box " + ac.getLabel() + " at (" + cx + "," + cy + ") height " + height + ", width " + width;
        } else {
            return "";
        }
    }

    public Shape getFatInterior(double fatter) {
        return new Area(makeRectangle(cx, cy, height + fatter, width + fatter));
    }

    public double getLabelXPosition() {
        return cx + 0.8 * width/2;
    }

    public double getLabelYPosition() {
        return cy - 0.75 * height/2;
    }

    public int getMinX() {
        return (int) (cx - width/2);
    }

    public int getMaxX() {
        return (int) (cx + width/2) + 1;
    }

    public int getMinY() {
        return (int) (cy - height/2);
    }

    public int getMaxY() {
        return (int) (cy + height/2) + 1;
    }

    static void fitRectanglesToSize(ArrayList<BoxContour> rectangles, int size) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (BoxContour bc : rectangles) {
            if (bc.getMinX() < minX) {
                minX = bc.getMinX();
            }
            if (bc.getMinY() < minY) {
                minY = bc.getMinY();
            }
            if (bc.getMaxX() > maxX) {
                maxX = bc.getMaxX();
            }
            if (bc.getMaxY() > maxY) {
                maxY = bc.getMaxY();
            }
        }

        double midX = (minX + maxX) * 0.5;
        double midY = (minY + maxY) * 0.5;
        for (BoxContour bc : rectangles) {
            bc.shift(-midX, -midY);
        }

        double width = maxX - minX;
        double height = maxY - minY;
        double biggest_HW = Math.max(height, width);
        double scale = (size * 0.95) / biggest_HW;
        for (BoxContour bc : rectangles) {
            bc.scaleAboutZero(scale);
        }

        for (BoxContour bc : rectangles) {
            bc.shift(size * 0.5, size * 0.5);
        }

    }

    public Color color() {
        return colour;
    }

    public void setColor(Color colour) {
        this.colour = colour;
    }

    public double getCx() {
        return cx;
    }

    public double getCy() {
        return cy;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setStroke(Stroke s) {
        stroke = s;
    }

    public Stroke stroke() {
        return stroke;
    }
}
