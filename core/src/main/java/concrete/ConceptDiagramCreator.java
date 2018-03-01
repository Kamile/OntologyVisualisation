package concrete;

import abstractDescription.AbstractArrow;
import abstractDescription.AbstractCOP;
import abstractDescription.AbstractConceptDiagram;
import abstractDescription.AbstractEquality;
import icircles.abstractDescription.AbstractDescription;
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
    HashMap<ClassObjectPropertyDiagram, AbstractCOP> abstractCOPDescriptions;
    Set<AbstractArrow> abstractArrowDescriptions;

    public ConceptDiagramCreator(AbstractConceptDiagram abstractDescription) {
        abstractCOPDescriptions = abstractDescription.getCOPs();
        abstractArrowDescriptions = abstractDescription.getArrowDescriptions();
    }

    public ConcreteConceptDiagram createDiagram(int size) throws CannotDrawException {
        HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> COPs = new HashMap<>();
        Set<ConcreteArrow> concreteArrows = new HashSet<>();

        for (ClassObjectPropertyDiagram copDiagram: abstractCOPDescriptions.keySet()) {
            AbstractCOP abstractCOPDescription = abstractCOPDescriptions.get(copDiagram);
            AbstractDescription ad = abstractCOPDescription.getPrimarySDDescription();
            Set<ConcreteArrow> concreteCOPArrows = new HashSet<>();
            Set<ConcreteEquality> concreteEqualities = new HashSet<>();

            for (AbstractArrow abstractArrow: abstractCOPDescription.getArrows()) {
                ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow);
                concreteCOPArrows.add(concreteArrow);
            }

            for (AbstractEquality abstractEquality: abstractCOPDescription.getEqualities()) {
                ConcreteEquality concreteEquality = new ConcreteEquality(abstractEquality);
                concreteEqualities.add(concreteEquality);
            }
            ConcreteClassObjectPropertyDiagram COPDescription;
            if (ad!=null) {
                DiagramCreator dc = new DiagramCreator(ad);
                icircles.concreteDiagram.ConcreteDiagram cd = dc.createDiagram(size);
                COPDescription = new ConcreteClassObjectPropertyDiagram(cd, concreteCOPArrows, concreteEqualities);
            } else { // no contours, just dots
                COPDescription = new ConcreteClassObjectPropertyDiagram(concreteCOPArrows, abstractCOPDescription.getDots(), concreteEqualities);
            }
            COPs.put(copDiagram, COPDescription);
        }

        for (AbstractArrow abstractArrow: abstractArrowDescriptions) {
            ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow);
            concreteArrows.add(concreteArrow);
        }

        return new ConcreteConceptDiagram(COPs, concreteArrows);
    }
}
