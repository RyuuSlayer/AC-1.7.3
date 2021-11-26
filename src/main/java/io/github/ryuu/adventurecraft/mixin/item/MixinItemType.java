package io.github.ryuu.adventurecraft.mixin.item;

import io.github.ryuu.adventurecraft.extensions.items.ExItemType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemType.class)
public abstract class MixinItemType implements ExItemType {

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
}
