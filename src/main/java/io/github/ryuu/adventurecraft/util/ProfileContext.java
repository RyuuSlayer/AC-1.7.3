package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
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
