package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class AntiBlind extends Module {
    public AntiBlind() { super("AntiBlind", Category.PLAYER); }
    @Override
    public void onTick() {
        if (mc.player != null) {
            mc.player.removeStatusEffect(net.minecraft.entity.effect.StatusEffects.BLINDNESS);
        }
    }
}
