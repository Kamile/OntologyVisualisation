package lang;

import speedith.core.i18n.Translations;
import speedith.core.lang.Region;
import speedith.core.lang.Zone;

import java.lang.ref.WeakReference;
import java.util.*;

public class ClassObjectPropertyDiagrams {
    private static final WeakHashMap<ClassObjectPropertyDiagram, WeakReference<ClassObjectPropertyDiagram>> pool = new WeakHashMap();

    private ClassObjectPropertyDiagrams() {}

    public static ClassObjectPropertyDiagram createClassObjectPropertyDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones, Collection<Zone> presentZones, Collection<Arrow> arrows) {
        return createClassObjectPropertyDiagram(spiders, habitats, shadedZones, presentZones, arrows,true);
    }

    public static ClassObjectPropertyDiagram createClassObjectPropertyDiagram(Map<String, Region> habitats, Collection<Zone> shadedZones, Collection<Zone> presentZones, Collection<Arrow> arrows) {
        return createClassObjectPropertyDiagram((Collection)habitats.keySet(), (Map)habitats, (Collection)shadedZones, (Collection)presentZones, arrows, true);
    }

    public static ClassObjectPropertyDiagram createClassObjectPropertyDiagram() {
        return createClassObjectPropertyDiagram((Collection)null, (Map)null, (Collection)null, (Collection)null, (Collection) null);
    }

    public static ClassObjectPropertyDiagram createClassObjectPropertyDiagramNoClassObjectPropertyy(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones, Collection<Zone> presentZones, Collection<Arrow> arrows) {
        return createClassObjectPropertyDiagram(spiders, habitats, shadedZones, presentZones, arrows, false);
    }

    static ClassObjectPropertyDiagram createClassObjectPropertyDiagram(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones, TreeSet<Zone> presentZones, TreeSet<Arrow> arrows, boolean ClassObjectPropertyyCollections) {
        return __createClassObjectProperty(spiders, habitats, shadedZones, presentZones, arrows, ClassObjectPropertyyCollections);
    }

    private static ClassObjectPropertyDiagram createClassObjectPropertyDiagram(Collection<String> spiders, Map<String, Region> habitats, Collection<Zone> shadedZones, Collection<Zone> presentZones, Collection<Arrow> arrows, boolean ClassObjectPropertyyCollections) {
        if (spiders != null && !(spiders instanceof TreeSet)
                || habitats != null && !(habitats instanceof TreeMap)
                || shadedZones != null && !(shadedZones instanceof TreeSet)
                || presentZones != null && !(presentZones instanceof TreeSet)
                || arrows != null && !(arrows instanceof TreeSet)) {
            TreeSet<String> spidersClassObjectPropertyy = spiders == null ? null : new TreeSet(spiders);
            TreeMap<String, Region> habitatsClassObjectPropertyy = habitats == null ? null : new TreeMap(habitats);
            TreeSet<Zone> shadedZonesClassObjectPropertyy = shadedZones == null ? null : new TreeSet(shadedZones);
            TreeSet<Zone> presentZonesClassObjectPropertyy = presentZones == null ? null : new TreeSet(presentZones);
            TreeSet<Arrow> arrowsClassObjectPropertyy = arrows == null ? null : new TreeSet(arrows);
            return __createClassObjectProperty(spidersClassObjectPropertyy, habitatsClassObjectPropertyy, shadedZonesClassObjectPropertyy, presentZonesClassObjectPropertyy, arrowsClassObjectPropertyy,false);
        } else {
            return createClassObjectPropertyDiagram(spiders == null ? null : (TreeSet)spiders,
                    habitats == null ? null : (TreeMap)habitats,
                    shadedZones == null ? null : (TreeSet)shadedZones,
                    presentZones == null ? null : (TreeSet)presentZones,
                    arrows == null ? null : (TreeSet) arrows,
                    ClassObjectPropertyyCollections);
        }
    }

    private static ClassObjectPropertyDiagram __createClassObjectProperty(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones, TreeSet<Zone> presentZones, TreeSet<Arrow> arrows, boolean ClassObjectPropertyCollections) {
        WeakHashMap var5 = pool;
        synchronized(pool) {
            ClassObjectPropertyDiagram diagram = null;
            if (ClassObjectPropertyCollections) {
                diagram = new ClassObjectPropertyDiagram(spiders == null ? null : (TreeSet)spiders.clone(),
                        habitats == null ? null : (TreeMap)habitats.clone(),
                        shadedZones == null ? null : (TreeSet)shadedZones.clone(),
                        presentZones == null ? null : (TreeSet)presentZones.clone(),
                        arrows == null ? null : (TreeSet) arrows.clone());
            } else {
                diagram = new ClassObjectPropertyDiagram(spiders, habitats, shadedZones, presentZones, arrows);
            }

            ClassObjectPropertyDiagram exDiagram = __getSDFromPool(diagram);
            if (exDiagram == null) {
                pool.put(diagram, new WeakReference(diagram));
                return diagram;
            } else {
                assert exDiagram instanceof ClassObjectPropertyDiagram : Translations.i18n("GERR_ILLEGAL_STATE_EXPLANATION", new Object[]{Translations.i18n("ERR_PRIMARY_SD_EQUALS_NON_PRIMARY_SD")});

                assert ((ClassObjectPropertyDiagram)exDiagram ).equals(diagram) : Translations.i18n("GERR_ILLEGAL_STATE");

                assert diagram.equals(exDiagram ) : Translations.i18n("GERR_ILLEGAL_STATE");

                pool.put(exDiagram , new WeakReference(exDiagram ));
                return (ClassObjectPropertyDiagram) exDiagram ;
            }
        }
    }

    private static ClassObjectPropertyDiagram __getSDFromPool(ClassObjectPropertyDiagram psd) {
        WeakReference<ClassObjectPropertyDiagram> poolClassObjectProperty = (WeakReference)pool.get(psd);
        return poolClassObjectProperty == null ? null : (ClassObjectPropertyDiagram) poolClassObjectProperty.get();
    }
}
