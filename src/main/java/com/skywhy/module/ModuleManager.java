package com.skywhy.module;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import java.util.ArrayList;
import java.util.List;
import com.skywhy.module.modules.*;
import com.skywhy.cosmetics.CosmeticManager;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // COMBAT
        addModule(new KillAura());
        addModule(new Reach());
        addModule(new Velocity());
        addModule(new AutoClicker());
        addModule(new AntiBot());
        addModule(new AutoCrystal());
        addModule(new HitCrystal());
        addModule(new AutoAnchor());
        addModule(new SafeAnchor());

        // MOVEMENT
        addModule(new Speed());
        addModule(new Flight());
        addModule(new LongJump());
        addModule(new Strafe());
        addModule(new Step());
        addModule(new NoSlow());
        addModule(new Sprint());
        addModule(new FlyBoost());

        // PLAYER
        addModule(new NoFall());
        addModule(new AntiPush());
        addModule(new AutoRespawn());
        addModule(new FastPlace());
        addModule(new AutoEat());
        addModule(new Regen());
        addModule(new AntiBlind());

        // VISUAL
        addModule(new ESP());
        addModule(new Nametags());
        addModule(new Tracers());
        addModule(new Skeleton());
        addModule(new ItemESP());
        addModule(new ChestESP());
        addModule(new SpawnerESP());

        // RENDER
        addModule(new FullBright());
        addModule(new Freecam());
        addModule(new Zoom());
        addModule(new NoRender());
        addModule(new WeatherChanger());
        addModule(new XRay());
        addModule(new CustomFOV());

        // MISC
        addModule(new MiddleClick());
        addModule(new Timer());
        addModule(new ChatSuffix());
        addModule(new DiscordRPC());

        // COSMETICS
        addModule(new CosmeticManager());

        // Tick registration
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
