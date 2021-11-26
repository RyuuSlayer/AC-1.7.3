package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.extensions.items.ExItemType;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;

public class ItemLantern extends ItemType implements ExItemType {

    public ItemLantern(int id) {
        super(id);
        this.maxStackSize = 1;
    }

    @Override
    public boolean isLighting(ItemInstance item) {
        if (item.getDamage() < item.method_723()) {
            item.setDamage(item.getDamage() + 1);
            return true;
        }
        if (item.getDamage() == item.method_723() &&
                AccessMinecraft.getInstance().player.inventory.decreaseAmountOfItem(Items.oil.id)) {
            item.setDamage(0);
            return true;
        }
        return false;
    }
}
