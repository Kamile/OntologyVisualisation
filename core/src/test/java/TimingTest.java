import gui.MainForm;
import org.junit.*;

import static reader.ExampleAxioms.*;

public class TimingTest {

    private long startTime;
    private static MainForm mf;
    private static long[] timings;
    private static int i;

    @BeforeClass
    public static void init() {
        mf = new MainForm();
        timings = new long[8];
        i = 0;
    }

    @Before
    public void startTimer() {
        startTime = System.nanoTime();
    }

    @After
    public void endTimer() {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  // in ms
        timings[i] = duration;
        i++;
        System.out.println(duration);
    }

    @Test
    public void drawOneContour() {
        mf.testVisualisation(SINGLE_CONTOUR);
    }

    @Test
    public void drawTwoDistinctCurves() {
        mf.testVisualisation(TWO_DISTINCT_CURVES);
    }

    @Test
    public void drawThreeDistinctCurves() {
        mf.testVisualisation(THREE_DISTINCT_CURVES);
    }

    @Test
    public void drawFourDistinctCurves() {
        mf.testVisualisation(FOUR_DISTINCT_CURVES);
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
