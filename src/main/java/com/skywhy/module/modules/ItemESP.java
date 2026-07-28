package com.skywhy.module.modules;
import com.skywhy.module.Module;
import com.skywhy.render.Render3D;
import net.minecraft.entity.ItemEntity;
public class ItemESP extends Module {
    public ItemESP() { super("ItemESP", Category.VISUAL); }
    @Override
    public void onRender3D() {
        if (mc.world == null) return;
        for (ItemEntity item : mc.world.getEntitiesByClass(ItemEntity.class, e -> true)) {
            Render3D.drawESPBox(item.getX()-0.5, item.getY()-0.5, item.getZ()-0.5, 1, 1, 1, 0xFFFF00, 0.4f);
        }
    }
}
