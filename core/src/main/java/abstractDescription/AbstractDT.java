package abstractDescription;

import icircles.abstractDescription.AbstractDescription;

import java.util.Set;

public class AbstractDT extends AbstractSubDiagram {
    public AbstractDT(AbstractDescription spiderDescription, Set<String> dots) {
        super(spiderDescription, null, null, dots, false);
    }
}
