package com.raven.swing.scrollbar;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

public class ScrollBarCustom extends JScrollBar {

    public ScrollBarCustom() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(7, 8));
        setForeground(new Color(142, 206, 255));
        setUnitIncrement(20);
        setOpaque(false);
    }
}
