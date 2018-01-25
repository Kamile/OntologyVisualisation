package gui;

import concrete.ConcreteArrowEnd;
import lang.Arrow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.util.HashMap;
import java.util.List;

public class ArrowPanel extends JComponent {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);
    private static float dash[] = {10.0f};
    private static final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
    private static final int headLength = 15;
    private static final double phi = Math.PI/6;

    private List<Arrow> arrows;
    private HashMap<String, ConcreteArrowEnd> targetMappings;
    private HashMap<Arrow, Double> arrowOffsets;

    ArrowPanel() {
        this(null, null);
    }

    ArrowPanel(List<Arrow> arrows, HashMap<String, ConcreteArrowEnd> targetMappings) {
        this.arrows = arrows;
        this.targetMappings = targetMappings;
        if (arrows != null) {
            arrowOffsets = new HashMap<>();
            for (Arrow a : arrows) {
                arrowOffsets.put(a, getRandomOffset());
            }
        }
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
        if (targetMappings != null) {
            System.out.println("target mappings exist: " + (targetMappings.keySet().size() > 0));
        }
        if (arrows != null && targetMappings.keySet().size() > 0) {
            System.out.println("Painting arrows with non-null target mappings");
            super.paintComponent(g);
            for (Arrow a: arrows) {
                String label = a.getLabel();
                String operator = a.getCardinalityOperator();
                int argument = a.getCardinalityArgument();
                label = label + " " + getOperator(operator) + " " + argument;

                String source = a.getSource();
                String target = a.getTarget();
                ConcreteArrowEnd arrowSource = targetMappings.get(source);
                ConcreteArrowEnd arrowTarget = targetMappings.get(target);
                double x1 = arrowSource.getX();
                double y1 = arrowSource.getY();
                double x2 = arrowTarget.getX();
                double y2 = arrowTarget.getY();

                System.out.println("x1 " + x1
                    + " y1 " + y1 + " x2 " + x2 + " y2 " + y2 );

                double gradient = (y2-y1)/(x2-x1);
                double perpendicular = -1/gradient;
                double midX = (Math.min(x1, x2) + Math.abs(x2 - x1)/2);
                double midY = (Math.min(y1, y2) + Math.abs(y2 - y1)/2);

                //get random control point
                double cx = midX - arrowOffsets.get(a)*perpendicular*getWidth()/1000;
                double cy = midY - arrowOffsets.get(a)*perpendicular*getHeight()/1000;

                QuadCurve2D curve = new QuadCurve2D.Double();
                curve.setCurve(x1, y1, cx, cy, x2, y2);
                if (a.isDashed()) {
                    g2d.setStroke(dashed);
                }
                g2d.draw(curve);

                // draw arrowheads for target
                double theta = Math.atan2(y2 - cy, x2 - cx);
                double x = x2 - headLength * Math.cos(theta + phi);
                double y = y2 - headLength * Math.sin(theta + phi);
                g2d.drawLine((int) x2, (int) y2, (int) x, (int) y);
                x = x2 - headLength * Math.cos(theta - phi);
                y = y2 - headLength * Math.sin(theta - phi);
                g2d.drawLine((int) x2, (int) y2, (int) x, (int) y);

                // add label
                double offset;
                if (curve.getFlatness() < 10) {
                    offset = 15;
                } else if (Math.abs(gradient) < 2) {
                    offset = curve.getFlatness()*gradient;
                } else {
                    offset = curve.getFlatness()/(gradient*2);
                }
                g2d.drawString(label, (int) midX - 15, (int) (midY + 2*offset));
            }
        }
    }

    private static double getRandomOffset() {
        return Math.floor(Math.random()*31 + 10);
    }

    private String getOperator(String operator) {
        operator = operator.toLowerCase();
        switch (operator) {
            case "leq":
                return "<";
            default:
                return "";
        }
    }
}
