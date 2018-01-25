package gui;

import concrete.ConcreteArrowEnd;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.util.CannotDrawException;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Set;

import static speedith.i18n.Translations.i18n;

/**
 * Bypass ProofPanel and SubgoalsPanel and implement version of SpiderDiagrams
 * Panel
 */
public class DiagramPanel extends JPanel {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);

    private Set<ConcreteDiagram> spiderDiagrams;
    private HashMap<String, ConcreteArrowEnd> targetMappings;

    public DiagramPanel(Set<ConcreteDiagram> spiderDiagrams) {
        targetMappings = new HashMap<>();
        initComponents();
        setDiagrams(spiderDiagrams);
    }

    private void initComponents() {
        setMinimumSize(MINIMUM_SIZE);
        setPreferredSize(PREFERRED_SIZE);
        setBackground(Color.WHITE);
    }

    private void drawErrorLabel() {
        JLabel errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLabel.setText(i18n("PSD_LABEL_DISPLAY_ERROR"));
        this.add(errorLabel);
    }

    public final void setDiagrams(Set<ConcreteDiagram> diagrams) {
        if (spiderDiagrams != diagrams) {
            spiderDiagrams = diagrams;
            this.removeAll();
            if (spiderDiagrams != null) {
                try {
                    drawDiagrams();
                } catch (CannotDrawException e) {
                    drawErrorLabel();
                }
            }
            validate();
            repaint();
        }
    }

    private void drawDiagrams() throws CannotDrawException {
        if (spiderDiagrams != null) {
            this.setLayout(new GridLayout(1, 0));
            for (ConcreteDiagram cd : spiderDiagrams) {
                ConceptDiagramsDrawer panel = new ConceptDiagramsDrawer(cd, targetMappings, this.getX());
                targetMappings.putAll(panel.getTargetMappings());
                System.out.println(targetMappings);
                panel.setBorder(BorderFactory.createEmptyBorder());
                panel.setVisible(true);
                add(panel);
            }
        }
    }

    public HashMap<String, ConcreteArrowEnd> getTargetMappings() {
        return targetMappings;
    }
}
