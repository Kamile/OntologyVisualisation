package abstractDescription;

import icircles.abstractDescription.AbstractDescription;

import java.util.Set;

public class AbstractDatatypeDiagram extends AbstractSubDiagram {
    public AbstractDatatypeDiagram(AbstractDescription spiderDescription, Set<String> dots) {
        super(spiderDescription, null, null, dots);
    }
}
