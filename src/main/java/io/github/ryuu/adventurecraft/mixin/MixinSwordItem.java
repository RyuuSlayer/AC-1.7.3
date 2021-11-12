package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;

public class MixinSwordItem extends ItemType {
    private final int a;

    public MixinSwordItem(int i, bu enumtoolmaterial) {
        super(i);
        this.bg = 1;
        e(enumtoolmaterial.a());
        this.a = 4 + enumtoolmaterial.c() * 2;
    }

    public float a(ItemInstance itemstack, Tile block) {
        return (block.bn != Tile.X.bn) ? 1.5F : 15.0F;
    }

    public boolean a(ItemInstance itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        return true;
    }

    public boolean a(ItemInstance itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        return true;
    }

    public int a(Entity entity) {
        return this.a;
    }

    public boolean b() {
        return true;
    }

    public boolean a(Tile block) {
        return (block.bn == Tile.X.bn);
    }

    public boolean mainActionLeftClick() {
        return true;
    }
}
