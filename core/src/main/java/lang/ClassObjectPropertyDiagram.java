package lang;

import propity.util.Sets;
import speedith.core.i18n.Translations;
import speedith.core.lang.*;
import sun.security.util.Cache;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static speedith.i18n.Translations.i18n;

public class ClassObjectPropertyDiagram implements Comparable<ClassObjectPropertyDiagram>, Serializable {
    private static final long serialVersionUID = -4328162983982834123L;
    private final TreeSet<String> spiders;
    private final TreeMap<String, Region> spiderHabitatsMap;
    private final TreeSet<Zone> shadedZones;
    private final TreeSet<Zone> presentZones;
    private final TreeSet<Arrow> arrows;
    private final TreeSet<String> dots;
    private final TreeSet<Equality> equalities;
    private final boolean containsInitialT;
    public boolean isSingleVariableT;
    public int id;
    private Boolean valid;

    public ClassObjectPropertyDiagram(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones, TreeSet<Zone> presentZones, TreeSet<Arrow> arrows, TreeSet<String> dots, TreeSet<Equality> equalities, boolean containsInitialT) {
        if (spiders != null && !spiders.isEmpty()) {
            if (habitats != null && !Sets.isNaturalSubset(habitats.navigableKeySet(), spiders)) {
                throw new IllegalArgumentException(Translations.i18n("ERR_SD_HABITATS_WITHOUT_SPIDERS"));
            }
        } else if (habitats != null && !habitats.isEmpty()) {
            throw new IllegalArgumentException(Translations.i18n("ERR_SD_HABITATS_WITHOUT_SPIDERS"));
        }

        this.spiders = spiders == null ? new TreeSet<>() : spiders;
        this.spiderHabitatsMap = habitats == null ? new TreeMap<>() : habitats;
        this.shadedZones = shadedZones == null ? new TreeSet<>() : shadedZones;
        this.presentZones = presentZones == null ? new TreeSet<>() : presentZones;
        this.arrows = arrows == null ? new TreeSet<>() : arrows;
        this.dots = dots == null ? new TreeSet<>() : dots;
        this.equalities = equalities == null ? new TreeSet<>() : equalities;
        this.containsInitialT = containsInitialT;
        this.isSingleVariableT = false;
    }

    public SpiderDiagram getSpiderDiagram() {
        return SpiderDiagrams.createPrimarySD(spiders, spiderHabitatsMap, shadedZones, presentZones);
    }

    public List<Arrow> getArrows() {
        return new ArrayList<>(arrows);
    }

    public List<String> getDots() {
        return new ArrayList<>(dots);
    }

    public List<Equality> getEqualities() {
        return new ArrayList<>(equalities);
    }

    public boolean containsInitialT() {
        return containsInitialT;
    }

    public void setAsSingleVariableT() {
        this.isSingleVariableT = true;
    }

    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        } else {
            throw new RuntimeException("User-set COP id must be a natural integer."); // we need to set the id as negative so as to be unique from others that will be set
        }
    }

    @Override
    public int compareTo(ClassObjectPropertyDiagram o) {
        return 0;
    }

    boolean isValid() {
        if (shadedZones.isEmpty() && presentZones.isEmpty() && !dots.isEmpty()) {
            valid = true; // zones empty so only dots
        } else {
            valid = getSpiderDiagram().isValid();
        }
        return valid;
    }

    public void toString(Appendable sb) {
        try {
            if (sb == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sb"));
            }
            sb.append(getSpiderDiagram().toString());
            sb.append(",");
            sb.append("arrows= [");
            for (Arrow a: arrows) {
                sb.append(a.toString());
                sb.append(", ");
            }
            sb.append("]");
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
