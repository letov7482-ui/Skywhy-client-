package com.skywhy.module.modules;
import com.skywhy.module.Module;
import net.minecraft.util.Hand;
public class AutoClicker extends Module {
    private int cps = 12;
    private long lastClick = 0;
    public AutoClicker() { super("AutoClicker", Category.MISC); }
    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (mc.options.attackKey.isPressed()) {
            long now = System.currentTimeMillis();
            if (now - lastClick > 1000 / cps) {
                mc.player.swingHand(Hand.MAIN_HAND);
                mc.interactionManager.attackEntity(mc.player, mc.targetedEntity);
                lastClick = now;
            }
        }
    }
}
