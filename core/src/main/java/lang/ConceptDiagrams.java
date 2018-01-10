package lang;

import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.reader.ReadingException;
import speedith.core.lang.reader.SpiderDiagramsReader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.WeakHashMap;

/** Provides Concept Diagram factory methods and utilities.
 * Used to construct concept diagrams.
 *
 * pool is a list of living concept diagrams allowing reuse and faster comparison.
 */
public class ConceptDiagrams {
    private static final WeakHashMap<ConceptDiagram, WeakReference<ConceptDiagram>> pool = new WeakHashMap<>();

    private ConceptDiagrams(){}

    public static BasicConceptDiagram createBasicConceptDiagram(Collection<String> sds) {
        ArrayList<SpiderDiagram> spiderDiagrams = new ArrayList<>();

        try {
            for (String s : sds) {
                System.out.println("Reading spider diagrams");
                spiderDiagrams.add(SpiderDiagramsReader.readSpiderDiagram(s));
            }
        } catch (ReadingException e) {
            System.err.println("Couldn't parse one or more spider diagrams");
        }
        return new BasicConceptDiagram(spiderDiagrams);
    }
}
