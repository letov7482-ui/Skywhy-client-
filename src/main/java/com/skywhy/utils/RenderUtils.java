package com.skywhy.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.Vec3d;

public class RenderUtils {
    public static void drawLine3D(Vec3d start, Vec3d end, int color) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        buf.begin(3, VertexFormats.POSITION_COLOR);
        float r = ((color>>16)&0xFF)/255f;
        float g = ((color>>8)&0xFF)/255f;
        float b = (color&0xFF)/255f;
        buf.vertex(start.x, start.y, start.z).color(r,g,b,1f).next();
        buf.vertex(end.x, end.y, end.z).color(r,g,b,0f).next();
        tess.draw();
    }

    public static void drawGradientBox(Vec3d min, Vec3d max, int colorStart, int colorEnd) {
        // Градиентный бокс для ESP
    }
}
