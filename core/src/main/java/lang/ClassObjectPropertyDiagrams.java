package lang;

import speedith.core.i18n.Translations;
import speedith.core.lang.Region;
import speedith.core.lang.Zone;

import java.lang.ref.WeakReference;
import java.util.*;

public class ClassObjectPropertyDiagrams {
    private static final WeakHashMap<ClassObjectPropertyDiagram, WeakReference<ClassObjectPropertyDiagram>> pool = new WeakHashMap();

    private ClassObjectPropertyDiagrams() {}

    public static ClassObjectPropertyDiagram createClassObjectPropertyDiagramNoCopy(
            Collection<String> spiders,
            Map<String, Region> habitats,
            Collection<Zone> shadedZones,
            Collection<Zone> presentZones,
            Collection<Zone> highlightedZones,
            Collection<Arrow> arrows,
            Collection<String> dots,
            Collection<Equality> knownEqualities,
            Collection<Equality> unknownEqualities,
            boolean containsInitialT) {

        if (unknownEqualities != null && knownEqualities != null) {
            knownEqualities.addAll(unknownEqualities);
        } else if (knownEqualities == null && unknownEqualities != null) {
            knownEqualities = unknownEqualities;
        }

        if (habitats == null && shadedZones == null && presentZones == null && highlightedZones == null) {
            return createClassObjectPropertyDiagram(
                    null, null,
                    null,null, null,
                    arrows, spiders,
                    knownEqualities, containsInitialT, false);
        }
        return createClassObjectPropertyDiagram(spiders, habitats,
                shadedZones, presentZones, highlightedZones,
                arrows, dots,
                knownEqualities, containsInitialT, false);
    }

    public static DatatypeDiagram createDatatypeDiagramNoCopy(
            Collection<String> spiders,
            Map<String, Region> habitats,
            Collection<Zone> shadedZones,
            Collection<Zone> presentZones,
            Collection<Zone> highlightedZones,
            Collection<String> dots,
            Collection<Equality> knownEqualities,
            Collection<Equality> unknownEqualities) {

        if (unknownEqualities != null && knownEqualities != null) {
            knownEqualities.addAll(unknownEqualities);
        } else if (knownEqualities == null && unknownEqualities != null) {
            knownEqualities = unknownEqualities;
        }

        if (habitats == null && shadedZones == null && presentZones == null && highlightedZones == null) {
            return createDatatypeDiagram(
                    null, null,
                    null,null, null, spiders, knownEqualities, false);
        }
        return createDatatypeDiagram(spiders, habitats,
                shadedZones, presentZones, highlightedZones, dots, knownEqualities, false);
    }

