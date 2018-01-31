package concrete;

import icircles.concreteDiagram.*;
import java.util.Set;

public class ConcreteClassObjectPropertyDiagram extends ConcreteDiagram {
    private ConcreteDiagram cd;
    public Set<ConcreteArrow> arrows;

    public ConcreteClassObjectPropertyDiagram(ConcreteDiagram cd, Set<ConcreteArrow> arrows) {
        super(cd.getBox(), cd.getCircles(), cd.getShadedZones(), cd.getUnshadedZones(), cd.getSpiders());
        this.cd = cd;
        this.arrows = arrows;
    }
}
