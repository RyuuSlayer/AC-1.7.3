package io.github.ryuu.adventurecraft.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity extends Entity {

    public MixinItemEntity(Level arg) {
        super(arg);
    }

    @Inject(method = "<init>(Lnet/minecraft/level/Level;DDDLnet/minecraft/item/ItemInstance;)V", at = @At("TAIL"))
    private void removeIfMissingOnInit(Level d, double d1, double d2, double arg1, ItemInstance par5, CallbackInfo ci) {
        if (ItemType.byId[par5.itemId] == null) {
            this.remove();
        }
    }
}
