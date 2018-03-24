package concrete;

import icircles.concreteDiagram.ConcreteDiagram;

import java.util.Set;

public class ConcreteClassObjectPropertyDiagram extends ConcreteSubDiagram {
    private int id;

    ConcreteClassObjectPropertyDiagram(int id, ConcreteDiagram cd, Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities, boolean containsInitialT) {
        super(cd, arrows, equalities, containsInitialT);
        this.id = id;
    }

    ConcreteClassObjectPropertyDiagram(int id, Set<ConcreteArrow> arrows, Set<String> dots, Set<ConcreteEquality> equalities, boolean containsInitialT) {
        super(arrows, dots, equalities, containsInitialT);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
