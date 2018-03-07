package abstractDescription;

import icircles.abstractDescription.AbstractDescription;
import lang.ClassObjectPropertyDiagram;
import lang.DatatypeDiagram;

import java.util.HashMap;
import java.util.Set;

public class AbstractPropertyDiagram extends AbstractDescription {
    private Set<AbstractArrow> m_arrows;
    private HashMap<ClassObjectPropertyDiagram, AbstractCOP> m_COPs;
    private HashMap<DatatypeDiagram, AbstractDatatypeDiagram> m_DTs;

    public AbstractPropertyDiagram(HashMap<ClassObjectPropertyDiagram, AbstractCOP> COPDescriptionMap, HashMap<DatatypeDiagram, AbstractDatatypeDiagram> DTDescriptionMap, Set<AbstractArrow> arrows) {
        m_COPs = COPDescriptionMap;
        m_DTs = DTDescriptionMap;
        m_arrows = arrows;
    }

    public HashMap<ClassObjectPropertyDiagram, AbstractCOP> getCOPs() {
        return m_COPs;
    }

    public HashMap<DatatypeDiagram, AbstractDatatypeDiagram> getDTs() {
        return m_DTs;
    }

    public Set<AbstractArrow> getArrowDescriptions() {
        return m_arrows;
    }

}
