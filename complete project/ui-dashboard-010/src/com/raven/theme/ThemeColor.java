package com.raven.theme;

import java.awt.Color;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public abstract class ThemeColor {

    public TimingTarget getTarget() {
        return target;
    }

    public void setTarget(TimingTarget target) {
        this.target = target;
    }

    public ThemeColor(Color dark, Color light) {
        this.dark = dark;
        this.light = light;
    }

    public ThemeColor() {
    }

    private Color dark;
    private Color light;
    private TimingTarget target;

    public TimingTarget newTarget(ThemeColorChange.Mode mode) {
        if (mode == ThemeColorChange.Mode.DARK) {
            target = new PropertySetter(this, "colorChange", light, dark);
        } else {
            target = new PropertySetter(this, "colorChange", dark, light);
        }
        return target;
    }

    public void setColorChange(Color color) {
        onColorChange(color);
    }

    public abstract void onColorChange(Color color);
}
