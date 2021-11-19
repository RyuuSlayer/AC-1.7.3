package io.github.ryuu.adventurecraft.entities.tile;

public class TileEntityUrl extends MixinTileEntity {

    public String url = "";

    TileEntityUrl() {
    }

    @Override
    public void readIdentifyingData(MixinCompoundTag tag) {
        super.readIdentifyingData(tag);
        this.url = tag.getString("url");
    }

    @Override
    public void writeIdentifyingData(MixinCompoundTag tag) {
        super.writeIdentifyingData(tag);
        if (this.url != null && !this.url.equals("")) {
            tag.put("url", this.url);
        }
    }
}
