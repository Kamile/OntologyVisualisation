package concrete;

import icircles.concreteDiagram.ConcreteDiagram;
import java.util.Set;

public class ConcreteDT extends ConcreteSubDiagram {
    public ConcreteDT(int id, ConcreteDiagram cd) {
        super(id, cd);
    }

    public ConcreteDT(int id, Set<String> dots) {
        super(id, dots);
    }
}
