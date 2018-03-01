package lang;

import java.io.IOException;
import java.io.Serializable;

import static speedith.i18n.Translations.i18n;

public class Equality implements Comparable<Equality>, Serializable {

    private String arg1;
    private String arg2;
    private boolean isKnown;

    public Equality(String arg1, String arg2, boolean isKnown) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.isKnown = isKnown;
    }

    public Equality(String arg1, String arg2) {
        this(arg1, arg2, true);
    }

    public String getArg1() {
        return arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public boolean isKnown() {
        return isKnown;
    }

    private void toString(Appendable sb) {
        try {
            if (sb == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
            }
            sb.append(arg1);
            sb.append(", ");
            sb.append(arg2);
            sb.append(", ");
            sb.append(isKnown ? "known equality" : "unknown equality");
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

    @Override
    public int compareTo(Equality o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (this == o) {
            return 0;
        } else if (this.arg1 != o.arg1){
            return this.arg1.compareTo(o.arg2);
        } else {
            return this.arg2.compareTo(o.arg2);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Equality) {
            Equality e = (Equality) obj;
            return arg1.equals(e.getArg1()) && arg2.equals(e.getArg2());
        }
        return false;
    }

}
