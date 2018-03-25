package concrete;

import abstractDescription.AbstractEquality;

import java.awt.geom.Ellipse2D;

public class ConcreteEquality {
    private AbstractEquality equality;
    private int parentId;
    private Ellipse2D.Double source;
    private Ellipse2D.Double target;

    public ConcreteEquality(AbstractEquality equality, int parentId) {
        this.parentId = parentId;
        this.equality = equality;
    }

    public AbstractEquality getAbstractEquality() {
        return equality;
    }

    public int getParentId() {
        return parentId;
    }

    public void setSource(Ellipse2D.Double source) {
        this.source = source;
    }

    public void setTarget(Ellipse2D.Double target) {
        this.target = target;
    }

    public Ellipse2D.Double getSource() {
        return source;
    }

    public Ellipse2D.Double getTarget() {
        return target;
    }
}
