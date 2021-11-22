package io.github.ryuu.adventurecraft.mixin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.util.LightCache;
import io.github.ryuu.adventurecraft.util.CoordBlock;

@Mixin(Class_66.class)
public class MixinClass_66 {

    @Shadow()
    public Level level;

    private int field_225 = -1;

    private static Tessellator tesselator;

    public static int field_230;

    public int field_231;

    public int field_232;

    public int field_233;

    public int field_234;

    public int field_235;

    public int field_236;

    public int field_237;

    public int field_238;

    public int field_239;

    public int field_240;

    public int field_241;

    public int field_242;

    public boolean field_243 = false;

    public boolean[] field_244 = new boolean[2];

    public int field_245;

    public int field_246;

    public int field_247;

    public float field_248;

    public boolean field_249;

    public Box field_250;

    public int field_251;

    public boolean field_252 = true;

    public boolean field_253;

    public int field_254;

    public boolean field_223;

    private boolean field_227 = false;

    public List field_224 = new ArrayList();

    private List field_228;

    public MixinClass_66(Level world, List list, int i, int j, int k, int l, int i1) {
        this.level = world;
        this.field_228 = list;
        this.field_235 = this.field_236 = l;
        this.field_234 = this.field_236;
        this.field_248 = MathsHelper.sqrt(this.field_234 * this.field_234 + this.field_235 * this.field_235 + this.field_236 * this.field_236) / 2.0f;
        this.field_225 = i1;
        this.field_231 = -999;
        this.method_298(i, j, k);
        this.field_249 = false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_298(int i, int j, int k) {
        if (i == this.field_231 && j == this.field_232 && k == this.field_233) {
            return;
        }
        this.method_301();
        this.field_231 = i;
        this.field_232 = j;
        this.field_233 = k;
        this.field_245 = i + this.field_234 / 2;
        this.field_246 = j + this.field_235 / 2;
        this.field_247 = k + this.field_236 / 2;
        this.field_240 = i & 0x3FF;
        this.field_241 = j;
        this.field_242 = k & 0x3FF;
        this.field_237 = i - this.field_240;
        this.field_238 = j - this.field_241;
        this.field_239 = k - this.field_242;
        float f = 6.0f;
        this.field_250 = Box.create((float) i - f, (float) j - f, (float) k - f, (float) (i + this.field_234) + f, (float) (j + this.field_235) + f, (float) (k + this.field_236) + f);
        GL11.glNewList((int) (this.field_225 + 2), (int) 4864);
        ItemRenderer.method_2024(Box.getOrCreate((float) this.field_240 - f, (float) this.field_241 - f, (float) this.field_242 - f, (float) (this.field_240 + this.field_234) + f, (float) (this.field_241 + this.field_235) + f, (float) (this.field_242 + this.field_236) + f));
        GL11.glEndList();
        this.method_305();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
        HashSet hashset = new HashSet();
        hashset.addAll((Collection) this.field_224);
        this.field_224.clear();
        int l1 = 1;
        WorldPopulationRegion chunkcache = new WorldPopulationRegion(this.level, i - l1, j - l1, k - l1, l + l1, i1 + l1, j1 + l1);
        TileRenderer renderblocks = new TileRenderer(chunkcache);
        for (int i2 = 0; i2 < 2; ++i2) {
            boolean flag = false;
            boolean flag1 = false;
            boolean flag2 = false;
            for (int texNum = 0; texNum <= 3; ++texNum) {
                if (texNum == 1)
                    continue;
                boolean startedDrawing = false;
                for (int j2 = j; j2 < i1; ++j2) {
                    for (int k2 = k; k2 < j1; ++k2) {
                        for (int l2 = i; l2 < l; ++l2) {
                            Tile block;
                            int j3;
                            TileEntity tileentity;
                            int i3 = chunkcache.getTileId(l2, j2, k2);
                            if (i3 <= 0 || texNum != Tile.BY_ID[i3].getTextureNum())
                                continue;
                            if (!flag2) {
                                flag2 = true;
                                GL11.glNewList((int) (this.field_225 + i2), (int) 4864);
                                GL11.glPushMatrix();
                                this.method_306();
                                float f = 1.000001f;
                                GL11.glTranslatef((float) ((float) (-this.field_236) / 2.0f), (float) ((float) (-this.field_235) / 2.0f), (float) ((float) (-this.field_236) / 2.0f));
                                GL11.glScalef((float) f, (float) f, (float) f);
                                GL11.glTranslatef((float) ((float) this.field_236 / 2.0f), (float) ((float) this.field_235 / 2.0f), (float) ((float) this.field_236 / 2.0f));
                            }
                            if (!startedDrawing) {
                                startedDrawing = true;
                                if (texNum == 0) {
                                    GL11.glBindTexture((int) 3553, (int) Minecraft.minecraftInstance.textureManager.getTextureId("/terrain.png"));
                                } else {
                                    GL11.glBindTexture((int) 3553, (int) Minecraft.minecraftInstance.textureManager.getTextureId(String.format((String) "/terrain%d.png", (Object[]) new Object[] { texNum })));
                                }
                                tesselator.start();
                                tesselator.prevPos(-this.field_231, -this.field_232, -this.field_233);
                            }
                            if (i2 == 0 && Tile.HAS_TILE_ENTITY[i3] && TileEntityRenderDispatcher.INSTANCE.hasRenderer(tileentity = chunkcache.getTileEntity(l2, j2, k2))) {
                                this.field_224.add((Object) tileentity);
                            }
                            if ((j3 = (block = Tile.BY_ID[i3]).method_1619()) != i2) {
                                flag = true;
                                continue;
                            }
                            if (j3 != i2)
                                continue;
                            flag1 |= renderblocks.method_57(block, l2, j2, k2);
                        }
                    }
                    if (!startedDrawing)
                        continue;
                    tesselator.draw();
                    startedDrawing = false;
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
            if (!flag)
                break;
        }
        HashSet hashset1 = new HashSet();
        hashset1.addAll((Collection) this.field_224);
        hashset1.removeAll((Collection) hashset);
        this.field_228.addAll((Collection) hashset1);
        hashset.removeAll((Collection) this.field_224);
        this.field_228.removeAll((Collection) hashset);
        this.field_223 = Chunk.field_953;
        this.field_227 = true;
        LightCache.cache.clear();
        CoordBlock.resetPool();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_299(Entity entity) {
        float f = (float) (entity.x - (double) this.field_245);
        float f1 = (float) (entity.y - (double) this.field_246);
        float f2 = (float) (entity.z - (double) this.field_247);
        return f * f + f1 * f1 + f2 * f2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_297(int i) {
        if (!this.field_243) {
            return -1;
        }
        if (!this.field_244[i]) {
            return this.field_225 + i;
        }
        return -1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_300(class_68 icamera) {
        this.field_243 = icamera.method_2007(this.field_250);
    }

    static {
        field_230 = 0;
        tesselator = Tessellator.INSTANCE;
    }
}
