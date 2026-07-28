package com.skywhy.module.modules;

import com.skywhy.module.Module;
import com.skywhy.utils.RotationUtils;
import com.skywhy.utils.PacketUtils;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import java.util.Comparator;

public class HitCrystal extends Module {
    private float range = 6.0f;
    private int delay = 100;
    private long lastHit = 0;
    private boolean silentRotate = true;

    public HitCrystal() { super("HitCrystal", Category.COMBAT); }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        long now = System.currentTimeMillis();
        if (now - lastHit < delay) return;

        EndCrystalEntity crystal = findNearestCrystal();
        if (crystal == null) return;

        if (silentRotate) {
            RotationUtils.silentLookAt(crystal.getPos());
        } else {
            RotationUtils.lookAt(crystal.getPos());
        }
        mc.interactionManager.attackEntity(mc.player, crystal);
        mc.player.swingHand(Hand.MAIN_HAND);
        lastHit = now + (long)(Math.random() * 30);
    }

    private EndCrystalEntity findNearestCrystal() {
        return mc.world.getEntitiesByClass(EndCrystalEntity.class, 
                mc.player.getBoundingBox().expand(range, range, range))
                .stream()
                .min(Comparator.comparingDouble(c -> c.distanceTo(mc.player)))
                .orElse(null);
    }
}
