package concrete;

import icircles.concreteDiagram.ConcreteDiagram;

import java.util.Set;

public class ConcreteCOP extends ConcreteSubDiagram {

    ConcreteCOP(int id, ConcreteDiagram cd, Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities, boolean containsInitialT) {
        super(id, cd, arrows, equalities, containsInitialT);
    }

    ConcreteCOP(int id, Set<ConcreteArrow> arrows, Set<String> dots, Set<ConcreteEquality> equalities, boolean containsInitialT) {
        super(id, arrows, dots, equalities, containsInitialT);
    }

}
