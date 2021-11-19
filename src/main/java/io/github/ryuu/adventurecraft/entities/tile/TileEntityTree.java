package io.github.ryuu.adventurecraft.entities.tile;

public class TileEntityTree extends MixinTileEntity {

    public float size = 1.0f;

    @Override
    public void readIdentifyingData(MixinCompoundTag tag) {
        super.readIdentifyingData(tag);
        this.size = tag.getFloat("size");
    }

    @Override
    public void writeIdentifyingData(MixinCompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("size", this.size);
    }
}
