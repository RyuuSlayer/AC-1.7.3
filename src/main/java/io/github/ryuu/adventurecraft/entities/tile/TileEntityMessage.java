package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

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
        if (this.message != null && !this.message.equals((Object) "")) {
            tag.put("message", this.message);
        }
        tag.put("sound", this.sound);
    }
}
