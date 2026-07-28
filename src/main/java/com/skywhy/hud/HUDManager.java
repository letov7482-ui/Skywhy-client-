package com.skywhy.hud;

import com.skywhy.client.SkyWhyClient;
import com.skywhy.module.Module;
import com.skywhy.render.Render2D;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HUDManager {
    private boolean enabled = true;
    private int watermarkX = 4;
    private int watermarkY = 4;
    private int arraylistX = -100;
    private int arraylistY = 40;
    private boolean showFPS = true;
    private boolean showPing = true;
    private boolean showWatermark = true;
    private boolean showArraylist = true;

    public void render(MatrixStack matrices, float tickDelta) {
        if (!enabled) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        int screenWidth = mc.getWindow().getScaledWidth();

        // WATERMARK
        if (showWatermark) {
            String watermark = "SkyWhy Client";
            if (showFPS) watermark += " §7| §f" + mc.getCurrentFps() + " FPS";
            if (showPing) {
                int ping = 0;
                if (mc.getNetworkHandler() != null && mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()) != null) {
                    ping = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()).getLatency();
                }
                watermark += " §7| §f" + ping + "ms";
            }
            Render2D.drawString(matrices, watermark, watermarkX, watermarkY, 0xFFFFFF, 1.0f);
        }

        // ARRAYLIST
        if (showArraylist) {
            List<Module> modules = new ArrayList<>(SkyWhyClient.INSTANCE.moduleManager.getModules());
            modules.sort(Comparator.comparing(m -> -mc.textRenderer.getWidth(m.getName())));
            int y = arraylistY;
            int x = screenWidth - arraylistX - 20;
            for (Module m : modules) {
                if (m.isEnabled() && m.isVisible()) {
                    String name = m.getName();
                    int color = 0x00FFAA;
                    Render2D.drawString(matrices, name, x - mc.textRenderer.getWidth(name), y, color, 1.0f);
                    y += 12;
                }
            }
        }
    }

    // Геттеры и сеттеры для настроек
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setWatermarkPos(int x, int y) { this.watermarkX = x; this.watermarkY = y; }
    public void setArraylistPos(int x, int y) { this.arraylistX = x; this.arraylistY = y; }
    public void setShowFPS(boolean show) { this.showFPS = show; }
    public void setShowPing(boolean show) { this.showPing = show; }
    public void setShowWatermark(boolean show) { this.showWatermark = show; }
    public void setShowArraylist(boolean show) { this.showArraylist = show; }
}
