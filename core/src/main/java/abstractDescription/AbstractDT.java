package abstractDescription;

import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractDescription;

import java.util.Set;

public class AbstractDT extends AbstractSubDiagram {
    public AbstractDT(AbstractDescription spiderDescription, Set<AbstractBasicRegion> highlightedZones, Set<String> dots) {
        super(spiderDescription,  highlightedZones,null, null, dots, false);
    }
}
