package abstractDescription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import icircles.abstractDescription.AbstractDescription;

import java.util.Iterator;
import java.util.Set;

public class AbstractCOPDescription extends AbstractDescription {
    AbstractDescription ad;
    Set<AbstractArrow> m_arrows;
    Set<String> m_dots;

    public AbstractCOPDescription(AbstractDescription spiderDescription, Set<AbstractArrow> arrowDescriptions) {
        ad = spiderDescription;
        m_arrows = arrowDescriptions;
    }

    public AbstractCOPDescription(AbstractDescription spiderDescription, Set<AbstractArrow> arrowDescriptions, Set<String> dots) {
        ad = spiderDescription;
        m_arrows = arrowDescriptions;
        m_dots = dots;
    }

    public AbstractDescription getPrimarySDDescription() {
        return ad;
    }

    @JsonIgnore
    public Iterator<AbstractArrow> getArrowIterator() {
        return m_arrows.iterator();
    }

    public Set<AbstractArrow> getArrows() {
        return m_arrows;
    }

    public Set<String> getDots() {
        return m_dots;
    }
}
