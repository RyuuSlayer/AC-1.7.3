package io.github.ryuu.adventurecraft.mixin.tile;

import java.util.ArrayList;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StairsTile.class)
public class MixinStairsTile extends BlockColor {

    @Shadow()
    private Tile field_1672;

    protected MixinStairsTile(int i, Tile block) {
        super(i, block.tex, block.material);
        this.field_1672 = block;
        this.hardness(block.hardness);
        this.blastResistance(block.resistance / 3.0f);
        this.sounds(block.sounds);
        this.method_1590(255);
        if (block.material == Material.WOOD) {
            this.defaultColor = 0xFFFFFF;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Box getCollisionShape(Level level, int x, int y, int z) {
        return super.getCollisionShape(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1618(TileView iblockaccess, int i, int j, int k, int l) {
        return super.method_1618(iblockaccess, i, j, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void intersectsInLevel(Level world, int i, int j, int k, Box axisalignedbb, ArrayList intersections) {
        int l = world.getTileMeta(i, j, k) & 3;
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
        if (l == 0) {
            int m;
            Tile b = Tile.BY_ID[world.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == 10) {
                m = world.getTileMeta(i - 1, j, k) & 3;
                if (m == 2) {
                    this.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else if (m == 3) {
                    this.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            }
            m = world.getTileMeta(i + 1, j, k) & 3;
            b = Tile.BY_ID[world.getTileId(i + 1, j, k)];
            if (b != null && b.method_1621() == 10 && (m == 2 || m == 3)) {
                if (m == 2) {
                    this.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else if (m == 3) {
                    this.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            } else {
                this.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
                super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
            }
        } else if (l == 1) {
            int m = world.getTileMeta(i - 1, j, k) & 3;
            Tile b = Tile.BY_ID[world.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == 10 && (m == 2 || m == 3)) {
                if (m == 3) {
                    this.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else {
                    this.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            } else {
                this.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 1.0f);
                super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
            }
            b = Tile.BY_ID[world.getTileId(i + 1, j, k)];
            if (b != null && b.method_1621() == 10) {
                m = world.getTileMeta(i + 1, j, k) & 3;
                if (m == 2) {
                    this.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else if (m == 3) {
                    this.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            }
        } else if (l == 2) {
            int m;
            Tile b = Tile.BY_ID[world.getTileId(i, j, k - 1)];
            if (b != null && b.method_1621() == 10) {
                m = world.getTileMeta(i, j, k - 1) & 3;
                if (m == 1) {
                    this.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else if (m == 0) {
                    this.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            }
            m = world.getTileMeta(i, j, k + 1) & 3;
            b = Tile.BY_ID[world.getTileId(i, j, k + 1)];
            if (b != null && b.method_1621() == 10 && (m == 0 || m == 1)) {
                if (m == 0) {
                    this.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else {
                    this.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            } else {
                this.setBoundingBox(0.0f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
            }
        } else if (l == 3) {
            int m;
            Tile b = Tile.BY_ID[world.getTileId(i, j, k + 1)];
            if (b != null && b.method_1621() == 10) {
                m = world.getTileMeta(i, j, k + 1) & 3;
                if (m == 1) {
                    this.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else if (m == 0) {
                    this.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            }
            m = world.getTileMeta(i, j, k - 1) & 3;
            b = Tile.BY_ID[world.getTileId(i, j, k - 1)];
            if (b != null && b.method_1621() == 10 && (m == 0 || m == 1)) {
                if (m == 0) {
                    this.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else {
                    this.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            } else {
                this.setBoundingBox(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
            }
        }
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void randomDisplayTick(Level level, int x, int y, int z, Random rand) {
        this.field_1672.randomDisplayTick(level, x, y, z, rand);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onPunched(Level level, int x, int y, int z, Player player) {
        this.field_1672.onPunched(level, x, y, z, player);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1612(Level world, int i, int j, int k, int l) {
        this.field_1672.method_1612(world, i, j, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public float method_1604(TileView iblockaccess, int i, int j, int k) {
        return this.field_1672.method_1604(iblockaccess, i, j, k);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getDropId(int meta, Random rand) {
        return this.field_1672.getDropId(meta, rand);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getDropCount(Random rand) {
        return this.field_1672.getDropCount(rand);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTextureForSide(int side, int meta) {
        return this.field_1672.getTextureForSide(side, meta);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getTextureForSide(int side) {
        return this.field_1672.getTextureForSide(side);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        return this.field_1672.method_1626(iblockaccess, i, j, k, l);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Box getOutlineShape(Level level, int x, int y, int z) {
        return this.field_1672.getOutlineShape(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1572(Level world, int i, int j, int k, Entity entity, Vec3f vec3d) {
        this.field_1672.method_1572(world, i, j, k, entity, vec3d);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1571(int i, boolean flag) {
        return this.field_1672.method_1571(i, flag);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canPlaceAt(Level level, int x, int y, int z) {
        return this.field_1672.canPlaceAt(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1611(Level level, int x, int y, int z) {
        this.method_1609(level, x, y, z, 0);
        this.field_1672.method_1611(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onTileRemoved(Level level, int x, int y, int z) {
        this.field_1672.onTileRemoved(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void beforeDestroyedByExplosion(Level level, int x, int y, int z, int meta, float dropChance) {
        this.field_1672.beforeDestroyedByExplosion(level, x, y, z, meta, dropChance);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void drop(Level level, int x, int y, int z, int meta) {
        this.field_1672.drop(level, x, y, z, meta);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1560(Level world, int i, int j, int k, Entity entity) {
        this.field_1672.method_1560(world, i, j, k, entity);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        this.field_1672.onScheduledTick(level, x, y, z, rand);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean activate(Level level, int x, int y, int z, Player player) {
        return this.field_1672.activate(level, x, y, z, player);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onDestroyedByExplosion(Level level, int x, int y, int z) {
        this.field_1672.onDestroyedByExplosion(level, x, y, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void afterPlaced(Level world, int i, int j, int k, LivingEntity entityliving) {
        int l = MathsHelper.floor((double) (entityliving.yaw * 4.0f / 360.0f) + 0.5) & 3;
        if (l == 0) {
            world.setTileMeta(i, j, k, 2);
        }
        if (l == 1) {
            world.setTileMeta(i, j, k, 1);
        }
        if (l == 2) {
            world.setTileMeta(i, j, k, 3);
        }
        if (l == 3) {
            world.setTileMeta(i, j, k, 0);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected int getColorMetaData(TileView iblockaccess, int i, int j, int k) {
        return iblockaccess.getTileMeta(i, j, k) >> 2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void setColorMetaData(Level world, int i, int j, int k, int color) {
        world.setTileMeta(i, j, k, world.getTileMeta(i, j, k) & 3 | color << 2);
    }
}
