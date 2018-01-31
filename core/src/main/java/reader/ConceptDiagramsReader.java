package reader;

import lang.*;
import org.antlr.runtime.tree.CommonTree;
import reader.ConceptDiagramsParser.conceptDiagram_return;
import org.antlr.runtime.*;
import speedith.core.lang.*;
import speedith.core.lang.reader.ParseException;
import speedith.core.lang.reader.ReadingException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;

import static i18n.Translations.i18n;
import static lang.ConceptDiagram.CDTextArrowsAttribute;
import static lang.ConceptDiagram.CDTextCOPDiagramAttribute;
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
public class ConceptDiagramsReader {

    private ConceptDiagramsReader() {}

    public static ConceptDiagram readConceptDiagram(String input) throws ReadingException {
        return readConceptDiagram(new ANTLRStringStream(input));
    }

    public static ConceptDiagram readConceptDiagram(Reader reader) throws ReadingException, IOException {
        return readConceptDiagram(new ANTLRReaderStream(reader));
    }

    public static ConceptDiagram readConceptDiagram(InputStream input) throws ReadingException, IOException {
        return readConceptDiagram(new ANTLRInputStream(input));
    }

    public static ConceptDiagram readConceptDiagram(InputStream input, String encoding) throws ReadingException, IOException {
        return readConceptDiagram(new ANTLRInputStream(input, encoding));
    }

    public static ConceptDiagram readConceptDiagram(File file) throws ReadingException, IOException {
        return readConceptDiagram(new ANTLRFileStream(file.getPath()));
    }

    public static ConceptDiagram readConceptDiagram(File file, String encoding) throws ReadingException, IOException {
        return readConceptDiagram(new ANTLRFileStream(file.getPath(), encoding));
    }

    public static ConceptDiagram readConceptDiagram(CharStream stream) throws ReadingException {
        reader.ConceptDiagramsLexer lexer = new reader.ConceptDiagramsLexer(stream);
        reader.ConceptDiagramsParser parser = new reader.ConceptDiagramsParser(new CommonTokenStream(lexer));

        try {
            return toConceptDiagram(parser.conceptDiagram());
        } catch (RecognitionException re) {
            throw new ReadingException(i18n("ERR_PARSE_INVALID_SYNTAX"), re);
        } catch (ParseException pe) {
            throw new ReadingException(pe.getMessage(), pe);
        }
    }

