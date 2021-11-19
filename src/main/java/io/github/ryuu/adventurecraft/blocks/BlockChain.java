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

public class BlockChain extends BlockRope {

    protected BlockChain(int i, int j) {
        super(i, j);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        return this.tex + side % 2 + meta / 3 * 2;
    }
}
