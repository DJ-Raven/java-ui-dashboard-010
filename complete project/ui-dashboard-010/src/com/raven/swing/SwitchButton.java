package com.raven.swing;

import com.raven.theme.SystemTheme;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class SwitchButton extends JComponent {

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            location = getPreferredSize().width - getPreferredSize().height + 2;
            setBackground(SystemTheme.mainColor);
        }
        repaint();
        runEvent(selected);
    }

    public void setSelectedAnimate(boolean selected) {
        if (this.selected != selected) {
            this.selected = !selected;
            startAnimation();
        }
    }

    private Animator animator;
    private float location;
    private boolean selected;
    private boolean mouseOver;
    private PropertySetter colorTarget;
    private List<EventSwitchSelected> events;
    private Color color;

    public SwitchButton() {
        setColor(new Color(80, 80, 80));
        setPreferredSize(new Dimension(35, 20));
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        events = new ArrayList<>();
        location = 2;
        colorTarget = new PropertySetter(this, "color", new Color(220, 220, 220), new Color(50, 50, 50));
        TimingTarget target = new TimingTargetAdapter() {
            private int width;

            @Override
            public void begin() {
                width = getWidth() - getHeight();
            }

            @Override
            public void timingEvent(float fraction) {
                if (isSelected()) {
                    location = width * (1f - fraction) + 2;
                } else {
                    location = width * fraction + 2;
                }
                repaint();
            }

            @Override
            public void end() {
                selected = !selected;
            }
        };
        animator = new Animator(300, target);
        animator.addTarget(colorTarget);
        animator.setResolution(0);
        animator.setDeceleration(.5f);
        animator.setAcceleration(.5f);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    if (mouseOver) {
                        startAnimation();
                    }
                }
            }
        });
    }

    private void startAnimation() {
        if (!animator.isRunning()) {
            if (isSelected()) {
                animator.removeTarget(colorTarget);
                colorTarget = new PropertySetter(SwitchButton.this, "color", SystemTheme.mainColor, new Color(80, 80, 80));
                animator.addTarget(colorTarget);
            } else {
                animator.removeTarget(colorTarget);
                colorTarget = new PropertySetter(SwitchButton.this, "color", new Color(80, 80, 80), SystemTheme.mainColor);
                animator.addTarget(colorTarget);
            }
            animator.start();
            runEvent(!selected);
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        g2.setColor(color);
        g2.fillRoundRect(0, 0, width, height, height, height);
        g2.setColor(getForeground());
        g2.fillOval((int) location, 2, height - 4, height - 4);
        g2.dispose();
        super.paintComponent(grphcs);
    }

    private void runEvent(boolean selected) {
        for (EventSwitchSelected event : events) {
            event.onSelected(selected);
        }
    }

    public void addEventSelected(EventSwitchSelected event) {
        events.add(event);
    }

    @Override
    public void setBackground(Color color) {
        if (isSelected()) {
            this.color = color;
        }
        super.setBackground(color);
    }

    public Animator getAnimator() {
        return animator;
    }
}
