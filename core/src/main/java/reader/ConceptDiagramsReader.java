package reader;

import lang.ConceptDiagram;
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

    private abstract static class ElementTranslator<T> {

        public abstract T fromASTNode(CommonTree treeNode) throws ReadingException;
    }

    private static abstract class GeneralCDTranslator<V extends ConceptDiagram>  {

    }

    private static class CDTranslator extends ElementTranslator<ConceptDiagram> {
        public static final CDTranslator Instance = new CDTranslator();

        private CDTranslator() { }

        @Override
        public ConceptDiagram fromASTNode(CommonTree treeNode) throws ReadingException {
            switch (treeNode.token.getType()) {
                case SpiderDiagramsParser.SD_BINARY:
            }
            return null;
        }
    }
}
