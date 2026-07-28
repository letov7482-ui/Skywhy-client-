package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class WeatherChanger extends Module {
    public WeatherChanger() { super("WeatherChanger", Category.RENDER); }
    @Override
    public void onTick() {
        if (mc.world != null) {
            mc.world.setRainGradient(0);
            mc.world.setThunderGradient(0);
            mc.world.setWeather(0, 0, false, false);
        }
    }
}
