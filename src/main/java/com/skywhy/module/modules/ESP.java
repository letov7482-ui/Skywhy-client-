package com.skywhy.module.modules;

import com.skywhy.module.Module;
import com.skywhy.render.Render3D;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ESP extends Module {
    private boolean glow = true;
    private boolean filled = true;
    private boolean outline = true;
    private float alpha = 0.6f;

    public ESP() { super("ESP", Category.VISUAL); }

    @Override
    public void onRender3D() {
        if (mc.world == null || mc.player == null) return;
        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof PlayerEntity) || e == mc.player) continue;
            if (e.distanceTo(mc.player) > 60) continue;
            Box bb = e.getBoundingBox();
            Vec3d pos = e.getPos();
            double x = pos.x - mc.getEntityRenderDispatcher().camera.getPos().x;
            double y = pos.y - mc.getEntityRenderDispatcher().camera.getPos().y;
            double z = pos.z - mc.getEntityRenderDispatcher().camera.getPos().z;
            double w = bb.getLengthX();
            double h = bb.getLengthY();
            double l = bb.getLengthZ();
            int color = 0x00AAFF; // Sky blue
            if (outline) Render3D.drawESPOutline(x, y, z, w, h, l, color, 1.5f);
            if (filled) Render3D.drawESPBox(x, y, z, w, h, l, color, alpha);
            if (glow) Render3D.drawGlow(x, y, z, w, h, l, color, 0.3f);
        }
    }
}
