/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.Random
 */
package io.github.ryuu.adventurecraft.mixin.tile;

import java.util.Random;

import net.minecraft.tile.StoneTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StoneTile.class)
public class MixinStoneTile extends BlockColor {

    public MixinStoneTile(int id, int texture) {
        super(id, texture, Material.STONE);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getDropId(int meta, Random rand) {
        return Tile.COBBLESTONE.id;
    }
}
