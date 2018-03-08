package concrete;

import abstractDescription.AbstractDiagram;
import icircles.util.CannotDrawException;
import lang.ClassObjectPropertyDiagram;
import lang.DatatypeDiagram;

import java.util.HashMap;
import java.util.Set;

public class ConcreteBaseDiagram {
    Set<ConcreteArrow> arrows;
    HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> COPDiagrams;
    HashMap<DatatypeDiagram, ConcreteDatatypeDiagram> DTDiagrams;

    public ConcreteBaseDiagram(HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> concreteCOPDiagrams,
                               HashMap<DatatypeDiagram, ConcreteDatatypeDiagram> concreteDTDiagrams,
                               Set<ConcreteArrow>  concreteArrows) {
        COPDiagrams = concreteCOPDiagrams;
        DTDiagrams = concreteDTDiagrams;
        arrows = concreteArrows;
    }

    public HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> getCOPMapping() {
        return COPDiagrams;
    }

    public HashMap<DatatypeDiagram, ConcreteDatatypeDiagram> getDTMapping() {
        return DTDiagrams;
    }

    public Set<ConcreteArrow> getArrows() {
        return arrows;
    }

    public static ConcreteBaseDiagram makeConcreteDiagram(AbstractDiagram abstractDescription, int size) throws CannotDrawException {

        if (!abstractDescription.isValid()) {
            throw new CannotDrawException("Invalid diagram specification");
        }

        BaseDiagramCreator cdc = new BaseDiagramCreator(abstractDescription);
        return cdc.createDiagram(size);
    }
}
