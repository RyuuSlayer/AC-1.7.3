package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.level.Level;
import net.minecraft.tile.FancyTile;
import net.minecraft.tile.LeavesTile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(LeavesTile.class)
public abstract class MixinLeavesTile extends FancyTile {

    protected MixinLeavesTile(int i, int j, Material arg, boolean flag) {
        super(i, j, arg, flag);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
    }
}
