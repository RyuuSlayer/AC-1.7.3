package io.github.ryuu.adventurecraft.entities.tile;

public class TileEntityMusic extends MixinTileEntity {

    public String musicName = "";

    public int fadeOut = 500;

    public int fadeIn = 500;

    @Override
    public void readIdentifyingData(MixinCompoundTag tag) {
        super.readIdentifyingData(tag);
        this.musicName = tag.getString("musicName");
        this.fadeOut = tag.getInt("fadeOut");
        this.fadeIn = tag.getInt("fadeIn");
    }

    @Override
    public void writeIdentifyingData(MixinCompoundTag tag) {
        super.writeIdentifyingData(tag);
        if (this.musicName != null && !this.musicName.equals("")) {
            tag.put("musicName", this.musicName);
        }
        tag.put("fadeOut", this.fadeOut);
        tag.put("fadeIn", this.fadeIn);
    }
}
