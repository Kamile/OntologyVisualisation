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

    public static ConceptDiagram createBasicConceptDiagram(ArrayList<ClassObjectPropertyDiagram> COPs,
                                                           ArrayList<DatatypeDiagram> DTs,
                                                           ArrayList<Arrow> arrows) {
        return new ConceptDiagram(COPs, DTs, arrows);
    }
}
