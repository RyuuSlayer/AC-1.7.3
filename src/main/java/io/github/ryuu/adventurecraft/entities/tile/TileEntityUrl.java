package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class TileEntityUrl extends TileEntity {

    public String url = "";

    TileEntityUrl() {
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.url = tag.getString("url");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        if (this.url != null && !this.url.equals((Object) "")) {
            tag.put("url", this.url);
        }
    }
}
