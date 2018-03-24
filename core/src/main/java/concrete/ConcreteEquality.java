package concrete;

import abstractDescription.AbstractEquality;

public class ConcreteEquality {
    private AbstractEquality equality;
    private int parentId;

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
}
