package com.skywhy.module.modules;
import com.skywhy.module.Module;
import com.skywhy.render.Render3D;
import net.minecraft.block.entity.ChestBlockEntity;
public class ChestESP extends Module {
    public ChestESP() { super("ChestESP", Category.VISUAL); }
    @Override
    public void onRender3D() {
        if (mc.world == null) return;
        for (ChestBlockEntity chest : mc.world.getBlockEntities(ChestBlockEntity.class)) {
            double x = chest.getPos().getX(), y = chest.getPos().getY(), z = chest.getPos().getZ();
            Render3D.drawESPBox(x-0.5, y-0.5, z-0.5, 1, 1, 1, 0xFFAA00, 0.4f);
        }
    }
}
