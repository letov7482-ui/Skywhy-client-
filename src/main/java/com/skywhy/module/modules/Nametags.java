package com.skywhy.module.modules;
import com.skywhy.module.Module;
import com.skywhy.render.Render2D;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
public class Nametags extends Module {
    public Nametags() { super("Nametags", Category.VISUAL); }
    @Override
    public void onRender3D() {
        if (mc.world == null || mc.player == null) return;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player) continue;
            double dist = p.distanceTo(mc.player);
            String tag = p.getName().getString() + " §7" + (int)dist + "m";
            // Render in 3D space (simplified)
        }
    }
}
