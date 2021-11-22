package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.entities.EntityBomb;

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
