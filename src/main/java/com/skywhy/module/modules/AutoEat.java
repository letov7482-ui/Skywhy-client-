package com.skywhy.module.modules;
import com.skywhy.module.Module;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
public class AutoEat extends Module {
    public AutoEat() { super("AutoEat", Category.PLAYER); }
    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.player.getHungerManager().getFoodLevel() < 10) {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = mc.player.getInventory().getStack(i);
                if (stack.getItem().getFoodComponent() != null) {
                    mc.player.getInventory().selectedSlot = i;
                    mc.options.useKey.setPressed(true);
                    break;
                }
            }
        } else {
            mc.options.useKey.setPressed(false);
        }
    }
}
