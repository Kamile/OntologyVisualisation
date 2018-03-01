package abstractDescription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import icircles.abstractDescription.AbstractDescription;
import lang.Equality;

import java.util.Iterator;
import java.util.Set;

public class AbstractCOP extends AbstractDescription {
    private AbstractDescription ad;
    private Set<AbstractArrow> m_arrows;
    private Set<String> m_dots;
    private Set<AbstractEquality> m_equalities;

    public AbstractCOP(AbstractDescription spiderDescription, Set<AbstractArrow> arrowDescriptions) {
        ad = spiderDescription;
        m_arrows = arrowDescriptions;
    }

    public AbstractCOP(AbstractDescription spiderDescription, Set<AbstractArrow> arrowDescriptions, Set<String> dots) {
        ad = spiderDescription;
        m_arrows = arrowDescriptions;
        m_dots = dots;
    }

    public AbstractCOP(AbstractDescription spiderDescription, Set<AbstractArrow> arrowDescriptions, Set<AbstractEquality> equalityDescriptions, Set<String> dots) {
        ad = spiderDescription;
        m_arrows = arrowDescriptions;
        m_dots = dots;
        m_equalities = equalityDescriptions;
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

    public Set<AbstractEquality> getEqualities() {
        return m_equalities;
    }
}
