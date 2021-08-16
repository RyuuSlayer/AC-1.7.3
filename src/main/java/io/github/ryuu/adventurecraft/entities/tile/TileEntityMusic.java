package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityMusic extends TileEntity {
    public int fadeOut = 500;

    public int fadeIn = 500;

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.musicName = nbttagcompound.getString("musicName");
        this.fadeOut = nbttagcompound.getInt("fadeOut");
        this.fadeIn = nbttagcompound.getInt("fadeIn");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        if (this.musicName != null && !this.musicName.equals(""))
            nbttagcompound.put("musicName", this.musicName);
        nbttagcompound.put("fadeOut", this.fadeOut);
        nbttagcompound.put("fadeIn", this.fadeIn);
    }

    public String musicName = "";
}
