package gui;

import concrete.ConcreteConceptDiagram;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Modified version from CirclesPanel, to draw arrows
 */
public class DiagramPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private ConcreteConceptDiagram cd;
    DiagramPanel dp;

    ConcreteConceptDiagram getDiagram() {
        return cd;
    }

    private void init(String desc, String failureMessages, ConcreteConceptDiagram diagram, int size, boolean useColours) {
        this.cd = diagram;
        setLayout(new BorderLayout());
        setBorder(new LineBorder(new Color(0,0,0), 1, true));

        int labelHeight = 0;
        if (desc != null && !desc.isEmpty()) {

        }
    }

    @Override
    public void paint(Graphics g) {
        // Draw bounding rectangles!

        // Draw curved arrows + dashed arrows!
    }
}
