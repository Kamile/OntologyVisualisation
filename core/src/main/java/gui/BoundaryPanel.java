package gui;

import abstractDescription.AbstractConceptDiagramDescription;
import concrete.ConcreteArrowEnd;
import concrete.ConcreteConceptDiagram;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.util.CannotDrawException;
import lang.Arrow;
import lang.BasicConceptDiagram;
import lang.BoundaryRectangle;
import lang.ConceptDiagram;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static speedith.i18n.Translations.i18n;

public class BoundaryPanel extends JPanel {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);

    private ArrowPanel arrowPanel;
    private ConceptDiagram conceptDiagram;
    private HashMap<String, ConcreteArrowEnd> targetMappings;
    private List<Arrow> arrows;

    /**
     * Create new diagram panel with nothing displayed in it.
     */
    public BoundaryPanel() {
        this(null);
        drawNoDiagramLabel();
    }

    /**
     * Create panel visualising given Concept Diagram
     *
     * @param cd
     */
    public BoundaryPanel(ConceptDiagram cd) {
        initComponents();
        setDiagram(cd);
    }

    private void initComponents() {
        setMinimumSize(MINIMUM_SIZE);
        setPreferredSize(PREFERRED_SIZE);
        setBackground(Color.WHITE);
        arrowPanel = new ArrowPanel();
    }

    public final void setDiagram(ConceptDiagram diagram) {
        if (conceptDiagram != diagram) {
            conceptDiagram = diagram;
            this.removeAll();
            if (conceptDiagram != null) {
                try {
                    drawDiagram();
                } catch (CannotDrawException e) {
                    drawErrorLabel();
                }
            } else {
                drawNoDiagramLabel();
            }
            validate();
            repaint();
        }
    }

    private void drawErrorLabel() {
        JLabel errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLabel.setText(i18n("PSD_LABEL_DISPLAY_ERROR"));
        this.add(errorLabel);
    }

    private void drawNoDiagramLabel() {
        JLabel noDiagramLbl = new JLabel();
        noDiagramLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noDiagramLbl.setText(i18n("CSD_PANEL_NO_DIAGRAM"));
        this.add(noDiagramLbl);
    }

    private void drawDiagram() throws CannotDrawException {
        if (conceptDiagram != null) {
            if (conceptDiagram instanceof BasicConceptDiagram) {
                arrows = ((BasicConceptDiagram) conceptDiagram).getArrows();
                this.setLayout(new GridLayout(1, 0));

                AbstractConceptDiagramDescription ad2 = AbstractDescriptionTranslator.getAbstractDescription((BasicConceptDiagram) conceptDiagram);
                final ConcreteConceptDiagram ccd = ConcreteConceptDiagram.makeConcreteDiagram(ad2, 300);
                targetMappings = new HashMap<>();

                HashMap<BoundaryRectangle, Set<ConcreteDiagram>> mapping = ccd.getBoundarySpiderDiagramMapping();
                for (BoundaryRectangle br : mapping.keySet()) {
                    final DiagramPanel dp = new DiagramPanel(mapping.get(br));

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            targetMappings.putAll(dp.getTargetMappings());
                            System.out.println(targetMappings.keySet().size());
                        }
                    });
                    dp.setVisible(true);
                    dp.setBorder(BorderFactory.createLineBorder(new Color(2,0, 113)));
                    add(dp);
                }

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        addArrows(targetMappings, arrows);
                    }
                });
            } else {
                throw new IllegalArgumentException(i18n("SD_PANEL_UNKNOWN_DIAGRAM_TYPE"));
            }
        }
    }

    public ArrowPanel getArrowGlassPanel() {
        return arrowPanel;
    }

    private void addArrows(final HashMap<String, ConcreteArrowEnd> targetMappings, final List<Arrow> arrows) {
        arrowPanel = new ArrowPanel(arrows, targetMappings);
    }
}
