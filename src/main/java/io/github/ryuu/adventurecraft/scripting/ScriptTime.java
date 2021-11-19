package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.client.Minecraft;

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
