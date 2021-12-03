package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.level.Level;

@SuppressWarnings("unused")
public class ScriptTime {

    Level level;

    public ScriptTime(Level level) {
        this.level = level;
    }

    public float get() {
        return ((ExLevel) this.level).getTimeOfDay();
    }

    public void set(long time) {
        ((ExLevel) this.level).setTimeOfDay(time);
    }

    public float getTime() {
        return ((ExLevel) this.level).getTimeOfDay();
    }

    public void setTime(long time) {
        ((ExLevel) this.level).setTimeOfDay(time);
    }

    public float getRate() {
        return ((ExLevelProperties) this.level.getProperties()).getTimeRate();
    }

    public void setRate(float timeRate) {
        ((ExLevelProperties) this.level.getProperties()).setTimeRate(timeRate);
    }

    public long getTickCount() {
        return this.level.getLevelTime();
    }

    public void sleep(float t) {
        // TODO: use this.level instead?
        ((ExLevel) AccessMinecraft.getInstance().level).getScript().sleep(t);
    }
}
