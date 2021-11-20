package io.github.ryuu.adventurecraft.blocks;

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
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;

public class BlockStairMulti extends MixinTile implements IBlockColor {

    private MixinTile modelBlock;

    protected BlockStairMulti(int i, MixinTile block, int textureID) {
        super(i, textureID, block.material);
        this.modelBlock = block;
        this.hardness(block.hardness);
        this.blastResistance(block.resistance / 3.0f);
        this.sounds(block.sounds);
        this.method_1590(255);
    }

    @Override
    public void method_1616(TileView iblockaccess, int i, int j, int k) {
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        return super.getCollisionShape(level, x, y, z);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public int method_1621() {
        return 10;
    }

    @Override
    public boolean method_1618(TileView iblockaccess, int i, int j, int k, int l) {
        return super.method_1618(iblockaccess, i, j, k, l);
    }

    @Override
    public void intersectsInLevel(MixinLevel world, int i, int j, int k, Box axisalignedbb, ArrayList intersections) {
        int l = world.getTileMeta(i, j, k) & 3;
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
        if (l == 0) {
            int m;
            MixinTile b = Tile.BY_ID[world.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == this.method_1621()) {
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
            if (b != null && b.method_1621() == this.method_1621() && (m == 2 || m == 3)) {
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
            MixinTile b = Tile.BY_ID[world.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == this.method_1621() && (m == 2 || m == 3)) {
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
            if (b != null && b.method_1621() == this.method_1621()) {
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
            MixinTile b = Tile.BY_ID[world.getTileId(i, j, k - 1)];
            if (b != null && b.method_1621() == this.method_1621()) {
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
            if (b != null && b.method_1621() == this.method_1621() && (m == 0 || m == 1)) {
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
            MixinTile b = Tile.BY_ID[world.getTileId(i, j, k + 1)];
            if (b != null && b.method_1621() == this.method_1621()) {
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
            if (b != null && b.method_1621() == this.method_1621() && (m == 0 || m == 1)) {
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

    @Override
    public void randomDisplayTick(MixinLevel level, int x, int y, int z, Random rand) {
        this.modelBlock.randomDisplayTick(level, x, y, z, rand);
    }

    @Override
    public void onPunched(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        this.modelBlock.onPunched(level, x, y, z, player);
    }

    @Override
    public void method_1612(MixinLevel world, int i, int j, int k, int l) {
        this.modelBlock.method_1612(world, i, j, k, l);
    }

    @Override
    public float method_1604(TileView iblockaccess, int i, int j, int k) {
        return this.modelBlock.method_1604(iblockaccess, i, j, k);
    }

    @Override
    public float method_1575(MixinEntity entity) {
        return this.modelBlock.method_1575(entity);
    }

    @Override
    public int method_1619() {
        return this.modelBlock.method_1619();
    }

    @Override
    public int getDropId(int meta, Random rand) {
        return this.modelBlock.getDropId(meta, rand);
    }

    @Override
    public int getDropCount(Random rand) {
        return this.modelBlock.getDropCount(rand);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        return this.tex + (meta >> 2);
    }

    @Override
    public int getTickrate() {
        return this.modelBlock.getTickrate();
    }

    @Override
    public Box getOutlineShape(MixinLevel level, int x, int y, int z) {
        return this.modelBlock.getOutlineShape(level, x, y, z);
    }

    @Override
    public void method_1572(MixinLevel world, int i, int j, int k, MixinEntity entity, Vec3f vec3d) {
        this.modelBlock.method_1572(world, i, j, k, entity, vec3d);
    }

    @Override
    public boolean method_1576() {
        return this.modelBlock.method_1576();
    }

    @Override
    public boolean method_1571(int i, boolean flag) {
        return this.modelBlock.method_1571(i, flag);
    }

    @Override
    public boolean canPlaceAt(MixinLevel level, int x, int y, int z) {
        return this.modelBlock.canPlaceAt(level, x, y, z);
    }

    @Override
    public void method_1611(MixinLevel level, int x, int y, int z) {
        this.method_1609(level, x, y, z, 0);
        this.modelBlock.method_1611(level, x, y, z);
    }

    @Override
    public void onTileRemoved(MixinLevel level, int x, int y, int z) {
        this.modelBlock.onTileRemoved(level, x, y, z);
    }

    @Override
    public void beforeDestroyedByExplosion(MixinLevel level, int x, int y, int z, int meta, float dropChance) {
        this.modelBlock.beforeDestroyedByExplosion(level, x, y, z, meta, dropChance);
    }

    @Override
    public void drop(MixinLevel level, int x, int y, int z, int meta) {
        this.modelBlock.drop(level, x, y, z, meta);
    }

    @Override
    public void method_1560(MixinLevel world, int i, int j, int k, MixinEntity entity) {
        this.modelBlock.method_1560(world, i, j, k, entity);
    }

    @Override
    public void onScheduledTick(MixinLevel level, int x, int y, int z, Random rand) {
        this.modelBlock.onScheduledTick(level, x, y, z, rand);
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        return this.modelBlock.activate(level, x, y, z, player);
    }

    @Override
    public void onDestroyedByExplosion(MixinLevel level, int x, int y, int z) {
        this.modelBlock.onDestroyedByExplosion(level, x, y, z);
    }

    @Override
    public void afterPlaced(MixinLevel world, int i, int j, int k, MixinLivingEntity entityliving) {
        int m = world.getTileMeta(i, j, k);
        int l = MathsHelper.floor((double) (entityliving.yaw * 4.0f / 360.0f) + 0.5) & 3;
        if (l == 0) {
            world.setTileMeta(i, j, k, 2 + m);
        }
        if (l == 1) {
            world.setTileMeta(i, j, k, 1 + m);
        }
        if (l == 2) {
            world.setTileMeta(i, j, k, 3 + m);
        }
        if (l == 3) {
            world.setTileMeta(i, j, k, 0 + m);
        }
    }

    protected int getColorMetaData(TileView iblockaccess, int i, int j, int k) {
        return iblockaccess.getTileMeta(i, j, k) >> 2;
    }

    protected void setColorMetaData(MixinLevel world, int i, int j, int k, int color) {
        world.setTileMeta(i, j, k, world.getTileMeta(i, j, k) & 3 | color << 2);
    }

    @Override
    public void incrementColor(MixinLevel world, int i, int j, int k) {
        int color = (this.getColorMetaData(world, i, j, k) + 1) % 16;
        this.setColorMetaData(world, i, j, k, color);
    }
}
