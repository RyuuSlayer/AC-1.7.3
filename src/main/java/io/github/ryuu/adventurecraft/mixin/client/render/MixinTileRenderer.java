package io.github.ryuu.adventurecraft.mixin.client.render;

import java.util.Random;

import io.github.ryuu.adventurecraft.blocks.BlockOverlay;
import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.sortme.MagicBedNumbers;
import net.minecraft.tile.*;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.lwjgl.opengl.GL11;

public class MixinTileRenderer {
    public TileView field_82;
    private int field_83 = -1;
    private boolean field_84 = false;
    private boolean field_85 = false;
    public static boolean field_67 = true;
    public boolean field_81 = true;
    private int field_86 = 0;
    private int field_87 = 0;
    private int field_88 = 0;
    private int field_89 = 0;
    private int field_90 = 0;
    private int field_91 = 0;
    private boolean field_92;
    private float field_93;
    private float field_94;
    private float field_95;
    private float field_96;
    private float field_97;
    private float field_98;
    private float field_99;
    private float field_100;
    private float field_101;
    private float field_102;
    private float field_103;
    private float field_104;
    private float field_105;
    private float field_41;
    private float field_42;
    private float field_43;
    private float field_44;
    private float field_45;
    private float field_46;
    private float field_47;
    private float field_48;
    private float field_49;
    private float field_50;
    private float field_51;
    private float field_52;
    private float field_53;
    private float field_54;
    private int field_55 = 1;
    private float field_56;
    private float field_57;
    private float field_58;
    private float field_59;
    private float field_60;
    private float field_61;
    private float field_62;
    private float field_63;
    private float field_64;
    private float field_65;
    private float field_66;
    private float field_68;
    private boolean field_69;
    private boolean field_70;
    private boolean field_71;
    private boolean field_72;
    private boolean field_73;
    private boolean field_74;
    private boolean field_75;
    private boolean field_76;
    private boolean field_77;
    private boolean field_78;
    private boolean field_79;
    private boolean field_80;
    private Random rand;
    private int curTextureNum = -1;

    public MixinTileRenderer(TileView iblockaccess) {
        this.field_82 = iblockaccess;
        this.rand = new Random();
    }

    public MixinTileRenderer() {
        this.rand = new Random();
    }

    public void method_51(Tile block, int i, int j, int k, int l) {
        this.field_83 = l;
        this.method_57(block, i, j, k);
        this.field_83 = -1;
    }

    public void method_49(Tile block, int i, int j, int k) {
        this.field_85 = true;
        this.method_57(block, i, j, k);
        this.field_85 = false;
    }

    public boolean method_57(Tile block, int i, int j, int k) {
        int l = block.method_1621();
        if (!block.shouldRender(this.field_82, i, j, k)) {
            return false;
        }
        block.method_1616(this.field_82, i, j, k);
        if (l == 0) {
            return this.method_76(block, i, j, k);
        }
        if (l == 4) {
            return this.method_75(block, i, j, k);
        }
        if (l == 13) {
            return this.method_77(block, i, j, k);
        }
        if (l == 1) {
            return this.method_73(block, i, j, k);
        }
        if (l == 6) {
            return this.method_74(block, i, j, k);
        }
        if (l == 2) {
            return this.method_62(block, i, j, k);
        }
        if (l == 3) {
            return this.method_70(block, i, j, k);
        }
        if (l == 5) {
            return this.method_71(block, i, j, k);
        }
        if (l == 8) {
            return this.method_72(block, i, j, k);
        }
        if (l == 7) {
            return this.method_80(block, i, j, k);
        }
        if (l == 9) {
            return this.method_44((RailTile)block, i, j, k);
        }
        if (l == 10) {
            return this.method_79(block, i, j, k);
        }
        if (l == 11) {
            return this.method_78(block, i, j, k);
        }
        if (l == 12) {
            return this.method_68(block, i, j, k);
        }
        if (l == 14) {
            return this.method_81(block, i, j, k);
        }
        if (l == 15) {
            return this.method_82(block, i, j, k);
        }
        if (l == 16) {
            return this.method_59(block, i, j, k, false);
        }
        if (l == 17) {
            return this.method_64(block, i, j, k, true);
        }
        if (l == 30) {
            int blockID;
            if (!(this.field_82 == null || this.field_83 != -1 || (blockID = this.field_82.getTileId(i, j + 1, k)) != 0 && Tile.BY_ID[blockID].shouldRender(this.field_82, i, j + 1, k))) {
                this.renderGrass(block, i, j, k);
            }
            return this.method_76(block, i, j, k);
        }
        if (l == 31) {
            boolean rendered = this.method_76(block, i, j, k);
            if (Minecraft.minecraftInstance.level.triggerManager.isActivated(i, j, k)) {
                Tessellator.INSTANCE.colour(1.0f, 1.0f, 1.0f);
                this.field_83 = 99;
            } else {
                this.field_83 = 115;
            }
            this.method_45(block, i, (double)j + 0.25, k, 0.0, 0.0);
            this.field_83 = -1;
            return rendered;
        }
        if (l == 32) {
            return this.renderSpikes(block, i, j, k);
        }
        if (l == 33) {
            return this.renderTable(block, i, j, k);
        }
        if (l == 34) {
            return this.renderChair(block, i, j, k);
        }
        if (l == 35) {
            return this.renderRope(block, i, j, k);
        }
        if (l == 36) {
            return this.renderBlockTree(block, i, j, k);
        }
        if (l == 37) {
            return this.renderBlockOverlay(block, i, j, k);
        }
        if (l == 38) {
            return this.renderBlockSlope(block, i, j, k);
        }
        return false;
    }

