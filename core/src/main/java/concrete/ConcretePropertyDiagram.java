package concrete;

import java.util.Set;

public class ConcretePropertyDiagram extends ConcreteBaseDiagram{
    public ConcretePropertyDiagram(Set<ConcreteClassObjectPropertyDiagram> concreteCOPDiagrams,
                                   Set<ConcreteDatatypeDiagram> concreteDTDiagrams,
                                   Set<ConcreteArrow>  concreteArrows) {
        super(concreteCOPDiagrams, concreteDTDiagrams, concreteArrows);
    }
}
