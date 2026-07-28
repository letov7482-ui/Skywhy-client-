package com.skywhy.module.modules;

import com.skywhy.module.Module;
import com.skywhy.utils.RotationUtils;
import com.skywhy.utils.PacketUtils;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;
import java.util.Comparator;
import java.util.Random;

public class SafeAnchor extends Module {
    private float range = 4.0f;
    private int delay = 300;
    private long lastAction = 0;
    private boolean rotate = true;
    private float safeDistance = 4.0f; // Distance from player to avoid self-damage
    private Random random = new Random();

    public SafeAnchor() { super("SafeAnchor", Category.COMBAT); }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        long now = System.currentTimeMillis();
        if (now - lastAction < delay + random.nextInt(40)) return;

        // Check if any anchor near player (self-protection)
        BlockPos dangerAnchor = findDangerAnchor();
        if (dangerAnchor != null) {
            // Break or deactivate it
            if (rotate) RotationUtils.smoothLookAt(Vec3d.ofCenter(dangerAnchor), 100f, 100f);
            mc.interactionManager.attackBlock(dangerAnchor, Hand.MAIN_HAND);
            mc.player.swingHand(Hand.MAIN_HAND);
            lastAction = now + (long)(random.nextInt(50) - 25);
            return;
        }

        // Place anchor at safe distance from self, but near target
        PlayerEntity target = findTarget();
        if (target == null) return;

        BlockPos placePos = findSafePlace(target);
        if (placePos != null) {
            if (rotate) RotationUtils.smoothLookAt(Vec3d.ofCenter(placePos), 120f, 120f);
            int slot = getAnchorSlot();
            if (slot != -1) {
                mc.player.getInventory().selectedSlot = slot;
                PacketUtils.placeBlock(placePos, Hand.MAIN_HAND);
                mc.player.swingHand(Hand.MAIN_HAND);
                lastAction = now + (long)(random.nextInt(40) - 20);
            }
        }
    }

    private PlayerEntity findTarget() {
        return mc.world.getPlayers().stream()
                .filter(p -> p != mc.player && p.distanceTo(mc.player) <= range)
                .min(Comparator.comparingDouble(p -> p.distanceTo(mc.player)))
                .orElse(null);
    }

    private BlockPos findDangerAnchor() {
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                for (int y = -2; y <= 2; y++) {
                    BlockPos pos = mc.player.getBlockPos().add(x, y, z);
                    if (mc.world.getBlockState(pos).getBlock() == Blocks.RESPAWN_ANCHOR) {
                        int charge = mc.world.getBlockState(pos).get(RespawnAnchorBlock.CHARGES);
                        if (charge > 0 && pos.getSquaredDistance(mc.player.getPos()) < safeDistance * safeDistance) {
                            return pos;
                        }
                    }
                }
            }
        }
        return null;
    }

    private BlockPos findSafePlace(PlayerEntity target) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos pos = target.getBlockPos().add(x, 0, z);
                if (mc.world.getBlockState(pos).isAir() && 
                    mc.world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN) {
                    double distToPlayer = pos.getSquaredDistance(mc.player.getPos());
                    double distToTarget = pos.getSquaredDistance(target.getPos());
                    if (distToPlayer > safeDistance * safeDistance && distToTarget < range * range) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    private int getAnchorSlot() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == Items.RESPAWN_ANCHOR) {
                return i;
            }
        }
        return -1;
    }
                      }
