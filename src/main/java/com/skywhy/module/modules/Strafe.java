package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class Strafe extends Module {
    private float strafeSpeed = 1.3f;
    public Strafe() { super("Strafe", Category.MOVEMENT); }
    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.player.isOnGround() && (mc.options.forwardKey.isPressed() || mc.options.leftKey.isPressed() || mc.options.rightKey.isPressed())) {
            mc.player.getVelocity().x *= strafeSpeed;
            mc.player.getVelocity().z *= strafeSpeed;
        }
    }
}
