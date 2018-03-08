package abstractDescription;

import lang.ClassObjectPropertyDiagram;
import lang.DatatypeDiagram;

import java.util.HashMap;
import java.util.Set;

public class AbstractPropertyDiagram extends AbstractDiagram {
    public AbstractPropertyDiagram(HashMap<ClassObjectPropertyDiagram, AbstractCOP> COPDescriptionMap, HashMap<DatatypeDiagram, AbstractDatatypeDiagram> DTDescriptionMap, Set<AbstractArrow> arrows) {
        super(COPDescriptionMap, DTDescriptionMap, arrows);
    }
}
