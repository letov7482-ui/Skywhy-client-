package com.skywhy.module.modules;
import com.skywhy.module.Module;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
public class Velocity extends Module {
    private float horizontalReduction = 0.8f;
    private float verticalReduction = 0.6f;
    public Velocity() { super("Velocity", Category.COMBAT); }
    @Override
    public void onTick() {
        if (mc.player == null) return;
        Vec3d vel = mc.player.getVelocity();
        mc.player.setVelocity(vel.x * horizontalReduction, vel.y * verticalReduction, vel.z * horizontalReduction);
    }
}
