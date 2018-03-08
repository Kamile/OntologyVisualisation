package abstractDescription;

import icircles.abstractDescription.AbstractDescription;
import java.util.Set;

public class AbstractCOP extends AbstractSubDiagram {
    public AbstractCOP(AbstractDescription spiderDescription, Set<AbstractArrow> arrowDescriptions, Set<AbstractEquality> equalityDescriptions, Set<String> dots) {
        super(spiderDescription, arrowDescriptions, equalityDescriptions, dots);
    }
}
