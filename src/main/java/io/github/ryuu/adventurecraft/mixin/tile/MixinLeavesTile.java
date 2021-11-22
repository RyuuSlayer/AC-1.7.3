package io.github.ryuu.adventurecraft.mixin.tile;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LeavesTile.class)
public class MixinLeavesTile extends FancyTile {

    @Shadow()
    private int field_1172;

    int[] field_1171;

    protected MixinLeavesTile(int id, int meta) {
        super(id, meta, Material.LEAVES, false);
        this.field_1172 = meta;
        this.setTicksRandomly(true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_1589(int i) {
        if ((i & 1) == 1) {
            return FoliageColour.method_1079();
        }
        if ((i & 2) == 2) {
            return FoliageColour.method_1082();
        }
        return FoliageColour.method_1083();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTint(TileView iblockaccess, int i, int j, int k) {
        int l = iblockaccess.getTileMeta(i, j, k);
        if ((l & 1) == 1) {
            return FoliageColour.method_1079();
        }
        if ((l & 2) == 2) {
            return FoliageColour.method_1082();
        }
        iblockaccess.getBiomeSource().getBiomes(i, k, 1, 1);
        double d = iblockaccess.getBiomeSource().temperatureNoises[0];
        double d1 = iblockaccess.getBiomeSource().rainfallNoises[0];
        return FoliageColour.getColour(d, d1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onTileRemoved(Level level, int x, int y, int z) {
        int l = 1;
        int i1 = l + 1;
        if (level.isRegionLoaded(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
            for (int j1 = -l; j1 <= l; ++j1) {
                for (int k1 = -l; k1 <= l; ++k1) {
                    for (int l1 = -l; l1 <= l; ++l1) {
                        int i2 = level.getTileId(x + j1, y + k1, z + l1);
                        if (i2 != Tile.LEAVES.id)
                            continue;
                        int j2 = level.getTileMeta(x + j1, y + k1, z + l1);
                        level.method_223(x + j1, y + k1, z + l1, j2 | 8);
                    }
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_990(Level world, int i, int j, int k) {
        this.drop(world, i, j, k, world.getTileMeta(i, j, k));
        world.setTile(i, j, k, 0);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getDropCount(Random rand) {
        return rand.nextInt(20) != 0 ? 0 : 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void afterBreak(Level world, Player entityplayer, int i, int j, int k, int l) {
        if (!world.isClient && entityplayer.getHeldItem() != null && entityplayer.getHeldItem().itemId == ItemType.shears.id) {
            entityplayer.increaseStat(Stats.mineBlock[this.id], 1);
            this.dropItem(world, i, j, k, new ItemInstance(Tile.LEAVES.id, 1, l & 3));
        } else {
            super.afterBreak(world, entityplayer, i, j, k, l);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected int getDropMeta(int i) {
        return i & 3;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTextureForSide(int side, int meta) {
        if ((meta & 3) == 1) {
            return this.tex + 80;
        }
        return this.tex;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setFastGraphics(boolean fast) {
        this.fastGraphics = fast;
        this.tex = this.field_1172 + (fast ? 0 : 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1560(Level world, int i, int j, int k, Entity entity) {
        super.method_1560(world, i, j, k, entity);
    }
}
