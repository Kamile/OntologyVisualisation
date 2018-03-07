package concrete;

import abstractDescription.AbstractConceptDiagram;
import icircles.util.CannotDrawException;
import lang.ClassObjectPropertyDiagram;
import lang.DatatypeDiagram;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class ConcretePropertyDiagram {

    private Set<ConcreteArrow> arrows;
    private HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> COPDiagrams;
    private HashMap<DatatypeDiagram, ConcreteDatatypeDiagram> DTDiagrams;

    public ConcretePropertyDiagram(HashMap<ClassObjectPropertyDiagram, ConcreteClassObjectPropertyDiagram> concreteCOPDiagrams, Set<ConcreteArrow>  concreteArrows) {
        COPDiagrams = concreteCOPDiagrams;
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

    public static ConcreteConceptDiagram makeConcreteDiagram(AbstractConceptDiagram abstractDescription, int size) throws CannotDrawException {

        if (!abstractDescription.isValid()) {
            throw new CannotDrawException("Invalid diagram specification");
        }

        ConceptDiagramCreator cdc = new ConceptDiagramCreator(abstractDescription);
        return cdc.createDiagram(size);
    }
}
