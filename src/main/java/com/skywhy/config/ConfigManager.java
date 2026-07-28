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
                // Дополнительные настройки
                if (modData.containsKey("settings")) {
                    Map<String, Object> settings = (Map<String, Object>) modData.get("settings");
                    m.loadSettings(settings);
                }
            }
        }
    }

    public void save() {
        if (!configDir.toFile().exists()) configDir.toFile().mkdirs();
        for (Module m : SkyWhyClient.INSTANCE.moduleManager.getModules()) {
            Map<String, Object> modData = new HashMap<>();
            modData.put("enabled", m.isEnabled());
            modData.put("key", m.getKey());
            modData.put("settings", m.saveSettings());
            data.put(m.getName(), modData);
        }
        try (Writer writer = new FileWriter(configFile.toFile())) {
            gson.toJson(data, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void saveServerHitbox(String serverIP, float multiplier) {
        Map<String, Object> serverMap = (Map<String, Object>) data.getOrDefault("servers", new HashMap<>());
        serverMap.put(serverIP, multiplier);
        data.put("servers", serverMap);
        save();
    }

    public float getServerHitbox(String serverIP) {
        Map<String, Object> serverMap = (Map<String, Object>) data.get("servers");
        if (serverMap == null) return 0.3f;
        return ((Double) serverMap.getOrDefault(serverIP, 0.3)).floatValue();
    }

    public void saveModuleSettings(Module m, Map<String, Object> settings) {
        data.put(m.getName() + "_settings", settings);
        save();
    }

    public Map<String, Object> loadModuleSettings(Module m) {
        return (Map<String, Object>) data.getOrDefault(m.getName() + "_settings", new HashMap<>());
    }
          }
