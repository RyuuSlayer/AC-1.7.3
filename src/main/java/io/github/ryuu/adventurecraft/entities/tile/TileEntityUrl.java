package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityUrl extends TileEntity {

    public String url = "";

    public TileEntityUrl() {
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.url = tag.getString("url");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        if (this.url != null && !this.url.equals("")) {
            tag.put("url", this.url);
        }
    }
}
