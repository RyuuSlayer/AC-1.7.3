package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;

public class TileEntityMessage extends TileEntity {
    public String message = "";

    public String sound = "";

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.message = nbttagcompound.i("message");
        this.sound = nbttagcompound.i("sound");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        if (this.message != null && !this.message.equals(""))
            nbttagcompound.a("message", this.message);
        nbttagcompound.a("sound", this.sound);
    }
}
