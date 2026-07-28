package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class DiscordRPC extends Module {
    public DiscordRPC() { super("DiscordRPC", Category.MISC); }
    @Override
    public void onEnable() {
        // Инициализация Discord Rich Presence (требуется библиотека)
    }
    @Override
    public void onDisable() {
        // Отключение RPC
    }
}
