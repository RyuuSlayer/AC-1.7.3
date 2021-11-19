/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.stat.Stats;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;
import io.github.ryuu.adventurecraft.mixin.item.MixinPlayer;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemType;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinLivingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinTile;
import io.github.ryuu.adventurecraft.mixin.item.MixinCompoundTag;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemInstance;

@Mixin(ItemInstance.class)
public final class MixinItemInstance {

    @Shadow()
    public int count = 0;

    public int cooldown;

    public int itemId;

    private int damage;

    public int timeLeft;

    public boolean isReloading = false;

    public boolean justReloaded = false;

    public MixinItemInstance(MixinTile tile) {
        this(tile, 1);
    }

    public MixinItemInstance(MixinTile tile, int count) {
        this(tile.id, count, 0);
    }

    public MixinItemInstance(MixinTile tile, int count, int damage) {
        this(tile.id, count, damage);
    }

    public MixinItemInstance(MixinItemType itemType) {
        this(itemType.id, 1, 0);
    }

    public MixinItemInstance(MixinItemType itemType, int count) {
        this(itemType.id, count, 0);
    }

    public MixinItemInstance(MixinItemType itemType, int count, int damage) {
        this(itemType.id, count, damage);
    }

    public MixinItemInstance(int id, int count, int damage) {
        this.itemId = id;
        this.count = count;
        this.damage = damage;
    }

    public MixinItemInstance(MixinCompoundTag tag) {
        this.fromTag(tag);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance split(int countToTake) {
        this.count -= countToTake;
        return new MixinItemInstance(this.itemId, countToTake, this.damage);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemType getType() {
        return ItemType.byId[this.itemId];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTexturePosition() {
        return this.getType().getTexturePosition(this);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean useOnTile(MixinPlayer entityplayer, MixinLevel world, int x, int y, int z, int l) {
        boolean flag = this.getType().useOnTile(this, entityplayer, world, x, y, z, l);
        if (flag) {
            entityplayer.increaseStat(Stats.useItem[this.itemId], 1);
        }
        return flag;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean useItemLeftClick(MixinPlayer entityplayer, MixinLevel world, int i, int j, int k, int l) {
        return this.getType().onItemUseLeftClick(this, entityplayer, world, i, j, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_708(MixinTile block) {
        return this.getType().method_438(this, block);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance use(MixinLevel world, MixinPlayer entityplayer) {
        return this.getType().use(this, world, entityplayer);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinCompoundTag toTag(MixinCompoundTag tag) {
        tag.put("id", (short) this.itemId);
        tag.put("Count", this.count);
        tag.put("Damage", (short) this.damage);
        return tag;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void fromTag(MixinCompoundTag tag) {
        this.itemId = tag.getShort("id");
        this.count = tag.getInt("Count");
        this.damage = tag.getShort("Damage");
        if (this.itemId == Items.boomerang.id) {
            this.damage = 0;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_709() {
        return this.getType().method_459();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_715() {
        return this.method_709() > 1 && (!this.method_717() || !this.method_720());
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_717() {
        return ItemType.byId[this.itemId].method_464() > 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_719() {
        return ItemType.byId[this.itemId].method_462();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_720() {
        return this.method_717() && this.damage > 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_721() {
        return this.damage;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getDamage() {
        return this.damage;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setDamage(int i) {
        this.damage = i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_723() {
        return ItemType.byId[this.itemId].method_464();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void applyDamage(int i, MixinEntity entity) {
        if (!this.method_717()) {
            return;
        }
        this.damage += i;
        if (this.damage > this.method_723()) {
            if (entity instanceof MixinPlayer) {
                ((MixinPlayer) entity).increaseStat(Stats.breakItem[this.itemId], 1);
            }
            --this.count;
            if (this.count < 0) {
                this.count = 0;
            }
            this.damage = 0;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void postHit(MixinLivingEntity entityliving, MixinPlayer entityplayer) {
        boolean flag = ItemType.byId[this.itemId].postHit(this, entityliving, entityplayer);
        if (flag) {
            entityplayer.increaseStat(Stats.useItem[this.itemId], 1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void postMine(int i, int j, int k, int l, MixinPlayer entityplayer) {
        boolean flag = ItemType.byId[this.itemId].postMine(this, i, j, k, l, entityplayer);
        if (flag) {
            entityplayer.increaseStat(Stats.useItem[this.itemId], 1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_707(MixinEntity entity) {
        return ItemType.byId[this.itemId].method_447(entity);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isEffectiveOn(MixinTile block) {
        return ItemType.byId[this.itemId].isEffectiveOn(block);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_700(MixinPlayer entityplayer) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void interactWithEntity(MixinLivingEntity entityliving) {
        ItemType.byId[this.itemId].interactWithEntity(this, entityliving);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance copy() {
        return new MixinItemInstance(this.itemId, this.count, this.damage);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static boolean method_703(MixinItemInstance itemstack, MixinItemInstance itemstack1) {
        if (itemstack == null && itemstack1 == null) {
            return true;
        }
        if (itemstack == null || itemstack1 == null) {
            return false;
        }
        return itemstack.method_718(itemstack1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private boolean method_718(MixinItemInstance itemstack) {
        if (this.count != itemstack.count) {
            return false;
        }
        if (this.itemId != itemstack.itemId) {
            return false;
        }
        return this.damage == itemstack.damage;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isEqualIgnoreFlags(MixinItemInstance itemstack) {
        return this.itemId == itemstack.itemId && this.damage == itemstack.damage;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String getTranslationKey() {
        return ItemType.byId[this.itemId].getTranslationKey(this);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static MixinItemInstance copy(MixinItemInstance itemstack) {
        return itemstack != null ? itemstack.copy() : null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String toString() {
        return this.count + "x" + ItemType.byId[this.itemId].getTranslationKey() + "@" + this.damage;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void inventoryTick(MixinLevel world, MixinEntity entity, int i, boolean flag) {
        if (this.cooldown > 0) {
            --this.cooldown;
        }
        ItemType.byId[this.itemId].inventoryTick(this, world, entity, i, flag);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onCrafted(MixinLevel world, MixinPlayer entityplayer) {
        entityplayer.increaseStat(Stats.field_809[this.itemId], this.count);
        ItemType.byId[this.itemId].method_461(this, world, entityplayer);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isItemEqualIgnoreDamage(MixinItemInstance itemstack) {
        return this.itemId == itemstack.itemId && this.count == itemstack.count && this.damage == itemstack.damage;
    }
}
