package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityHealDamage extends TileEntity {
    public int healDamage;

    @Override
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.healDamage = nbttagcompound.getInt("healDamage");
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        nbttagcompound.put("healDamage", this.healDamage);
    }
}
