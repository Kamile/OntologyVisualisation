package concrete;

import icircles.concreteDiagram.ConcreteDiagram;
import java.util.Set;

public class ConcreteClassObjectPropertyDiagram extends ConcreteSubDiagram {

    public ConcreteClassObjectPropertyDiagram(ConcreteDiagram cd, Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities, boolean containsInitialT) {
        super(cd, arrows, equalities, containsInitialT);
    }

    public ConcreteClassObjectPropertyDiagram(Set<ConcreteArrow> arrows, Set<String> dots, Set<ConcreteEquality> equalities, boolean containsInitialT) {
        super(arrows, dots, equalities, containsInitialT);
    }
}