    private static ConceptDiagram toConceptDiagram(conceptDiagram_return cd) throws ReadingException {
        if (cd == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "conceptDiagram"));
        }
        return CDTranslator.Instance.fromASTNode(cd.tree);
    }

    /** BEGIN methods from SpiderDiagramsReader **/
    private abstract static class ElementTranslator<T> {

        public abstract T fromASTNode(CommonTree treeNode) throws ReadingException;
    }

    private static class IDTranslator extends ElementTranslator<String> {

        public static final IDTranslator Instance = new IDTranslator();

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

        public CollectionTranslator(int headTokenType) {
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

        public static final StringTranslator Instance = new StringTranslator();

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

        public static final ListTranslator<String> StringListTranslator = new ListTranslator<>(StringTranslator.Instance);
        ElementTranslator<? extends V> valueTranslator = null;

        public ListTranslator(ElementTranslator<? extends V> valueTranslator) {
            this(ConceptDiagramsParser.LIST, valueTranslator);
        }

        public ListTranslator(int headTokenType, ElementTranslator<? extends V> valueTranslator) {
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

        public GeneralMapTranslator(Map<String, ElementTranslator<? extends V>> typedValueTranslators) {
            this(typedValueTranslators, null);
        }

        public GeneralMapTranslator(ElementTranslator<? extends V> defaultValueTranslator) {
            this(null, defaultValueTranslator);
        }

        public GeneralMapTranslator(Map<String, ElementTranslator<? extends V>> typedValueTranslators, ElementTranslator<? extends V> defaultValueTranslator) {
            this(ConceptDiagramsParser.DICT, typedValueTranslators, defaultValueTranslator);
        }

        public GeneralMapTranslator(int headTokenType, Map<String, ElementTranslator<? extends V>> typedValueTranslators, ElementTranslator<? extends V> defaultElements) {
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
            throw new ReadingException(i18n("ERR_TRANSLATE_UNEXPECTED_ELEMENT", i18n(i18n("ERR_TRANSLATE_LIST_OR_SLIST"))), treeNode);
        }
    }

    private static class TupleTranslator<V> extends CollectionTranslator<V> {

        List<ElementTranslator<? extends V>> valueTranslators = null;

        public TupleTranslator(List<ElementTranslator<? extends V>> valueTranslators) {
            this(ConceptDiagramsParser.SLIST, valueTranslators);
        }

        public TupleTranslator(ElementTranslator<? extends V>[] valueTranslators) {
            this(ConceptDiagramsParser.SLIST, Arrays.asList(valueTranslators));
        }

        public TupleTranslator(int headTokenType, ElementTranslator<? extends V>[] valueTranslators) {
            this(headTokenType, Arrays.asList(valueTranslators));
        }

        public TupleTranslator(int headTokenType, List<ElementTranslator<? extends V>> valueTranslators) {
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

        public static final ZoneTranslator Instance = new ZoneTranslator();
        public static final ListTranslator<Zone> ZoneListTranslator = new ListTranslator<>(Instance);
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

        public static final HabitatTranslator Instance = new HabitatTranslator();
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

    private static abstract class GeneralCOPTranslator<V extends ClassObjectPropertyDiagram> extends ElementTranslator<V> {

        private GeneralMapTranslator<Object> keyValueMapTranslator;
        private TreeSet<String> mandatoryAttributes;

        private GeneralCOPTranslator(int headTokenType) {
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

        <T> void addDefaultAttribute(ElementTranslator<T> valueTranslator) {
            keyValueMapTranslator.defaultValueTranslator = valueTranslator;
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
                return createClassObjectPropertyDiagram(attrs, treeNode);
            } else {
                throw new ReadingException(i18n("ERR_TRANSLATE_MISSING_ELEMENTS", keyValueMapTranslator.typedValueTranslators.keySet()), treeNode);
            }
        }

        abstract V createClassObjectPropertyDiagram(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException;
    }

    private static class ClassObjectPropertyTranslator extends ElementTranslator<ClassObjectPropertyDiagram> {

        public static final ClassObjectPropertyTranslator Instance = new ClassObjectPropertyTranslator();

        private ClassObjectPropertyTranslator() {
        }

        @Override
        public ClassObjectPropertyDiagram fromASTNode(CommonTree treeNode) throws ReadingException {
            switch (treeNode.token.getType()) {
                case ConceptDiagramsParser.SD_PRIMARY:
                    return COPTranslator.Instance.fromASTNode(treeNode);
                default:
                    throw new ReadingException(i18n("ERR_UNKNOWN_SD_TYPE"));
            }
        }
    }



    private static class COPTranslator extends GeneralCOPTranslator<ClassObjectPropertyDiagram> {
        public static final COPTranslator Instance = new COPTranslator();

        private COPTranslator() {
            super(ConceptDiagramsParser.SD_PRIMARY);
            addMandatoryAttribute(SDTextSpidersAttribute, ListTranslator.StringListTranslator);
            addMandatoryAttribute(SDTextHabitatsAttribute, HabitatTranslator.Instance);
            addMandatoryAttribute(SDTextShadedZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(SDTextPresentZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(CDTextArrowsAttribute, new ListTranslator<>(ArrowTranslator.Instance));
        }

        @Override
        @SuppressWarnings("unchecked")
        ClassObjectPropertyDiagram createClassObjectPropertyDiagram(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Map.Entry<Object, CommonTree> presentZonesAttribute = attributes.get(SDTextPresentZonesAttribute);
            Map.Entry<Object, CommonTree> arrowAttribute = attributes.get(CDTextArrowsAttribute);

            return ClassObjectPropertyDiagrams.createClassObjectPropertyDiagramNoClassObjectPropertyy((Collection<String>) attributes.get(SDTextSpidersAttribute).getKey(),
                    (Map<String, Region>) attributes.get(SDTextHabitatsAttribute).getKey(),
                    (Collection<Zone>) attributes.get(SDTextShadedZonesAttribute).getKey(),
                    presentZonesAttribute == null ? null : (Collection<Zone>) presentZonesAttribute.getKey(),
                    arrowAttribute == null ? null : (Collection<Arrow>) arrowAttribute.getKey());
        }
    }

    private static class ArrowTranslator extends ElementTranslator<Arrow> {
        public static final ArrowTranslator Instance = new ArrowTranslator();
        private ListTranslator<String> translator;

        private ArrowTranslator() {
            translator = new ListTranslator<>(ConceptDiagramsParser.SLIST, StringTranslator.Instance);
        }

        @Override
        public Arrow fromASTNode(CommonTree treeNode) throws ReadingException {
            ArrayList<String> arrows = translator.fromASTNode(treeNode);
            if (arrows != null && arrows.size() == 2) {
                return new Arrow(arrows.get(0), arrows.get(1));
            } else if (arrows != null && arrows.size() == 3) {
                return new Arrow(arrows.get(0), arrows.get(1), arrows.get(2));
            } else if (arrows!= null && arrows.size() == 6) {
                return new Arrow(arrows.get(0),
                        arrows.get(1),
                        arrows.get(2),
                        arrows.get(3),
                        Integer.parseInt(arrows.get(4)),
                        Boolean.parseBoolean(arrows.get(5)));
            }  else {
                throw new ReadingException("Error translating arrow", treeNode);
            }
        }
    }

    private static abstract class GeneralCDTranslator<V> extends ElementTranslator<V>  {
        private GeneralMapTranslator<Object> keyValueMapTranslator;
        private TreeSet<String> mandatoryAttributes;

        private GeneralCDTranslator(int headTokenType) {
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

        <T> void addDefaultAttribute(ElementTranslator<T> valueTranslator) {
            keyValueMapTranslator.defaultValueTranslator = valueTranslator;
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
                return createCD(attrs, treeNode);
            } else {
                throw new ReadingException(i18n("ERR_TRANSLATE_MISSING_ELEMENTS", keyValueMapTranslator.typedValueTranslators.keySet()), treeNode);
            }
        }

        abstract V createCD(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException;
    }

    private static class CDTranslator extends ElementTranslator<ConceptDiagram> {
        public static final CDTranslator Instance = new CDTranslator();

        private CDTranslator() { }

        @Override
        public ConceptDiagram fromASTNode(CommonTree treeNode) throws ReadingException {
            switch (treeNode.token.getType()) {
                case reader.ConceptDiagramsParser.CD:
                    return BasicCDTranslator.Instance.fromASTNode(treeNode);
                default:
                    throw new ReadingException(i18n("ERR_UNKNOWN_SD_TYPE"));
            }
        }
    }

    private static class BasicCDTranslator extends GeneralCDTranslator<ConceptDiagram> {

        public static final BasicCDTranslator Instance = new BasicCDTranslator();

        private BasicCDTranslator() {
            super(reader.ConceptDiagramsParser.CD);
            addMandatoryAttribute(CDTextCOPDiagramAttribute, new ListTranslator<>(ClassObjectPropertyTranslator.Instance));
            addOptionalAttribute(CDTextArrowsAttribute, new ListTranslator<>(ArrowTranslator.Instance));
        }

        @Override
        @SuppressWarnings("unchecked")
        ConceptDiagram createCD(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            return ConceptDiagrams.createBasicConceptDiagram((ArrayList<ClassObjectPropertyDiagram>) attributes.get(CDTextCOPDiagramAttribute).getKey(),
                    (ArrayList<Arrow>) attributes.get(CDTextArrowsAttribute).getKey());
        }
    }
}
