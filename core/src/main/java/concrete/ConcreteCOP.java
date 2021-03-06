package concrete;

import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteZone;

import java.util.Set;

public class ConcreteCOP extends ConcreteSubDiagram {
    public boolean containsInitialT;
    public boolean isSingleVariableTInstance;

    public ConcreteCOP(int id, ConcreteDiagram cd, Set<ConcreteZone> highlightedZones, Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities) {
        super(id, cd, highlightedZones, arrows, equalities);
    }

    public ConcreteCOP(int id, Set<ConcreteArrow> arrows, Set<String> dots, Set<ConcreteEquality> equalities) {
        super(id, arrows, dots, equalities);
    }
}
