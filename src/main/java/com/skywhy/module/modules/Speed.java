package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class Speed extends Module {
    private float speed = 1.2f;
    public Speed() { super("Speed", Category.MOVEMENT); }
    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.player.isOnGround()) {
            mc.player.getVelocity().x *= speed;
            mc.player.getVelocity().z *= speed;
        }
    }
}
