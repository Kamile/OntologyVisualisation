package concrete;

import abstractDescription.AbstractArrow;
import abstractDescription.AbstractConceptDiagramDescription;
import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.DiagramCreator;
import icircles.util.CannotDrawException;
import lang.ClassObjectPropertyDiagram;
import org.apache.log4j.Logger;

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
    HashMap<ClassObjectPropertyDiagram, Set<AbstractDescription>> spiderDiagramDescriptions;
    Set<AbstractArrow> arrowDescriptions;
    static Logger logger = Logger.getLogger(DiagramCreator.class.getName());

    public ConceptDiagramCreator(AbstractConceptDiagramDescription abstractDescription) {
        spiderDiagramDescriptions = abstractDescription.getSpiderDescriptions();
        arrowDescriptions = abstractDescription.getArrowDescriptions();
    }

    public ConcreteConceptDiagram createDiagram(int size) throws CannotDrawException {
        HashMap<ClassObjectPropertyDiagram, Set<ConcreteDiagram>> spiderDiagramGroups = new HashMap<>();
        Set<ConcreteDiagram> concreteSpiderDiagrams = new HashSet<>();
        Set<ConcreteArrow> concreteArrows = new HashSet<>();

        for (ClassObjectPropertyDiagram br: spiderDiagramDescriptions.keySet()) {
            for (AbstractDescription ad: spiderDiagramDescriptions.get(br)) {
                DiagramCreator dc = new DiagramCreator(ad);
                ConcreteDiagram cd = dc.createDiagram(size);
                concreteSpiderDiagrams.add(cd);
            }
            spiderDiagramGroups.put(br, concreteSpiderDiagrams);
            concreteSpiderDiagrams = new HashSet<>();
            System.out.println(spiderDiagramGroups);
        }


        for (AbstractArrow abstractArrow: arrowDescriptions) {
            ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow);
            concreteArrows.add(concreteArrow);
        }

        return new ConcreteConceptDiagram(spiderDiagramGroups, concreteArrows);
    }
}
