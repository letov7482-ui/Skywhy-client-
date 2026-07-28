package com.skywhy.client;

import com.skywhy.module.ModuleManager;
import com.skywhy.config.ConfigManager;
import com.skywhy.hud.HUDManager;
import com.skywhy.gui.ClickGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

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
                if (client.currentScreen instanceof ClickGUI) {
                    client.setScreen(null);
                } else {
                    client.setScreen(new ClickGUI());
                }
            }
            moduleManager.onTick();
        });

        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> hudManager.render(matrixStack, tickDelta));
        configManager.load();

        System.out.println("[SkyWhy] Client initialized successfully!");
    }
}
