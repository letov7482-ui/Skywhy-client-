package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class NoSlow extends Module {
    public NoSlow() { super("NoSlow", Category.MOVEMENT); }
    @Override
    public void onTick() {
        if (mc.player != null && mc.player.isUsingItem()) {
            mc.player.getVelocity().x *= 1.2;
            mc.player.getVelocity().z *= 1.2;
        }
    }
}
