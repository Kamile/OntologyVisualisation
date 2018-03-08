package concrete;

import icircles.concreteDiagram.ConcreteDiagram;
import java.util.Set;

public class ConcreteDatatypeDiagram  extends ConcreteSubDiagram {
    public ConcreteDatatypeDiagram(ConcreteDiagram cd) {
        super(cd);
    }

    public ConcreteDatatypeDiagram(Set<String> dots) {
        super(dots);
    }
}
