package lang;

import speedith.core.lang.Transformer;

import java.util.Iterator;

public abstract class ConceptDiagram implements Iterable<ConceptDiagram>, ConceptDiagramElement {

    public Iterator<ConceptDiagram> iterator() {
        return null;
    }

    public boolean isEqualvalentTo(ConceptDiagram cd) {
        return equals(cd);
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public ConceptDiagram transform(Transformer t) {
        return transform(t, true);
    }

    public ConceptDiagram transform(Transformer t, boolean trackParents) {
        return transform(t);
    }



    public String toString() {
        return "";
    }
}
