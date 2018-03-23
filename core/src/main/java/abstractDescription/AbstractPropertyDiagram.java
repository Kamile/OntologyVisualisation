package abstractDescription;

import java.util.Set;

public class AbstractPropertyDiagram extends AbstractDiagram {
    public AbstractPropertyDiagram(Set<AbstractCOP> COPs, Set<AbstractDT> DTs, Set<AbstractArrow> arrows) {
        super(COPs, DTs, arrows);
    }
}
