package com.skywhy.hud;

import com.skywhy.client.SkyWhyClient;
import com.skywhy.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class HUDManager {
    private boolean enabled = true;
    private boolean showWatermark = true;
    private boolean showArraylist = true;

    public void render(DrawContext context, float tickDelta) {
        if (!enabled) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.textRenderer == null) return;

        if (showWatermark) {
            String text = "SkyWhy Client";
            context.drawText(mc.textRenderer, text, 4, 4, 0x00AAFF, false);
            if (mc.getCurrentFps() > 0) {
                String fps = "FPS: " + mc.getCurrentFps();
                context.drawText(mc.textRenderer, fps, 4, 16, 0xAAAAAA, false);
            }
        }

        if (showArraylist) {
            int y = 40;
            for (Module m : SkyWhyClient.INSTANCE.moduleManager.getModules()) {
                if (m.isEnabled() && m.isVisible()) {
                    String name = m.getName();
                    int x = mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(name) - 10;
                    context.drawText(mc.textRenderer, name, x, y, 0x00FFAA, false);
                    y += 12;
                }
            }
        }
    }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setShowWatermark(boolean show) { this.showWatermark = show; }
    public void setShowArraylist(boolean show) { this.showArraylist = show; }
}
