package abstractDescription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import icircles.abstractDescription.AbstractDescription;
import lang.ClassObjectPropertyDiagram;
import lang.DatatypeDiagram;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class AbstractDiagram extends AbstractDescription {
    Set<AbstractArrow> m_arrows;
    HashMap<ClassObjectPropertyDiagram, AbstractCOP> m_COPs;
    HashMap<DatatypeDiagram, AbstractDatatypeDiagram> m_DTs;

    public AbstractDiagram(HashMap<ClassObjectPropertyDiagram, AbstractCOP> COPDescriptionMap, Set<AbstractArrow> arrows) {
        m_COPs = COPDescriptionMap;
        m_DTs = new HashMap<>();
        m_arrows = arrows;
    }

    public AbstractDiagram(HashMap<ClassObjectPropertyDiagram, AbstractCOP> COPDescriptionMap,
                           HashMap<DatatypeDiagram, AbstractDatatypeDiagram> DTDescriptionMap,
                           Set<AbstractArrow> arrows) {
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

    public void addArrow(AbstractArrow a) {
        m_arrows.add(a);
    }

    @JsonIgnore
    public int getNumArrows() {
        return m_arrows.size();
    }

    @JsonIgnore
    public Iterator<AbstractArrow> getArrowIterator() {
        return m_arrows.iterator();
    }

    public boolean arrowIncludesLabel(String l) {
        Iterator i = m_arrows.iterator();
        AbstractArrow a;

        if (i.hasNext()) {
            a = (AbstractArrow) i.next();
        } else {
            return false;
        }

        while (a.getLabel().equals(l)) {
            if (!i.hasNext()){
                return false;
            }

            a = (AbstractArrow) i.next();
        }
        return true;
    }

    public boolean isValid() {
        return true;
    }
}
