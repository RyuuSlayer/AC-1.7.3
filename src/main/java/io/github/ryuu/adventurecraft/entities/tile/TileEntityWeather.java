package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityWeather extends TileEntity {

    public boolean changePrecipitate;

    public boolean precipitate;

    public boolean changeTempOffset;

    public double tempOffset;

    public boolean changeTimeOfDay;

    public int timeOfDay;

    public boolean changeTimeRate;

    public float timeRate;

    public boolean thundering;

    public boolean changeThundering;

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.changePrecipitate = tag.getBoolean("changePrecipitate");
        this.precipitate = tag.getBoolean("precipitate");
        this.changeTempOffset = tag.getBoolean("changeTempOffset");
        this.tempOffset = tag.getDouble("tempOffset");
        this.changeTimeOfDay = tag.getBoolean("changeTimeOfDay");
        this.timeOfDay = tag.getInt("timeOfDay");
        this.changeTimeRate = tag.getBoolean("changeTimeRate");
        this.timeRate = tag.getFloat("timeRate");
        this.changeThundering = tag.getBoolean("changeThundering");
        this.thundering = tag.getBoolean("thundering");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("changePrecipitate", this.changePrecipitate);
        tag.put("precipitate", this.precipitate);
        tag.put("changeTempOffset", this.changeTempOffset);
        tag.put("tempOffset", this.tempOffset);
        tag.put("changeTimeOfDay", this.changeTimeOfDay);
        tag.put("timeOfDay", this.timeOfDay);
        tag.put("changeTimeRate", this.changeTimeRate);
        tag.put("timeRate", this.timeRate);
        tag.put("changeThundering", this.changeThundering);
        tag.put("thundering", this.thundering);
    }
}
