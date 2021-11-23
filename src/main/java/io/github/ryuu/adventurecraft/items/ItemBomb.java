package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityBomb;
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
