package lang;

import java.util.ArrayList;

public class PropertyDiagrams {
    private PropertyDiagrams(){}

    public static PropertyDiagram createBasicPropertyDiagram(ArrayList<ClassObjectPropertyDiagram> COPs, ArrayList<DatatypeDiagram> DTs) {
        return new PropertyDiagram(COPs, DTs);
    }

    public static PropertyDiagram createBasicPropertyDiagram(ArrayList<ClassObjectPropertyDiagram> COPs, ArrayList<DatatypeDiagram> DTs, ArrayList<Arrow> arrows) {
        return new PropertyDiagram(COPs, DTs, arrows);
    }
}
