package concrete;

import abstractDescription.AbstractArrow;
import abstractDescription.AbstractCOPDescription;
import abstractDescription.AbstractConceptDiagramDescription;
import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.DiagramCreator;
import icircles.util.CannotDrawException;
import lang.ClassObjectPropertyDiagram;

import java.util.*;

/***
 * Layout algorithms for well-formedness of concept and property diagrams.
 *
 * Here we re-write the DiagramCreator from iCircles to remove split curves and apply
 * all of the layout constraints as described in the success criteria.
 *
 * First use DiagramCreator to draw each spider diagram separately, space them out and draw arrows
 */
public class ConceptDiagramCreator {
    HashMap<ClassObjectPropertyDiagram, AbstractCOPDescription> abstractCOPDescriptions;
    Set<AbstractArrow> abstractArrowDescriptions;

    public ConceptDiagramCreator(AbstractConceptDiagramDescription abstractDescription) {
        abstractCOPDescriptions = abstractDescription.getCOPs();
        abstractArrowDescriptions = abstractDescription.getArrowDescriptions();
    }

    public ConcreteConceptDiagram createDiagram(int size) throws CannotDrawException {
        HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> COPs = new HashMap<>();
        Set<ConcreteArrow> concreteArrows = new HashSet<>();

        for (ClassObjectPropertyDiagram copDiagram: abstractCOPDescriptions.keySet()) {
            AbstractCOPDescription abstractCOPDescription = abstractCOPDescriptions.get(copDiagram);
            AbstractDescription ad = abstractCOPDescription.getPrimarySDDescription();
            Set<ConcreteArrow> concreteCOPArrows = new HashSet<>();

            for (AbstractArrow abstractArrow: abstractCOPDescription.getArrows()) {
                ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow);
                concreteCOPArrows.add(concreteArrow);
            }

            DiagramCreator dc = new DiagramCreator(ad);
            ConcreteDiagram cd = dc.createDiagram(size);
            ConcreteClassObjectPropertyDiagram COPDescription = new ConcreteClassObjectPropertyDiagram(cd, concreteCOPArrows);
            COPs.put(copDiagram, COPDescription);
        }

        for (AbstractArrow abstractArrow: abstractArrowDescriptions) {
            ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow);
            concreteArrows.add(concreteArrow);
        }

        return new ConcreteConceptDiagram(COPs, concreteArrows);
    }
}
