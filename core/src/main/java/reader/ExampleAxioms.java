package reader;

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

    public static String SINGLE_CONTOUR = "ConceptDiagram {\n" +
            "    COPs = [\n" +
            "        COP {\n" +
            "            spiders = [],\n" +
            "            habitats = [],\n" +
            "            sh_zones = [],\n" +
            "            present_zones = [([], [\"C\"]), ([\"C\"], [])]}]\n" +
            "}\n";

    public static String TWO_DISTINCT_CURVES = "ConceptDiagram {\n" +
            "    COPs = [\n" +
            "        COP {\n" +
            "            spiders = [],\n" +
            "            habitats = [],\n" +
            "            sh_zones = [([\"C\",\"D\"], [])],\n" +
            "            present_zones = [([\"C\"], [\"D\"]),\n" +
            "                             ([\"D\"], [\"C\"]),\n" +
            "                             ([],[\"C\",\"D\"])\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}\n";

    public static String THREE_DISTINCT_CURVES = "ConceptDiagram {\n" +
            "    COPs = [\n" +
            "        COP {\n" +
            "            spiders = [],\n" +
            "            habitats = [],\n" +
            "            sh_zones = [([\"C\",\"D\",\"E\"], []), \n" +
            "                        ([\"C\",\"D\"], [\"E\"]),\n" +
            "                        ([\"C\",\"E\"], [\"D\"]),\n" +
            "                        ([\"D\",\"E\"], [\"C\"])],\n" +
            "            present_zones = [([\"C\"], [\"D\",\"E\"]),\n" +
            "                             ([\"D\"], [\"C\", \"E\"]),\n" +
            "                             ([\"E\"], [\"C\", \"D\"]),\n" +
            "                             ([],[\"C\",\"D\", \"E\"])\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}\n";

    public static String FOUR_DISTINCT_CURVES = "ConceptDiagram {\n" +
            "    COPs = [\n" +
            "        COP {\n" +
            "            spiders = [],\n" +
            "            habitats = [],\n" +
            "            sh_zones = [([\"C\",\"D\",\"E\",\"F\"], []), \n" +
            "                        ([\"C\",\"D\"], [\"E\", \"F\"]),\n" +
            "                        ([\"C\",\"E\"], [\"D\", \"F\"]),\n" +
            "                        ([\"C\",\"F\"], [\"D\", \"E\"]),\n" +
            "                        ([\"D\",\"E\"], [\"C\", \"F\"]),\n" +
            "                        ([\"D\",\"F\"], [\"C\", \"E\"]),\n" +
            "                        ([\"E\",\"F\"], [\"D\", \"C\"]),\n" +
            "                        ([\"D\",\"E\",\"F\"], [\"C\"]),\n" +
            "                        ([\"D\",\"E\",\"C\"], [\"F\"]),\n" +
            "                        ([\"C\",\"E\",\"F\"], [\"D\"]),\n" +
            "                        ([\"D\",\"C\",\"F\"], [\"E\"])],\n" +
            "            present_zones = [([\"C\"], [\"D\", \"E\", \"F\"]),\n" +
            "                             ([\"D\"], [\"C\", \"E\", \"F\"]),\n" +
            "                             ([\"E\"], [\"C\", \"D\", \"F\"]),\n" +
            "                             ([\"F\"], [\"C\", \"D\", \"E\"]),\n" +
            "                             ([], [\"C\",\"D\", \"E\", \"F\"])\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}\n";
}
