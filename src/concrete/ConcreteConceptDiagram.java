package concrete;


import abstractDescription.AbstractConceptDiagramDescription;
import icircles.concreteDiagram.*;
import icircles.util.CannotDrawException;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/***
 * Build on ConcreteDiagram in iCircles, adding boxes around entities,
 * allowing for different edges.
 */
public class ConcreteConceptDiagram extends ConcreteDiagram {

    ArrayList<BoxContour> boxes;

    public ConcreteConceptDiagram(ArrayList<BoxContour> boxes,
                                  ArrayList<CircleContour> circles,
                                  ArrayList<ConcreteZone> shadedZones,
                                  ArrayList<ConcreteZone> unshadedZones,
                                  ArrayList<ConcreteSpider> spiders) {
        super(new Rectangle2D.Double(), circles, shadedZones, unshadedZones, spiders);
        this.boxes = boxes;
    }

    public ArrayList<BoxContour> getBoxes() {
        return boxes;
    }

    public static ConcreteConceptDiagram makeConcreteDiagram(AbstractConceptDiagramDescription abstractDescription, int size) throws CannotDrawException {

        if (abstractDescription.isValid()) {
            throw new CannotDrawException("Invalid diagram specification");
        }

        ConceptDiagramCreator cdc = new ConceptDiagramCreator(abstractDescription);
        return cdc.createDiagram(size);
    }

}
