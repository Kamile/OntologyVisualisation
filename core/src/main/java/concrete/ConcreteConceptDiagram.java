package concrete;

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
public class ConcreteConceptDiagram extends ConcreteBaseDiagram {
    public ConcreteConceptDiagram(Set<ConcreteCOP> concreteCOPDiagrams,
                                  Set<ConcreteDT> concreteDTDiagrams,
                                  Set<ConcreteArrow>  concreteArrows) {
        super(concreteCOPDiagrams, concreteDTDiagrams, concreteArrows);
    }
}
