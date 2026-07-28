package com.skywhy.module.modules;
import com.skywhy.module.Module;
import com.skywhy.render.Render3D;
import net.minecraft.entity.player.PlayerEntity;
public class Skeleton extends Module {
    public Skeleton() { super("Skeleton", Category.VISUAL); }
    @Override
    public void onRender3D() {
        if (mc.world == null || mc.player == null) return;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player) continue;
            Render3D.drawSkeleton(p, 0x00FFAA);
        }
    }
}
