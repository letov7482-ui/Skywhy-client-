package com.skywhy.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import java.util.Random;

public class AntiBanSystem {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static Random random = new Random();
    private static long lastJitter = 0;

    // Вызывать каждый тик
    public static void bypass() {
        if (mc.player == null) return;
        long now = System.currentTimeMillis();
        // 1) Random jitter in movement
        if (now - lastJitter > 200 + random.nextInt(300)) {
            double offsetX = (random.nextDouble() - 0.5) * 0.001;
            double offsetZ = (random.nextDouble() - 0.5) * 0.001;
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                mc.player.getX() + offsetX,
                mc.player.getY(),
                mc.player.getZ() + offsetZ,
                mc.player.isOnGround()
            ));
            lastJitter = now;
        }
        // 2) Random sneak toggles
        if (random.nextInt(100) > 98) {
            mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, 
                ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
        }
        if (random.nextInt(100) > 99) {
            mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, 
                ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
        }
        // 3) Random look jitter (silent)
        if (random.nextInt(100) > 95) {
            float yaw = mc.player.getYaw() + (float)(random.nextDouble() - 0.5) * 0.5f;
            float pitch = mc.player.getPitch() + (float)(random.nextDouble() - 0.5) * 0.5f;
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, mc.player.isOnGround()));
        }
    }
}
