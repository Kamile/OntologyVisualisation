package abstractDescription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import icircles.abstractDescription.AbstractDescription;

import java.util.Iterator;
import java.util.Set;

public class AbstractCOPDescription extends AbstractDescription {
    AbstractDescription ad;
    Set<AbstractArrow> m_arrows;

    public AbstractCOPDescription(AbstractDescription spiderDescription, Set<AbstractArrow> arrowDescriptions) {
        this.ad = spiderDescription;
        m_arrows = arrowDescriptions;
    }

    public AbstractDescription getPrimarySDDescription() {
        return ad;
    }

    public void addArrow(AbstractArrow a) {
        m_arrows.add(a);
    }

    @JsonIgnore
    public Iterator<AbstractArrow> getArrowIterator() {
        return m_arrows.iterator();
    }

    public Set<AbstractArrow> getArrows() {
        return m_arrows;
    }
}
