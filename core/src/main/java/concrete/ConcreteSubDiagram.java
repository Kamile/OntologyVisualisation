package concrete;

import icircles.concreteDiagram.ConcreteDiagram;

import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ConcreteSubDiagram extends ConcreteDiagram {
    public Set<ConcreteArrow> arrows;
    public Set<String> dots;
    public Set<ConcreteEquality> equalities;
    public boolean containsInitialT;

    ConcreteSubDiagram(ConcreteDiagram cd, Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities, boolean containsInitialT) {
        super(cd.getBox(), cd.getCircles(), cd.getShadedZones(), cd.getUnshadedZones(), cd.getSpiders());
        this.arrows = arrows;
        this.equalities = equalities;
        this.containsInitialT = containsInitialT;
        dots = new TreeSet<>(); // empty
    }

    /**
     * No ConcreteDiagram for PSD when we have dots
     * @param arrows
     * @param dots
     */
     ConcreteSubDiagram(Set<ConcreteArrow> arrows, Set<String> dots, Set<ConcreteEquality> equalities, boolean containsInitialT) {
        super(new Rectangle2D.Double(0.0D, 0.0D, (double)300, (double)300), null, null, null, null);
        this.arrows = arrows;
        this.dots = dots;
        this.equalities = equalities;
        this.containsInitialT = containsInitialT;
    }

    /**
     * Datatype diagrams have no arrows or equalities since we deal only with literals
     * @param cd
     */
    ConcreteSubDiagram(ConcreteDiagram cd) {
        super(cd.getBox(), cd.getCircles(), cd.getShadedZones(), cd.getUnshadedZones(), cd.getSpiders());
        arrows = new HashSet<>(); // empty
        equalities = new HashSet<>(); // empty
        dots = new HashSet<>(); // empty
    }

    ConcreteSubDiagram(Set<String> dots) {
        super(new Rectangle2D.Double(0.0D, 0.0D, (double)300, (double)300), null, null, null, null);
        this.dots = dots;
        arrows = new HashSet<>(); // empty
        equalities = new HashSet<>(); //empty;
    }
}
