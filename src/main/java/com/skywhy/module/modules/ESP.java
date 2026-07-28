package com.skywhy.module.modules;

import com.skywhy.module.Module;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import com.skywhy.render.Render3D;

public class ESP extends Module {
    public ESP() {
        super("ESP", Category.VISUAL);
    }

    @Override
    public void onRender3D() {
        if (mc.world == null || mc.player == null) return;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player) continue;
            if (player.distanceTo(mc.player) > 50) continue;
            Box bb = player.getBoundingBox();
            Vec3d pos = player.getPos();
            double x = pos.x - mc.getEntityRenderDispatcher().camera.getPos().x;
            double y = pos.y - mc.getEntityRenderDispatcher().camera.getPos().y;
            double z = pos.z - mc.getEntityRenderDispatcher().camera.getPos().z;
            double w = bb.getLengthX();
            double h = bb.getLengthY();
            double l = bb.getLengthZ();
            Render3D.drawESPBox(x, y, z, w, h, l, 0x00FF00, 0x0000FF);
        }
    }
}
