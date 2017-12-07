package concrete;

import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.DiagramCreator;
import icircles.decomposition.DecompositionStrategy;
import icircles.recomposition.RecompositionStrategy;

import java.util.ArrayList;

public class ConceptDiagramCreator extends DiagramCreator {

    ArrayList<BoxContour> boxes;

    public ConceptDiagramCreator(AbstractDescription ad) {
        super(ad);
    }

    public ConceptDiagramCreator(AbstractDescription ad, DecompositionStrategy decomp_strategy, RecompositionStrategy recomp_strategy) {
        super(ad, decomp_strategy, recomp_strategy);
    }


}
