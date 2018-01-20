package concrete;

import java.util.ArrayList;

public class ConcreteConceptDiagramRevised {

    private ArrayList<ConcreteArrow> arrows;
    private ArrayList<ConcreteCOPDiagram> COPs;

    public ConcreteConceptDiagramRevised(ArrayList<ConcreteCOPDiagram> COPs, ArrayList<ConcreteArrow> arrows) {
        this.arrows = arrows;
        this.COPs = COPs;
    }
}
