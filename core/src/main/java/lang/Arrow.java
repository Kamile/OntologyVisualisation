package lang;

public class Arrow {

    private final static String NO_LABEL = "";
    private String label;
    private String source;
    private String target;

    public Arrow(String source, String target) {
        this(NO_LABEL, source, target);
    }

    public Arrow(String label, String source, String target) {
        this.label = label;
        this.source = source;
        this.target = target;
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

}
