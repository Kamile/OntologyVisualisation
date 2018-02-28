package abstractDescription;

import icircles.abstractDescription.AbstractBasicRegion;

public class AbstractEquality implements Comparable<AbstractEquality>{
    static int id = 0;
    private boolean isKnown;
    private AbstractBasicRegion abr1;
    private AbstractBasicRegion abr2;

    public AbstractEquality(boolean isKnown, AbstractBasicRegion abr1, AbstractBasicRegion abr2) {
        this.abr1 = abr1;
        this.abr2 = abr2;
        this.isKnown = isKnown;
        ++id;
    }

    public boolean isKnown() {
        return isKnown;
    }

    public AbstractBasicRegion getAbr1() {
        return abr1;
    }

    public AbstractBasicRegion getAbr2() {
        return abr2;
    }

    @Override
    public int compareTo(AbstractEquality o) {
        if (o == null) {
            return 1;
        } else {
            return id < o.id ? 1 : (id == o.id? 0 : 1);
        }
    }

    public String toString() {
        return abr1.toString() + " " + abr2.toString();
    }
}
