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
import net.minecraft.client.Minecraft;

public class ScriptChat {

    ScriptChat() {
    }

    public void print(String msg, Object... args) {
        msg = String.format((String) msg, (Object[]) args);
        Minecraft.minecraftInstance.overlay.addChatMessage(msg);
    }
}
