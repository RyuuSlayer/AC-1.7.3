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
import net.minecraft.level.Level;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;

public class ScriptWeather {

    MixinLevel world;

    ScriptWeather(MixinLevel w) {
        this.world = w;
    }

    public void setPrecipitating(boolean precipate) {
        this.world.properties.setRaining(precipate);
    }

    public boolean getPrecipitating() {
        return this.world.properties.isRaining();
    }

    public void setTemperatureOffset(double tempOffset) {
        this.world.properties.tempOffset = tempOffset;
    }

    public double getTemperatureOffset() {
        return this.world.properties.tempOffset;
    }

    public void setThundering(boolean precipate) {
        this.world.properties.setThundering(precipate);
    }

    public boolean getThundering() {
        return this.world.properties.isThundering();
    }
}
