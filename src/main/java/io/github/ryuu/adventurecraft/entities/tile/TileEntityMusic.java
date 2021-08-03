package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;

public class TileEntityMusic extends TileEntity {
    public int fadeOut = 500;

    public int fadeIn = 500;

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.musicName = nbttagcompound.i("musicName");
        this.fadeOut = nbttagcompound.e("fadeOut");
        this.fadeIn = nbttagcompound.e("fadeIn");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        if (this.musicName != null && !this.musicName.equals(""))
            nbttagcompound.a("musicName", this.musicName);
        nbttagcompound.a("fadeOut", this.fadeOut);
        nbttagcompound.a("fadeIn", this.fadeIn);
    }

    public String musicName = "";
}
