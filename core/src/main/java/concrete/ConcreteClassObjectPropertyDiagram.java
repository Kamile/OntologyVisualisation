package concrete;

import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteSpider;
import icircles.concreteDiagram.ConcreteZone;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class ConcreteClassObjectPropertyDiagram extends ConcreteDiagram{

    ArrayList<ConcreteArrow> arrows;
    public ConcreteClassObjectPropertyDiagram(Rectangle2D.Double box, ArrayList<CircleContour> circles, ArrayList<ConcreteZone> shadedZones, ArrayList<ConcreteZone> unshadedZones, ArrayList<ConcreteSpider> spiders,
                                              ArrayList<ConcreteArrow> arrows) {
        super(box, circles, shadedZones, unshadedZones, spiders);
        this.arrows = arrows;
    }
}
