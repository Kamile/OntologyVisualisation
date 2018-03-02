package lang;

import java.util.ArrayList;

/**
 * All property diagrams include a boundary rectangle that contains the variable t
 */
public class PropertyDiagram extends Diagram{

    public static final String PDBasicId = "PropertyDiagram";

    private static final long serialVersionUID = -1382957832471L;
    private boolean hashInvalid = true;
    private int hash;

    PropertyDiagram(ArrayList<ClassObjectPropertyDiagram> COPs, ArrayList<DatatypeDiagram> DTs) {
        super(COPs, DTs);
    }

    PropertyDiagram(ArrayList<ClassObjectPropertyDiagram> COPs, ArrayList<DatatypeDiagram> DTs, ArrayList<Arrow> arrows) {
        super(COPs, DTs, arrows);
    }




}
