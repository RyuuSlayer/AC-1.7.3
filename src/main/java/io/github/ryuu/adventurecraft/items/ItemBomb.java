package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemBomb extends MixinItemType {

    public ItemBomb(int id) {
        super(id);
        this.setTexturePosition(150);
    }

    @Override
    public MixinItemInstance use(MixinItemInstance item, MixinLevel level, MixinPlayer player) {
        --item.count;
        level.spawnEntity(new EntityBomb(level, player));
        return item;
    }
}
