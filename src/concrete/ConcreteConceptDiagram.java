package concrete;

import abstractDescription.AbstractConceptDiagramDescription;
import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteZone;
import icircles.concreteDiagram.ConcreteSpider;
import icircles.util.CannotDrawException;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/***
 * Build on ConcreteDiagram in iCircles, adding boundary rectangles,
 * allowing for different edges.
 *
 * Each Concept Diagram consists of a set of the following:
 *
 * -- Dots (spiders), representing named individuals
 * -- Boundary rectangles, representing the universal set
 * -- Curves, representing sets, which can be labelled or unlabelled. Curves
 *    without labels represent anonymous sets. We use spatial relationships
 *    between cruves and spiders within a boundary rectangle to convey semantics.
 * -- Shaded zones (and unshaded zones), representing upper bounds on set cardinality.
 *    In a shaded region, all elements are represented by spiders.
 * -- Arrows (dashed/solid, labelled/unlabelled), representing semantics about
 *    binary relations.
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
