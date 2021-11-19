/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;

public class ScriptTime {

    MixinLevel worldObj;

    ScriptTime(MixinLevel w) {
        this.worldObj = w;
    }

    public float get() {
        return this.worldObj.getTimeOfDay();
    }

    public void set(long time) {
        this.worldObj.setTimeOfDay(time);
    }

    public float getTime() {
        return this.worldObj.getTimeOfDay();
    }

    public void setTime(long time) {
        this.worldObj.setTimeOfDay(time);
    }

    public float getRate() {
        return this.worldObj.properties.getTimeRate();
    }

    public void setRate(float timeRate) {
        this.worldObj.properties.setTimeRate(timeRate);
    }

    public long getTickCount() {
        return this.worldObj.getLevelTime();
    }

    public void sleep(float t) {
        Minecraft.minecraftInstance.level.script.sleep(t);
    }
}
