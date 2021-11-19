/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.item.tool;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.tool.SwordItem;
import net.minecraft.item.tool.ToolMaterial;
import net.minecraft.tile.Tile;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemType;
import io.github.ryuu.adventurecraft.mixin.item.MixinLivingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemInstance;

@Mixin(SwordItem.class)
public class MixinSwordItem extends MixinItemType {

    @Shadow()
    private int field_1314;

    public MixinSwordItem(int i, ToolMaterial enumtoolmaterial) {
        super(i);
        this.maxStackSize = 1;
        this.setDurability(enumtoolmaterial.getDurability());
        this.field_1314 = 4 + enumtoolmaterial.getAttackDamage() * 2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public float method_438(MixinItemInstance item, MixinTile tile) {
        return tile.id != Tile.WEB.id ? 1.5f : 15.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean postHit(MixinItemInstance itemstack, MixinLivingEntity entityliving, MixinLivingEntity entityliving1) {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean postMine(MixinItemInstance itemstack, int i, int j, int k, int l, MixinLivingEntity entityliving) {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_447(MixinEntity entity) {
        return this.field_1314;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean shouldRenderLikeStick() {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isEffectiveOn(MixinTile tile) {
        return tile.id == Tile.WEB.id;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean mainActionLeftClick() {
        return true;
    }
}
