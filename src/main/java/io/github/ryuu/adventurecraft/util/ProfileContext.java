package io.github.ryuu.adventurecraft.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

class ProfileContext {

    public String contextName;

    long startTime;

    public ProfileContext(String n) {
        this.contextName = n;
        this.startTime = System.nanoTime();
    }

    long getTime() {
        return System.nanoTime() - this.startTime;
    }
}
