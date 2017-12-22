package concrete;

import abstractDescription.AbstractConceptDiagramDescription;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.DiagramCreator;
import icircles.decomposition.DecompositionStep;
import icircles.decomposition.DecompositionStrategy;
import icircles.recomposition.RecompData;
import icircles.recomposition.RecompositionStep;
import icircles.recomposition.RecompositionStrategy;
import icircles.util.CannotDrawException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/***
 * Layout algorithms for well-formedness of concept and property diagrams.
 *
 * Here we re-write the DiagramCreator from iCircles to remove split curves and apply
 * all of the layout constraints as described in the success criteria.
 */
public class ConceptDiagramCreator extends DiagramCreator {

    ArrayList<BoxContour> boxes;
    static Logger logger = Logger.getLogger(DiagramCreator.class.getName());

    AbstractDescription m_initial_diagram;
    final static int smallest_rad = 10;
    ArrayList<DecompositionStep> d_steps;
    ArrayList<RecompositionStep> r_steps;
    HashMap<AbstractBasicRegion, Double> zoneScores;
    HashMap<AbstractCurve, Double> contScores;
    HashMap<AbstractCurve, Double> guide_sizes;
    HashMap<AbstractCurve, CircleContour> map;
    ArrayList<CircleContour> circles;
    int debug_image_number = 0;
    int debug_size = 50;


    public ConceptDiagramCreator(AbstractConceptDiagramDescription abstractDescription) {
        super(abstractDescription);
    }

    public ConceptDiagramCreator(AbstractConceptDiagramDescription abstractDescription, DecompositionStrategy decomp_strategy, RecompositionStrategy recomp_strategy) {
        super(abstractDescription, decomp_strategy, recomp_strategy);
    }

    public ConcreteConceptDiagram createDiagram(int size) throws CannotDrawException {
        circles = new ArrayList<CircleContour>();
        ConcreteConceptDiagram result = null;
        return result;
    }

    /**
     * Re-written from createCircles in iCircles to remove split curves and apply extra layout criteria.
     * @param deb_size
     * @return
     * @throws CannotDrawException
     */
    private boolean createCircles(int deb_size) throws CannotDrawException {
        debug_size = deb_size;
        debug_image_number = 0;
        BuildStep bs = null;
        BuildStep tail = null;

        for (RecompositionStep rs : r_steps) {
            // we need to add the new curves with regard to their placement
            // relative to the existing ones in the map
            Iterator<RecompData> it = rs.getRecompIterator();
            while (it.hasNext()) {
                RecompData rd = it.next();
                BuildStep newOne = new BuildStep(rd);
                if (bs == null) {
                    bs = newOne;
                    tail = newOne;
                } else {
                    tail.next = newOne;
                    tail = newOne;
                }
            }
        }

        shuffleAndCombine(bs);

        BuildStep step = bs;


        return true;
    }

    private void shuffleAndCombine(BuildStep bs) {

    }
}
