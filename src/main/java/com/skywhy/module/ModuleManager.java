package com.skywhy.module;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import java.util.ArrayList;
import java.util.List;
import com.skywhy.module.modules.*;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Combat
        addModule(new KillAura());
        addModule(new Reach());
        addModule(new Velocity());
        // Movement
        addModule(new Speed());
        addModule(new Flight());
        addModule(new LongJump());
        addModule(new Sprint());
        // Player
        addModule(new NoFall());
        addModule(new AntiPush());
        addModule(new AutoRespawn());
        // Visual
        addModule(new ESP());
        addModule(new Nametags());
        addModule(new Tracers());
        // Render
        addModule(new FullBright());
        addModule(new Freecam());
        addModule(new Zoom());
        addModule(new NoRender());
        // Misc
        addModule(new AutoClicker());
        addModule(new MiddleClick());
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (Module m : modules) if (m.isEnabled()) m.onTick();
        });
    }

    public void addModule(Module m) { modules.add(m); }
    public List<Module> getModules() { return modules; }
    public List<Module> getModulesByCategory(Module.Category cat) {
        List<Module> list = new ArrayList<>();
        for (Module m : modules) if (m.getCategory() == cat) list.add(m);
        return list;
    }
    public Module getModule(Class<? extends Module> cls) {
        for (Module m : modules) if (m.getClass() == cls) return m;
        return null;
    }
    public void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        for (Module m : modules) {
            if (m.isEnabled()) m.onTick();
            if (m.isEnabled() && m.isVisible()) m.onRender3D();
            if (m.isEnabled() && m.isVisible()) m.onRender2D();
        }
    }
}
