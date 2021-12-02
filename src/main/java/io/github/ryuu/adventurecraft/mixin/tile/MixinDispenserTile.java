package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.entities.EntityArrowBomb;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.DispenserTile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(DispenserTile.class)
public abstract class MixinDispenserTile extends TileWithEntity {

    protected MixinDispenserTile(int id) {
        super(id, Material.STONE);
        this.tex = 45;
    }

    @Inject(method = "activate", at = @At("HEAD"), cancellable = true)
    private void disableDispenserScreen(Level i, int j, int k, int arg1, Player par5, CallbackInfoReturnable<Boolean> cir) {
        if (!DebugMode.active) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "method_1774", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;playLevelEvent(IIIII)V",
            ordinal = 5,
            shift = At.Shift.BEFORE))
    private void dispenseBombArrow(Level world, int i, int j, int k, Random random, CallbackInfo ci,
                                   ItemInstance var12, double var9, double var10, double var13, double var15, double var17) {
        if (var12.itemId == Items.bombArow.id) {
            EntityArrowBomb entityarrow = new EntityArrowBomb(world, var13, var15, var17);
            entityarrow.method_1291(var9, 0.1f, var10, 1.1f, 6.0f);
            world.spawnEntity(entityarrow);
            world.playLevelEvent(1002, i, j, k, 0);
        }
    }
}
