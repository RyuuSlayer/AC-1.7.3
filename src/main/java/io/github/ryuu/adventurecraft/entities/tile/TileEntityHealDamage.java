package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityHealDamage extends TileEntity {
    public int healDamage;

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.healDamage = nbttagcompound.e("healDamage");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("healDamage", this.healDamage);
    }
}
