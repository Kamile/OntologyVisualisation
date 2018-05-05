package evaluation;

import gui.MainForm;

import java.io.*;

import static reader.ExampleAxioms.*;
import static reader.ExampleAxioms.PROPERTY_CHAIN;

public class TimingTests {
    private static long startTime;
    private static long endTime;
    private static long duration;
    private static MainForm mf;

    public static void main(String[] args) {
        init();
        for (int i = 0; i < 20; i++) {
            timeIncreasingContours();
        }
        timeIncreasingZones();
    }

    private static void init() {
        System.out.println(FIVE_DISTINCT_CURVES);

        mf = new MainForm();

        // draw random to stabilise first drawing time
        mf.testVisualisation(CLASS_EXPRESSION);
    }

    private static void startTimer() {
        startTime = System.nanoTime();
    }

    private static void endTimer() {
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;  // in ms
        System.out.println(duration);
    }

    // Increasing number of distinct visible curves
    private static void timeIncreasingContours() {
        String[] axioms = {
            SINGLE_CONTOUR, TWO_DISTINCT_CURVES, THREE_DISTINCT_CURVES, FOUR_DISTINCT_CURVES,
                FIVE_DISTINCT_CURVES, SIX_DISTINCT_CURVES, SEVEN_DISTINCT_CURVES, EIGHT_DISTINCT_CURVES, NINE_DISTINCT_CURVES, TEN_DISTINCT_CURVES
        };
        time(axioms, "contours.txt");
    }

    // Increasing number of visible zones
    private static void timeIncreasingZones() {
        String[] axioms = {ZONES2, ZONES4, ZONES8, ZONES16, ZONES32, ZONES64, ZONES128, ZONES256};
        time(axioms, "zones.txt");
    }

    // Increasing number of COPs DTs - not connected by arrows
    private static void timeIncreasingSubdiagrams() {
        String[] axioms = {};
        time(axioms, "subdiagrams.txt");
    }

    // Increasing number of arrows within one COP
    private static void timeIncreasingInnerArrows() {
        String[] axioms = {};
        time(axioms, "innerarrows.txt");
    }

    // Increasing number of arrows between COPs and DTs
    private static void timeIncreasingOuterArrows() {
        // 1, 5
        String[] axioms = {OBJECT_SOME_VALUE, PROPERTY_CHAIN};
        time(axioms, "outerarrows.txt");
    }

    private static void time(String[] input, String fileName) {
        long[] timings = new long[input.length];
        int i = 0;
        for(String s: input) {
            startTimer();
            mf.testVisualisation(s);
            endTimer();
            timings[i] = duration;
            i++;
        }
        writeToFile(timings, fileName);
    }

    private static void writeToFile(long[] timings, String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("eval_data/" + fileName, true));
            for (long timing : timings) {
                out.append(timing + " ");
            }
            out.append("\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
