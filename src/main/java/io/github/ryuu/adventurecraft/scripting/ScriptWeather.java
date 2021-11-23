package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.level.Level;

public class ScriptWeather {

    Level world;

    ScriptWeather(Level w) {
        this.world = w;
    }

    public boolean getPrecipitating() {
        return this.world.properties.isRaining();
    }

    public void setPrecipitating(boolean precipate) {
        this.world.properties.setRaining(precipate);
    }

    public double getTemperatureOffset() {
        return this.world.properties.tempOffset;
    }

    public void setTemperatureOffset(double tempOffset) {
        this.world.properties.tempOffset = tempOffset;
    }

    public boolean getThundering() {
        return this.world.properties.isThundering();
    }

    public void setThundering(boolean precipate) {
        this.world.properties.setThundering(precipate);
    }
}
