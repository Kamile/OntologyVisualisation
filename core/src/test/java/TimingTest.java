import gui.MainForm;

public class TimingTest {

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        drawOneContour();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  // in ms
        System.out.println(duration);

    }

    private static void drawOneContour() {
        MainForm mf = new MainForm();
    }

    private static void drawSimpleEulerDiagram() {

    }

    private static void drawConceptDiagram_oneCOP() {

    }

    private static void drawConceptDiagram_twoCOPs_oneArrow() {

    }

    private static void drawPropertyChain() {

    }

}
