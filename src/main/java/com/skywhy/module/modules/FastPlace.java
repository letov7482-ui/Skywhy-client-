package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class FastPlace extends Module {
    public FastPlace() { super("FastPlace", Category.PLAYER); }
    @Override
    public void onTick() {
        if (mc.player != null) mc.player.getItemUseCooldown();
        // Убираем задержку на постановку блоков
    }
}
