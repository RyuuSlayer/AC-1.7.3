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

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.changePrecipitate = nbttagcompound.m("changePrecipitate");
        this.precipitate = nbttagcompound.m("precipitate");
        this.changeTempOffset = nbttagcompound.m("changeTempOffset");
        this.tempOffset = nbttagcompound.h("tempOffset");
        this.changeTimeOfDay = nbttagcompound.m("changeTimeOfDay");
        this.timeOfDay = nbttagcompound.e("timeOfDay");
        this.changeTimeRate = nbttagcompound.m("changeTimeRate");
        this.timeRate = nbttagcompound.g("timeRate");
        this.changeThundering = nbttagcompound.m("changeThundering");
        this.thundering = nbttagcompound.m("thundering");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("changePrecipitate", this.changePrecipitate);
        nbttagcompound.a("precipitate", this.precipitate);
        nbttagcompound.a("changeTempOffset", this.changeTempOffset);
        nbttagcompound.a("tempOffset", this.tempOffset);
        nbttagcompound.a("changeTimeOfDay", this.changeTimeOfDay);
        nbttagcompound.a("timeOfDay", this.timeOfDay);
        nbttagcompound.a("changeTimeRate", this.changeTimeRate);
        nbttagcompound.a("timeRate", this.timeRate);
        nbttagcompound.a("changeThundering", this.changeThundering);
        nbttagcompound.a("thundering", this.thundering);
    }
}
