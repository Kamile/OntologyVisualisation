package reader;

import lang.*;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.*;
import speedith.core.lang.*;
import speedith.core.lang.reader.ParseException;
import speedith.core.lang.reader.ReadingException;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static i18n.Translations.i18n;
import static lang.Arrow.*;
import static lang.ConceptDiagram.*;
import static speedith.core.lang.PrimarySpiderDiagram.*;

import reader.ConceptDiagramsParser;
import reader.ConceptDiagramsLexer;

/**
 * Read text representation of Concept Diagram and create a
 * corresponding abstract representation of a Concept Diagram as
 * a Java object. The syntax of the textual representation of concept
 * diagrams is specified in the ConceptDiagrams.g ANTLR file which also
 * generates the corresponding parser and lexer.
 *
 * Methods already implemented in SpiderDiagramsReader
 * - readRegion
 * - readElement
 */
public class DiagramsReader {

    private DiagramsReader() {}

    public static Diagram readConceptDiagram(String input) throws ReadingException {
        return readConceptDiagram(new ANTLRStringStream(input));
    }

    public static Diagram readConceptDiagram(File file) throws ReadingException, IOException {
        return readConceptDiagram(new ANTLRFileStream(file.getPath()));
    }

    static Diagram readConceptDiagram(CharStream stream) throws ReadingException {
        reader.ConceptDiagramsLexer lexer = new reader.ConceptDiagramsLexer(stream);
        reader.ConceptDiagramsParser parser = new reader.ConceptDiagramsParser(new CommonTokenStream(lexer));

        try {
            return toConceptDiagram(parser.start());
        } catch (RecognitionException re) {
            throw new ReadingException(i18n("ERR_PARSE_INVALID_SYNTAX"), re);
        } catch (ParseException pe) {
            throw new ReadingException(pe.getMessage(), pe);
        }
    }

