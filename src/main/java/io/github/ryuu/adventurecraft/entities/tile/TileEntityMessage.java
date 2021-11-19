package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityMessage extends TileEntity {
    public String message = "";

    public String sound = "";

    @Override
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.message = nbttagcompound.getString("message");
        this.sound = nbttagcompound.getString("sound");
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        if (this.message != null && !this.message.equals(""))
            nbttagcompound.put("message", this.message);
        nbttagcompound.put("sound", this.sound);
    }
}
