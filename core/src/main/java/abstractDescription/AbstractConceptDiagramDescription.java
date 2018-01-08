package abstractDescription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class AbstractConceptDiagramDescription extends AbstractDescription {
    ArrayList<AbstractArrow> m_arrows;

    public AbstractConceptDiagramDescription() {
        super();
        m_arrows = new ArrayList<>();
    }

    public AbstractConceptDiagramDescription(Set<AbstractCurve> contours, Set<AbstractBasicRegion> zones, Set<AbstractBasicRegion> shaded_zones) {
        super(contours, zones, shaded_zones);
        m_arrows = new ArrayList<>();
    }

    public AbstractConceptDiagramDescription(Set<AbstractCurve> contours, Set<AbstractBasicRegion> zones) {
        super(contours, zones);
        m_arrows = new ArrayList<>();
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
