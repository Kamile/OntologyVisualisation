package concrete;

import abstractDescription.*;
import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.DiagramCreator;
import icircles.util.CannotDrawException;
import java.util.*;

/***
 * Layout algorithms for well-formedness of concept and property diagrams.
 *
 * Here we re-write the DiagramCreator from iCircles to remove split curves and apply
 * all of the layout constraints as described in the success criteria.
 *
 * First use DiagramCreator to draw each spider diagram separately, space them out and draw arrows
 */
class BaseDiagramCreator {
    private Set<AbstractCOP> abstractCOPs;
    private Set<AbstractDT> abstractDTs;
    private Set<AbstractArrow> abstractArrows;

    BaseDiagramCreator(AbstractDiagram abstractDescription) {
        abstractCOPs = abstractDescription.getCOPs();
        abstractDTs = abstractDescription.getDTs();
        abstractArrows = abstractDescription.getArrowDescriptions();
    }

    ConcreteBaseDiagram createDiagram(int size) throws CannotDrawException {
        Set<ConcreteClassObjectPropertyDiagram> COPs = new HashSet<>();
        Set<ConcreteDatatypeDiagram> DTs = new HashSet<>();
        Set<ConcreteArrow> concreteArrows = new HashSet<>();
        int id = 0;

        for (AbstractCOP abstractCOP: abstractCOPs) {
            AbstractDescription ad = abstractCOP.getPrimarySDDescription();
            Set<ConcreteArrow> concreteCOPArrows = new HashSet<>();
            Set<ConcreteEquality> concreteEqualities = new HashSet<>();
            boolean containsInitialT = abstractCOP.containsInitialT();

            for (AbstractArrow abstractArrow: abstractCOP.getArrows()) {
                ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow, id);
                concreteCOPArrows.add(concreteArrow);
            }

            for (AbstractEquality abstractEquality: abstractCOP.getEqualities()) {
                ConcreteEquality concreteEquality = new ConcreteEquality(abstractEquality);
                concreteEqualities.add(concreteEquality);
            }
            ConcreteClassObjectPropertyDiagram COPDescription;
            if (ad!=null) {
                DiagramCreator dc = new DiagramCreator(ad);
                icircles.concreteDiagram.ConcreteDiagram cd = dc.createDiagram(size);
                COPDescription = new ConcreteClassObjectPropertyDiagram(cd, concreteCOPArrows, concreteEqualities, containsInitialT);
            } else { // no contours, just dots
                COPDescription = new ConcreteClassObjectPropertyDiagram(concreteCOPArrows, abstractCOP.getDots(), concreteEqualities, containsInitialT);
            }
            COPs.add(COPDescription);
            id++;
        }

        for (AbstractDT abstractDT: abstractDTs) {
            AbstractDescription ad = abstractDT.getPrimarySDDescription();

            ConcreteDatatypeDiagram DTDescription;
            if (ad!=null) {
                DiagramCreator dc = new DiagramCreator(ad);
                icircles.concreteDiagram.ConcreteDiagram cd = dc.createDiagram(size);
                DTDescription = new ConcreteDatatypeDiagram(cd);
            } else { // no contours, just dots
                DTDescription = new ConcreteDatatypeDiagram(abstractDT.getDots());
            }
            DTs.add(DTDescription);
        }

        for (AbstractArrow abstractArrow: abstractArrows) {
            ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow, 0);
            concreteArrows.add(concreteArrow);
        }

        return new ConcreteBaseDiagram(COPs, DTs, concreteArrows);
    }
}
