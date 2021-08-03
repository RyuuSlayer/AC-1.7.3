package io.github.ryuu.adventurecraft.scripting;


import net.minecraft.level.Level;

public class ScriptWeather {
    Level world;

    ScriptWeather(Level w) {
        this.world = w;
    }

    public void setPrecipitating(boolean precipate) {
        this.world.x.b(precipate);
    }

    public boolean getPrecipitating() {
        return this.world.x.o();
    }

    public void setTemperatureOffset(double tempOffset) {
        this.world.x.tempOffset = tempOffset;
    }

    public double getTemperatureOffset() {
        return this.world.x.tempOffset;
    }

    public void setThundering(boolean precipate) {
        this.world.x.a(precipate);
    }

    public boolean getThundering() {
        return this.world.x.m();
    }
}
