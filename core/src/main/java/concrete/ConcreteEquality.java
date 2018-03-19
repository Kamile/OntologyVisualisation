package concrete;

import abstractDescription.AbstractEquality;

public class ConcreteEquality {
    private AbstractEquality equality;

    public ConcreteEquality(AbstractEquality equality) {
        this.equality = equality;
    }

    public AbstractEquality getAbstractEquality() {
        return equality;
    }
}
