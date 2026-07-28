package com.skywhy.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import java.awt.Color;

public class Render2D {
    public static void drawRoundedRect(DrawContext context, int x, int y, int w, int h, int radius, int color) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        // Simple fallback: draw normal rect for compatibility
        context.fill(x, y, x+w, y+h, color);
        matrices.pop();
    }

    public static void drawRoundedOutline(DrawContext context, int x, int y, int w, int h, int radius, int thickness, int color) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        context.drawBorder(x, y, w, h, color);
        matrices.pop();
    }

    public static void drawString(DrawContext context, String text, int x, int y, int color, float scale) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.scale(scale, scale, 1.0f);
        context.drawText(context.getTextRenderer(), text, (int)(x/scale), (int)(y/scale), color, false);
        matrices.pop();
    }

    public static void drawCircle(DrawContext context, int x, int y, int radius, int color) {
        // Fallback: draw a filled circle using pixel approximation
        int r = new Color(color).getRed();
        int g = new Color(color).getGreen();
        int b = new Color(color).getBlue();
        int a = new Color(color).getAlpha();
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (i*i + j*j <= radius*radius) {
                    context.fill(x+i, y+j, x+i+1, y+j+1, new Color(r,g,b,a).getRGB());
                }
            }
        }
    }
}
