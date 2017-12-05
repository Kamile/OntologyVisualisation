package concrete;

import icircles.abstractDescription.AbstractCurve;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Rectangle2D;

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

    private Rectangle2D.Double makeRectangle(double x, double y, double h, double w) {
        return new Rectangle2D.Double(x, y, h, w);
    }
}