    private static Diagram toConceptDiagram(ConceptDiagramsParser.start_return diagram) throws ReadingException {
        if (diagram == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "conceptDiagram"));
        }
        return TopTranslator.Instance.fromASTNode(diagram.tree);
    }

    /** BEGIN methods from SpiderDiagramsReader **/
    private abstract static class ElementTranslator<T> {

        public abstract T fromASTNode(CommonTree treeNode) throws ReadingException;
    }

    private static class IDTranslator extends ElementTranslator<String> {

        static final IDTranslator Instance = new IDTranslator();

        @Override
        public String fromASTNode(CommonTree treeNode) throws ReadingException {
            if (treeNode.token != null && treeNode.token.getType() == reader.ConceptDiagramsParser.ID) {
                return treeNode.token.getText();
            }
            throw new ReadingException(i18n("ERR_TRANSLATE_INVALID_ID"), treeNode);
        }
    }

    private static abstract class CollectionTranslator<V> extends ElementTranslator<ArrayList<V>> {

        private int headTokenType;

        CollectionTranslator(int headTokenType) {
            if (headTokenType == reader.ConceptDiagramsParser.SLIST || headTokenType == ConceptDiagramsParser.LIST) {
                this.headTokenType = headTokenType;
            } else {
                throw new IllegalArgumentException(i18n("GERR_ILLEGAL_ARGUMENT", "headTokenType"));
            }
        }

        @Override
        public ArrayList<V> fromASTNode(CommonTree treeNode) throws ReadingException {
            if (treeNode.token != null && treeNode.token.getType() == headTokenType) {
                checkNode(treeNode);
                if (treeNode.getChildCount() < 1) {
                    return null;
                }
                ArrayList<V> objs = new ArrayList<>(treeNode.getChildCount());
                int i = 0;
                for (Object obj : treeNode.getChildren()) {
                    objs.add(fromASTChildAt(i++, (CommonTree) obj));
                }
                return objs;
            }
            throw new ReadingException(i18n("ERR_TRANSLATE_UNEXPECTED_ELEMENT", i18n(i18n("ERR_TRANSLATE_LIST_OR_SLIST"))), treeNode);
        }

        protected abstract V fromASTChildAt(int i, CommonTree treeNode) throws ReadingException;

        /**
         * Checks whether the node (which should be a list) is okay for
         * translation. It indicates so by not throwing an exception.
         * <p>The default implementation does nothing.</p>
         * @param treeNode the node which should be checked.
         * @exception ReadingException this exception should be thrown if the
         * AST node is not valid in some sense.
         */
        protected void checkNode(CommonTree treeNode) throws ReadingException {
        }
    }

    private static class StringTranslator extends ElementTranslator<String> {

        static final StringTranslator Instance = new StringTranslator();

        @Override
        public String fromASTNode(CommonTree treeNode) throws ReadingException {
            if (treeNode.token != null && treeNode.token.getType() == ConceptDiagramsParser.STRING) {
                String str = treeNode.token.getText();
                if (str != null && str.length() >= 2) {
                    return str.substring(1, str.length() - 1);
                }
            }
            throw new ReadingException(i18n("ERR_TRANSLATE_INVALID_STRING"), treeNode);
        }
    }

    private static class ListTranslator<V> extends CollectionTranslator<V> {

        static final ListTranslator<String> StringListTranslator = new ListTranslator<>(StringTranslator.Instance);
        ElementTranslator<? extends V> valueTranslator = null;

        ListTranslator(ElementTranslator<? extends V> valueTranslator) {
            this(ConceptDiagramsParser.LIST, valueTranslator);
        }

        ListTranslator(int headTokenType, ElementTranslator<? extends V> valueTranslator) {
            super(headTokenType);
            if (valueTranslator == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "valueTranslator"));
            }
            this.valueTranslator = valueTranslator;
        }

        @Override
        protected V fromASTChildAt(int i, CommonTree treeNode) throws ReadingException {
            return valueTranslator.fromASTNode(treeNode);
        }
    }

    private static class GeneralMapTranslator<V> extends ElementTranslator<Map<String, Map.Entry<V, CommonTree>>> {
        private Map<String, ElementTranslator<? extends V>> typedValueTranslators;
        private ElementTranslator<? extends V> defaultValueTranslator;
        private int headTokenType = ConceptDiagramsParser.DICT;

        GeneralMapTranslator(int headTokenType, Map<String, ElementTranslator<? extends V>> typedValueTranslators, ElementTranslator<? extends V> defaultElements) {
            if (typedValueTranslators == null && defaultElements == null) {
                throw new IllegalArgumentException(i18n("ERR_ARGUMENT_ALL_NULL"));
            }
            this.typedValueTranslators = typedValueTranslators;
            this.defaultValueTranslator = defaultElements;
            this.headTokenType = headTokenType;
        }

        @Override
        public Map<String, Map.Entry<V, CommonTree>> fromASTNode(CommonTree treeNode) throws ReadingException {
            if (treeNode.token != null && treeNode.token.getType() == headTokenType) {
                if (treeNode.getChildCount() < 1) {
                    return null;
                }
                HashMap<String, Map.Entry<V, CommonTree>> kVals = new HashMap<>();
                for (Object obj : treeNode.getChildren()) {
                    CommonTree node = (CommonTree) obj;
                    if (node.token != null && node.token.getType() == ConceptDiagramsParser.PAIR && node.getChildCount() == 2) {
                        String key = IDTranslator.Instance.fromASTNode((CommonTree) node.getChild(0));
                        ElementTranslator<? extends V> translator = null;
                        if (typedValueTranslators != null) {
                            translator = typedValueTranslators.get(key);
                        }
                        if (translator == null) {
                            if (defaultValueTranslator != null) {
                                translator = defaultValueTranslator;
                            } else {
                                throw new ReadingException(i18n("ERR_TRANSLATE_UNEXPECTED_KEY_VALUE", key, typedValueTranslators == null ? "" : typedValueTranslators.keySet()), (CommonTree) node.getChild(0));
                            }
                        }
                        V value = translator.fromASTNode((CommonTree) node.getChild(1));
                        kVals.put(key, new AbstractMap.SimpleEntry<>(value, node));
                    } else {
                        throw new ReadingException(i18n("ERR_TRANSLATE_UNEXPECTED_ELEMENT", i18n("TRANSLATE_KEY_VALUE_PAIR")), node);
                    }
                }
                return kVals;
            }
            throw new ReadingException(i18n("ERR_TRANSLATE_UNEXPECTED_ELEMENT", i18n("ERR_TRANSLATE_LIST_OR_SLIST")), treeNode);
        }
    }

    private static class TupleTranslator<V> extends CollectionTranslator<V> {

        List<ElementTranslator<? extends V>> valueTranslators = null;
        TupleTranslator(ElementTranslator<? extends V>[] valueTranslators) {
            this(ConceptDiagramsParser.SLIST, Arrays.asList(valueTranslators));
        }

        TupleTranslator(int headTokenType, List<ElementTranslator<? extends V>> valueTranslators) {
            super(headTokenType);
            if (valueTranslators == null) {
                throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "valueTranslators"));
            }
            this.valueTranslators = valueTranslators;
        }

        @Override
        protected V fromASTChildAt(int i, CommonTree treeNode) throws ReadingException {
            if (i >= valueTranslators.size()) {
                throw new ReadingException(i18n("ERR_TRANSLATE_TOO_MANY_ELMNTS"), treeNode);
            }
            return valueTranslators.get(i).fromASTNode(treeNode);
        }

        @Override
        protected void checkNode(CommonTree treeNode) throws ReadingException {
            if (treeNode.getChildCount() != valueTranslators.size()) {
                throw new ReadingException(i18n("ERR_TRANSLATE_ELEMENTS_COUNT", valueTranslators.size(), treeNode.getChildCount()), treeNode);
            }
        }
    }

    private static class ZoneTranslator extends ElementTranslator<Zone> {
        static final ZoneTranslator Instance = new ZoneTranslator();
        static final ListTranslator<Zone> ZoneListTranslator = new ListTranslator<>(Instance);
        private ListTranslator<ArrayList<String>> translator;

        private ZoneTranslator() {
            translator = new ListTranslator<>(ConceptDiagramsParser.SLIST, ListTranslator.StringListTranslator);
        }

        @Override
        public Zone fromASTNode(CommonTree treeNode) throws ReadingException {
            ArrayList<ArrayList<String>> inOutContours = translator.fromASTNode(treeNode);
            if (inOutContours == null || inOutContours.size() != 2) {
                throw new ReadingException(i18n("ERR_TRANSLATE_ZONE"), treeNode);
            }
            return new Zone(inOutContours.get(0), inOutContours.get(1));
        }
    }

    private static class HabitatTranslator extends ElementTranslator<Map<String, Region>> {
        static final HabitatTranslator Instance = new HabitatTranslator();
        private ListTranslator<ArrayList<Object>> regionListTranslator;

        @SuppressWarnings("unchecked")
        private HabitatTranslator() {
            regionListTranslator = new ListTranslator<>(new TupleTranslator<>(new ElementTranslator<?>[]{StringTranslator.Instance, ZoneTranslator.ZoneListTranslator}));
        }

        @Override
        @SuppressWarnings("unchecked")
        public Map<String, Region> fromASTNode(CommonTree treeNode) throws ReadingException {
            ArrayList<ArrayList<Object>> rawHabitats = regionListTranslator.fromASTNode(treeNode);
            if (rawHabitats == null || rawHabitats.size() < 1) {
                return null;
            }
            HashMap<String, Region> habitats = new HashMap<>();
            for (ArrayList<Object> rawHabitat : rawHabitats) {
                if (rawHabitat.size() == 2) {
                    habitats.put((String) rawHabitat.get(0), new Region((ArrayList<Zone>) rawHabitat.get(1)));
                }
            }
            return habitats;
        }
    }

    /** END methods from SpiderDiagramsReader **/

    private static class ClassObjectPropertyTranslator extends ElementTranslator<ClassObjectPropertyDiagram> {
        static final ClassObjectPropertyTranslator Instance = new ClassObjectPropertyTranslator();

        private ClassObjectPropertyTranslator() {
        }

        @Override
        public ClassObjectPropertyDiagram fromASTNode(CommonTree treeNode) throws ReadingException {
            switch (treeNode.token.getType()) {
                case ConceptDiagramsParser.COP_PRIMARY:
                    return COPTranslator.Instance.fromASTNode(treeNode);
                case ConceptDiagramsParser.COP_INITIAL:
                    return new COPTranslator(true).fromASTNode(treeNode);
                default:
                    throw new ReadingException(i18n("ERR_UNKNOWN_SD_TYPE"));
            }
        }
    }

    private static class DatatypeTranslator extends ElementTranslator<DatatypeDiagram> {
        static final DatatypeTranslator Instance = new DatatypeTranslator();

        private DatatypeTranslator() {
        }

        @Override
        public DatatypeDiagram fromASTNode(CommonTree treeNode) throws ReadingException {
            switch (treeNode.token.getType()) {
                case ConceptDiagramsParser.DT:
                    return DTTranslator.Instance.fromASTNode(treeNode);
                default:
                    throw new ReadingException(i18n("ERR_UNKNOWN_SD_TYPE"));
            }
        }
    }

    private static class COPTranslator extends GeneralTranslator<ClassObjectPropertyDiagram> {
        static final COPTranslator Instance = new COPTranslator();
        boolean containsInitialT = false;

        private COPTranslator() {
            super(ConceptDiagramsParser.COP_PRIMARY);
            addAttributes();
        }

        private COPTranslator(boolean containsInitialT) {
            super(ConceptDiagramsParser.COP_INITIAL);
            this.containsInitialT = containsInitialT;
            addAttributes();
        }

        void addAttributes() {
            addMandatoryAttribute(SDTextSpidersAttribute, ListTranslator.StringListTranslator);
            addOptionalAttribute(SDTextHabitatsAttribute, HabitatTranslator.Instance);
            addOptionalAttribute(SDTextShadedZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(SDTextPresentZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(HighlightedZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(ArrowsAttribute, new ListTranslator<>(ArrowTranslator.Instance));
            addOptionalAttribute(KnownEqualityAttribute, new ListTranslator<>(new EqualityTranslator(true)));
            addOptionalAttribute(UnknownEqualityAttribute, new ListTranslator<>(new EqualityTranslator(false)));
            addOptionalAttribute(IdAttribute, StringTranslator.Instance);
        }

        @Override
        @SuppressWarnings("unchecked")
        ClassObjectPropertyDiagram createDiagram(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Map.Entry<Object, CommonTree> habitatsAttribute = attributes.get(SDTextHabitatsAttribute);
            Map.Entry<Object, CommonTree> shadedZonesAttribute = attributes.get(SDTextShadedZonesAttribute);
            Map.Entry<Object, CommonTree> presentZonesAttribute = attributes.get(SDTextPresentZonesAttribute);
            Map.Entry<Object, CommonTree> highlightedZonesAttribute = attributes.get(HighlightedZonesAttribute);
            Map.Entry<Object, CommonTree> arrowAttribute = attributes.get(ArrowsAttribute);
            Map.Entry<Object, CommonTree> knownEqualityAttribute = attributes.get(KnownEqualityAttribute);
            Map.Entry<Object, CommonTree> unknownEqualityAttribute = attributes.get(UnknownEqualityAttribute);
            Map.Entry<Object, CommonTree> idAttribute = attributes.get(IdAttribute);

            ClassObjectPropertyDiagram cop = ClassObjectPropertyDiagrams.createClassObjectPropertyDiagramNoCopy(
                    (Collection<String>) attributes.get(SDTextSpidersAttribute).getKey(),
                    habitatsAttribute == null ? null : (Map<String, Region>) attributes.get(SDTextHabitatsAttribute).getKey(),
                    shadedZonesAttribute == null ? null : (Collection<Zone>) attributes.get(SDTextShadedZonesAttribute).getKey(),
                    presentZonesAttribute == null ? null : (Collection<Zone>) presentZonesAttribute.getKey(),
                    highlightedZonesAttribute == null ? null : (Collection<Zone>) highlightedZonesAttribute.getKey(),
                    arrowAttribute == null ? null : (Collection<Arrow>) arrowAttribute.getKey(),
                    null,
                    knownEqualityAttribute == null ? null : (Collection<Equality>) knownEqualityAttribute.getKey(),
                    unknownEqualityAttribute == null ? null : (Collection<Equality>) unknownEqualityAttribute.getKey(),
                    containsInitialT);
            if (idAttribute != null) {
                cop.setId(Integer.parseInt((String) idAttribute.getKey()));
            }
            return cop;
        }
    }

    private static class DTTranslator extends GeneralTranslator<DatatypeDiagram> {
        static final DTTranslator Instance = new DTTranslator();

        private DTTranslator() {
            super(ConceptDiagramsParser.DT);
            addMandatoryAttribute(SDTextSpidersAttribute, ListTranslator.StringListTranslator);
            addOptionalAttribute(SDTextHabitatsAttribute, HabitatTranslator.Instance);
            addOptionalAttribute(SDTextShadedZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(SDTextPresentZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(HighlightedZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(KnownEqualityAttribute, new ListTranslator<>(new EqualityTranslator(true)));
            addOptionalAttribute(UnknownEqualityAttribute, new ListTranslator<>(new EqualityTranslator(false)));
        }

        @Override
        @SuppressWarnings("unchecked")
        DatatypeDiagram createDiagram(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Map.Entry<Object, CommonTree> habitatsAttribute = attributes.get(SDTextHabitatsAttribute);
            Map.Entry<Object, CommonTree> shadedZonesAttribute = attributes.get(SDTextShadedZonesAttribute);
            Map.Entry<Object, CommonTree> presentZonesAttribute = attributes.get(SDTextPresentZonesAttribute);
            Map.Entry<Object, CommonTree> highlightedZonesAttribute = attributes.get(HighlightedZonesAttribute);
            Map.Entry<Object, CommonTree> knownEqualityAttribute = attributes.get(KnownEqualityAttribute);
            Map.Entry<Object, CommonTree> unknownEqualityAttribute = attributes.get(UnknownEqualityAttribute);
            return ClassObjectPropertyDiagrams.createDatatypeDiagramNoCopy(
                    (Collection<String>) attributes.get(SDTextSpidersAttribute).getKey(),
                    habitatsAttribute == null ? null : (Map<String, Region>) attributes.get(SDTextHabitatsAttribute).getKey(),
                    shadedZonesAttribute == null ? null : (Collection<Zone>) attributes.get(SDTextShadedZonesAttribute).getKey(),
                    presentZonesAttribute == null ? null : (Collection<Zone>) presentZonesAttribute.getKey(),
                    highlightedZonesAttribute == null ? null : (Collection<Zone>) highlightedZonesAttribute.getKey(),
                    null,
                    knownEqualityAttribute == null ? null : (Collection<Equality>) knownEqualityAttribute.getKey(),
                    unknownEqualityAttribute == null ? null : (Collection<Equality>) unknownEqualityAttribute.getKey());
        }
    }

    private static class EqualityTranslator extends ElementTranslator<Equality> {
        private ListTranslator<String> translator;
        private boolean knownEquality;

        private EqualityTranslator(boolean knownEquality) {
            this.knownEquality = knownEquality;
            translator = new ListTranslator<>(ConceptDiagramsParser.SLIST, StringTranslator.Instance);
        }

        @Override
        public Equality fromASTNode(CommonTree treeNode) throws ReadingException {
            ArrayList<String> equalities = translator.fromASTNode(treeNode);
            if (equalities != null && equalities.size() == 2) {
                if (knownEquality) {
                    return new Equality(equalities.get(0), equalities.get(1));
                } else {
                    return new Equality(equalities.get(0), equalities.get(1), false);
                }
            }  else {
                throw new ReadingException("Error translating equality", treeNode);
            }
        }
    }

    private static abstract class GeneralTranslator<V> extends ElementTranslator<V>  {
        private GeneralMapTranslator<Object> keyValueMapTranslator;
        private TreeSet<String> mandatoryAttributes;

        private GeneralTranslator(int headTokenType) {
            keyValueMapTranslator = new GeneralMapTranslator<>(headTokenType, new HashMap<String, ElementTranslator<? extends Object>>(), null);
        }

        <T> void addMandatoryAttribute(String key, ElementTranslator<T> valueTranslator) {
            if (mandatoryAttributes == null) {
                mandatoryAttributes = new TreeSet<>();
            }
            mandatoryAttributes.add(key);
            keyValueMapTranslator.typedValueTranslators.put(key, valueTranslator);
        }

        <T> void addOptionalAttribute(String key, ElementTranslator<T> valueTranslator) {
            keyValueMapTranslator.typedValueTranslators.put(key, valueTranslator);
        }

        private boolean areMandatoryPresent(Map<String, ? extends Object> attributes) {
            if (mandatoryAttributes != null) {
                for (String string : mandatoryAttributes) {
                    if (!attributes.containsKey(string)) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public V fromASTNode(CommonTree treeNode) throws ReadingException {
            Map<String, Map.Entry<Object, CommonTree>> attrs = keyValueMapTranslator.fromASTNode(treeNode);
            if (areMandatoryPresent(attrs)) {
                return createDiagram(attrs, treeNode);
            } else {
                throw new ReadingException(i18n("ERR_TRANSLATE_MISSING_ELEMENTS", keyValueMapTranslator.typedValueTranslators.keySet()), treeNode);
            }
        }

        abstract V createDiagram(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException;
    }

    private static class TopTranslator extends ElementTranslator<Diagram> {
        static final TopTranslator Instance = new TopTranslator();

        private TopTranslator() { }

        @Override
        public Diagram fromASTNode(CommonTree treeNode) throws ReadingException {
            switch (treeNode.token.getType()) {
                case reader.ConceptDiagramsParser.CD:
                    return BasicCDTranslator.Instance.fromASTNode(treeNode);
                case reader.ConceptDiagramsParser.PD:
                    return BasicPDTranslator.Instance.fromASTNode(treeNode);
                default:
                    throw new ReadingException(i18n("ERR_UNKNOWN_SD_TYPE"));
            }
        }
    }

    private static class ArrowTranslator extends GeneralTranslator<Arrow> {
        static final ArrowTranslator Instance = new ArrowTranslator();

        private ArrowTranslator() {
            super(ConceptDiagramsParser.ARROW);
            addMandatoryAttribute(SourceAttribute, StringTranslator.Instance);
            addMandatoryAttribute(TargetAttribute, StringTranslator.Instance);
            addMandatoryAttribute(PropertyAttribute, StringTranslator.Instance);
            addOptionalAttribute(CardinalityOperator, StringTranslator.Instance);
            addOptionalAttribute(CardinalityArgument, StringTranslator.Instance);
            addOptionalAttribute(DashedAttribute, StringTranslator.Instance);
            addOptionalAttribute(SourceIdAttribute, StringTranslator.Instance);
            addOptionalAttribute(TargetIdAttribute, StringTranslator.Instance);
        }

        @Override
        Arrow createDiagram(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Map.Entry<Object, CommonTree> op = attributes.get(CardinalityOperator);
            Map.Entry<Object, CommonTree> arg = attributes.get(CardinalityArgument);
            Map.Entry<Object, CommonTree> dashed = attributes.get(DashedAttribute);
            Map.Entry<Object, CommonTree> sourceIdAttribute = attributes.get(SourceIdAttribute);
            Map.Entry<Object, CommonTree> targetIdAttribute = attributes.get(TargetIdAttribute);

            Arrow arrow =  new Arrow((String) attributes.get(PropertyAttribute).getKey(),
                    (String) attributes.get(SourceAttribute).getKey(),
                    (String) attributes.get(TargetAttribute).getKey(),
                    op == null ? null : (String) op.getKey(),
                    arg == null ? 0 : Integer.parseInt((String) arg.getKey()),
                    dashed != null && Boolean.parseBoolean((String) dashed.getKey()));
            if  (sourceIdAttribute != null) {
                int sourceId = Integer.parseInt((String) sourceIdAttribute.getKey());
                arrow.setSourceId(sourceId);
            }
            if (targetIdAttribute != null) {
                int targetId = Integer.parseInt((String) targetIdAttribute.getKey());
                arrow.setTargetId(targetId);
            }
            return arrow;
        }
    }

    private static class BasicCDTranslator extends GeneralTranslator<ConceptDiagram> {
        static final BasicCDTranslator Instance = new BasicCDTranslator();

        private BasicCDTranslator() {
            super(reader.ConceptDiagramsParser.CD);
            addOptionalAttribute(COPDiagramAttribute, new ListTranslator<>(ClassObjectPropertyTranslator.Instance));
            addOptionalAttribute(DTDiagramAttribute, new ListTranslator<>(DatatypeTranslator.Instance));
            addOptionalAttribute(ArrowsAttribute, new ListTranslator<>(ArrowTranslator.Instance));
        }

        @Override
        @SuppressWarnings("unchecked")
        ConceptDiagram createDiagram(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Map.Entry<Object, CommonTree> COPAttribute = attributes.get(COPDiagramAttribute);
            Map.Entry<Object, CommonTree> arrowAttribute = attributes.get(ArrowsAttribute);
            Map.Entry<Object, CommonTree> DTAttribute = attributes.get(DTDiagramAttribute);

            return ConceptDiagrams.createBasicConceptDiagram(
                    COPAttribute == null ? null : (ArrayList<ClassObjectPropertyDiagram>) attributes.get(COPDiagramAttribute).getKey(),
                    DTAttribute == null ? null : (ArrayList<DatatypeDiagram>) DTAttribute.getKey(),
                    arrowAttribute == null ? null : (ArrayList<Arrow>) arrowAttribute.getKey());
        }
    }

    private static class BasicPDTranslator extends GeneralTranslator<PropertyDiagram> {
        static final BasicPDTranslator Instance = new BasicPDTranslator();

        private BasicPDTranslator() {
            super(reader.ConceptDiagramsParser.PD);
            addOptionalAttribute(COPDiagramAttribute, new ListTranslator<>(ClassObjectPropertyTranslator.Instance));
            addOptionalAttribute(DTDiagramAttribute, new ListTranslator<>(DatatypeTranslator.Instance));
            addOptionalAttribute(ArrowsAttribute, new ListTranslator<>(ArrowTranslator.Instance));
        }

        @Override
        @SuppressWarnings("unchecked")
        PropertyDiagram createDiagram(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Map.Entry<Object, CommonTree> COPAttribute = attributes.get(COPDiagramAttribute);
            Map.Entry<Object, CommonTree> DTAttribute = attributes.get(DTDiagramAttribute);
            Map.Entry<Object, CommonTree> arrowAttribute = attributes.get(ArrowsAttribute);

            return PropertyDiagrams.createBasicPropertyDiagram(
                    COPAttribute == null ? null : (ArrayList<ClassObjectPropertyDiagram>) attributes.get(COPDiagramAttribute).getKey(),
                    DTAttribute == null ? null : (ArrayList<DatatypeDiagram>) attributes.get(DTDiagramAttribute).getKey(),
                    arrowAttribute == null ? null : (ArrayList<Arrow>) arrowAttribute.getKey());
        }
    }
}