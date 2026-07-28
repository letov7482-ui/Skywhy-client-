package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class Regen extends Module {
    public Regen() { super("Regen", Category.PLAYER); }
    @Override
    public void onTick() {
        if (mc.player != null && mc.player.getHealth() < 20) {
            mc.player.heal(0.5f);
        }
    }
}
