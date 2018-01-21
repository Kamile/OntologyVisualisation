package lang;

import speedith.core.lang.SpiderDiagram;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import static speedith.i18n.Translations.i18n;

public class ClassObjectPropertyDiagram implements Comparable<ClassObjectPropertyDiagram>, ConceptDiagramElement, Serializable {
    private ArrayList<SpiderDiagram> spiderDiagrams;
    private boolean hashInvalid;
    private int hash;

    public ClassObjectPropertyDiagram(ArrayList<SpiderDiagram> spiderDiagrams) {
        this.spiderDiagrams = spiderDiagrams;
    }

    public ArrayList<SpiderDiagram> getSpiderDiagrams() {
        return spiderDiagrams;
    }

    @Override
    public int compareTo(ClassObjectPropertyDiagram o) {
        return 0;
    }

    public boolean isValid() {
        for (SpiderDiagram sd: getSpiderDiagrams()) {
            if (!sd.isValid()) {
                return false;
            }
        }
        return true;
    }

    public void toString(Appendable sb) {
        try {
            if (sb == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
            }
            sb.append("(");
            for (SpiderDiagram sd: spiderDiagrams) {
                sb.append(sd.toString());
                sb.append(", ");
            }

            sb.append(")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }
}
