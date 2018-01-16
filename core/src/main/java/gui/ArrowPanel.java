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
        setBorder(BorderFactory.createLineBorder(new Color(166,255, 88, 132)));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.translate(0, 22);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (arrows != null && targetMappings.keySet().size() > 0) {
            super.paintComponent(g);
            for (Arrow a: arrows) {
                String label = a.getLabel();
                String source = a.getSource();
                String target = a.getTarget();
                ConcreteArrowEnd arrowSource = targetMappings.get(source);
                ConcreteArrowEnd arrowTarget = targetMappings.get(target);
                g2d.drawLine((int) arrowSource.getX(), (int) arrowSource.getY(), (int) arrowTarget.getX(), (int) arrowTarget.getY());
            }
        }
    }
}
