package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.mixin.tile.AccessTile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3d;

import java.util.ArrayList;
import java.util.Random;

public class BlockStairMulti extends BlockColor {

    private final Tile modelBlock;

    protected BlockStairMulti(int i, Tile block, int textureID) {
        super(i, textureID, block.material);
        this.modelBlock = block;
        this.hardness(((AccessTile) block).getHardness());
        this.blastResistance(((AccessTile) block).getResistance() / 3.0f);
        this.sounds(block.sounds);
        this.method_1590(255);
        ((ExTile) this).setTextureNum(3);
        ((ExTile) this).setSubTypes(4);
    }

    @Override
    public void method_1616(TileView iblockaccess, int i, int j, int k) {
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
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
    public void intersectsInLevel(Level world, int i, int j, int k, Box axisalignedbb, ArrayList intersections) {
        int l = world.getTileMeta(i, j, k) & 3;
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
        if (l == 0) {
            Tile b = Tile.BY_ID[world.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == this.method_1621()) {
                int m = world.getTileMeta(i - 1, j, k) & 3;
                if (m == 2) {
                    this.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else if (m == 3) {
                    this.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            }
            int m = world.getTileMeta(i + 1, j, k) & 3;
            b = Tile.BY_ID[world.getTileId(i + 1, j, k)];
            if (b != null && b.method_1621() == this.method_1621() && (m == 2 || m == 3)) {
                if (m == 2) {
                    this.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                } else {
                    this.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                }
            } else {
                this.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
        } else if (l == 1) {
            int m = world.getTileMeta(i - 1, j, k) & 3;
            Tile b = Tile.BY_ID[world.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == this.method_1621() && (m == 2 || m == 3)) {
                if (m == 3) {
                    this.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                } else {
                    this.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                }
            } else {
                this.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 1.0f);
            }
            super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
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
            Tile b = Tile.BY_ID[world.getTileId(i, j, k - 1)];
            if (b != null && b.method_1621() == this.method_1621()) {
                int m = world.getTileMeta(i, j, k - 1) & 3;
                if (m == 1) {
                    this.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else if (m == 0) {
                    this.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            }
            int m = world.getTileMeta(i, j, k + 1) & 3;
            b = Tile.BY_ID[world.getTileId(i, j, k + 1)];
            if (b != null && b.method_1621() == this.method_1621() && (m == 0 || m == 1)) {
                if (m == 0) {
                    this.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                } else {
                    this.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                }
            } else {
                this.setBoundingBox(0.0f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
            }
            super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
        } else {
            Tile b = Tile.BY_ID[world.getTileId(i, j, k + 1)];
            if (b != null && b.method_1621() == this.method_1621()) {
                int m = world.getTileMeta(i, j, k + 1) & 3;
                if (m == 1) {
                    this.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                } else if (m == 0) {
                    this.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
                }
            }
            int m = world.getTileMeta(i, j, k - 1) & 3;
            b = Tile.BY_ID[world.getTileId(i, j, k - 1)];
            if (b != null && b.method_1621() == this.method_1621() && (m == 0 || m == 1)) {
                if (m == 0) {
                    this.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                } else {
                    this.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                }
            } else {
                this.setBoundingBox(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
            }
            super.intersectsInLevel(world, i, j, k, axisalignedbb, intersections);
        }
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void randomDisplayTick(Level level, int x, int y, int z, Random rand) {
        this.modelBlock.randomDisplayTick(level, x, y, z, rand);
    }

    @Override
    public void onPunched(Level level, int x, int y, int z, Player player) {
        this.modelBlock.onPunched(level, x, y, z, player);
    }

    @Override
    public void method_1612(Level world, int i, int j, int k, int l) {
        this.modelBlock.method_1612(world, i, j, k, l);
    }

    @Override
    public float method_1604(TileView iblockaccess, int i, int j, int k) {
        return this.modelBlock.method_1604(iblockaccess, i, j, k);
    }

    @Override
    public float method_1575(Entity entity) {
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
    public Box getOutlineShape(Level level, int x, int y, int z) {
        return this.modelBlock.getOutlineShape(level, x, y, z);
    }

    @Override
    public void method_1572(Level world, int i, int j, int k, Entity entity, Vec3d vec3d) {
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
    public boolean canPlaceAt(Level level, int x, int y, int z) {
        return this.modelBlock.canPlaceAt(level, x, y, z);
    }

    @Override
    public void method_1611(Level level, int x, int y, int z) {
        this.method_1609(level, x, y, z, 0);
        this.modelBlock.method_1611(level, x, y, z);
    }

    @Override
    public void onTileRemoved(Level level, int x, int y, int z) {
        this.modelBlock.onTileRemoved(level, x, y, z);
    }

    @Override
    public void beforeDestroyedByExplosion(Level level, int x, int y, int z, int meta, float dropChance) {
        this.modelBlock.beforeDestroyedByExplosion(level, x, y, z, meta, dropChance);
    }

    @Override
    public void drop(Level level, int x, int y, int z, int meta) {
        ((AccessTile) this.modelBlock).invokeDrop(level, x, y, z, meta);
    }

    @Override
    public void method_1560(Level world, int i, int j, int k, Entity entity) {
        this.modelBlock.method_1560(world, i, j, k, entity);
    }

    @Override
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        this.modelBlock.onScheduledTick(level, x, y, z, rand);
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        return this.modelBlock.activate(level, x, y, z, player);
    }

    @Override
    public void onDestroyedByExplosion(Level level, int x, int y, int z) {
        this.modelBlock.onDestroyedByExplosion(level, x, y, z);
    }

    @Override
    public void afterPlaced(Level world, int i, int j, int k, LivingEntity entityliving) {
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
            world.setTileMeta(i, j, k, m);
        }
    }

    @Override
    public int getColorMetaData(TileView iblockaccess, int i, int j, int k) {
        return iblockaccess.getTileMeta(i, j, k) >> 2;
    }

    @Override
    public void setColorMetaData(Level world, int i, int j, int k, int color) {
        world.setTileMeta(i, j, k, world.getTileMeta(i, j, k) & 3 | color << 2);
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int color = (this.getColorMetaData(world, i, j, k) + 1) % 16;
        this.setColorMetaData(world, i, j, k, color);
    }
}
