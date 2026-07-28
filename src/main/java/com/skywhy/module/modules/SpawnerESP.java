package com.skywhy.module.modules;
import com.skywhy.module.Module;
import com.skywhy.render.Render3D;
import net.minecraft.block.entity.SpawnerBlockEntity;
public class SpawnerESP extends Module {
    public SpawnerESP() { super("SpawnerESP", Category.VISUAL); }
    @Override
    public void onRender3D() {
        if (mc.world == null) return;
        for (SpawnerBlockEntity spawner : mc.world.getBlockEntities(SpawnerBlockEntity.class)) {
            double x = spawner.getPos().getX(), y = spawner.getPos().getY(), z = spawner.getPos().getZ();
            Render3D.drawESPBox(x-0.5, y-0.5, z-0.5, 1, 1, 1, 0xFF00FF, 0.4f);
        }
    }
}
