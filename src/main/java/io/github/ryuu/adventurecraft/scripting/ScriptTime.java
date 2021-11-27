package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.level.Level;

public class ScriptTime {

    Level world;

    ScriptTime(Level w) {
        this.world = w;
    }

    public float get() {
        return ((ExLevel) this.world).getTimeOfDay();
    }

    public void set(long time) {
        ((ExLevel) this.world).setTimeOfDay(time);
    }

    public float getTime() {
        return ((ExLevel) this.world).getTimeOfDay();
    }

    public void setTime(long time) {
        ((ExLevel) this.world).setTimeOfDay(time);
    }

    public float getRate() {
        return ((ExLevelProperties) this.world.getProperties()).getTimeRate();
    }

    public void setRate(float timeRate) {
        ((ExLevelProperties) this.world.getProperties()).setTimeRate(timeRate);
    }

    public long getTickCount() {
        return this.world.getLevelTime();
    }

    public void sleep(float t) {
        // TODO: use this.world instead?
        ((ExLevel) AccessMinecraft.getInstance().level).getScript().sleep(t);
    }
}
