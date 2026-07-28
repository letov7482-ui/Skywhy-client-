package com.skywhy.utils;

import java.awt.Color;

public class ColorUtils {
    public static int rainbow(int offset) {
        float hue = (System.currentTimeMillis() + offset) % 3600 / 3600f;
        return Color.HSBtoRGB(hue, 1f, 1f);
    }
    public static int blend(int c1, int c2, float ratio) {
        Color a = new Color(c1), b = new Color(c2);
        return new Color(
            (int)(a.getRed() + (b.getRed()-a.getRed())*ratio),
            (int)(a.getGreen() + (b.getGreen()-a.getGreen())*ratio),
            (int)(a.getBlue() + (b.getBlue()-a.getBlue())*ratio)
        ).getRGB();
    }
}
