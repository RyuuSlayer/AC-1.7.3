package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityMusic extends TileEntity {

    public String musicName = "";

    public int fadeOut = 500;

    public int fadeIn = 500;

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.musicName = tag.getString("musicName");
        this.fadeOut = tag.getInt("fadeOut");
        this.fadeIn = tag.getInt("fadeIn");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        if (this.musicName != null && !this.musicName.equals("")) {
            tag.put("musicName", this.musicName);
        }
        tag.put("fadeOut", this.fadeOut);
        tag.put("fadeIn", this.fadeIn);
    }
}
