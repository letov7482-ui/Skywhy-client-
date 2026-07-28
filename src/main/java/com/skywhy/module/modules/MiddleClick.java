package com.skywhy.module.modules;
import com.skywhy.module.Module;
import net.minecraft.util.hit.EntityHitResult;
public class MiddleClick extends Module {
    public MiddleClick() { super("MiddleClick", Category.MISC); }
    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.options.pickItemKey.isPressed()) {
            if (mc.crosshairTarget instanceof EntityHitResult) {
                // Perform action on entity
            }
        }
    }
}
