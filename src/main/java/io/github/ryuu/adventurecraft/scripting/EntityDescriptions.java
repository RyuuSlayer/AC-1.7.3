package io.github.ryuu.adventurecraft.scripting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class EntityDescriptions {

    static final HashMap<String, ScriptEntityDescription> descriptions = new HashMap<>();

    public static ScriptEntityDescription getDescription(String descName) {
        return descriptions.get(descName);
    }

    static void addDescription(String descName, ScriptEntityDescription desc) {
        descriptions.put(descName, desc);
    }

    public static void clearDescriptions() {
        descriptions.clear();
    }

    public static void loadDescriptions(File dir) {
        EntityDescriptions.clearDescriptions();
        if (dir != null && dir.exists() && dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if (!f.isFile() || !f.getName().endsWith(".txt")) continue;
                EntityDescriptions.loadDescription(f);
            }
        }
    }

    private static void loadDescription(File descFile) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(descFile));
            ScriptEntityDescription desc = new ScriptEntityDescription(descFile.getName().split("\\.")[0]);
            try {
                int health = Integer.parseInt(p.getProperty("health", "10"));
                desc.health = health;
            } catch (NumberFormatException e) {
            }
            try {
                desc.width = Float.parseFloat(p.getProperty("width", "0.6"));
            } catch (NumberFormatException e) {
            }
            try {
                desc.height = Float.parseFloat(p.getProperty("height", "1.8"));
            } catch (NumberFormatException e) {
            }
            try {
                desc.moveSpeed = Float.parseFloat(p.getProperty("moveSpeed", "0.7"));
            } catch (NumberFormatException numberFormatException) {
            }
            desc.texture = p.getProperty("texture", "/mob/char.png");
            desc.onCreated = p.getProperty("onCreated", "");
            desc.onUpdate = p.getProperty("onUpdate", "");
            desc.onDeath = p.getProperty("onDeath", "");
            desc.onPathReached = p.getProperty("onPathReached", "");
            desc.onAttacked = p.getProperty("onAttacked", "");
            desc.onInteraction = p.getProperty("onInteraction", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<String> getDescriptions() {
        return descriptions.keySet();
    }
}
