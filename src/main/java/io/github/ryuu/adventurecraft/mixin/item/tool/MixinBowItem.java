package io.github.ryuu.adventurecraft.mixin.item.tool;

import io.github.ryuu.adventurecraft.entities.EntityArrowBomb;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayerInventory;
import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.tool.BowItem;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BowItem.class)
public abstract class MixinBowItem extends ItemType {

    public MixinBowItem(int id) {
        super(id);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        ItemInstance curItem = player.inventory.getHeldItem();
        ItemInstance offItem = ((ExPlayerInventory)player.inventory).getOffhandItem();
        if (curItem != null && curItem.itemId == Items.bombArow.id || offItem != null && offItem.itemId == Items.bombArow.id) {
            if (player.inventory.decreaseAmountOfItem(Items.bombArow.id)) {
                level.playSound(player, "random.bow", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
                if (!level.isClient) {
                    level.spawnEntity(new EntityArrowBomb(level, player));
                }
            }
        } else if (player.inventory.decreaseAmountOfItem(ItemType.arrow.id)) {
            level.playSound(player, "random.bow", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
            if (!level.isClient) {
                level.spawnEntity(new Arrow(level, player));
            }
        }
        return item;
    }
}
