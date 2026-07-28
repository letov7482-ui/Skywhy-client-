package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class Timer extends Module {
    private float timerSpeed = 1.2f;
    public Timer() { super("Timer", Category.MISC); }
    @Override
    public void onTick() {
        if (mc.player != null) {
            mc.player.getAbilities().setFlySpeed(timerSpeed);
        }
    }
}
