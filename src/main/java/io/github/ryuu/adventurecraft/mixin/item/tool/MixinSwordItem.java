package io.github.ryuu.adventurecraft.mixin.item.tool;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.tool.ToolMaterial;
import net.minecraft.tile.Tile;

public class MixinSwordItem extends ItemType {
    private int field_1314;

    public MixinSwordItem(int i, ToolMaterial enumtoolmaterial) {
        super(i);
        this.maxStackSize = 1;
        this.setDurability(enumtoolmaterial.getDurability());
        this.field_1314 = 4 + enumtoolmaterial.getAttackDamage() * 2;
    }

    public float method_438(ItemInstance item, Tile tile) {
        return tile.id != Tile.WEB.id ? 1.5f : 15.0f;
    }

    public boolean postHit(ItemInstance itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        return true;
    }

    public boolean postMine(ItemInstance itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        return true;
    }

    public int method_447(Entity entity) {
        return this.field_1314;
    }

    public boolean shouldRenderLikeStick() {
        return true;
    }

    public boolean isEffectiveOn(Tile tile) {
        return tile.id == Tile.WEB.id;
    }

    public boolean mainActionLeftClick() {
        return true;
    }
}