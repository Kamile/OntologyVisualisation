package abstractDescription;

import lang.ClassObjectPropertyDiagram;
import lang.DatatypeDiagram;

import java.util.*;

public class AbstractConceptDiagram extends AbstractDiagram {

    public AbstractConceptDiagram(HashMap<ClassObjectPropertyDiagram, AbstractCOP> COPDescriptionMap, Set<AbstractArrow> arrows) {
        super(COPDescriptionMap, arrows);
        m_COPs = COPDescriptionMap;
        m_arrows = arrows;
    }

    public AbstractConceptDiagram(HashMap<ClassObjectPropertyDiagram, AbstractCOP> COPDescriptionMap,
                           HashMap<DatatypeDiagram, AbstractDatatypeDiagram> DTDescriptionMap,
                           Set<AbstractArrow> arrows) {
        super(COPDescriptionMap, DTDescriptionMap, arrows);
    }
}
