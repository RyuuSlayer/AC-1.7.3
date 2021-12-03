package io.github.ryuu.adventurecraft.mixin.client;

import io.github.ryuu.adventurecraft.extensions.client.ExClientInteractionManager;
import net.minecraft.client.ClientInteractionManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientInteractionManager.class)
public abstract class MixinClientInteractionManager implements ExClientInteractionManager {

    public int destroyExtraWidth;

    public int destroyExtraDepth;

    @Override
    public int getDestroyExtraWidth() {
        return destroyExtraWidth;
    }

    @Override
    public void setDestroyExtraWidth(int destroyExtraWidth) {
        this.destroyExtraWidth = destroyExtraWidth;
    }

    @Override
    public int getDestroyExtraDepth() {
        return destroyExtraDepth;
    }

    @Override
    public void setDestroyExtraDepth(int destroyExtraDepth) {
        this.destroyExtraDepth = destroyExtraDepth;
    }
}
