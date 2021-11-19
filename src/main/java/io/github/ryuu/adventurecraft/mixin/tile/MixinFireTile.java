package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.level.TileView;
import net.minecraft.tile.FireTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(FireTile.class)
public class MixinFireTile extends MixinTile {

    private final int[] spreadabilities = new int[256];
    @Shadow()
    private int[] flammabilities = new int[256];

    protected MixinFireTile(int id, int j) {
        super(id, j, Material.FIRE);
        this.setTicksRandomly(true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void afterTileItemCreated() {
        this.setFlammability(Tile.WOOD.id, 5, 20);
        this.setFlammability(Tile.FENCE.id, 5, 20);
        this.setFlammability(Tile.STAIRS_WOOD.id, 5, 20);
        this.setFlammability(Tile.LOG.id, 5, 5);
        this.setFlammability(Tile.LEAVES.id, 30, 60);
        this.setFlammability(Tile.BOOKSHELF.id, 30, 20);
        this.setFlammability(Tile.TNT.id, 15, 100);
        this.setFlammability(Tile.TALLGRASS.id, 60, 100);
        this.setFlammability(Tile.WOOL.id, 30, 60);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void setFlammability(int tileId, int flammability, int spreadabilities) {
        this.flammabilities[tileId] = flammability;
        this.spreadabilities[tileId] = spreadabilities;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isFullOpaque() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isFullCube() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_1621() {
        return 3;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getDropCount(Random rand) {
        return 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTickrate() {
        return 40;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onScheduledTick(MixinLevel level, int x, int y, int z, Random rand) {
        boolean flag;
        if (DebugMode.active) {
            return;
        }
        boolean bl = flag = level.getTileId(x, y - 1, z) == Tile.NETHERRACK.id;
        if (!this.canPlaceAt(level, x, y, z)) {
            level.setTile(x, y, z, 0);
        }
        if (!flag && level.raining() && (level.canRainAt(x, y, z) || level.canRainAt(x - 1, y, z) || level.canRainAt(x + 1, y, z) || level.canRainAt(x, y, z - 1) || level.canRainAt(x, y, z + 1))) {
            level.setTile(x, y, z, 0);
            return;
        }
        int l = level.getTileMeta(x, y, z);
        if (l < 15) {
            level.method_223(x, y, z, l + rand.nextInt(3) / 2);
        }
        level.method_216(x, y, z, this.id, this.getTickrate());
        if (!flag && !this.method_1826(level, x, y, z)) {
            if (!level.canSuffocate(x, y - 1, z) || l > 3) {
                level.setTile(x, y, z, 0);
            }
            return;
        }
        if (!flag && !this.method_1824(level, x, y - 1, z) && l == 15 && rand.nextInt(4) == 0) {
            level.setTile(x, y, z, 0);
            return;
        }
        this.method_1823(level, x + 1, y, z, 300, rand, l);
        this.method_1823(level, x - 1, y, z, 300, rand, l);
        this.method_1823(level, x, y - 1, z, 250, rand, l);
        this.method_1823(level, x, y + 1, z, 250, rand, l);
        this.method_1823(level, x, y, z - 1, 300, rand, l);
        this.method_1823(level, x, y, z + 1, 300, rand, l);
        for (int i1 = x - 1; i1 <= x + 1; ++i1) {
            for (int j1 = z - 1; j1 <= z + 1; ++j1) {
                for (int k1 = y - 1; k1 <= y + 4; ++k1) {
                    int j2;
                    int i2;
                    if (i1 == x && k1 == y && j1 == z) continue;
                    int l1 = 100;
                    if (k1 > y + 1) {
                        l1 += (k1 - (y + 1)) * 100;
                    }
                    if ((i2 = this.method_1827(level, i1, k1, j1)) <= 0 || (j2 = (i2 + 40) / (l + 30)) <= 0 || rand.nextInt(l1) > j2 || level.raining() && level.canRainAt(i1, k1, j1) || level.canRainAt(i1 - 1, k1, z) || level.canRainAt(i1 + 1, k1, j1) || level.canRainAt(i1, k1, j1 - 1) || level.canRainAt(i1, k1, j1 + 1))
                        continue;
                    int k2 = l + rand.nextInt(5) / 4;
                    if (k2 > 15) {
                        k2 = 15;
                    }
                    level.method_201(i1, k1, j1, this.id, k2);
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1823(MixinLevel world, int i, int j, int k, int l, Random random, int i1) {
        int j1 = this.spreadabilities[world.getTileId(i, j, k)];
        if (random.nextInt(l) < j1) {
            boolean flag;
            boolean bl = flag = world.getTileId(i, j, k) == Tile.TNT.id;
            if (random.nextInt(i1 + 10) < 5 && !world.canRainAt(i, j, k)) {
                int k1 = i1 + random.nextInt(5) / 4;
                if (k1 > 15) {
                    k1 = 15;
                }
                world.method_201(i, j, k, this.id, k1);
            } else {
                world.setTile(i, j, k, 0);
            }
            if (flag) {
                Tile.TNT.method_1612(world, i, j, k, 1);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private boolean method_1826(MixinLevel world, int i, int j, int k) {
        if (this.method_1824(world, i + 1, j, k)) {
            return true;
        }
        if (this.method_1824(world, i - 1, j, k)) {
            return true;
        }
        if (this.method_1824(world, i, j - 1, k)) {
            return true;
        }
        if (this.method_1824(world, i, j + 1, k)) {
            return true;
        }
        if (this.method_1824(world, i, j, k - 1)) {
            return true;
        }
        return this.method_1824(world, i, j, k + 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int method_1827(MixinLevel world, int i, int j, int k) {
        int l = 0;
        if (!world.isAir(i, j, k)) {
            return 0;
        }
        l = this.method_1825(world, i + 1, j, k, l);
        l = this.method_1825(world, i - 1, j, k, l);
        l = this.method_1825(world, i, j - 1, k, l);
        l = this.method_1825(world, i, j + 1, k, l);
        l = this.method_1825(world, i, j, k - 1, l);
        l = this.method_1825(world, i, j, k + 1, l);
        return l;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1576() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1824(TileView iblockaccess, int i, int j, int k) {
        return this.flammabilities[iblockaccess.getTileId(i, j, k)] > 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_1825(MixinLevel world, int i, int j, int k, int l) {
        int i1 = this.flammabilities[world.getTileId(i, j, k)];
        if (i1 > l) {
            return i1;
        }
        return l;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canPlaceAt(MixinLevel level, int x, int y, int z) {
        return level.canSuffocate(x, y - 1, z) || this.method_1826(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1609(MixinLevel level, int x, int y, int z, int id) {
        if (!level.canSuffocate(x, y - 1, z) && !this.method_1826(level, x, y, z)) {
            level.setTile(x, y, z, 0);
            return;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1611(MixinLevel level, int x, int y, int z) {
        if (level.getTileId(x, y - 1, z) == Tile.OBSIDIAN.id && Tile.PORTAL.method_736(level, x, y, z)) {
            return;
        }
        if (!level.canSuffocate(x, y - 1, z) && !this.method_1826(level, x, y, z)) {
            level.setTile(x, y, z, 0);
            return;
        }
        level.method_216(x, y, z, this.id, this.getTickrate());
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void randomDisplayTick(MixinLevel level, int x, int y, int z, Random rand) {
        block12:
        {
            block11:
            {
                if (rand.nextInt(24) == 0) {
                    level.playSound((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f, "fire.fire", 1.0f + rand.nextFloat(), rand.nextFloat() * 0.7f + 0.3f);
                }
                if (!level.canSuffocate(x, y - 1, z) && !Tile.FIRE.method_1824(level, x, y - 1, z)) break block11;
                for (int l = 0; l < 3; ++l) {
                    float f = (float) x + rand.nextFloat();
                    float f6 = (float) y + rand.nextFloat() * 0.5f + 0.5f;
                    float f12 = (float) z + rand.nextFloat();
                    level.addParticle("largesmoke", f, f6, f12, 0.0, 0.0, 0.0);
                }
                break block12;
            }
            if (Tile.FIRE.method_1824(level, x - 1, y, z)) {
                for (int i1 = 0; i1 < 2; ++i1) {
                    float f1 = (float) x + rand.nextFloat() * 0.1f;
                    float f7 = (float) y + rand.nextFloat();
                    float f13 = (float) z + rand.nextFloat();
                    level.addParticle("largesmoke", f1, f7, f13, 0.0, 0.0, 0.0);
                }
            }
            if (Tile.FIRE.method_1824(level, x + 1, y, z)) {
                for (int j1 = 0; j1 < 2; ++j1) {
                    float f2 = (float) (x + 1) - rand.nextFloat() * 0.1f;
                    float f8 = (float) y + rand.nextFloat();
                    float f14 = (float) z + rand.nextFloat();
                    level.addParticle("largesmoke", f2, f8, f14, 0.0, 0.0, 0.0);
                }
            }
            if (Tile.FIRE.method_1824(level, x, y, z - 1)) {
                for (int k1 = 0; k1 < 2; ++k1) {
                    float f3 = (float) x + rand.nextFloat();
                    float f9 = (float) y + rand.nextFloat();
                    float f15 = (float) z + rand.nextFloat() * 0.1f;
                    level.addParticle("largesmoke", f3, f9, f15, 0.0, 0.0, 0.0);
                }
            }
            if (Tile.FIRE.method_1824(level, x, y, z + 1)) {
                for (int l1 = 0; l1 < 2; ++l1) {
                    float f4 = (float) x + rand.nextFloat();
                    float f10 = (float) y + rand.nextFloat();
                    float f16 = (float) (z + 1) - rand.nextFloat() * 0.1f;
                    level.addParticle("largesmoke", f4, f10, f16, 0.0, 0.0, 0.0);
                }
            }
            if (!Tile.FIRE.method_1824(level, x, y + 1, z)) break block12;
            for (int i2 = 0; i2 < 2; ++i2) {
                float f5 = (float) x + rand.nextFloat();
                float f11 = (float) (y + 1) - rand.nextFloat() * 0.1f;
                float f17 = (float) z + rand.nextFloat();
                level.addParticle("largesmoke", f5, f11, f17, 0.0, 0.0, 0.0);
            }
        }
    }
}
