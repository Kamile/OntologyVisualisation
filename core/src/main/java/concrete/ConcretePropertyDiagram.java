package concrete;

import java.util.Set;

public class ConcretePropertyDiagram extends ConcreteBaseDiagram {
    public ConcretePropertyDiagram(Set<ConcreteCOP> concreteCOPDiagrams,
                                   Set<ConcreteDT> concreteDTDiagrams,
                                   Set<ConcreteArrow>  concreteArrows) {
        super(concreteCOPDiagrams, concreteDTDiagrams, concreteArrows);
    }
}
