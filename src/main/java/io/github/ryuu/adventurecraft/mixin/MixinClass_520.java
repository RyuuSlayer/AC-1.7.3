package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.extensions.client.ExClientInteractionManager;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.class_520;
import net.minecraft.client.ClientInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemInstance;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(class_520.class)
public abstract class MixinClass_520 extends ClientInteractionManager {

    public MixinClass_520(Minecraft minecraft) {
        super(minecraft);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public boolean method_1716(int i, int j, int k, int side) {
        ((ExLevel) this.minecraft.level).getUndoStack().startRecording();
        boolean flag = false;
        int destroyExtraWidth = ((ExClientInteractionManager) this).getDestroyExtraWidth();
        int destroyExtraDepth = ((ExClientInteractionManager) this).getDestroyExtraDepth();
        for (int x = -destroyExtraWidth; x <= destroyExtraWidth; ++x) {
            for (int y = -destroyExtraWidth; y <= destroyExtraWidth; ++y) {
                for (int z = 0; z <= destroyExtraDepth; ++z) {
                    if (side == 0) {
                        flag |= this._sendBlockRemoved(i + x, j + z, k + y, side);
                        continue;
                    }
                    if (side == 1) {
                        flag |= this._sendBlockRemoved(i + x, j - z, k + y, side);
                        continue;
                    }
                    if (side == 2) {
                        flag |= this._sendBlockRemoved(i + x, j + y, k + z, side);
                        continue;
                    }
                    if (side == 3) {
                        flag |= this._sendBlockRemoved(i + x, j + y, k - z, side);
                        continue;
                    }
                    if (side == 4) {
                        flag |= this._sendBlockRemoved(i + z, j + y, k + x, side);
                        continue;
                    }
                    if (side != 5) continue;
                    flag |= this._sendBlockRemoved(i - z, j + y, k + x, side);
                }
            }
        }
        ((ExLevel) this.minecraft.level).getUndoStack().stopRecording();
        return flag;
    }

    private boolean _sendBlockRemoved(int i, int j, int k, int l) {
        int i1 = this.minecraft.level.getTileId(i, j, k);
        if (i1 == 0) {
            return false;
        }
        int j1 = this.minecraft.level.getTileMeta(i, j, k);
        boolean flag = super.method_1716(i, j, k, l);
        ItemInstance itemstack = this.minecraft.player.getHeldItem();
        boolean flag1 = this.minecraft.player.method_514(Tile.BY_ID[i1]);
        if (itemstack != null) {
            itemstack.postMine(i1, i, j, k, this.minecraft.player);
            if (itemstack.count == 0) {
                itemstack.method_700(this.minecraft.player);
                this.minecraft.player.method_503();
            }
        }
        if (flag && flag1) {
            Tile.BY_ID[i1].afterBreak(this.minecraft.level, this.minecraft.player, i, j, k, j1);
        }
        return flag;
    }

    @Redirect(method = "method_1707", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/class_520;method_1716(IIII)Z"))
    private boolean method_1716OnlyWhenDebug(class_520 instance, int i, int j, int k, int l) {
        if (DebugMode.active) {
            instance.method_1716(i, j, k, l);
        }
        return false;
    }

    @Inject(method = "method_1721", at = @At("HEAD"), cancellable = true)
    private void method_1721OnlyWhenDebug(int i, int j, int k, int l, CallbackInfo ci) {
        if (!DebugMode.active) {
            ci.cancel();
        }
    }

    @Inject(method = "method_1715", at = @At("HEAD"), cancellable = true)
    private void method_1715(CallbackInfoReturnable<Float> cir) {
        if (this.minecraft.player.getHeldItem() != null && this.minecraft.player.getHeldItem().itemId == Items.quill.id) {
            cir.setReturnValue(500.0f);
        }
        if (DebugMode.active) {
            cir.setReturnValue((float) DebugMode.reachDistance);
        }
    }
}