    private static ClassObjectPropertyDiagram createClassObjectPropertyDiagram(Collection<String> spiders, Map<String,
            Region> habitats, Collection<Zone> shadedZones, Collection<Zone> presentZones, Collection<Zone> highlightedZones, Collection<Arrow> arrows, Collection<String> dots, Collection<Equality> equalities, boolean containsInitialT, boolean ClassObjectPropertyyCollections) {
        if (spiders != null && !(spiders instanceof TreeSet)
                || habitats != null && !(habitats instanceof TreeMap)
                || shadedZones != null && !(shadedZones instanceof TreeSet)
                || presentZones != null && !(presentZones instanceof TreeSet)
                || highlightedZones != null && !(highlightedZones instanceof TreeSet)
                || arrows != null && !(arrows instanceof TreeSet)
                || dots != null && !(dots instanceof TreeSet)
                || equalities != null && !(equalities instanceof TreeSet)) {
            TreeSet<String> spidersClassObjectProperty = spiders == null ? null : new TreeSet(spiders);
            TreeMap<String, Region> habitatsClassObjectProperty = habitats == null ? null : new TreeMap(habitats);
            TreeSet<Zone> shadedZonesClassObjectProperty = shadedZones == null ? null : new TreeSet(shadedZones);
            TreeSet<Zone> presentZonesClassObjectProperty = presentZones == null ? null : new TreeSet(presentZones);
            TreeSet<Zone> highlightedZonesClassObjectProperty = highlightedZones == null ? null : new TreeSet(highlightedZones);
            TreeSet<Arrow> arrowsClassObjectProperty = arrows == null ? null : new TreeSet(arrows);
            TreeSet<String> dotsClassObjectProperty = dots == null ? null : new TreeSet(dots);
            TreeSet<Equality> equalityClassObjectProperty = equalities == null ? null: new TreeSet(equalities);
            return __createClassObjectProperty(spidersClassObjectProperty, habitatsClassObjectProperty, shadedZonesClassObjectProperty, presentZonesClassObjectProperty, highlightedZonesClassObjectProperty, arrowsClassObjectProperty, dotsClassObjectProperty, equalityClassObjectProperty, containsInitialT, false);
        } else {
            return createClassObjectPropertyDiagram(spiders == null ? null : (TreeSet)spiders,
                    habitats == null ? null : (TreeMap)habitats,
                    shadedZones == null ? null : (TreeSet)shadedZones,
                    presentZones == null ? null : (TreeSet)presentZones,
                    highlightedZones == null ? null : (TreeSet) highlightedZones,
                    arrows == null ? null : (TreeSet) arrows,
                    dots,
                    equalities,
                    containsInitialT,
                    ClassObjectPropertyyCollections);
        }
    }

    private static DatatypeDiagram createDatatypeDiagram(Collection<String> spiders, Map<String,
            Region> habitats, Collection<Zone> shadedZones, Collection<Zone> presentZones, Collection<Zone> highlightedZones, Collection<String> dots, Collection<Equality> equalities, boolean ClassObjectPropertyyCollections) {
        if (spiders != null && !(spiders instanceof TreeSet)
                || habitats != null && !(habitats instanceof TreeMap)
                || shadedZones != null && !(shadedZones instanceof TreeSet)
                || presentZones != null && !(presentZones instanceof TreeSet)
                || highlightedZones != null && !(highlightedZones instanceof TreeSet)
                || dots != null && !(dots instanceof TreeSet)) {
            TreeSet<String> spidersClassObjectProperty = spiders == null ? null : new TreeSet<>(spiders);
            TreeMap<String, Region> habitatsClassObjectProperty = habitats == null ? null : new TreeMap(habitats);
            TreeSet<Zone> shadedZonesClassObjectProperty = shadedZones == null ? null : new TreeSet(shadedZones);
            TreeSet<Zone> presentZonesClassObjectProperty = presentZones == null ? null : new TreeSet(presentZones);
            TreeSet<Zone> highlightedZonesClassObjectProperty = highlightedZones == null ? null : new TreeSet(highlightedZones);
            TreeSet<String> dotsClassObjectProperty = dots == null ? null : new TreeSet(dots);
            TreeSet<Equality> equalitiesClassObjectProperty = equalities == null ? null : new TreeSet(equalities);
            return __createDatatype(spidersClassObjectProperty, habitatsClassObjectProperty, shadedZonesClassObjectProperty, presentZonesClassObjectProperty, highlightedZonesClassObjectProperty, dotsClassObjectProperty, equalitiesClassObjectProperty,false);
        } else {
            return createDatatypeDiagram(spiders == null ? null : (TreeSet)spiders,
                    habitats == null ? null : (TreeMap)habitats,
                    shadedZones == null ? null : (TreeSet)shadedZones,
                    presentZones == null ? null : (TreeSet)presentZones,
                    highlightedZones == null ? null : (TreeSet)highlightedZones,
                    dots,
                    equalities,
                    ClassObjectPropertyyCollections);
        }
    }

