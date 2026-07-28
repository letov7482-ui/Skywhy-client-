package com.skywhy.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class HitBoxUtils {
    public static Box expandHitBox(Entity entity, float multiplier) {
        Box box = entity.getBoundingBox();
        return box.expand(multiplier, multiplier * 0.5, multiplier);
    }

    public static boolean isInExpandedBox(Entity entity, double x, double y, double z, float multiplier) {
        Box expanded = expandHitBox(entity, multiplier);
        return expanded.contains(x, y, z);
    }

    // Server-specific bypass: FunTime, SookyTime use 3.0-3.2 reach check
    public static float getOptimalMultiplier(String serverBrand) {
        if (serverBrand.contains("funtime") || serverBrand.contains("sooky")) {
            return 0.25f; // safe
        }
        return 0.35f; // default
    }
}
