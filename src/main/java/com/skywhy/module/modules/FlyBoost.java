package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class FlyBoost extends Module {
    private float boost = 2.0f;
    public FlyBoost() { super("FlyBoost", Category.MOVEMENT); }
    @Override
    public void onTick() {
        if (mc.player != null && mc.player.getAbilities().flying) {
            mc.player.getAbilities().setFlySpeed(0.1f * boost);
        }
    }
}
