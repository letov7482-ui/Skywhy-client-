package com.skywhy.module.modules;

import com.skywhy.module.Module;
import com.skywhy.utils.RotationUtils;
import com.skywhy.utils.PacketUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AutoCrystal extends Module {
    private float range = 5.0f;
    private float placeRange = 4.5f;
    private float breakRange = 5.0f;
    private int delay = 50; // ms between actions
    private long lastAction = 0;
    private boolean rotate = true;
    private boolean onlyOnGround = true;
    private boolean multiPlace = false;
    private Random random = new Random();

    public AutoCrystal() { super("AutoCrystal", Category.COMBAT); }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (onlyOnGround && !mc.player.isOnGround()) return;
        long now = System.currentTimeMillis();
        if (now - lastAction < delay + random.nextInt(20)) return;

        // 1) Find target player (closest)
        PlayerEntity target = findTarget();
        if (target == null) return;

        // 2) Find crystal to break
        EndCrystalEntity crystal = findCrystal(target);
        if (crystal != null) {
            // Break crystal with delay and random offset
            if (rotate) RotationUtils.smoothLookAt(crystal.getPos(), 150f, 150f);
            mc.interactionManager.attackEntity(mc.player, crystal);
            mc.player.swingHand(Hand.MAIN_HAND);
            lastAction = now + (long)(random.nextInt(30) - 15);
            return;
        }

        // 3) Place new crystal
        BlockPos placePos = findPlacePosition(target);
        if (placePos != null) {
            if (rotate) RotationUtils.smoothLookAt(Vec3d.ofCenter(placePos), 150f, 150f);
            // Switch to end crystal
            int slot = getCrystalSlot();
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

    private EndCrystalEntity findCrystal(PlayerEntity target) {
        return mc.world.getEntitiesByClass(EndCrystalEntity.class, 
                new Box(target.getPos().add(-range, -range, -range), target.getPos().add(range, range, range)))
                .stream()
                .filter(c -> c.distanceTo(mc.player) <= breakRange)
                .min(Comparator.comparingDouble(c -> c.distanceTo(target)))
                .orElse(null);
    }

    private BlockPos findPlacePosition(PlayerEntity target) {
        List<BlockPos> positions = new ArrayList<>();
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = -1; y <= 1; y++) {
                    BlockPos pos = target.getBlockPos().add(x, y, z);
                    if (mc.world.getBlockState(pos).isAir() && 
                        mc.world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN) {
                        if (pos.getSquaredDistance(mc.player.getPos()) <= placeRange * placeRange) {
                            positions.add(pos);
                        }
                    }
                }
            }
        }
        if (positions.isEmpty()) return null;
        return positions.stream()
                .min(Comparator.comparingDouble(p -> p.getSquaredDistance(target.getPos())))
                .orElse(null);
    }

    private int getCrystalSlot() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == Items.END_CRYSTAL) {
                return i;
            }
        }
        return -1;
    }
    }
