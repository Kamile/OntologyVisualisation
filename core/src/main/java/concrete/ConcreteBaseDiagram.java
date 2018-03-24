package concrete;

import abstractDescription.AbstractDiagram;
import icircles.util.CannotDrawException;

import java.util.Set;

public class ConcreteBaseDiagram {
    Set<ConcreteArrow> arrows;
    Set<ConcreteCOP> COPDiagrams;
    Set<ConcreteDT> DTDiagrams;

    ConcreteBaseDiagram(Set<ConcreteCOP> concreteCOPDiagrams,
                               Set<ConcreteDT> concreteDTDiagrams,
                               Set<ConcreteArrow>  concreteArrows) {
        COPDiagrams = concreteCOPDiagrams;
        DTDiagrams = concreteDTDiagrams;
        arrows = concreteArrows;
    }

    public Set<ConcreteCOP> getCOPs() {
        return COPDiagrams;
    }

    public Set<ConcreteDT> getDTs() {
        return DTDiagrams;
    }

    public Set<ConcreteArrow> getArrows() {
        return arrows;
    }

    public static ConcreteBaseDiagram makeConcreteDiagram(AbstractDiagram abstractDescription, int size) throws CannotDrawException {

        if (!abstractDescription.isValid()) {
            throw new CannotDrawException("Invalid diagram specification");
        }

        BaseDiagramCreator diagramCreator = new BaseDiagramCreator(abstractDescription);
        return diagramCreator.createDiagram(size);
    }
}
