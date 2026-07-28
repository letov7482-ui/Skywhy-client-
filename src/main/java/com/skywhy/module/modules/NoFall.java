package com.skywhy.module.modules;
import com.skywhy.module.Module;
public class NoFall extends Module { public NoFall() { super("NoFall", Category.PLAYER); }
    @Override public void onTick() { if (mc.player != null) mc.player.fallDistance = 0; } }
