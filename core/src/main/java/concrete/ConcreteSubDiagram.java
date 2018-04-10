package concrete;

import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteZone;

import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ConcreteSubDiagram extends ConcreteDiagram {
    private int id;
    public Set<ConcreteArrow> arrows;
    public Set<String> dots;
    public Set<ConcreteEquality> equalities;
    public Set<ConcreteZone> highlightedZones;

    ConcreteSubDiagram(int id, ConcreteDiagram cd, Set<ConcreteZone> highlightedZones, Set<ConcreteArrow> arrows, Set<ConcreteEquality> equalities) {
        super(cd.getBox(), cd.getCircles(), cd.getShadedZones(), cd.getUnshadedZones(), cd.getSpiders());
        this.id = id;
        this.arrows = arrows;
        this.equalities = equalities;
        dots = new TreeSet<>(); // empty
    }

    /**
     * No ConcreteDiagram for PSD when we have dots
     * @param arrows
     * @param dots
     */
     ConcreteSubDiagram(int id, Set<ConcreteArrow> arrows, Set<String> dots, Set<ConcreteEquality> equalities) {
        super(new Rectangle2D.Double(0.0D, 0.0D, (double)300, (double)300), null, null, null, null);
        this.id = id;
        this.arrows = arrows;
        this.dots = dots;
        this.equalities = equalities;
    }

    /**
     * Datatype diagrams have no arrows or equalities since we deal only with literals
     * @param cd
     */
    ConcreteSubDiagram(int id, ConcreteDiagram cd, Set<ConcreteZone> highlightedZones) {
        super(cd.getBox(), cd.getCircles(), cd.getShadedZones(), cd.getUnshadedZones(), cd.getSpiders());
        this.id = id;
        arrows = new HashSet<>(); // empty
        equalities = new HashSet<>(); // empty
        dots = new HashSet<>(); // empty
    }

    ConcreteSubDiagram(int id, Set<String> dots) {
        super(new Rectangle2D.Double(0.0D, 0.0D, (double)300, (double)300), null, null, null, null);
        this.id = id;
        this.dots = dots;
        arrows = new HashSet<>(); // empty
        equalities = new HashSet<>(); //empty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
