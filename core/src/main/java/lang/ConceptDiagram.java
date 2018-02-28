package lang;

import speedith.core.lang.Transformer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static speedith.i18n.Translations.i18n;

public class ConceptDiagram implements Serializable, Iterable<ConceptDiagram> {

    public static final String CDTextBasicId = "ConceptDiagram";
    public static final String CDTextCOPDiagramAttribute = "COPs";
    public static final String CDTextArrowsAttribute = "arrows";
    public static final String CDTextKnownEqualityAttribute = "equality";
    public static final String CDTextUnknownEqualityAttribute = "unknown_equality";

    private static final long serialVersionUID = -23423534656432L;
    private ArrayList<ClassObjectPropertyDiagram> classObjectPropertyDiagrams;
    private ArrayList<Arrow> arrows;
    private boolean hashInvalid = true;
    private int hash;

    ConceptDiagram(ArrayList<ClassObjectPropertyDiagram> COPs) {
        setClassObjectPropertyDiagrams(COPs);
        this.arrows = new ArrayList<>();
    }

    ConceptDiagram(ArrayList<ClassObjectPropertyDiagram> classObjectPropertyDiagrams, ArrayList<Arrow> arrows) {
        this.classObjectPropertyDiagrams = classObjectPropertyDiagrams;
        this.arrows = arrows;
    }

    public List<ClassObjectPropertyDiagram> getClassObjectPropertyDiagrams() {
        return Collections.unmodifiableList(classObjectPropertyDiagrams);
    }

    public List<Arrow> getArrows() {
        if (arrows != null) {
            return arrows;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if( other instanceof ConceptDiagram) {
            return __isCDEqual((ConceptDiagram) other);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (hashInvalid) {
            hash = 0;
            if (classObjectPropertyDiagrams != null) {
                for (ClassObjectPropertyDiagram sd: classObjectPropertyDiagrams) {
                    hash += sd.hashCode();
                }
            }
            if (arrows != null) {
                for (Arrow a: arrows) {
                    hash += a.hashCode();
                }
            }
            hashInvalid = false;
        }
        return hash;
    }

    public boolean isValid() {
        for (ClassObjectPropertyDiagram sd: classObjectPropertyDiagrams) {
            if (!sd.isValid()) {
                return false;
            }
        }
        return true;
    }

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

    private void printId(Appendable sb) throws IOException {
        sb.append(CDTextBasicId);
    }

    private void printArg(Appendable sb, int i) throws IOException {
        sb.append(CDTextCOPDiagramAttribute).append(Integer.toString(i)).append(" = ");
        classObjectPropertyDiagrams.get(i-1).toString(sb);
    }

    private void printArgs(Appendable sb) throws IOException {
        if (classObjectPropertyDiagrams.size() > 0) {
            printArg(sb, 1);
            for (int i = 2; i <= classObjectPropertyDiagrams.size(); i++) {
                printArg(sb.append(", "), i);
            }
        }
    }

    private boolean __isCDEqual(ConceptDiagram bcd) {
        return hashCode() == bcd.hashCode()
                && classObjectPropertyDiagrams.equals(bcd.classObjectPropertyDiagrams);
    }

    private void setClassObjectPropertyDiagrams(ArrayList<ClassObjectPropertyDiagram> sds) {
        for (ClassObjectPropertyDiagram sd: sds) {
            if (sd == null) {
                throw new IllegalArgumentException(i18n("ERR_OPERAND_NULL"));
            }
        }
        classObjectPropertyDiagrams = sds;
    }

    public Iterator<ConceptDiagram> iterator() {
        return null;
    }

    public boolean isEquivalentTo(ConceptDiagram cd) {
        return equals(cd);
    }

    public ConceptDiagram transform(Transformer t) {
        return transform(t, true);
    }

    public ConceptDiagram transform(Transformer t, boolean trackParents) {
        return transform(t);
    }
}
