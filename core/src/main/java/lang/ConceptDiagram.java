package lang;

import speedith.core.lang.Transformer;

import java.io.IOException;
import java.util.Iterator;

public abstract class ConceptDiagram implements Iterable<ConceptDiagram>, ConceptDiagramElement {

    public Iterator<ConceptDiagram> iterator() {
        return null;
    }

    public boolean isEqualvalentTo(ConceptDiagram cd) {
        return equals(cd);
    }

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();

    public ConceptDiagram transform(Transformer t) {
        return transform(t, true);
    }

    public ConceptDiagram transform(Transformer t, boolean trackParents) {
        return transform(t);
    }

    public abstract boolean isValid();

    public abstract String toString();

    public abstract void toString(Appendable sb) throws IOException;
}
