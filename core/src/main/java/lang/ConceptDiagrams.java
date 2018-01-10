package lang;

import speedith.core.lang.SpiderDiagram;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/** Provides Concept Diagram factory methods and utilities.
 * Used to construct concept diagrams.
 *
 * pool is a list of living concept diagrams allowing reuse and faster comparison.
 */
public class ConceptDiagrams {
    private static final WeakHashMap<ConceptDiagram, WeakReference<ConceptDiagram>> pool = new WeakHashMap<>();

    private ConceptDiagrams(){}

    public static BasicConceptDiagram createBasicConceptDiagram(SpiderDiagram sd) {
        // TODO: call SpiderDiagrams method createPrimarySD.
        return null;
    }
}
