package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class Zoom extends Module {
    private float zoomFOV = 30.0f;
    private boolean smooth = true;
    public Zoom() { super("Zoom", Category.RENDER); }
    @Override
    public void onTick() {
        if (mc.options == null) return;
        if (mc.options.attackKey.isPressed()) {
            float current = mc.options.getFov().getValue();
            if (smooth) {
                mc.options.getFov().setValue(current - (current - zoomFOV) * 0.1f);
            } else {
                mc.options.getFov().setValue(zoomFOV);
            }
        } else {
            mc.options.getFov().setValue(70.0f);
        }
    }
}
