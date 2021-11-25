package io.github.ryuu.adventurecraft.mixin.client.gui.screen.container;

import io.github.ryuu.adventurecraft.accessors.entity.player.PlayerAccessor;
import net.minecraft.client.gui.screen.container.ContainerScreen;
import net.minecraft.client.gui.screen.container.PlayerInventoryScreen;
import net.minecraft.container.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventoryScreen.class)
public abstract class MixinPlayerInventoryScreen extends ContainerScreen {
    public MixinPlayerInventoryScreen(Container arg) {
        super(arg);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void renderForeground() {
    }


    @Inject(method = "renderContainerBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/container/PlayerInventoryScreen;blit(IIIIII)V", shift = At.Shift.AFTER))
    private void renderContainerBackground(float par1, CallbackInfo ci) {
        int width = (this.width - this.containerWidth) / 2;
        int height = (this.height - this.containerHeight) / 2;
        int heartPiece = this.minecraft.textureManager.getTextureId("/assets/adventurecraft/gui/heartPiece.png");
        this.minecraft.textureManager.bindTexture(heartPiece);
        this.blit(width + 89, height + 6, ((PlayerAccessor) this.minecraft.player).getHeartPieces() * 32, 0, 32, 32);
    }

    @ModifyArg(method = "renderContainerBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;getTextureId(Ljava/lang/String;)I"))
    private String renderContainerBackground(String url) {
        return "/assets/adventurecraft/gui/inventory.png";
    }
}
