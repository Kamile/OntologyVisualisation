package abstractDescription;

import com.fasterxml.jackson.annotation.JsonProperty;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class AbstractArrow implements Comparable<AbstractArrow> {
    static Logger logger = Logger.getLogger(AbstractArrow.class.getName());
    static int id = 0;

    @JsonProperty("label")
    private String m_label;
    private int m_id;
    private boolean m_isAnon;

    /* Two of the following four will be set; one source, one target */
    private AbstractBasicRegion abr_source;
    private AbstractBasicRegion abr_target;
    private AbstractCurve c_source;
    private AbstractCurve c_target;

    public AbstractArrow(String label, boolean isAnon, AbstractBasicRegion source, AbstractBasicRegion target) {
        ++id;
        m_id = id;
        m_label = label;
        m_isAnon = isAnon;
        abr_source = source;
        abr_target = target;
    }

    public AbstractArrow(String label, boolean isAnon, AbstractBasicRegion source, AbstractCurve to) {
        ++id;
        m_id = id;
        m_label = label;
        m_isAnon = isAnon;
        abr_source = source;
        c_target = to;
    }

    public AbstractArrow(String label, boolean isAnon, AbstractCurve source, AbstractCurve target) {
        ++id;
        m_id = id;
        m_label = label;
        m_isAnon = isAnon;
        c_source = source;
        c_target = target;
    }

    public AbstractArrow(String label, boolean isAnon, AbstractCurve source, AbstractBasicRegion target) {
        ++id;
        m_id = id;
        m_label = label;
        m_isAnon = isAnon;
        c_source = source;
        abr_target = target;
    }

    public int getId() {
        return m_id;
    }

    public String getLabel() {
        return m_label;
    }

    public boolean isAnon() {
        return m_isAnon;
    }

    public Object getSource() {
        if (abr_source == null) {
            return c_source;
        } else {
            return abr_source;
        }
    }

    public Object getTarget() {
        if (abr_target == null) {
            return c_target;
        } else {
            return abr_target;
        }
    }

    @Override
    public int compareTo(AbstractArrow o) {
        if (o == null) {
            return 1;
        } else {
            int tmp = m_label.compareTo(o.getLabel());
            if (tmp != 0) {
                return tmp;
            } else {
                return m_id < o.getId() ? -1 : (m_id == o.getId() ? 0 : 1);
            }
        }
    }

    public String debug() {
        StringBuilder sb = new StringBuilder();
        sb.append("arrow(");
        sb.append(m_label);
        sb.append("_" + m_id + ")@");
        sb.append(this.hashCode());
        return logger.getEffectiveLevel() == Level.DEBUG ? sb.toString() : new String();
    }

    public double checksum() {
        logger.debug("build checksum from" + m_label + " (and not " + m_id + ")\ngiving " + m_label.hashCode());
        return (double) m_label.hashCode();
    }

    public String toString() {
        return m_label;
    }
}
