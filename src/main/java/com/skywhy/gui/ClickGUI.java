package com.skywhy.gui;

import com.skywhy.client.SkyWhyClient;
import com.skywhy.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGUI extends Screen {
    public ClickGUI() {
        super(Text.literal("SkyWhy Client"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x88000000);
        context.drawCenteredTextWithShadow(textRenderer, "SkyWhy Client", width/2, 20, 0x00AAFF);
        int y = 60;
        for (Module m : SkyWhyClient.INSTANCE.moduleManager.getModules()) {
            if (m.isEnabled()) {
                context.drawText(textRenderer, "§a" + m.getName(), 20, y, 0xFFFFFF, false);
            } else {
                context.drawText(textRenderer, "§7" + m.getName(), 20, y, 0xFFFFFF, false);
            }
            y += 16;
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int y = 60;
        for (Module m : SkyWhyClient.INSTANCE.moduleManager.getModules()) {
            if (mouseX > 20 && mouseX < 200 && mouseY > y && mouseY < y + 12) {
                m.toggle();
                SkyWhyClient.INSTANCE.configManager.save();
                return true;
            }
            y += 16;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }
    @Override
    public boolean shouldCloseOnEsc() { return true; }
}
