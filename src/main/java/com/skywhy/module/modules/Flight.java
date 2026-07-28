package com.skywhy.module.modules;

import com.skywhy.module.Module;

public class Flight extends Module {
    private boolean glide = true;
    private float speed = 0.4f;
    private float verticalSpeed = 0.05f;
    private long lastJitter = 0;

    public Flight() { super("Flight", Category.MOVEMENT); }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        long now = System.currentTimeMillis();
        // Random vertical jitter every 500ms to bypass anti-fly
        if (now - lastJitter > 500 + (long)(Math.random() * 200)) {
            if (Math.random() > 0.7) {
                mc.player.getVelocity().y = -0.02; // small drop
            } else {
                mc.player.getVelocity().y = 0.02; // small rise
            }
            lastJitter = now;
        }
        // Horizontal movement
        double forward = mc.player.input.pressingForward ? 1 : 0;
        double strafe = mc.player.input.pressingRight ? 1 : mc.player.input.pressingLeft ? -1 : 0;
        if (forward != 0 || strafe != 0) {
            double yaw = Math.toRadians(mc.player.getYaw());
            double moveX = (forward * Math.sin(yaw) + strafe * Math.cos(yaw)) * speed;
            double moveZ = (forward * Math.cos(yaw) - strafe * Math.sin(yaw)) * speed;
            mc.player.getVelocity().x = moveX;
            mc.player.getVelocity().z = moveZ;
        }
        if (glide) {
            mc.player.getVelocity().y = -verticalSpeed;
        }
        mc.player.fallDistance = 0;
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.getVelocity().x = 0;
            mc.player.getVelocity().z = 0;
        }
    }
}
