package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class CustomFOV extends Module {
    private float fov = 100.0f;
    public CustomFOV() { super("CustomFOV", Category.RENDER); }
    @Override
    public void onTick() {
        if (mc.options != null) mc.options.getFov().setValue(fov);
    }
    public void setFov(float fov) { this.fov = Math.min(fov, 120f); }
}
