package abstractDescription;

import com.fasterxml.jackson.annotation.JsonProperty;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import lang.ArrowLabel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;

import static speedith.i18n.Translations.i18n;

public class AbstractArrow implements Comparable<AbstractArrow> {
    private static Logger logger = Logger.getLogger(AbstractArrow.class.getName());
    private static int id = 0;

    @JsonProperty("label")
    private ArrowLabel label;
    private int mId;
    private boolean isAnon;
    private String sourceLabel;
    private String targetLabel;
    private int sourceId;
    private int targetId;

    /* Two of the following four will be set; one source, one target */
    private AbstractBasicRegion abrSource;
    private AbstractBasicRegion abrTarget;
    private AbstractCurve cSource;
    private AbstractCurve cTarget;

    private AbstractArrow(ArrowLabel label, boolean isAnon, String sourceLabel, String targetLabel) {
        ++id;
        mId = id;
        this.label = label;
        this.isAnon = isAnon;
        this.sourceLabel = sourceLabel;
        this.targetLabel = targetLabel;
    }

    public AbstractArrow(ArrowLabel label, boolean isAnon,
                         AbstractBasicRegion source, AbstractBasicRegion target,
                         String sourceLabel, String targetLabel) {
        this(label, isAnon, sourceLabel, targetLabel);
        abrSource = source;
        abrTarget = target;
    }

    public AbstractArrow(ArrowLabel label, boolean isAnon, AbstractBasicRegion source, AbstractCurve to,
                         String sourceLabel, String targetLabel) {
        this(label, isAnon, sourceLabel, targetLabel);
        abrSource = source;
        cTarget = to;
    }

    public AbstractArrow(ArrowLabel label, boolean isAnon, AbstractCurve source, AbstractCurve target,
                         String sourceLabel, String targetLabel) {
        this(label, isAnon, sourceLabel, targetLabel);
        cSource = source;
        cTarget = target;
    }

    public AbstractArrow(ArrowLabel label, boolean isAnon, AbstractCurve source, AbstractBasicRegion target,
                         String sourceLabel, String targetLabel) {
        this(label, isAnon, sourceLabel, targetLabel);
        cSource = source;
        abrTarget = target;
    }

    public int getId() {
        return mId;
    }

    public ArrowLabel getLabel() {
        return label;
    }

    public boolean isAnon() {
        return isAnon;
    }

    private Object getSource() {
        if (abrSource == null) {
            return cSource;
        } else {
            return abrSource;
        }
    }

    private Object getTarget() {
        if (abrTarget == null) {
            return cTarget;
        } else {
            return abrTarget;
        }
    }

    public String getSourceLabel() {
        return sourceLabel;
    }

    public String getTargetLabel() {
        return targetLabel;
    }

    public void setSourceId(int id) {
        sourceId = id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setTargetId(int id) {
        targetId = id;
    }

    public int getTargetId() {
        return targetId;
    }

    @Override
    public int compareTo(AbstractArrow o) {
        if (o == null) {
            return 1;
        } else {
            int tmp = label.compareTo(o.getLabel());
            if (tmp != 0) {
                return tmp;
            } else {
                return mId < o.getId() ? -1 : (mId == o.getId() ? 0 : 1);
            }
        }
    }

    public String debug() {
        StringBuilder sb = new StringBuilder();
        sb.append("arrow(");
        sb.append(label);
        sb.append("_" + mId + ")@");
        sb.append(this.hashCode());
        return logger.getEffectiveLevel() == Level.DEBUG ? sb.toString() : new String();
    }

    public double checksum() {
        logger.debug("build checksum from" + label + " (and not " + mId + ")\ngiving " + label.hashCode());
        return (double) label.hashCode();
    }

    public void toString(Appendable sb) {
        try {
            if (sb == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
            }
            sb.append("Arrow: id= ").append(String.valueOf(mId));
            sb.append(", label= ").append(label.toString());
            sb.append(", source= ").append(getSource().toString());
            sb.append(", target= ").append(getTarget().toString());
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
