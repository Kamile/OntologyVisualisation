package lang;

import java.io.IOException;

import static speedith.i18n.Translations.i18n;

public class Equality {

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
}
