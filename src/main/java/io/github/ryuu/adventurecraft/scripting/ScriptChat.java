package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.client.Minecraft;

public class ScriptChat {
    public void print(String msg, Object... args) {
        msg = String.format(msg, args);
        Minecraft.minecraftInstance.overlay.addChatMessage(msg);
    }
}