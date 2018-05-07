package reader;

import speedith.core.lang.Zone;
import speedith.core.lang.Zones;

import java.util.*;

import static speedith.core.lang.SpiderDiagrams.createPrimarySD;

public class ExampleAxioms {

    public static String CLASS_EXPRESSION = "ConceptDiagram {\n" +
            "    COPs = [\n" +
            "        COP {\n" +
            "            spiders = [\"a\"],\n" +
            "            sh_zones = [],\n" +
            "            habitats = [(\"a\", [([\"C1\"], [\"C2\"])])],\n" +
            "            present_zones=[([\"C2\"],[\"C1\"]), ([\"C1\"],[\"C2\"]), ([],[\"C1\", \"C2\"]), ([\"C1\", \"C2\"],[])]}\n" +
            "    ]\n" +
            "}";


    public static String SUBCLASS = "ConceptDiagram {\n" +
            "    COPs = [\n" +
            "        COP {\n" +
            "            spiders = [\"c\", \"_1\", \"_2\"],\n" +
            "            habitats = [\n" +
            "                (\"c\", [([\"C4\"],[\"_anon\", \"C5\"])]),\n" +
            "                (\"_1\", [([\"C5\", \"_anon\"],[\"C4\"])]),\n" +
            "                (\"_2\", [([\"C5\", \"_anon\"],[\"C4\"])])],\n" +
            "            sh_zones = [\n" +
            "                ([\"C4\", \"C5\", \"_anon\"],[]),\n" +
            "                ([\"_anon\"],[\"C4\", \"C5\"]),\n" +
            "                ([\"_anon\", \"C4\"],[\"C5\"]),\n" +
            "                ([\"C4\", \"C5\"], [\"_anon\"])],\n" +
            "            present_zones = [\n" +
            "                ([], [\"C4\", \"C5\", \"_anon\"]),\n" +
            "                ([\"C5\"], [\"C4\", \"_anon\"]),\n" +
            "                ([\"C5\", \"_anon\"], [\"C4\"]),\n" +
            "                ([\"C4\"],[\"C5\", \"_anon\"])],\n" +
            "            arrows = [" +
            "               Arrow {\n" +
            "                    source = \"C4\",\n" +
            "                    target = \"_anon\",\n" +
            "                    property = \"op\",\n" +
            "                    op = \"leq\",\n" +
            "                    arg = \"2\",\n" +
            "                    dashed = \"true\"\n" +
            "                }" +
            "           ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";


    public static String OBJECT_SOME_VALUE = "ConceptDiagram {\n" +
            "    COPs = [\n" +
            "        COP {\n" +
            "            spiders = [],\n" +
            "            habitats = [],\n" +
            "            sh_zones = [],\n" +
            "            present_zones = [([], [\"CE\"]), ([\"CE\"], [])]},\n" +
            "        COP {\n" +
            "            spiders = [],\n" +
            "            habitats = [],\n" +
            "sh_zones =[],\n " +
            "                    present_zones = [([], [\"_anon\"]), ([\"_anon\"], [])],\n" +
            "            highlighted_zones = [([\"_anon\"], [])]}\n" +
            "    ],\n" +
            "\tarrows = [" +
            "           Arrow {\n" +
            "            source = \"CE\",\n" +
            "            target = \"_anon\",\n" +
            "            property = \"op-\"\n" +
            "        }" +
            "]\n" +
            "}\n";

    public static String DATATYPE_DEFINITION = "ConceptDiagram {\n" +
            "    DTs = [\n" +
            "        DT {\n" +
            "            spiders = [],\n" +
            "            sh_zones = [\n" +
            "                ([\"DT\"], [\"DR1\", \"DR2\"]),\n" +
            "                ([\"DR1\", \"DR2\"], [\"DT\"]),\n" +
            "                ([\"DR2\"], [\"DT\", \"DR1\"]),\n" +
            "                ([\"DR1\"],[\"DT\", \"DR2\"])],\n" +
            "            habitats = [],\n" +
            "            present_zones=[\n" +
            "                ([\"DR2\", \"DT\"],[\"DR1\"]),\n" +
            "                ([\"DR1\", \"DT\"],[\"DR2\"]),\n" +
            "                ([],[\"DT\", \"DR1\", \"DR2\"]),\n" +
            "                ([\"DT\", \"DR1\", \"DR2\"],[])]\n" +
            "        }\n" +
            "    ]}\n";

    public static String INVALID_FIRST_TOKEN = "Concept {\n" +
            "    COPs = [\n" +
            "        COP {\n" +
            "            spiders = [\"a\"],\n" +
            "    ]\n" +
            "}";

