package com.skywhy.module;

import net.minecraft.client.MinecraftClient;

public abstract class Module {
    protected String name;
    protected int key;
    protected Category category;
    protected boolean enabled;
    protected boolean visible = true;
    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
        this.key = 0;
        this.enabled = false;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) onEnable();
            else onDisable();
        }
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
    public void onRender3D() {}
    public void onRender2D() {}

    public String getName() { return name; }
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }
    public Category getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }

    public enum Category {
        COMBAT, MOVEMENT, PLAYER, VISUAL, RENDER, MISC, HUD
    }
}
