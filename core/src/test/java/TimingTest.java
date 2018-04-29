import gui.MainForm;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static reader.ExampleAxioms.*;


public class TimingTest {

    private long startTime;
    private static MainForm mf;

    @BeforeClass
    public static void init() {
        mf = new MainForm();
    }

    @Before
    public void startTimer() {
        startTime = System.nanoTime();

    }

    @After
    public void endTimer() {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  // in ms
        System.out.println(duration);
    }

    @Test
    public void drawOneContour() {
        mf.testVisualisation(SINGLE_CONTOUR);
    }

    @Test
    public void drawSimpleEulerDiagram() {
        mf.testVisualisation(CLASS_EXPRESSION);
    }

    @Test
    public void drawConceptDiagram_oneCOP() {
        mf.testVisualisation(DATATYPE_DEFINITION);

    }

    @Test
    public void drawConceptDiagram_twoCOPs_oneArrow() {
        mf.testVisualisation(OBJECT_SOME_VALUE);

    }

    @Test
    public void drawPropertyChain() {
        mf.testVisualisation(PROPERTY_CHAIN);
    }

}
