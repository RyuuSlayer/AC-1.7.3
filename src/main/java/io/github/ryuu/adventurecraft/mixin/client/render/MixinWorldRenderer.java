package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.scripting.ScriptModel;
import io.github.ryuu.adventurecraft.util.CutsceneCameraPoint;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.IEntityPather;
import io.github.ryuu.adventurecraft.util.PlayerTorch;
import net.minecraft.*;
import net.minecraft.client.GLAllocator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.tile.TileEntityRenderDispatcher;
import net.minecraft.client.sortme.ReverseEntityComparatorThing;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.WalkingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.RecordItem;
import net.minecraft.level.Level;
import net.minecraft.level.LevelListener;
import net.minecraft.level.chunk.ClientChunkCache;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import net.minecraft.util.maths.Vec3i;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.IntBuffer;
import java.util.*;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer implements LevelListener {

    private final TextureManager textureManager;
    private final List field_1807;
    private final int field_1813;
    private final Minecraft client;
    private final int field_1818 = 0;
    private final int field_1819;
    private final int field_1820;
    private final int field_1775;
    private final List field_1793;
    private final class_472[] field_1794 = new class_472[]{new class_472(), new class_472(), new class_472(), new class_472()};
    @Shadow()
    public List field_1795;
    public float field_1803;
    int[] field_1796;
    IntBuffer field_1797;
    int field_1798 = 0;
    int field_1799;
    double field_1800 = -9999.0;
    double field_1801 = -9999.0;
    double field_1802 = -9999.0;
    int field_1804 = 0;
    private Level level;
    private class_66[] field_1808;
    private class_66[] field_1809;
    private int field_1810;
    private int field_1811;
    private int field_1812;
    private TileRenderer tileRenderer;
    private IntBuffer field_1816;
    private boolean field_1817 = false;
    private int field_1776;
    private int field_1777;
    private int field_1778;
    private int field_1779;
    private int field_1780;
    private int field_1781;
    private int field_1782 = -1;
    private int field_1783 = 2;
    private int field_1784;
    private int field_1785;
    private int field_1786;
    private int field_1787;
    private int field_1788;
    private int field_1789;
    private int field_1790;
    private int field_1791;
    private int field_1792;

    public MixinWorldRenderer(Minecraft minecraft, TextureManager renderengine) {
        this.field_1795 = new ArrayList();
        this.field_1807 = new ArrayList();
        this.field_1796 = new int[50000];
        this.field_1797 = GLAllocator.createIntBuffer(64);
        this.field_1793 = new ArrayList();
        this.field_1799 = GLAllocator.add(1);
        this.client = minecraft;
        this.textureManager = renderengine;
        int byte0 = 64;
        this.field_1813 = GLAllocator.add(byte0 * byte0 * byte0 * 3);
        this.field_1817 = minecraft.getOcclusionQueryTester().isEnabled();
        if (this.field_1817) {
            this.field_1797.clear();
            this.field_1816 = GLAllocator.createIntBuffer(byte0 * byte0 * byte0);
            this.field_1816.clear();
            this.field_1816.position(0);
            this.field_1816.limit(byte0 * byte0 * byte0);
            ARBOcclusionQuery.glGenQueriesARB(this.field_1816);
        }
        this.field_1819 = GLAllocator.add(3);
        GL11.glPushMatrix();
        GL11.glNewList(this.field_1819, 4864);
        this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator tessellator = Tessellator.INSTANCE;
        this.field_1820 = this.field_1819 + 1;
        GL11.glNewList(this.field_1820, 4864);
        int byte1 = 64;
        int i = 256 / byte1 + 2;
        float f = 16.0f;
        for (int j = -byte1 * i; j <= byte1 * i; j += byte1) {
            for (int l = -byte1 * i; l <= byte1 * i; l += byte1) {
                tessellator.start();
                tessellator.pos(j + 0, f, l + 0);
                tessellator.pos(j + byte1, f, l + 0);
                tessellator.pos(j + byte1, f, l + byte1);
                tessellator.pos(j + 0, f, l + byte1);
                tessellator.draw();
            }
        }
        GL11.glEndList();
        this.field_1775 = this.field_1819 + 2;
        GL11.glNewList(this.field_1775, 4864);
        f = -16.0f;
        tessellator.start();
        for (int k = -byte1 * i; k <= byte1 * i; k += byte1) {
            for (int i1 = -byte1 * i; i1 <= byte1 * i; i1 += byte1) {
                tessellator.pos(k + byte1, f, i1 + 0);
                tessellator.pos(k + 0, f, i1 + 0);
                tessellator.pos(k + 0, f, i1 + byte1);
                tessellator.pos(k + byte1, f, i1 + byte1);
            }
        }
        tessellator.draw();
        GL11.glEndList();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void renderStars() {
        Random random = new Random(10842L);
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start();
        for (int i = 0; i < 1500; ++i) {
            double d = random.nextFloat() * 2.0f - 1.0f;
            double d1 = random.nextFloat() * 2.0f - 1.0f;
            double d2 = random.nextFloat() * 2.0f - 1.0f;
            double d3 = 0.25f + random.nextFloat() * 0.25f;
            double d4 = d * d + d1 * d1 + d2 * d2;
            if (d4 >= 1.0 || d4 <= 0.01) continue;
            d4 = 1.0 / Math.sqrt(d4);
            double d5 = (d *= d4) * 100.0;
            double d6 = (d1 *= d4) * 100.0;
            double d7 = (d2 *= d4) * 100.0;
            double d8 = Math.atan2(d, d2);
            double d9 = Math.sin(d8);
            double d10 = Math.cos(d8);
            double d11 = Math.atan2(Math.sqrt(d * d + d2 * d2), d1);
            double d12 = Math.sin(d11);
            double d13 = Math.cos(d11);
            double d14 = random.nextDouble() * Math.PI * 2.0;
            double d15 = Math.sin(d14);
            double d16 = Math.cos(d14);
            for (int j = 0; j < 4; ++j) {
                double d22;
                double d17 = 0.0;
                double d18 = (double) ((j & 2) - 1) * d3;
                double d19 = (double) ((j + 1 & 2) - 1) * d3;
                double d20 = d17;
                double d21 = d18 * d16 - d19 * d15;
                double d23 = d22 = d19 * d16 + d18 * d15;
                double d24 = d21 * d12 + d20 * d13;
                double d25 = d20 * d12 - d21 * d13;
                double d26 = d25 * d9 - d23 * d10;
                double d27 = d24;
                double d28 = d23 * d9 + d25 * d10;
                tessellator.pos(d5 + d26, d6 + d27, d7 + d28);
            }
        }
        tessellator.draw();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1546(Level world) {
        if (this.level != null) {
            this.level.removeListener(this);
        }
        this.field_1800 = -9999.0;
        this.field_1801 = -9999.0;
        this.field_1802 = -9999.0;
        EntityRenderDispatcher.INSTANCE.setLevel(world);
        this.level = world;
        this.tileRenderer = new TileRenderer(world);
        if (world != null) {
            world.addListener(this);
            this.method_1537();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1537() {
        LivingEntity entityliving;
        Tile.LEAVES.setFastGraphics(this.client.options.fancyGraphics);
        this.field_1782 = this.client.options.viewDistance;
        if (this.field_1809 != null) {
            for (net.minecraft.class_66 class_66 : this.field_1809) {
                class_66.method_302();
            }
        }
        int j = 64 << 4 - this.field_1782;
        if (this.field_1782 > 0) {
            if (j > 400) {
                j = 400;
            }
        } else if (j > 800) {
            j = 800;
        }
        this.field_1810 = j / 16 + 1;
        this.field_1811 = 8;
        this.field_1812 = j / 16 + 1;
        this.field_1809 = new class_66[this.field_1810 * this.field_1811 * this.field_1812];
        this.field_1808 = new class_66[this.field_1810 * this.field_1811 * this.field_1812];
        int k = 0;
        int l = 0;
        this.field_1776 = 0;
        this.field_1777 = 0;
        this.field_1778 = 0;
        this.field_1779 = this.field_1810;
        this.field_1780 = this.field_1811;
        this.field_1781 = this.field_1812;
        for (Object o : this.field_1807) {
            ((class_66) o).field_249 = false;
        }
        this.field_1807.clear();
        this.field_1795.clear();
        for (int j1 = 0; j1 < this.field_1810; ++j1) {
            for (int k1 = 0; k1 < this.field_1811; ++k1) {
                for (int l1 = 0; l1 < this.field_1812; ++l1) {
                    this.field_1809[(l1 * this.field_1811 + k1) * this.field_1810 + j1] = new class_66(this.level, this.field_1795, j1 * 16, k1 * 16, l1 * 16, 16, this.field_1813 + k);
                    if (this.field_1817) {
                        this.field_1809[(l1 * this.field_1811 + k1) * this.field_1810 + j1].field_254 = this.field_1816.get(l);
                    }
                    this.field_1809[(l1 * this.field_1811 + k1) * this.field_1810 + j1].field_253 = false;
                    this.field_1809[(l1 * this.field_1811 + k1) * this.field_1810 + j1].field_252 = true;
                    this.field_1809[(l1 * this.field_1811 + k1) * this.field_1810 + j1].field_243 = true;
                    this.field_1809[(l1 * this.field_1811 + k1) * this.field_1810 + j1].field_251 = l++;
                    this.field_1809[(l1 * this.field_1811 + k1) * this.field_1810 + j1].method_305();
                    this.field_1808[(l1 * this.field_1811 + k1) * this.field_1810 + j1] = this.field_1809[(l1 * this.field_1811 + k1) * this.field_1810 + j1];
                    this.field_1807.add(this.field_1809[(l1 * this.field_1811 + k1) * this.field_1810 + j1]);
                    k += 3;
                }
            }
        }
        if (this.level != null && (entityliving = this.client.field_2807) != null) {
            this.method_1553(MathsHelper.floor(entityliving.x), MathsHelper.floor(entityliving.y), MathsHelper.floor(entityliving.z));
            Arrays.sort((Object[]) this.field_1808, new ReverseEntityComparatorThing(entityliving));
        }
        this.field_1783 = 2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1544(Vec3f vec3d, class_68 icamera, float f) {
        if (this.field_1783 > 0) {
            --this.field_1783;
            return;
        }
        TileEntityRenderDispatcher.INSTANCE.refresh(this.level, this.textureManager, this.client.textRenderer, this.client.field_2807, f);
        EntityRenderDispatcher.INSTANCE.method_1917(this.level, this.textureManager, this.client.textRenderer, this.client.field_2807, this.client.options, f);
        this.field_1784 = 0;
        this.field_1785 = 0;
        this.field_1786 = 0;
        LivingEntity entityliving = this.client.field_2807;
        EntityRenderDispatcher.field_2490 = entityliving.prevRenderX + (entityliving.x - entityliving.prevRenderX) * (double) f;
        EntityRenderDispatcher.field_2491 = entityliving.prevRenderY + (entityliving.y - entityliving.prevRenderY) * (double) f;
        EntityRenderDispatcher.field_2492 = entityliving.prevRenderZ + (entityliving.z - entityliving.prevRenderZ) * (double) f;
        TileEntityRenderDispatcher.renderOffsetX = entityliving.prevRenderX + (entityliving.x - entityliving.prevRenderX) * (double) f;
        TileEntityRenderDispatcher.renderOffsetY = entityliving.prevRenderY + (entityliving.y - entityliving.prevRenderY) * (double) f;
        TileEntityRenderDispatcher.renderOffsetZ = entityliving.prevRenderZ + (entityliving.z - entityliving.prevRenderZ) * (double) f;
        List list = this.level.method_291();
        this.field_1784 = list.size();
        for (int i = 0; i < this.level.field_201.size(); ++i) {
            Entity entity = (Entity) this.level.field_201.get(i);
            ++this.field_1785;
            if (!entity.shouldRenderFrom(vec3d)) continue;
            EntityRenderDispatcher.INSTANCE.method_1921(entity, f);
        }
        for (Object o : list) {
            Entity entity1 = (Entity) o;
            if (!entity1.shouldRenderFrom(vec3d) || !entity1.field_1622 && !icamera.method_2007(entity1.boundingBox) || entity1 == this.client.field_2807 && !this.client.options.thirdPerson && !this.client.field_2807.isSleeping())
                continue;
            int l = MathsHelper.floor(entity1.y);
            if (l < 0) {
                l = 0;
            }
            if (l >= 128) {
                l = 127;
            }
            if (!this.level.isTileLoaded(MathsHelper.floor(entity1.x), l, MathsHelper.floor(entity1.z))) continue;
            ++this.field_1785;
            if (this.client.cameraActive && this.client.cameraPause || DebugMode.active && !(entity1 instanceof Player) || entity1.stunned > 0) {
                EntityRenderDispatcher.INSTANCE.method_1921(entity1, 1.0f);
                continue;
            }
            EntityRenderDispatcher.INSTANCE.method_1921(entity1, f);
        }
        GL11.glPushMatrix();
        GL11.glTranslated(-EntityRenderDispatcher.field_2490, -EntityRenderDispatcher.field_2491, -EntityRenderDispatcher.field_2492);
        ScriptModel.renderAll(f);
        GL11.glPopMatrix();
        for (Object o : this.field_1795) {
            TileEntityRenderDispatcher.INSTANCE.renderTileEntity((TileEntity) o, f);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1553(int i, int j, int k) {
        i -= 8;
        j -= 8;
        k -= 8;
        this.field_1776 = Integer.MAX_VALUE;
        this.field_1777 = Integer.MAX_VALUE;
        this.field_1778 = Integer.MAX_VALUE;
        this.field_1779 = Integer.MIN_VALUE;
        this.field_1780 = Integer.MIN_VALUE;
        this.field_1781 = Integer.MIN_VALUE;
        int l = this.field_1810 * 16;
        int i1 = l / 2;
        for (int j1 = 0; j1 < this.field_1810; ++j1) {
            int k1 = j1 * 16;
            int l1 = k1 + i1 - i;
            if (l1 < 0) {
                l1 -= l - 1;
            }
            if ((k1 -= (l1 /= l) * l) < this.field_1776) {
                this.field_1776 = k1;
            }
            if (k1 > this.field_1779) {
                this.field_1779 = k1;
            }
            for (int i2 = 0; i2 < this.field_1812; ++i2) {
                int j2 = i2 * 16;
                int k2 = j2 + i1 - k;
                if (k2 < 0) {
                    k2 -= l - 1;
                }
                if ((j2 -= (k2 /= l) * l) < this.field_1778) {
                    this.field_1778 = j2;
                }
                if (j2 > this.field_1781) {
                    this.field_1781 = j2;
                }
                for (int l2 = 0; l2 < this.field_1811; ++l2) {
                    int i3 = l2 * 16;
                    if (i3 < this.field_1777) {
                        this.field_1777 = i3;
                    }
                    if (i3 > this.field_1780) {
                        this.field_1780 = i3;
                    }
                    class_66 worldrenderer = this.field_1809[(i2 * this.field_1811 + l2) * this.field_1810 + j1];
                    boolean flag = worldrenderer.field_249;
                    worldrenderer.method_298(k1, i3, j2);
                    if (flag || !worldrenderer.field_249) continue;
                    this.field_1807.add(worldrenderer);
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_1548(LivingEntity entityliving, int i, double d) {
        for (int j = 0; j < 10; ++j) {
            this.field_1792 = (this.field_1792 + 1) % this.field_1809.length;
            class_66 worldrenderer = this.field_1809[this.field_1792];
            if (!worldrenderer.field_249 || this.field_1807.contains(worldrenderer)) continue;
            this.field_1807.add(worldrenderer);
        }
        if (this.client.options.viewDistance != this.field_1782) {
            ((ClientChunkCache) this.level.cache).updateVeryFar();
            this.method_1537();
        }
        if (i == 0) {
            this.field_1787 = 0;
            this.field_1788 = 0;
            this.field_1789 = 0;
            this.field_1790 = 0;
            this.field_1791 = 0;
        }
        double d1 = entityliving.prevRenderX + (entityliving.x - entityliving.prevRenderX) * d;
        double d2 = entityliving.prevRenderY + (entityliving.y - entityliving.prevRenderY) * d;
        double d3 = entityliving.prevRenderZ + (entityliving.z - entityliving.prevRenderZ) * d;
        double d4 = entityliving.x - this.field_1800;
        double d5 = entityliving.y - this.field_1801;
        double d6 = entityliving.z - this.field_1802;
        if (d4 * d4 + d5 * d5 + d6 * d6 > 64.0) {
            this.field_1800 = entityliving.x;
            this.field_1801 = entityliving.y;
            this.field_1802 = entityliving.z;
            this.method_1553(MathsHelper.floor(entityliving.x), MathsHelper.floor(entityliving.y), MathsHelper.floor(entityliving.z));
            Arrays.sort((Object[]) this.field_1808, new ReverseEntityComparatorThing(entityliving));
        }
        RenderHelper.disableLighting();
        int k = 0;
        if (this.field_1817 && this.client.options.advancedOpengl && !this.client.options.anaglyph3d && i == 0) {
            int l = 0;
            int i1 = 16;
            this.method_1541(l, i1);
            for (int j1 = l; j1 < i1; ++j1) {
                this.field_1808[j1].field_252 = true;
            }
            k += this.method_1542(l, i1, i, d);
            float farPlane = this.client.gameRenderer.getFarPlane() * 1.25f;
            do {
                int byte0 = i1;
                if ((i1 *= 2) > this.field_1808.length) {
                    i1 = this.field_1808.length;
                }
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDisable(3008);
                GL11.glDisable(2912);
                GL11.glColorMask(false, false, false, false);
                GL11.glDepthMask(false);
                this.method_1541(byte0, i1);
                GL11.glPushMatrix();
                float f = 0.0f;
                float f1 = 0.0f;
                float f2 = 0.0f;
                for (int k1 = byte0; k1 < i1; ++k1) {
                    float f3;
                    int l1;
                    if (this.field_1808[k1].method_304()) {
                        this.field_1808[k1].field_243 = false;
                        continue;
                    }
                    if (!this.field_1808[k1].field_243) {
                        this.field_1808[k1].field_252 = true;
                    }
                    if (this.field_1808[k1].field_252) {
                        boolean bl = this.field_1808[k1].field_252 = this.field_1808[k1].method_299(entityliving) > farPlane;
                    }
                    if (!this.field_1808[k1].field_243 || this.field_1808[k1].field_253 || this.field_1818 % (l1 = (int) (1.0f + (f3 = MathsHelper.sqrt(this.field_1808[k1].method_299(entityliving))) / 128.0f)) != k1 % l1)
                        continue;
                    class_66 worldrenderer1 = this.field_1808[k1];
                    float f4 = (float) ((double) worldrenderer1.field_237 - d1);
                    float f5 = (float) ((double) worldrenderer1.field_238 - d2);
                    float f6 = (float) ((double) worldrenderer1.field_239 - d3);
                    float f7 = f4 - f;
                    float f8 = f5 - f1;
                    float f9 = f6 - f2;
                    if (f7 != 0.0f || f8 != 0.0f || f9 != 0.0f) {
                        GL11.glTranslatef(f7, f8, f9);
                        f += f7;
                        f1 += f8;
                        f2 += f9;
                    }
                    ARBOcclusionQuery.glBeginQueryARB(35092, this.field_1808[k1].field_254);
                    this.field_1808[k1].method_303();
                    ARBOcclusionQuery.glEndQueryARB(35092);
                    this.field_1808[k1].field_253 = true;
                }
                GL11.glPopMatrix();
                if (this.client.options.anaglyph3d) {
                    if (GameRenderer.field_2341 == 0) {
                        GL11.glColorMask(false, true, true, true);
                    } else {
                        GL11.glColorMask(true, false, false, true);
                    }
                } else {
                    GL11.glColorMask(true, true, true, true);
                }
                GL11.glDepthMask(true);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glEnable(2912);
                k += this.method_1542(byte0, i1, i, d);
            } while (i1 < this.field_1808.length);
        } else {
            k += this.method_1542(0, this.field_1808.length, i, d);
        }
        return k;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1541(int i, int j) {
        for (int k = i; k < j; ++k) {
            if (!this.field_1808[k].field_253) continue;
            this.field_1797.clear();
            ARBOcclusionQuery.glGetQueryObjectuARB(this.field_1808[k].field_254, 34919, this.field_1797);
            if (this.field_1797.get(0) == 0) continue;
            this.field_1808[k].field_253 = false;
            this.field_1797.clear();
            ARBOcclusionQuery.glGetQueryObjectuARB(this.field_1808[k].field_254, 34918, this.field_1797);
            this.field_1808[k].field_252 = this.field_1797.get(0) != 0;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int method_1542(int i, int j, int k, double d) {
        this.field_1793.clear();
        int l = 0;
        for (int i1 = i; i1 < j; ++i1) {
            int j1;
            if (k == 0) {
                ++this.field_1787;
                if (this.field_1808[i1].field_244[k]) {
                    ++this.field_1791;
                } else if (!this.field_1808[i1].field_243) {
                    ++this.field_1788;
                } else if (this.field_1817 && !this.field_1808[i1].field_252) {
                    ++this.field_1789;
                } else {
                    ++this.field_1790;
                }
            }
            if (this.field_1808[i1].field_244[k] || !this.field_1808[i1].field_243 || this.field_1817 && !this.field_1808[i1].field_252 || (j1 = this.field_1808[i1].method_297(k)) < 0)
                continue;
            this.field_1793.add(this.field_1808[i1]);
            ++l;
        }
        LivingEntity entityliving = this.client.field_2807;
        double d1 = entityliving.prevRenderX + (entityliving.x - entityliving.prevRenderX) * d;
        double d2 = entityliving.prevRenderY + (entityliving.y - entityliving.prevRenderY) * d;
        double d3 = entityliving.prevRenderZ + (entityliving.z - entityliving.prevRenderZ) * d;
        int k1 = 0;
        for (net.minecraft.class_472 class_472 : this.field_1794) {
            class_472.method_1913();
        }
        for (Object o : this.field_1793) {
            class_66 worldrenderer = (class_66) o;
            int j2 = -1;
            for (int k2 = 0; k2 < k1; ++k2) {
                if (!this.field_1794[k2].method_1911(worldrenderer.field_237, worldrenderer.field_238, worldrenderer.field_239))
                    continue;
                j2 = k2;
            }
            if (j2 < 0) {
                j2 = k1++;
                this.field_1794[j2].method_1912(worldrenderer.field_237, worldrenderer.field_238, worldrenderer.field_239, d1, d2, d3);
            }
            this.field_1794[j2].method_1910(worldrenderer.method_297(k));
        }
        this.method_1540(k, d);
        return l;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1540(int i, double d) {
        for (net.minecraft.class_472 class_472 : this.field_1794) {
            class_472.method_1909();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderSky(float f) {
        if (this.client.level.dimension.hasFog) {
            return;
        }
        GL11.glDisable(3553);
        Vec3f vec3d = this.level.method_279(this.client.field_2807, f);
        float f1 = (float) vec3d.x;
        float f2 = (float) vec3d.y;
        float f3 = (float) vec3d.z;
        if (this.client.options.anaglyph3d) {
            float f4 = (f1 * 30.0f + f2 * 59.0f + f3 * 11.0f) / 100.0f;
            float f5 = (f1 * 30.0f + f2 * 70.0f) / 100.0f;
            float f7 = (f1 * 30.0f + f3 * 70.0f) / 100.0f;
            f1 = f4;
            f2 = f5;
            f3 = f7;
        }
        GL11.glColor3f(f1, f2, f3);
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glDepthMask(false);
        GL11.glEnable(2912);
        GL11.glColor3f(f1, f2, f3);
        GL11.glCallList(this.field_1820);
        GL11.glDisable(2912);
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        RenderHelper.disableLighting();
        float[] af = this.level.dimension.method_1761(this.level.method_198(f), f);
        if (af != null) {
            GL11.glDisable(3553);
            GL11.glShadeModel(7425);
            GL11.glPushMatrix();
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            float f8 = this.level.method_198(f);
            GL11.glRotatef(f8 <= 0.5f ? 0.0f : 180.0f, 0.0f, 0.0f, 1.0f);
            float f10 = af[0];
            float f12 = af[1];
            float f14 = af[2];
            if (this.client.options.anaglyph3d) {
                float f16 = (f10 * 30.0f + f12 * 59.0f + f14 * 11.0f) / 100.0f;
                float f18 = (f10 * 30.0f + f12 * 70.0f) / 100.0f;
                float f19 = (f10 * 30.0f + f14 * 70.0f) / 100.0f;
                f10 = f16;
                f12 = f18;
                f14 = f19;
            }
            tessellator.start(6);
            tessellator.colour(f10, f12, f14, af[3]);
            tessellator.pos(0.0, 100.0, 0.0);
            int i = 16;
            tessellator.colour(af[0], af[1], af[2], 0.0f);
            for (int j = 0; j <= i; ++j) {
                float f20 = (float) j * 3.141593f * 2.0f / (float) i;
                float f21 = MathsHelper.sin(f20);
                float f22 = MathsHelper.cos(f20);
                tessellator.pos(f21 * 120.0f, f22 * 120.0f, -f22 * 40.0f * af[3]);
            }
            tessellator.draw();
            GL11.glPopMatrix();
            GL11.glShadeModel(7424);
        }
        GL11.glEnable(3553);
        GL11.glBlendFunc(770, 1);
        GL11.glPushMatrix();
        float f6 = 1.0f - this.level.getRainGradient(f);
        float f9 = 0.0f;
        float f11 = 0.0f;
        float f13 = 0.0f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, f6);
        GL11.glTranslatef(f9, f11, f13);
        GL11.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(this.level.method_198(f) * 360.0f, 1.0f, 0.0f, 0.0f);
        float f15 = 30.0f;
        GL11.glBindTexture(3553, this.textureManager.getTextureId("/terrain/sun.png"));
        tessellator.start();
        tessellator.vertex(-f15, 100.0, -f15, 0.0, 0.0);
        tessellator.vertex(f15, 100.0, -f15, 1.0, 0.0);
        tessellator.vertex(f15, 100.0, f15, 1.0, 1.0);
        tessellator.vertex(-f15, 100.0, f15, 0.0, 1.0);
        tessellator.draw();
        f15 = 20.0f;
        GL11.glBindTexture(3553, this.textureManager.getTextureId("/terrain/moon.png"));
        tessellator.start();
        tessellator.vertex(-f15, -100.0, f15, 1.0, 1.0);
        tessellator.vertex(f15, -100.0, f15, 0.0, 1.0);
        tessellator.vertex(f15, -100.0, -f15, 0.0, 0.0);
        tessellator.vertex(-f15, -100.0, -f15, 1.0, 0.0);
        tessellator.draw();
        GL11.glDisable(3553);
        float f17 = this.level.method_286(f) * f6;
        if (f17 > 0.0f) {
            GL11.glColor4f(f17, f17, f17, f17);
            GL11.glCallList(this.field_1819);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(2912);
        GL11.glPopMatrix();
        if (this.level.dimension.renderAsSkylands()) {
            GL11.glColor3f(f1 * 0.2f + 0.04f, f2 * 0.2f + 0.04f, f3 * 0.6f + 0.1f);
        } else {
            GL11.glColor3f(f1, f2, f3);
        }
        GL11.glDisable(3553);
        GL11.glCallList(this.field_1775);
        GL11.glEnable(3553);
        GL11.glDepthMask(true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1552(float f) {
        if (this.client.level.dimension.hasFog) {
            return;
        }
        if (this.client.options.fancyGraphics) {
            this.renderClouds(f);
            return;
        }
        GL11.glDisable(2884);
        float f1 = (float) (this.client.field_2807.prevRenderY + (this.client.field_2807.y - this.client.field_2807.prevRenderY) * (double) f);
        double d = this.client.field_2807.prevX + (this.client.field_2807.x - this.client.field_2807.prevX) * (double) f + (double) (((float) this.field_1818 + f) * 0.03f);
        double d1 = this.client.field_2807.prevZ + (this.client.field_2807.z - this.client.field_2807.prevZ) * (double) f;
        if (this.client.cameraActive) {
            CutsceneCameraPoint p = this.client.cutsceneCamera.getCurrentPoint(f);
            f1 = p.posY;
            d = (double) p.posX + (double) (((float) this.field_1818 + f) * 0.03f);
            d1 = p.posZ;
        }
        int byte0 = 32;
        int i = 256 / byte0;
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glBindTexture(3553, this.textureManager.getTextureId("/environment/clouds.png"));
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        Vec3f vec3d = this.level.method_282(f);
        float f2 = (float) vec3d.x;
        float f3 = (float) vec3d.y;
        float f4 = (float) vec3d.z;
        if (this.client.options.anaglyph3d) {
            float f5 = (f2 * 30.0f + f3 * 59.0f + f4 * 11.0f) / 100.0f;
            float f7 = (f2 * 30.0f + f3 * 70.0f) / 100.0f;
            float f8 = (f2 * 30.0f + f4 * 70.0f) / 100.0f;
            f2 = f5;
            f3 = f7;
            f4 = f8;
        }
        float f6 = 4.882813E-4f;
        int j = MathsHelper.floor(d / 2048.0);
        int k = MathsHelper.floor(d1 / 2048.0);
        float f9 = this.level.dimension.getCloudHeight() - f1 + 0.33f;
        float f10 = (float) ((d -= j * 2048) * (double) f6);
        float f11 = (float) ((d1 -= k * 2048) * (double) f6);
        tessellator.start();
        tessellator.colour(f2, f3, f4, 0.8f);
        for (int l = -byte0 * i; l < byte0 * i; l += byte0) {
            for (int i1 = -byte0 * i; i1 < byte0 * i; i1 += byte0) {
                tessellator.vertex(l + 0, f9, i1 + byte0, (float) (l + 0) * f6 + f10, (float) (i1 + byte0) * f6 + f11);
                tessellator.vertex(l + byte0, f9, i1 + byte0, (float) (l + byte0) * f6 + f10, (float) (i1 + byte0) * f6 + f11);
                tessellator.vertex(l + byte0, f9, i1 + 0, (float) (l + byte0) * f6 + f10, (float) (i1 + 0) * f6 + f11);
                tessellator.vertex(l + 0, f9, i1 + 0, (float) (l + 0) * f6 + f10, (float) (i1 + 0) * f6 + f11);
            }
        }
        tessellator.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderClouds(float f) {
        GL11.glDisable(2884);
        float f1 = (float) (this.client.field_2807.prevRenderY + (this.client.field_2807.y - this.client.field_2807.prevRenderY) * (double) f);
        Tessellator tessellator = Tessellator.INSTANCE;
        float f2 = 12.0f;
        float f3 = 4.0f;
        double d = (this.client.field_2807.prevX + (this.client.field_2807.x - this.client.field_2807.prevX) * (double) f + (double) (((float) this.field_1818 + f) * 0.03f)) / (double) f2;
        double d1 = (this.client.field_2807.prevZ + (this.client.field_2807.z - this.client.field_2807.prevZ) * (double) f) / (double) f2 + (double) 0.33f;
        float f4 = this.level.dimension.getCloudHeight() - f1 + 0.33f;
        int i = MathsHelper.floor(d / 2048.0);
        int j = MathsHelper.floor(d1 / 2048.0);
        d -= i * 2048;
        d1 -= j * 2048;
        GL11.glBindTexture(3553, this.textureManager.getTextureId("/environment/clouds.png"));
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        Vec3f vec3d = this.level.method_282(f);
        float f5 = (float) vec3d.x;
        float f6 = (float) vec3d.y;
        float f7 = (float) vec3d.z;
        if (this.client.options.anaglyph3d) {
            float f8 = (f5 * 30.0f + f6 * 59.0f + f7 * 11.0f) / 100.0f;
            float f10 = (f5 * 30.0f + f6 * 70.0f) / 100.0f;
            float f12 = (f5 * 30.0f + f7 * 70.0f) / 100.0f;
            f5 = f8;
            f6 = f10;
            f7 = f12;
        }
        float f9 = (float) (d * 0.0);
        float f11 = (float) (d1 * 0.0);
        float f13 = 0.00390625f;
        f9 = (float) MathsHelper.floor(d) * f13;
        f11 = (float) MathsHelper.floor(d1) * f13;
        float f14 = (float) (d - (double) MathsHelper.floor(d));
        float f15 = (float) (d1 - (double) MathsHelper.floor(d1));
        int k = 8;
        int byte0 = 3;
        float f16 = 9.765625E-4f;
        GL11.glScalef(f2, 1.0f, f2);
        for (int l = 0; l < 2; ++l) {
            if (l == 0) {
                GL11.glColorMask(false, false, false, false);
            } else if (this.client.options.anaglyph3d) {
                if (GameRenderer.field_2341 == 0) {
                    GL11.glColorMask(false, true, true, true);
                } else {
                    GL11.glColorMask(true, false, false, true);
                }
            } else {
                GL11.glColorMask(true, true, true, true);
            }
            for (int i1 = -byte0 + 1; i1 <= byte0; ++i1) {
                for (int j1 = -byte0 + 1; j1 <= byte0; ++j1) {
                    tessellator.start();
                    float f17 = i1 * k;
                    float f18 = j1 * k;
                    float f19 = f17 - f14;
                    float f20 = f18 - f15;
                    if (f4 > -f3 - 1.0f) {
                        tessellator.colour(f5 * 0.7f, f6 * 0.7f, f7 * 0.7f, 0.8f);
                        tessellator.method_1697(0.0f, -1.0f, 0.0f);
                        tessellator.vertex(f19 + 0.0f, f4 + 0.0f, f20 + (float) k, (f17 + 0.0f) * f13 + f9, (f18 + (float) k) * f13 + f11);
                        tessellator.vertex(f19 + (float) k, f4 + 0.0f, f20 + (float) k, (f17 + (float) k) * f13 + f9, (f18 + (float) k) * f13 + f11);
                        tessellator.vertex(f19 + (float) k, f4 + 0.0f, f20 + 0.0f, (f17 + (float) k) * f13 + f9, (f18 + 0.0f) * f13 + f11);
                        tessellator.vertex(f19 + 0.0f, f4 + 0.0f, f20 + 0.0f, (f17 + 0.0f) * f13 + f9, (f18 + 0.0f) * f13 + f11);
                    }
                    if (f4 <= f3 + 1.0f) {
                        tessellator.colour(f5, f6, f7, 0.8f);
                        tessellator.method_1697(0.0f, 1.0f, 0.0f);
                        tessellator.vertex(f19 + 0.0f, f4 + f3 - f16, f20 + (float) k, (f17 + 0.0f) * f13 + f9, (f18 + (float) k) * f13 + f11);
                        tessellator.vertex(f19 + (float) k, f4 + f3 - f16, f20 + (float) k, (f17 + (float) k) * f13 + f9, (f18 + (float) k) * f13 + f11);
                        tessellator.vertex(f19 + (float) k, f4 + f3 - f16, f20 + 0.0f, (f17 + (float) k) * f13 + f9, (f18 + 0.0f) * f13 + f11);
                        tessellator.vertex(f19 + 0.0f, f4 + f3 - f16, f20 + 0.0f, (f17 + 0.0f) * f13 + f9, (f18 + 0.0f) * f13 + f11);
                    }
                    tessellator.colour(f5 * 0.9f, f6 * 0.9f, f7 * 0.9f, 0.8f);
                    if (i1 > -1) {
                        tessellator.method_1697(-1.0f, 0.0f, 0.0f);
                        for (int k1 = 0; k1 < k; ++k1) {
                            tessellator.vertex(f19 + (float) k1 + 0.0f, f4 + 0.0f, f20 + (float) k, (f17 + (float) k1 + 0.5f) * f13 + f9, (f18 + (float) k) * f13 + f11);
                            tessellator.vertex(f19 + (float) k1 + 0.0f, f4 + f3, f20 + (float) k, (f17 + (float) k1 + 0.5f) * f13 + f9, (f18 + (float) k) * f13 + f11);
                            tessellator.vertex(f19 + (float) k1 + 0.0f, f4 + f3, f20 + 0.0f, (f17 + (float) k1 + 0.5f) * f13 + f9, (f18 + 0.0f) * f13 + f11);
                            tessellator.vertex(f19 + (float) k1 + 0.0f, f4 + 0.0f, f20 + 0.0f, (f17 + (float) k1 + 0.5f) * f13 + f9, (f18 + 0.0f) * f13 + f11);
                        }
                    }
                    if (i1 <= 1) {
                        tessellator.method_1697(1.0f, 0.0f, 0.0f);
                        for (int l1 = 0; l1 < k; ++l1) {
                            tessellator.vertex(f19 + (float) l1 + 1.0f - f16, f4 + 0.0f, f20 + (float) k, (f17 + (float) l1 + 0.5f) * f13 + f9, (f18 + (float) k) * f13 + f11);
                            tessellator.vertex(f19 + (float) l1 + 1.0f - f16, f4 + f3, f20 + (float) k, (f17 + (float) l1 + 0.5f) * f13 + f9, (f18 + (float) k) * f13 + f11);
                            tessellator.vertex(f19 + (float) l1 + 1.0f - f16, f4 + f3, f20 + 0.0f, (f17 + (float) l1 + 0.5f) * f13 + f9, (f18 + 0.0f) * f13 + f11);
                            tessellator.vertex(f19 + (float) l1 + 1.0f - f16, f4 + 0.0f, f20 + 0.0f, (f17 + (float) l1 + 0.5f) * f13 + f9, (f18 + 0.0f) * f13 + f11);
                        }
                    }
                    tessellator.colour(f5 * 0.8f, f6 * 0.8f, f7 * 0.8f, 0.8f);
                    if (j1 > -1) {
                        tessellator.method_1697(0.0f, 0.0f, -1.0f);
                        for (int i2 = 0; i2 < k; ++i2) {
                            tessellator.vertex(f19 + 0.0f, f4 + f3, f20 + (float) i2 + 0.0f, (f17 + 0.0f) * f13 + f9, (f18 + (float) i2 + 0.5f) * f13 + f11);
                            tessellator.vertex(f19 + (float) k, f4 + f3, f20 + (float) i2 + 0.0f, (f17 + (float) k) * f13 + f9, (f18 + (float) i2 + 0.5f) * f13 + f11);
                            tessellator.vertex(f19 + (float) k, f4 + 0.0f, f20 + (float) i2 + 0.0f, (f17 + (float) k) * f13 + f9, (f18 + (float) i2 + 0.5f) * f13 + f11);
                            tessellator.vertex(f19 + 0.0f, f4 + 0.0f, f20 + (float) i2 + 0.0f, (f17 + 0.0f) * f13 + f9, (f18 + (float) i2 + 0.5f) * f13 + f11);
                        }
                    }
                    if (j1 <= 1) {
                        tessellator.method_1697(0.0f, 0.0f, 1.0f);
                        for (int j2 = 0; j2 < k; ++j2) {
                            tessellator.vertex(f19 + 0.0f, f4 + f3, f20 + (float) j2 + 1.0f - f16, (f17 + 0.0f) * f13 + f9, (f18 + (float) j2 + 0.5f) * f13 + f11);
                            tessellator.vertex(f19 + (float) k, f4 + f3, f20 + (float) j2 + 1.0f - f16, (f17 + (float) k) * f13 + f9, (f18 + (float) j2 + 0.5f) * f13 + f11);
                            tessellator.vertex(f19 + (float) k, f4 + 0.0f, f20 + (float) j2 + 1.0f - f16, (f17 + (float) k) * f13 + f9, (f18 + (float) j2 + 0.5f) * f13 + f11);
                            tessellator.vertex(f19 + 0.0f, f4 + 0.0f, f20 + (float) j2 + 1.0f - f16, (f17 + 0.0f) * f13 + f9, (f18 + (float) j2 + 0.5f) * f13 + f11);
                        }
                    }
                    tessellator.draw();
                }
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1549(LivingEntity entityliving, boolean flag) {
        int j2;
        boolean flag1 = false;
        if (flag1) {
            Collections.sort(this.field_1807, new class_430(entityliving));
            int i = this.field_1807.size() - 1;
            int j = this.field_1807.size();
            for (int k = 0; k < j; ++k) {
                class_66 worldrenderer = (class_66) this.field_1807.get(i - k);
                if (!flag) {
                    if (worldrenderer.method_299(entityliving) > 256.0f && (worldrenderer.field_243 ? k >= 3 : k >= 1)) {
                        return false;
                    }
                } else if (!worldrenderer.field_243) continue;
                worldrenderer.method_296();
                this.field_1807.remove(worldrenderer);
                worldrenderer.field_249 = false;
            }
            return this.field_1807.size() == 0;
        }
        int byte0 = 2;
        class_430 rendersorter = new class_430(entityliving);
        class_66[] aworldrenderer = new class_66[byte0];
        ArrayList arraylist = null;
        int l = this.field_1807.size();
        int i1 = 0;
        long avgTime = 0L;
        if (PlayerTorch.isTorchActive()) {
            avgTime = AccessMinecraft.getInstance().getAvgFrameTime();
        }
        for (int j1 = 0; j1 < l; ++j1) {
            class_66 worldrenderer1 = (class_66) this.field_1807.get(j1);
            if (!flag) {
                if (worldrenderer1.method_299(entityliving) > 256.0f) {
                    int k2;
                    for (k2 = 0; k2 < byte0 && (aworldrenderer[k2] == null || rendersorter.method_1421(aworldrenderer[k2], worldrenderer1) <= 0); ++k2) {
                    }
                    if (--k2 <= 0) continue;
                    int i3 = k2;
                    while (--i3 != 0) {
                        aworldrenderer[i3 - 1] = aworldrenderer[i3];
                    }
                    aworldrenderer[k2] = worldrenderer1;
                    continue;
                }
            } else if (!worldrenderer1.field_243) continue;
            if (arraylist == null) {
                arraylist = new ArrayList();
            }
            arraylist.add(worldrenderer1);
            this.field_1807.set(j1, null);
            if (PlayerTorch.isTorchActive() && (++i1 >= 3 || avgTime > 40000000L || i1 >= 2 && avgTime > 16666666L))
                break;
        }
        if (arraylist != null) {
            if (arraylist.size() > 1) {
                Collections.sort(arraylist, rendersorter);
            }
            for (int k1 = arraylist.size() - 1; k1 >= 0; --k1) {
                class_66 worldrenderer2 = (class_66) arraylist.get(k1);
                worldrenderer2.method_296();
                worldrenderer2.field_249 = false;
            }
        }
        int l1 = 0;
        for (int i2 = byte0 - 1; i2 >= 0; --i2) {
            class_66 worldrenderer3 = aworldrenderer[i2];
            if (worldrenderer3 == null) continue;
            if (!worldrenderer3.field_243 && i2 != byte0 - 1) {
                aworldrenderer[i2] = null;
                aworldrenderer[0] = null;
                break;
            }
            aworldrenderer[i2].method_296();
            aworldrenderer[i2].field_249 = false;
            ++l1;
        }
        int l2 = 0;
        int j3 = this.field_1807.size();
        for (j2 = 0; j2 != j3; ++j2) {
            class_66 worldrenderer4 = (class_66) this.field_1807.get(j2);
            if (worldrenderer4 == null) continue;
            boolean flag2 = false;
            for (int k3 = 0; k3 < byte0 && !flag2; ++k3) {
                if (worldrenderer4 != aworldrenderer[k3]) continue;
                flag2 = true;
            }
            if (flag2) continue;
            if (l2 != j2) {
                this.field_1807.set(l2, worldrenderer4);
            }
            ++l2;
        }
        while (--j2 >= l2) {
            this.field_1807.remove(j2);
        }
        return l == i1 + l1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1547(Player player, HitResult movingobjectposition, int i, ItemInstance itemstack, float f) {
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glEnable(3042);
        GL11.glEnable(3008);
        GL11.glBlendFunc(770, 1);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, (MathsHelper.sin((float) System.currentTimeMillis() / 100.0f) * 0.2f + 0.4f) * 0.5f);
        if (i == 0) {
            if (this.field_1803 > 0.0f) {
                GL11.glBlendFunc(774, 768);
                int j = this.textureManager.getTextureId("/terrain.png");
                GL11.glBindTexture(3553, j);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
                GL11.glPushMatrix();
                int k = this.level.getTileId(movingobjectposition.x, movingobjectposition.y, movingobjectposition.z);
                Tile block = k <= 0 ? null : Tile.BY_ID[k];
                GL11.glDisable(3008);
                GL11.glPolygonOffset(-3.0f, -3.0f);
                GL11.glEnable(32823);
                double d = player.prevRenderX + (player.x - player.prevRenderX) * (double) f;
                double d1 = player.prevRenderY + (player.y - player.prevRenderY) * (double) f;
                double d2 = player.prevRenderZ + (player.z - player.prevRenderZ) * (double) f;
                if (block == null) {
                    block = Tile.STONE;
                }
                GL11.glEnable(3008);
                tessellator.start();
                tessellator.prevPos(-d, -d1, -d2);
                tessellator.method_1699();
                this.tileRenderer.method_51(block, movingobjectposition.x, movingobjectposition.y, movingobjectposition.z, 240 + (int) (this.field_1803 * 10.0f));
                tessellator.draw();
                tessellator.prevPos(0.0, 0.0, 0.0);
                GL11.glDisable(3008);
                GL11.glPolygonOffset(0.0f, 0.0f);
                GL11.glDisable(32823);
                GL11.glEnable(3008);
                GL11.glDepthMask(true);
                GL11.glPopMatrix();
            }
        } else if (itemstack != null) {
            GL11.glBlendFunc(770, 771);
            float f1 = MathsHelper.sin((float) System.currentTimeMillis() / 100.0f) * 0.2f + 0.8f;
            GL11.glColor4f(f1, f1, f1, MathsHelper.sin((float) System.currentTimeMillis() / 200.0f) * 0.2f + 0.5f);
            int l = this.textureManager.getTextureId("/terrain.png");
            GL11.glBindTexture(3553, l);
            int i1 = movingobjectposition.x;
            int j1 = movingobjectposition.y;
            int k1 = movingobjectposition.z;
            if (movingobjectposition.field_1987 == 0) {
                --j1;
            }
            if (movingobjectposition.field_1987 == 1) {
                ++j1;
            }
            if (movingobjectposition.field_1987 == 2) {
                --k1;
            }
            if (movingobjectposition.field_1987 == 3) {
                ++k1;
            }
            if (movingobjectposition.field_1987 == 4) {
                --i1;
            }
            if (movingobjectposition.field_1987 == 5) {
                ++i1;
            }
        }
        GL11.glDisable(3042);
        GL11.glDisable(3008);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1554(Player entityplayer, HitResult movingobjectposition, int i, ItemInstance itemstack, float f) {
        if (i == 0 && movingobjectposition.type == HitType.TILE) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
            GL11.glLineWidth(2.0f);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            float f1 = 0.002f;
            int j = this.level.getTileId(movingobjectposition.x, movingobjectposition.y, movingobjectposition.z);
            if (j > 0) {
                Tile.BY_ID[j].method_1616(this.level, movingobjectposition.x, movingobjectposition.y, movingobjectposition.z);
                double d = entityplayer.prevRenderX + (entityplayer.x - entityplayer.prevRenderX) * (double) f;
                double d1 = entityplayer.prevRenderY + (entityplayer.y - entityplayer.prevRenderY) * (double) f;
                double d2 = entityplayer.prevRenderZ + (entityplayer.z - entityplayer.prevRenderZ) * (double) f;
                this.method_1545(Tile.BY_ID[j].getOutlineShape(this.level, movingobjectposition.x, movingobjectposition.y, movingobjectposition.z).expand(f1, f1, f1).move(-d, -d1, -d2));
            }
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void drawCursorSelection(LivingEntity entityplayer, ItemInstance itemstack, float f) {
        if (ItemCursor.bothSet && itemstack != null && itemstack.itemId >= Items.cursor.id && itemstack.itemId <= Items.cursor.id + 20) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0f, 0.6f, 0.0f, 0.4f);
            GL11.glLineWidth(3.0f);
            GL11.glDisable(3553);
            int minX = Math.min(ItemCursor.oneX, ItemCursor.twoX);
            int maxX = Math.max(ItemCursor.oneX, ItemCursor.twoX) + 1;
            int minY = Math.min(ItemCursor.oneY, ItemCursor.twoY);
            int maxY = Math.max(ItemCursor.oneY, ItemCursor.twoY) + 1;
            int minZ = Math.min(ItemCursor.oneZ, ItemCursor.twoZ);
            int maxZ = Math.max(ItemCursor.oneZ, ItemCursor.twoZ) + 1;
            double offX = entityplayer.prevRenderX + (entityplayer.x - entityplayer.prevRenderX) * (double) f;
            double offY = entityplayer.prevRenderY + (entityplayer.y - entityplayer.prevRenderY) * (double) f;
            double offZ = entityplayer.prevRenderZ + (entityplayer.z - entityplayer.prevRenderZ) * (double) f;
            Tessellator tessellator = Tessellator.INSTANCE;
            for (int x = minX; x <= maxX; ++x) {
                tessellator.start(3);
                tessellator.pos((double) x - offX, (double) minY - offY, (double) minZ - offZ);
                tessellator.pos((double) x - offX, (double) maxY - offY, (double) minZ - offZ);
                tessellator.pos((double) x - offX, (double) maxY - offY, (double) maxZ - offZ);
                tessellator.pos((double) x - offX, (double) minY - offY, (double) maxZ - offZ);
                tessellator.pos((double) x - offX, (double) minY - offY, (double) minZ - offZ);
                tessellator.draw();
            }
            for (int y = minY; y <= maxY; ++y) {
                tessellator.start(3);
                tessellator.pos((double) minX - offX, (double) y - offY, (double) minZ - offZ);
                tessellator.pos((double) maxX - offX, (double) y - offY, (double) minZ - offZ);
                tessellator.pos((double) maxX - offX, (double) y - offY, (double) maxZ - offZ);
                tessellator.pos((double) minX - offX, (double) y - offY, (double) maxZ - offZ);
                tessellator.pos((double) minX - offX, (double) y - offY, (double) minZ - offZ);
                tessellator.draw();
            }
            for (int z = minZ; z <= maxZ; ++z) {
                tessellator.start(3);
                tessellator.pos((double) minX - offX, (double) minY - offY, (double) z - offZ);
                tessellator.pos((double) maxX - offX, (double) minY - offY, (double) z - offZ);
                tessellator.pos((double) maxX - offX, (double) maxY - offY, (double) z - offZ);
                tessellator.pos((double) minX - offX, (double) maxY - offY, (double) z - offZ);
                tessellator.pos((double) minX - offX, (double) minY - offY, (double) z - offZ);
                tessellator.draw();
            }
            GL11.glLineWidth(1.0f);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void drawEntityPath(Entity e, LivingEntity entityplayer, float f) {
        if (e instanceof IEntityPather) {
            IEntityPather ent = (IEntityPather) e;
            class_61 path = ent.getCurrentPath();
            double offX = entityplayer.prevRenderX + (entityplayer.x - entityplayer.prevRenderX) * (double) f;
            double offY = entityplayer.prevRenderY + (entityplayer.y - entityplayer.prevRenderY) * (double) f;
            double offZ = entityplayer.prevRenderZ + (entityplayer.z - entityplayer.prevRenderZ) * (double) f;
            if (path != null) {
                Tessellator tessellator = Tessellator.INSTANCE;
                tessellator.start(3);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                if (e instanceof WalkingEntity && ((WalkingEntity) e).method_634() != null) {
                    GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.4f);
                } else {
                    GL11.glColor4f(1.0f, 1.0f, 0.0f, 0.4f);
                }
                GL11.glLineWidth(5.0f);
                GL11.glDisable(3553);
                tessellator.pos(e.x - offX, e.y - offY, e.z - offZ);
                for (int i = path.field_2692; i < path.field_2690; ++i) {
                    Vec3i p = path.field_2691[i];
                    tessellator.pos((double) p.x - offX + 0.5, (double) p.y - offY + 0.5, (double) p.z - offZ + 0.5);
                }
                tessellator.draw();
                GL11.glLineWidth(1.0f);
                GL11.glEnable(3553);
                GL11.glDisable(3042);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void drawEntityFOV(LivingEntity e, LivingEntity entityplayer, float f) {
        if (e == entityplayer) {
            return;
        }
        double offX = entityplayer.prevRenderX + (entityplayer.x - entityplayer.prevRenderX) * (double) f;
        double offY = entityplayer.prevRenderY + (entityplayer.y - entityplayer.prevRenderY) * (double) f;
        double offZ = entityplayer.prevRenderZ + (entityplayer.z - entityplayer.prevRenderZ) * (double) f;
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start(3);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        if (e.extraFov > 0.0f) {
            GL11.glColor4f(1.0f, 0.5f, 0.0f, 0.4f);
        } else {
            GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.4f);
        }
        GL11.glLineWidth(5.0f);
        GL11.glDisable(3553);
        float fov = Math.min((float) (e.fov / 2.0f + e.extraFov), 180.0f);
        double xFov = 5.0 * Math.sin(-Math.PI * (double) (e.yaw - fov) / 180.0) + e.x;
        double zFov = 5.0 * Math.cos(-Math.PI * (double) (e.yaw - fov) / 180.0) + e.z;
        tessellator.pos(xFov - offX, e.y - offY + (double) e.getStandingEyeHeight(), zFov - offZ);
        tessellator.pos(e.x - offX, e.y - offY + (double) e.getStandingEyeHeight(), e.z - offZ);
        xFov = 5.0 * Math.sin(-Math.PI * (double) (e.yaw + fov) / 180.0) + e.x;
        zFov = 5.0 * Math.cos(-Math.PI * (double) (e.yaw + fov) / 180.0) + e.z;
        tessellator.pos(xFov - offX, e.y - offY + (double) e.getStandingEyeHeight(), zFov - offZ);
        tessellator.draw();
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1545(Box axisalignedbb) {
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start(3);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.start(3);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.start(1);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.draw();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1543(int i, int j, int k, int l, int i1, int j1) {
        int k1 = MathsHelper.method_650(i, 16);
        int l1 = MathsHelper.method_650(j, 16);
        int i2 = MathsHelper.method_650(k, 16);
        int j2 = MathsHelper.method_650(l, 16);
        int k2 = MathsHelper.method_650(i1, 16);
        int l2 = MathsHelper.method_650(j1, 16);
        for (int i3 = k1; i3 <= j2; ++i3) {
            int j3 = i3 % this.field_1810;
            if (j3 < 0) {
                j3 += this.field_1810;
            }
            for (int k3 = l1; k3 <= k2; ++k3) {
                int l3 = k3 % this.field_1811;
                if (l3 < 0) {
                    l3 += this.field_1811;
                }
                for (int i4 = i2; i4 <= l2; ++i4) {
                    int j4 = i4 % this.field_1812;
                    if (j4 < 0) {
                        j4 += this.field_1812;
                    }
                    int k4 = (j4 * this.field_1811 + l3) * this.field_1810 + j3;
                    class_66 worldrenderer = this.field_1809[k4];
                    if (worldrenderer.field_249) continue;
                    this.field_1807.add(worldrenderer);
                    worldrenderer.method_305();
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1149(int i, int j, int k) {
        this.method_1543(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1150(int i, int j, int k, int l, int i1, int j1) {
        this.method_1543(i - 1, j - 1, k - 1, l + 1, i1 + 1, j1 + 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1550(class_68 icamera, float f) {
        for (int i = 0; i < this.field_1809.length; ++i) {
            if (this.field_1809[i].method_304() || this.field_1809[i].field_243 && (i + this.field_1804 & 0xF) != 0)
                continue;
            this.field_1809[i].method_300(icamera);
        }
        ++this.field_1804;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1155(String s, int i, int j, int k) {
        if (s != null) {
            this.client.overlay.method_1952("C418 - " + s);
        }
        this.client.soundHelper.method_2010(s, i, j, k, 1.0f, 1.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void playSound(String sound, double x, double y, double z, float f, float f1) {
        float f2 = 16.0f;
        if (f > 1.0f) {
            f2 *= f;
        }
        if (this.client.field_2807.squaredDistanceTo(x, y, z) < (double) (f2 * f2)) {
            this.client.soundHelper.playSound(sound, (float) x, (float) y, (float) z, f, f1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void addParticle(String particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this.spawnParticleR(particle, x, y, z, velocityX, velocityY, velocityZ);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Particle spawnParticleR(String s, double d, double d1, double d2, double d3, double d4, double d5) {
        if (this.client == null || this.client.field_2807 == null || this.client.particleManager == null) {
            return null;
        }
        double d6 = this.client.field_2807.x - d;
        double d7 = this.client.field_2807.y - d1;
        double d8 = this.client.field_2807.z - d2;
        double d9 = 16384.0;
        if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9) {
            return null;
        }
        Particle particle = null;
        switch (s) {
            case "bubble":
                particle = new BubbleParticle(this.level, d, d1, d2, d3, d4, d5);
                break;
            case "smoke":
                particle = new SmokeParticle(this.level, d, d1, d2, d3, d4, d5);
                break;
            case "note":
                particle = new NoteParticle(this.level, d, d1, d2, d3, d4, d5);
                break;
            case "portal":
                particle = new PortalParticle(this.level, d, d1, d2, d3, d4, d5);
                break;
            case "explode":
                particle = new ExplodeParticle(this.level, d, d1, d2, d3, d4, d5);
                break;
            case "flame":
                particle = new FlameParticle(this.level, d, d1, d2, d3, d4, d5);
                break;
            case "lava":
                particle = new LavaParticle(this.level, d, d1, d2);
                break;
            case "footstep":
                particle = new FootstepParticle(this.textureManager, this.level, d, d1, d2);
                break;
            case "splash":
                particle = new SplashParticle(this.level, d, d1, d2, d3, d4, d5);
                break;
            case "largesmoke":
                particle = new SmokeParticle(this.level, d, d1, d2, d3, d4, d5, 2.5f);
                break;
            case "reddust":
                particle = new RedDustParticle(this.level, d, d1, d2, (float) d3, (float) d4, (float) d5);
                break;
            case "snowballpoof":
                particle = new PoofParticle(this.level, d, d1, d2, ItemType.snowball);
                break;
            case "snowshovel":
                particle = new SnowshovelParticle(this.level, d, d1, d2, d3, d4, d5);
                break;
            case "slime":
                particle = new PoofParticle(this.level, d, d1, d2, ItemType.slimeball);
                break;
            case "heart":
                particle = new HeartParticle(this.level, d, d1, d2, d3, d4, d5);
                break;
        }
        if (particle != null) {
            this.client.particleManager.addParticle(particle);
        }
        return particle;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void updateAllTheRenderers() {
        for (net.minecraft.class_66 class_66 : this.field_1809) {
            if (!class_66.field_249) {
                this.field_1807.add(class_66);
            }
            class_66.method_305();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void playLevelEvent(Player entityplayer, int i, int j, int k, int l, int i1) {
        Random random = this.level.rand;
        switch (i) {
            default: {
                break;
            }
            case 1001: {
                this.level.playSound(j, k, l, "random.click", 1.0f, 1.2f);
                break;
            }
            case 1000: {
                this.level.playSound(j, k, l, "random.click", 1.0f, 1.0f);
                break;
            }
            case 1002: {
                this.level.playSound(j, k, l, "random.bow", 1.0f, 1.2f);
                break;
            }
            case 2000: {
                int j1 = i1 % 3 - 1;
                int k1 = i1 / 3 % 3 - 1;
                double d = (double) j + (double) j1 * 0.6 + 0.5;
                double d1 = (double) k + 0.5;
                double d2 = (double) l + (double) k1 * 0.6 + 0.5;
                for (int l1 = 0; l1 < 10; ++l1) {
                    double d3 = random.nextDouble() * 0.2 + 0.01;
                    double d4 = d + (double) j1 * 0.01 + (random.nextDouble() - 0.5) * (double) k1 * 0.5;
                    double d5 = d1 + (random.nextDouble() - 0.5) * 0.5;
                    double d6 = d2 + (double) k1 * 0.01 + (random.nextDouble() - 0.5) * (double) j1 * 0.5;
                    double d7 = (double) j1 * d3 + random.nextGaussian() * 0.01;
                    double d8 = -0.03 + random.nextGaussian() * 0.01;
                    double d9 = (double) k1 * d3 + random.nextGaussian() * 0.01;
                    this.addParticle("smoke", d4, d5, d6, d7, d8, d9);
                }
                break;
            }
            case 2001: {
                int i2 = i1 & 0xFF;
                if (i2 > 0) {
                    Tile block = Tile.BY_ID[i2];
                    this.client.soundHelper.playSound(block.sounds.getBreakSound(), (float) j + 0.5f, (float) k + 0.5f, (float) l + 0.5f, (block.sounds.getVolume() + 1.0f) / 2.0f, block.sounds.getPitch() * 0.8f);
                }
                this.client.particleManager.addTileBreakParticles(j, k, l, i1 & 0xFF, i1 >> 8 & 0xFF);
                break;
            }
            case 1003: {
                if (Math.random() < 0.5) {
                    this.level.playSound((double) j + 0.5, (double) k + 0.5, (double) l + 0.5, "random.door_open", 1.0f, this.level.rand.nextFloat() * 0.1f + 0.9f);
                    break;
                }
                this.level.playSound((double) j + 0.5, (double) k + 0.5, (double) l + 0.5, "random.door_close", 1.0f, this.level.rand.nextFloat() * 0.1f + 0.9f);
                break;
            }
            case 1004: {
                this.level.playSound((float) j + 0.5f, (float) k + 0.5f, (float) l + 0.5f, "random.fizz", 0.5f, 2.6f + (random.nextFloat() - random.nextFloat()) * 0.8f);
                break;
            }
            case 1005: {
                if (ItemType.byId[i1] instanceof RecordItem) {
                    this.level.method_179(((RecordItem) ItemType.byId[i1]).record, j, k, l);
                    break;
                }
                this.level.method_179(null, j, k, l);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void resetAll() {
        this.doReset(false);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void resetForDeath() {
        this.doReset(true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void doReset(boolean forDeath) {
        Tile.resetActive = true;
        for (net.minecraft.class_66 class_66 : this.field_1809) {
            int xOffset = class_66.field_231;
            int yOffset = class_66.field_232;
            int zOffset = class_66.field_233;
            if (!this.level.isRegionLoaded(xOffset, yOffset, zOffset, xOffset + 15, yOffset + 15, zOffset + 15))
                continue;
            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 16; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        int blockID = this.level.getTileId(xOffset + x, yOffset + y, zOffset + z);
                        if (blockID <= 0) continue;
                        Tile.BY_ID[blockID].reset(this.level, xOffset + x, yOffset + y, zOffset + z, forDeath);
                    }
                }
            }
        }
        Tile.resetActive = false;
    }
}
