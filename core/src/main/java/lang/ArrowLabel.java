package lang;

import java.io.Serializable;

public class ArrowLabel implements Comparable<ArrowLabel>, Serializable {
    private String label;
    private String cardinalityOperator;
    private int cardinalityArgument;

    public ArrowLabel(String label, String cardinalityOperator, int cardinalityArgument) {
        this.label = label;
        this.cardinalityOperator = cardinalityOperator;
        this.cardinalityArgument = cardinalityArgument;
    }

    public String getLabel() {
        return label;
    }

    public String getCardinalityOperator() {
        return getOperator(cardinalityOperator);
    }

    public String getCardinalityArgument() {
        if (cardinalityArgument == 0) {
            return "";
        }
        return String.valueOf(cardinalityArgument);
    }

    @Override
    public int compareTo(ArrowLabel o) {
        return (label+cardinalityOperator+cardinalityArgument).compareTo(o.getLabel() + o.getCardinalityOperator() + o.getCardinalityArgument());
    }

    private static String getOperator(String operator) {
        if (operator == null) {
            return "";
        }
        operator = operator.toLowerCase();
        switch (operator) {
            case "leq":
                return "<=";
            case "greq":
                return ">=";
            case "eq":
                return "=";
            default:
                return operator;
        }
    }
}
