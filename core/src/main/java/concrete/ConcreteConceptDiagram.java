package concrete;

import abstractDescription.AbstractConceptDiagram;
import icircles.util.CannotDrawException;
import lang.ClassObjectPropertyDiagram;

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
 *    between curves and spiders within a boundary rectangle to convey semantics.
 * -- Shaded zones (and unshaded zones), representing upper bounds on set cardinality.
 *    In a shaded region, all elements are represented by spiders.
 * -- Arrows (dashed/solid, labelled/unlabelled), representing semantics about
 *    binary relations.
 */
public class ConcreteConceptDiagram {

    private Set<ConcreteArrow> arrows;
    private HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> COPDiagrams;

    public ConcreteConceptDiagram(HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> concreteCOPDiagrams, Set<ConcreteArrow>  concreteArrows) {
        COPDiagrams = concreteCOPDiagrams;
        arrows = concreteArrows;
    }

    public HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> getCOPMapping() {
        return COPDiagrams;
    }

    public Set<ConcreteArrow> getArrows() {
        return arrows;
    }

    public static ConcreteConceptDiagram makeConcreteDiagram(AbstractConceptDiagram abstractDescription, int size) throws CannotDrawException {

        if (!abstractDescription.isValid()) {
            throw new CannotDrawException("Invalid diagram specification");
        }

        ConceptDiagramCreator cdc = new ConceptDiagramCreator(abstractDescription);
        return cdc.createDiagram(size);
    }
}
