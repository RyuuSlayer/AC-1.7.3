package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.Box;

import java.util.ArrayList;
import java.util.Random;

public class BlockStairMulti extends Tile implements IBlockColor {
    private final Tile modelBlock;

    protected BlockStairMulti(int i, Tile block, int textureID) {
        super(i, textureID, block.bA);
        this.modelBlock = block;
        c(block.bo);
        b(block.bp / 3.0F);
        a(block.by);
        g(255);
    }

    public void a(TileView iblockaccess, int i, int j, int k) {
        a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public Box e(Level world, int i, int j, int k) {
        return super.e(world, i, j, k);
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 10;
    }

    public boolean b(TileView iblockaccess, int i, int j, int k, int l) {
        return super.b(iblockaccess, i, j, k, l);
    }

    public void a(Level world, int i, int j, int k, Box axisalignedbb, ArrayList arraylist) {
        int l = world.e(i, j, k) & 0x3;
        a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        super.a(world, i, j, k, axisalignedbb, arraylist);
        if (l == 0) {
            Tile b = Tile.m[world.a(i - 1, j, k)];
            if (b != null && b.b() == b()) {
                int n = world.e(i - 1, j, k) & 0x3;
                if (n == 2) {
                    a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else if (n == 3) {
                    a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            }
            int m = world.e(i + 1, j, k) & 0x3;
            b = Tile.m[world.a(i + 1, j, k)];
            if (b != null && b.b() == b() && (m == 2 || m == 3)) {
                if (m == 2) {
                    a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else if (m == 3) {
                    a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            } else {
                a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                super.a(world, i, j, k, axisalignedbb, arraylist);
            }
        } else if (l == 1) {
            int m = world.e(i - 1, j, k) & 0x3;
            Tile b = Tile.m[world.a(i - 1, j, k)];
            if (b != null && b.b() == b() && (m == 2 || m == 3)) {
                if (m == 3) {
                    a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else {
                    a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            } else {
                a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
                super.a(world, i, j, k, axisalignedbb, arraylist);
            }
            b = Tile.m[world.a(i + 1, j, k)];
            if (b != null && b.b() == b()) {
                m = world.e(i + 1, j, k) & 0x3;
                if (m == 2) {
                    a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else if (m == 3) {
                    a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            }
        } else if (l == 2) {
            Tile b = Tile.m[world.a(i, j, k - 1)];
            if (b != null && b.b() == b()) {
                int n = world.e(i, j, k - 1) & 0x3;
                if (n == 1) {
                    a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else if (n == 0) {
                    a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            }
            int m = world.e(i, j, k + 1) & 0x3;
            b = Tile.m[world.a(i, j, k + 1)];
            if (b != null && b.b() == b() && (m == 0 || m == 1)) {
                if (m == 0) {
                    a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else {
                    a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            } else {
                a(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                super.a(world, i, j, k, axisalignedbb, arraylist);
            }
        } else if (l == 3) {
            Tile b = Tile.m[world.a(i, j, k + 1)];
            if (b != null && b.b() == b()) {
                int n = world.e(i, j, k + 1) & 0x3;
                if (n == 1) {
                    a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else if (n == 0) {
                    a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            }
            int m = world.e(i, j, k - 1) & 0x3;
            b = Tile.m[world.a(i, j, k - 1)];
            if (b != null && b.b() == b() && (m == 0 || m == 1)) {
                if (m == 0) {
                    a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else {
                    a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            } else {
                a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                super.a(world, i, j, k, axisalignedbb, arraylist);
            }
        }
        a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void b(Level world, int i, int j, int k, Random random) {
        this.modelBlock.b(world, i, j, k, random);
    }

    public void b(Level world, int i, int j, int k, Player entityplayer) {
        this.modelBlock.b(world, i, j, k, entityplayer);
    }

    public void c(Level world, int i, int j, int k, int l) {
        this.modelBlock.c(world, i, j, k, l);
    }

    public float d(TileView iblockaccess, int i, int j, int k) {
        return this.modelBlock.d(iblockaccess, i, j, k);
    }

    public float a(Entity entity) {
        return this.modelBlock.a(entity);
    }

    public int b_() {
        return this.modelBlock.b_();
    }

    public int a(int i, Random random) {
        return this.modelBlock.a(i, random);
    }

    public int a(Random random) {
        return this.modelBlock.a(random);
    }

    public int a(int i, int j) {
        return this.bm + (j >> 2);
    }

    public int e() {
        return this.modelBlock.e();
    }

    public Box f(Level world, int i, int j, int k) {
        return this.modelBlock.f(world, i, j, k);
    }

    public void a(Level world, int i, int j, int k, Entity entity, bt vec3d) {
        this.modelBlock.a(world, i, j, k, entity, vec3d);
    }

    public boolean v_() {
        return this.modelBlock.v_();
    }

    public boolean a(int i, boolean flag) {
        return this.modelBlock.a(i, flag);
    }

    public boolean a(Level world, int i, int j, int k) {
        return this.modelBlock.a(world, i, j, k);
    }

    public void c(Level world, int i, int j, int k) {
        b(world, i, j, k, 0);
        this.modelBlock.c(world, i, j, k);
    }

    public void b(Level world, int i, int j, int k) {
        this.modelBlock.b(world, i, j, k);
    }

    public void a(Level world, int i, int j, int k, int l, float f) {
        this.modelBlock.a(world, i, j, k, l, f);
    }

    public void g(Level world, int i, int j, int k, int l) {
        this.modelBlock.g(world, i, j, k, l);
    }

    public void b(Level world, int i, int j, int k, Entity entity) {
        this.modelBlock.b(world, i, j, k, entity);
    }

    public void a(Level world, int i, int j, int k, Random random) {
        this.modelBlock.a(world, i, j, k, random);
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        return this.modelBlock.a(world, i, j, k, entityplayer);
    }

    public void d(Level world, int i, int j, int k) {
        this.modelBlock.d(world, i, j, k);
    }

    public void a(Level world, int i, int j, int k, LivingEntity entityliving) {
        int m = world.e(i, j, k);
        int l = in.b((entityliving.aS * 4.0F / 360.0F) + 0.5D) & 0x3;
        if (l == 0)
            world.d(i, j, k, 2 + m);
        if (l == 1)
            world.d(i, j, k, 1 + m);
        if (l == 2)
            world.d(i, j, k, 3 + m);
        if (l == 3)
            world.d(i, j, k, 0 + m);
    }

    protected int getColorMetaData(TileView iblockaccess, int i, int j, int k) {
        return iblockaccess.e(i, j, k) >> 2;
    }

    protected void setColorMetaData(Level world, int i, int j, int k, int color) {
        world.d(i, j, k, world.e(i, j, k) & 0x3 | color << 2);
    }

    public void incrementColor(Level world, int i, int j, int k) {
        int color = (getColorMetaData(world, i, j, k) + 1) % 16;
        setColorMetaData(world, i, j, k, color);
    }
}
