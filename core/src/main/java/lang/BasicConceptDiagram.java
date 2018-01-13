package lang;

import speedith.core.lang.SpiderDiagram;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static speedith.i18n.Translations.i18n;

/**
 * Basic concept diagram that is just a set of primary spider
 * diagrams with no arrows.
 */
public class BasicConceptDiagram extends ConceptDiagram implements Serializable {

    public static final String CDTextBasicId = "BasicCD";

    public static final String CDTextSpiderDiagramAttribute = "spider_diagrams";
    public static final String CDTextArrowsAttribute = "arrows";

    private static final long serialVersionUID = -23423534656432L;
    private ArrayList<SpiderDiagram> spiderDiagrams;
    private ArrayList<Arrow> arrows;
    private boolean hashInvalid = true;
    private int hash;
    private Boolean valid;
    private int subDiagramCount = -1;

    BasicConceptDiagram(ArrayList<SpiderDiagram> sds) {
        setSpiderDiagrams(sds);
        this.arrows = new ArrayList<>();
    }

    BasicConceptDiagram(ArrayList<SpiderDiagram> sds, ArrayList<Arrow> arrows) {
        setSpiderDiagrams(sds);
        this.arrows = arrows;
    }

    public List<SpiderDiagram> getSpiderDiagrams() {
        return Collections.unmodifiableList(spiderDiagrams);
    }

    public List<Arrow> getArrows() {
        return Collections.unmodifiableList(arrows);
    }

    public int getSpiderDiagramsCount() {
        return spiderDiagrams.size();
    }

    public int getArrowCount() {
        return arrows.size();
    }

    public SpiderDiagram getSpiderDiagram(int index) {
        return spiderDiagrams.get(index);
    }

    public Arrow getArrow(int index) {
        return arrows.get(index);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if( other instanceof BasicConceptDiagram) {
            return __isCDEqual((BasicConceptDiagram) other);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (hashInvalid) {
            hash = 0;
            if (spiderDiagrams != null) {
                for (SpiderDiagram sd: spiderDiagrams) {
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
        for (SpiderDiagram sd: spiderDiagrams) {
            if (!sd.isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override
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
        sb.append(CDTextSpiderDiagramAttribute).append(Integer.toString(i)).append(" = ");
        spiderDiagrams.get(i-1).toString(sb);
    }

    private void printArgs(Appendable sb) throws IOException {
        if (spiderDiagrams.size() > 0) {
            printArg(sb, 1);
            for (int i = 2; i <= spiderDiagrams.size(); i++) {
                printArg(sb.append(", "), i);
            }
        }
    }

    private boolean __isCDEqual(BasicConceptDiagram bcd) {
        return hashCode() == bcd.hashCode()
                && spiderDiagrams.equals(bcd.spiderDiagrams);
    }

    private void setSpiderDiagrams(ArrayList<SpiderDiagram> sds) {
        for (SpiderDiagram sd: sds) {
            if (sd == null) {
                throw new IllegalArgumentException(i18n("ERR_OPERAND_NULL"));
            }
        }
        spiderDiagrams = sds;
    }

    private void setArrows(ArrayList<Arrow> arrows) {
        for (Arrow a: arrows) {
            if (a == null) {
                throw new IllegalArgumentException(i18n("ERR_OPERAND_NULL"));
            }
        }
        this.arrows = arrows;
    }
}
