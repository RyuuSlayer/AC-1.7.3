package io.github.ryuu.adventurecraft.mixin.entity.monster;

import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import net.minecraft.entity.monster.Zombie;
import net.minecraft.entity.monster.ZombiePigman;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombiePigman.class)
public abstract class MixinZombiePigman extends Zombie {

    public MixinZombiePigman(Level arg) {
        super(arg);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Level level, CallbackInfo ci) {
        ((ExLivingEntity) this).setHeldItem(new ItemInstance(ItemType.swordGold, 1));
    }
}
