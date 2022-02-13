package com.raven.properties;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemProperties {

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public SystemProperties(Color color, boolean darkMode, String backgroundImage) {
        this.color = color;
        this.darkMode = darkMode;
        this.backgroundImage = backgroundImage;
    }

    public SystemProperties() {
    }

    private Color color;
    private boolean darkMode;
    private String backgroundImage;

    public void loadFromFile() {
        try {
            Properties pro = new Properties();
            FileInputStream in = new FileInputStream(new File("config.properties"));
            pro.load(in);
            String themColor = pro.getProperty("theme_color");
            color = new Color(Integer.valueOf(themColor));
            darkMode = Boolean.valueOf(pro.getProperty("dark_mode"));
            backgroundImage = pro.getProperty("background_image");
            in.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void save(String name, String values) {
        try {
            Properties pro = new Properties();
            File file = new File("config.properties");
            InputStream in = new FileInputStream(file);
            pro.load(in);
            pro.setProperty(name, values);
            pro.store(new FileOutputStream(file), null);
            in.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
