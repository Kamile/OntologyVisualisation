package concrete;

import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteZone;
import lang.Equality;

import java.util.Set;

public class ConcreteDT extends ConcreteSubDiagram {
    public ConcreteDT(int id, ConcreteDiagram cd, Set<ConcreteZone> highlightedZones, Set<ConcreteEquality> equalities) {
        super(id, cd, highlightedZones, equalities);
    }

    public ConcreteDT(int id, Set<String> dots, Set<ConcreteEquality> equalities) {
        super(id, dots, equalities);
    }
}
