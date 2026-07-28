package com.skywhy.module.modules;
import com.skywhy.module.Module;
import net.minecraft.client.network.ClientPlayerEntity;
public class AutoRespawn extends Module {
    public AutoRespawn() { super("AutoRespawn", Category.PLAYER); }
    @Override
    public void onTick() {
        if (mc.player != null && mc.player.isDead()) {
            mc.player.requestRespawn();
        }
    }
}
