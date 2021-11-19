package io.github.ryuu.adventurecraft.mixin.item.tool;

import net.minecraft.item.ItemType;
import net.minecraft.item.tool.BowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BowItem.class)
public class MixinBowItem extends MixinItemType {

    public MixinBowItem(int id) {
        super(id);
        this.maxStackSize = 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public MixinItemInstance use(MixinItemInstance item, MixinLevel level, MixinPlayer player) {
        MixinItemInstance curItem = player.inventory.getHeldItem();
        MixinItemInstance offItem = player.inventory.getOffhandItem();
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
                level.spawnEntity(new MixinArrow(level, player));
            }
        }
        return item;
    }
}
