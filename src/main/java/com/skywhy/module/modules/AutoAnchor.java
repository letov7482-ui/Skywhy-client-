package com.skywhy.module.modules;

import com.skywhy.module.Module;
import com.skywhy.utils.RotationUtils;
import com.skywhy.utils.PacketUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.RespawnAnchorBlock;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AutoAnchor extends Module {
    private float range = 4.5f;
    private int delay = 200;
    private long lastAction = 0;
    private boolean rotate = true;
    private Random random = new Random();

    public AutoAnchor() { super("AutoAnchor", Category.COMBAT); }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        long now = System.currentTimeMillis();
        if (now - lastAction < delay + random.nextInt(50)) return;

        PlayerEntity target = findTarget();
        if (target == null) return;

        // Find placed anchor near target
        BlockPos anchorPos = findAnchor(target);
        if (anchorPos != null) {
            // Interact with anchor (explode)
            if (rotate) RotationUtils.smoothLookAt(Vec3d.ofCenter(anchorPos), 120f, 120f);
            PacketUtils.interactBlock(anchorPos, Hand.MAIN_HAND);
            mc.player.swingHand(Hand.MAIN_HAND);
            lastAction = now + (long)(random.nextInt(60) - 30);
            return;
        }

        // Place anchor
        BlockPos placePos = findPlacePosition(target);
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

    private BlockPos findAnchor(PlayerEntity target) {
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                for (int y = -2; y <= 2; y++) {
                    BlockPos pos = target.getBlockPos().add(x, y, z);
                    if (mc.world.getBlockState(pos).getBlock() == Blocks.RESPAWN_ANCHOR) {
                        int charge = mc.world.getBlockState(pos).get(RespawnAnchorBlock.CHARGES);
                        if (charge > 0 && pos.getSquaredDistance(mc.player.getPos()) <= range * range) {
                            return pos;
                        }
                    }
                }
            }
        }
        return null;
    }

    private BlockPos findPlacePosition(PlayerEntity target) {
        List<BlockPos> positions = new ArrayList<>();
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos pos = target.getBlockPos().add(x, 0, z);
                if (mc.world.getBlockState(pos).isAir() && 
                    mc.world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN) {
                    if (pos.getSquaredDistance(mc.player.getPos()) <= range * range) {
                        positions.add(pos);
                    }
                }
            }
        }
        if (positions.isEmpty()) return null;
        return positions.stream()
                .min(Comparator.comparingDouble(p -> p.getSquaredDistance(target.getPos())))
                .orElse(null);
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
