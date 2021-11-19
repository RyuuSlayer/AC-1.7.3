package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.mixin.client.MixinMinecraft;
import net.minecraft.level.Level;

public class ScriptTime {
    Level worldObj;

    ScriptTime(Level w) {
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
        MixinMinecraft.minecraftInstance.level.script.sleep(t);
    }
}