package com.skywhy.module.modules;
import com.skywhy.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;

public class Reach extends Module {
    private float reachDistance = 4.2f;
    public Reach() { super("Reach", Category.COMBAT); }
    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        mc.player.getAbilities().setReach((int)(reachDistance * 10));
        // Expand hitboxes for all entities
        for (Entity e : mc.world.getEntities()) {
            if (e != mc.player) {
                e.setBoundingBox(e.getBoundingBox().expand(0.2f));
            }
        }
    }
    public void setReach(float val) { this.reachDistance = Math.min(val, 6.0f); }
}
