package gui;

import concrete.ConcreteArrowEnd;
import lang.Arrow;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class ArrowPanel extends JComponent {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);
    private static final int headLength = 15;
    private static final double phi = Math.PI/6;

    private List<Arrow> arrows;
    private HashMap<String, ConcreteArrowEnd> targetMappings;

    ArrowPanel() {
        this(null, null);
    }

    ArrowPanel(List<Arrow> arrows, HashMap<String, ConcreteArrowEnd> targetMappings) {
        this.arrows = arrows;
        this.targetMappings = targetMappings;
        initComponents();
    }

    private void initComponents() {
        setMinimumSize(MINIMUM_SIZE);
        setPreferredSize(PREFERRED_SIZE);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.translate(0, 22);
        g.setColor(new Color(10, 86, 0, 255));
        g.setFont(new Font("Courier", Font.PLAIN, 16));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (arrows != null && targetMappings.keySet().size() > 0) {
            super.paintComponent(g);
            for (Arrow a: arrows) {
                String label = a.getLabel();
                String source = a.getSource();
                String target = a.getTarget();
                ConcreteArrowEnd arrowSource = targetMappings.get(source);
                ConcreteArrowEnd arrowTarget = targetMappings.get(target);
                double x1 = arrowSource.getX();
                double y1 = arrowSource.getY();
                double x2 = arrowTarget.getX();
                double y2 = arrowTarget.getY();
                g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

                // draw arrowheads for target
                double theta = Math.atan2(y2 - y1, x2 - x1);
                double x = x2 - headLength * Math.cos(theta + phi);
                double y = y2 - headLength * Math.sin(theta + phi);
                g2d.drawLine((int) x2, (int) y2, (int) x, (int) y);
                x = x2 - headLength * Math.cos(theta - phi);
                y = y2 - headLength * Math.sin(theta - phi);
                g2d.drawLine((int) x2, (int) y2, (int) x, (int) y);

                // add label
                g2d.drawString(label, (int)(Math.min(x1, x2) + Math.abs(x2 - x1)/2), (int)(Math.min(y1, y2) + Math.abs(y2 - y1)/2) - 8);
            }
        }
    }
}
