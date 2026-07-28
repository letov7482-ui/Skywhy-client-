package com.skywhy.cosmetics;

import com.skywhy.module.Module;
import com.skywhy.render.Render3D;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CosmeticManager extends Module {
    public enum CosmeticType { CAPE, WINGS, TRAIL, KILL_EFFECT, HALO, AURA }
    private CosmeticType activeCosmetic = CosmeticType.CAPE;
    private int color = 0x00AAFF;
    private boolean enabled = true;
    private List<Vec3d> trailPositions = new ArrayList<>();
    private Random random = new Random();

    public CosmeticManager() { super("Cosmetics", Category.VISUAL); }

    @Override
    public void onTick() {
        if (!enabled || mc.player == null) return;
        // Trail
        if (activeCosmetic == CosmeticType.TRAIL) {
            trailPositions.add(mc.player.getPos());
            if (trailPositions.size() > 50) trailPositions.remove(0);
        }
        // Kill effect - обрабатывается отдельно
    }

    @Override
    public void onRender3D() {
        if (!enabled || mc.player == null) return;
        switch (activeCosmetic) {
            case CAPE:
                renderCape();
                break;
            case WINGS:
                renderWings();
                break;
            case TRAIL:
                renderTrail();
                break;
            case HALO:
                renderHalo();
                break;
            case AURA:
                renderAura();
                break;
        }
    }

    private void renderCape() {
        // Простая накидка за спиной (прямоугольник)
        Vec3d pos = mc.player.getPos();
        Render3D.drawESPBox(pos.x - 0.5, pos.y + 1.2, pos.z - 0.3, 1.0, 0.8, 0.1, color, 0.8f);
    }

    private void renderWings() {
        // Крылья в виде двух треугольников
        Vec3d pos = mc.player.getPos();
        // Левое крыло
        Render3D.drawTriangle(pos.x - 0.5, pos.y + 1.2, pos.z, pos.x - 1.0, pos.y + 1.8, pos.z - 0.5, pos.x - 1.0, pos.y + 0.6, pos.z - 0.5, color);
        // Правое крыло
        Render3D.drawTriangle(pos.x + 0.5, pos.y + 1.2, pos.z, pos.x + 1.0, pos.y + 1.8, pos.z - 0.5, pos.x + 1.0, pos.y + 0.6, pos.z - 0.5, color);
    }

    private void renderTrail() {
        for (int i = 0; i < trailPositions.size() - 1; i++) {
            Vec3d p1 = trailPositions.get(i);
            Vec3d p2 = trailPositions.get(i+1);
            float alpha = i / (float)trailPositions.size();
            int trailColor = ColorUtils.blend(color, 0x00FFFF, alpha);
            Render3D.drawLine3D(p1, p2, trailColor);
        }
    }

    private void renderHalo() {
        // Круг над головой
        Vec3d pos = mc.player.getPos().add(0, 2.0, 0);
        for (int i = 0; i < 36; i++) {
            double angle = Math.PI * 2 * i / 36;
            double angle2 = Math.PI * 2 * (i+1) / 36;
            Vec3d p1 = new Vec3d(pos.x + Math.cos(angle) * 0.5, pos.y, pos.z + Math.sin(angle) * 0.5);
            Vec3d p2 = new Vec3d(pos.x + Math.cos(angle2) * 0.5, pos.y, pos.z + Math.sin(angle2) * 0.5);
            Render3D.drawLine3D(p1, p2, color);
        }
    }

    private void renderAura() {
        // Светящаяся аура вокруг игрока
        Vec3d pos = mc.player.getPos();
        float time = (System.currentTimeMillis() % 2000) / 2000f;
        float radius = 1.5f + (float)Math.sin(time * Math.PI * 2) * 0.3f;
        for (int i = 0; i < 36; i++) {
            double angle = Math.PI * 2 * i / 36;
            double angle2 = Math.PI * 2 * (i+1) / 36;
            Vec3d p1 = new Vec3d(pos.x + Math.cos(angle) * radius, pos.y + 0.5, pos.z + Math.sin(angle) * radius);
            Vec3d p2 = new Vec3d(pos.x + Math.cos(angle2) * radius, pos.y + 0.5, pos.z + Math.sin(angle2) * radius);
            int glowColor = ColorUtils.rainbow((int)(i * 50));
            Render3D.drawLine3D(p1, p2, glowColor);
        }
    }

    public void setCosmetic(CosmeticType type) { this.activeCosmetic = type; }
    public void setColor(int color) { this.color = color; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
              }
