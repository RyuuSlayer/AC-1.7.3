package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class TileEntityMessage extends TileEntity {

    public String message = "";

    public String sound = "";

    TileEntityMessage() {
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
        if (this.message != null && !this.message.equals((Object) "")) {
            tag.put("message", this.message);
        }
        tag.put("sound", this.sound);
    }
}
