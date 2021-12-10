package io.github.ryuu.adventurecraft.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ResourceDownloadThread;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Minecraft.class)
public interface AccessMinecraft {

    @Accessor
    static Minecraft getInstance() {
        throw new AssertionError();
    }

    @Accessor
    ResourceDownloadThread getResourceDownloadThread();
}
