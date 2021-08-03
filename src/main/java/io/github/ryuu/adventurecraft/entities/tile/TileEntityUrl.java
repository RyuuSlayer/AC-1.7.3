package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityUrl extends TileEntity {
    public String url = "";

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.url = nbttagcompound.i("url");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        if (this.url != null && !this.url.equals(""))
            nbttagcompound.a("url", this.url);
    }
}
