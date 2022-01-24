package com.raven.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelBackground extends JPanel {

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
        createImage();
        repaint();
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        repaint();
    }

    public PanelBackground() {
        setOpaque(false);
        super.setBackground(Color.WHITE);
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        createImage();
    }

    private int round = 15;
    private Icon image;
    private BufferedImage bffImage;

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        if (bffImage != null) {
            g2.drawImage(bffImage, 0, 0, null);
        } else {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), round, round);
        }
        g2.dispose();
        super.paint(grphcs);
    }

    private void createImage() {
        if (image != null) {
            int width = getWidth();
            int height = getHeight();
            Rectangle size = getAutoSize(image);
            bffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2_img = bffImage.createGraphics();
            g2_img.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2_img.fillRoundRect(0, 0, width, height, round, round);
            g2_img.setComposite(AlphaComposite.SrcIn);
            g2_img.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2_img.drawImage(toImage(image), size.x, size.y, size.width, size.height, null);
            g2_img.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.8f));
            g2_img.setColor(getBackground());
            g2_img.fillRect(0, 0, getWidth(), getHeight());
            g2_img.dispose();
        } else {
            bffImage = null;
        }
    }

    private Rectangle getAutoSize(Icon image) {
        int w = getWidth();
        int h = getHeight();
        if (w > image.getIconWidth()) {
            w = image.getIconWidth();
        }
        if (h > image.getIconHeight()) {
            h = image.getIconHeight();
        }
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        int x = getWidth() / 2 - (width / 2);
        int y = getHeight() / 2 - (height / 2);
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }

    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        createImage();
    }
}
