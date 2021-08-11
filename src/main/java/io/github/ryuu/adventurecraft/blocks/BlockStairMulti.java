package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;

import java.util.ArrayList;
import java.util.Random;

public class BlockStairMulti extends Tile implements IBlockColor {
    private final Tile modelBlock;

    protected BlockStairMulti(int i, Tile block, int textureID) {
        super(i, textureID, block.material);
        this.modelBlock = block;
        hardness(block.getHardness());
        resistance(block.resistance / 3.0F);
        sounds(block.sounds);
        method_1590(255);
    }

    @Override
    public void method_1616(TileView iblockaccess, int i, int j, int k) {
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        return super.getCollisionShape(world, i, j, k);
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
    public void intersectsInLevel(Level world, int i, int j, int k, Box axisalignedbb, ArrayList arraylist) {
        int l = world.getTileMeta(i, j, k) & 0x3;
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
        if (l == 0) {
            Tile b = Tile.BY_ID[world.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == method_1621()) {
                int n = world.getTileMeta(i - 1, j, k) & 0x3;
                if (n == 2) {
                    setBoundingBox(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
                } else if (n == 3) {
                    setBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
                }
            }
            int m = world.getTileMeta(i + 1, j, k) & 0x3;
            b = Tile.BY_ID[world.getTileId(i + 1, j, k)];
            if (b != null && b.method_1621() == method_1621() && (m == 2 || m == 3)) {
                if (m == 2) {
                    setBoundingBox(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
                } else if (m == 3) {
                    setBoundingBox(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
                }
            } else {
                setBoundingBox(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
            }
        } else if (l == 1) {
            int m = world.getTileMeta(i - 1, j, k) & 0x3;
            Tile b = Tile.BY_ID[world.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == method_1621() && (m == 2 || m == 3)) {
                if (m == 3) {
                    setBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                } else {
                    setBoundingBox(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                }
            } else {
                setBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
            }
            super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
            b = Tile.BY_ID[world.getTileId(i + 1, j, k)];
            if (b != null && b.method_1621() == method_1621()) {
                m = world.getTileMeta(i + 1, j, k) & 0x3;
                if (m == 2) {
                    setBoundingBox(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
                } else if (m == 3) {
                    setBoundingBox(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
                }
            }
        } else if (l == 2) {
            Tile b = Tile.BY_ID[world.getTileId(i, j, k - 1)];
            if (b != null && b.method_1621() == method_1621()) {
                int n = world.getTileMeta(i, j, k - 1) & 0x3;
                if (n == 1) {
                    setBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
                } else if (n == 0) {
                    setBoundingBox(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
                }
            }
            int m = world.getTileMeta(i, j, k + 1) & 0x3;
            b = Tile.BY_ID[world.getTileId(i, j, k + 1)];
            if (b != null && b.method_1621() == method_1621() && (m == 0 || m == 1)) {
                if (m == 0) {
                    setBoundingBox(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                } else {
                    setBoundingBox(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                }
            } else {
                setBoundingBox(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
            }
            super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
        } else if (l == 3) {
            Tile b = Tile.BY_ID[world.getTileId(i, j, k + 1)];
            if (b != null && b.method_1621() == method_1621()) {
                int n = world.getTileMeta(i, j, k + 1) & 0x3;
                if (n == 1) {
                    setBoundingBox(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
                } else if (n == 0) {
                    setBoundingBox(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
                }
            }
            int m = world.getTileMeta(i, j, k - 1) & 0x3;
            b = Tile.BY_ID[world.getTileId(i, j, k - 1)];
            if (b != null && b.method_1621() == method_1621() && (m == 0 || m == 1)) {
                if (m == 0) {
                    setBoundingBox(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                } else {
                    setBoundingBox(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                }
            } else {
                setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
            }
            super.intersectsInLevel(world, i, j, k, axisalignedbb, arraylist);
        }
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void randomDisplayTick(Level world, int i, int j, int k, Random random) {
        this.modelBlock.randomDisplayTick(world, i, j, k, random);
    }

    @Override
    public void onPunched(Level world, int i, int j, int k, Player entityplayer) {
        this.modelBlock.onPunched(world, i, j, k, entityplayer);
    }

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
    public int getDropId(int i, Random random) {
        return this.modelBlock.getDropId(i, random);
    }

    @Override
    public int getDropCount(Random random) {
        return this.modelBlock.getDropCount(random);
    }

    @Override
    public int getTextureForSide(int i, int j) {
        return this.tex + (j >> 2);
    }

    @Override
    public int getTickrate() {
        return this.modelBlock.getTickrate();
    }

    @Override
    public Box getOutlineShape(Level world, int i, int j, int k) {
        return this.modelBlock.getOutlineShape(world, i, j, k);
    }

    @Override
    public void method_1572(Level world, int i, int j, int k, Entity entity, Vec3f vec3d) {
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
    public boolean canPlaceAt(Level world, int i, int j, int k) {
        return this.modelBlock.canPlaceAt(world, i, j, k);
    }

    @Override
    public void method_1611(Level world, int i, int j, int k) {
        method_1609(world, i, j, k, 0);
        this.modelBlock.method_1611(world, i, j, k);
    }

    @Override
    public void onTileRemoved(Level world, int i, int j, int k) {
        this.modelBlock.onTileRemoved(world, i, j, k);
    }

    @Override
    public void beforeDestroyedByExplosion(Level world, int i, int j, int k, int l, float f) {
        this.modelBlock.beforeDestroyedByExplosion(world, i, j, k, l, f);
    }

    @Override
    public void drop(Level world, int i, int j, int k, int l) {
        this.modelBlock.drop(world, i, j, k, l);
    }

    @Override
    public void method_1560(Level world, int i, int j, int k, Entity entity) {
        this.modelBlock.method_1560(world, i, j, k, entity);
    }

    @Override
    public void onScheduledTick(Level world, int i, int j, int k, Random random) {
        this.modelBlock.onScheduledTick(world, i, j, k, random);
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        return this.modelBlock.activate(world, i, j, k, entityplayer);
    }

    @Override
    public void onDestroyedByExplosion(Level world, int i, int j, int k) {
        this.modelBlock.onDestroyedByExplosion(world, i, j, k);
    }

    @Override
    public void afterPlaced(Level world, int i, int j, int k, LivingEntity entityliving) {
        int m = world.getTileMeta(i, j, k);
        int l = MathsHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
        if (l == 0)
            world.setTileMeta(i, j, k, 2 + m);
        if (l == 1)
            world.setTileMeta(i, j, k, 1 + m);
        if (l == 2)
            world.setTileMeta(i, j, k, 3 + m);
        if (l == 3)
            world.setTileMeta(i, j, k, 0 + m);
    }

    protected int getColorMetaData(TileView iblockaccess, int i, int j, int k) {
        return iblockaccess.getTileMeta(i, j, k) >> 2;
    }

    protected void setColorMetaData(Level world, int i, int j, int k, int color) {
        world.setTileMeta(i, j, k, world.getTileMeta(i, j, k) & 0x3 | color << 2);
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int color = (getColorMetaData(world, i, j, k) + 1) % 16;
        setColorMetaData(world, i, j, k, color);
    }
}
