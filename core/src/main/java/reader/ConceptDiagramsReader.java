package reader;

import i18n.Translations;
import lang.BasicConceptDiagram;
import lang.ConceptDiagram;
import lang.ConceptDiagrams;
import org.antlr.runtime.tree.CommonTree;
import reader.ConceptDiagramsLexer;
import reader.ConceptDiagramsParser;
import reader.ConceptDiagramsParser.conceptDiagram_return;
import org.antlr.runtime.*;
import speedith.core.lang.reader.ParseException;
import speedith.core.lang.reader.ReadingException;
import speedith.core.lang.reader.SpiderDiagramsParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;

import static lang.BasicConceptDiagram.CDTextSpiderDiagramAttribute;
import static speedith.i18n.Translations.i18n;

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
        ConceptDiagramsLexer lexer = new ConceptDiagramsLexer(stream);
        ConceptDiagramsParser parser = new ConceptDiagramsParser(new CommonTokenStream(lexer));

        try {
            return toConceptDiagram(parser.conceptDiagram());
        } catch (RecognitionException re) {
            System.err.println("Invalid Syntax");
            throw new ReadingException(i18n("ERR_PARSE_INVALID_SYNTAX"), re);
        } catch (ParseException pe) {
            System.err.println("Parse Exception");
            throw new ReadingException(pe.getMessage(), pe);
        }
    }

    private static ConceptDiagram toConceptDiagram(conceptDiagram_return cd) throws ReadingException {
        if (cd == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "conceptDiagram"));
        }
        System.out.println("We got a non-null conceptDiagram_return");
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
            if (treeNode.token != null && treeNode.token.getType() == speedith.core.lang.reader.SpiderDiagramsParser.ID) {
                return treeNode.token.getText();
            }
            throw new ReadingException(i18n("ERR_TRANSLATE_INVALID_ID"), treeNode);
        }
    }

    private static abstract class CollectionTranslator<V> extends ElementTranslator<ArrayList<V>> {

        private int headTokenType;

        public CollectionTranslator(int headTokenType) {
            if (headTokenType == SpiderDiagramsParser.SLIST || headTokenType == SpiderDiagramsParser.LIST) {
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
            if (treeNode.token != null && treeNode.token.getType() == speedith.core.lang.reader.SpiderDiagramsParser.STRING) {
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
            this(SpiderDiagramsParser.LIST, valueTranslator);
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
        private int headTokenType = SpiderDiagramsParser.DICT;

        public GeneralMapTranslator(Map<String, ElementTranslator<? extends V>> typedValueTranslators) {
            this(typedValueTranslators, null);
        }

        public GeneralMapTranslator(ElementTranslator<? extends V> defaultValueTranslator) {
            this(null, defaultValueTranslator);
        }

        public GeneralMapTranslator(Map<String, ElementTranslator<? extends V>> typedValueTranslators, ElementTranslator<? extends V> defaultValueTranslator) {
            this(SpiderDiagramsParser.DICT, typedValueTranslators, defaultValueTranslator);
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
                    if (node.token != null && node.token.getType() == SpiderDiagramsParser.PAIR && node.getChildCount() == 2) {
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
                        throw new ReadingException("Stumbled upon an unexpected element in the spider diagram description. Expected: %s key = value");
                    }
                }
                return kVals;
            }
            throw new ReadingException(i18n("ERR_TRANSLATE_UNEXPECTED_ELEMENT", i18n(i18n("ERR_TRANSLATE_LIST_OR_SLIST"))), treeNode);
        }
    }

    /** END methods from SpiderDiagramsReader **/

    private static abstract class GeneralCDTranslator<V extends ConceptDiagram> extends ElementTranslator<V>  {
        private GeneralMapTranslator<Object> keyValueMapTranslator;
        private TreeSet<String> mandatoryAttributes;

        private GeneralCDTranslator(int headTokenType) {
            keyValueMapTranslator = new GeneralMapTranslator<Object>(headTokenType, new HashMap<String, ElementTranslator<? extends Object>>(), null);
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
                case ConceptDiagramsParser.CD_BASIC:
                    System.out.println("Matched on CD_BASIC");
                    return BasicCDTranslator.Instance.fromASTNode(treeNode);
//                    return SpiderDiagramsReader.readSpiderDiagram(treeNode.token.getText());
                default:
                    System.err.println("couldn't find a match for the token.");
                    throw new ReadingException(i18n("ERR_UNKNOWN_SD_TYPE"));
            }
        }
    }

    private static class BasicCDTranslator extends GeneralCDTranslator<BasicConceptDiagram> {

        public static final BasicCDTranslator Instance = new BasicCDTranslator();

        private BasicCDTranslator() {
            super(ConceptDiagramsParser.CD_BASIC);
            addMandatoryAttribute(CDTextSpiderDiagramAttribute, ListTranslator.StringListTranslator);
        }

        @Override
        BasicConceptDiagram createCD(Map<String, Map.Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            return ConceptDiagrams.createBasicConceptDiagram((Collection<String>) attributes.get(CDTextSpiderDiagramAttribute).getKey());
        }
    }
}
