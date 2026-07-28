package com.skywhy.module.modules;
import com.skywhy.module.Module;
import net.minecraft.client.render.WeatherRendering;
public class NoRender extends Module {
    public NoRender() { super("NoRender", Category.RENDER); }
    @Override
    public void onTick() {
        if (mc.world == null) return;
        mc.world.setRainGradient(0);
        mc.world.setThunderGradient(0);
        mc.options.setFovEffectScale(0.0f);
        mc.options.setDistortionEffectScale(0.0f);
    }
}
