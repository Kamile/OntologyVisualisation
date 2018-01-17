package concrete;

import abstractDescription.AbstractConceptDiagramDescription;
import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteZone;
import icircles.concreteDiagram.ConcreteSpider;
import icircles.util.CannotDrawException;
import lang.BoundaryRectangle;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
public class ConcreteConceptDiagram{

    private Set<ConcreteArrow> arrows;
    private HashMap<BoundaryRectangle, Set<ConcreteDiagram>> spiderDiagrams;

    public ConcreteConceptDiagram(HashMap<BoundaryRectangle, Set<ConcreteDiagram>> concreteSpiderDiagrams, Set<ConcreteArrow>  concreteArrows) {
        spiderDiagrams = concreteSpiderDiagrams;
        arrows = concreteArrows;
    }

    public HashMap<BoundaryRectangle, Set<ConcreteDiagram>> getBoundarySpiderDiagramMapping() {
        return spiderDiagrams;
    }

    public Set<ConcreteDiagram> getSpiderDiagram(BoundaryRectangle br) {
        return spiderDiagrams.get(br);
    }

    public Set<ConcreteArrow> getArrows() {
        return arrows;
    }

    public static ConcreteConceptDiagram makeConcreteDiagram(AbstractConceptDiagramDescription abstractDescription, int size) throws CannotDrawException {

        if (!abstractDescription.isValid()) {
            throw new CannotDrawException("Invalid diagram specification");
        }

        ConceptDiagramCreator cdc = new ConceptDiagramCreator(abstractDescription);
        return cdc.createDiagram(size);
    }
}
