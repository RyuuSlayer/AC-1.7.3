package io.github.ryuu.adventurecraft.mixin.item;

import io.github.ryuu.adventurecraft.extensions.items.ExItemInstance;
import io.github.ryuu.adventurecraft.extensions.items.ExItemType;
import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInstance.class)
public abstract class MixinItemInstance implements ExItemInstance {

    @Shadow
    public int count;
    @Shadow
    public int itemId;
    @Shadow
    private int damage;

    @Shadow
    public abstract ItemType getType();

    public boolean useItemLeftClick(Player entityplayer, Level world, int i, int j, int k, int l) {
        return ((ExItemType)this.getType()).onItemUseLeftClick((ItemInstance) (Object) this, entityplayer, world, i, j, k, l);
    }

    @Inject(method = "fromTag", at = @At("TAIL"))
    public void fromTag(CompoundTag par1, CallbackInfo ci) {
        // FIXME: wow this is hacky
        if (this.itemId == Items.boomerang.id) {
            this.damage = 0;
        }
    }
}
