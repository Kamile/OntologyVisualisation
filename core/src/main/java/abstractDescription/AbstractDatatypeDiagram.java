package abstractDescription;

import icircles.abstractDescription.AbstractDescription;

import java.util.Set;

public class AbstractDatatypeDiagram extends AbstractDiagram {

    public AbstractDatatypeDiagram(AbstractDescription spiderDescription, Set<AbstractArrow> arrowDescriptions,
                                   Set<AbstractEquality> equalityDescriptions, Set<String> dots) {
        super(spiderDescription, arrowDescriptions, equalityDescriptions, dots);
    }
}
