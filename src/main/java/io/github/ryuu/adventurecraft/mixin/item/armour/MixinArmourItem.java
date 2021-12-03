package io.github.ryuu.adventurecraft.mixin.item.armour;

import net.minecraft.item.armour.ArmourItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmourItem.class)
public abstract class MixinArmourItem {

    @Final
    @Shadow
    private static int[] field_2086;

    public float bl;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(int id, int j, int k, int slot, CallbackInfo ci) {
        float reduction = (j + 1.0f) / 4.0f;
        this.bl = reduction * field_2086[slot];
    }
}
