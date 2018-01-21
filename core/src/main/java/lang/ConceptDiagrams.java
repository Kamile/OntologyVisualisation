package lang;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;

/** Provides Concept Diagram factory methods and utilities.
 * Used to construct concept diagrams.
 *
 * pool is a list of living concept diagrams allowing reuse and faster comparison.
 */
public class ConceptDiagrams {
    private static final WeakHashMap<ConceptDiagram, WeakReference<ConceptDiagram>> pool = new WeakHashMap<>();

    private ConceptDiagrams(){}

    public static ConceptDiagram createBasicConceptDiagram(ArrayList<ClassObjectPropertyDiagram> br) {
        return new ConceptDiagram(br);
    }

    public static ConceptDiagram createBasicConceptDiagram(ArrayList<ClassObjectPropertyDiagram> br, ArrayList<Arrow> arrows) {
        return new ConceptDiagram(br, arrows);
    }
}
