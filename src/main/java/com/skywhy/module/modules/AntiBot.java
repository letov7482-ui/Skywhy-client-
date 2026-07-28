package com.skywhy.module.modules;
import com.skywhy.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
public class AntiBot extends Module {
    public AntiBot() { super("AntiBot", Category.COMBAT); }
    @Override
    public void onTick() {
        if (mc.world == null) return;
        for (Entity e : mc.world.getEntities()) {
            if (e instanceof PlayerEntity && e.isInvisible()) {
                e.remove(Entity.RemovalReason.DISCARDED);
            }
            if (e instanceof ArmorStandEntity && e.getCustomName() != null) {
                e.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }
}
