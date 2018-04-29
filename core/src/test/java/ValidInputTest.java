import org.junit.Test;
import reader.DiagramsReader;
import speedith.core.lang.reader.ReadingException;

import static reader.ExampleAxioms.CLASS_EXPRESSION;
import static reader.ExampleAxioms.INVALID_FIRST_TOKEN;

/**
 * Unit tests for Reader class. Verifying expected errors for invalid axiom expressions.
 */
public class ValidInputTest {

    @Test
    public void Valid_ClassExpression() throws ReadingException {
        DiagramsReader.readConceptDiagram(CLASS_EXPRESSION);
    }

    @Test(expected = ReadingException.class)
    public void Invalid_WrongHeadToken() throws ReadingException {
        DiagramsReader.readConceptDiagram(INVALID_FIRST_TOKEN);

    }
}
