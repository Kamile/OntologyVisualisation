package concrete;

import abstractDescription.AbstractArrow;
import abstractDescription.AbstractConceptDiagramDescription;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.DiagramCreator;
import icircles.decomposition.DecompositionStep;
import icircles.decomposition.DecompositionStrategy;
import icircles.recomposition.RecompData;
import icircles.recomposition.RecompositionStep;
import icircles.recomposition.RecompositionStrategy;
import icircles.util.CannotDrawException;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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
    Set<AbstractDescription> spiderDiagramDescriptions;
    Set<AbstractArrow> arrowDescriptions;
    static Logger logger = Logger.getLogger(DiagramCreator.class.getName());

    public ConceptDiagramCreator(AbstractConceptDiagramDescription abstractDescription) {
        spiderDiagramDescriptions = abstractDescription.getSpiderDescriptions();
        arrowDescriptions = abstractDescription.getArrowDescriptions();
    }

    public ConcreteConceptDiagram createDiagram(int size) throws CannotDrawException {
        Set<ConcreteDiagram> concreteSpiderDiagrams = new HashSet<>();
        Set<ConcreteArrow> concreteArrows = new HashSet<>();
        for (AbstractDescription ad: spiderDiagramDescriptions) {
            DiagramCreator dc = new DiagramCreator(ad);
            ConcreteDiagram cd = dc.createDiagram(size);
            concreteSpiderDiagrams.add(cd);
        }

        for (AbstractArrow abstractArrow: arrowDescriptions) {
            ConcreteArrow concreteArrow = new ConcreteArrow(abstractArrow);
            concreteArrows.add(concreteArrow);
        }

        return new ConcreteConceptDiagram(concreteSpiderDiagrams, concreteArrows);
    }
}
