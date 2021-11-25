package io.github.ryuu.adventurecraft.mixin.item;

import io.github.ryuu.adventurecraft.accessors.items.ClickableItemType;
import io.github.ryuu.adventurecraft.accessors.items.ItemTypeLightEmitter;
import io.github.ryuu.adventurecraft.accessors.items.ItemTypeMuzzleFlash;
import io.github.ryuu.adventurecraft.accessors.items.ItemTypeSlotChangeNotifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemType.class)
public abstract class MixinItemType implements ClickableItemType, ItemTypeSlotChangeNotifier, ItemTypeLightEmitter, ItemTypeMuzzleFlash {

    @Shadow
    public abstract int getTexturePosition(int i);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Environment(EnvType.CLIENT)
    @Overwrite
    public final int getTexturePosition(ItemInstance item) {
        int damage = 0;
        if (item != null) {
            damage = item.getDamage();
        }
        return this.getTexturePosition(damage);
    }

    @Override
    public boolean onItemUseLeftClick(ItemInstance instance, Player player, Level world, int i, int j, int k, int l) {
        return false;
    }

    @Override
    public boolean isLighting(ItemInstance itemstack) {
        return false;
    }

    @Override
    public boolean isMuzzleFlash(ItemInstance itemstack) {
        return false;
    }

    @Override
    public void onAddToSlot(Player entityPlayer, int slot, int damage) {
    }

    @Override
    public void onRemovedFromSlot(Player entityPlayer, int slot, int damage) {
    }
}
