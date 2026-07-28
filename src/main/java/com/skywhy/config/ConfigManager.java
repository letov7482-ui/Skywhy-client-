package com.skywhy.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skywhy.client.SkyWhyClient;
import com.skywhy.module.Module;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path configDir = FabricLoader.getInstance().getConfigDir().resolve("skywhy");
    private final Path configFile = configDir.resolve("config.json");
    private Map<String, Object> data = new HashMap<>();

    public void load() {
        if (!configFile.toFile().exists()) { save(); return; }
        try (Reader reader = new FileReader(configFile.toFile())) {
            data = gson.fromJson(reader, Map.class);
        } catch (IOException e) { e.printStackTrace(); }
        for (Module m : SkyWhyClient.INSTANCE.moduleManager.getModules()) {
            if (data.containsKey(m.getName())) {
                Map<String, Object> modData = (Map<String, Object>) data.get(m.getName());
                m.setEnabled((Boolean) modData.getOrDefault("enabled", false));
                m.setKey(((Double) modData.getOrDefault("key", 0.0)).intValue());
            }
        }
    }

    public void save() {
        if (!configDir.toFile().exists()) configDir.toFile().mkdirs();
        for (Module m : SkyWhyClient.INSTANCE.moduleManager.getModules()) {
            Map<String, Object> modData = new HashMap<>();
            modData.put("enabled", m.isEnabled());
            modData.put("key", m.getKey());
            data.put(m.getName(), modData);
        }
        try (Writer writer = new FileWriter(configFile.toFile())) {
            gson.toJson(data, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
