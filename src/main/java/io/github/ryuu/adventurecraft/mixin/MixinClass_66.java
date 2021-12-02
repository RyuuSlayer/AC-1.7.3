package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.CoordBlock;
import io.github.ryuu.adventurecraft.util.LightCache;
import net.minecraft.class_66;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.tile.TileEntityRenderDispatcher;
import net.minecraft.level.Level;
import net.minecraft.level.WorldPopulationRegion;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashSet;
import java.util.List;

@Mixin(class_66.class)
public abstract class MixinClass_66 {

    @Shadow
    private static Tessellator tesselator;

    @Shadow
    public static int field_230;

    @Shadow
    private List<TileEntity> field_228;

    @Shadow
    public Level level;

    @Shadow
    public int field_231;

    @Shadow
    public int field_232;

    @Shadow
    public int field_233;

    @Shadow
    public int field_234;

    @Shadow
    public int field_235;

    @Shadow
    public int field_236;

    @Shadow
    public boolean[] field_244;

    @Shadow
    public boolean field_249;

    @Shadow
    public boolean field_223;

    @Shadow
    public List<TileEntity> field_224;

    @Shadow
    private int field_225;

    @Shadow
    private boolean field_227;

    @Shadow
    protected abstract void method_306();

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_296() {
        if (!this.field_249) {
            return;
        }
        ++field_230;
        int i = this.field_231;
        int j = this.field_232;
        int k = this.field_233;
        int l = this.field_231 + this.field_234;
        int i1 = this.field_232 + this.field_235;
        int j1 = this.field_233 + this.field_236;
        for (int k1 = 0; k1 < 2; ++k1) {
            this.field_244[k1] = true;
        }
        Chunk.field_953 = false;
        HashSet<TileEntity> hashset = new HashSet<>(this.field_224);
        this.field_224.clear();
        int l1 = 1;
        WorldPopulationRegion chunkcache = new WorldPopulationRegion(this.level, i - l1, j - l1, k - l1, l + l1, i1 + l1, j1 + l1);
        TileRenderer renderblocks = new TileRenderer(chunkcache);
        for (int i2 = 0; i2 < 2; ++i2) {
            boolean flag = false;
            boolean flag1 = false;
            boolean flag2 = false;
            for (int texNum = 1; texNum <= 3; ++texNum) {
                boolean startedDrawing = false;
                for (int j2 = j; j2 < i1; ++j2) {
                    for (int k2 = k; k2 < j1; ++k2) {
                        for (int l2 = i; l2 < l; ++l2) {
                            int i3 = chunkcache.getTileId(l2, j2, k2);
                            if (i3 > 0 && texNum == ((ExTile) Tile.BY_ID[i3]).getTextureNum()) {
                                if (!flag2) {
                                    flag2 = true;
                                    GL11.glNewList(this.field_225 + i2, 4864);
                                    GL11.glPushMatrix();
                                    this.method_306();
                                    float f = 1.000001f;
                                    GL11.glTranslatef((float) (-this.field_236) / 2.0f, (float) (-this.field_235) / 2.0f, (float) (-this.field_236) / 2.0f);
                                    GL11.glScalef(f, f, f);
                                    GL11.glTranslatef((float) this.field_236 / 2.0f, (float) this.field_235 / 2.0f, (float) this.field_236 / 2.0f);
                                }
                                if (!startedDrawing) {
                                    startedDrawing = true;
                                    if (texNum == 0) {
                                        GL11.glBindTexture(3553, AccessMinecraft.getInstance().textureManager.getTextureId("/terrain.png"));
                                    } else {
                                        GL11.glBindTexture(3553, AccessMinecraft.getInstance().textureManager.getTextureId(String.format("/terrain%d.png", texNum)));
                                    }
                                    tesselator.start();
                                    tesselator.prevPos(-this.field_231, -this.field_232, -this.field_233);
                                }

                                if (i2 == 0 && Tile.HAS_TILE_ENTITY[i3]) {
                                    TileEntity tileentity = chunkcache.getTileEntity(l2, j2, k2);
                                    if (TileEntityRenderDispatcher.INSTANCE.hasRenderer(tileentity)) {
                                        this.field_224.add(tileentity);
                                    }
                                }

                                Tile block = Tile.BY_ID[i3];
                                int j3 = block.method_1619();
                                if (j3 != i2) {
                                    flag = true;
                                    continue;
                                }
                                flag1 |= renderblocks.method_57(block, l2, j2, k2);
                            }
                        }
                    }
                    if (startedDrawing) {
                        tesselator.draw();
                        startedDrawing = false;
                    }
                }
            }
            if (flag2) {
                GL11.glPopMatrix();
                GL11.glEndList();
                tesselator.prevPos(0.0, 0.0, 0.0);
            } else {
                flag1 = false;
            }
            if (flag1) {
                this.field_244[i2] = false;
            }
            if (!flag) break;
        }
        HashSet<TileEntity> hashset1 = new HashSet<>(this.field_224);
        hashset1.removeAll(hashset);
        this.field_228.addAll(hashset1);
        hashset.removeAll(this.field_224);
        this.field_228.removeAll(hashset);
        this.field_223 = Chunk.field_953;
        this.field_227 = true;
        LightCache.cache.clear();
        CoordBlock.resetPool();
    }
}
