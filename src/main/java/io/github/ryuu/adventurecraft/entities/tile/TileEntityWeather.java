package io.github.ryuu.adventurecraft.entities.tile;

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
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.changePrecipitate = nbttagcompound.getBoolean("changePrecipitate");
        this.precipitate = nbttagcompound.getBoolean("precipitate");
        this.changeTempOffset = nbttagcompound.getBoolean("changeTempOffset");
        this.tempOffset = nbttagcompound.getDouble("tempOffset");
        this.changeTimeOfDay = nbttagcompound.getBoolean("changeTimeOfDay");
        this.timeOfDay = nbttagcompound.getInt("timeOfDay");
        this.changeTimeRate = nbttagcompound.getBoolean("changeTimeRate");
        this.timeRate = nbttagcompound.getFloat("timeRate");
        this.changeThundering = nbttagcompound.getBoolean("changeThundering");
        this.thundering = nbttagcompound.getBoolean("thundering");
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        nbttagcompound.put("changePrecipitate", this.changePrecipitate);
        nbttagcompound.put("precipitate", this.precipitate);
        nbttagcompound.put("changeTempOffset", this.changeTempOffset);
        nbttagcompound.put("tempOffset", this.tempOffset);
        nbttagcompound.put("changeTimeOfDay", this.changeTimeOfDay);
        nbttagcompound.put("timeOfDay", this.timeOfDay);
        nbttagcompound.put("changeTimeRate", this.changeTimeRate);
        nbttagcompound.put("timeRate", this.timeRate);
        nbttagcompound.put("changeThundering", this.changeThundering);
        nbttagcompound.put("thundering", this.thundering);
    }
}
