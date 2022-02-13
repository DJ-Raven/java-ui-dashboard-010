package com.raven.swing;

import com.raven.theme.SystemTheme;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;

public class ButtomImage extends JButton {

    public ButtomImage() {
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(100, 56));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        if (isSelected()) {
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(SystemTheme.mainColor);
            int width = getWidth();
            int height = getHeight();
            Area area = new Area(new Rectangle2D.Double(0, 0, width, height));
            area.subtract(new Area(new Rectangle2D.Double(2, 2, width - 4, height - 4)));
            g2.fill(area);
            g2.dispose();
        }
    }
}
