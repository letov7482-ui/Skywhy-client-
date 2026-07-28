package com.skywhy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import com.skywhy.module.ModuleManager;
import com.skywhy.config.ConfigManager;
import com.skywhy.hud.HUDManager;

public class SkyWhyClient implements ClientModInitializer {
    public static final String MOD_ID = "skywhy";
    public static final String MOD_NAME = "SkyWhy Client";
    public static SkyWhyClient INSTANCE;
    public ModuleManager moduleManager;
    public ConfigManager configManager;
    public HUDManager hudManager;
    private KeyBinding clickGuiKey;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();
        hudManager = new HUDManager();
        clickGuiKey = new KeyBinding("key.skywhy.clickgui", GLFW.GLFW_KEY_RSHIFT, "SkyWhy Client");
        KeyBindingHelper.registerKeyBinding(clickGuiKey);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKey.wasPressed()) {
                // Open ClickGUI
                // client.setScreen(new ClickGUIScreen());
            }
            moduleManager.onTick();
        });
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> hudManager.render(matrixStack, tickDelta));
        configManager.load();
    }
}
