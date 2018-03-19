package lang;

import java.util.ArrayList;
import java.util.TreeSet;

public class PropertyDiagrams {
    private PropertyDiagrams(){}

    public static PropertyDiagram createBasicPropertyDiagram(ArrayList<ClassObjectPropertyDiagram> COPs, ArrayList<DatatypeDiagram> DTs, ArrayList<Arrow> arrows) {
        // Add initial T COP that is the source of arrows only
        TreeSet<String> singleT = new TreeSet<>();
        singleT.add("t");
        ClassObjectPropertyDiagram initialT =  new ClassObjectPropertyDiagram(null, null, null, null, null, singleT, null, true);
        COPs.add(initialT);
        return new PropertyDiagram(COPs, DTs, arrows);
    }
}
