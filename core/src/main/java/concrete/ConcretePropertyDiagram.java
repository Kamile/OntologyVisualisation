package concrete;

import lang.ClassObjectPropertyDiagram;
import lang.DatatypeDiagram;

import java.util.HashMap;
import java.util.Set;

public class ConcretePropertyDiagram extends ConcreteBaseDiagram{
    public ConcretePropertyDiagram(HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> concreteCOPDiagrams,
                                   HashMap<DatatypeDiagram, ConcreteDatatypeDiagram> concreteDTDiagrams,
                                   Set<ConcreteArrow>  concreteArrows) {
        super(concreteCOPDiagrams, concreteDTDiagrams, concreteArrows);
    }
}
