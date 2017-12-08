package concrete;


import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteSpider;
import icircles.concreteDiagram.ConcreteZone;

import java.awt.*;
import java.util.ArrayList;

/***
 * Build on ConcreteDiagram in iCircles, adding boxes around entities,
 * allowing for different edges.
 */
public class ConcreteConceptDiagram {

    ArrayList<CircleContour> circles;
    ArrayList<BoxContour> boxes;
    ArrayList<ConcreteZone> shadedZones;
    ArrayList<ConcreteZone> unshadedZones;
    ArrayList<ConcreteSpider> spiders;
    private Font font;

    public ConcreteConceptDiagram(ArrayList<BoxContour> boxes,
                                  ArrayList<CircleContour> circles,
                                  ArrayList<ConcreteZone> shadedZones,
                                  ArrayList<ConcreteZone> unshadedZones,
                                  ArrayList<ConcreteSpider> spiders) {
        this.boxes = boxes;
        this.circles = circles;
        this.shadedZones = shadedZones;
        this.unshadedZones = unshadedZones;
        this.spiders = spiders;
    }

    public ArrayList<CircleContour> getCircles() {
        return circles;
    }

}
