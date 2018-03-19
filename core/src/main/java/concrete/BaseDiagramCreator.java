package concrete;

import abstractDescription.*;
import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.DiagramCreator;
import icircles.util.CannotDrawException;
import lang.ClassObjectPropertyDiagram;
import lang.DatatypeDiagram;

import java.util.*;

/***
 * Layout algorithms for well-formedness of concept and property diagrams.
 *
 * Here we re-write the DiagramCreator from iCircles to remove split curves and apply
 * all of the layout constraints as described in the success criteria.
 *
 * First use DiagramCreator to draw each spider diagram separately, space them out and draw arrows
 */
public class BaseDiagramCreator {
    HashMap<ClassObjectPropertyDiagram, AbstractCOP> abstractCOPDescriptions;
    HashMap<DatatypeDiagram, AbstractDatatypeDiagram> abstractDTDescriptions;
    Set<AbstractArrow> abstractArrowDescriptions;

    public BaseDiagramCreator(AbstractDiagram abstractDescription) {
        abstractCOPDescriptions = abstractDescription.getCOPs();
        abstractDTDescriptions = abstractDescription.getDTs();
        abstractArrowDescriptions = abstractDescription.getArrowDescriptions();
    }

    public ConcreteBaseDiagram createDiagram(int size) throws CannotDrawException {
        HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> COPs = new HashMap<>();
        HashMap<DatatypeDiagram, ConcreteDatatypeDiagram> DTs = new HashMap<>();
        Set<ConcreteArrow> concreteArrows = new HashSet<>();

        for (ClassObjectPropertyDiagram copDiagram: abstractCOPDescriptions.keySet()) {
            AbstractCOP abstractCOPDescription = abstractCOPDescriptions.get(copDiagram);
            AbstractDescription ad = abstractCOPDescription.getPrimarySDDescription();
            Set<ConcreteArrow> concreteCOPArrows = new HashSet<>();
            Set<ConcreteEquality> concreteEqualities = new HashSet<>();
            boolean containsInitialT = abstractCOPDescription.containsInitialT();

            for (AbstractArrow abstractArrow: abstractCOPDescription.getArrows()) {
                ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow, copDiagram.hashCode());
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
                COPDescription = new ConcreteClassObjectPropertyDiagram(cd, concreteCOPArrows, concreteEqualities, containsInitialT);
            } else { // no contours, just dots
                COPDescription = new ConcreteClassObjectPropertyDiagram(concreteCOPArrows, abstractCOPDescription.getDots(), concreteEqualities, containsInitialT);
            }
            COPs.put(copDiagram, COPDescription);
        }

        for (DatatypeDiagram datatypeDiagram: abstractDTDescriptions.keySet()) {
            AbstractDatatypeDiagram abstractDTDescription = abstractDTDescriptions.get(datatypeDiagram);
            AbstractDescription ad = abstractDTDescription.getPrimarySDDescription();

            ConcreteDatatypeDiagram DTDescription;
            if (ad!=null) {
                DiagramCreator dc = new DiagramCreator(ad);
                icircles.concreteDiagram.ConcreteDiagram cd = dc.createDiagram(size);
                DTDescription = new ConcreteDatatypeDiagram(cd);
            } else { // no contours, just dots
                DTDescription = new ConcreteDatatypeDiagram(abstractDTDescription.getDots());
            }
            DTs.put(datatypeDiagram, DTDescription);
        }

        for (AbstractArrow abstractArrow: abstractArrowDescriptions) {
            ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow, 0);
            concreteArrows.add(concreteArrow);
        }

        return new ConcreteBaseDiagram(COPs, DTs, concreteArrows);
    }
}
