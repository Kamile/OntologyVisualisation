package abstractDescription;

import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractDescription;
import java.util.Set;

public class AbstractCOP extends AbstractSubDiagram {
    public boolean isSingleVariableT;
    public int id;

    public AbstractCOP(AbstractDescription spiderDescription, Set<AbstractBasicRegion> highlightedZones, Set<AbstractArrow> arrowDescriptions, Set<AbstractEquality> equalityDescriptions, Set<String> dots, boolean containsInitialT) {
        super(spiderDescription, highlightedZones, arrowDescriptions, equalityDescriptions, dots, containsInitialT);
    }
}
