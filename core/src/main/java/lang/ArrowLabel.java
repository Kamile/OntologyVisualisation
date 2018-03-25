package lang;

import java.io.Serializable;

public class ArrowLabel implements Comparable<ArrowLabel>, Serializable {
    private String property;
    private String cardinalityOperator;
    private int cardinalityArgument;

    public ArrowLabel(String property, String cardinalityOperator, int cardinalityArgument) {
        this.property = property;
        this.cardinalityOperator = cardinalityOperator;
        this.cardinalityArgument = cardinalityArgument;
    }

    public String getProperty() {
        return property;
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

    public String getCardinality() {
        return getCardinalityOperator() + getCardinalityArgument();
    }

    public String getFullLabel() {
        return getProperty() + getCardinalityOperator() + getCardinalityArgument();
    }

    @Override
    public int compareTo(ArrowLabel o) {
        return (getFullLabel()).compareTo(o.getFullLabel());
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
