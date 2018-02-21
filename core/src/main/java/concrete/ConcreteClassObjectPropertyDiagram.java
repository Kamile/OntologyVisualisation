package concrete;

import icircles.concreteDiagram.ConcreteDiagram;

import java.awt.geom.Rectangle2D;
import java.util.Set;

public class ConcreteClassObjectPropertyDiagram extends ConcreteDiagram {
    private ConcreteDiagram cd;
    public Set<ConcreteArrow> arrows;
    public Set<String> dots;

    public ConcreteClassObjectPropertyDiagram(ConcreteDiagram cd, Set<ConcreteArrow> arrows) {
        super(cd.getBox(), cd.getCircles(), cd.getShadedZones(), cd.getUnshadedZones(), cd.getSpiders());
        this.cd = cd;
        this.arrows = arrows;
    }

    /**
     * No ConcreteDiagram for PSD when we have dots
     * @param arrows
     * @param dots
     */
    public ConcreteClassObjectPropertyDiagram(Set<ConcreteArrow> arrows, Set<String> dots) {
        super(new Rectangle2D.Double(0.0D, 0.0D, (double)300, (double)300), null, null, null, null);
        this.arrows = arrows;
        this.dots = dots;
    }
}
