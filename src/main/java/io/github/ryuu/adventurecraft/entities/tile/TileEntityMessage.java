package io.github.ryuu.adventurecraft.entities.tile;

public class TileEntityMessage extends MixinTileEntity {

    public String message = "";

    public String sound = "";

    TileEntityMessage() {
    }

    @Override
    public void readIdentifyingData(MixinCompoundTag tag) {
        super.readIdentifyingData(tag);
        this.message = tag.getString("message");
        this.sound = tag.getString("sound");
    }

    @Override
    public void writeIdentifyingData(MixinCompoundTag tag) {
        super.writeIdentifyingData(tag);
        if (this.message != null && !this.message.equals("")) {
            tag.put("message", this.message);
        }
        tag.put("sound", this.sound);
    }
}
