package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityMessage extends TileEntity {

    public String message = "";

    public String sound = "";

    public TileEntityMessage() {
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.message = tag.getString("message");
        this.sound = tag.getString("sound");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        if (this.message != null && !this.message.equals("")) {
            tag.put("message", this.message);
        }
        tag.put("sound", this.sound);
    }
}
