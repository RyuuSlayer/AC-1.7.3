package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.Level;

public interface IBlockColor {

    public void incrementColor(MixinLevel var1, int var2, int var3, int var4);
}
