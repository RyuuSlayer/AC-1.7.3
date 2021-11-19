package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_61;
import io.github.ryuu.adventurecraft.mixin.item.MixinClass_61;

public interface IEntityPather {

    public MixinClass_61 getCurrentPath();
}
