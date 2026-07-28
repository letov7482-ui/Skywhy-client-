package com.skywhy.module.modules;
import com.skywhy.module.Module;
import net.minecraft.block.Blocks;
public class XRay extends Module {
    public XRay() { super("XRay", Category.RENDER); }
    @Override
    public void onTick() {
        if (mc.world != null) {
            mc.world.getBlockStates().forEach(pos -> {
                if (pos.getBlock() != Blocks.DIAMOND_ORE && pos.getBlock() != Blocks.IRON_ORE &&
                    pos.getBlock() != Blocks.GOLD_ORE && pos.getBlock() != Blocks.EMERALD_ORE &&
                    pos.getBlock() != Blocks.REDSTONE_ORE && pos.getBlock() != Blocks.LAPIS_ORE) {
                    // Скрываем все остальные блоки
                }
            });
        }
    }
}
