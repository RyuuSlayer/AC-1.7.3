package io.github.ryuu.adventurecraft.mixin.tile;

import java.util.Random;

import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TerrainImage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.FluidTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FluidTile.class)
public abstract class MixinFluidTile extends Tile {

    @Shadow()
    static boolean isHittable = true;

    protected MixinFluidTile(int id, Material material) {
        super(id, (material != Material.LAVA ? 12 : 14) * 16 + 13, material);
        float f = 0.0f;
        float f1 = 0.0f;
        this.setBoundingBox(0.0f + f1, 0.0f + f, 0.0f + f1, 1.0f + f1, 1.0f + f, 1.0f + f1);
        this.setTicksRandomly(true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTint(TileView iblockaccess, int i, int j, int k) {
        if (!TerrainImage.isWaterLoaded || this.id != Tile.FLOWING_WATER.id && this.id != Tile.STILL_WATER.id) {
            return -1;
        }
        return TerrainImage.getWaterColor(i, k);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static float method_1218(int i) {
        if (i >= 8) {
            i = 0;
        }
        float f = (float) (i + 1) / 9.0f;
        return f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTextureForSide(int side) {
        if (side == 0 || side == 1) {
            return this.tex;
        }
        return this.tex + 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected int method_1220(Level world, int i, int j, int k) {
        if (world.getMaterial(i, j, k) != this.material) {
            return -1;
        }
        return world.getTileMeta(i, j, k);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected int method_1217(TileView iblockaccess, int i, int j, int k) {
        if (iblockaccess.getMaterial(i, j, k) != this.material) {
            return -1;
        }
        int l = iblockaccess.getTileMeta(i, j, k);
        if (l >= 8) {
            l = 0;
        }
        return l;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1571(int i, boolean flag) {
        return DebugMode.active && isHittable || flag && i == 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1576() {
        return DebugMode.active && isHittable;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1573(TileView iblockaccess, int i, int j, int k, int l) {
        Material material = iblockaccess.getMaterial(i, j, k);
        if (material == this.material) {
            return false;
        }
        if (material == Material.ICE) {
            return false;
        }
        if (l == 1) {
            return true;
        }
        return super.method_1573(iblockaccess, i, j, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1618(TileView iblockaccess, int i, int j, int k, int l) {
        Material material = iblockaccess.getMaterial(i, j, k);
        if (material == this.material) {
            return false;
        }
        if (material == Material.ICE) {
            return false;
        }
        if (l == 1) {
            return true;
        }
        return super.method_1618(iblockaccess, i, j, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private Vec3f method_1219(TileView iblockaccess, int i, int j, int k) {
        Vec3f vec3d = Vec3f.from(0.0, 0.0, 0.0);
        int l = this.method_1217(iblockaccess, i, j, k);
        for (int i1 = 0; i1 < 4; ++i1) {
            int i2;
            int j1 = i;
            int k1 = j;
            int l1 = k;
            if (i1 == 0) {
                --j1;
            }
            if (i1 == 1) {
                --l1;
            }
            if (i1 == 2) {
                ++j1;
            }
            if (i1 == 3) {
                ++l1;
            }
            if ((i2 = this.method_1217(iblockaccess, j1, k1, l1)) < 0) {
                if (iblockaccess.getMaterial(j1, k1, l1).blocksMovement() || (i2 = this.method_1217(iblockaccess, j1, k1 - 1, l1)) < 0)
                    continue;
                int j2 = i2 - (l - 8);
                vec3d = vec3d.method_1301((j1 - i) * j2, (k1 - j) * j2, (l1 - k) * j2);
                continue;
            }
            if (i2 < 0)
                continue;
            int k2 = i2 - l;
            vec3d = vec3d.method_1301((j1 - i) * k2, (k1 - j) * k2, (l1 - k) * k2);
        }
        if (iblockaccess.getTileMeta(i, j, k) >= 8) {
            boolean flag = false;
            if (flag || this.method_1573(iblockaccess, i, j, k - 1, 2)) {
                flag = true;
            }
            if (flag || this.method_1573(iblockaccess, i, j, k + 1, 3)) {
                flag = true;
            }
            if (flag || this.method_1573(iblockaccess, i - 1, j, k, 4)) {
                flag = true;
            }
            if (flag || this.method_1573(iblockaccess, i + 1, j, k, 5)) {
                flag = true;
            }
            if (flag || this.method_1573(iblockaccess, i, j + 1, k - 1, 2)) {
                flag = true;
            }
            if (flag || this.method_1573(iblockaccess, i, j + 1, k + 1, 3)) {
                flag = true;
            }
            if (flag || this.method_1573(iblockaccess, i - 1, j + 1, k, 4)) {
                flag = true;
            }
            if (flag || this.method_1573(iblockaccess, i + 1, j + 1, k, 5)) {
                flag = true;
            }
            if (flag) {
                vec3d = vec3d.method_1296().method_1301(0.0, -6.0, 0.0);
            }
        }
        vec3d = vec3d.method_1296();
        return vec3d;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1572(Level world, int i, int j, int k, Entity entity, Vec3f vec3d) {
        Vec3f vec3d1 = this.method_1219(world, i, j, k);
        vec3d.x += vec3d1.x;
        vec3d.y += vec3d1.y;
        vec3d.z += vec3d1.z;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTickrate() {
        if (this.material == Material.WATER) {
            return 5;
        }
        return this.material != Material.LAVA ? 0 : 30;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public float method_1604(TileView iblockaccess, int i, int j, int k) {
        float f1;
        float f = iblockaccess.getBrightness(i, j, k);
        return f <= (f1 = iblockaccess.getBrightness(i, j + 1, k)) ? f1 : f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        super.onScheduledTick(level, x, y, z, rand);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_1619() {
        return this.material != Material.WATER ? 0 : 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void randomDisplayTick(Level level, int x, int y, int z, Random rand) {
        int l;
        if (this.material == Material.WATER && rand.nextInt(64) == 0 && (l = level.getTileMeta(x, y, z)) > 0 && l < 8) {
            level.playSound((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f, "liquid.water", rand.nextFloat() * 0.25f + 0.75f, rand.nextFloat() * 1.0f + 0.5f);
        }
        if (this.material == Material.LAVA && level.getMaterial(x, y + 1, z) == Material.AIR && !level.isFullOpaque(x, y + 1, z) && rand.nextInt(100) == 0) {
            double d = (float) x + rand.nextFloat();
            double d1 = (double) y + this.maxY;
            double d2 = (float) z + rand.nextFloat();
            level.addParticle("lava", d, d1, d2, 0.0, 0.0, 0.0);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static double method_1223(TileView iblockaccess, int i, int j, int k, Material material) {
        Vec3f vec3d = null;
        if (material == Material.WATER) {
            vec3d = ((FluidTile) Tile.FLOWING_WATER).method_1219(iblockaccess, i, j, k);
        }
        if (material == Material.LAVA) {
            vec3d = ((FluidTile) Tile.FLOWING_LAVA).method_1219(iblockaccess, i, j, k);
        }
        if (vec3d.x == 0.0 && vec3d.z == 0.0) {
            return -1000.0;
        }
        return Math.atan2((double) vec3d.z, (double) vec3d.x) - 1.5707963267948966;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1611(Level level, int x, int y, int z) {
        this.method_1222(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1609(Level level, int x, int y, int z, int id) {
        this.method_1222(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1222(Level world, int i, int j, int k) {
        if (world.getTileId(i, j, k) != this.id) {
            return;
        }
        if (this.material == Material.LAVA) {
            boolean flag = false;
            if (flag || world.getMaterial(i, j, k - 1) == Material.WATER) {
                flag = true;
            }
            if (flag || world.getMaterial(i, j, k + 1) == Material.WATER) {
                flag = true;
            }
            if (flag || world.getMaterial(i - 1, j, k) == Material.WATER) {
                flag = true;
            }
            if (flag || world.getMaterial(i + 1, j, k) == Material.WATER) {
                flag = true;
            }
            if (flag || world.getMaterial(i, j + 1, k) == Material.WATER) {
                flag = true;
            }
            if (flag) {
                int l = world.getTileMeta(i, j, k);
                if (l == 0) {
                    world.setTile(i, j, k, Tile.OBSIDIAN.id);
                } else if (l <= 4) {
                    world.setTile(i, j, k, Tile.COBBLESTONE.id);
                }
                this.method_1221(world, i, j, k);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_1221(Level world, int i, int j, int k) {
        world.playSound((float) i + 0.5f, (float) j + 0.5f, (float) k + 0.5f, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
        for (int l = 0; l < 8; ++l) {
            world.addParticle("largesmoke", (double) i + Math.random(), (double) j + 1.2, (double) k + Math.random(), 0.0, 0.0, 0.0);
        }
    }
}
