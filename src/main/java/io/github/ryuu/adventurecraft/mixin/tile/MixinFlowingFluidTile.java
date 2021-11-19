package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.tile.FlowingFluidTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(FlowingFluidTile.class)
public class MixinFlowingFluidTile extends MixinFluidTile {

    @Shadow()
    int field_1232 = 0;

    boolean[] field_1233 = new boolean[4];

    int[] field_1234 = new int[4];

    protected MixinFlowingFluidTile(int id, Material material) {
        super(id, material);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1055(MixinLevel world, int i, int j, int k) {
        int l = world.getTileMeta(i, j, k);
        world.setTileWithMetadata(i, j, k, this.id + 1, l);
        world.updateRedstone(i, j, k, i, j, k);
        world.method_243(i, j, k);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onScheduledTick(MixinLevel level, int x, int y, int z, Random rand) {
        if (DebugMode.active) {
            return;
        }
        int l = this.method_1220(level, x, y, z);
        int byte0 = 1;
        if (this.material == Material.LAVA && !level.dimension.field_2176) {
            byte0 = 2;
        }
        boolean flag = true;
        if (l > 0) {
            int i1 = -100;
            this.field_1232 = 0;
            i1 = this.method_1053(level, x - 1, y, z, i1);
            i1 = this.method_1053(level, x + 1, y, z, i1);
            i1 = this.method_1053(level, x, y, z - 1, i1);
            int j1 = (i1 = this.method_1053(level, x, y, z + 1, i1)) + byte0;
            if (j1 >= 8 || i1 < 0) {
                j1 = -1;
            }
            if (this.method_1220(level, x, y + 1, z) >= 0) {
                int l1 = this.method_1220(level, x, y + 1, z);
                j1 = l1 >= 8 ? l1 : l1 + 8;
            }
            if (this.field_1232 >= 2 && this.material == Material.WATER) {
                if (level.getMaterial(x, y - 1, z).isSolid()) {
                    j1 = 0;
                } else if (level.getMaterial(x, y - 1, z) == this.material && level.getTileMeta(x, y, z) == 0) {
                    j1 = 0;
                }
            }
            if (this.material == Material.LAVA && l < 8 && j1 < 8 && j1 > l && rand.nextInt(4) != 0) {
                j1 = l;
                flag = false;
            }
            if (j1 != l) {
                l = j1;
                if (l < 0) {
                    level.setTile(x, y, z, 0);
                } else {
                    level.setTileMeta(x, y, z, l);
                    level.method_216(x, y, z, this.id, this.getTickrate());
                    level.method_244(x, y, z, this.id);
                }
            } else if (flag) {
                this.method_1055(level, x, y, z);
            }
        } else {
            this.method_1055(level, x, y, z);
        }
        if (this.method_1058(level, x, y - 1, z)) {
            if (l >= 8) {
                level.method_201(x, y - 1, z, this.id, l);
            } else {
                level.method_201(x, y - 1, z, this.id, l + 8);
            }
        } else if (l >= 0 && (l == 0 || this.method_1057(level, x, y - 1, z))) {
            boolean[] aflag = this.method_1056(level, x, y, z);
            int k1 = l + byte0;
            if (l >= 8) {
                k1 = 1;
            }
            if (k1 >= 8) {
                return;
            }
            if (aflag[0]) {
                this.method_1054(level, x - 1, y, z, k1);
            }
            if (aflag[1]) {
                this.method_1054(level, x + 1, y, z, k1);
            }
            if (aflag[2]) {
                this.method_1054(level, x, y, z - 1, k1);
            }
            if (aflag[3]) {
                this.method_1054(level, x, y, z + 1, k1);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1054(MixinLevel world, int i, int j, int k, int l) {
        if (this.method_1058(world, i, j, k)) {
            int i1 = world.getTileId(i, j, k);
            if (i1 > 0) {
                if (this.material == Material.LAVA) {
                    this.method_1221(world, i, j, k);
                } else {
                    Tile.BY_ID[i1].drop(world, i, j, k, world.getTileMeta(i, j, k));
                }
            }
            world.method_201(i, j, k, this.id, l);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int method_1052(MixinLevel world, int i, int j, int k, int l, int i1) {
        int j1 = 1000;
        for (int k1 = 0; k1 < 4; ++k1) {
            int k2;
            if (k1 == 0 && i1 == 1 || k1 == 1 && i1 == 0 || k1 == 2 && i1 == 3 || k1 == 3 && i1 == 2) continue;
            int l1 = i;
            int i2 = j;
            int j2 = k;
            if (k1 == 0) {
                --l1;
            }
            if (k1 == 1) {
                ++l1;
            }
            if (k1 == 2) {
                --j2;
            }
            if (k1 == 3) {
                ++j2;
            }
            if (this.method_1057(world, l1, i2, j2) || world.getMaterial(l1, i2, j2) == this.material && world.getTileMeta(l1, i2, j2) == 0)
                continue;
            if (!this.method_1057(world, l1, i2 - 1, j2)) {
                return l;
            }
            if (l >= 4 || (k2 = this.method_1052(world, l1, i2, j2, l + 1, k1)) >= j1) continue;
            j1 = k2;
        }
        return j1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private boolean[] method_1056(MixinLevel world, int i, int j, int k) {
        for (int l = 0; l < 4; ++l) {
            this.field_1234[l] = 1000;
            int j1 = i;
            int i2 = j;
            int j2 = k;
            if (l == 0) {
                --j1;
            }
            if (l == 1) {
                ++j1;
            }
            if (l == 2) {
                --j2;
            }
            if (l == 3) {
                ++j2;
            }
            if (this.method_1057(world, j1, i2, j2) || world.getMaterial(j1, i2, j2) == this.material && world.getTileMeta(j1, i2, j2) == 0)
                continue;
            this.field_1234[l] = !this.method_1057(world, j1, i2 - 1, j2) ? 0 : this.method_1052(world, j1, i2, j2, 1, l);
        }
        int i1 = this.field_1234[0];
        for (int k1 = 1; k1 < 4; ++k1) {
            if (this.field_1234[k1] >= i1) continue;
            i1 = this.field_1234[k1];
        }
        for (int l1 = 0; l1 < 4; ++l1) {
            this.field_1233[l1] = this.field_1234[l1] == i1;
        }
        return this.field_1233;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private boolean method_1057(MixinLevel world, int i, int j, int k) {
        int l = world.getTileId(i, j, k);
        if (l == Tile.DOOR_WOOD.id || l == Tile.DOOR_IRON.id || l == Tile.STANDING_SIGN.id || l == Tile.LADDER.id || l == Tile.REEDS.id) {
            return true;
        }
        if (l == 0) {
            return false;
        }
        Material material = Tile.BY_ID[l].material;
        return material.blocksMovement();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected int method_1053(MixinLevel world, int i, int j, int k, int l) {
        int i1 = this.method_1220(world, i, j, k);
        if (i1 < 0) {
            return l;
        }
        if (i1 == 0) {
            ++this.field_1232;
        }
        if (i1 >= 8) {
            i1 = 0;
        }
        return l >= 0 && i1 >= l ? l : i1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private boolean method_1058(MixinLevel world, int i, int j, int k) {
        Material material = world.getMaterial(i, j, k);
        if (material == this.material) {
            return false;
        }
        if (material == Material.LAVA) {
            return false;
        }
        return !this.method_1057(world, i, j, k);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1611(MixinLevel level, int x, int y, int z) {
        super.method_1611(level, x, y, z);
        if (level.getTileId(x, y, z) == this.id) {
            level.method_216(x, y, z, this.id, this.getTickrate());
        }
    }
}
