package com.skywhy.module.modules;

import com.skywhy.module.Module;
import com.skywhy.utils.RotationUtils;
import com.skywhy.utils.HitBoxUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import java.util.Comparator;
import java.util.List;

public class KillAura extends Module {
    private long lastSwing = 0;
    private int delay = 150; // 150ms for smooth, bypass
    private float range = 4.2f; // under 4.5 for vanilla-like
    private boolean throughWalls = false;
    private boolean rotateSmooth = true;
    private float hitboxMultiplier = 0.3f; // expand hitbox by 0.3 blocks

    public KillAura() { super("KillAura", Category.COMBAT); }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        Entity target = findTarget();
        if (target == null) return;
        long now = System.currentTimeMillis();
        if (now - lastSwing < delay) return;

        // Expand hitbox for better reach without detection
        double expandedRange = range + hitboxMultiplier;
        Vec3d targetPos = target.getPos().add(0, target.getHeight()/2, 0);
        Vec3d playerPos = mc.player.getEyePos();
        double dist = playerPos.distanceTo(targetPos);
        if (dist > expandedRange + 1.0) return;

        // Smooth rotation
        if (rotateSmooth) {
            RotationUtils.smoothLookAt(targetPos, 120f, 120f);
        } else {
            RotationUtils.lookAt(targetPos);
        }

        // Attack with delay and random offset to mimic human
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        lastSwing = now + (long)(Math.random() * 20 - 10); // randomize timing
    }

    private Entity findTarget() {
        if (mc.world == null) return null;
        List<Entity> targets = mc.world.getEntities()
                .stream()
                .filter(e -> e instanceof PlayerEntity && e != mc.player)
                .filter(e -> e.distanceTo(mc.player) < range + hitboxMultiplier + 2.0)
                .sorted(Comparator.comparingDouble(e -> e.distanceTo(mc.player)))
                .toList();
        return targets.isEmpty() ? null : targets.get(0);
    }

    // Settings
    public void setHitboxMultiplier(float val) { this.hitboxMultiplier = val; }
    public void setRange(float val) { this.range = Math.min(val, 4.5f); }
    public void setDelay(int val) { this.delay = Math.max(val, 50); }
    public void setThroughWalls(boolean val) { this.throughWalls = val; }
    public void setRotateSmooth(boolean val) { this.rotateSmooth = val; }
}
