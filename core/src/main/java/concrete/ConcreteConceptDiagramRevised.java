package concrete;

import java.util.ArrayList;

public class ConcreteConceptDiagramRevised {

    private ArrayList<ConcreteArrow> arrows;
    private ArrayList<ConcreteClassObjectPropertyDiagram> COPs;

    public ConcreteConceptDiagramRevised(ArrayList<ConcreteClassObjectPropertyDiagram> COPs, ArrayList<ConcreteArrow> arrows) {
        this.arrows = arrows;
        this.COPs = COPs;
    }
}
