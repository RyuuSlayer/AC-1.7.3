package io.github.ryuu.adventurecraft.mixin.level;

import io.github.ryuu.adventurecraft.blocks.BlockStairMulti;
import io.github.ryuu.adventurecraft.util.LightCache;
import io.github.ryuu.adventurecraft.util.PlayerTorch;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldPopulationRegion.class)
public class MixinWorldPopulationRegion implements TileView {

    @Shadow()
    private final int field_166;

    private final int field_167;

    private final Chunk[][] chunks;

    private final Level level;

    public MixinWorldPopulationRegion(Level world, int i, int j, int k, int l, int i1, int j1) {
        this.level = world;
        this.field_166 = i >> 4;
        this.field_167 = k >> 4;
        int k1 = l >> 4;
        int l1 = j1 >> 4;
        this.chunks = new Chunk[k1 - this.field_166 + 1][l1 - this.field_167 + 1];
        for (int i2 = this.field_166; i2 <= k1; ++i2) {
            for (int j2 = this.field_167; j2 <= l1; ++j2) {
                this.chunks[i2 - this.field_166][j2 - this.field_167] = world.getChunkFromCache(i2, j2);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTileId(int x, int y, int z) {
        if (y < 0) {
            return 0;
        }
        if (y >= 128) {
            return 0;
        }
        int l = (x >> 4) - this.field_166;
        int i1 = (z >> 4) - this.field_167;
        if (l < 0 || l >= this.chunks.length || i1 < 0 || i1 >= this.chunks[l].length) {
            return 0;
        }
        Chunk chunk = this.chunks[l][i1];
        if (chunk == null) {
            return 0;
        }
        return chunk.getTileId(x & 0xF, y, z & 0xF);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public TileEntity getTileEntity(int i, int j, int k) {
        int l = (i >> 4) - this.field_166;
        int i1 = (k >> 4) - this.field_167;
        return this.chunks[l][i1].getTileEntity(i & 0xF, j, k & 0xF);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public float method_1784(int i, int j, int k, int l) {
        float torchLight;
        float light = LightCache.cache.getLightValue(i, j, k);
        if (light >= 0.0f) {
            return light;
        }
        int lightValue = this.method_143(i, j, k);
        if (lightValue < l) {
            lightValue = l;
        }
        if ((float) lightValue < (torchLight = PlayerTorch.getTorchLight(this.level, i, j, k))) {
            int floorValue = (int) Math.floor(torchLight);
            if (floorValue == 15) {
                return this.level.dimension.field_2178[15];
            }
            int ceilValue = (int) Math.ceil(torchLight);
            float lerpValue = torchLight - (float) floorValue;
            return (1.0f - lerpValue) * this.level.dimension.field_2178[floorValue] + lerpValue * this.level.dimension.field_2178[ceilValue];
        }
        light = this.level.dimension.field_2178[lightValue];
        LightCache.cache.setLightValue(i, j, k, light);
        return light;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public float getBrightness(int i, int j, int k) {
        float torchLight;
        float l = LightCache.cache.getLightValue(i, j, k);
        if (l >= 0.0f) {
            return l;
        }
        int lightValue = this.method_143(i, j, k);
        if ((float) lightValue < (torchLight = PlayerTorch.getTorchLight(this.level, i, j, k))) {
            int floorValue = (int) Math.floor(torchLight);
            if (floorValue == 15) {
                return this.level.dimension.field_2178[15];
            }
            int ceilValue = (int) Math.ceil(torchLight);
            float lerpValue = torchLight - (float) floorValue;
            return (1.0f - lerpValue) * this.level.dimension.field_2178[floorValue] + lerpValue * this.level.dimension.field_2178[ceilValue];
        }
        l = this.level.dimension.field_2178[lightValue];
        LightCache.cache.setLightValue(i, j, k, l);
        return l;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_143(int i, int j, int k) {
        return this.method_142(i, j, k, true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_142(int i, int j, int k, boolean flag) {
        int l;
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000) {
            return 15;
        }
        if (flag && (l = this.getTileId(i, j, k)) != 0 && (l == Tile.STONE_SLAB.id || l == Tile.FARMLAND.id || l == Tile.STAIRS_WOOD.id || l == Tile.STAIRS_STONE.id || Tile.BY_ID[l] instanceof BlockStairMulti)) {
            int k1 = this.method_142(i, j + 1, k, false);
            int i2 = this.method_142(i + 1, j, k, false);
            int j2 = this.method_142(i - 1, j, k, false);
            int k2 = this.method_142(i, j, k + 1, false);
            int l2 = this.method_142(i, j, k - 1, false);
            if (i2 > k1) {
                k1 = i2;
            }
            if (j2 > k1) {
                k1 = j2;
            }
            if (k2 > k1) {
                k1 = k2;
            }
            if (l2 > k1) {
                k1 = l2;
            }
            return k1;
        }
        if (j < 0) {
            return 0;
        }
        if (j >= 128) {
            int i1 = 15 - this.level.field_202;
            if (i1 < 0) {
                i1 = 0;
            }
            return i1;
        }
        int j1 = (i >> 4) - this.field_166;
        int l1 = (k >> 4) - this.field_167;
        return this.chunks[j1][l1].getAbsoluteLight(i & 0xF, j, k & 0xF, this.level.field_202);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTileMeta(int x, int y, int z) {
        if (y < 0) {
            return 0;
        }
        if (y >= 128) {
            return 0;
        }
        int l = (x >> 4) - this.field_166;
        int i1 = (z >> 4) - this.field_167;
        return this.chunks[l][i1].getMetadata(x & 0xF, y, z & 0xF);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Material getMaterial(int x, int y, int z) {
        int l = this.getTileId(x, y, z);
        if (l == 0) {
            return Material.AIR;
        }
        return Tile.BY_ID[l].material;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isFullOpaque(int i, int j, int k) {
        Tile block = Tile.BY_ID[this.getTileId(i, j, k)];
        if (block == null) {
            return false;
        }
        return block.isFullOpaque();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canSuffocate(int i, int j, int k) {
        Tile block = Tile.BY_ID[this.getTileId(i, j, k)];
        if (block == null) {
            return false;
        }
        return block.material.blocksMovement() && block.isFullCube();
    }
}
