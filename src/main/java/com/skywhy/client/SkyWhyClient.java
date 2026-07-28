package com.skywhy.client;

import com.skywhy.module.ModuleManager;
import com.skywhy.config.ConfigManager;
import com.skywhy.hud.HUDManager;
import com.skywhy.gui.ClickGUI;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SkyWhyClient implements ClientModInitializer {
    public static final String MOD_ID = "skywhy";
    public static final String MOD_NAME = "SkyWhy Client";
    public static SkyWhyClient INSTANCE;
    public ModuleManager moduleManager;
    public ConfigManager configManager;
    public HUDManager hudManager;
    private KeyBinding clickGuiKey;
    private static boolean initialized = false;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();
        hudManager = new HUDManager();

        clickGuiKey = new KeyBinding(
            "key.skywhy.clickgui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RSHIFT,
            "SkyWhy Client"
        );

        MinecraftClient client = MinecraftClient.getInstance();
        client.execute(() -> {
            if (!initialized) {
                initialized = true;
                System.out.println("[SkyWhy] Client initialized successfully!");
            }
        });

        configManager.load();
    }

    public static void toggleClickGUI() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen instanceof ClickGUI) {
            client.setScreen(null);
        } else {
            client.setScreen(new ClickGUI());
        }
    }

    public static void onTick() {
        if (INSTANCE != null && INSTANCE.moduleManager != null) {
            INSTANCE.moduleManager.onTick();
        }
    }
}
