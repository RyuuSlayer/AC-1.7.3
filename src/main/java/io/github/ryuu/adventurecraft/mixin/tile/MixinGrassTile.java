package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.colour.GrassColour;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(GrassTile.class)
public class MixinGrassTile extends Tile implements IBlockColor {

    protected MixinGrassTile(int id) {
        super(id, Material.ORGANIC);
        this.tex = 3;
        this.setTicksRandomly(true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        if (l == 1) {
            int metadata = iblockaccess.getTileMeta(i, j, k);
            if (metadata == 0) {
                return 0;
            }
            return 232 + metadata - 1;
        }
        if (l == 0) {
            return 2;
        }
        Material material = iblockaccess.getMaterial(i, j + 1, k);
        return material != Material.SNOW && material != Material.SNOW_BLOCK ? 3 : 68;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTextureForSide(int side, int meta) {
        if (meta == 0) {
            return 0;
        }
        return 232 + meta - 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTint(TileView iblockaccess, int i, int j, int k) {
        iblockaccess.getBiomeSource().getBiomes(i, k, 1, 1);
        double d = iblockaccess.getBiomeSource().temperatureNoises[0];
        double d1 = iblockaccess.getBiomeSource().rainfallNoises[0];
        return GrassColour.getColour(d, d1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        if (level.isClient) {
            return;
        }
        if (level.getLightLevel(x, y + 1, z) < 4 && Tile.field_1941[level.getTileId(x, y + 1, z)] > 2) {
            if (rand.nextInt(4) != 0) {
                return;
            }
            Chunk.isNotPopulating = false;
            level.setTile(x, y, z, Tile.DIRT.id);
            Chunk.isNotPopulating = true;
        } else if (level.getLightLevel(x, y + 1, z) >= 9) {
            int l = x + rand.nextInt(3) - 1;
            int i1 = y + rand.nextInt(5) - 3;
            int j1 = z + rand.nextInt(3) - 1;
            int k1 = level.getTileId(l, i1 + 1, j1);
            if (level.getTileId(l, i1, j1) == Tile.DIRT.id && level.getLightLevel(l, i1 + 1, j1) >= 4 && Tile.field_1941[k1] <= 2) {
                Chunk.isNotPopulating = false;
                level.setTile(l, i1, j1, Tile.GRASS.id);
                Chunk.isNotPopulating = true;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getDropId(int meta, Random rand) {
        return Tile.DIRT.getDropId(0, rand);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_1621() {
        if (Minecraft.minecraftInstance.options.grass3d) {
            return 30;
        }
        return super.method_1621();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 1) % subTypes[this.id]);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float grassMultiplier(int metadata) {
        switch (metadata) {
            case 2: {
                return 0.62f;
            }
            case 3: {
                return 0.85f;
            }
            case 4: {
                return -1.0f;
            }
        }
        return 1.0f;
    }
}
