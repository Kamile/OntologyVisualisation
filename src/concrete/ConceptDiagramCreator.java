package concrete;

import abstractDescription.AbstractConceptDiagramDescription;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.DiagramCreator;
import icircles.decomposition.DecompositionStrategy;
import icircles.recomposition.RecompositionStrategy;
import icircles.util.CannotDrawException;

import java.util.ArrayList;
import java.util.HashMap;

/***
 * Layout algorithms for well-formedness of concept and property diagrams.
 *
 * Here we re-write the DiagramCreator from iCircles to remove split curves and apply
 * all of the layout constraints as described in the success criteria.
 */
public class ConceptDiagramCreator {

    ArrayList<BoxContour> boxes;
    ArrayList<CircleContour> circles;
    HashMap<AbstractCurve, CircleContour> map;

    public ConceptDiagramCreator(AbstractConceptDiagramDescription abstractDescription) {

    }

    public ConceptDiagramCreator(AbstractConceptDiagramDescription abstractDescription, DecompositionStrategy decomp_strategy, RecompositionStrategy recomp_strategy) {

    }

    public ConcreteConceptDiagram createDiagram(int size) throws CannotDrawException {
        circles = new ArrayList<CircleContour>();
        ConcreteConceptDiagram result = null;

        return result;

    }
}
