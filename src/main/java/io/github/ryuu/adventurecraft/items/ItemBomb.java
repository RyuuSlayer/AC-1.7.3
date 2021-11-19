package io.github.ryuu.adventurecraft.items;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemBomb extends ItemType {

    public ItemBomb(int id) {
        super(id);
        this.setTexturePosition(150);
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        --item.count;
        level.spawnEntity(new EntityBomb(level, player));
        return item;
    }
}
