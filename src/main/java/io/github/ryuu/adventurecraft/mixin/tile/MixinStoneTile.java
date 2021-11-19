package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.tile.StoneTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

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
