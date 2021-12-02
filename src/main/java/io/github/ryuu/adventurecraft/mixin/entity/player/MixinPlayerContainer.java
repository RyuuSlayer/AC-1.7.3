package io.github.ryuu.adventurecraft.mixin.entity.player;

import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.container.Container;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerContainer.class)
public abstract class MixinPlayerContainer extends Container {

    @Redirect(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;Z)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerContainer;addSlot(Lnet/minecraft/container/slot/Slot;)V",
            ordinal = 0))
    private void initResultSlot(PlayerContainer instance, Slot slot) {
        ExLevelProperties exProps = (ExLevelProperties) AccessMinecraft.getInstance().level.getProperties();
        if (exProps.getAllowsInventoryCrafting()) {
            slot.y = 52;
            this.addSlot(slot);
        }
    }

    @Redirect(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;Z)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerContainer;addSlot(Lnet/minecraft/container/slot/Slot;)V",
            ordinal = 1))
    private void initCraftingSlot(PlayerContainer instance, Slot slot) {
        ExLevelProperties exProps = (ExLevelProperties) AccessMinecraft.getInstance().level.getProperties();
        if (exProps.getAllowsInventoryCrafting()) {
            slot.y += 16;
            this.addSlot(slot);
        }
    }
}
