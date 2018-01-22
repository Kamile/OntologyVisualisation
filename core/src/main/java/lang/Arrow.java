package lang;

import java.io.IOException;
import java.io.Serializable;

import static speedith.i18n.Translations.i18n;

public class Arrow implements Comparable<Arrow>, ConceptDiagramElement, Serializable{

    private final static String NO_LABEL = "";
    private String label;
    private String source;
    private String target;
    private String cardinalityOperator;
    private int cardinalityArgument;
    private boolean isDashed;
    private boolean hashInvalid = true;
    private int hash;


    public Arrow(String source, String target) {
        this(NO_LABEL, source, target);
    }

    public Arrow(String label, String source, String target) {
        this.label = label;
        this.source = source;
        this.target = target;
    }

    public Arrow(String label, String source, String target, String cardinalityOperator, int cardinalityArgument, boolean isDashed) {
        this.label = label;
        this.source = source;
        this.target = target;
        this.cardinalityArgument = cardinalityArgument;
        this.cardinalityOperator = cardinalityOperator;
        this.isDashed = isDashed;
    }

    public String getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public boolean isDashed() {
        return isDashed;
    }

    public String getCardinalityOperator() {
        return cardinalityOperator;
    }

    public int getCardinalityArgument() {
        return cardinalityArgument;
    }

    @Override
    public int compareTo(Arrow o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (this == o) {
            return 0;
        } else if (this.source != o.source){
            return this.source.compareTo(o.source);
        } else {
            return this.target.compareTo(o.target);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Arrow) {
            Arrow a = (Arrow) obj;
            return label.equals(a.label) && source.equals(a.source) && target.equals(a.target);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashInvalid) {
            hash = (label == null ? 0 : label.hashCode())
                    + (source == null ? 0: source.hashCode())
                    + (target == null ? 0: target.hashCode());
            hashInvalid = false;
        }
        return hash;
    }

    public void toString(Appendable sb) {
        try {
            if (sb == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
            }
            sb.append('[');
            sb.append(label);
            sb.append(", ");
            sb.append(source);
            sb.append(", ");
            sb.append(target);
            sb.append(']');
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
