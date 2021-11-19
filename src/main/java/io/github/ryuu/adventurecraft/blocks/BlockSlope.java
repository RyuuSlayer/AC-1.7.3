package io.github.ryuu.adventurecraft.blocks;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.tile.Tile;

public class BlockSlope extends BlockStairMulti {

    protected BlockSlope(int i, Tile block, int textureID) {
        super(i, block, textureID);
    }

    @Override
    public int method_1621() {
        return 38;
    }
}
