package concrete;

import icircles.concreteDiagram.ConcreteDiagram;
import java.util.Set;

public class ConcreteDatatypeDiagram  extends ConcreteBaseDiagram {
    public ConcreteDatatypeDiagram(ConcreteDiagram cd, Set<ConcreteEquality> equalities) {
        super(cd, equalities);
    }

    public ConcreteDatatypeDiagram( Set<String> dots, Set<ConcreteEquality> equalities) {
        super(dots, equalities);
    }
}
