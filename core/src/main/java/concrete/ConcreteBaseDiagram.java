package concrete;

import icircles.concreteDiagram.ConcreteDiagram;

import java.awt.geom.Rectangle2D;
import java.util.Set;

public class ConcreteBaseDiagram extends ConcreteDiagram {
    public Set<ConcreteArrow> arrows;
    public Set<String> dots;
    public Set<ConcreteEquality> equalities;

    public ConcreteBaseDiagram(ConcreteDiagram cd, Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities) {
        super(cd.getBox(), cd.getCircles(), cd.getShadedZones(), cd.getUnshadedZones(), cd.getSpiders());
        this.arrows = arrows;
        this.equalities = equalities;
    }

    /**
     * No ConcreteDiagram for PSD when we have dots
     * @param arrows
     * @param dots
     */
    public ConcreteBaseDiagram(Set<ConcreteArrow> arrows, Set<String> dots, Set<ConcreteEquality> equalities) {
        super(new Rectangle2D.Double(0.0D, 0.0D, (double)300, (double)300), null, null, null, null);
        this.arrows = arrows;
        this.dots = dots;
        this.equalities = equalities;
    }

    /**
     * Datatype diagrams have no arrows or equalities since we deal only with literals
     * @param cd
     */
    public ConcreteBaseDiagram(ConcreteDiagram cd) {
        super(cd.getBox(), cd.getCircles(), cd.getShadedZones(), cd.getUnshadedZones(), cd.getSpiders());
    }

    public ConcreteBaseDiagram(Set<String> dots) {
        super(new Rectangle2D.Double(0.0D, 0.0D, (double)300, (double)300), null, null, null, null);
        this.dots = dots;
    }

}
