package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;

public class ScriptChat {

    ScriptChat() {
    }

    public void print(String msg, Object... args) {
        msg = String.format(msg, args);
        AccessMinecraft.getInstance().overlay.addChatMessage(msg);
    }
}
