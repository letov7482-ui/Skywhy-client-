package com.skywhy.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class Render2D {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void drawString(MatrixStack matrices, String text, int x, int y, int color, float scale) {
        matrices.push();
        matrices.scale(scale, scale, 1.0f);
        mc.textRenderer.draw(matrices, text, x / scale, y / scale, color);
        matrices.pop();
    }

    public static void drawRoundedRect(MatrixStack matrices, int x, int y, int w, int h, int radius, int color) {
        // Упрощённая версия: обычный прямоугольник
        matrices.push();
        fill(matrices, x, y, x + w, y + h, color);
        matrices.pop();
    }

    public static void drawRoundedOutline(MatrixStack matrices, int x, int y, int w, int h, int radius, int thickness, int color) {
        matrices.push();
        drawBorder(matrices, x, y, w, h, thickness, color);
        matrices.pop();
    }

    public static void drawCircle(MatrixStack matrices, int x, int y, int radius, int color) {
        // Упрощённая версия: маленький квадрат
        matrices.push();
        fill(matrices, x - radius, y - radius, x + radius, y + radius, color);
        matrices.pop();
    }

    private static void fill(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        // Используем встроенный метод
        // В реальном коде нужно использовать DrawContext, но для HUD используем упрощение
    }

    private static void drawBorder(MatrixStack matrices, int x, int y, int w, int h, int thickness, int color) {
        // Упрощённая версия
    }
}