    public static String PROPERTY_CHAIN = "PropertyDiagram {\n" +
            "    COPs = [\n" +
            "        COP {\n" +
            "            spiders = [],\n" +
            "            habitats = [],\n" +
            "            sh_zones = [],\n" +
            "            present_zones = [([], [\"_anon1\"]), ([\"_anon1\"],[])]},\n" +
            "        COP {\n" +
            "            spiders = [],\n" +
            "            habitats = [],\n" +
            "            sh_zones = [],\n" +
            "            present_zones = [([], [\"_anon2\"]), ([\"_anon2\"],[])]},\n" +
            "        COP {\n" +
            "            spiders = [],\n" +
            "            habitats = [],\n" +
            "            sh_zones = [],\n" +
            "            present_zones = [([], [\"_anon3\"]), ([\"_anon3\"],[])]},\n" +
            "        COP {\n" +
            "            spiders = [],\n" +
            "            habitats = [],\n" +
            "            sh_zones = [([\"_anonB\"], [\"_anonA\"])],\n" +
            "            present_zones = [([], [\"_anonA\", \"_anonB\"]), ([\"_anonA\"],[\"_anonB\"]), ([\"_anonA\", \"_anonB\"],[])]}\n" +
            "    ],\n" +
            "    arrows = [\n" +
            "        Arrow {\n" +
            "            source = \"t\",\n" +
            "            target = \"_anon1\",\n" +
            "            property = \"op\",\n" +
            "            arg = \"1\"\n" +
            "        },\n" +
            "        Arrow {\n" +
            "            source = \"_anon1\",\n" +
            "            target = \"_anon2\",\n" +
            "            property = \"op\",\n" +
            "            arg = \"2\"\n" +
            "        },\n" +
            "        Arrow {\n" +
            "            source = \"_anon2\",\n" +
            "            target = \"_anon3\",\n" +
            "            property = \"op\",\n" +
            "            arg = \"3\"\n" +
            "        },\n" +
            "        Arrow {\n" +
            "            source = \"_anon3\",\n" +
            "            target = \"_anonB\",\n" +
            "            property = \"op\",\n" +
            "            arg = \"4\"\n" +
            "        },\n" +
            "        Arrow {\n" +
            "            source = \"t\",\n" +
            "            target = \"_anonA\",\n" +
            "            property = \"op\"\n" +
            "        }\n" +
            "    ]\n" +
            "}\n";


    /**
     * ZONE TESTS - Generate all possible zones for contours. Zone number increases exponentially with each new contour
     */
    private static final List<Zone> POWER_REGION_A = Zones.allZonesForContours("A");
    private static final List<Zone> POWER_REGION_AB = Zones.allZonesForContours("A", "B");
    private static final List<Zone> POWER_REGION_ABC = Zones.allZonesForContours("A", "B", "C");
    private static final List<Zone> POWER_REGION_ABCD = Zones.allZonesForContours("A", "B", "C", "D");
    private static final List<Zone> POWER_REGION_ABCDE = Zones.allZonesForContours("A", "B", "C", "D", "E");
    private static final List<Zone> POWER_REGION_ABCDEF = Zones.allZonesForContours("A", "B", "C", "D", "E", "F");
    private static final List<Zone> POWER_REGION_ABCDEFG = Zones.allZonesForContours("A", "B", "C", "D", "E", "F", "G");
    private static final List<Zone> POWER_REGION_ABCDEFGH = Zones.allZonesForContours("A", "B", "C", "D", "E", "F", "G", "H");
    private static final List<Zone> POWER_REGION_ABCDEFGHI = Zones.allZonesForContours("A", "B", "C", "D", "E", "F", "G", "H", "I");
    private static final List<Zone> POWER_REGION_ABCDEFGHIJ = Zones.allZonesForContours("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");

    public static final String ZONES2 = getCOP(createPrimarySD(null, null, null, POWER_REGION_A).toString());
    public static final String ZONES4 = getCOP(createPrimarySD(null, null, null, POWER_REGION_AB).toString());
    public static final String ZONES8 = getCOP(createPrimarySD(null, null, null, POWER_REGION_ABC).toString());
    public static final String ZONES16 = getCOP(createPrimarySD(null, null, null, POWER_REGION_ABCD).toString());
    public static final String ZONES32 = getCOP(createPrimarySD(null, null, null, POWER_REGION_ABCDE).toString());
    public static final String ZONES64 = getCOP(createPrimarySD(null, null, null, POWER_REGION_ABCDEF).toString());
    public static final String ZONES128 = getCOP(createPrimarySD(null, null, null, POWER_REGION_ABCDEFG).toString());
    public static final String ZONES256 = getCOP(createPrimarySD(null, null, null, POWER_REGION_ABCDEFGH).toString());

