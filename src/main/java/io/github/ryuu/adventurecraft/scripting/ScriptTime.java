package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.mixin.MixinMinecraft;
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
        return this.worldObj.x.getTimeRate();
    }

    public void setRate(float timeRate) {
        this.worldObj.x.setTimeRate(timeRate);
    }

    public long getTickCount() {
        return this.worldObj.t();
    }

    public void sleep(float t) {
        MixinMinecraft.minecraftInstance.f.script.sleep(t);
    }
}