    private static ClassObjectPropertyDiagram __createClassObjectProperty(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones, TreeSet<Zone> presentZones, TreeSet<Zone> highlightedZones, TreeSet<Arrow> arrows, TreeSet<String> dots, TreeSet<Equality> equalities, boolean containsInitialT, boolean ClassObjectPropertyCollections) {
        synchronized(pool) {
            ClassObjectPropertyDiagram diagram;
            if (ClassObjectPropertyCollections) {
                diagram = new ClassObjectPropertyDiagram(
                        spiders == null ? null : (TreeSet)spiders.clone(),
                        habitats == null ? null : (TreeMap)habitats.clone(),
                        shadedZones == null ? null : (TreeSet)shadedZones.clone(),
                        presentZones == null ? null : (TreeSet)presentZones.clone(),
                        highlightedZones == null ? null : (TreeSet)highlightedZones.clone(),
                        arrows == null ? null : (TreeSet) arrows.clone(),
                        dots == null ? null : (TreeSet) dots.clone(),
                        equalities == null ? null : (TreeSet) equalities.clone(),
                        containsInitialT);
            } else {
                diagram = new ClassObjectPropertyDiagram(spiders, habitats, shadedZones, presentZones, highlightedZones, arrows, dots, equalities, containsInitialT);
            }

            ClassObjectPropertyDiagram exDiagram = __getSDFromPool(diagram);
            if (exDiagram == null) {
                pool.put(diagram, new WeakReference(diagram));
                return diagram;
            } else {
                assert ((ClassObjectPropertyDiagram)exDiagram ).equals(diagram) : Translations.i18n("GERR_ILLEGAL_STATE");

                assert diagram.equals(exDiagram ) : Translations.i18n("GERR_ILLEGAL_STATE");

                pool.put(exDiagram , new WeakReference(exDiagram ));
                return (ClassObjectPropertyDiagram) exDiagram ;
            }
        }
    }

    private static DatatypeDiagram __createDatatype(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones, TreeSet<Zone> presentZones, TreeSet<Zone> highlightedZones, TreeSet<String> dots, TreeSet<Equality> equalities, boolean ClassObjectPropertyCollections) {
        synchronized(pool) {
            DatatypeDiagram diagram;
            if (ClassObjectPropertyCollections) {
                diagram = new DatatypeDiagram(
                        spiders == null ? null : (TreeSet)spiders.clone(),
                        habitats == null ? null : (TreeMap)habitats.clone(),
                        shadedZones == null ? null : (TreeSet)shadedZones.clone(),
                        presentZones == null ? null : (TreeSet)presentZones.clone(),
                        highlightedZones == null ? null : (TreeSet) highlightedZones.clone(),
                        dots == null ? null : (TreeSet) dots.clone(),
                        equalities == null ? null : (TreeSet) equalities.clone());
            } else {
                diagram = new DatatypeDiagram(spiders, habitats, shadedZones, presentZones, highlightedZones, dots, equalities);
            }

            DatatypeDiagram exDiagram = __getSDFromPool(diagram);
            if (exDiagram == null) {
                pool.put(diagram, new WeakReference(diagram));
                return diagram;
            } else {
                assert ((ClassObjectPropertyDiagram)exDiagram ).equals(diagram) : Translations.i18n("GERR_ILLEGAL_STATE");

                assert diagram.equals(exDiagram ) : Translations.i18n("GERR_ILLEGAL_STATE");

                pool.put(exDiagram , new WeakReference(exDiagram ));
                return (DatatypeDiagram) exDiagram ;
            }
        }
    }

    private static ClassObjectPropertyDiagram __getSDFromPool(ClassObjectPropertyDiagram psd) {
        WeakReference<ClassObjectPropertyDiagram> poolClassObjectProperty = (WeakReference)pool.get(psd);
        return poolClassObjectProperty == null ? null : (ClassObjectPropertyDiagram) poolClassObjectProperty.get();
    }


    private static DatatypeDiagram __getSDFromPool(DatatypeDiagram psd) {
        WeakReference<DatatypeDiagram> poolDT = (WeakReference)pool.get(psd);
        return poolDT == null ? null : (DatatypeDiagram) poolDT.get();
    }
}
