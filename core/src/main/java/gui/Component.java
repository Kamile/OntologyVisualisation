package gui;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

class Component {
    private Area area;
    private Ellipse2D.Double ellipse;
    private Line2D.Double line;
    private Color colour;
    private Stroke stroke;
    private String label;
    private Font font;
    private double x;
    private double y;
    boolean fill;

    Component(double x, double y, String label, Font font, Stroke stroke, Color colour) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.stroke = stroke;
        this.colour = colour;
        this.font = font;
    }

    Component(Area area, Color colour) {
        this.area = area;
        this.colour = colour;
    }

    Component(Ellipse2D.Double ellipse, Stroke stroke, Color colour, boolean fill) {
        this.ellipse = ellipse;
        this.stroke = stroke;
        this.colour = colour;
        this.fill = fill;
    }

    Component(Line2D.Double line, Stroke stroke, Color colour) {
        this.line = line;
        this.stroke = stroke;
        this.colour = colour;
    }

    Ellipse2D.Double getEllipse() {
        return ellipse;
    }

    Line2D.Double getLine() {
        return line;
    }

    String getLabel() {
        return label;
    }

    Area getArea() {
        return area;
    }

    Color getColour() {
        return colour;
    }

    Stroke getStroke() {
        return stroke;
    }

    Font getFont() {
        return font;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }
}
