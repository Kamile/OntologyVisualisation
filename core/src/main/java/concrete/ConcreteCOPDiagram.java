package concrete;

import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteSpider;
import icircles.concreteDiagram.ConcreteZone;
import speedith.core.lang.PrimarySpiderDiagram;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class ConcreteCOPDiagram extends ConcreteDiagram{

    ArrayList<ConcreteArrow> arrows;
    public ConcreteCOPDiagram(Rectangle2D.Double box, ArrayList<CircleContour> circles, ArrayList<ConcreteZone> shadedZones, ArrayList<ConcreteZone> unshadedZones, ArrayList<ConcreteSpider> spiders,
                              ArrayList<ConcreteArrow> arrows) {
        super(box, circles, shadedZones, unshadedZones, spiders);
        this.arrows = arrows;
    }
}
