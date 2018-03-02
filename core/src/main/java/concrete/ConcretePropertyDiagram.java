package concrete;

import icircles.concreteDiagram.ConcreteDiagram;

import java.util.Set;

/**
 *
 */
public class ConcretePropertyDiagram extends ConcreteBaseDiagram {

    public ConcretePropertyDiagram(ConcreteDiagram cd) {
        super(cd);
    }

    public ConcretePropertyDiagram(Set<String> dots) {
        super(dots);
    }
}
