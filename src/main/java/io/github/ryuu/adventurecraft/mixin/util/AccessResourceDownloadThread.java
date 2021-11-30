package io.github.ryuu.adventurecraft.mixin.util;

import net.minecraft.client.util.ResourceDownloadThread;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.File;

@Mixin(ResourceDownloadThread.class)
public interface AccessResourceDownloadThread {

    @Invoker
    void invokeMethod_108(File file, String string);
}