    private static String getCOP(String input) {
        input = input.replace("PrimarySD", "COP");
        return "ConceptDiagram {"
                    + "COPs = ["
                    + input + "]" +
                "}";
    }

    private static String getDistinctCurves(List<Zone> shadedZones, Zone outContour, int n) {
        List<String> allContours = contours.subList(0, n);
        List<Zone> presentZones = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String contourName = letters[i];
            allContours.remove(contourName);
            Zone z = new Zone(getSingleElementSet(contourName), new TreeSet<>(allContours));
            presentZones.add(z);
            shadedZones.remove(z);
            allContours.add(contourName);
        }
        presentZones.add(outContour);
        shadedZones.remove(outContour);
        return getCOP(createPrimarySD(null, null, shadedZones, presentZones).toString());
    }

    private static TreeSet<String> getSingleElementSet(String s) {
        TreeSet<String> set = new TreeSet<>();
        set.add(s);
        return set;
    }

    /**
     * CONTOUR TESTS - add distinct curves
     */
    private static String[] letters = {"A", "B", "C", "D", "E", "F" ,"G", "H", "I", "J"};
    private static Zone TWO_OUT_CONTOURS = Zone.fromOutContours("A", "B");
    private static Zone THREE_OUT_CONTOURS = Zone.fromOutContours("A", "B", "C");
    private static Zone FOUR_OUT_CONTOURS = Zone.fromOutContours("A", "B", "C", "D");
    private static Zone FIVE_OUT_CONTOURS = Zone.fromOutContours("A", "B", "C", "D", "E");
    private static Zone SIX_OUT_CONTOURS = Zone.fromOutContours("A", "B", "C", "D", "E", "F");
    private static Zone SEVEN_OUT_CONTOURS = Zone.fromOutContours("A", "B", "C", "D", "E", "F", "G");
    private static Zone EIGHT_OUT_CONTOURS = Zone.fromOutContours("A", "B", "C", "D", "E", "F", "G", "H");
    private static Zone NINE_OUT_CONTOURS = Zone.fromOutContours("A", "B", "C", "D", "E", "F", "G", "H", "I");
    private static Zone TEN_OUT_CONTOURS = Zone.fromOutContours("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
    private static List<String> contours = new ArrayList<>(Arrays.asList(letters));

    public static String SINGLE_CONTOUR = ZONES2;
    public static String TWO_DISTINCT_CURVES = getDistinctCurves(POWER_REGION_AB, TWO_OUT_CONTOURS,2);
    public static String THREE_DISTINCT_CURVES = getDistinctCurves(POWER_REGION_ABC, THREE_OUT_CONTOURS, 3);
    public static String FOUR_DISTINCT_CURVES = getDistinctCurves(POWER_REGION_ABCD, FOUR_OUT_CONTOURS, 4);
    public static String FIVE_DISTINCT_CURVES = getDistinctCurves(POWER_REGION_ABCDE, FIVE_OUT_CONTOURS, 5);
    public static String SIX_DISTINCT_CURVES = getDistinctCurves(POWER_REGION_ABCDEF, SIX_OUT_CONTOURS, 6);
    public static String SEVEN_DISTINCT_CURVES = getDistinctCurves(POWER_REGION_ABCDEFG, SEVEN_OUT_CONTOURS, 7);
    public static String EIGHT_DISTINCT_CURVES = getDistinctCurves(POWER_REGION_ABCDEFGH, EIGHT_OUT_CONTOURS, 8);
    public static String NINE_DISTINCT_CURVES = getDistinctCurves(POWER_REGION_ABCDEFGHI, NINE_OUT_CONTOURS, 9);
    public static String TEN_DISTINCT_CURVES = getDistinctCurves(POWER_REGION_ABCDEFGHIJ, TEN_OUT_CONTOURS, 10);

    /**
     * UNCONNECTED COP/DT tests - timing as number of subdiagrams in CD/PD increases. No arrows.
     */
    public static String getDisconnectedSubDiagrams(int n) {
        // simple spider in COP
        String COP = "COP {" +
                "spiders = [\"a\"]" +
                "}";

        String COPlist = COP;
        for (int i=0; i< n-1; i++) {
            COPlist += ", " + COP;
        }

        return "ConceptDiagram {" +
                "   COPs = [" +
                        COPlist +
                "   ]" +
                "}";
    }

}


