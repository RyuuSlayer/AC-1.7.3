package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;

public class TileEntityHealDamage extends TileEntity {
    public int healDamage;

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.healDamage = nbttagcompound.e("healDamage");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("healDamage", this.healDamage);
    }
}
