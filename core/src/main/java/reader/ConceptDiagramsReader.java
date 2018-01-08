package reader;

import lang.ConceptDiagram;
import org.antlr.runtime.*;
import speedith.core.lang.reader.ReadingException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

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
        return null;
    }
}
