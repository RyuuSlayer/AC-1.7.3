package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityBoomerang;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public class ItemBoomerang extends ACItemType {

    public ItemBoomerang(int id) {
        super(id);
        this.setTexturePosition(144);
        this.maxStackSize = 1;
        this.setDurability(0);
        this.method_457(true);
    }

    @Override
    public int getTexturePosition(int damage) {
        if (damage == 0) {
            return this.texturePosition;
        }
        return 165;
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        if (item.getDamage() == 0) {
            level.spawnEntity(new EntityBoomerang(level, player, item));
            item.setDamage(1);
        }
        return item;
    }
}
