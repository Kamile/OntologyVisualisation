package lang;

import java.io.IOException;
import java.util.ArrayList;

/**
 * All property diagrams include a boundary rectangle that contains the variable t
 */
public class PropertyDiagram extends Diagram {
    public static final String PDBasicId = "PropertyDiagram";
    private static final long serialVersionUID = -1382957832471L;
    private boolean hashInvalid = true;
    private int hash;

    PropertyDiagram(ArrayList<ClassObjectPropertyDiagram> COPs, ArrayList<DatatypeDiagram> DTs, ArrayList<Arrow> arrows) {
        super(COPs, DTs, arrows);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if( other instanceof PropertyDiagram) {
            return __isPDEqual((PropertyDiagram) other);
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

    @Override
    public boolean isValid() {
        for (ClassObjectPropertyDiagram sd : classObjectPropertyDiagrams) {
            if (!sd.isValid()) {
                return false;
            }
        }

        for (DatatypeDiagram sd: datatypeDiagrams) {
            if (!sd.isValid()) {
                return false;
            }
        }

        return true;
    }

    @Override
    void printId(Appendable sb) throws IOException {
        sb.append(PDBasicId);
    }

    @Override
    void printArg(Appendable sb, int i) throws IOException {
        sb.append(COPDiagramAttribute).append(Integer.toString(i)).append(" = ");
        classObjectPropertyDiagrams.get(i-1).toString(sb);
    }

    @Override
    void printArgs(Appendable sb) throws IOException {
        if (classObjectPropertyDiagrams.size() > 0) {
            printArg(sb, 1);
            for (int i = 2; i <= classObjectPropertyDiagrams.size(); i++) {
                printArg(sb.append(", "), i);
            }
        }
    }

    private boolean __isPDEqual(PropertyDiagram pd) {
        return hashCode() == pd.hashCode()
                && classObjectPropertyDiagrams.equals(pd.classObjectPropertyDiagrams)
                && datatypeDiagrams.equals(pd.datatypeDiagrams);
    }
}
