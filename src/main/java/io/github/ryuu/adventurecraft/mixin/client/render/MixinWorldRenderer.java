package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.extensions.client.render.ExWorldRenderer;
import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayer;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.chunk.ExClientChunkCache;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.AccessClass_61;
import io.github.ryuu.adventurecraft.mixin.level.AccessLevel;
import io.github.ryuu.adventurecraft.scripting.ScriptModel;
import io.github.ryuu.adventurecraft.util.CutsceneCameraPoint;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.IEntityPather;
import io.github.ryuu.adventurecraft.util.PlayerTorch;
import net.minecraft.*;
import net.minecraft.client.GLAllocator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.WalkingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.LevelListener;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import net.minecraft.util.maths.Vec3i;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer implements LevelListener, ExWorldRenderer {

    @Shadow
    private TextureManager textureManager;

    @Shadow
    private List<class_66> field_1807;

    @Shadow
    private int field_1813;

    @Shadow
    private Minecraft client;

    @Shadow
    private int field_1818;

    @Shadow
    private int field_1819;

    @Shadow
    private int field_1820;

    @Shadow
    private int field_1775;

    @Shadow
    private List<class_66> field_1793;

    @Shadow
    public List<TileEntity> field_1795;

    @Shadow
    int[] field_1796;

    @Shadow
    IntBuffer field_1797;

    @Shadow
    int field_1799;

    @Shadow
    private Level level;

    @Shadow
    private class_66[] field_1808;

    @Shadow
    private class_66[] field_1809;

    @Shadow
    private IntBuffer field_1816;

    @Shadow
    private boolean field_1817;

    @Shadow
    private int field_1782;

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

    @Shadow
    public abstract void renderClouds(float f);

    @ModifyVariable(method = "method_1537", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/WorldRenderer;field_1810:I",
            shift = At.Shift.BEFORE,
            ordinal = 0))
    private int modifyArgOfMethod_1537(int var1) {
        if (this.field_1782 <= 0) {
            if (var1 > 800) {
                return 800;
            }
        }
        return var1;
    }

    @Redirect(method = "method_1544", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;method_1921(Lnet/minecraft/entity/Entity;F)V",
            ordinal = 1))
    private void redirectEntityDispatch(EntityRenderDispatcher instance, Entity entity, float v) {
        ExMinecraft client = (ExMinecraft) this.client;
        if (client.isCameraActive() && client.isCameraPause() ||
                DebugMode.active && !(entity instanceof Player) || ((ExPlayer) entity).getStunned() > 0) {
            instance.method_1921(entity, 1.0f);
            return;
        }
        instance.method_1921(entity, v);
    }

    @Inject(method = "method_1544", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/WorldRenderer;field_1795:Ljava/util/List;",
            shift = At.Shift.BEFORE,
            ordinal = 0))
    private void renderScriptModels(Vec3f vec3d, class_68 icamera, float f, CallbackInfo ci) {
        GL11.glPushMatrix();
        GL11.glTranslated(-EntityRenderDispatcher.field_2490, -EntityRenderDispatcher.field_2491, -EntityRenderDispatcher.field_2492);
        ScriptModel.renderAll(f);
        GL11.glPopMatrix();
    }

    @Inject(method = "method_1548", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/WorldRenderer;method_1537()V",
            shift = At.Shift.BEFORE))
    private void updateCacheVeryFar(LivingEntity i, int d, double par3, CallbackInfoReturnable<Integer> cir) {
        ((ExClientChunkCache) ((AccessLevel) this.level).getCache()).updateVeryFar();
    }

    @Inject(method = "method_1548", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/WorldRenderer;field_1808:[Lnet/minecraft/class_66;",
            shift = At.Shift.BEFORE,
            ordinal = 8))
    private void updateField1808BasedOnFarplane(LivingEntity entityliving, int i, double d, CallbackInfoReturnable<Integer> cir, int var23) {
        if (this.field_1808[var23].field_252) {
            float farPlane = this.client.gameRenderer.getFarPlane() * 1.25f;
            this.field_1808[var23].field_252 = this.field_1808[var23].method_299(entityliving) > farPlane;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_1552(float f) {
        if (!this.client.level.dimension.hasFog) {
            if (this.client.options.fancyGraphics) {
                this.renderClouds(f);
            } else {
                GL11.glDisable(2884);
                float f1 = (float) (this.client.field_2807.prevRenderY + (this.client.field_2807.y - this.client.field_2807.prevRenderY) * (double) f);
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
                double d = this.client.field_2807.prevX + (this.client.field_2807.x - this.client.field_2807.prevX) * (double) f + (double) (((float) this.field_1818 + f) * 0.03f);
                double d1 = this.client.field_2807.prevZ + (this.client.field_2807.z - this.client.field_2807.prevZ) * (double) f;

                if (((ExMinecraft) this.client).isCameraActive()) {
                    CutsceneCameraPoint p = ((ExMinecraft) this.client).getCutsceneCamera().getCurrentPoint(f);
                    f1 = p.posY;
                    d = (double) p.posX + (double) (((float) this.field_1818 + f) * 0.03f);
                    d1 = p.posZ;
                }

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
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
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
        ArrayList<class_66> arraylist = null;
        int l = this.field_1807.size();
        int i1 = 0;

        long avgTime = 0L;
        if (PlayerTorch.isTorchActive()) {
            avgTime = ExMinecraft.getInstance().getAvgFrameTime();
        }

        for (int j1 = 0; j1 < l; ++j1) {
            class_66 worldrenderer1 = (class_66) this.field_1807.get(j1);
            if (!flag) {
                if (worldrenderer1.method_299(entityliving) > 256.0f) {
                    int k2;
                    for (k2 = 0; k2 < byte0 && (aworldrenderer[k2] == null || rendersorter.compare(aworldrenderer[k2], worldrenderer1) <= 0); ++k2) {
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
                arraylist = new ArrayList<>();
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

    @Override
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
            double offX = entityplayer.prevRenderX + (entityplayer.x - entityplayer.prevRenderX) * f;
            double offY = entityplayer.prevRenderY + (entityplayer.y - entityplayer.prevRenderY) * f;
            double offZ = entityplayer.prevRenderZ + (entityplayer.z - entityplayer.prevRenderZ) * f;
            Tessellator tessellator = Tessellator.INSTANCE;
            for (int x = minX; x <= maxX; ++x) {
                tessellator.start(3);
                tessellator.pos(x - offX, minY - offY, minZ - offZ);
                tessellator.pos(x - offX, maxY - offY, minZ - offZ);
                tessellator.pos(x - offX, maxY - offY, maxZ - offZ);
                tessellator.pos(x - offX, minY - offY, maxZ - offZ);
                tessellator.pos(x - offX, minY - offY, minZ - offZ);
                tessellator.draw();
            }
            for (int y = minY; y <= maxY; ++y) {
                tessellator.start(3);
                tessellator.pos(minX - offX, y - offY, minZ - offZ);
                tessellator.pos(maxX - offX, y - offY, minZ - offZ);
                tessellator.pos(maxX - offX, y - offY, maxZ - offZ);
                tessellator.pos(minX - offX, y - offY, maxZ - offZ);
                tessellator.pos(minX - offX, y - offY, minZ - offZ);
                tessellator.draw();
            }
            for (int z = minZ; z <= maxZ; ++z) {
                tessellator.start(3);
                tessellator.pos(minX - offX, minY - offY, z - offZ);
                tessellator.pos(maxX - offX, minY - offY, z - offZ);
                tessellator.pos(maxX - offX, maxY - offY, z - offZ);
                tessellator.pos(minX - offX, maxY - offY, z - offZ);
                tessellator.pos(minX - offX, minY - offY, z - offZ);
                tessellator.draw();
            }
            GL11.glLineWidth(1.0f);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
        }
    }

    @Override
    public void drawEntityPath(Entity e, LivingEntity entityplayer, float f) {
        if (e instanceof IEntityPather) {
            IEntityPather ent = (IEntityPather) e;
            class_61 path = ent.getCurrentPath();
            double offX = entityplayer.prevRenderX + (entityplayer.x - entityplayer.prevRenderX) * f;
            double offY = entityplayer.prevRenderY + (entityplayer.y - entityplayer.prevRenderY) * f;
            double offZ = entityplayer.prevRenderZ + (entityplayer.z - entityplayer.prevRenderZ) * f;
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
                for (int i = ((AccessClass_61) path).getField_2692(); i < path.field_2690; ++i) {
                    Vec3i p = ((AccessClass_61) path).getField_2691()[i];
                    tessellator.pos(p.x - offX + 0.5, p.y - offY + 0.5, p.z - offZ + 0.5);
                }
                tessellator.draw();
                GL11.glLineWidth(1.0f);
                GL11.glEnable(3553);
                GL11.glDisable(3042);
            }
        }
    }

    @Override
    public void drawEntityFOV(LivingEntity e, LivingEntity entityplayer, float f) {
        if (e == entityplayer) {
            return;
        }
        double offX = entityplayer.prevRenderX + (entityplayer.x - entityplayer.prevRenderX) * f;
        double offY = entityplayer.prevRenderY + (entityplayer.y - entityplayer.prevRenderY) * f;
        double offZ = entityplayer.prevRenderZ + (entityplayer.z - entityplayer.prevRenderZ) * f;
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start(3);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        if (((ExLivingEntity) e).getExtraFov() > 0.0f) {
            GL11.glColor4f(1.0f, 0.5f, 0.0f, 0.4f);
        } else {
            GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.4f);
        }
        GL11.glLineWidth(5.0f);
        GL11.glDisable(3553);
        float fov = Math.min(((ExLivingEntity) e).getFov() / 2.0f + ((ExLivingEntity) e).getExtraFov(), 180.0f);
        double xFov = 5.0 * Math.sin(-Math.PI * (e.yaw - fov) / 180.0) + e.x;
        double zFov = 5.0 * Math.cos(-Math.PI * (e.yaw - fov) / 180.0) + e.z;
        tessellator.pos(xFov - offX, e.y - offY + e.getStandingEyeHeight(), zFov - offZ);
        tessellator.pos(e.x - offX, e.y - offY + e.getStandingEyeHeight(), e.z - offZ);
        xFov = 5.0 * Math.sin(-Math.PI * (e.yaw + fov) / 180.0) + e.x;
        zFov = 5.0 * Math.cos(-Math.PI * (e.yaw + fov) / 180.0) + e.z;
        tessellator.pos(xFov - offX, e.y - offY + e.getStandingEyeHeight(), zFov - offZ);
        tessellator.draw();
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void addParticle(String particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this.spawnParticleR(particle, x, y, z, velocityX, velocityY, velocityZ);
    }

    @Override
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

    @Override
    public void updateAllTheRenderers() {
        for (class_66 class_66 : this.field_1809) {
            if (!class_66.field_249) {
                this.field_1807.add(class_66);
            }
            class_66.method_305();
        }
    }

    @Override
    public void resetAll() {
        this.doReset(false);
    }

    @Override
    public void resetForDeath() {
        this.doReset(true);
    }

    private void doReset(boolean forDeath) {
        ((ExLevel)level).getTriggerManager().resetActive = true;
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
                        ((ExTile)Tile.BY_ID[blockID]).reset(this.level, xOffset + x, yOffset + y, zOffset + z, forDeath);
                    }
                }
            }
        }
        ((ExLevel)level).getTriggerManager().resetActive = false;
    }
}