    private boolean method_81(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int l = this.field_82.getTileMeta(i, j, k);
        int i1 = BedTile.orientationOnly(l);
        boolean flag = BedTile.isFoot(l);
        float f = 0.5f;
        float f1 = 1.0f;
        float f2 = 0.8f;
        float f3 = 0.6f;
        float f4 = f1;
        float f5 = f1;
        float f6 = f1;
        float f7 = f;
        float f8 = f2;
        float f9 = f3;
        float f10 = f;
        float f11 = f2;
        float f12 = f3;
        float f13 = f;
        float f14 = f2;
        float f15 = f3;
        float f16 = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f7 * f16, f10 * f16, f13 * f16);
        int f17 = block.method_1626(this.field_82, i, j, k, 0);
        int j1 = (f17 & 0xF) << 4;
        int k1 = f17 & 0xF0;
        double d = (float)j1 / 256.0f;
        double d2 = ((double)(j1 + 16) - 0.01) / 256.0;
        double d4 = (float)k1 / 256.0f;
        double d6 = ((double)(k1 + 16) - 0.01) / 256.0;
        double d8 = (double)i + block.minX;
        double d10 = (double)i + block.maxX;
        double d12 = (double)j + block.minY + 0.1875;
        double d14 = (double)k + block.minZ;
        double d16 = (double)k + block.maxZ;
        tessellator.vertex(d8, d12, d16, d, d6);
        tessellator.vertex(d8, d12, d14, d, d4);
        tessellator.vertex(d10, d12, d14, d2, d4);
        tessellator.vertex(d10, d12, d16, d2, d6);
        float f17a = block.method_1604(this.field_82, i, j + 1, k);
        tessellator.colour(f4 * f17a, f5 * f17a, f6 * f17a);
        j1 = block.method_1626(this.field_82, i, j, k, 1);
        k1 = (j1 & 0xF) << 4;
        d = j1 & 0xF0;
        double d1 = (float)k1 / 256.0f;
        double d3 = ((double)(k1 + 16) - 0.01) / 256.0;
        double d5 = (float)d / 256.0f;
        double d7 = (d + 16.0 - 0.01) / 256.0;
        double d9 = d1;
        double d11 = d3;
        double d13 = d5;
        double d15 = d5;
        double d17 = d1;
        double d18 = d3;
        double d19 = d7;
        double d20 = d7;
        if (i1 == 0) {
            d11 = d1;
            d13 = d7;
            d17 = d3;
            d20 = d5;
        } else if (i1 == 2) {
            d9 = d3;
            d15 = d7;
            d18 = d1;
            d19 = d5;
        } else if (i1 == 3) {
            d9 = d3;
            d15 = d7;
            d18 = d1;
            d19 = d5;
            d11 = d1;
            d13 = d7;
            d17 = d3;
            d20 = d5;
        }
        double d21 = (double)i + block.minX;
        double d22 = (double)i + block.maxX;
        double d23 = (double)j + block.maxY;
        double d24 = (double)k + block.minZ;
        double d25 = (double)k + block.maxZ;
        tessellator.vertex(d22, d23, d25, d17, d19);
        tessellator.vertex(d22, d23, d24, d9, d13);
        tessellator.vertex(d21, d23, d24, d11, d15);
        tessellator.vertex(d21, d23, d25, d18, d20);
        f17 = MagicBedNumbers.field_792[i1];
        if (flag) {
            f17 = MagicBedNumbers.field_792[MagicBedNumbers.field_793[i1]];
        }
        j1 = 4;
        switch (i1) {
            case 0: {
                j1 = 5;
                break;
            }
            case 3: {
                j1 = 2;
                break;
            }
            case 1: {
                j1 = 3;
            }
        }
        if (f17 != 2 && (this.field_85 || block.method_1618(this.field_82, i, j, k - 1, 2))) {
            float f18 = block.method_1604(this.field_82, i, j, k - 1);
            if (block.minZ > 0.0) {
                f18 = f16;
            }
            tessellator.colour(f8 * f18, f11 * f18, f14 * f18);
            this.field_84 = j1 == 2;
            this.method_61(block, i, j, k, block.method_1626(this.field_82, i, j, k, 2));
        }
        if (f17 != 3 && (this.field_85 || block.method_1618(this.field_82, i, j, k + 1, 3))) {
            float f19 = block.method_1604(this.field_82, i, j, k + 1);
            if (block.maxZ < 1.0) {
                f19 = f16;
            }
            tessellator.colour(f8 * f19, f11 * f19, f14 * f19);
            this.field_84 = j1 == 3;
            this.method_65(block, i, j, k, block.method_1626(this.field_82, i, j, k, 3));
        }
        if (f17 != 4 && (this.field_85 || block.method_1618(this.field_82, i - 1, j, k, 4))) {
            float f20 = block.method_1604(this.field_82, i - 1, j, k);
            if (block.minX > 0.0) {
                f20 = f16;
            }
            tessellator.colour(f9 * f20, f12 * f20, f15 * f20);
            this.field_84 = j1 == 4;
            this.method_67(block, i, j, k, block.method_1626(this.field_82, i, j, k, 4));
        }
        if (f17 != 5 && (this.field_85 || block.method_1618(this.field_82, i + 1, j, k, 5))) {
            float f21 = block.method_1604(this.field_82, i + 1, j, k);
            if (block.maxX < 1.0) {
                f21 = f16;
            }
            tessellator.colour(f9 * f21, f12 * f21, f15 * f21);
            this.field_84 = j1 == 5;
            this.method_69(block, i, j, k, block.method_1626(this.field_82, i, j, k, 5));
        }
        this.field_84 = false;
        return true;
    }

    public boolean method_62(Tile block, int i, int j, int k) {
        int l = this.field_82.getTileMeta(i, j, k);
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        if (block.getBlockLightValue(this.field_82, i, j, k) > 0) {
            f = 1.0f;
        }
        tessellator.colour(f, f, f);
        double d = 0.4f;
        double d1 = 0.5 - d;
        double d2 = 0.2f;
        if (l == 1) {
            this.method_45(block, (double)i - d1, (double)j + d2, k, -d, 0.0);
        } else if (l == 2) {
            this.method_45(block, (double)i + d1, (double)j + d2, k, d, 0.0);
        } else if (l == 3) {
            this.method_45(block, i, (double)j + d2, (double)k - d1, 0.0, -d);
        } else if (l == 4) {
            this.method_45(block, i, (double)j + d2, (double)k + d1, 0.0, d);
        } else {
            this.method_45(block, i, j, k, 0.0, 0.0);
        }
        return true;
    }

    private boolean method_82(Tile block, int i, int j, int k) {
        int l = this.field_82.getTileMeta(i, j, k);
        int i1 = l & 3;
        int j1 = (l & 0xC) >> 2;
        this.method_76(block, i, j, k);
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        if (block.getBlockLightValue(this.field_82, i, j, k) > 0) {
            f = (f + 1.0f) * 0.5f;
        }
        tessellator.colour(f, f, f);
        double d = -0.1875;
        double d1 = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        double d4 = 0.0;
        switch (i1) {
            case 0: {
                d4 = -0.3125;
                d2 = RedstoneRepeaterTile.field_2123[j1];
                break;
            }
            case 2: {
                d4 = 0.3125;
                d2 = -RedstoneRepeaterTile.field_2123[j1];
                break;
            }
            case 3: {
                d3 = -0.3125;
                d1 = RedstoneRepeaterTile.field_2123[j1];
                break;
            }
            case 1: {
                d3 = 0.3125;
                d1 = -RedstoneRepeaterTile.field_2123[j1];
            }
        }
        this.method_45(block, (double)i + d1, (double)j + d, (double)k + d2, 0.0, 0.0);
        this.method_45(block, (double)i + d3, (double)j + d, (double)k + d4, 0.0, 0.0);
        int k1 = block.getTextureForSide(1);
        int l1 = (k1 & 0xF) << 4;
        int i2 = k1 & 0xF0;
        double d5 = (float)l1 / 256.0f;
        double d6 = ((float)l1 + 15.99f) / 256.0f;
        double d7 = (float)i2 / 256.0f;
        double d8 = ((float)i2 + 15.99f) / 256.0f;
        float f1 = 0.125f;
        float f2 = i + 1;
        float f3 = i + 1;
        float f4 = i + 0;
        float f5 = i + 0;
        float f6 = k + 0;
        float f7 = k + 1;
        float f8 = k + 1;
        float f9 = k + 0;
        float f10 = (float)j + f1;
        if (i1 == 2) {
            f2 = f3 = (float)(i + 0);
            f4 = f5 = (float)(i + 1);
            f6 = f9 = (float)(k + 1);
            f7 = f8 = (float)(k + 0);
        } else if (i1 == 3) {
            f2 = f5 = (float)(i + 0);
            f3 = f4 = (float)(i + 1);
            f6 = f7 = (float)(k + 0);
            f8 = f9 = (float)(k + 1);
        } else if (i1 == 1) {
            f2 = f5 = (float)(i + 1);
            f3 = f4 = (float)(i + 0);
            f6 = f7 = (float)(k + 1);
            f8 = f9 = (float)(k + 0);
        }
        tessellator.vertex(f5, f10, f9, d5, d7);
        tessellator.vertex(f4, f10, f8, d5, d8);
        tessellator.vertex(f3, f10, f7, d6, d8);
        tessellator.vertex(f2, f10, f6, d6, d7);
        return true;
    }

    public void method_66(Tile block, int i, int j, int k) {
        this.field_85 = true;
        this.method_59(block, i, j, k, true);
        this.field_85 = false;
    }

    private boolean method_59(Tile block, int i, int j, int k, boolean flag) {
        int l = this.field_82.getTileMeta(i, j, k);
        boolean flag1 = flag || (l & 8) != 0;
        int i1 = PistonTile.method_760(l);
        if (flag1) {
            switch (i1) {
                case 0: {
                    this.field_86 = 3;
                    this.field_87 = 3;
                    this.field_88 = 3;
                    this.field_89 = 3;
                    block.setBoundingBox(0.0f, 0.25f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 1: {
                    block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
                    break;
                }
                case 2: {
                    this.field_88 = 1;
                    this.field_89 = 2;
                    block.setBoundingBox(0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 3: {
                    this.field_88 = 2;
                    this.field_89 = 1;
                    this.field_90 = 3;
                    this.field_91 = 3;
                    block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.75f);
                    break;
                }
                case 4: {
                    this.field_86 = 1;
                    this.field_87 = 2;
                    this.field_90 = 2;
                    this.field_91 = 1;
                    block.setBoundingBox(0.25f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 5: {
                    this.field_86 = 2;
                    this.field_87 = 1;
                    this.field_90 = 1;
                    this.field_91 = 2;
                    block.setBoundingBox(0.0f, 0.0f, 0.0f, 0.75f, 1.0f, 1.0f);
                }
            }
            this.method_76(block, i, j, k);
            this.field_86 = 0;
            this.field_87 = 0;
            this.field_88 = 0;
            this.field_89 = 0;
            this.field_90 = 0;
            this.field_91 = 0;
            block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        } else {
            switch (i1) {
                case 0: {
                    this.field_86 = 3;
                    this.field_87 = 3;
                    this.field_88 = 3;
                    this.field_89 = 3;
                    break;
                }
                case 2: {
                    this.field_88 = 1;
                    this.field_89 = 2;
                    break;
                }
                case 3: {
                    this.field_88 = 2;
                    this.field_89 = 1;
                    this.field_90 = 3;
                    this.field_91 = 3;
                    break;
                }
                case 4: {
                    this.field_86 = 1;
                    this.field_87 = 2;
                    this.field_90 = 2;
                    this.field_91 = 1;
                    break;
                }
                case 5: {
                    this.field_86 = 2;
                    this.field_87 = 1;
                    this.field_90 = 1;
                    this.field_91 = 2;
                }
            }
            this.method_76(block, i, j, k);
            this.field_86 = 0;
            this.field_87 = 0;
            this.field_88 = 0;
            this.field_89 = 0;
            this.field_90 = 0;
            this.field_91 = 0;
        }
        return true;
    }

    private void method_41(double d, double d1, double d2, double d3, double d4, double d5, float f, double d6) {
        int i = 108;
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        Tessellator tessellator = Tessellator.INSTANCE;
        double d7 = (float)(j + 0) / 256.0f;
        double d8 = (float)(k + 0) / 256.0f;
        double d9 = ((double)j + d6 - 0.01) / 256.0;
        double d10 = ((double)((float)k + 4.0f) - 0.01) / 256.0;
        tessellator.colour(f, f, f);
        tessellator.vertex(d, d3, d4, d9, d8);
        tessellator.vertex(d, d2, d4, d7, d8);
        tessellator.vertex(d1, d2, d5, d7, d10);
        tessellator.vertex(d1, d3, d5, d9, d10);
    }

    private void method_54(double d, double d1, double d2, double d3, double d4, double d5, float f, double d6) {
        int i = 108;
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        Tessellator tessellator = Tessellator.INSTANCE;
        double d7 = (float)(j + 0) / 256.0f;
        double d8 = (float)(k + 0) / 256.0f;
        double d9 = ((double)j + d6 - 0.01) / 256.0;
        double d10 = ((double)((float)k + 4.0f) - 0.01) / 256.0;
        tessellator.colour(f, f, f);
        tessellator.vertex(d, d2, d5, d9, d8);
        tessellator.vertex(d, d2, d4, d7, d8);
        tessellator.vertex(d1, d3, d4, d7, d10);
        tessellator.vertex(d1, d3, d5, d9, d10);
    }

    private void method_60(double d, double d1, double d2, double d3, double d4, double d5, float f, double d6) {
        int i = 108;
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        Tessellator tessellator = Tessellator.INSTANCE;
        double d7 = (float)(j + 0) / 256.0f;
        double d8 = (float)(k + 0) / 256.0f;
        double d9 = ((double)j + d6 - 0.01) / 256.0;
        double d10 = ((double)((float)k + 4.0f) - 0.01) / 256.0;
        tessellator.colour(f, f, f);
        tessellator.vertex(d1, d2, d4, d9, d8);
        tessellator.vertex(d, d2, d4, d7, d8);
        tessellator.vertex(d, d3, d5, d7, d10);
        tessellator.vertex(d1, d3, d5, d9, d10);
    }

    public void method_52(Tile block, int i, int j, int k, boolean flag) {
        this.field_85 = true;
        this.method_64(block, i, j, k, flag);
        this.field_85 = false;
    }

    private boolean method_64(Tile block, int i, int j, int k, boolean flag) {
        int l = this.field_82.getTileMeta(i, j, k);
        int i1 = PistonHead.method_727(l);
        float f = block.method_1604(this.field_82, i, j, k);
        float f1 = flag ? 1.0f : 0.5f;
        double d = flag ? 16.0 : 8.0;
        switch (i1) {
            case 0: {
                this.field_86 = 3;
                this.field_87 = 3;
                this.field_88 = 3;
                this.field_89 = 3;
                block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                this.method_76(block, i, j, k);
                this.method_41((float)i + 0.375f, (float)i + 0.625f, (float)j + 0.25f, (float)j + 0.25f + f1, (float)k + 0.625f, (float)k + 0.625f, f * 0.8f, d);
                this.method_41((float)i + 0.625f, (float)i + 0.375f, (float)j + 0.25f, (float)j + 0.25f + f1, (float)k + 0.375f, (float)k + 0.375f, f * 0.8f, d);
                this.method_41((float)i + 0.375f, (float)i + 0.375f, (float)j + 0.25f, (float)j + 0.25f + f1, (float)k + 0.375f, (float)k + 0.625f, f * 0.6f, d);
                this.method_41((float)i + 0.625f, (float)i + 0.625f, (float)j + 0.25f, (float)j + 0.25f + f1, (float)k + 0.625f, (float)k + 0.375f, f * 0.6f, d);
                break;
            }
            case 1: {
                block.setBoundingBox(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                this.method_76(block, i, j, k);
                this.method_41((float)i + 0.375f, (float)i + 0.625f, (float)j - 0.25f + 1.0f - f1, (float)j - 0.25f + 1.0f, (float)k + 0.625f, (float)k + 0.625f, f * 0.8f, d);
                this.method_41((float)i + 0.625f, (float)i + 0.375f, (float)j - 0.25f + 1.0f - f1, (float)j - 0.25f + 1.0f, (float)k + 0.375f, (float)k + 0.375f, f * 0.8f, d);
                this.method_41((float)i + 0.375f, (float)i + 0.375f, (float)j - 0.25f + 1.0f - f1, (float)j - 0.25f + 1.0f, (float)k + 0.375f, (float)k + 0.625f, f * 0.6f, d);
                this.method_41((float)i + 0.625f, (float)i + 0.625f, (float)j - 0.25f + 1.0f - f1, (float)j - 0.25f + 1.0f, (float)k + 0.625f, (float)k + 0.375f, f * 0.6f, d);
                break;
            }
            case 2: {
                this.field_88 = 1;
                this.field_89 = 2;
                block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                this.method_76(block, i, j, k);
                this.method_54((float)i + 0.375f, (float)i + 0.375f, (float)j + 0.625f, (float)j + 0.375f, (float)k + 0.25f, (float)k + 0.25f + f1, f * 0.6f, d);
                this.method_54((float)i + 0.625f, (float)i + 0.625f, (float)j + 0.375f, (float)j + 0.625f, (float)k + 0.25f, (float)k + 0.25f + f1, f * 0.6f, d);
                this.method_54((float)i + 0.375f, (float)i + 0.625f, (float)j + 0.375f, (float)j + 0.375f, (float)k + 0.25f, (float)k + 0.25f + f1, f * 0.5f, d);
                this.method_54((float)i + 0.625f, (float)i + 0.375f, (float)j + 0.625f, (float)j + 0.625f, (float)k + 0.25f, (float)k + 0.25f + f1, f, d);
                break;
            }
            case 3: {
                this.field_88 = 2;
                this.field_89 = 1;
                this.field_90 = 3;
                this.field_91 = 3;
                block.setBoundingBox(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                this.method_76(block, i, j, k);
                this.method_54((float)i + 0.375f, (float)i + 0.375f, (float)j + 0.625f, (float)j + 0.375f, (float)k - 0.25f + 1.0f - f1, (float)k - 0.25f + 1.0f, f * 0.6f, d);
                this.method_54((float)i + 0.625f, (float)i + 0.625f, (float)j + 0.375f, (float)j + 0.625f, (float)k - 0.25f + 1.0f - f1, (float)k - 0.25f + 1.0f, f * 0.6f, d);
                this.method_54((float)i + 0.375f, (float)i + 0.625f, (float)j + 0.375f, (float)j + 0.375f, (float)k - 0.25f + 1.0f - f1, (float)k - 0.25f + 1.0f, f * 0.5f, d);
                this.method_54((float)i + 0.625f, (float)i + 0.375f, (float)j + 0.625f, (float)j + 0.625f, (float)k - 0.25f + 1.0f - f1, (float)k - 0.25f + 1.0f, f, d);
                break;
            }
            case 4: {
                this.field_86 = 1;
                this.field_87 = 2;
                this.field_90 = 2;
                this.field_91 = 1;
                block.setBoundingBox(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                this.method_76(block, i, j, k);
                this.method_60((float)i + 0.25f, (float)i + 0.25f + f1, (float)j + 0.375f, (float)j + 0.375f, (float)k + 0.625f, (float)k + 0.375f, f * 0.5f, d);
                this.method_60((float)i + 0.25f, (float)i + 0.25f + f1, (float)j + 0.625f, (float)j + 0.625f, (float)k + 0.375f, (float)k + 0.625f, f, d);
                this.method_60((float)i + 0.25f, (float)i + 0.25f + f1, (float)j + 0.375f, (float)j + 0.625f, (float)k + 0.375f, (float)k + 0.375f, f * 0.6f, d);
                this.method_60((float)i + 0.25f, (float)i + 0.25f + f1, (float)j + 0.625f, (float)j + 0.375f, (float)k + 0.625f, (float)k + 0.625f, f * 0.6f, d);
                break;
            }
            case 5: {
                this.field_86 = 2;
                this.field_87 = 1;
                this.field_90 = 1;
                this.field_91 = 2;
                block.setBoundingBox(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                this.method_76(block, i, j, k);
                this.method_60((float)i - 0.25f + 1.0f - f1, (float)i - 0.25f + 1.0f, (float)j + 0.375f, (float)j + 0.375f, (float)k + 0.625f, (float)k + 0.375f, f * 0.5f, d);
                this.method_60((float)i - 0.25f + 1.0f - f1, (float)i - 0.25f + 1.0f, (float)j + 0.625f, (float)j + 0.625f, (float)k + 0.375f, (float)k + 0.625f, f, d);
                this.method_60((float)i - 0.25f + 1.0f - f1, (float)i - 0.25f + 1.0f, (float)j + 0.375f, (float)j + 0.625f, (float)k + 0.375f, (float)k + 0.375f, f * 0.6f, d);
                this.method_60((float)i - 0.25f + 1.0f - f1, (float)i - 0.25f + 1.0f, (float)j + 0.625f, (float)j + 0.375f, (float)k + 0.625f, (float)k + 0.625f, f * 0.6f, d);
            }
        }
        this.field_86 = 0;
        this.field_87 = 0;
        this.field_88 = 0;
        this.field_89 = 0;
        this.field_90 = 0;
        this.field_91 = 0;
        block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return true;
    }

    public boolean method_68(Tile block, int i, int j, int k) {
        boolean flag1;
        int l = this.field_82.getTileMeta(i, j, k);
        int i1 = l & 7;
        boolean flag = (l & 8) > 0;
        Tessellator tessellator = Tessellator.INSTANCE;
        boolean bl = flag1 = this.field_83 >= 0;
        if (!flag1) {
            this.field_83 = Tile.COBBLESTONE.tex;
        }
        float f = 0.25f;
        float f1 = 0.1875f;
        float f2 = 0.1875f;
        if (i1 == 5) {
            block.setBoundingBox(0.5f - f1, 0.0f, 0.5f - f, 0.5f + f1, f2, 0.5f + f);
        } else if (i1 == 6) {
            block.setBoundingBox(0.5f - f, 0.0f, 0.5f - f1, 0.5f + f, f2, 0.5f + f1);
        } else if (i1 == 4) {
            block.setBoundingBox(0.5f - f1, 0.5f - f, 1.0f - f2, 0.5f + f1, 0.5f + f, 1.0f);
        } else if (i1 == 3) {
            block.setBoundingBox(0.5f - f1, 0.5f - f, 0.0f, 0.5f + f1, 0.5f + f, f2);
        } else if (i1 == 2) {
            block.setBoundingBox(1.0f - f2, 0.5f - f, 0.5f - f1, 1.0f, 0.5f + f, 0.5f + f1);
        } else if (i1 == 1) {
            block.setBoundingBox(0.0f, 0.5f - f, 0.5f - f1, f2, 0.5f + f, 0.5f + f1);
        }
        this.method_76(block, i, j, k);
        if (!flag1) {
            this.field_83 = -1;
        }
        float f3 = block.method_1604(this.field_82, i, j, k);
        if (block.getBlockLightValue(this.field_82, i, j, k) > 0) {
            f3 = 1.0f;
        }
        tessellator.colour(f3, f3, f3);
        int j1 = block.getTextureForSide(0);
        if (this.field_83 >= 0) {
            j1 = this.field_83;
        }
        int k1 = (j1 & 0xF) << 4;
        int l1 = j1 & 0xF0;
        float f4 = (float)k1 / 256.0f;
        float f5 = ((float)k1 + 15.99f) / 256.0f;
        float f6 = (float)l1 / 256.0f;
        float f7 = ((float)l1 + 15.99f) / 256.0f;
        Vec3f[] avec3d = new Vec3f[8];
        float f8 = 0.0625f;
        float f9 = 0.0625f;
        float f10 = 0.625f;
        avec3d[0] = Vec3f.from(-f8, 0.0, -f9);
        avec3d[1] = Vec3f.from(f8, 0.0, -f9);
        avec3d[2] = Vec3f.from(f8, 0.0, f9);
        avec3d[3] = Vec3f.from(-f8, 0.0, f9);
        avec3d[4] = Vec3f.from(-f8, f10, -f9);
        avec3d[5] = Vec3f.from(f8, f10, -f9);
        avec3d[6] = Vec3f.from(f8, f10, f9);
        avec3d[7] = Vec3f.from(-f8, f10, f9);
        for (int i2 = 0; i2 < 8; ++i2) {
            if (flag) {
                avec3d[i2].z -= 0.0625;
                avec3d[i2].method_1306(0.6981317f);
            } else {
                avec3d[i2].z += 0.0625;
                avec3d[i2].method_1306(-0.6981317f);
            }
            if (i1 == 6) {
                avec3d[i2].method_1308(1.570796f);
            }
            if (i1 < 5) {
                avec3d[i2].y -= 0.375;
                avec3d[i2].method_1306(1.570796f);
                if (i1 == 4) {
                    avec3d[i2].method_1308(0.0f);
                }
                if (i1 == 3) {
                    avec3d[i2].method_1308(3.141593f);
                }
                if (i1 == 2) {
                    avec3d[i2].method_1308(1.570796f);
                }
                if (i1 == 1) {
                    avec3d[i2].method_1308(-1.570796f);
                }
                avec3d[i2].x += (double)i + 0.5;
                avec3d[i2].y += (double)((float)j + 0.5f);
                avec3d[i2].z += (double)k + 0.5;
                continue;
            }
            avec3d[i2].x += (double)i + 0.5;
            avec3d[i2].y += (double)((float)j + 0.125f);
            avec3d[i2].z += (double)k + 0.5;
        }
        Vec3f vec3d = null;
        Vec3f vec3d1 = null;
        Vec3f vec3d2 = null;
        Vec3f vec3d3 = null;
        for (int j2 = 0; j2 < 6; ++j2) {
            if (j2 == 0) {
                f4 = (float)(k1 + 7) / 256.0f;
                f5 = ((float)(k1 + 9) - 0.01f) / 256.0f;
                f6 = (float)(l1 + 6) / 256.0f;
                f7 = ((float)(l1 + 8) - 0.01f) / 256.0f;
            } else if (j2 == 2) {
                f4 = (float)(k1 + 7) / 256.0f;
                f5 = ((float)(k1 + 9) - 0.01f) / 256.0f;
                f6 = (float)(l1 + 6) / 256.0f;
                f7 = ((float)(l1 + 16) - 0.01f) / 256.0f;
            }
            if (j2 == 0) {
                vec3d = avec3d[0];
                vec3d1 = avec3d[1];
                vec3d2 = avec3d[2];
                vec3d3 = avec3d[3];
            } else if (j2 == 1) {
                vec3d = avec3d[7];
                vec3d1 = avec3d[6];
                vec3d2 = avec3d[5];
                vec3d3 = avec3d[4];
            } else if (j2 == 2) {
                vec3d = avec3d[1];
                vec3d1 = avec3d[0];
                vec3d2 = avec3d[4];
                vec3d3 = avec3d[5];
            } else if (j2 == 3) {
                vec3d = avec3d[2];
                vec3d1 = avec3d[1];
                vec3d2 = avec3d[5];
                vec3d3 = avec3d[6];
            } else if (j2 == 4) {
                vec3d = avec3d[3];
                vec3d1 = avec3d[2];
                vec3d2 = avec3d[6];
                vec3d3 = avec3d[7];
            } else if (j2 == 5) {
                vec3d = avec3d[0];
                vec3d1 = avec3d[3];
                vec3d2 = avec3d[7];
                vec3d3 = avec3d[4];
            }
            tessellator.vertex(vec3d.x, vec3d.y, vec3d.z, f4, f7);
            tessellator.vertex(vec3d1.x, vec3d1.y, vec3d1.z, f5, f7);
            tessellator.vertex(vec3d2.x, vec3d2.y, vec3d2.z, f5, f6);
            tessellator.vertex(vec3d3.x, vec3d3.y, vec3d3.z, f4, f6);
        }
        return true;
    }

    public boolean method_70(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int l = block.getTextureForSide(0);
        if (this.field_83 >= 0) {
            l = this.field_83;
        }
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        int i1 = (l & 0xF) << 4;
        int j1 = l & 0xF0;
        double d = (float)i1 / 256.0f;
        double d2 = ((float)i1 + 15.99f) / 256.0f;
        double d4 = (float)j1 / 256.0f;
        double d6 = ((float)j1 + 15.99f) / 256.0f;
        float f1 = 1.4f;
        if (this.field_82.canSuffocate(i, j - 1, k) || Tile.FIRE.method_1824(this.field_82, i, j - 1, k)) {
            double d8 = (double)i + 0.5 + 0.2;
            double d9 = (double)i + 0.5 - 0.2;
            double d12 = (double)k + 0.5 + 0.2;
            double d14 = (double)k + 0.5 - 0.2;
            double d16 = (double)i + 0.5 - 0.3;
            double d18 = (double)i + 0.5 + 0.3;
            double d20 = (double)k + 0.5 - 0.3;
            double d22 = (double)k + 0.5 + 0.3;
            tessellator.vertex(d16, (float)j + f1, k + 1, d2, d4);
            tessellator.vertex(d8, j + 0, k + 1, d2, d6);
            tessellator.vertex(d8, j + 0, k + 0, d, d6);
            tessellator.vertex(d16, (float)j + f1, k + 0, d, d4);
            tessellator.vertex(d18, (float)j + f1, k + 0, d2, d4);
            tessellator.vertex(d9, j + 0, k + 0, d2, d6);
            tessellator.vertex(d9, j + 0, k + 1, d, d6);
            tessellator.vertex(d18, (float)j + f1, k + 1, d, d4);
            d = (float)i1 / 256.0f;
            d2 = ((float)i1 + 15.99f) / 256.0f;
            d4 = (float)(j1 + 16) / 256.0f;
            d6 = ((float)j1 + 15.99f + 16.0f) / 256.0f;
            tessellator.vertex(i + 1, (float)j + f1, d22, d2, d4);
            tessellator.vertex(i + 1, j + 0, d14, d2, d6);
            tessellator.vertex(i + 0, j + 0, d14, d, d6);
            tessellator.vertex(i + 0, (float)j + f1, d22, d, d4);
            tessellator.vertex(i + 0, (float)j + f1, d20, d2, d4);
            tessellator.vertex(i + 0, j + 0, d12, d2, d6);
            tessellator.vertex(i + 1, j + 0, d12, d, d6);
            tessellator.vertex(i + 1, (float)j + f1, d20, d, d4);
            d8 = (double)i + 0.5 - 0.5;
            d9 = (double)i + 0.5 + 0.5;
            d12 = (double)k + 0.5 - 0.5;
            d14 = (double)k + 0.5 + 0.5;
            d16 = (double)i + 0.5 - 0.4;
            d18 = (double)i + 0.5 + 0.4;
            d20 = (double)k + 0.5 - 0.4;
            d22 = (double)k + 0.5 + 0.4;
            tessellator.vertex(d16, (float)j + f1, k + 0, d, d4);
            tessellator.vertex(d8, j + 0, k + 0, d, d6);
            tessellator.vertex(d8, j + 0, k + 1, d2, d6);
            tessellator.vertex(d16, (float)j + f1, k + 1, d2, d4);
            tessellator.vertex(d18, (float)j + f1, k + 1, d, d4);
            tessellator.vertex(d9, j + 0, k + 1, d, d6);
            tessellator.vertex(d9, j + 0, k + 0, d2, d6);
            tessellator.vertex(d18, (float)j + f1, k + 0, d2, d4);
            d = (float)i1 / 256.0f;
            d2 = ((float)i1 + 15.99f) / 256.0f;
            d4 = (float)j1 / 256.0f;
            d6 = ((float)j1 + 15.99f) / 256.0f;
            tessellator.vertex(i + 0, (float)j + f1, d22, d, d4);
            tessellator.vertex(i + 0, j + 0, d14, d, d6);
            tessellator.vertex(i + 1, j + 0, d14, d2, d6);
            tessellator.vertex(i + 1, (float)j + f1, d22, d2, d4);
            tessellator.vertex(i + 1, (float)j + f1, d20, d, d4);
            tessellator.vertex(i + 1, j + 0, d12, d, d6);
            tessellator.vertex(i + 0, j + 0, d12, d2, d6);
            tessellator.vertex(i + 0, (float)j + f1, d20, d2, d4);
        } else {
            float f3 = 0.2f;
            float f4 = 0.0625f;
            if ((i + j + k & 1) == 1) {
                d = (float)i1 / 256.0f;
                d2 = ((float)i1 + 15.99f) / 256.0f;
                d4 = (float)(j1 + 16) / 256.0f;
                d6 = ((float)j1 + 15.99f + 16.0f) / 256.0f;
            }
            if ((i / 2 + j / 2 + k / 2 & 1) == 1) {
                double d10 = d2;
                d2 = d;
                d = d10;
            }
            if (Tile.FIRE.method_1824(this.field_82, i - 1, j, k)) {
                tessellator.vertex((float)i + f3, (float)j + f1 + f4, k + 1, d2, d4);
                tessellator.vertex(i + 0, (float)(j + 0) + f4, k + 1, d2, d6);
                tessellator.vertex(i + 0, (float)(j + 0) + f4, k + 0, d, d6);
                tessellator.vertex((float)i + f3, (float)j + f1 + f4, k + 0, d, d4);
                tessellator.vertex((float)i + f3, (float)j + f1 + f4, k + 0, d, d4);
                tessellator.vertex(i + 0, (float)(j + 0) + f4, k + 0, d, d6);
                tessellator.vertex(i + 0, (float)(j + 0) + f4, k + 1, d2, d6);
                tessellator.vertex((float)i + f3, (float)j + f1 + f4, k + 1, d2, d4);
            }
            if (Tile.FIRE.method_1824(this.field_82, i + 1, j, k)) {
                tessellator.vertex((float)(i + 1) - f3, (float)j + f1 + f4, k + 0, d, d4);
                tessellator.vertex(i + 1 - 0, (float)(j + 0) + f4, k + 0, d, d6);
                tessellator.vertex(i + 1 - 0, (float)(j + 0) + f4, k + 1, d2, d6);
                tessellator.vertex((float)(i + 1) - f3, (float)j + f1 + f4, k + 1, d2, d4);
                tessellator.vertex((float)(i + 1) - f3, (float)j + f1 + f4, k + 1, d2, d4);
                tessellator.vertex(i + 1 - 0, (float)(j + 0) + f4, k + 1, d2, d6);
                tessellator.vertex(i + 1 - 0, (float)(j + 0) + f4, k + 0, d, d6);
                tessellator.vertex((float)(i + 1) - f3, (float)j + f1 + f4, k + 0, d, d4);
            }
            if (Tile.FIRE.method_1824(this.field_82, i, j, k - 1)) {
                tessellator.vertex(i + 0, (float)j + f1 + f4, (float)k + f3, d2, d4);
                tessellator.vertex(i + 0, (float)(j + 0) + f4, k + 0, d2, d6);
                tessellator.vertex(i + 1, (float)(j + 0) + f4, k + 0, d, d6);
                tessellator.vertex(i + 1, (float)j + f1 + f4, (float)k + f3, d, d4);
                tessellator.vertex(i + 1, (float)j + f1 + f4, (float)k + f3, d, d4);
                tessellator.vertex(i + 1, (float)(j + 0) + f4, k + 0, d, d6);
                tessellator.vertex(i + 0, (float)(j + 0) + f4, k + 0, d2, d6);
                tessellator.vertex(i + 0, (float)j + f1 + f4, (float)k + f3, d2, d4);
            }
            if (Tile.FIRE.method_1824(this.field_82, i, j, k + 1)) {
                tessellator.vertex(i + 1, (float)j + f1 + f4, (float)(k + 1) - f3, d, d4);
                tessellator.vertex(i + 1, (float)(j + 0) + f4, k + 1 - 0, d, d6);
                tessellator.vertex(i + 0, (float)(j + 0) + f4, k + 1 - 0, d2, d6);
                tessellator.vertex(i + 0, (float)j + f1 + f4, (float)(k + 1) - f3, d2, d4);
                tessellator.vertex(i + 0, (float)j + f1 + f4, (float)(k + 1) - f3, d2, d4);
                tessellator.vertex(i + 0, (float)(j + 0) + f4, k + 1 - 0, d2, d6);
                tessellator.vertex(i + 1, (float)(j + 0) + f4, k + 1 - 0, d, d6);
                tessellator.vertex(i + 1, (float)j + f1 + f4, (float)(k + 1) - f3, d, d4);
            }
            if (Tile.FIRE.method_1824(this.field_82, i, j + 1, k)) {
                double d11 = (double)i + 0.5 + 0.5;
                double d13 = (double)i + 0.5 - 0.5;
                double d15 = (double)k + 0.5 + 0.5;
                double d17 = (double)k + 0.5 - 0.5;
                double d19 = (double)i + 0.5 - 0.5;
                double d21 = (double)i + 0.5 + 0.5;
                double d23 = (double)k + 0.5 - 0.5;
                double d24 = (double)k + 0.5 + 0.5;
                double d1 = (float)i1 / 256.0f;
                double d3 = ((float)i1 + 15.99f) / 256.0f;
                double d5 = (float)j1 / 256.0f;
                double d7 = ((float)j1 + 15.99f) / 256.0f;
                float f2 = -0.2f;
                if ((i + ++j + k & 1) == 0) {
                    tessellator.vertex(d19, (float)j + f2, k + 0, d3, d5);
                    tessellator.vertex(d11, j + 0, k + 0, d3, d7);
                    tessellator.vertex(d11, j + 0, k + 1, d1, d7);
                    tessellator.vertex(d19, (float)j + f2, k + 1, d1, d5);
                    d1 = (float)i1 / 256.0f;
                    d3 = ((float)i1 + 15.99f) / 256.0f;
                    d5 = (float)(j1 + 16) / 256.0f;
                    d7 = ((float)j1 + 15.99f + 16.0f) / 256.0f;
                    tessellator.vertex(d21, (float)j + f2, k + 1, d3, d5);
                    tessellator.vertex(d13, j + 0, k + 1, d3, d7);
                    tessellator.vertex(d13, j + 0, k + 0, d1, d7);
                    tessellator.vertex(d21, (float)j + f2, k + 0, d1, d5);
                } else {
                    tessellator.vertex(i + 0, (float)j + f2, d24, d3, d5);
                    tessellator.vertex(i + 0, j + 0, d17, d3, d7);
                    tessellator.vertex(i + 1, j + 0, d17, d1, d7);
                    tessellator.vertex(i + 1, (float)j + f2, d24, d1, d5);
                    d1 = (float)i1 / 256.0f;
                    d3 = ((float)i1 + 15.99f) / 256.0f;
                    d5 = (float)(j1 + 16) / 256.0f;
                    d7 = ((float)j1 + 15.99f + 16.0f) / 256.0f;
                    tessellator.vertex(i + 1, (float)j + f2, d23, d3, d5);
                    tessellator.vertex(i + 1, j + 0, d15, d3, d7);
                    tessellator.vertex(i + 0, j + 0, d15, d1, d7);
                    tessellator.vertex(i + 0, (float)j + f2, d23, d1, d5);
                }
            }
        }
        return true;
    }

    public boolean method_71(Tile block, int i, int j, int k) {
        boolean flag3;
        Tessellator tessellator = Tessellator.INSTANCE;
        int l = this.field_82.getTileMeta(i, j, k);
        int i1 = block.getTextureForSide(1, l);
        if (this.field_83 >= 0) {
            i1 = this.field_83;
        }
        float f = block.method_1604(this.field_82, i, j, k);
        float f1 = (float)l / 15.0f;
        float f2 = f1 * 0.6f + 0.4f;
        if (l == 0) {
            f2 = 0.3f;
        }
        float f3 = f1 * f1 * 0.7f - 0.5f;
        float f4 = f1 * f1 * 0.6f - 0.7f;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        tessellator.colour(f * f2, f * f3, f * f4);
        int j1 = (i1 & 0xF) << 4;
        int k1 = i1 & 0xF0;
        double d = (float)j1 / 256.0f;
        double d2 = ((float)j1 + 15.99f) / 256.0f;
        double d4 = (float)k1 / 256.0f;
        double d6 = ((float)k1 + 15.99f) / 256.0f;
        boolean flag = RedstoneDustTile.method_1287(this.field_82, i - 1, j, k, 1) || !this.field_82.canSuffocate(i - 1, j, k) && RedstoneDustTile.method_1287(this.field_82, i - 1, j - 1, k, -1);
        boolean flag1 = RedstoneDustTile.method_1287(this.field_82, i + 1, j, k, 3) || !this.field_82.canSuffocate(i + 1, j, k) && RedstoneDustTile.method_1287(this.field_82, i + 1, j - 1, k, -1);
        boolean flag2 = RedstoneDustTile.method_1287(this.field_82, i, j, k - 1, 2) || !this.field_82.canSuffocate(i, j, k - 1) && RedstoneDustTile.method_1287(this.field_82, i, j - 1, k - 1, -1);
        boolean bl = flag3 = RedstoneDustTile.method_1287(this.field_82, i, j, k + 1, 0) || !this.field_82.canSuffocate(i, j, k + 1) && RedstoneDustTile.method_1287(this.field_82, i, j - 1, k + 1, -1);
        if (!this.field_82.canSuffocate(i, j + 1, k)) {
            if (this.field_82.canSuffocate(i - 1, j, k) && RedstoneDustTile.method_1287(this.field_82, i - 1, j + 1, k, -1)) {
                flag = true;
            }
            if (this.field_82.canSuffocate(i + 1, j, k) && RedstoneDustTile.method_1287(this.field_82, i + 1, j + 1, k, -1)) {
                flag1 = true;
            }
            if (this.field_82.canSuffocate(i, j, k - 1) && RedstoneDustTile.method_1287(this.field_82, i, j + 1, k - 1, -1)) {
                flag2 = true;
            }
            if (this.field_82.canSuffocate(i, j, k + 1) && RedstoneDustTile.method_1287(this.field_82, i, j + 1, k + 1, -1)) {
                flag3 = true;
            }
        }
        float f5 = i + 0;
        float f6 = i + 1;
        float f7 = k + 0;
        float f8 = k + 1;
        int byte0 = 0;
        if ((flag || flag1) && !flag2 && !flag3) {
            byte0 = 1;
        }
        if ((flag2 || flag3) && !flag1 && !flag) {
            byte0 = 2;
        }
        if (byte0 != 0) {
            d = (float)(j1 + 16) / 256.0f;
            d2 = ((float)(j1 + 16) + 15.99f) / 256.0f;
            d4 = (float)k1 / 256.0f;
            d6 = ((float)k1 + 15.99f) / 256.0f;
        }
        if (byte0 == 0) {
            if (flag1 || flag2 || flag3 || flag) {
                if (!flag) {
                    f5 += 0.3125f;
                }
                if (!flag) {
                    d += 0.01953125;
                }
                if (!flag1) {
                    f6 -= 0.3125f;
                }
                if (!flag1) {
                    d2 -= 0.01953125;
                }
                if (!flag2) {
                    f7 += 0.3125f;
                }
                if (!flag2) {
                    d4 += 0.01953125;
                }
                if (!flag3) {
                    f8 -= 0.3125f;
                }
                if (!flag3) {
                    d6 -= 0.01953125;
                }
            }
            tessellator.vertex(f6, (float)j + 0.015625f, f8, d2, d6);
            tessellator.vertex(f6, (float)j + 0.015625f, f7, d2, d4);
            tessellator.vertex(f5, (float)j + 0.015625f, f7, d, d4);
            tessellator.vertex(f5, (float)j + 0.015625f, f8, d, d6);
            tessellator.colour(f, f, f);
            tessellator.vertex(f6, (float)j + 0.015625f, f8, d2, d6 + 0.0625);
            tessellator.vertex(f6, (float)j + 0.015625f, f7, d2, d4 + 0.0625);
            tessellator.vertex(f5, (float)j + 0.015625f, f7, d, d4 + 0.0625);
            tessellator.vertex(f5, (float)j + 0.015625f, f8, d, d6 + 0.0625);
        } else if (byte0 == 1) {
            tessellator.vertex(f6, (float)j + 0.015625f, f8, d2, d6);
            tessellator.vertex(f6, (float)j + 0.015625f, f7, d2, d4);
            tessellator.vertex(f5, (float)j + 0.015625f, f7, d, d4);
            tessellator.vertex(f5, (float)j + 0.015625f, f8, d, d6);
            tessellator.colour(f, f, f);
            tessellator.vertex(f6, (float)j + 0.015625f, f8, d2, d6 + 0.0625);
            tessellator.vertex(f6, (float)j + 0.015625f, f7, d2, d4 + 0.0625);
            tessellator.vertex(f5, (float)j + 0.015625f, f7, d, d4 + 0.0625);
            tessellator.vertex(f5, (float)j + 0.015625f, f8, d, d6 + 0.0625);
        } else if (byte0 == 2) {
            tessellator.vertex(f6, (float)j + 0.015625f, f8, d2, d6);
            tessellator.vertex(f6, (float)j + 0.015625f, f7, d, d6);
            tessellator.vertex(f5, (float)j + 0.015625f, f7, d, d4);
            tessellator.vertex(f5, (float)j + 0.015625f, f8, d2, d4);
            tessellator.colour(f, f, f);
            tessellator.vertex(f6, (float)j + 0.015625f, f8, d2, d6 + 0.0625);
            tessellator.vertex(f6, (float)j + 0.015625f, f7, d, d6 + 0.0625);
            tessellator.vertex(f5, (float)j + 0.015625f, f7, d, d4 + 0.0625);
            tessellator.vertex(f5, (float)j + 0.015625f, f8, d2, d4 + 0.0625);
        }
        if (!this.field_82.canSuffocate(i, j + 1, k)) {
            double d1 = (float)(j1 + 16) / 256.0f;
            double d3 = ((float)(j1 + 16) + 15.99f) / 256.0f;
            double d5 = (float)k1 / 256.0f;
            double d7 = ((float)k1 + 15.99f) / 256.0f;
            if (this.field_82.canSuffocate(i - 1, j, k) && this.field_82.getTileId(i - 1, j + 1, k) == Tile.REDSTONE_DUST.id) {
                tessellator.colour(f * f2, f * f3, f * f4);
                tessellator.vertex((float)i + 0.015625f, (float)(j + 1) + 0.021875f, k + 1, d3, d5);
                tessellator.vertex((float)i + 0.015625f, j + 0, k + 1, d1, d5);
                tessellator.vertex((float)i + 0.015625f, j + 0, k + 0, d1, d7);
                tessellator.vertex((float)i + 0.015625f, (float)(j + 1) + 0.021875f, k + 0, d3, d7);
                tessellator.colour(f, f, f);
                tessellator.vertex((float)i + 0.015625f, (float)(j + 1) + 0.021875f, k + 1, d3, d5 + 0.0625);
                tessellator.vertex((float)i + 0.015625f, j + 0, k + 1, d1, d5 + 0.0625);
                tessellator.vertex((float)i + 0.015625f, j + 0, k + 0, d1, d7 + 0.0625);
                tessellator.vertex((float)i + 0.015625f, (float)(j + 1) + 0.021875f, k + 0, d3, d7 + 0.0625);
            }
            if (this.field_82.canSuffocate(i + 1, j, k) && this.field_82.getTileId(i + 1, j + 1, k) == Tile.REDSTONE_DUST.id) {
                tessellator.colour(f * f2, f * f3, f * f4);
                tessellator.vertex((float)(i + 1) - 0.015625f, j + 0, k + 1, d1, d7);
                tessellator.vertex((float)(i + 1) - 0.015625f, (float)(j + 1) + 0.021875f, k + 1, d3, d7);
                tessellator.vertex((float)(i + 1) - 0.015625f, (float)(j + 1) + 0.021875f, k + 0, d3, d5);
                tessellator.vertex((float)(i + 1) - 0.015625f, j + 0, k + 0, d1, d5);
                tessellator.colour(f, f, f);
                tessellator.vertex((float)(i + 1) - 0.015625f, j + 0, k + 1, d1, d7 + 0.0625);
                tessellator.vertex((float)(i + 1) - 0.015625f, (float)(j + 1) + 0.021875f, k + 1, d3, d7 + 0.0625);
                tessellator.vertex((float)(i + 1) - 0.015625f, (float)(j + 1) + 0.021875f, k + 0, d3, d5 + 0.0625);
                tessellator.vertex((float)(i + 1) - 0.015625f, j + 0, k + 0, d1, d5 + 0.0625);
            }
            if (this.field_82.canSuffocate(i, j, k - 1) && this.field_82.getTileId(i, j + 1, k - 1) == Tile.REDSTONE_DUST.id) {
                tessellator.colour(f * f2, f * f3, f * f4);
                tessellator.vertex(i + 1, j + 0, (float)k + 0.015625f, d1, d7);
                tessellator.vertex(i + 1, (float)(j + 1) + 0.021875f, (float)k + 0.015625f, d3, d7);
                tessellator.vertex(i + 0, (float)(j + 1) + 0.021875f, (float)k + 0.015625f, d3, d5);
                tessellator.vertex(i + 0, j + 0, (float)k + 0.015625f, d1, d5);
                tessellator.colour(f, f, f);
                tessellator.vertex(i + 1, j + 0, (float)k + 0.015625f, d1, d7 + 0.0625);
                tessellator.vertex(i + 1, (float)(j + 1) + 0.021875f, (float)k + 0.015625f, d3, d7 + 0.0625);
                tessellator.vertex(i + 0, (float)(j + 1) + 0.021875f, (float)k + 0.015625f, d3, d5 + 0.0625);
                tessellator.vertex(i + 0, j + 0, (float)k + 0.015625f, d1, d5 + 0.0625);
            }
            if (this.field_82.canSuffocate(i, j, k + 1) && this.field_82.getTileId(i, j + 1, k + 1) == Tile.REDSTONE_DUST.id) {
                tessellator.colour(f * f2, f * f3, f * f4);
                tessellator.vertex(i + 1, (float)(j + 1) + 0.021875f, (float)(k + 1) - 0.015625f, d3, d5);
                tessellator.vertex(i + 1, j + 0, (float)(k + 1) - 0.015625f, d1, d5);
                tessellator.vertex(i + 0, j + 0, (float)(k + 1) - 0.015625f, d1, d7);
                tessellator.vertex(i + 0, (float)(j + 1) + 0.021875f, (float)(k + 1) - 0.015625f, d3, d7);
                tessellator.colour(f, f, f);
                tessellator.vertex(i + 1, (float)(j + 1) + 0.021875f, (float)(k + 1) - 0.015625f, d3, d5 + 0.0625);
                tessellator.vertex(i + 1, j + 0, (float)(k + 1) - 0.015625f, d1, d5 + 0.0625);
                tessellator.vertex(i + 0, j + 0, (float)(k + 1) - 0.015625f, d1, d7 + 0.0625);
                tessellator.vertex(i + 0, (float)(j + 1) + 0.021875f, (float)(k + 1) - 0.015625f, d3, d7 + 0.0625);
            }
        }
        return true;
    }

    public boolean method_44(RailTile blockrail, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int l = this.field_82.getTileMeta(i, j, k);
        int i1 = blockrail.getTextureForSide(0, l);
        if (this.field_83 >= 0) {
            i1 = this.field_83;
        }
        if (blockrail.method_1108()) {
            l &= 7;
        }
        float f = blockrail.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        int j1 = (i1 & 0xF) << 4;
        int k1 = i1 & 0xF0;
        double d = (float)j1 / 256.0f;
        double d1 = ((float)j1 + 15.99f) / 256.0f;
        double d2 = (float)k1 / 256.0f;
        double d3 = ((float)k1 + 15.99f) / 256.0f;
        float f1 = 0.0625f;
        float f2 = i + 1;
        float f3 = i + 1;
        float f4 = i + 0;
        float f5 = i + 0;
        float f6 = k + 0;
        float f7 = k + 1;
        float f8 = k + 1;
        float f9 = k + 0;
        float f10 = (float)j + f1;
        float f11 = (float)j + f1;
        float f12 = (float)j + f1;
        float f13 = (float)j + f1;
        if (l == 1 || l == 2 || l == 3 || l == 7) {
            f2 = f5 = (float)(i + 1);
            f3 = f4 = (float)(i + 0);
            f6 = f7 = (float)(k + 1);
            f8 = f9 = (float)(k + 0);
        } else if (l == 8) {
            f2 = f3 = (float)(i + 0);
            f4 = f5 = (float)(i + 1);
            f6 = f9 = (float)(k + 1);
            f7 = f8 = (float)(k + 0);
        } else if (l == 9) {
            f2 = f5 = (float)(i + 0);
            f3 = f4 = (float)(i + 1);
            f6 = f7 = (float)(k + 0);
            f8 = f9 = (float)(k + 1);
        }
        if (l == 2 || l == 4) {
            f10 += 1.0f;
            f13 += 1.0f;
        } else if (l == 3 || l == 5) {
            f11 += 1.0f;
            f12 += 1.0f;
        }
        tessellator.vertex(f2, f10, f6, d1, d2);
        tessellator.vertex(f3, f11, f7, d1, d3);
        tessellator.vertex(f4, f12, f8, d, d3);
        tessellator.vertex(f5, f13, f9, d, d2);
        tessellator.vertex(f5, f13, f9, d, d2);
        tessellator.vertex(f4, f12, f8, d, d3);
        tessellator.vertex(f3, f11, f7, d1, d3);
        tessellator.vertex(f2, f10, f6, d1, d2);
        return true;
    }

    public boolean method_72(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int m = this.field_82.getTileMeta(i, j, k);
        int l = block.getTextureForSide(0, m);
        if (this.field_83 >= 0) {
            l = this.field_83;
        }
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        int i1 = (l & 0xF) << 4;
        int j1 = l & 0xF0;
        double d = (float)i1 / 256.0f;
        double d1 = ((float)i1 + 15.99f) / 256.0f;
        double d2 = (float)j1 / 256.0f;
        double d3 = ((float)j1 + 15.99f) / 256.0f;
        int k1 = m % 4 + 2;
        float f1 = 0.0f;
        float f2 = 0.025f;
        if (k1 == 5) {
            tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d, d2);
            tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d, d3);
            tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d1, d3);
            tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d1, d2);
            tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d, d3);
            tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d, d2);
            tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d1, d2);
            tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d1, d3);
        }
        if (k1 == 4) {
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d1, d3);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d1, d2);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d, d2);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d, d3);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d, d3);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d, d2);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d1, d2);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d1, d3);
        }
        if (k1 == 3) {
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)k + f2, d1, d3);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)k + f2, d1, d2);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)k + f2, d, d2);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)k + f2, d, d3);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)k + f2, d, d3);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)k + f2, d, d2);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)k + f2, d1, d2);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)k + f2, d1, d3);
        }
        if (k1 == 2) {
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d, d2);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d, d3);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d1, d3);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d1, d2);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d1, d2);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d1, d3);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d, d3);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d, d2);
        }
        return true;
    }

    public boolean method_73(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        int l = block.getTint(this.field_82, i, j, k);
        float f1 = (float)(l >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(l >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(l & 0xFF) / 255.0f;
        if (GameRenderer.field_2340) {
            float f4 = (f1 * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
            float f5 = (f1 * 30.0f + f2 * 70.0f) / 100.0f;
            float f6 = (f1 * 30.0f + f3 * 70.0f) / 100.0f;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }
        tessellator.colour(f * f1, f * f2, f * f3);
        double d = i;
        double d1 = j;
        double d2 = k;
        if (block == Tile.TALLGRASS) {
            long l1 = (long)(i * 3129871) ^ (long)k * 116129781L ^ (long)j;
            l1 = l1 * l1 * 42317861L + l1 * 11L;
            d += ((double)((float)(l1 >> 16 & 0xFL) / 15.0f) - 0.5) * 0.5;
            d1 += ((double)((float)(l1 >> 20 & 0xFL) / 15.0f) - 1.0) * 0.2;
            d2 += ((double)((float)(l1 >> 24 & 0xFL) / 15.0f) - 0.5) * 0.5;
        }
        this.method_47(block, this.field_82.getTileMeta(i, j, k), d, d1, d2);
        return true;
    }

    public boolean method_74(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        this.method_56(block, this.field_82.getTileMeta(i, j, k), i, (float)j - 0.0625f, k);
        return true;
    }

    public void method_45(Tile block, double d, double d1, double d2, double d3, double d4) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int i = block.getTextureForSide(0);
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        float f = (float)j / 256.0f;
        float f1 = ((float)j + 15.99f) / 256.0f;
        float f2 = (float)k / 256.0f;
        float f3 = ((float)k + 15.99f) / 256.0f;
        double d5 = (double)f + 0.02734375;
        double d6 = (double)f2 + 0.0234375;
        double d7 = (double)f + 0.03515625;
        double d8 = (double)f2 + 0.03125;
        double d9 = (d += 0.5) - 0.5;
        double d10 = d + 0.5;
        double d11 = (d2 += 0.5) - 0.5;
        double d12 = d2 + 0.5;
        double d13 = 0.0625;
        double d14 = 0.625;
        tessellator.vertex(d + d3 * (1.0 - d14) - d13, d1 + d14, d2 + d4 * (1.0 - d14) - d13, d5, d6);
        tessellator.vertex(d + d3 * (1.0 - d14) - d13, d1 + d14, d2 + d4 * (1.0 - d14) + d13, d5, d8);
        tessellator.vertex(d + d3 * (1.0 - d14) + d13, d1 + d14, d2 + d4 * (1.0 - d14) + d13, d7, d8);
        tessellator.vertex(d + d3 * (1.0 - d14) + d13, d1 + d14, d2 + d4 * (1.0 - d14) - d13, d7, d6);
        tessellator.vertex(d - d13, d1 + 1.0, d11, f, f2);
        tessellator.vertex(d - d13 + d3, d1 + 0.0, d11 + d4, f, f3);
        tessellator.vertex(d - d13 + d3, d1 + 0.0, d12 + d4, f1, f3);
        tessellator.vertex(d - d13, d1 + 1.0, d12, f1, f2);
        tessellator.vertex(d + d13, d1 + 1.0, d12, f, f2);
        tessellator.vertex(d + d3 + d13, d1 + 0.0, d12 + d4, f, f3);
        tessellator.vertex(d + d3 + d13, d1 + 0.0, d11 + d4, f1, f3);
        tessellator.vertex(d + d13, d1 + 1.0, d11, f1, f2);
        tessellator.vertex(d9, d1 + 1.0, d2 + d13, f, f2);
        tessellator.vertex(d9 + d3, d1 + 0.0, d2 + d13 + d4, f, f3);
        tessellator.vertex(d10 + d3, d1 + 0.0, d2 + d13 + d4, f1, f3);
        tessellator.vertex(d10, d1 + 1.0, d2 + d13, f1, f2);
        tessellator.vertex(d10, d1 + 1.0, d2 - d13, f, f2);
        tessellator.vertex(d10 + d3, d1 + 0.0, d2 - d13 + d4, f, f3);
        tessellator.vertex(d9 + d3, d1 + 0.0, d2 - d13 + d4, f1, f3);
        tessellator.vertex(d9, d1 + 1.0, d2 - d13, f1, f2);
    }

    public void method_47(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (float)k / 256.0f;
        double d4 = ((float)k + 15.99f) / 256.0f;
        double d5 = (float)l / 256.0f;
        double d6 = ((float)l + 15.99f) / 256.0f;
        double d7 = d + 0.5 - (double)0.45f;
        double d8 = d + 0.5 + (double)0.45f;
        double d9 = d2 + 0.5 - (double)0.45f;
        double d10 = d2 + 0.5 + (double)0.45f;
        tessellator.vertex(d7, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d10, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d9, d4, d5);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (float)k / 256.0f;
            d4 = ((float)k + 15.99f) / 256.0f;
            d5 = (float)l / 256.0f;
            d6 = ((float)l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d7, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d9, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d10, d4, d5);
    }

    public void renderCrossedSquaresUpsideDown(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (float)k / 256.0f;
        double d4 = ((float)k + 15.99f) / 256.0f;
        double d5 = (float)l / 256.0f;
        double d6 = ((float)l + 15.99f) / 256.0f;
        double d7 = d + 0.5 - (double)0.45f;
        double d8 = d + 0.5 + (double)0.45f;
        double d9 = d2 + 0.5 - (double)0.45f;
        double d10 = d2 + 0.5 + (double)0.45f;
        tessellator.vertex(d7, d1 + 0.0, d9, d3, d5);
        tessellator.vertex(d7, d1 + 1.0, d9, d3, d6);
        tessellator.vertex(d8, d1 + 1.0, d10, d4, d6);
        tessellator.vertex(d8, d1 + 0.0, d10, d4, d5);
        tessellator.vertex(d8, d1 + 0.0, d10, d3, d5);
        tessellator.vertex(d8, d1 + 1.0, d10, d3, d6);
        tessellator.vertex(d7, d1 + 1.0, d9, d4, d6);
        tessellator.vertex(d7, d1 + 0.0, d9, d4, d5);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (float)k / 256.0f;
            d4 = ((float)k + 15.99f) / 256.0f;
            d5 = (float)l / 256.0f;
            d6 = ((float)l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d7, d1 + 0.0, d10, d3, d5);
        tessellator.vertex(d7, d1 + 1.0, d10, d3, d6);
        tessellator.vertex(d8, d1 + 1.0, d9, d4, d6);
        tessellator.vertex(d8, d1 + 0.0, d9, d4, d5);
        tessellator.vertex(d8, d1 + 0.0, d9, d3, d5);
        tessellator.vertex(d8, d1 + 1.0, d9, d3, d6);
        tessellator.vertex(d7, d1 + 1.0, d10, d4, d6);
        tessellator.vertex(d7, d1 + 0.0, d10, d4, d5);
    }

    public void renderCrossedSquaresEast(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (float)k / 256.0f;
        double d4 = ((float)k + 15.99f) / 256.0f;
        double d5 = (float)l / 256.0f;
        double d6 = ((float)l + 15.99f) / 256.0f;
        double d7 = d1 + 0.5 - (double)0.45f;
        double d8 = d1 + 0.5 + (double)0.45f;
        double d9 = d2 + 0.5 - (double)0.45f;
        double d10 = d2 + 0.5 + (double)0.45f;
        tessellator.vertex(d + 1.0, d7, d9, d3, d5);
        tessellator.vertex(d + 0.0, d7, d9, d3, d6);
        tessellator.vertex(d + 0.0, d8, d10, d4, d6);
        tessellator.vertex(d + 1.0, d8, d10, d4, d5);
        tessellator.vertex(d + 1.0, d8, d10, d3, d5);
        tessellator.vertex(d + 0.0, d8, d10, d3, d6);
        tessellator.vertex(d + 0.0, d7, d9, d4, d6);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (float)k / 256.0f;
            d4 = ((float)k + 15.99f) / 256.0f;
            d5 = (float)l / 256.0f;
            d6 = ((float)l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d + 1.0, d7, d9, d4, d5);
        tessellator.vertex(d + 1.0, d7, d10, d3, d5);
        tessellator.vertex(d + 0.0, d7, d10, d3, d6);
        tessellator.vertex(d + 0.0, d8, d9, d4, d6);
        tessellator.vertex(d + 1.0, d8, d9, d4, d5);
        tessellator.vertex(d + 1.0, d8, d9, d3, d5);
        tessellator.vertex(d + 0.0, d8, d9, d3, d6);
        tessellator.vertex(d + 0.0, d7, d10, d4, d6);
        tessellator.vertex(d + 1.0, d7, d10, d4, d5);
    }

    public void renderCrossedSquaresWest(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (float)k / 256.0f;
        double d4 = ((float)k + 15.99f) / 256.0f;
        double d5 = (float)l / 256.0f;
        double d6 = ((float)l + 15.99f) / 256.0f;
        double d7 = d1 + 0.5 - (double)0.45f;
        double d8 = d1 + 0.5 + (double)0.45f;
        double d9 = d2 + 0.5 - (double)0.45f;
        double d10 = d2 + 0.5 + (double)0.45f;
        tessellator.vertex(d + 0.0, d7, d9, d3, d5);
        tessellator.vertex(d + 1.0, d7, d9, d3, d6);
        tessellator.vertex(d + 1.0, d8, d10, d4, d6);
        tessellator.vertex(d + 0.0, d8, d10, d4, d5);
        tessellator.vertex(d + 0.0, d8, d10, d3, d5);
        tessellator.vertex(d + 1.0, d8, d10, d3, d6);
        tessellator.vertex(d + 1.0, d7, d9, d4, d6);
        tessellator.vertex(d + 0.0, d7, d9, d4, d5);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (float)k / 256.0f;
            d4 = ((float)k + 15.99f) / 256.0f;
            d5 = (float)l / 256.0f;
            d6 = ((float)l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d + 0.0, d7, d10, d3, d5);
        tessellator.vertex(d + 1.0, d7, d10, d3, d6);
        tessellator.vertex(d + 1.0, d8, d9, d4, d6);
        tessellator.vertex(d + 0.0, d8, d9, d4, d5);
        tessellator.vertex(d + 0.0, d8, d9, d3, d5);
        tessellator.vertex(d + 1.0, d8, d9, d3, d6);
        tessellator.vertex(d + 1.0, d7, d10, d4, d6);
        tessellator.vertex(d + 0.0, d7, d10, d4, d5);
    }

    public void renderCrossedSquaresNorth(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (float)k / 256.0f;
        double d4 = ((float)k + 15.99f) / 256.0f;
        double d5 = (float)l / 256.0f;
        double d6 = ((float)l + 15.99f) / 256.0f;
        double d7 = d1 + 0.5 - (double)0.45f;
        double d8 = d1 + 0.5 + (double)0.45f;
        double d9 = d + 0.5 - (double)0.45f;
        double d10 = d + 0.5 + (double)0.45f;
        tessellator.vertex(d9, d7, d2 + 1.0, d3, d5);
        tessellator.vertex(d9, d7, d2 + 0.0, d3, d6);
        tessellator.vertex(d10, d8, d2 + 0.0, d4, d6);
        tessellator.vertex(d10, d8, d2 + 1.0, d4, d5);
        tessellator.vertex(d10, d8, d2 + 1.0, d3, d5);
        tessellator.vertex(d10, d8, d2 + 0.0, d3, d6);
        tessellator.vertex(d9, d7, d2 + 0.0, d4, d6);
        tessellator.vertex(d9, d7, d2 + 1.0, d4, d5);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (float)k / 256.0f;
            d4 = ((float)k + 15.99f) / 256.0f;
            d5 = (float)l / 256.0f;
            d6 = ((float)l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d10, d7, d2 + 1.0, d3, d5);
        tessellator.vertex(d10, d7, d2 + 0.0, d3, d6);
        tessellator.vertex(d9, d8, d2 + 0.0, d4, d6);
        tessellator.vertex(d9, d8, d2 + 1.0, d4, d5);
        tessellator.vertex(d9, d8, d2 + 1.0, d3, d5);
        tessellator.vertex(d9, d8, d2 + 0.0, d3, d6);
        tessellator.vertex(d10, d7, d2 + 0.0, d4, d6);
        tessellator.vertex(d10, d7, d2 + 1.0, d4, d5);
    }

    public void renderCrossedSquaresSouth(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (float)k / 256.0f;
        double d4 = ((float)k + 15.99f) / 256.0f;
        double d5 = (float)l / 256.0f;
        double d6 = ((float)l + 15.99f) / 256.0f;
        double d7 = d1 + 0.5 - (double)0.45f;
        double d8 = d1 + 0.5 + (double)0.45f;
        double d9 = d + 0.5 - (double)0.45f;
        double d10 = d + 0.5 + (double)0.45f;
        tessellator.vertex(d9, d7, d2 + 0.0, d3, d5);
        tessellator.vertex(d9, d7, d2 + 1.0, d3, d6);
        tessellator.vertex(d10, d8, d2 + 1.0, d4, d6);
        tessellator.vertex(d10, d8, d2 + 0.0, d4, d5);
        tessellator.vertex(d10, d8, d2 + 0.0, d3, d5);
        tessellator.vertex(d10, d8, d2 + 1.0, d3, d6);
        tessellator.vertex(d9, d7, d2 + 1.0, d4, d6);
        tessellator.vertex(d9, d7, d2 + 0.0, d4, d5);
        if (this.field_83 < 0) {
            j = block.getTextureForSide(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (float)k / 256.0f;
            d4 = ((float)k + 15.99f) / 256.0f;
            d5 = (float)l / 256.0f;
            d6 = ((float)l + 15.99f) / 256.0f;
        }
        tessellator.vertex(d10, d7, d2 + 0.0, d3, d5);
        tessellator.vertex(d10, d7, d2 + 1.0, d3, d6);
        tessellator.vertex(d9, d8, d2 + 1.0, d4, d6);
        tessellator.vertex(d9, d8, d2 + 0.0, d4, d5);
        tessellator.vertex(d9, d8, d2 + 0.0, d3, d5);
        tessellator.vertex(d9, d8, d2 + 1.0, d3, d6);
        tessellator.vertex(d10, d7, d2 + 1.0, d4, d6);
        tessellator.vertex(d10, d7, d2 + 0.0, d4, d5);
    }

    public void method_56(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int j = block.getTextureForSide(0, i);
        if (this.field_83 >= 0) {
            j = this.field_83;
        }
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (float)k / 256.0f;
        double d4 = ((float)k + 15.99f) / 256.0f;
        double d5 = (float)l / 256.0f;
        double d6 = ((float)l + 15.99f) / 256.0f;
        double d7 = d + 0.5 - 0.25;
        double d8 = d + 0.5 + 0.25;
        double d9 = d2 + 0.5 - 0.5;
        double d10 = d2 + 0.5 + 0.5;
        tessellator.vertex(d7, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d10, d4, d5);
        tessellator.vertex(d7, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d9, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d9, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d10, d4, d5);
        d7 = d + 0.5 - 0.5;
        d8 = d + 0.5 + 0.5;
        d9 = d2 + 0.5 - 0.25;
        d10 = d2 + 0.5 + 0.25;
        tessellator.vertex(d7, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d9, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d9, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d10, d4, d5);
        tessellator.vertex(d7, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d10, d4, d5);
    }

    public boolean method_75(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        boolean flag = block.method_1618(this.field_82, i, j + 1, k, 1);
        boolean flag1 = block.method_1618(this.field_82, i, j - 1, k, 0);
        boolean[] aflag = new boolean[]{block.method_1618(this.field_82, i, j, k - 1, 2), block.method_1618(this.field_82, i, j, k + 1, 3), block.method_1618(this.field_82, i - 1, j, k, 4), block.method_1618(this.field_82, i + 1, j, k, 5)};
        if (!(flag || flag1 || aflag[0] || aflag[1] || aflag[2] || aflag[3])) {
            return false;
        }
        int color = block.getTint(this.field_82, i, j, k);
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        boolean flag2 = false;
        float f3 = 0.5f;
        float f4 = 1.0f;
        float f5 = 0.8f;
        float f6 = 0.6f;
        double d = 0.0;
        double d1 = 1.0;
        Material material = block.material;
        int i1 = this.field_82.getTileMeta(i, j, k);
        float f7 = this.method_43(i, j, k, material);
        float f8 = this.method_43(i, j, k + 1, material);
        float f9 = this.method_43(i + 1, j, k + 1, material);
        float f10 = this.method_43(i + 1, j, k, material);
        if (this.field_85 || flag) {
            flag2 = true;
            int j1 = block.getTextureForSide(1, i1);
            float f12 = (float)FluidTile.method_1223(this.field_82, i, j, k, material);
            if (f12 > -999.0f) {
                j1 = block.getTextureForSide(2, i1);
            }
            int i2 = (j1 & 0xF) << 4;
            int k2 = j1 & 0xF0;
            double d2 = ((double)i2 + 8.0) / 256.0;
            double d3 = ((double)k2 + 8.0) / 256.0;
            if (f12 < -999.0f) {
                f12 = 0.0f;
            } else {
                d2 = (float)(i2 + 16) / 256.0f;
                d3 = (float)(k2 + 16) / 256.0f;
            }
            float f14 = MathsHelper.sin(f12) * 8.0f / 256.0f;
            float f16 = MathsHelper.cos(f12) * 8.0f / 256.0f;
            float f18 = block.method_1604(this.field_82, i, j, k);
            tessellator.colour(f4 * f18 * red, f4 * f18 * green, f4 * f18 * blue);
            tessellator.vertex(i + 0, (float)j + f7, k + 0, d2 - (double)f16 - (double)f14, d3 - (double)f16 + (double)f14);
            tessellator.vertex(i + 0, (float)j + f8, k + 1, d2 - (double)f16 + (double)f14, d3 + (double)f16 + (double)f14);
            tessellator.vertex(i + 1, (float)j + f9, k + 1, d2 + (double)f16 + (double)f14, d3 + (double)f16 - (double)f14);
            tessellator.vertex(i + 1, (float)j + f10, k + 0, d2 + (double)f16 - (double)f14, d3 - (double)f16 - (double)f14);
        }
        if (this.field_85 || flag1) {
            float f11 = block.method_1604(this.field_82, i, j - 1, k);
            tessellator.colour(red * f3 * f11, green * f3 * f11, blue * f3 * f11);
            this.method_46(block, i, j, k, block.getTextureForSide(0));
            flag2 = true;
        }
        for (int k1 = 0; k1 < 4; ++k1) {
            float f21;
            float f19;
            float f20;
            float f17;
            float f15;
            float f13;
            int l1 = i;
            int j2 = j;
            int l2 = k;
            if (k1 == 0) {
                --l2;
            }
            if (k1 == 1) {
                ++l2;
            }
            if (k1 == 2) {
                --l1;
            }
            if (k1 == 3) {
                ++l1;
            }
            int i3 = block.getTextureForSide(k1 + 2, i1);
            int j3 = (i3 & 0xF) << 4;
            int k3 = i3 & 0xF0;
            if (!this.field_85 && !aflag[k1]) continue;
            if (k1 == 0) {
                f13 = f7;
                f15 = f10;
                f17 = i;
                f20 = i + 1;
                f19 = k;
                f21 = k;
            } else if (k1 == 1) {
                f13 = f9;
                f15 = f8;
                f17 = i + 1;
                f20 = i;
                f19 = k + 1;
                f21 = k + 1;
            } else if (k1 == 2) {
                f13 = f8;
                f15 = f7;
                f17 = i;
                f20 = i;
                f19 = k + 1;
                f21 = k;
            } else {
                f13 = f10;
                f15 = f9;
                f17 = i + 1;
                f20 = i + 1;
                f19 = k;
                f21 = k + 1;
            }
            flag2 = true;
            double d4 = (float)(j3 + 0) / 256.0f;
            double d5 = ((double)(j3 + 16) - 0.01) / 256.0;
            double d6 = ((float)k3 + (1.0f - f13) * 16.0f) / 256.0f;
            double d7 = ((float)k3 + (1.0f - f15) * 16.0f) / 256.0f;
            double d8 = ((double)(k3 + 16) - 0.01) / 256.0;
            float f22 = block.method_1604(this.field_82, l1, j2, l2);
            f22 = k1 < 2 ? (f22 *= f5) : (f22 *= f6);
            tessellator.colour(f4 * f22 * red, f4 * f22 * green, f4 * f22 * blue);
            tessellator.vertex(f17, (float)j + f13, f19, d4, d6);
            tessellator.vertex(f20, (float)j + f15, f21, d5, d7);
            tessellator.vertex(f20, j + 0, f21, d5, d8);
            tessellator.vertex(f17, j + 0, f19, d4, d8);
        }
        block.minY = d;
        block.maxY = d1;
        return flag2;
    }

    private float method_43(int i, int j, int k, Material material) {
        int l = 0;
        float f = 0.0f;
        for (int i1 = 0; i1 < 4; ++i1) {
            int j1 = i - (i1 & 1);
            int k1 = j;
            int l1 = k - (i1 >> 1 & 1);
            if (this.field_82.getMaterial(j1, k1 + 1, l1) == material) {
                return 1.0f;
            }
            Material material1 = this.field_82.getMaterial(j1, k1, l1);
            if (material1 == material) {
                int i2 = this.field_82.getTileMeta(j1, k1, l1);
                if (i2 >= 8 || i2 == 0) {
                    f += FluidTile.method_1218(i2) * 10.0f;
                    l += 10;
                }
                f += FluidTile.method_1218(i2);
                ++l;
                continue;
            }
            if (material1.isSolid()) continue;
            f += 1.0f;
            ++l;
        }
        return 1.0f - f / (float)l;
    }

    public void method_53(Tile block, Level world, int i, int j, int k) {
        GL11.glTranslatef((float)(-i), (float)(-j), (float)(-k));
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        this.startRenderingBlocks(world);
        this.method_57(block, i, j, k);
        this.stopRenderingBlocks();
    }

    public void startRenderingBlocks(Level world) {
        this.field_82 = world;
        if (Minecraft.isSmoothLightingEnabled()) {
            GL11.glShadeModel(7425);
        }
        Tessellator.INSTANCE.start();
        this.field_85 = true;
    }

    public void stopRenderingBlocks() {
        this.field_85 = false;
        Tessellator.INSTANCE.draw();
        if (Minecraft.isSmoothLightingEnabled()) {
            GL11.glShadeModel(7424);
        }
        this.field_82 = null;
    }

    public boolean method_76(Tile block, int i, int j, int k) {
        int l = block.getTint(this.field_82, i, j, k);
        float f = (float)(l >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(l >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(l & 0xFF) / 255.0f;
        if (GameRenderer.field_2340) {
            float f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f;
            float f4 = (f * 30.0f + f1 * 70.0f) / 100.0f;
            float f5 = (f * 30.0f + f2 * 70.0f) / 100.0f;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        if (Minecraft.isSmoothLightingEnabled()) {
            return this.method_50(block, i, j, k, f, f1, f2);
        }
        return this.method_58(block, i, j, k, f, f1, f2);
    }

    public boolean method_50(Tile block, int i, int j, int k, float f, float f1, float f2) {
        float bottomRight;
        float topRight;
        float bottomLeft;
        float topLeft;
        float lerpBottom;
        float lerpTop;
        float lerpRight;
        float lerpLeft;
        boolean isGrass;
        this.field_92 = true;
        boolean flag = false;
        float f3 = this.field_93;
        float f10 = this.field_93;
        float f17 = this.field_93;
        float f24 = this.field_93;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        boolean flag5 = true;
        boolean flag6 = true;
        this.field_93 = block.method_1604(this.field_82, i, j, k);
        this.field_94 = block.method_1604(this.field_82, i - 1, j, k);
        this.field_95 = block.method_1604(this.field_82, i, j - 1, k);
        this.field_96 = block.method_1604(this.field_82, i, j, k - 1);
        this.field_97 = block.method_1604(this.field_82, i + 1, j, k);
        this.field_98 = block.method_1604(this.field_82, i, j + 1, k);
        this.field_99 = block.method_1604(this.field_82, i, j, k + 1);
        this.field_70 = Tile.IS_AIR[this.field_82.getTileId(i + 1, j + 1, k)];
        this.field_78 = Tile.IS_AIR[this.field_82.getTileId(i + 1, j - 1, k)];
        this.field_74 = Tile.IS_AIR[this.field_82.getTileId(i + 1, j, k + 1)];
        this.field_76 = Tile.IS_AIR[this.field_82.getTileId(i + 1, j, k - 1)];
        this.field_71 = Tile.IS_AIR[this.field_82.getTileId(i - 1, j + 1, k)];
        this.field_79 = Tile.IS_AIR[this.field_82.getTileId(i - 1, j - 1, k)];
        this.field_73 = Tile.IS_AIR[this.field_82.getTileId(i - 1, j, k - 1)];
        this.field_75 = Tile.IS_AIR[this.field_82.getTileId(i - 1, j, k + 1)];
        this.field_72 = Tile.IS_AIR[this.field_82.getTileId(i, j + 1, k + 1)];
        this.field_69 = Tile.IS_AIR[this.field_82.getTileId(i, j + 1, k - 1)];
        this.field_80 = Tile.IS_AIR[this.field_82.getTileId(i, j - 1, k + 1)];
        this.field_77 = Tile.IS_AIR[this.field_82.getTileId(i, j - 1, k - 1)];
        boolean bl = isGrass = block.id == Tile.GRASS.id;
        if (isGrass || this.field_83 >= 0) {
            flag6 = false;
            flag5 = false;
            flag4 = false;
            flag3 = false;
            flag1 = false;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j - 1, k, 0)) {
            float f11;
            float f18;
            float f25;
            float f4;
            if (this.field_55 > 0) {
                this.field_101 = block.method_1604(this.field_82, i - 1, --j, k);
                this.field_103 = block.method_1604(this.field_82, i, j, k - 1);
                this.field_104 = block.method_1604(this.field_82, i, j, k + 1);
                this.field_41 = block.method_1604(this.field_82, i + 1, j, k);
                this.field_100 = this.field_77 || this.field_79 ? block.method_1604(this.field_82, i - 1, j, k - 1) : this.field_101;
                this.field_102 = this.field_80 || this.field_79 ? block.method_1604(this.field_82, i - 1, j, k + 1) : this.field_101;
                this.field_105 = this.field_77 || this.field_78 ? block.method_1604(this.field_82, i + 1, j, k - 1) : this.field_41;
                this.field_42 = this.field_80 || this.field_78 ? block.method_1604(this.field_82, i + 1, j, k + 1) : this.field_41;
                ++j;
                f4 = (this.field_102 + this.field_101 + this.field_104 + this.field_95) / 4.0f;
                f25 = (this.field_104 + this.field_95 + this.field_42 + this.field_41) / 4.0f;
                f18 = (this.field_95 + this.field_103 + this.field_41 + this.field_105) / 4.0f;
                f11 = (this.field_101 + this.field_100 + this.field_95 + this.field_103) / 4.0f;
            } else {
                f18 = f25 = this.field_95;
                f11 = f25;
                f4 = f25;
            }
            this.field_58 = this.field_59 = (flag1 ? f : 1.0f) * 0.5f;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_62 = this.field_63 = (flag1 ? f1 : 1.0f) * 0.5f;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_66 = this.field_68 = (flag1 ? f2 : 1.0f) * 0.5f;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = 1.0f - (float)Math.max(block.minX, 0.0);
            lerpRight = 1.0f - (float)Math.min(block.maxX, 1.0);
            lerpTop = (float)Math.min(block.maxZ, 1.0);
            lerpBottom = (float)Math.max(block.minZ, 0.0);
            topLeft = lerpTop * (lerpLeft * f4 + (1.0f - lerpLeft) * f25) + (1.0f - lerpTop) * (lerpLeft * f11 + (1.0f - lerpLeft) * f18);
            bottomLeft = lerpBottom * (lerpLeft * f4 + (1.0f - lerpLeft) * f25) + (1.0f - lerpBottom) * (lerpLeft * f11 + (1.0f - lerpLeft) * f18);
            topRight = lerpTop * (lerpRight * f4 + (1.0f - lerpRight) * f25) + (1.0f - lerpTop) * (lerpRight * f11 + (1.0f - lerpRight) * f18);
            bottomRight = lerpBottom * (lerpRight * f4 + (1.0f - lerpRight) * f25) + (1.0f - lerpBottom) * (lerpRight * f11 + (1.0f - lerpRight) * f18);
            f4 = topLeft;
            f25 = topRight;
            f11 = bottomLeft;
            f18 = bottomRight;
            this.field_56 *= f4;
            this.field_60 *= f4;
            this.field_64 *= f4;
            this.field_57 *= f11;
            this.field_61 *= f11;
            this.field_65 *= f11;
            this.field_58 *= f18;
            this.field_62 *= f18;
            this.field_66 *= f18;
            this.field_59 *= f25;
            this.field_63 *= f25;
            this.field_68 *= f25;
            this.method_46(block, i, j, k, block.method_1626(this.field_82, i, j, k, 0));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j + 1, k, 1)) {
            float f19;
            float f12;
            float f5;
            float f26;
            if (this.field_55 > 0) {
                this.field_44 = block.method_1604(this.field_82, i - 1, ++j, k);
                this.field_48 = block.method_1604(this.field_82, i + 1, j, k);
                this.field_46 = block.method_1604(this.field_82, i, j, k - 1);
                this.field_49 = block.method_1604(this.field_82, i, j, k + 1);
                this.field_43 = this.field_69 || this.field_71 ? block.method_1604(this.field_82, i - 1, j, k - 1) : this.field_44;
                this.field_47 = this.field_69 || this.field_70 ? block.method_1604(this.field_82, i + 1, j, k - 1) : this.field_48;
                this.field_45 = this.field_72 || this.field_71 ? block.method_1604(this.field_82, i - 1, j, k + 1) : this.field_44;
                this.field_50 = this.field_72 || this.field_70 ? block.method_1604(this.field_82, i + 1, j, k + 1) : this.field_48;
                --j;
                f26 = (this.field_45 + this.field_44 + this.field_49 + this.field_98) / 4.0f;
                f5 = (this.field_49 + this.field_98 + this.field_50 + this.field_48) / 4.0f;
                f12 = (this.field_98 + this.field_46 + this.field_48 + this.field_47) / 4.0f;
                f19 = (this.field_44 + this.field_43 + this.field_98 + this.field_46) / 4.0f;
            } else {
                f19 = f26 = this.field_98;
                f12 = f26;
                f5 = f26;
            }
            this.field_59 = flag2 ? f : 1.0f;
            this.field_58 = this.field_59;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_63 = flag2 ? f1 : 1.0f;
            this.field_62 = this.field_63;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_68 = flag2 ? f2 : 1.0f;
            this.field_66 = this.field_68;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = 1.0f - (float)Math.max(block.minX, 0.0);
            lerpRight = 1.0f - (float)Math.min(block.maxX, 1.0);
            lerpTop = (float)Math.max(block.minZ, 0.0);
            lerpBottom = (float)Math.min(block.maxZ, 1.0);
            topLeft = lerpTop * (lerpLeft * f26 + (1.0f - lerpLeft) * f5) + (1.0f - lerpTop) * (lerpLeft * f19 + (1.0f - lerpLeft) * f12);
            float topRight2 = lerpTop * (lerpRight * f26 + (1.0f - lerpRight) * f5) + (1.0f - lerpTop) * (lerpRight * f19 + (1.0f - lerpRight) * f12);
            float bottomLeft2 = lerpBottom * (lerpLeft * f26 + (1.0f - lerpLeft) * f5) + (1.0f - lerpBottom) * (lerpLeft * f19 + (1.0f - lerpLeft) * f12);
            bottomRight = lerpBottom * (lerpRight * f26 + (1.0f - lerpRight) * f5) + (1.0f - lerpBottom) * (lerpRight * f19 + (1.0f - lerpRight) * f12);
            f19 = topLeft;
            f26 = bottomLeft2;
            f5 = bottomRight;
            f12 = topRight2;
            this.field_56 *= f5;
            this.field_60 *= f5;
            this.field_64 *= f5;
            this.field_57 *= f12;
            this.field_61 *= f12;
            this.field_65 *= f12;
            this.field_58 *= f19;
            this.field_62 *= f19;
            this.field_66 *= f19;
            this.field_59 *= f26;
            this.field_63 *= f26;
            this.field_68 *= f26;
            this.method_55(block, i, j, k, block.method_1626(this.field_82, i, j, k, 1));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k - 1, 2)) {
            float f27;
            float f20;
            float f13;
            float f6;
            if (this.field_55 > 0) {
                this.field_51 = block.method_1604(this.field_82, i - 1, j, --k);
                this.field_103 = block.method_1604(this.field_82, i, j - 1, k);
                this.field_46 = block.method_1604(this.field_82, i, j + 1, k);
                this.field_52 = block.method_1604(this.field_82, i + 1, j, k);
                this.field_100 = this.field_73 || this.field_77 ? block.method_1604(this.field_82, i - 1, j - 1, k) : this.field_51;
                this.field_43 = this.field_73 || this.field_69 ? block.method_1604(this.field_82, i - 1, j + 1, k) : this.field_51;
                this.field_105 = this.field_76 || this.field_77 ? block.method_1604(this.field_82, i + 1, j - 1, k) : this.field_52;
                this.field_47 = this.field_76 || this.field_69 ? block.method_1604(this.field_82, i + 1, j + 1, k) : this.field_52;
                ++k;
                f6 = (this.field_51 + this.field_43 + this.field_96 + this.field_46) / 4.0f;
                f13 = (this.field_96 + this.field_46 + this.field_52 + this.field_47) / 4.0f;
                f20 = (this.field_103 + this.field_96 + this.field_105 + this.field_52) / 4.0f;
                f27 = (this.field_100 + this.field_51 + this.field_103 + this.field_96) / 4.0f;
            } else {
                f20 = f27 = this.field_96;
                f13 = f27;
                f6 = f27;
            }
            this.field_58 = this.field_59 = (flag3 ? f : 1.0f) * 0.8f;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_62 = this.field_63 = (flag3 ? f1 : 1.0f) * 0.8f;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_66 = this.field_68 = (flag3 ? f2 : 1.0f) * 0.8f;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = (float)Math.min(block.maxX, 1.0);
            lerpRight = (float)Math.max(block.minX, 0.0);
            lerpTop = (float)Math.min(block.maxY, 1.0);
            lerpBottom = (float)Math.max(block.minY, 0.0);
            topLeft = lerpTop * (lerpLeft * f13 + (1.0f - lerpLeft) * f6) + (1.0f - lerpTop) * (lerpLeft * f20 + (1.0f - lerpLeft) * f27);
            bottomLeft = lerpBottom * (lerpLeft * f13 + (1.0f - lerpLeft) * f6) + (1.0f - lerpBottom) * (lerpLeft * f20 + (1.0f - lerpLeft) * f27);
            topRight = lerpTop * (lerpRight * f13 + (1.0f - lerpRight) * f6) + (1.0f - lerpTop) * (lerpRight * f20 + (1.0f - lerpRight) * f27);
            bottomRight = lerpBottom * (lerpRight * f13 + (1.0f - lerpRight) * f6) + (1.0f - lerpBottom) * (lerpRight * f20 + (1.0f - lerpRight) * f27);
            f13 = topLeft;
            f20 = bottomLeft;
            f27 = bottomRight;
            f6 = topRight;
            this.field_56 *= f6;
            this.field_60 *= f6;
            this.field_64 *= f6;
            this.field_57 *= f13;
            this.field_61 *= f13;
            this.field_65 *= f13;
            this.field_58 *= f20;
            this.field_62 *= f20;
            this.field_66 *= f20;
            this.field_59 *= f27;
            this.field_63 *= f27;
            this.field_68 *= f27;
            int l = block.method_1626(this.field_82, i, j, k, 2);
            this.method_61(block, i, j, k, l);
            if (field_67 && isGrass && this.field_83 < 0) {
                this.field_56 *= f;
                this.field_57 *= f;
                this.field_58 *= f;
                this.field_59 *= f;
                this.field_60 *= f1;
                this.field_61 *= f1;
                this.field_62 *= f1;
                this.field_63 *= f1;
                this.field_64 *= f2;
                this.field_65 *= f2;
                this.field_66 *= f2;
                this.field_68 *= f2;
                this.method_61(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k + 1, 3)) {
            float f14;
            float f21;
            float f28;
            float f7;
            if (this.field_55 > 0) {
                this.field_53 = block.method_1604(this.field_82, i - 1, j, ++k);
                this.field_54 = block.method_1604(this.field_82, i + 1, j, k);
                this.field_104 = block.method_1604(this.field_82, i, j - 1, k);
                this.field_49 = block.method_1604(this.field_82, i, j + 1, k);
                this.field_102 = this.field_75 || this.field_80 ? block.method_1604(this.field_82, i - 1, j - 1, k) : this.field_53;
                this.field_45 = this.field_75 || this.field_72 ? block.method_1604(this.field_82, i - 1, j + 1, k) : this.field_53;
                this.field_42 = this.field_74 || this.field_80 ? block.method_1604(this.field_82, i + 1, j - 1, k) : this.field_54;
                this.field_50 = this.field_74 || this.field_72 ? block.method_1604(this.field_82, i + 1, j + 1, k) : this.field_54;
                --k;
                f7 = (this.field_53 + this.field_45 + this.field_99 + this.field_49) / 4.0f;
                f28 = (this.field_99 + this.field_49 + this.field_54 + this.field_50) / 4.0f;
                f21 = (this.field_104 + this.field_99 + this.field_42 + this.field_54) / 4.0f;
                f14 = (this.field_102 + this.field_53 + this.field_104 + this.field_99) / 4.0f;
            } else {
                f21 = f28 = this.field_99;
                f14 = f28;
                f7 = f28;
            }
            this.field_58 = this.field_59 = (flag4 ? f : 1.0f) * 0.8f;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_62 = this.field_63 = (flag4 ? f1 : 1.0f) * 0.8f;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_66 = this.field_68 = (flag4 ? f2 : 1.0f) * 0.8f;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = (float)Math.min(1.0 - block.minX, 1.0);
            lerpRight = (float)Math.max(1.0 - block.maxX, 0.0);
            lerpTop = (float)Math.min(block.maxY, 1.0);
            lerpBottom = (float)Math.max(block.minY, 0.0);
            topLeft = lerpTop * (lerpLeft * f7 + (1.0f - lerpLeft) * f28) + (1.0f - lerpTop) * (lerpLeft * f14 + (1.0f - lerpLeft) * f21);
            bottomLeft = lerpBottom * (lerpLeft * f7 + (1.0f - lerpLeft) * f28) + (1.0f - lerpBottom) * (lerpLeft * f14 + (1.0f - lerpLeft) * f21);
            topRight = lerpTop * (lerpRight * f7 + (1.0f - lerpRight) * f28) + (1.0f - lerpTop) * (lerpRight * f14 + (1.0f - lerpRight) * f21);
            bottomRight = lerpBottom * (lerpRight * f7 + (1.0f - lerpRight) * f28) + (1.0f - lerpBottom) * (lerpRight * f14 + (1.0f - lerpRight) * f21);
            f7 = topLeft;
            f14 = bottomLeft;
            f21 = bottomRight;
            f28 = topRight;
            this.field_56 *= f7;
            this.field_60 *= f7;
            this.field_64 *= f7;
            this.field_57 *= f14;
            this.field_61 *= f14;
            this.field_65 *= f14;
            this.field_58 *= f21;
            this.field_62 *= f21;
            this.field_66 *= f21;
            this.field_59 *= f28;
            this.field_63 *= f28;
            this.field_68 *= f28;
            int i1 = block.method_1626(this.field_82, i, j, k, 3);
            this.method_65(block, i, j, k, block.method_1626(this.field_82, i, j, k, 3));
            if (field_67 && isGrass && this.field_83 < 0) {
                this.field_56 *= f;
                this.field_57 *= f;
                this.field_58 *= f;
                this.field_59 *= f;
                this.field_60 *= f1;
                this.field_61 *= f1;
                this.field_62 *= f1;
                this.field_63 *= f1;
                this.field_64 *= f2;
                this.field_65 *= f2;
                this.field_66 *= f2;
                this.field_68 *= f2;
                this.method_65(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i - 1, j, k, 4)) {
            float f22;
            float f15;
            float f8;
            float f29;
            if (this.field_55 > 0) {
                this.field_101 = block.method_1604(this.field_82, --i, j - 1, k);
                this.field_51 = block.method_1604(this.field_82, i, j, k - 1);
                this.field_53 = block.method_1604(this.field_82, i, j, k + 1);
                this.field_44 = block.method_1604(this.field_82, i, j + 1, k);
                this.field_100 = this.field_73 || this.field_79 ? block.method_1604(this.field_82, i, j - 1, k - 1) : this.field_51;
                this.field_102 = this.field_75 || this.field_79 ? block.method_1604(this.field_82, i, j - 1, k + 1) : this.field_53;
                this.field_43 = this.field_73 || this.field_71 ? block.method_1604(this.field_82, i, j + 1, k - 1) : this.field_51;
                this.field_45 = this.field_75 || this.field_71 ? block.method_1604(this.field_82, i, j + 1, k + 1) : this.field_53;
                ++i;
                f29 = (this.field_101 + this.field_102 + this.field_94 + this.field_53) / 4.0f;
                f8 = (this.field_94 + this.field_53 + this.field_44 + this.field_45) / 4.0f;
                f15 = (this.field_51 + this.field_94 + this.field_43 + this.field_44) / 4.0f;
                f22 = (this.field_100 + this.field_101 + this.field_51 + this.field_94) / 4.0f;
            } else {
                f22 = f29 = this.field_94;
                f15 = f29;
                f8 = f29;
            }
            this.field_58 = this.field_59 = (flag5 ? f : 1.0f) * 0.6f;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_62 = this.field_63 = (flag5 ? f1 : 1.0f) * 0.6f;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_66 = this.field_68 = (flag5 ? f2 : 1.0f) * 0.6f;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = (float)Math.min(1.0 - block.minZ, 1.0);
            lerpRight = (float)Math.max(1.0 - block.maxZ, 0.0);
            lerpTop = (float)Math.min(block.maxY, 1.0);
            lerpBottom = (float)Math.max(block.minY, 0.0);
            topLeft = lerpTop * (lerpLeft * f15 + (1.0f - lerpLeft) * f8) + (1.0f - lerpTop) * (lerpLeft * f22 + (1.0f - lerpLeft) * f29);
            bottomLeft = lerpBottom * (lerpLeft * f15 + (1.0f - lerpLeft) * f8) + (1.0f - lerpBottom) * (lerpLeft * f22 + (1.0f - lerpLeft) * f29);
            topRight = lerpTop * (lerpRight * f15 + (1.0f - lerpRight) * f8) + (1.0f - lerpTop) * (lerpRight * f22 + (1.0f - lerpRight) * f29);
            bottomRight = lerpBottom * (lerpRight * f15 + (1.0f - lerpRight) * f8) + (1.0f - lerpBottom) * (lerpRight * f22 + (1.0f - lerpRight) * f29);
            f15 = topLeft;
            f22 = bottomLeft;
            f29 = bottomRight;
            f8 = topRight;
            this.field_56 *= f8;
            this.field_60 *= f8;
            this.field_64 *= f8;
            this.field_57 *= f15;
            this.field_61 *= f15;
            this.field_65 *= f15;
            this.field_58 *= f22;
            this.field_62 *= f22;
            this.field_66 *= f22;
            this.field_59 *= f29;
            this.field_63 *= f29;
            this.field_68 *= f29;
            int j1 = block.method_1626(this.field_82, i, j, k, 4);
            this.method_67(block, i, j, k, j1);
            if (field_67 && isGrass && this.field_83 < 0) {
                this.field_56 *= f;
                this.field_57 *= f;
                this.field_58 *= f;
                this.field_59 *= f;
                this.field_60 *= f1;
                this.field_61 *= f1;
                this.field_62 *= f1;
                this.field_63 *= f1;
                this.field_64 *= f2;
                this.field_65 *= f2;
                this.field_66 *= f2;
                this.field_68 *= f2;
                this.method_67(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i + 1, j, k, 5)) {
            float f16;
            float f23;
            float f30;
            float f9;
            if (this.field_55 > 0) {
                this.field_41 = block.method_1604(this.field_82, ++i, j - 1, k);
                this.field_52 = block.method_1604(this.field_82, i, j, k - 1);
                this.field_54 = block.method_1604(this.field_82, i, j, k + 1);
                this.field_48 = block.method_1604(this.field_82, i, j + 1, k);
                this.field_105 = this.field_78 || this.field_76 ? block.method_1604(this.field_82, i, j - 1, k - 1) : this.field_52;
                this.field_42 = this.field_78 || this.field_74 ? block.method_1604(this.field_82, i, j - 1, k + 1) : this.field_54;
                this.field_47 = this.field_70 || this.field_76 ? block.method_1604(this.field_82, i, j + 1, k - 1) : this.field_52;
                this.field_50 = this.field_70 || this.field_74 ? block.method_1604(this.field_82, i, j + 1, k + 1) : this.field_54;
                --i;
                f9 = (this.field_41 + this.field_42 + this.field_97 + this.field_54) / 4.0f;
                f30 = (this.field_97 + this.field_54 + this.field_48 + this.field_50) / 4.0f;
                f23 = (this.field_52 + this.field_97 + this.field_47 + this.field_48) / 4.0f;
                f16 = (this.field_105 + this.field_41 + this.field_52 + this.field_97) / 4.0f;
            } else {
                f23 = f30 = this.field_97;
                f16 = f30;
                f9 = f30;
            }
            this.field_58 = this.field_59 = (flag6 ? f : 1.0f) * 0.6f;
            this.field_57 = this.field_59;
            this.field_56 = this.field_59;
            this.field_62 = this.field_63 = (flag6 ? f1 : 1.0f) * 0.6f;
            this.field_61 = this.field_63;
            this.field_60 = this.field_63;
            this.field_66 = this.field_68 = (flag6 ? f2 : 1.0f) * 0.6f;
            this.field_65 = this.field_68;
            this.field_64 = this.field_68;
            lerpLeft = (float)Math.min(1.0 - block.minZ, 1.0);
            lerpRight = (float)Math.max(1.0 - block.maxZ, 0.0);
            lerpTop = (float)Math.min(block.maxY, 1.0);
            lerpBottom = (float)Math.max(block.minY, 0.0);
            topLeft = lerpTop * (lerpLeft * f23 + (1.0f - lerpLeft) * f30) + (1.0f - lerpTop) * (lerpLeft * f16 + (1.0f - lerpLeft) * f9);
            bottomLeft = lerpBottom * (lerpLeft * f23 + (1.0f - lerpLeft) * f30) + (1.0f - lerpBottom) * (lerpLeft * f16 + (1.0f - lerpLeft) * f9);
            topRight = lerpTop * (lerpRight * f23 + (1.0f - lerpRight) * f30) + (1.0f - lerpTop) * (lerpRight * f16 + (1.0f - lerpRight) * f9);
            f9 = bottomRight = lerpBottom * (lerpRight * f23 + (1.0f - lerpRight) * f30) + (1.0f - lerpBottom) * (lerpRight * f16 + (1.0f - lerpRight) * f9);
            f16 = bottomLeft;
            f23 = topLeft;
            f30 = topRight;
            this.field_56 *= f9;
            this.field_60 *= f9;
            this.field_64 *= f9;
            this.field_57 *= f16;
            this.field_61 *= f16;
            this.field_65 *= f16;
            this.field_58 *= f23;
            this.field_62 *= f23;
            this.field_66 *= f23;
            this.field_59 *= f30;
            this.field_63 *= f30;
            this.field_68 *= f30;
            int k1 = block.method_1626(this.field_82, i, j, k, 5);
            this.method_69(block, i, j, k, k1);
            if (field_67 && isGrass && this.field_83 < 0) {
                this.field_56 *= f;
                this.field_57 *= f;
                this.field_58 *= f;
                this.field_59 *= f;
                this.field_60 *= f1;
                this.field_61 *= f1;
                this.field_62 *= f1;
                this.field_63 *= f1;
                this.field_64 *= f2;
                this.field_65 *= f2;
                this.field_66 *= f2;
                this.field_68 *= f2;
                this.method_69(block, i, j, k, 38);
            }
            flag = true;
        }
        this.field_92 = false;
        return flag;
    }

    public boolean method_58(Tile block, int i, int j, int k, float f, float f1, float f2) {
        boolean isGrass;
        this.field_92 = false;
        Tessellator tessellator = Tessellator.INSTANCE;
        boolean flag = false;
        float f3 = 0.5f;
        float f4 = 1.0f;
        float f5 = 0.8f;
        float f6 = 0.6f;
        float f7 = f4 * f;
        float f8 = f4 * f1;
        float f9 = f4 * f2;
        float f10 = f3;
        float f11 = f5;
        float f12 = f6;
        float f13 = f3;
        float f14 = f5;
        float f15 = f6;
        float f16 = f3;
        float f17 = f5;
        float f18 = f6;
        boolean bl = isGrass = block == Tile.GRASS;
        if (!isGrass) {
            f10 *= f;
            f11 *= f;
            f12 *= f;
            f13 *= f1;
            f14 *= f1;
            f15 *= f1;
            f16 *= f2;
            f17 *= f2;
            f18 *= f2;
        }
        float f19 = block.method_1604(this.field_82, i, j, k);
        if (this.field_85 || block.method_1618(this.field_82, i, j - 1, k, 0)) {
            float f20 = block.method_1604(this.field_82, i, j - 1, k);
            tessellator.colour(f10 * f20, f13 * f20, f16 * f20);
            this.method_46(block, i, j, k, block.method_1626(this.field_82, i, j, k, 0));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j + 1, k, 1)) {
            float f21 = block.method_1604(this.field_82, i, j + 1, k);
            if (block.maxY != 1.0 && !block.material.isLiquid()) {
                f21 = f19;
            }
            tessellator.colour(f7 * f21, f8 * f21, f9 * f21);
            this.method_55(block, i, j, k, block.method_1626(this.field_82, i, j, k, 1));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k - 1, 2)) {
            float f22 = block.method_1604(this.field_82, i, j, k - 1);
            if (block.minZ > 0.0) {
                f22 = f19;
            }
            tessellator.colour(f11 * f22, f14 * f22, f17 * f22);
            int l = block.method_1626(this.field_82, i, j, k, 2);
            this.method_61(block, i, j, k, l);
            if (field_67 && isGrass && this.field_83 < 0) {
                tessellator.colour(f11 * f22 * f, f14 * f22 * f1, f17 * f22 * f2);
                this.method_61(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k + 1, 3)) {
            float f23 = block.method_1604(this.field_82, i, j, k + 1);
            if (block.maxZ < 1.0) {
                f23 = f19;
            }
            tessellator.colour(f11 * f23, f14 * f23, f17 * f23);
            int i1 = block.method_1626(this.field_82, i, j, k, 3);
            this.method_65(block, i, j, k, i1);
            if (field_67 && isGrass && this.field_83 < 0) {
                tessellator.colour(f11 * f23 * f, f14 * f23 * f1, f17 * f23 * f2);
                this.method_65(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i - 1, j, k, 4)) {
            float f24 = block.method_1604(this.field_82, i - 1, j, k);
            if (block.minX > 0.0) {
                f24 = f19;
            }
            tessellator.colour(f12 * f24, f15 * f24, f18 * f24);
            int j1 = block.method_1626(this.field_82, i, j, k, 4);
            this.method_67(block, i, j, k, j1);
            if (field_67 && isGrass && this.field_83 < 0) {
                tessellator.colour(f12 * f24 * f, f15 * f24 * f1, f18 * f24 * f2);
                this.method_67(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i + 1, j, k, 5)) {
            float f25 = block.method_1604(this.field_82, i + 1, j, k);
            if (block.maxX < 1.0) {
                f25 = f19;
            }
            tessellator.colour(f12 * f25, f15 * f25, f18 * f25);
            int k1 = block.method_1626(this.field_82, i, j, k, 5);
            this.method_69(block, i, j, k, k1);
            if (field_67 && isGrass && this.field_83 < 0) {
                tessellator.colour(f12 * f25 * f, f15 * f25 * f1, f18 * f25 * f2);
                this.method_69(block, i, j, k, 38);
            }
            flag = true;
        }
        return flag;
    }

    public boolean method_77(Tile block, int i, int j, int k) {
        int l = block.getTint(this.field_82, i, j, k);
        float f = (float)(l >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(l >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(l & 0xFF) / 255.0f;
        if (GameRenderer.field_2340) {
            float f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f;
            float f4 = (f * 30.0f + f1 * 70.0f) / 100.0f;
            float f5 = (f * 30.0f + f2 * 70.0f) / 100.0f;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        return this.method_63(block, i, j, k, f, f1, f2);
    }

    public boolean method_63(Tile block, int i, int j, int k, float f, float f1, float f2) {
        Tessellator tessellator = Tessellator.INSTANCE;
        boolean flag = false;
        float f3 = 0.5f;
        float f4 = 1.0f;
        float f5 = 0.8f;
        float f6 = 0.6f;
        float f7 = f3 * f;
        float f8 = f4 * f;
        float f9 = f5 * f;
        float f10 = f6 * f;
        float f11 = f3 * f1;
        float f12 = f4 * f1;
        float f13 = f5 * f1;
        float f14 = f6 * f1;
        float f15 = f3 * f2;
        float f16 = f4 * f2;
        float f17 = f5 * f2;
        float f18 = f6 * f2;
        float f19 = 0.0625f;
        float f20 = block.method_1604(this.field_82, i, j, k);
        if (this.field_85 || block.method_1618(this.field_82, i, j - 1, k, 0)) {
            float f21 = block.method_1604(this.field_82, i, j - 1, k);
            tessellator.colour(f7 * f21, f11 * f21, f15 * f21);
            this.method_46(block, i, j, k, block.method_1626(this.field_82, i, j, k, 0));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j + 1, k, 1)) {
            float f22 = block.method_1604(this.field_82, i, j + 1, k);
            if (block.maxY != 1.0 && !block.material.isLiquid()) {
                f22 = f20;
            }
            tessellator.colour(f8 * f22, f12 * f22, f16 * f22);
            this.method_55(block, i, j, k, block.method_1626(this.field_82, i, j, k, 1));
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k - 1, 2)) {
            float f23 = block.method_1604(this.field_82, i, j, k - 1);
            if (block.minZ > 0.0) {
                f23 = f20;
            }
            tessellator.colour(f9 * f23, f13 * f23, f17 * f23);
            tessellator.changePrevPos(0.0f, 0.0f, f19);
            this.method_61(block, i, j, k, block.method_1626(this.field_82, i, j, k, 2));
            tessellator.changePrevPos(0.0f, 0.0f, -f19);
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i, j, k + 1, 3)) {
            float f24 = block.method_1604(this.field_82, i, j, k + 1);
            if (block.maxZ < 1.0) {
                f24 = f20;
            }
            tessellator.colour(f9 * f24, f13 * f24, f17 * f24);
            tessellator.changePrevPos(0.0f, 0.0f, -f19);
            this.method_65(block, i, j, k, block.method_1626(this.field_82, i, j, k, 3));
            tessellator.changePrevPos(0.0f, 0.0f, f19);
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i - 1, j, k, 4)) {
            float f25 = block.method_1604(this.field_82, i - 1, j, k);
            if (block.minX > 0.0) {
                f25 = f20;
            }
            tessellator.colour(f10 * f25, f14 * f25, f18 * f25);
            tessellator.changePrevPos(f19, 0.0f, 0.0f);
            this.method_67(block, i, j, k, block.method_1626(this.field_82, i, j, k, 4));
            tessellator.changePrevPos(-f19, 0.0f, 0.0f);
            flag = true;
        }
        if (this.field_85 || block.method_1618(this.field_82, i + 1, j, k, 5)) {
            float f26 = block.method_1604(this.field_82, i + 1, j, k);
            if (block.maxX < 1.0) {
                f26 = f20;
            }
            tessellator.colour(f10 * f26, f14 * f26, f18 * f26);
            tessellator.changePrevPos(-f19, 0.0f, 0.0f);
            this.method_69(block, i, j, k, block.method_1626(this.field_82, i, j, k, 5));
            tessellator.changePrevPos(f19, 0.0f, 0.0f);
            flag = true;
        }
        return flag;
    }

    public boolean method_78(Tile block, int i, int j, int k) {
        float b2;
        float b1;
        double v2;
        double v1;
        double u2;
        double u1;
        int v;
        int u;
        int texture;
        Tessellator tessellator;
        float f7;
        boolean flag6;
        boolean flag = false;
        float f = 0.375f;
        float f1 = 0.625f;
        block.setBoundingBox(f, 0.0f, f, f1, 1.0f, f1);
        this.method_76(block, i, j, k);
        flag = true;
        boolean flag1 = false;
        boolean flag2 = false;
        if (this.field_82.getTileId(i - 1, j, k) == block.id || this.field_82.getTileId(i + 1, j, k) == block.id) {
            flag1 = true;
        }
        if (this.field_82.getTileId(i, j, k - 1) == block.id || this.field_82.getTileId(i, j, k + 1) == block.id) {
            flag2 = true;
        }
        boolean flag3 = this.field_82.getTileId(i - 1, j, k) == block.id;
        boolean flag4 = this.field_82.getTileId(i + 1, j, k) == block.id;
        boolean flag5 = this.field_82.getTileId(i, j, k - 1) == block.id;
        boolean bl = flag6 = this.field_82.getTileId(i, j, k + 1) == block.id;
        if (!flag1 && !flag2) {
            flag1 = true;
        }
        f = 0.4375f;
        f1 = 0.5625f;
        float f2 = 0.75f;
        float f3 = 0.9375f;
        float f4 = flag3 ? 0.0f : f;
        float f5 = flag4 ? 1.0f : f1;
        float f6 = flag5 ? 0.0f : f;
        float f8 = f7 = flag6 ? 1.0f : f1;
        if (flag1) {
            block.setBoundingBox(f4, f2, f, f5, f3, f1);
            this.method_76(block, i, j, k);
            flag = true;
        }
        if (flag2) {
            block.setBoundingBox(f, f2, f6, f1, f3, f7);
            this.method_76(block, i, j, k);
            flag = true;
        }
        f2 = 0.375f;
        f3 = 0.5625f;
        if (flag1) {
            block.setBoundingBox(f4, f2, f, f5, f3, f1);
            this.method_76(block, i, j, k);
            flag = true;
        }
        if (flag2) {
            block.setBoundingBox(f, f2, f6, f1, f3, f7);
            this.method_76(block, i, j, k);
            flag = true;
        }
        f = (f - 0.5f) * 0.707f + 0.5f;
        f1 = (f1 - 0.5f) * 0.707f + 0.5f;
        if (this.field_82.getTileId(i - 1, j, k + 1) == block.id && !flag6 && !flag3) {
            tessellator = Tessellator.INSTANCE;
            texture = block.method_1626(this.field_82, i, j, k, 0);
            u = (texture & 0xF) << 4;
            v = texture & 0xF0;
            u1 = (double)u / 256.0;
            u2 = ((double)u + 16.0 - 0.01) / 256.0;
            v1 = ((double)v + 16.0 * (double)f3 - 1.0) / 256.0;
            v2 = ((double)v + 16.0 * (double)f2 - 1.0 - 0.01) / 256.0;
            b1 = this.field_82.getBrightness(i, j, k);
            b2 = this.field_82.getBrightness(i - 1, j, k + 1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f1 + (float)i, f2 + (float)j, f1 + (float)k, u1, v2);
            tessellator.vertex(f1 + (float)i, f3 + (float)j, f1 + (float)k, u1, v1);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f1 + (float)i - 1.0f, f3 + (float)j, f1 + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f1 + (float)i - 1.0f, f2 + (float)j, f1 + (float)k + 1.0f, u2, v2);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f + (float)i - 1.0f, f2 + (float)j, f + (float)k + 1.0f, u2, v2);
            tessellator.vertex(f + (float)i - 1.0f, f3 + (float)j, f + (float)k + 1.0f, u2, v1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f + (float)i, f3 + (float)j, f + (float)k, u1, v1);
            tessellator.vertex(f + (float)i, f2 + (float)j, f + (float)k, u1, v2);
            v1 = ((double)v + 16.0 * (double)f3) / 256.0;
            v2 = ((double)v + 16.0 * (double)f3 + 2.0 - 0.01) / 256.0;
            tessellator.colour(b2 * 0.5f, b2 * 0.5f, b2 * 0.5f);
            tessellator.vertex(f1 + (float)i - 1.0f, f2 + (float)j, f1 + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f + (float)i - 1.0f, f2 + (float)j, f + (float)k + 1.0f, u2, v2);
            tessellator.colour(b1 * 0.5f, b1 * 0.5f, b1 * 0.5f);
            tessellator.vertex(f + (float)i, f2 + (float)j, f + (float)k, u1, v2);
            tessellator.vertex(f1 + (float)i, f2 + (float)j, f1 + (float)k, u1, v1);
            tessellator.colour(b2, b2, b2);
            tessellator.vertex(f + (float)i - 1.0f, f3 + (float)j, f + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f1 + (float)i - 1.0f, f3 + (float)j, f1 + (float)k + 1.0f, u2, v2);
            tessellator.colour(b1, b1, b1);
            tessellator.vertex(f1 + (float)i, f3 + (float)j, f1 + (float)k, u1, v2);
            tessellator.vertex(f + (float)i, f3 + (float)j, f + (float)k, u1, v1);
            f2 = 0.75f;
            f3 = 0.9375f;
            v1 = ((double)v + 16.0 * (double)f3 - 1.0) / 256.0;
            v2 = ((double)v + 16.0 * (double)f2 - 1.0 - 0.01) / 256.0;
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f1 + (float)i, f2 + (float)j, f1 + (float)k, u1, v2);
            tessellator.vertex(f1 + (float)i, f3 + (float)j, f1 + (float)k, u1, v1);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f1 + (float)i - 1.0f, f3 + (float)j, f1 + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f1 + (float)i - 1.0f, f2 + (float)j, f1 + (float)k + 1.0f, u2, v2);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f + (float)i - 1.0f, f2 + (float)j, f + (float)k + 1.0f, u2, v2);
            tessellator.vertex(f + (float)i - 1.0f, f3 + (float)j, f + (float)k + 1.0f, u2, v1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f + (float)i, f3 + (float)j, f + (float)k, u1, v1);
            tessellator.vertex(f + (float)i, f2 + (float)j, f + (float)k, u1, v2);
            v1 = ((double)v + 16.0 * (double)f3) / 256.0;
            v2 = ((double)v + 16.0 * (double)f3 - 2.0 - 0.01) / 256.0;
            tessellator.colour(b2 * 0.5f, b2 * 0.5f, b2 * 0.5f);
            tessellator.vertex(f1 + (float)i - 1.0f, f2 + (float)j, f1 + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f + (float)i - 1.0f, f2 + (float)j, f + (float)k + 1.0f, u2, v2);
            tessellator.colour(b1 * 0.5f, b1 * 0.5f, b1 * 0.5f);
            tessellator.vertex(f + (float)i, f2 + (float)j, f + (float)k, u1, v2);
            tessellator.vertex(f1 + (float)i, f2 + (float)j, f1 + (float)k, u1, v1);
            tessellator.colour(b2, b2, b2);
            tessellator.vertex(f + (float)i - 1.0f, f3 + (float)j, f + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f1 + (float)i - 1.0f, f3 + (float)j, f1 + (float)k + 1.0f, u2, v2);
            tessellator.colour(b1, b1, b1);
            tessellator.vertex(f1 + (float)i, f3 + (float)j, f1 + (float)k, u1, v2);
            tessellator.vertex(f + (float)i, f3 + (float)j, f + (float)k, u1, v1);
        }
        if (this.field_82.getTileId(i + 1, j, k + 1) == block.id && !flag6 && !flag4) {
            f2 = 0.375f;
            f3 = 0.5625f;
            tessellator = Tessellator.INSTANCE;
            texture = block.method_1626(this.field_82, i, j, k, 0);
            u = (texture & 0xF) << 4;
            v = texture & 0xF0;
            u1 = (double)u / 256.0;
            u2 = ((double)u + 16.0 - 0.01) / 256.0;
            v1 = ((double)v + 16.0 * (double)f3 - 1.0) / 256.0;
            v2 = ((double)v + 16.0 * (double)f2 - 1.0 - 0.01) / 256.0;
            b1 = this.field_82.getBrightness(i, j, k);
            b2 = this.field_82.getBrightness(i - 1, j, k + 1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f1 + (float)i, f2 + (float)j, f + (float)k, u1, v2);
            tessellator.vertex(f1 + (float)i, f3 + (float)j, f + (float)k, u1, v1);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f1 + (float)i + 1.0f, f3 + (float)j, f + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f1 + (float)i + 1.0f, f2 + (float)j, f + (float)k + 1.0f, u2, v2);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f + (float)i + 1.0f, f2 + (float)j, f1 + (float)k + 1.0f, u2, v2);
            tessellator.vertex(f + (float)i + 1.0f, f3 + (float)j, f1 + (float)k + 1.0f, u2, v1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f + (float)i, f3 + (float)j, f1 + (float)k, u1, v1);
            tessellator.vertex(f + (float)i, f2 + (float)j, f1 + (float)k, u1, v2);
            v1 = ((double)v + 16.0 * (double)f3) / 256.0;
            v2 = ((double)v + 16.0 * (double)f3 + 2.0 - 0.01) / 256.0;
            tessellator.colour(b2 * 0.5f, b2 * 0.5f, b2 * 0.5f);
            tessellator.vertex(f1 + (float)i + 1.0f, f2 + (float)j, f + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f + (float)i + 1.0f, f2 + (float)j, f1 + (float)k + 1.0f, u2, v2);
            tessellator.colour(b1 * 0.5f, b1 * 0.5f, b1 * 0.5f);
            tessellator.vertex(f + (float)i, f2 + (float)j, f1 + (float)k, u1, v2);
            tessellator.vertex(f1 + (float)i, f2 + (float)j, f + (float)k, u1, v1);
            tessellator.colour(b2, b2, b2);
            tessellator.vertex(f + (float)i + 1.0f, f3 + (float)j, f1 + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f1 + (float)i + 1.0f, f3 + (float)j, f + (float)k + 1.0f, u2, v2);
            tessellator.colour(b1, b1, b1);
            tessellator.vertex(f1 + (float)i, f3 + (float)j, f + (float)k, u1, v2);
            tessellator.vertex(f + (float)i, f3 + (float)j, f1 + (float)k, u1, v1);
            f2 = 0.75f;
            f3 = 0.9375f;
            v1 = ((double)v + 16.0 * (double)f3 - 1.0) / 256.0;
            v2 = ((double)v + 16.0 * (double)f2 - 1.0 - 0.01) / 256.0;
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f1 + (float)i, f2 + (float)j, f + (float)k, u1, v2);
            tessellator.vertex(f1 + (float)i, f3 + (float)j, f + (float)k, u1, v1);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f1 + (float)i + 1.0f, f3 + (float)j, f + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f1 + (float)i + 1.0f, f2 + (float)j, f + (float)k + 1.0f, u2, v2);
            tessellator.colour(b2 * 0.7f, b2 * 0.7f, b2 * 0.7f);
            tessellator.vertex(f + (float)i + 1.0f, f2 + (float)j, f1 + (float)k + 1.0f, u2, v2);
            tessellator.vertex(f + (float)i + 1.0f, f3 + (float)j, f1 + (float)k + 1.0f, u2, v1);
            tessellator.colour(b1 * 0.7f, b1 * 0.7f, b1 * 0.7f);
            tessellator.vertex(f + (float)i, f3 + (float)j, f1 + (float)k, u1, v1);
            tessellator.vertex(f + (float)i, f2 + (float)j, f1 + (float)k, u1, v2);
            v1 = ((double)v + 16.0 * (double)f3) / 256.0;
            v2 = ((double)v + 16.0 * (double)f3 - 2.0 - 0.01) / 256.0;
            tessellator.colour(b2 * 0.5f, b2 * 0.5f, b2 * 0.5f);
            tessellator.vertex(f1 + (float)i + 1.0f, f2 + (float)j, f + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f + (float)i + 1.0f, f2 + (float)j, f1 + (float)k + 1.0f, u2, v2);
            tessellator.colour(b1 * 0.5f, b1 * 0.5f, b1 * 0.5f);
            tessellator.vertex(f + (float)i, f2 + (float)j, f1 + (float)k, u1, v2);
            tessellator.vertex(f1 + (float)i, f2 + (float)j, f + (float)k, u1, v1);
            tessellator.colour(b2, b2, b2);
            tessellator.vertex(f + (float)i + 1.0f, f3 + (float)j, f1 + (float)k + 1.0f, u2, v1);
            tessellator.vertex(f1 + (float)i + 1.0f, f3 + (float)j, f + (float)k + 1.0f, u2, v2);
            tessellator.colour(b1, b1, b1);
            tessellator.vertex(f1 + (float)i, f3 + (float)j, f + (float)k, u1, v2);
            tessellator.vertex(f + (float)i, f3 + (float)j, f1 + (float)k, u1, v1);
        }
        block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return flag;
    }

    public boolean method_79(Tile block, int i, int j, int k) {
        boolean flag = false;
        int l = this.field_82.getTileMeta(i, j, k) & 3;
        block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        this.method_76(block, i, j, k);
        if (l == 0) {
            int m;
            Tile b = Tile.BY_ID[this.field_82.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == 10) {
                m = this.field_82.getTileMeta(i - 1, j, k) & 3;
                if (m == 2) {
                    block.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    this.method_76(block, i, j, k);
                } else if (m == 3) {
                    block.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    this.method_76(block, i, j, k);
                }
            }
            m = this.field_82.getTileMeta(i + 1, j, k) & 3;
            b = Tile.BY_ID[this.field_82.getTileId(i + 1, j, k)];
            if (b != null && b.method_1621() == 10 && (m == 2 || m == 3)) {
                if (m == 2) {
                    block.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    this.method_76(block, i, j, k);
                } else if (m == 3) {
                    block.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    this.method_76(block, i, j, k);
                }
            } else {
                block.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
                this.method_76(block, i, j, k);
            }
            flag = true;
        } else if (l == 1) {
            int m = this.field_82.getTileMeta(i - 1, j, k) & 3;
            Tile b = Tile.BY_ID[this.field_82.getTileId(i - 1, j, k)];
            if (b != null && b.method_1621() == 10 && (m == 2 || m == 3)) {
                if (m == 3) {
                    block.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    this.method_76(block, i, j, k);
                } else {
                    block.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    this.method_76(block, i, j, k);
                }
            } else {
                block.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 1.0f);
                this.method_76(block, i, j, k);
            }
            b = Tile.BY_ID[this.field_82.getTileId(i + 1, j, k)];
            if (b != null && b.method_1621() == 10) {
                m = this.field_82.getTileMeta(i + 1, j, k) & 3;
                if (m == 2) {
                    block.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    this.method_76(block, i, j, k);
                } else if (m == 3) {
                    block.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    this.method_76(block, i, j, k);
                }
            }
            flag = true;
        } else if (l == 2) {
            int m;
            Tile b = Tile.BY_ID[this.field_82.getTileId(i, j, k - 1)];
            if (b != null && b.method_1621() == 10) {
                m = this.field_82.getTileMeta(i, j, k - 1) & 3;
                if (m == 1) {
                    block.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    this.method_76(block, i, j, k);
                } else if (m == 0) {
                    block.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    this.method_76(block, i, j, k);
                }
            }
            m = this.field_82.getTileMeta(i, j, k + 1) & 3;
            b = Tile.BY_ID[this.field_82.getTileId(i, j, k + 1)];
            if (b != null && b.method_1621() == 10 && (m == 0 || m == 1)) {
                if (m == 0) {
                    block.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    this.method_76(block, i, j, k);
                } else {
                    block.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    this.method_76(block, i, j, k);
                }
            } else {
                block.setBoundingBox(0.0f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                this.method_76(block, i, j, k);
            }
            flag = true;
        } else if (l == 3) {
            int m;
            Tile b = Tile.BY_ID[this.field_82.getTileId(i, j, k + 1)];
            if (b != null && b.method_1621() == 10) {
                m = this.field_82.getTileMeta(i, j, k + 1) & 3;
                if (m == 1) {
                    block.setBoundingBox(0.0f, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f);
                    this.method_76(block, i, j, k);
                } else if (m == 0) {
                    block.setBoundingBox(0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f);
                    this.method_76(block, i, j, k);
                }
            }
            m = this.field_82.getTileMeta(i, j, k - 1) & 3;
            b = Tile.BY_ID[this.field_82.getTileId(i, j, k - 1)];
            if (b != null && b.method_1621() == 10 && (m == 0 || m == 1)) {
                if (m == 0) {
                    block.setBoundingBox(0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                    this.method_76(block, i, j, k);
                } else {
                    block.setBoundingBox(0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 0.5f);
                    this.method_76(block, i, j, k);
                }
            } else {
                block.setBoundingBox(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 0.5f);
                this.method_76(block, i, j, k);
            }
            flag = true;
        }
        block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return flag;
    }

    public boolean renderBlockSlope(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        int l = this.field_82.getTileMeta(i, j, k) & 3;
        int texture = block.method_1626(this.field_82, i, j, k, 0);
        int u = (texture & 0xF) << 4;
        int v = texture & 0xF0;
        double u1 = (double)u / 256.0;
        double u2 = ((double)u + 16.0 - 0.01) / 256.0;
        double v1 = (double)v / 256.0;
        double v2 = ((double)v + 16.0 - 0.01) / 256.0;
        float b = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(0.5f * b, 0.5f * b, 0.5f * b);
        block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        tessellator.vertex(i, j, k, u1, v1);
        tessellator.vertex(i + 1, j, k, u2, v1);
        tessellator.vertex(i + 1, j, k + 1, u2, v2);
        tessellator.vertex(i, j, k + 1, u1, v2);
        if (l == 0) {
            Tile nB = Tile.BY_ID[this.field_82.getTileId(i - 1, j, k)];
            int m = this.field_82.getTileMeta(i - 1, j, k) & 3;
            if (nB != null && nB.method_1621() == 38 && (m == 2 || m == 3)) {
                if (m == 2) {
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                } else if (m == 3) {
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                }
                tessellator.vertex(i, j, k, u1, v2);
                tessellator.vertex(i, j, k + 1, u2, v2);
                tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                tessellator.vertex(i + 1, j + 1, k, u1, v1);
                tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                tessellator.vertex(i + 1, j, k, u1, v2);
                tessellator.vertex(i + 1, j + 1, k, u1, v1);
                tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                tessellator.vertex(i + 1, j, k + 1, u2, v2);
            } else {
                m = this.field_82.getTileMeta(i + 1, j, k) & 3;
                nB = Tile.BY_ID[this.field_82.getTileId(i + 1, j, k)];
                if (nB != null && nB.method_1621() == 38 && (m == 2 || m == 3)) {
                    if (m == 2) {
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    } else if (m == 3) {
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j + 1, k, u2, v1);
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k, u1, v1);
                        tessellator.vertex(i + 1, j + 1, k, u1, v1);
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i + 1, j + 1, k, u1, v1);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                    }
                } else {
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                }
            }
        } else if (l == 1) {
            Tile nB = Tile.BY_ID[this.field_82.getTileId(i + 1, j, k)];
            int m = this.field_82.getTileMeta(i + 1, j, k) & 3;
            if (nB != null && nB.method_1621() == 38 && (m == 2 || m == 3)) {
                if (m == 2) {
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                } else {
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                }
                tessellator.vertex(i, j + 1, k, u2, v1);
                tessellator.vertex(i, j + 1, k + 1, u1, v1);
                tessellator.vertex(i + 1, j, k + 1, u1, v2);
                tessellator.vertex(i + 1, j, k, u2, v2);
                tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                tessellator.vertex(i, j, k, u1, v2);
                tessellator.vertex(i, j, k + 1, u2, v2);
                tessellator.vertex(i, j + 1, k + 1, u2, v1);
                tessellator.vertex(i, j + 1, k, u1, v1);
            } else {
                m = this.field_82.getTileMeta(i - 1, j, k) & 3;
                nB = Tile.BY_ID[this.field_82.getTileId(i - 1, j, k)];
                if (nB != null && nB.method_1621() == 38 && (m == 2 || m == 3)) {
                    if (m == 3) {
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i, j + 1, k, u1, v1);
                        tessellator.vertex(i, j + 1, k, u1, v1);
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j + 1, k, u2, v1);
                        tessellator.vertex(i, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.vertex(i, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                    } else {
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.vertex(i, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    }
                } else {
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                }
            }
        } else if (l == 2) {
            int m = this.field_82.getTileMeta(i, j, k - 1) & 3;
            Tile nB = Tile.BY_ID[this.field_82.getTileId(i, j, k - 1)];
            if (nB != null && nB.method_1621() == 38 && (m == 0 || m == 1)) {
                if (m == 1) {
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                } else if (m == 0) {
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                }
                tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                tessellator.vertex(i + 1, j, k + 1, u2, v2);
                tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                tessellator.vertex(i, j + 1, k + 1, u1, v1);
                tessellator.vertex(i, j, k + 1, u1, v2);
                tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                tessellator.vertex(i, j + 1, k + 1, u2, v1);
                tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                tessellator.vertex(i + 1, j, k, u1, v2);
                tessellator.vertex(i, j, k, u2, v2);
            } else {
                m = this.field_82.getTileMeta(i, j, k + 1) & 3;
                nB = Tile.BY_ID[this.field_82.getTileId(i, j, k + 1)];
                if (nB != null && nB.method_1621() == 38 && (m == 0 || m == 1)) {
                    if (m == 0) {
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    } else {
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i, j + 1, k + 1, u1, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i + 1, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u2, v2);
                        tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i, j + 1, k + 1, u2, v1);
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u1, v2);
                    }
                } else {
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u2, v2);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                }
            }
        } else if (l == 3) {
            int m = this.field_82.getTileMeta(i, j, k + 1) & 3;
            Tile nB = Tile.BY_ID[this.field_82.getTileId(i, j, k + 1)];
            if (nB != null && nB.method_1621() == 38 && (m == 0 || m == 1)) {
                if (m == 1) {
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k + 1, u1, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                } else if (m == 0) {
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u1, v2);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k + 1, u2, v1);
                    tessellator.vertex(i + 1, j + 1, k, u1, v1);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                }
                tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                tessellator.vertex(i, j + 1, k, u1, v1);
                tessellator.vertex(i + 1, j + 1, k, u2, v1);
                tessellator.vertex(i + 1, j, k, u2, v2);
                tessellator.vertex(i, j, k, u1, v2);
                tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                tessellator.vertex(i, j, k + 1, u1, v2);
                tessellator.vertex(i + 1, j, k + 1, u2, v2);
                tessellator.vertex(i + 1, j + 1, k, u2, v1);
                tessellator.vertex(i, j + 1, k, u1, v1);
            } else {
                m = this.field_82.getTileMeta(i, j, k - 1) & 3;
                nB = Tile.BY_ID[this.field_82.getTileId(i, j, k - 1)];
                if (nB != null && nB.method_1621() == 38 && (m == 0 || m == 1)) {
                    if (m == 0) {
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k, u1, v1);
                        tessellator.vertex(i + 1, j + 1, k, u1, v1);
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j + 1, k, u2, v1);
                        tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.vertex(i + 1, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    } else {
                        tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                        tessellator.vertex(i, j + 1, k, u2, v1);
                        tessellator.vertex(i, j + 1, k, u2, v1);
                        tessellator.vertex(i + 1, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k, u2, v2);
                        tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                        tessellator.vertex(i, j, k + 1, u1, v2);
                        tessellator.vertex(i + 1, j, k + 1, u2, v2);
                        tessellator.vertex(i, j + 1, k, u1, v1);
                        tessellator.vertex(i, j + 1, k, u1, v1);
                        tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                        tessellator.vertex(i, j, k + 1, u2, v2);
                        tessellator.vertex(i, j + 1, k, u1, v1);
                        tessellator.vertex(i, j, k, u1, v2);
                        tessellator.vertex(i, j, k, u1, v2);
                    }
                } else {
                    tessellator.colour(0.8f * b, 0.8f * b, 0.8f * b);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.colour(0.9f * b, 0.9f * b, 0.9f * b);
                    tessellator.vertex(i, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.colour(0.6f * b, 0.6f * b, 0.6f * b);
                    tessellator.vertex(i + 1, j, k, u2, v2);
                    tessellator.vertex(i + 1, j + 1, k, u2, v1);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i + 1, j, k + 1, u1, v2);
                    tessellator.vertex(i, j, k + 1, u2, v2);
                    tessellator.vertex(i, j + 1, k, u1, v1);
                    tessellator.vertex(i, j, k, u1, v2);
                    tessellator.vertex(i, j, k, u1, v2);
                }
            }
        }
        return true;
    }

    public boolean method_80(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        DoorTile blockdoor = (DoorTile)block;
        boolean flag = false;
        float f = 0.5f;
        float f1 = 1.0f;
        float f2 = 0.8f;
        float f3 = 0.6f;
        float f4 = block.method_1604(this.field_82, i, j, k);
        float f5 = block.method_1604(this.field_82, i, j - 1, k);
        if (blockdoor.minY > 0.0) {
            f5 = f4;
        }
        if (block.getBlockLightValue(this.field_82, i, j, k) > 0) {
            f5 = 1.0f;
        }
        tessellator.colour(f * f5, f * f5, f * f5);
        this.method_46(block, i, j, k, block.method_1626(this.field_82, i, j, k, 0));
        flag = true;
        f5 = block.method_1604(this.field_82, i, j + 1, k);
        if (blockdoor.maxY < 1.0) {
            f5 = f4;
        }
        if (block.getBlockLightValue(this.field_82, i, j, k) > 0) {
            f5 = 1.0f;
        }
        tessellator.colour(f1 * f5, f1 * f5, f1 * f5);
        this.method_55(block, i, j, k, block.method_1626(this.field_82, i, j, k, 1));
        flag = true;
        f5 = block.method_1604(this.field_82, i, j, k - 1);
        if (blockdoor.minZ > 0.0) {
            f5 = f4;
        }
        if (block.getBlockLightValue(this.field_82, i, j, k) > 0) {
            f5 = 1.0f;
        }
        tessellator.colour(f2 * f5, f2 * f5, f2 * f5);
        int l = block.method_1626(this.field_82, i, j, k, 2);
        if (l < 0) {
            this.field_84 = true;
            l = -l;
        }
        this.method_61(block, i, j, k, l);
        flag = true;
        this.field_84 = false;
        f5 = block.method_1604(this.field_82, i, j, k + 1);
        if (blockdoor.maxZ < 1.0) {
            f5 = f4;
        }
        if (block.getBlockLightValue(this.field_82, i, j, k) > 0) {
            f5 = 1.0f;
        }
        tessellator.colour(f2 * f5, f2 * f5, f2 * f5);
        l = block.method_1626(this.field_82, i, j, k, 3);
        if (l < 0) {
            this.field_84 = true;
            l = -l;
        }
        this.method_65(block, i, j, k, l);
        flag = true;
        this.field_84 = false;
        f5 = block.method_1604(this.field_82, i - 1, j, k);
        if (blockdoor.minX > 0.0) {
            f5 = f4;
        }
        if (block.getBlockLightValue(this.field_82, i, j, k) > 0) {
            f5 = 1.0f;
        }
        tessellator.colour(f3 * f5, f3 * f5, f3 * f5);
        l = block.method_1626(this.field_82, i, j, k, 4);
        if (l < 0) {
            this.field_84 = true;
            l = -l;
        }
        this.method_67(block, i, j, k, l);
        flag = true;
        this.field_84 = false;
        f5 = block.method_1604(this.field_82, i + 1, j, k);
        if (blockdoor.maxX < 1.0) {
            f5 = f4;
        }
        if (block.getBlockLightValue(this.field_82, i, j, k) > 0) {
            f5 = 1.0f;
        }
        tessellator.colour(f3 * f5, f3 * f5, f3 * f5);
        l = block.method_1626(this.field_82, i, j, k, 5);
        if (l < 0) {
            this.field_84 = true;
            l = -l;
        }
        this.method_69(block, i, j, k, l);
        flag = true;
        this.field_84 = false;
        return flag;
    }

    public void method_46(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.INSTANCE;
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = ((double)j + block.minX * 16.0) / 256.0;
        double d4 = ((double)j + block.maxX * 16.0 - 0.01) / 256.0;
        double d5 = ((double)k + block.minZ * 16.0) / 256.0;
        double d6 = ((double)k + block.maxZ * 16.0 - 0.01) / 256.0;
        if (block.minX < 0.0 || block.maxX > 1.0) {
            d3 = ((float)j + 0.0f) / 256.0f;
            d4 = ((float)j + 15.99f) / 256.0f;
        }
        if (block.minZ < 0.0 || block.maxZ > 1.0) {
            d5 = ((float)k + 0.0f) / 256.0f;
            d6 = ((float)k + 15.99f) / 256.0f;
        }
        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;
        if (this.field_91 == 2) {
            d3 = ((double)j + block.minZ * 16.0) / 256.0;
            d5 = ((double)(k + 16) - block.maxX * 16.0) / 256.0;
            d4 = ((double)j + block.maxZ * 16.0) / 256.0;
            d6 = ((double)(k + 16) - block.minX * 16.0) / 256.0;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        } else if (this.field_91 == 1) {
            d3 = ((double)(j + 16) - block.maxZ * 16.0) / 256.0;
            d5 = ((double)k + block.minX * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.minZ * 16.0) / 256.0;
            d6 = ((double)k + block.maxX * 16.0) / 256.0;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d3 = d7;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        } else if (this.field_91 == 3) {
            d3 = ((double)(j + 16) - block.minX * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.maxX * 16.0 - 0.01) / 256.0;
            d5 = ((double)(k + 16) - block.minZ * 16.0) / 256.0;
            d6 = ((double)(k + 16) - block.maxZ * 16.0 - 0.01) / 256.0;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }
        double d11 = d + block.minX;
        double d12 = d + block.maxX;
        double d13 = d1 + block.minY;
        double d14 = d2 + block.minZ;
        double d15 = d2 + block.maxZ;
        if (this.field_92) {
            tessellator.colour(this.field_56, this.field_60, this.field_64);
            tessellator.vertex(d11, d13, d15, d8, d10);
            tessellator.colour(this.field_57, this.field_61, this.field_65);
            tessellator.vertex(d11, d13, d14, d3, d5);
            tessellator.colour(this.field_58, this.field_62, this.field_66);
            tessellator.vertex(d12, d13, d14, d7, d9);
            tessellator.colour(this.field_59, this.field_63, this.field_68);
            tessellator.vertex(d12, d13, d15, d4, d6);
        } else {
            tessellator.vertex(d11, d13, d15, d8, d10);
            tessellator.vertex(d11, d13, d14, d3, d5);
            tessellator.vertex(d12, d13, d14, d7, d9);
            tessellator.vertex(d12, d13, d15, d4, d6);
        }
    }

    public void method_55(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.INSTANCE;
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = ((double)j + block.minX * 16.0) / 256.0;
        double d4 = ((double)j + block.maxX * 16.0 - 0.01) / 256.0;
        double d5 = ((double)k + block.minZ * 16.0) / 256.0;
        double d6 = ((double)k + block.maxZ * 16.0 - 0.01) / 256.0;
        if (block.minX < 0.0 || block.maxX > 1.0) {
            d3 = ((float)j + 0.0f) / 256.0f;
            d4 = ((float)j + 15.99f) / 256.0f;
        }
        if (block.minZ < 0.0 || block.maxZ > 1.0) {
            d5 = ((float)k + 0.0f) / 256.0f;
            d6 = ((float)k + 15.99f) / 256.0f;
        }
        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;
        if (this.field_90 == 1) {
            d3 = ((double)j + block.minZ * 16.0) / 256.0;
            d5 = ((double)(k + 16) - block.maxX * 16.0) / 256.0;
            d4 = ((double)j + block.maxZ * 16.0) / 256.0;
            d6 = ((double)(k + 16) - block.minX * 16.0) / 256.0;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        } else if (this.field_90 == 2) {
            d3 = ((double)(j + 16) - block.maxZ * 16.0) / 256.0;
            d5 = ((double)k + block.minX * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.minZ * 16.0) / 256.0;
            d6 = ((double)k + block.maxX * 16.0) / 256.0;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d3 = d7;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        } else if (this.field_90 == 3) {
            d3 = ((double)(j + 16) - block.minX * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.maxX * 16.0 - 0.01) / 256.0;
            d5 = ((double)(k + 16) - block.minZ * 16.0) / 256.0;
            d6 = ((double)(k + 16) - block.maxZ * 16.0 - 0.01) / 256.0;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }
        double d11 = d + block.minX;
        double d12 = d + block.maxX;
        double d13 = d1 + block.maxY;
        double d14 = d2 + block.minZ;
        double d15 = d2 + block.maxZ;
        if (this.field_92) {
            tessellator.colour(this.field_56, this.field_60, this.field_64);
            tessellator.vertex(d12, d13, d15, d4, d6);
            tessellator.colour(this.field_57, this.field_61, this.field_65);
            tessellator.vertex(d12, d13, d14, d7, d9);
            tessellator.colour(this.field_58, this.field_62, this.field_66);
            tessellator.vertex(d11, d13, d14, d3, d5);
            tessellator.colour(this.field_59, this.field_63, this.field_68);
            tessellator.vertex(d11, d13, d15, d8, d10);
        } else {
            tessellator.vertex(d12, d13, d15, d4, d6);
            tessellator.vertex(d12, d13, d14, d7, d9);
            tessellator.vertex(d11, d13, d14, d3, d5);
            tessellator.vertex(d11, d13, d15, d8, d10);
        }
    }

    public void method_61(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.INSTANCE;
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = ((double)j + block.minX * 16.0) / 256.0;
        double d4 = ((double)j + block.maxX * 16.0 - 0.01) / 256.0;
        double d5 = ((double)(k + 16) - block.maxY * 16.0) / 256.0;
        double d6 = ((double)(k + 16) - block.minY * 16.0 - 0.01) / 256.0;
        if (this.field_84) {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if (block.minX < 0.0 || block.maxX > 1.0) {
            d3 = ((float)j + 0.0f) / 256.0f;
            d4 = ((float)j + 15.99f) / 256.0f;
        }
        if (block.minY < 0.0 || block.maxY > 1.0) {
            d5 = ((float)k + 0.0f) / 256.0f;
            d6 = ((float)k + 15.99f) / 256.0f;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.field_86 == 2) {
            d3 = ((double)j + block.minY * 16.0) / 256.0;
            d5 = ((double)(k + 16) - block.minX * 16.0) / 256.0;
            d4 = ((double)j + block.maxY * 16.0) / 256.0;
            d6 = ((double)(k + 16) - block.maxX * 16.0) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.field_86 == 1) {
            d3 = ((double)(j + 16) - block.maxY * 16.0) / 256.0;
            d5 = ((double)k + block.maxX * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.minY * 16.0) / 256.0;
            d6 = ((double)k + block.minX * 16.0) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.field_86 == 3) {
            d3 = ((double)(j + 16) - block.minX * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.maxX * 16.0 - 0.01) / 256.0;
            d5 = ((double)k + block.maxY * 16.0) / 256.0;
            d6 = ((double)k + block.minY * 16.0 - 0.01) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.minX;
        double d13 = d + block.maxX;
        double d14 = d1 + block.minY;
        double d15 = d1 + block.maxY;
        double d16 = d2 + block.minZ;
        if (this.field_92) {
            tessellator.colour(this.field_56, this.field_60, this.field_64);
            tessellator.vertex(d12, d15, d16, d8, d10);
            tessellator.colour(this.field_57, this.field_61, this.field_65);
            tessellator.vertex(d13, d15, d16, d3, d5);
            tessellator.colour(this.field_58, this.field_62, this.field_66);
            tessellator.vertex(d13, d14, d16, d9, d11);
            tessellator.colour(this.field_59, this.field_63, this.field_68);
            tessellator.vertex(d12, d14, d16, d4, d6);
        } else {
            tessellator.vertex(d12, d15, d16, d8, d10);
            tessellator.vertex(d13, d15, d16, d3, d5);
            tessellator.vertex(d13, d14, d16, d9, d11);
            tessellator.vertex(d12, d14, d16, d4, d6);
        }
    }

    public void method_65(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.INSTANCE;
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = ((double)j + block.minX * 16.0) / 256.0;
        double d4 = ((double)j + block.maxX * 16.0 - 0.01) / 256.0;
        double d5 = ((double)(k + 16) - block.maxY * 16.0) / 256.0;
        double d6 = ((double)(k + 16) - block.minY * 16.0 - 0.01) / 256.0;
        if (this.field_84) {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if (block.minX < 0.0 || block.maxX > 1.0) {
            d3 = ((float)j + 0.0f) / 256.0f;
            d4 = ((float)j + 15.99f) / 256.0f;
        }
        if (block.minY < 0.0 || block.maxY > 1.0) {
            d5 = ((float)k + 0.0f) / 256.0f;
            d6 = ((float)k + 15.99f) / 256.0f;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.field_87 == 1) {
            d3 = ((double)j + block.minY * 16.0) / 256.0;
            d6 = ((double)(k + 16) - block.minX * 16.0) / 256.0;
            d4 = ((double)j + block.maxY * 16.0) / 256.0;
            d5 = ((double)(k + 16) - block.maxX * 16.0) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.field_87 == 2) {
            d3 = ((double)(j + 16) - block.maxY * 16.0) / 256.0;
            d5 = ((double)k + block.minX * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.minY * 16.0) / 256.0;
            d6 = ((double)k + block.maxX * 16.0) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.field_87 == 3) {
            d3 = ((double)(j + 16) - block.minX * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.maxX * 16.0 - 0.01) / 256.0;
            d5 = ((double)k + block.maxY * 16.0) / 256.0;
            d6 = ((double)k + block.minY * 16.0 - 0.01) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.minX;
        double d13 = d + block.maxX;
        double d14 = d1 + block.minY;
        double d15 = d1 + block.maxY;
        double d16 = d2 + block.maxZ;
        if (this.field_92) {
            tessellator.colour(this.field_56, this.field_60, this.field_64);
            tessellator.vertex(d12, d15, d16, d3, d5);
            tessellator.colour(this.field_57, this.field_61, this.field_65);
            tessellator.vertex(d12, d14, d16, d9, d11);
            tessellator.colour(this.field_58, this.field_62, this.field_66);
            tessellator.vertex(d13, d14, d16, d4, d6);
            tessellator.colour(this.field_59, this.field_63, this.field_68);
            tessellator.vertex(d13, d15, d16, d8, d10);
        } else {
            tessellator.vertex(d12, d15, d16, d3, d5);
            tessellator.vertex(d12, d14, d16, d9, d11);
            tessellator.vertex(d13, d14, d16, d4, d6);
            tessellator.vertex(d13, d15, d16, d8, d10);
        }
    }

    public void method_67(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.INSTANCE;
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = ((double)j + block.minZ * 16.0) / 256.0;
        double d4 = ((double)j + block.maxZ * 16.0 - 0.01) / 256.0;
        double d5 = ((double)k + (1.0 - block.maxY) * 16.0) / 256.0;
        double d6 = ((double)k + (1.0 - block.minY) * 16.0 - 0.01) / 256.0;
        if (this.field_84) {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if (block.minZ < 0.0 || block.maxZ > 1.0) {
            d3 = ((float)j + 0.0f) / 256.0f;
            d4 = ((float)j + 15.99f) / 256.0f;
        }
        if (block.minY < 0.0 || block.maxY > 1.0) {
            d5 = ((float)k + 0.0f) / 256.0f;
            d6 = ((float)k + 15.99f) / 256.0f;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.field_89 == 1) {
            d3 = ((double)j + block.minY * 16.0) / 256.0;
            d5 = ((double)(k + 16) - block.maxZ * 16.0) / 256.0;
            d4 = ((double)j + block.maxY * 16.0) / 256.0;
            d6 = ((double)(k + 16) - block.minZ * 16.0) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.field_89 == 2) {
            d3 = ((double)(j + 16) - block.maxY * 16.0) / 256.0;
            d5 = ((double)k + block.minZ * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.minY * 16.0) / 256.0;
            d6 = ((double)k + block.maxZ * 16.0) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.field_89 == 3) {
            d3 = ((double)(j + 16) - block.minZ * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.maxZ * 16.0 - 0.01) / 256.0;
            d5 = ((double)k + block.maxY * 16.0) / 256.0;
            d6 = ((double)k + block.minY * 16.0 - 0.01) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.minX;
        double d13 = d1 + block.minY;
        double d14 = d1 + block.maxY;
        double d15 = d2 + block.minZ;
        double d16 = d2 + block.maxZ;
        if (this.field_92) {
            tessellator.colour(this.field_56, this.field_60, this.field_64);
            tessellator.vertex(d12, d14, d16, d8, d10);
            tessellator.colour(this.field_57, this.field_61, this.field_65);
            tessellator.vertex(d12, d14, d15, d3, d5);
            tessellator.colour(this.field_58, this.field_62, this.field_66);
            tessellator.vertex(d12, d13, d15, d9, d11);
            tessellator.colour(this.field_59, this.field_63, this.field_68);
            tessellator.vertex(d12, d13, d16, d4, d6);
        } else {
            tessellator.vertex(d12, d14, d16, d8, d10);
            tessellator.vertex(d12, d14, d15, d3, d5);
            tessellator.vertex(d12, d13, d15, d9, d11);
            tessellator.vertex(d12, d13, d16, d4, d6);
        }
    }

    public void method_69(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.INSTANCE;
        if (this.field_83 >= 0) {
            i = this.field_83;
        }
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = ((double)j + block.minZ * 16.0) / 256.0;
        double d4 = ((double)j + block.maxZ * 16.0 - 0.01) / 256.0;
        double d5 = ((double)k + (1.0 - block.maxY) * 16.0) / 256.0;
        double d6 = ((double)k + (1.0 - block.minY) * 16.0 - 0.01) / 256.0;
        if (this.field_84) {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if (block.minZ < 0.0 || block.maxZ > 1.0) {
            d3 = ((float)j + 0.0f) / 256.0f;
            d4 = ((float)j + 15.99f) / 256.0f;
        }
        if (block.minY < 0.0 || block.maxY > 1.0) {
            d5 = ((float)k + 0.0f) / 256.0f;
            d6 = ((float)k + 15.99f) / 256.0f;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.field_88 == 2) {
            d3 = ((double)j + block.minY * 16.0) / 256.0;
            d5 = ((double)(k + 16) - block.minZ * 16.0) / 256.0;
            d4 = ((double)j + block.maxY * 16.0) / 256.0;
            d6 = ((double)(k + 16) - block.maxZ * 16.0) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.field_88 == 1) {
            d3 = ((double)(j + 16) - block.maxY * 16.0) / 256.0;
            d5 = ((double)k + block.maxZ * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.minY * 16.0) / 256.0;
            d6 = ((double)k + block.minZ * 16.0) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.field_88 == 3) {
            d3 = ((double)(j + 16) - block.minZ * 16.0) / 256.0;
            d4 = ((double)(j + 16) - block.maxZ * 16.0 - 0.01) / 256.0;
            d5 = ((double)k + block.maxY * 16.0) / 256.0;
            d6 = ((double)k + block.minY * 16.0 - 0.01) / 256.0;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.maxX;
        double d13 = d1 + block.minY;
        double d14 = d1 + block.maxY;
        double d15 = d2 + block.minZ;
        double d16 = d2 + block.maxZ;
        if (this.field_92) {
            tessellator.colour(this.field_56, this.field_60, this.field_64);
            tessellator.vertex(d12, d13, d16, d9, d11);
            tessellator.colour(this.field_57, this.field_61, this.field_65);
            tessellator.vertex(d12, d13, d15, d4, d6);
            tessellator.colour(this.field_58, this.field_62, this.field_66);
            tessellator.vertex(d12, d14, d15, d8, d10);
            tessellator.colour(this.field_59, this.field_63, this.field_68);
            tessellator.vertex(d12, d14, d16, d3, d5);
        } else {
            tessellator.vertex(d12, d13, d16, d9, d11);
            tessellator.vertex(d12, d13, d15, d4, d6);
            tessellator.vertex(d12, d14, d15, d8, d10);
            tessellator.vertex(d12, d14, d16, d3, d5);
        }
    }

    public void method_48(Tile block, int i, float f) {
        int k;
        Tessellator tessellator = Tessellator.INSTANCE;
        if (this.field_81) {
            int j = block.method_1589(i);
            float f1 = (float)(j >> 16 & 0xFF) / 255.0f;
            float f3 = (float)(j >> 8 & 0xFF) / 255.0f;
            float f5 = (float)(j & 0xFF) / 255.0f;
            GL11.glColor4f(f1 * f, f3 * f, f5 * f, 1.0f);
        }
        if ((k = block.method_1621()) == 0 || k == 16) {
            if (k == 16) {
                i = 1;
            }
            block.method_1605();
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            tessellator.start();
            tessellator.method_1697(0.0f, -1.0f, 0.0f);
            this.method_46(block, 0.0, 0.0, 0.0, block.getTextureForSide(0, i));
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(0.0f, 1.0f, 0.0f);
            this.method_55(block, 0.0, 0.0, 0.0, block.getTextureForSide(1, i));
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(0.0f, 0.0f, -1.0f);
            this.method_61(block, 0.0, 0.0, 0.0, block.getTextureForSide(2, i));
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(0.0f, 0.0f, 1.0f);
            this.method_65(block, 0.0, 0.0, 0.0, block.getTextureForSide(3, i));
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(-1.0f, 0.0f, 0.0f);
            this.method_67(block, 0.0, 0.0, 0.0, block.getTextureForSide(4, i));
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(1.0f, 0.0f, 0.0f);
            this.method_69(block, 0.0, 0.0, 0.0, block.getTextureForSide(5, i));
            tessellator.draw();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        } else if (k == 1) {
            tessellator.start();
            tessellator.method_1697(0.0f, -1.0f, 0.0f);
            this.method_47(block, i, -0.5, -0.5, -0.5);
            tessellator.draw();
        } else if (k == 13) {
            block.method_1605();
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            float f2 = 0.0625f;
            tessellator.start();
            tessellator.method_1697(0.0f, -1.0f, 0.0f);
            this.method_46(block, 0.0, 0.0, 0.0, block.getTextureForSide(0, i));
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(0.0f, 1.0f, 0.0f);
            this.method_55(block, 0.0, 0.0, 0.0, block.getTextureForSide(1, i));
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(0.0f, 0.0f, -1.0f);
            tessellator.changePrevPos(0.0f, 0.0f, f2);
            this.method_61(block, 0.0, 0.0, 0.0, block.getTextureForSide(2, i));
            tessellator.changePrevPos(0.0f, 0.0f, -f2);
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(0.0f, 0.0f, 1.0f);
            tessellator.changePrevPos(0.0f, 0.0f, -f2);
            this.method_65(block, 0.0, 0.0, 0.0, block.getTextureForSide(3, i));
            tessellator.changePrevPos(0.0f, 0.0f, f2);
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(-1.0f, 0.0f, 0.0f);
            tessellator.changePrevPos(f2, 0.0f, 0.0f);
            this.method_67(block, 0.0, 0.0, 0.0, block.getTextureForSide(4, i));
            tessellator.changePrevPos(-f2, 0.0f, 0.0f);
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(1.0f, 0.0f, 0.0f);
            tessellator.changePrevPos(-f2, 0.0f, 0.0f);
            this.method_69(block, 0.0, 0.0, 0.0, block.getTextureForSide(5, i));
            tessellator.changePrevPos(f2, 0.0f, 0.0f);
            tessellator.draw();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        } else if (k == 6) {
            tessellator.start();
            tessellator.method_1697(0.0f, -1.0f, 0.0f);
            this.method_56(block, i, -0.5, -0.5, -0.5);
            tessellator.draw();
        } else if (k == 2) {
            tessellator.start();
            tessellator.method_1697(0.0f, -1.0f, 0.0f);
            this.method_45(block, -0.5, -0.5, -0.5, 0.0, 0.0);
            tessellator.draw();
        } else if (k == 10) {
            for (int l = 0; l < 2; ++l) {
                if (l == 0) {
                    block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
                }
                if (l == 1) {
                    block.setBoundingBox(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
                }
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                tessellator.start();
                tessellator.method_1697(0.0f, -1.0f, 0.0f);
                this.method_46(block, 0.0, 0.0, 0.0, block.getTextureForSide(0, i));
                tessellator.draw();
                tessellator.start();
                tessellator.method_1697(0.0f, 1.0f, 0.0f);
                this.method_55(block, 0.0, 0.0, 0.0, block.getTextureForSide(1, i));
                tessellator.draw();
                tessellator.start();
                tessellator.method_1697(0.0f, 0.0f, -1.0f);
                this.method_61(block, 0.0, 0.0, 0.0, block.getTextureForSide(2, i));
                tessellator.draw();
                tessellator.start();
                tessellator.method_1697(0.0f, 0.0f, 1.0f);
                this.method_65(block, 0.0, 0.0, 0.0, block.getTextureForSide(3, i));
                tessellator.draw();
                tessellator.start();
                tessellator.method_1697(-1.0f, 0.0f, 0.0f);
                this.method_67(block, 0.0, 0.0, 0.0, block.getTextureForSide(4, i));
                tessellator.draw();
                tessellator.start();
                tessellator.method_1697(1.0f, 0.0f, 0.0f);
                this.method_69(block, 0.0, 0.0, 0.0, block.getTextureForSide(5, i));
                tessellator.draw();
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            }
        } else if (k == 11) {
            for (int i1 = 0; i1 < 4; ++i1) {
                float f4 = 0.125f;
                if (i1 == 0) {
                    block.setBoundingBox(0.5f - f4, 0.0f, 0.0f, 0.5f + f4, 1.0f, f4 * 2.0f);
                }
                if (i1 == 1) {
                    block.setBoundingBox(0.5f - f4, 0.0f, 1.0f - f4 * 2.0f, 0.5f + f4, 1.0f, 1.0f);
                }
                f4 = 0.0625f;
                if (i1 == 2) {
                    block.setBoundingBox(0.5f - f4, 1.0f - f4 * 3.0f, -f4 * 2.0f, 0.5f + f4, 1.0f - f4, 1.0f + f4 * 2.0f);
                }
                if (i1 == 3) {
                    block.setBoundingBox(0.5f - f4, 0.5f - f4 * 3.0f, -f4 * 2.0f, 0.5f + f4, 0.5f - f4, 1.0f + f4 * 2.0f);
                }
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                tessellator.start();
                tessellator.method_1697(0.0f, -1.0f, 0.0f);
                this.method_46(block, 0.0, 0.0, 0.0, block.getTextureForSide(0));
                tessellator.draw();
                tessellator.start();
                tessellator.method_1697(0.0f, 1.0f, 0.0f);
                this.method_55(block, 0.0, 0.0, 0.0, block.getTextureForSide(1));
                tessellator.draw();
                tessellator.start();
                tessellator.method_1697(0.0f, 0.0f, -1.0f);
                this.method_61(block, 0.0, 0.0, 0.0, block.getTextureForSide(2));
                tessellator.draw();
                tessellator.start();
                tessellator.method_1697(0.0f, 0.0f, 1.0f);
                this.method_65(block, 0.0, 0.0, 0.0, block.getTextureForSide(3));
                tessellator.draw();
                tessellator.start();
                tessellator.method_1697(-1.0f, 0.0f, 0.0f);
                this.method_67(block, 0.0, 0.0, 0.0, block.getTextureForSide(4));
                tessellator.draw();
                tessellator.start();
                tessellator.method_1697(1.0f, 0.0f, 0.0f);
                this.method_69(block, 0.0, 0.0, 0.0, block.getTextureForSide(5));
                tessellator.draw();
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
            }
            block.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static boolean method_42(int i) {
        if (i == 0) {
            return true;
        }
        if (i == 13) {
            return true;
        }
        if (i == 10) {
            return true;
        }
        if (i == 11) {
            return true;
        }
        return i == 16;
    }

    public boolean renderGrass(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j + 1, k);
        int l = block.getTint(this.field_82, i, j, k);
        float r = (float)(l >> 16 & 0xFF) / 255.0f;
        float g = (float)(l >> 8 & 0xFF) / 255.0f;
        float b = (float)(l & 0xFF) / 255.0f;
        int metadata = this.field_82.getTileMeta(i, j, k);
        float multiplier = Tile.GRASS.grassMultiplier(metadata);
        if (multiplier < 0.0f) {
            return false;
        }
        tessellator.colour(f * (r *= multiplier), f * (g *= multiplier), f * (b *= multiplier));
        double d = i;
        double d1 = (float)j - 0.0625f + 1.0f;
        double d2 = k;
        this.rand.setSeed((long)(i * i * 3121 + i * 45238971 + k * k * 418711 + k * 13761 + j));
        j = 168;
        int u = (j & 0xF) << 4;
        int v = j & 0xF0;
        double d3 = (float)(u += this.rand.nextInt(32)) / 256.0f;
        double d4 = ((float)u + 15.99f) / 256.0f;
        double d5 = (float)v / 256.0f;
        double d6 = ((float)v + 15.99f) / 256.0f;
        double d7 = d + 0.5 - (double)0.45f;
        double d8 = d + 0.5 + (double)0.45f;
        double d9 = d2 + 0.5 - (double)0.45f;
        double d10 = d2 + 0.5 + (double)0.45f;
        tessellator.vertex(d7, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d10, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d9, d4, d5);
        u = (j & 0xF) << 4;
        v = j & 0xF0;
        d3 = (float)(u += this.rand.nextInt(32)) / 256.0f;
        d4 = ((float)u + 15.99f) / 256.0f;
        d5 = (float)v / 256.0f;
        d6 = ((float)v + 15.99f) / 256.0f;
        tessellator.vertex(d7, d1 + 1.0, d10, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d8, d1 + 1.0, d9, d4, d5);
        tessellator.vertex(d8, d1 + 1.0, d9, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d7, d1 + 1.0, d10, d4, d5);
        return true;
    }

    public boolean renderSpikes(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        if (this.field_82.isFullOpaque(i, j - 1, k)) {
            this.method_47(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (this.field_82.isFullOpaque(i, j + 1, k)) {
            this.renderCrossedSquaresUpsideDown(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (this.field_82.isFullOpaque(i - 1, j, k)) {
            this.renderCrossedSquaresEast(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (this.field_82.isFullOpaque(i + 1, j, k)) {
            this.renderCrossedSquaresWest(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (this.field_82.isFullOpaque(i, j, k - 1)) {
            this.renderCrossedSquaresNorth(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (this.field_82.isFullOpaque(i, j, k + 1)) {
            this.renderCrossedSquaresSouth(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else {
            this.method_47(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        }
        return true;
    }

    public boolean renderTable(Tile block, int i, int j, int k) {
        boolean east;
        boolean rendered = this.method_76(block, i, j, k);
        boolean north = this.field_82.getTileId(i, j, k + 1) != Blocks.tableBlocks.id;
        boolean south = this.field_82.getTileId(i, j, k - 1) != Blocks.tableBlocks.id;
        boolean west = this.field_82.getTileId(i - 1, j, k) != Blocks.tableBlocks.id;
        boolean bl = east = this.field_82.getTileId(i + 1, j, k) != Blocks.tableBlocks.id;
        if (west && south) {
            block.setBoundingBox(0.0f, 0.0f, 0.0f, 0.1875f, 0.875f, 0.1875f);
            rendered |= this.method_76(block, i, j, k);
        }
        if (east && south) {
            block.setBoundingBox(0.8125f, 0.0f, 0.0f, 1.0f, 0.875f, 0.1875f);
            rendered |= this.method_76(block, i, j, k);
        }
        if (east && north) {
            block.setBoundingBox(0.8125f, 0.0f, 0.8125f, 1.0f, 0.875f, 1.0f);
            rendered |= this.method_76(block, i, j, k);
        }
        if (west && north) {
            block.setBoundingBox(0.0f, 0.0f, 0.8125f, 0.1875f, 0.875f, 1.0f);
            rendered |= this.method_76(block, i, j, k);
        }
        block.setBoundingBox(0.0f, 0.875f, 0.0f, 1.0f, 1.0f, 1.0f);
        return rendered;
    }

    public boolean renderChair(Tile block, int i, int j, int k) {
        boolean rendered = this.method_76(block, i, j, k);
        int side = this.field_82.getTileMeta(i, j, k) % 4;
        switch (side) {
            case 0: {
                block.setBoundingBox(0.125f, 0.625f, 0.125f, 0.25f, 1.25f, 0.875f);
                rendered |= this.method_76(block, i, j, k);
                break;
            }
            case 1: {
                block.setBoundingBox(0.125f, 0.625f, 0.125f, 0.875f, 1.25f, 0.25f);
                rendered |= this.method_76(block, i, j, k);
                break;
            }
            case 2: {
                block.setBoundingBox(0.75f, 0.625f, 0.125f, 0.875f, 1.25f, 0.875f);
                rendered |= this.method_76(block, i, j, k);
                break;
            }
            case 3: {
                block.setBoundingBox(0.125f, 0.625f, 0.75f, 0.875f, 1.25f, 0.875f);
                rendered |= this.method_76(block, i, j, k);
            }
        }
        block.setBoundingBox(0.125f, 0.0f, 0.125f, 0.25f, 0.5f, 0.25f);
        rendered |= this.method_76(block, i, j, k);
        block.setBoundingBox(0.75f, 0.0f, 0.125f, 0.875f, 0.5f, 0.25f);
        rendered |= this.method_76(block, i, j, k);
        block.setBoundingBox(0.75f, 0.0f, 0.75f, 0.875f, 0.5f, 0.875f);
        rendered |= this.method_76(block, i, j, k);
        block.setBoundingBox(0.125f, 0.0f, 0.75f, 0.25f, 0.5f, 0.875f);
        block.setBoundingBox(0.125f, 0.5f, 0.125f, 0.875f, 0.625f, 0.875f);
        return rendered |= this.method_76(block, i, j, k);
    }

    public boolean renderRope(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        int m = this.field_82.getTileMeta(i, j, k) % 3;
        if (m == 0) {
            this.method_47(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else if (m == 1) {
            this.renderCrossedSquaresEast(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        } else {
            this.renderCrossedSquaresNorth(block, this.field_82.getTileMeta(i, j, k), i, j, k);
        }
        return true;
    }

    public boolean renderBlockTree(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        TileEntity o = this.field_82.getTileEntity(i, j, k);
        TileEntityTree obj = null;
        if (o instanceof TileEntityTree) {
            obj = (TileEntityTree)o;
        }
        double d = i;
        double d1 = j;
        double d2 = k;
        int m = this.field_82.getTileMeta(i, j, k);
        int t = block.getTextureForSide(0, m);
        if (this.field_83 >= 0) {
            t = this.field_83;
        }
        int u = (t & 0xF) << 4;
        int v = t & 0xF0;
        double d3 = (float)u / 256.0f;
        double d4 = ((float)u + 15.99f) / 256.0f;
        double d5 = (float)v / 256.0f;
        double d6 = ((float)v + 15.99f) / 256.0f;
        double size = 1.0;
        if (obj != null) {
            size = obj.size;
        }
        double d7 = d + 0.5 - (double)0.45f * size;
        double d8 = d + 0.5 + (double)0.45f * size;
        double d9 = d2 + 0.5 - (double)0.45f * size;
        double d10 = d2 + 0.5 + (double)0.45f * size;
        tessellator.vertex(d7, d1 + size, d9, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d8, d1 + size, d10, d4, d5);
        tessellator.vertex(d8, d1 + size, d10, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d7, d1 + size, d9, d4, d5);
        if (this.field_83 < 0) {
            t = block.getTextureForSide(1, m);
            u = (t & 0xF) << 4;
            v = t & 0xF0;
            d3 = (float)u / 256.0f;
            d4 = ((float)u + 15.99f) / 256.0f;
            d5 = (float)v / 256.0f;
            d6 = ((float)v + 15.99f) / 256.0f;
        }
        tessellator.vertex(d7, d1 + size, d10, d3, d5);
        tessellator.vertex(d7, d1 + 0.0, d10, d3, d6);
        tessellator.vertex(d8, d1 + 0.0, d9, d4, d6);
        tessellator.vertex(d8, d1 + size, d9, d4, d5);
        tessellator.vertex(d8, d1 + size, d9, d3, d5);
        tessellator.vertex(d8, d1 + 0.0, d9, d3, d6);
        tessellator.vertex(d7, d1 + 0.0, d10, d4, d6);
        tessellator.vertex(d7, d1 + size, d10, d4, d5);
        return true;
    }

    public boolean renderBlockOverlay(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f = block.method_1604(this.field_82, i, j, k);
        tessellator.colour(f, f, f);
        int m = this.field_82.getTileMeta(i, j, k);
        int t = block.getTextureForSide(0, m);
        ((BlockOverlay)block).updateBounds(this.field_82, i, j, k);
        if (this.field_82.isFullOpaque(i, j - 1, k)) {
            this.method_55(block, i, j, k, t);
        } else if (this.field_82.isFullOpaque(i, j + 1, k)) {
            this.method_46(block, i, j, k, t);
        } else if (this.field_82.isFullOpaque(i - 1, j, k)) {
            this.method_69(block, i, j, k, t);
        } else if (this.field_82.isFullOpaque(i + 1, j, k)) {
            this.method_67(block, i, j, k, t);
        } else if (this.field_82.isFullOpaque(i, j, k - 1)) {
            this.method_65(block, i, j, k, t);
        } else if (this.field_82.isFullOpaque(i, j, k + 1)) {
            this.method_61(block, i, j, k, t);
        } else {
            this.method_55(block, i, j, k, t);
        }
        return true;
    }
}