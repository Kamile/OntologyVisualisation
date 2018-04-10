package abstractDescription;

import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractDescription;

import java.util.Set;

public class AbstractSubDiagram extends AbstractDescription {
    private AbstractDescription ad;
    private Set<AbstractArrow> m_arrows;
    private Set<String> m_dots;
    private Set<AbstractEquality> m_equalities;
    private Set<AbstractBasicRegion> m_highlightedZones;
    private boolean m_containsInitialT;

    AbstractSubDiagram(AbstractDescription spiderDescription, Set<AbstractBasicRegion> highlightedZones, Set<AbstractArrow> arrowDescriptions, Set<AbstractEquality> equalityDescriptions, Set<String> dots, boolean containsInitialT) {
        ad = spiderDescription;
        m_arrows = arrowDescriptions;
        m_highlightedZones = highlightedZones;
        m_dots = dots;
        m_equalities = equalityDescriptions;
        m_containsInitialT = containsInitialT;
    }

    public AbstractDescription getPrimarySDDescription() {
        return ad;
    }

    public Set<AbstractBasicRegion> getHighlightedZones() {
        return m_highlightedZones;
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

    public boolean containsInitialT() {
        return m_containsInitialT;
    }
}
