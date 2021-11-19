/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 */
package io.github.ryuu.adventurecraft.mixin.item.tool;

import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.tool.BowItem;
import net.minecraft.level.Level;
import io.github.ryuu.adventurecraft.mixin.item.MixinPlayer;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemType;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinArrow;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

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
