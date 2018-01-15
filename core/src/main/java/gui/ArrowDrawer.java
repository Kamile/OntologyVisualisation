package gui;

import concrete.ConcreteArrowEnd;
import lang.Arrow;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class ArrowDrawer extends JPanel {
    private List<Arrow> arrows;
    private HashMap<String, ConcreteArrowEnd> targetMappings;

    public ArrowDrawer(List<Arrow> arrows, HashMap<String, ConcreteArrowEnd> targetMappings) {
        this.arrows = arrows;
        this.targetMappings = targetMappings;
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(255,255,255,255));
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("In paint in ArrowDrawer");
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (arrows != null) {
            super.paintComponent(g);
            g2d.setBackground(new Color(255,255,255,255));
            for (Arrow a: arrows) {
                String label = a.getLabel();
                String source = a.getSource();
                String target = a.getTarget();
                if (targetMappings.keySet().size() > 0) {
                    ConcreteArrowEnd arrowSource = targetMappings.get(source);
                    ConcreteArrowEnd arrowTarget = targetMappings.get(target);
                    System.out.println("Drawing arrows");
                    g2d.drawLine((int) arrowSource.getX(), (int) arrowSource.getY(), (int) arrowTarget.getX(), (int) arrowTarget.getY());
                }
            }
        }
    }



}
