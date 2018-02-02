package concrete;

import icircles.concreteDiagram.*;
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

    public ConcreteClassObjectPropertyDiagram(ConcreteDiagram cd, Set<ConcreteArrow> arrows, Set<String> dots) {
        super(cd.getBox(), cd.getCircles(), cd.getShadedZones(), cd.getUnshadedZones(), cd.getSpiders());
        this.cd = cd;
        this.arrows = arrows;
        this.dots = dots;
    }
}
