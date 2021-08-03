package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;

public class TileEntityUrl extends TileEntity {
    public String url = "";

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.url = nbttagcompound.i("url");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        if (this.url != null && !this.url.equals(""))
            nbttagcompound.a("url", this.url);
    }
}
