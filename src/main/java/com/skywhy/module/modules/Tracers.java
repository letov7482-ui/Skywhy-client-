package com.skywhy.module.modules;
import com.skywhy.module.Module;
import com.skywhy.render.Render3D;
import net.minecraft.entity.player.PlayerEntity;
public class Tracers extends Module {
    public Tracers() { super("Tracers", Category.VISUAL); }
    @Override
    public void onRender3D() {
        if (mc.world == null || mc.player == null) return;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player) continue;
            double x = p.getX() - mc.player.getX();
            double y = p.getY() - mc.player.getY();
            double z = p.getZ() - mc.player.getZ();
            int color = 0x00AAFF;
            Render3D.drawTracer(0, mc.player.getEyeY(), 0, x, y, z, color);
        }
    }
}
