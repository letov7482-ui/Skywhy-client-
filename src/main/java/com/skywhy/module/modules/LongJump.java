package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class LongJump extends Module {
    public LongJump() { super("LongJump", Category.MOVEMENT); }
    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.player.isOnGround()) {
            mc.player.jump();
            mc.player.getVelocity().x *= 1.5;
            mc.player.getVelocity().z *= 1.5;
        }
    }
}
