package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class Step extends Module {
    private float stepHeight = 1.5f;
    public Step() { super("Step", Category.MOVEMENT); }
    @Override
    public void onTick() {
        if (mc.player != null) mc.player.setStepHeight(stepHeight);
    }
    @Override
    public void onDisable() { if (mc.player != null) mc.player.setStepHeight(0.6f); }
}
