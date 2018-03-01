package concrete;

import icircles.concreteDiagram.ConcreteDiagram;
import java.util.Set;

public class ConcreteClassObjectPropertyDiagram extends ConcreteBaseDiagram {

    public ConcreteClassObjectPropertyDiagram(ConcreteDiagram cd, Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities) {
        super(cd, arrows, equalities);
    }

    public ConcreteClassObjectPropertyDiagram(Set<ConcreteArrow> arrows, Set<String> dots, Set<ConcreteEquality> equalities) {
        super(arrows, dots, equalities);
    }
}
