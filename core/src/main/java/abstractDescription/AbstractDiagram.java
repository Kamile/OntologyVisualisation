package abstractDescription;

import icircles.abstractDescription.AbstractDescription;

import java.util.Set;

public class AbstractDiagram extends AbstractDescription {
    private AbstractDescription ad;
    private Set<AbstractArrow> m_arrows;
    private Set<String> m_dots;
    private Set<AbstractEquality> m_equalities;

    AbstractDiagram(AbstractDescription spiderDescription, Set<AbstractArrow> arrowDescriptions, Set<AbstractEquality> equalityDescriptions, Set<String> dots) {
        ad = spiderDescription;
        m_arrows = arrowDescriptions;
        m_dots = dots;
        m_equalities = equalityDescriptions;
    }

    public AbstractDescription getPrimarySDDescription() {
        return ad;
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
