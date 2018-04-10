package lang;

import speedith.core.lang.Transformer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static speedith.i18n.Translations.i18n;

public abstract class Diagram implements Serializable, Iterable<Diagram> {
    public static final String COPDiagramAttribute = "COPs";
    public static final String DTDiagramAttribute = "DTs";
    public static final String ArrowsAttribute = "arrows";
    public static final String KnownEqualityAttribute = "equality";
    public static final String UnknownEqualityAttribute = "unknown_equality";
    public static final String HighlightedZonesAttribute = "highlighted_zones";
    public static final String IdAttribute = "id";

    ArrayList<ClassObjectPropertyDiagram> classObjectPropertyDiagrams;
    ArrayList<DatatypeDiagram> datatypeDiagrams;
    ArrayList<Arrow> arrows;

    Diagram(ArrayList<ClassObjectPropertyDiagram> COPs, ArrayList<DatatypeDiagram> DTs, ArrayList<Arrow> arrows) {
        classObjectPropertyDiagrams = COPs;
        datatypeDiagrams = DTs;
        this.arrows = arrows;
    }

    public List<ClassObjectPropertyDiagram> getClassObjectPropertyDiagrams() {
        if (classObjectPropertyDiagrams != null) {
            return Collections.unmodifiableList(classObjectPropertyDiagrams);
        } else {
            return null;
        }
    }

    public List<DatatypeDiagram> getDatatypeDiagrams() {
        if (datatypeDiagrams != null) {
            return Collections.unmodifiableList(datatypeDiagrams);
        } else {
            return null;
        }
    }

    public List<Arrow> getArrows() {
        if (arrows != null) {
            return arrows;
        } else {
            return new ArrayList<>();
        }
    }

    public abstract boolean equals(Object other);

    public abstract int hashCode();

    public abstract boolean isValid();

    public void toString(Appendable sb) throws IOException {
        if (sb == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
        }
        printId(sb);
        sb.append(" {");
        printArgs(sb);
        sb.append(" }");
    }

    @Override
    public String toString() {
        try {
            final StringBuilder sb = new StringBuilder();
            toString(sb);
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    abstract void printId(Appendable sb) throws IOException;

    abstract void printArg(Appendable sb, int i) throws IOException;

    abstract void printArgs(Appendable sb) throws IOException;

    public Iterator<Diagram> iterator() {
        return null;
    }

    public boolean isEquivalentTo(Diagram cd) {
        return equals(cd);
    }

    public Diagram transform(Transformer t) {
        return transform(t, true);
    }

    public Diagram transform(Transformer t, boolean trackParents) {
        return transform(t);
    }
}
