package com.skywhy.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static float currentYaw, currentPitch;
    private static float targetYaw, targetPitch;
    private static long lastUpdate = 0;

    public static void lookAt(Vec3d target) {
        if (mc.player == null) return;
        double dx = target.x - mc.player.getEyePos().x;
        double dy = target.y - mc.player.getEyePos().y;
        double dz = target.z - mc.player.getEyePos().z;
        float yaw = (float) (Math.atan2(dz, dx) * 180 / Math.PI) - 90;
        float pitch = (float) (-Math.atan2(dy, Math.sqrt(dx*dx + dz*dz)) * 180 / Math.PI);
        mc.player.setYaw(yaw);
        mc.player.setPitch(pitch);
    }

    public static void smoothLookAt(Vec3d target, float speedYaw, float speedPitch) {
        if (mc.player == null) return;
        targetYaw = (float) (Math.atan2(target.z - mc.player.getZ(), target.x - mc.player.getX()) * 180 / Math.PI) - 90;
        targetPitch = (float) (-Math.atan2(target.y - mc.player.getEyeY(), Math.hypot(target.x - mc.player.getX(), target.z - mc.player.getZ())) * 180 / Math.PI);
        currentYaw = mc.player.getYaw();
        currentPitch = mc.player.getPitch();
        // Smooth interpolation with random jitter
        float yawDiff = MathHelper.wrapDegrees(targetYaw - currentYaw);
        float pitchDiff = MathHelper.wrapDegrees(targetPitch - currentPitch);
        float randomJitter = (float)(Math.random() * 0.5 - 0.25);
        mc.player.setYaw(currentYaw + yawDiff / speedYaw + randomJitter);
        mc.player.setPitch(currentPitch + pitchDiff / speedPitch + randomJitter * 0.5f);
    }

    public static void silentLookAt(Vec3d target) {
        // Server-side only rotation (not visually)
        if (mc.player == null) return;
        double dx = target.x - mc.player.getEyePos().x;
        double dy = target.y - mc.player.getEyePos().y;
        double dz = target.z - mc.player.getEyePos().z;
        float yaw = (float) (Math.atan2(dz, dx) * 180 / Math.PI) - 90;
        float pitch = (float) (-Math.atan2(dy, Math.sqrt(dx*dx + dz*dz)) * 180 / Math.PI);
        mc.player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, mc.player.isOnGround()));
    }
}
