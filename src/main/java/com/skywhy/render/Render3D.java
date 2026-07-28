package com.skywhy.render;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import net.minecraft.entity.player.PlayerEntity;

public class Render3D {
    public static void drawESPBox(double x, double y, double z, double w, double h, double l, int color, float alpha) {
        MatrixStack stack = new MatrixStack();
        Matrix4f matrix = stack.peek().getPositionMatrix();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer.getPositionColorProgram());
        float r = ((color>>16)&0xFF)/255f;
        float g = ((color>>8)&0xFF)/255f;
        float b = (color&0xFF)/255f;
        buf.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        Vec3d[] corners = {
            new Vec3d(x,y,z), new Vec3d(x+w,y,z), new Vec3d(x+w,y+h,z), new Vec3d(x,y+h,z),
            new Vec3d(x,y,z+l), new Vec3d(x+w,y,z+l), new Vec3d(x+w,y+h,z+l), new Vec3d(x,y+h,z+l)
        };
        int[] indices = {0,1,2,3, 0,4,5,1, 1,5,6,2, 2,6,7,3, 3,7,4,0, 4,5,6,7};
        for (int i : indices) {
            Vec3d p = corners[i];
            buf.vertex(matrix, (float)p.x, (float)p.y, (float)p.z).color(r,g,b,alpha).next();
        }
        tess.draw();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void drawESPOutline(double x, double y, double z, double w, double h, double l, int color, float thickness) {
        RenderSystem.lineWidth(thickness);
        MatrixStack stack = new MatrixStack();
        Matrix4f matrix = stack.peek().getPositionMatrix();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer.getPositionColorProgram());
        float r = ((color>>16)&0xFF)/255f;
        float g = ((color>>8)&0xFF)/255f;
        float b = (color&0xFF)/255f;
        buf.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        Vec3d[] verts = {
            new Vec3d(x,y,z), new Vec3d(x+w,y,z),
            new Vec3d(x+w,y,z), new Vec3d(x+w,y+h,z),
            new Vec3d(x+w,y+h,z), new Vec3d(x,y+h,z),
            new Vec3d(x,y+h,z), new Vec3d(x,y,z),
            new Vec3d(x,y,z), new Vec3d(x,y,z+l),
            new Vec3d(x+w,y,z+l), new Vec3d(x+w,y+h,z+l),
            new Vec3d(x,y+h,z+l), new Vec3d(x,y,z+l),
            new Vec3d(x,y,z), new Vec3d(x+w,y,z),
            new Vec3d(x+w,y,z), new Vec3d(x+w,y,z+l),
            new Vec3d(x+w,y,z+l), new Vec3d(x+w,y+h,z+l),
            new Vec3d(x+w,y+h,z+l), new Vec3d(x+w,y+h,z),
            new Vec3d(x+w,y+h,z), new Vec3d(x,y+h,z),
            new Vec3d(x,y+h,z), new Vec3d(x,y+h,z+l)
        };
        for (int i=0; i<verts.length; i+=2) {
            Vec3d p1=verts[i], p2=verts[i+1];
            buf.vertex(matrix, (float)p1.x, (float)p1.y, (float)p1.z).color(r,g,b,1f).next();
            buf.vertex(matrix, (float)p2.x, (float)p2.y, (float)p2.z).color(r,g,b,1f).next();
        }
        tess.draw();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.lineWidth(1f);
    }

    public static void drawLine3D(Vec3d start, Vec3d end, int color) {
        MatrixStack stack = new MatrixStack();
        Matrix4f matrix = stack.peek().getPositionMatrix();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer.getPositionColorProgram());
        buf.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        float r = ((color>>16)&0xFF)/255f;
        float g = ((color>>8)&0xFF)/255f;
        float b = (color&0xFF)/255f;
        float a = ((color>>24)&0xFF)/255f;
        if (a < 0.01f) a = 1.0f;
        buf.vertex(matrix, (float)start.x, (float)start.y, (float)start.z).color(r,g,b,a).next();
        buf.vertex(matrix, (float)end.x, (float)end.y, (float)end.z).color(r,g,b,a).next();
        tess.draw();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void drawTracer(double x1, double y1, double z1, double x2, double y2, double z2, int color) {
        drawLine3D(new Vec3d(x1,y1,z1), new Vec3d(x2,y2,z2), color);
    }
}
