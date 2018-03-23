package abstractDescription;

import icircles.abstractDescription.AbstractDescription;
import java.util.Iterator;
import java.util.Set;

public class AbstractDiagram extends AbstractDescription {
    private Set<AbstractCOP> m_COPs;
    private Set<AbstractDT> m_DTs;
    private Set<AbstractArrow> m_arrows;

    AbstractDiagram(Set<AbstractCOP> COPs, Set<AbstractDT> DTs, Set<AbstractArrow> arrows) {
        m_COPs = COPs;
        m_DTs = DTs;
        m_arrows = arrows;
    }

    public Set<AbstractCOP> getCOPs() {
        return m_COPs;
    }

    public Set<AbstractDT> getDTs() {
        return m_DTs;
    }

    public Set<AbstractArrow> getArrowDescriptions() {
        return m_arrows;
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
        if (this instanceof AbstractConceptDiagram) {
            for (AbstractCOP cop: m_COPs) {
                if (cop.containsInitialT()) {
                    return false;
                }
            }
        }
        return true;
    }
}
