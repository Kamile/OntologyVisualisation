package concrete;

import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteZone;

import java.util.Set;

public class ConcreteDT extends ConcreteSubDiagram {
    public ConcreteDT(int id, ConcreteDiagram cd, Set<ConcreteZone> highlightedZones) {
        super(id, cd, highlightedZones);
    }

    public ConcreteDT(int id, Set<String> dots) {
        super(id, dots);
    }
}
