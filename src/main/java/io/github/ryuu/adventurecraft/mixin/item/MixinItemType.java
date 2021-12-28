package io.github.ryuu.adventurecraft.mixin.item;

import io.github.ryuu.adventurecraft.extensions.items.ExItemType;
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
public abstract class MixinItemType implements AccessItemType, ExItemType {

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
    public boolean canDecrementDamage() {
        return false;
    }

    @Override
    public int getItemUseDelay() {
        return 5;
    }

    @Override
    public boolean isLighting(ItemInstance itemStack) {
        return false;
    }

    @Override
    public boolean isMuzzleFlash(ItemInstance itemStack) {
        return false;
    }

    @Override
    public boolean onItemUseLeftClick(ItemInstance instance, Player player, Level world, int i, int j, int k, int l) {
        return false;
    }

    @Override
    public void onItemLeftClick(ItemInstance itemstack, Level world, Player entityplayer) {
    }

    @Override
    public boolean mainActionLeftClick() {
        return false;
    }

    @Override
    public void onAddToSlot(Player entityPlayer, int slot, int damage) {
    }

    @Override
    public void onRemovedFromSlot(Player entityPlayer, int slot, int damage) {
    }
}
