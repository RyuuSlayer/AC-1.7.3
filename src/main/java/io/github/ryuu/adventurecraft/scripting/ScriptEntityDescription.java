/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.script.EntityDescriptions;

public class ScriptEntityDescription {

    public int health = 10;

    public float height = 1.8f;

    public float width = 0.6f;

    public float moveSpeed = 0.7f;

    public String texture;

    public String onCreated = "";

    public String onUpdate = "";

    public String onAttacked = "";

    public String onPathReached = "";

    public String onDeath = "";

    public String onInteraction = "";

    public ScriptEntityDescription(String descName) {
        EntityDescriptions.addDescription(descName, this);
    }
}
