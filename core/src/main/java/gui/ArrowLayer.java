package gui;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;

public class ArrowLayer {
    private static JComponent component;

    public ArrowLayer(JComponent component) {
        this.component = component;
    }

    public JLayer<JComponent> createLayer() {
        LayerUI<JComponent> layerUI = new LayerUI<JComponent>() {
            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g,c);
                g.setColor(new Color(0, 128, 0, 128));
                g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }

            @Override
            public void installUI(JComponent c) {
                super.installUI(c);
            }

            @Override
            public void uninstallUI(JComponent c) {
                super.uninstallUI(c);
            }

        };
        return new JLayer<>(component, layerUI);
    }
}
