/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileNotFoundException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Properties
 *  java.util.Set
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.scripting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.script.ScriptEntityDescription;

public class EntityDescriptions {

    static final HashMap<String, ScriptEntityDescription> descriptions = new HashMap();

    public static ScriptEntityDescription getDescription(String descName) {
        return (ScriptEntityDescription) descriptions.get((Object) descName);
    }

    static void addDescription(String descName, ScriptEntityDescription desc) {
        descriptions.put((Object) descName, (Object) desc);
    }

    public static void clearDescriptions() {
        descriptions.clear();
    }

    public static void loadDescriptions(File dir) {
        EntityDescriptions.clearDescriptions();
        if (dir != null && dir.exists() && dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if (!f.isFile() || !f.getName().endsWith(".txt"))
                    continue;
                EntityDescriptions.loadDescription(f);
            }
        }
    }

    private static void loadDescription(File descFile) {
        Properties p = new Properties();
        try {
            p.load((InputStream) new FileInputStream(descFile));
            ScriptEntityDescription desc = new ScriptEntityDescription(descFile.getName().split("\\.")[0]);
            try {
                Integer health = Integer.parseInt((String) p.getProperty("health", "10"));
                desc.health = health;
            } catch (NumberFormatException e) {
                // empty catch block
            }
            try {
                desc.width = Float.parseFloat((String) p.getProperty("width", "0.6"));
            } catch (NumberFormatException e) {
                // empty catch block
            }
            try {
                desc.height = Float.parseFloat((String) p.getProperty("height", "1.8"));
            } catch (NumberFormatException e) {
                // empty catch block
            }
            try {
                desc.moveSpeed = Float.parseFloat((String) p.getProperty("moveSpeed", "0.7"));
            } catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            desc.texture = p.getProperty("texture", "/mob/char.png");
            desc.onCreated = p.getProperty("onCreated", "");
            desc.onUpdate = p.getProperty("onUpdate", "");
            desc.onDeath = p.getProperty("onDeath", "");
            desc.onPathReached = p.getProperty("onPathReached", "");
            desc.onAttacked = p.getProperty("onAttacked", "");
            desc.onInteraction = p.getProperty("onInteraction", "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<String> getDescriptions() {
        return descriptions.keySet();
    }
}
