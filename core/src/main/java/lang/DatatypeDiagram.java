package lang;

import speedith.core.lang.Region;
import speedith.core.lang.Zone;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Datatype diagrams consist of literals, habitats, missing zones and present zones.
 *
 * There are no arrows and no equalities between literals.
 */
public class DatatypeDiagram extends ClassObjectPropertyDiagram {
    public DatatypeDiagram(TreeSet<String> spiders, TreeMap<String, Region> habitats, TreeSet<Zone> shadedZones, TreeSet<Zone> presentZones, TreeSet<String> dots) {
        super(spiders, habitats, shadedZones, presentZones, null, dots, null);
    }
}
