package com.skywhy.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PacketUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void placeBlock(BlockPos pos, Hand hand) {
        if (mc.player == null || mc.interactionManager == null) return;
        // Отправляем пакет с задержкой и случайным смещением
        Vec3d hitVec = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, pos));
        mc.player.networkHandler.sendPacket(new HandSwingC2SPacket(hand));
        // Дополнительный пакет движения для байпасса
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
            mc.player.getX() + (Math.random() - 0.5) * 0.01,
            mc.player.getY(),
            mc.player.getZ() + (Math.random() - 0.5) * 0.01,
            mc.player.isOnGround()
        ));
    }

    public static void interactBlock(BlockPos pos, Hand hand) {
        if (mc.player == null) return;
        mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, pos));
        mc.player.networkHandler.sendPacket(new HandSwingC2SPacket(hand));
    }

    public static void sendSilentMove(double x, double y, double z, boolean onGround) {
        if (mc.player == null) return;
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, onGround));
    }
}
