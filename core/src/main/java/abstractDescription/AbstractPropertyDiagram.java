package abstractDescription;

import icircles.abstractDescription.AbstractDescription;

import java.util.Set;

public class AbstractPropertyDiagram extends AbstractDiagram {
    AbstractPropertyDiagram(AbstractDescription spiderDescription, Set<String> dots) {
        super(spiderDescription, null, null, dots);
    }
}
