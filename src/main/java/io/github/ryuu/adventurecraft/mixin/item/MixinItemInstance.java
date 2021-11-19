package io.github.ryuu.adventurecraft.mixin.item;

import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.stat.Stats;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;

public final class MixinItemInstance {
    public int count = 0;
    public int cooldown;
    public int itemId;
    private int damage;
    public int timeLeft;
    public boolean isReloading = false;
    public boolean justReloaded = false;

    public MixinItemInstance(Tile tile) {
        this(tile, 1);
    }

    public MixinItemInstance(Tile tile, int count) {
        this(tile.id, count, 0);
    }

    public MixinItemInstance(Tile tile, int count, int damage) {
        this(tile.id, count, damage);
    }

    public MixinItemInstance(ItemType itemType) {
        this(itemType.id, 1, 0);
    }

    public MixinItemInstance(ItemType itemType, int count) {
        this(itemType.id, count, 0);
    }

    public MixinItemInstance(ItemType itemType, int count, int damage) {
        this(itemType.id, count, damage);
    }

    public MixinItemInstance(int id, int count, int damage) {
        this.itemId = id;
        this.count = count;
        this.damage = damage;
    }

    public MixinItemInstance(CompoundTag tag) {
        this.fromTag(tag);
    }

    public ItemInstance split(int countToTake) {
        this.count -= countToTake;
        return new ItemInstance(this.itemId, countToTake, this.damage);
    }

    public ItemType getType() {
        return ItemType.byId[this.itemId];
    }

    public int getTexturePosition() {
        return this.getType().getTexturePosition(this);
    }

    public boolean useOnTile(Player entityplayer, Level world, int x, int y, int z, int l) {
        boolean flag = this.getType().useOnTile(this, entityplayer, world, x, y, z, l);
        if (flag) {
            entityplayer.increaseStat(Stats.useItem[this.itemId], 1);
        }
        return flag;
    }

    public boolean useItemLeftClick(Player entityplayer, Level world, int i, int j, int k, int l) {
        return this.getType().onItemUseLeftClick(this, entityplayer, world, i, j, k, l);
    }

    public float method_708(Tile block) {
        return this.getType().method_438(this, block);
    }

    public ItemInstance use(Level world, Player entityplayer) {
        return this.getType().use(this, world, entityplayer);
    }

    public CompoundTag toTag(CompoundTag tag) {
        tag.put("id", (short)this.itemId);
        tag.put("Count", this.count);
        tag.put("Damage", (short)this.damage);
        return tag;
    }

    public void fromTag(CompoundTag tag) {
        this.itemId = tag.getShort("id");
        this.count = tag.getInt("Count");
        this.damage = tag.getShort("Damage");
        if (this.itemId == Items.boomerang.id) {
            this.damage = 0;
        }
    }

    public int method_709() {
        return this.getType().method_459();
    }

    public boolean method_715() {
        return this.method_709() > 1 && (!this.method_717() || !this.method_720());
    }

    public boolean method_717() {
        return ItemType.byId[this.itemId].method_464() > 0;
    }

    public boolean method_719() {
        return ItemType.byId[this.itemId].method_462();
    }

    public boolean method_720() {
        return this.method_717() && this.damage > 0;
    }

    public int method_721() {
        return this.damage;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int i) {
        this.damage = i;
    }

    public int method_723() {
        return ItemType.byId[this.itemId].method_464();
    }

    public void applyDamage(int i, Entity entity) {
        if (!this.method_717()) {
            return;
        }
        this.damage += i;
        if (this.damage > this.method_723()) {
            if (entity instanceof Player) {
                ((Player)entity).increaseStat(Stats.breakItem[this.itemId], 1);
            }
            --this.count;
            if (this.count < 0) {
                this.count = 0;
            }
            this.damage = 0;
        }
    }

    public void postHit(LivingEntity entityliving, Player entityplayer) {
        boolean flag = ItemType.byId[this.itemId].postHit(this, entityliving, entityplayer);
        if (flag) {
            entityplayer.increaseStat(Stats.useItem[this.itemId], 1);
        }
    }

    public void postMine(int i, int j, int k, int l, Player entityplayer) {
        boolean flag = ItemType.byId[this.itemId].postMine(this, i, j, k, l, entityplayer);
        if (flag) {
            entityplayer.increaseStat(Stats.useItem[this.itemId], 1);
        }
    }

    public int method_707(Entity entity) {
        return ItemType.byId[this.itemId].method_447(entity);
    }

    public boolean isEffectiveOn(Tile block) {
        return ItemType.byId[this.itemId].isEffectiveOn(block);
    }

    public void method_700(Player entityplayer) {
    }

    public void interactWithEntity(LivingEntity entityliving) {
        ItemType.byId[this.itemId].interactWithEntity(this, entityliving);
    }

    public ItemInstance copy() {
        return new ItemInstance(this.itemId, this.count, this.damage);
    }

    public static boolean method_703(ItemInstance itemstack, ItemInstance itemstack1) {
        if (itemstack == null && itemstack1 == null) {
            return true;
        }
        if (itemstack == null || itemstack1 == null) {
            return false;
        }
        return itemstack.method_718(itemstack1);
    }

    private boolean method_718(ItemInstance itemstack) {
        if (this.count != itemstack.count) {
            return false;
        }
        if (this.itemId != itemstack.itemId) {
            return false;
        }
        return this.damage == itemstack.damage;
    }

    public boolean isEqualIgnoreFlags(ItemInstance itemstack) {
        return this.itemId == itemstack.itemId && this.damage == itemstack.damage;
    }

    public String getTranslationKey() {
        return ItemType.byId[this.itemId].getTranslationKey(this);
    }

    public static ItemInstance copy(ItemInstance itemstack) {
        return itemstack != null ? itemstack.copy() : null;
    }

    public String toString() {
        return this.count + "x" + ItemType.byId[this.itemId].getTranslationKey() + "@" + this.damage;
    }

    public void inventoryTick(Level world, Entity entity, int i, boolean flag) {
        if (this.cooldown > 0) {
            --this.cooldown;
        }
        ItemType.byId[this.itemId].inventoryTick(this, world, entity, i, flag);
    }

    public void onCrafted(Level world, Player entityplayer) {
        entityplayer.increaseStat(Stats.field_809[this.itemId], this.count);
        ItemType.byId[this.itemId].method_461(this, world, entityplayer);
    }

    public boolean isItemEqualIgnoreDamage(ItemInstance itemstack) {
        return this.itemId == itemstack.itemId && this.count == itemstack.count && this.damage == itemstack.damage;
    }
}