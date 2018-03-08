package lang;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;

/** Provides Concept Diagram factory methods and utilities.
 * Used to construct concept diagrams.
 *
 */
public class ConceptDiagrams {
    private ConceptDiagrams(){}

//    public static ConceptDiagram createBasicConceptDiagram(ArrayList<ClassObjectPropertyDiagram> COPs) {
//        return new ConceptDiagram(COPs);
//    }
//
//    public static ConceptDiagram createBasicConceptDiagram(ArrayList<ClassObjectPropertyDiagram> COPs, ArrayList<Arrow> arrows) {
//        return new ConceptDiagram(COPs, arrows);
//    }

    public static ConceptDiagram createBasicConceptDiagram(ArrayList<ClassObjectPropertyDiagram> COPs,
                                                           ArrayList<DatatypeDiagram> DTs) {
        return new ConceptDiagram(COPs, DTs);
    }

    public static ConceptDiagram createBasicConceptDiagram(ArrayList<ClassObjectPropertyDiagram> COPs,
                                                           ArrayList<DatatypeDiagram> DTs,
                                                           ArrayList<Arrow> arrows) {
        return new ConceptDiagram(COPs, DTs, arrows);
    }
}
