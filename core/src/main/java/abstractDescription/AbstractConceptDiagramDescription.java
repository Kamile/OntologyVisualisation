package abstractDescription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
import lang.BoundaryRectangle;

import java.util.*;

public class AbstractConceptDiagramDescription {
    private Set<AbstractArrow> m_arrows;
    private HashMap<BoundaryRectangle, Set<AbstractDescription>> m_spiderDiagrams;

    public AbstractConceptDiagramDescription() {
        super();
        m_arrows = new TreeSet<>();
    }

    public AbstractConceptDiagramDescription(HashMap<BoundaryRectangle, Set<AbstractDescription>> primarySpiderDiagrams, Set<AbstractArrow> arrows) {
        m_spiderDiagrams = primarySpiderDiagrams;
        m_arrows = arrows;
    }

    public HashMap<BoundaryRectangle, Set<AbstractDescription>> getSpiderDescriptions() {
        return m_spiderDiagrams;
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
        //TODO: Proper implementation...
        return true;
    }
}
