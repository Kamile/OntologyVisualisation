package abstractDescription;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class AbstractArrow implements Comparable<AbstractArrow> {
    static Logger logger = Logger.getLogger(AbstractArrow.class.getName());
    static int id = 0;

    @JsonProperty("label")
    private String m_label;
    private int m_id;
    private boolean m_isAnon;

    public AbstractArrow() {

    }

    public AbstractArrow(String label) {
        ++id;
        this.m_id = id;
        this.m_label = label;
        this.m_isAnon = false;
    }

    public AbstractArrow(String label, boolean isAnon) {
        ++id;
        this.m_id = id;
        this.m_label = label;
        this.m_isAnon = isAnon;
    }

    public int getId() {
        return m_id;
    }

    public String getLabel() {
        return m_label;
    }

    public AbstractArrow clone() {
        return new AbstractArrow(m_label, m_isAnon);
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

    public boolean matchesLabel(AbstractArrow a) {
        return m_label.equals(a.getLabel());
    }

    public String debugWithId() {
        return this.debug() + "_" + m_id;
    }

    public double checksum() {
        logger.debug("build checksum from" + m_label + " (and not " + m_id + ")\ngiving " + m_label.hashCode());
        return (double) m_label.hashCode();
    }

    public String toString() {
        return m_label;
    }
}
