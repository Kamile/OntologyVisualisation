package concrete;

import icircles.concreteDiagram.ConcreteDiagram;
import java.util.Set;

public class ConcreteDT extends ConcreteSubDiagram {
    public ConcreteDT(ConcreteDiagram cd) {
        super(cd);
    }

    public ConcreteDT(Set<String> dots) {
        super(dots);
    }
}
