package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import net.minecraft.level.Level;

@SuppressWarnings("unused")
public class ScriptWeather {

    public Level level;

    public ScriptWeather(Level level) {
        this.level = level;
    }

    public boolean getPrecipitating() {
        return this.level.getProperties().isRaining();
    }

    public void setPrecipitating(boolean precipate) {
        this.level.getProperties().setRaining(precipate);
    }

    public double getTemperatureOffset() {
        return ((ExLevelProperties) this.level.getProperties()).getTempOffset();
    }

    public void setTemperatureOffset(double tempOffset) {
        ((ExLevelProperties) this.level.getProperties()).setTempOffset(tempOffset);
    }

    public boolean getThundering() {
        return this.level.getProperties().isThundering();
    }

    public void setThundering(boolean precipate) {
        this.level.getProperties().setThundering(precipate);
    }
}
