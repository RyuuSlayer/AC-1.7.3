package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.LightType;
import net.minecraft.level.TileView;
import net.minecraft.tile.IceTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TranslucentTile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(IceTile.class)
public class MixinIceTile extends TranslucentTile {

    public MixinIceTile(int i, int j) {
        super(i, j, Material.ICE, false);
        this.field_1901 = 0.98f;
        this.setTicksRandomly(true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1618(TileView iblockaccess, int i, int j, int k, int l) {
        return super.method_1618(iblockaccess, i, j, k, 1 - l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void afterBreak(Level world, Player entityplayer, int i, int j, int k, int l) {
        super.afterBreak(world, entityplayer, i, j, k, l);
        Material material = world.getMaterial(i, j - 1, k);
        if (material.blocksMovement() || material.isLiquid()) {
            world.setTile(i, j, k, Tile.FLOWING_WATER.id);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        if (!level.properties.iceMelts) {
            return;
        }
        if (level.getLightLevel(LightType.Block, x, y, z) > 11 - Tile.field_1941[this.id]) {
            this.drop(level, x, y, z, level.getTileMeta(x, y, z));
            level.setTile(x, y, z, Tile.STILL_WATER.id);
        }
    }
}
