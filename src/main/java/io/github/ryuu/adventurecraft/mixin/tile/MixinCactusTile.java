package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.level.Level;
import net.minecraft.tile.CactusTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(CactusTile.class)
public abstract class MixinCactusTile extends Tile {

    protected MixinCactusTile(int i, Material arg) {
        super(i, arg);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
    }
}
