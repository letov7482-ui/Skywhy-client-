package com.skywhy.render;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Render3D {
    public static void drawESPBox(double x, double y, double z, double w, double h, double l, int color, int glowColor) {
        MatrixStack stack = new MatrixStack();
        Matrix4f matrix = stack.peek().getPositionMatrix();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer.getPositionColorProgram());
        // Outline
        buf.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        drawBoxLines(buf, matrix, x, y, z, w, h, l, color);
        tess.draw();
        // Filled
        buf.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        drawFilledBox(buf, matrix, x, y, z, w, h, l, glowColor);
        tess.draw();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    private static void drawBoxLines(BufferBuilder buf, Matrix4f mat, double x, double y, double z, double w, double h, double l, int color) {
        float r = ((color>>16)&0xFF)/255f;
        float g = ((color>>8)&0xFF)/255f;
        float b = (color&0xFF)/255f;
        float a = 1f;
        Vec3d[] vertices = {
            new Vec3d(x,y,z), new Vec3d(x+w,y,z), new Vec3d(x+w,y,z), new Vec3d(x+w,y+h,z),
            new Vec3d(x+w,y+h,z), new Vec3d(x,y+h,z), new Vec3d(x,y+h,z), new Vec3d(x,y,z),
            new Vec3d(x,y,z), new Vec3d(x,y,z+l), new Vec3d(x+w,y,z+l), new Vec3d(x+w,y+h,z+l),
            new Vec3d(x,y+h,z+l), new Vec3d(x,y,z+l)
        };
        for (int i=0; i<vertices.length; i+=2) {
            Vec3d p1 = vertices[i], p2 = vertices[i+1];
            buf.vertex(mat, (float)p1.x, (float)p1.y, (float)p1.z).color(r,g,b,a).next();
            buf.vertex(mat, (float)p2.x, (float)p2.y, (float)p2.z).color(r,g,b,a).next();
        }
    }

    private static void drawFilledBox(BufferBuilder buf, Matrix4f mat, double x, double y, double z, double w, double h, double l, int color) {
        float r = ((color>>16)&0xFF)/255f;
        float g = ((color>>8)&0xFF)/255f;
        float b = (color&0xFF)/255f;
        float a = 0.3f;
        Vec3d[] corners = {
            new Vec3d(x,y,z), new Vec3d(x+w,y,z), new Vec3d(x+w,y+h,z), new Vec3d(x,y+h,z),
            new Vec3d(x,y,z+l), new Vec3d(x+w,y,z+l), new Vec3d(x+w,y+h,z+l), new Vec3d(x,y+h,z+l)
        };
        int[] indices = {0,1,2,3, 4,5,6,7, 0,4,5,1, 1,5,6,2, 2,6,7,3, 3,7,4,0};
        for (int i=0; i<indices.length-1; i+=2) {
            Vec3d p1 = corners[indices[i]], p2 = corners[indices[i+1]];
            buf.vertex(mat, (float)p1.x, (float)p1.y, (float)p1.z).color(r,g,b,a).next();
            buf.vertex(mat, (float)p2.x, (float)p2.y, (float)p2.z).color(r,g,b,a).next();
        }
    }
              }
