package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityStore;
import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.extensions.client.options.ExGameOptions;
import io.github.ryuu.adventurecraft.extensions.client.render.ExGameRenderer;
import io.github.ryuu.adventurecraft.extensions.client.render.ExHandItemRenderer;
import io.github.ryuu.adventurecraft.extensions.client.render.ExWorldRenderer;
import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayer;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayerInventory;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.CutsceneCameraPoint;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.MapEditing;
import net.minecraft.class_573;
import net.minecraft.class_598;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.particle.UnknownParticle;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.client.util.Smoother;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.ClientChunkCache;
import net.minecraft.level.source.LevelSource;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.FloatBuffer;
import java.util.Random;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer implements AccessGameRenderer, ExGameRenderer {

    @Shadow
    public static boolean field_2340;

    @Shadow
    public static int field_2341;

    @Shadow
    private Minecraft minecraft;

    @Shadow
    private Smoother cinematicPitchSmoother;

    @Shadow
    private Smoother cinematicYawSmoother;

    @Shadow
    private float field_2359;

    @Shadow
    private float field_2361;

    @Shadow
    private float field_2363;

    @Shadow
    private float field_2328;

    @Shadow
    private Random random;

    @Shadow
    public HandItemRenderer handItemRenderer;

    @Shadow
    private float field_2365;

    @Shadow
    float r;

    @Shadow
    float g;

    @Shadow
    float b;

    @Shadow
    private float field_2350;

    @Shadow
    private int field_2351;

    @Shadow
    private Entity field_2352;

    @Shadow
    private float field_2360;

    @Shadow
    private float field_2362;

    @Shadow
    private float field_2364;

    @Shadow
    private float field_2327;

    @Shadow
    private float field_2329;

    @Shadow
    private boolean field_2330;

    @Shadow
    private double field_2331;

    @Shadow
    private long field_2334;

    @Shadow
    private long field_2335;

    @Shadow
    private int field_2337;

    @Shadow
    private float field_2338;

    @Shadow
    private float field_2339;

    public HandItemRenderer offHandItemRenderer;
    float farClipAdjustment;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Minecraft minecraft, CallbackInfo ci) {
        this.offHandItemRenderer = new HandItemRenderer(minecraft);
        this.farClipAdjustment = 1.0f;
    }

    @Shadow
    protected abstract void renderSkyBase(float f);

    @Shadow
    public abstract void method_1838(float f);

    @Shadow
    protected abstract FloatBuffer method_1839(float f, float f1, float f2, float f3);

    @Shadow
    protected abstract void method_1840(float f, int i);

    @Shadow
    public abstract void method_1843();

    @Shadow
    protected abstract float method_1848(float f);

    @Shadow
    protected abstract void method_1849(float f);

    @Shadow
    protected abstract void method_1850(float f);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_1837() {
        this.field_2338 = this.field_2339;
        this.field_2360 = this.field_2359;
        this.field_2362 = this.field_2361;
        this.field_2364 = this.field_2363;
        this.field_2327 = this.field_2365;
        this.field_2329 = this.field_2328;
        if (this.minecraft.field_2807 == null) {
            this.minecraft.field_2807 = this.minecraft.player;
        }
        float f = this.minecraft.level.getBrightness(MathsHelper.floor(this.minecraft.field_2807.x), MathsHelper.floor(this.minecraft.field_2807.y), MathsHelper.floor(this.minecraft.field_2807.z));
        float f1 = (float) (4 - this.minecraft.options.viewDistance) / 4.0f;
        float f2 = f * (1.0f - f1) + f1;
        this.field_2339 += (f2 - this.field_2339) * 0.1f;
        ++this.field_2351;
        this.handItemRenderer.method_1859();
        ((ExPlayerInventory) this.minecraft.player.inventory).swapOffhandWithMain();
        this.offHandItemRenderer.method_1859();
        ((ExPlayerInventory) this.minecraft.player.inventory).swapOffhandWithMain();
        this.method_1846();
    }

    @Inject(method = "method_1850", at = @At("HEAD"), cancellable = true)
    private void cancelMethod_1850(float f, CallbackInfo ci) {
        if (((ExMinecraft) this.minecraft).isCameraActive() ||
                ((ExEntity) this.minecraft.field_2807).getStunned() != 0) {
            ci.cancel();
        }
    }

    @Inject(method = "method_1851", at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V",
            ordinal = 3,
            shift = At.Shift.BEFORE,
            remap = false),
            cancellable = true)
    private void translateCameraWhenCutscene(float f, CallbackInfo ci) {
        if (((ExMinecraft) this.minecraft).isCameraActive()) {
            CutsceneCameraPoint p = ((ExMinecraft) this.minecraft).getCutsceneCamera().getCurrentPoint(f);
            GL11.glRotatef(p.rotPitch, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(p.rotYaw + 180.0f, 0.0f, 1.0f, 0.0f);
            ci.cancel();
        }
    }

    public void resetZoom() {
        this.field_2331 = 1.0;
    }

    @Override
    public float getFarPlane() {
        if (!((ExGameOptions) this.minecraft.options).isAutoFarClip()) {
            return 512 >> this.minecraft.options.viewDistance;
        }
        long avgTime = ((ExMinecraft) this.minecraft).getAvgFrameTime();
        if (avgTime > 33333333L) {
            this.farClipAdjustment *= 0.99f;
        } else if (avgTime < 20000000L) {
            this.farClipAdjustment *= 1.01f;
        }
        this.farClipAdjustment = Math.max(Math.min(this.farClipAdjustment, 1.0f), 0.25f);
        return this.farClipAdjustment * (float) (512 >> this.minecraft.options.viewDistance);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    private void method_1845(float f, int i) {
        GL11.glLoadIdentity();
        if (this.minecraft.options.anaglyph3d) {
            GL11.glTranslatef((float) (i * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        GL11.glPushMatrix();
        this.method_1849(f);
        boolean cameraActive = ((ExMinecraft) this.minecraft).isCameraActive();
        if (this.minecraft.options.bobView && !cameraActive) {
            this.method_1850(f);
        }
        if (!(this.minecraft.options.thirdPerson || cameraActive || this.minecraft.field_2807.isSleeping() || this.minecraft.options.hideHud)) {
            float swingOffhand = ((ExPlayer) this.minecraft.player).getSwingOffhandProgress(f);
            ((ExHandItemRenderer) this.handItemRenderer).renderItemInFirstPerson(f, this.minecraft.player.method_930(f), swingOffhand);
            if (((ExHandItemRenderer) this.offHandItemRenderer).hasItem()) {
                GL11.glScalef(-1.0f, 1.0f, 1.0f);
                GL11.glFrontFace(2304);
                ((ExHandItemRenderer) this.offHandItemRenderer).renderItemInFirstPerson(f, swingOffhand, this.minecraft.player.method_930(f));
                GL11.glFrontFace(2305);
            }
        }
        GL11.glPopMatrix();
        if (!this.minecraft.options.thirdPerson && !this.minecraft.field_2807.isSleeping()) {
            this.handItemRenderer.method_1864(f);
            this.method_1849(f);
        }
        if (this.minecraft.options.bobView) {
            this.method_1850(f);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_1844(float delta) {
        if (!Display.isActive()) {
            if (System.currentTimeMillis() - this.field_2334 > 500L) {
                this.minecraft.method_2135();
            }
        } else {
            this.field_2334 = System.currentTimeMillis();
        }
        if (this.minecraft.field_2778) {
            this.minecraft.field_2767.method_1972();
            float f1 = this.minecraft.options.mouseSensitivity * 0.6f + 0.2f;
            float f2 = f1 * f1 * f1 * 8.0f;
            float f3 = (float) this.minecraft.field_2767.field_2586 * f2;
            float f4 = (float) this.minecraft.field_2767.field_2587 * f2;
            int l = 1;
            if (this.minecraft.options.invertYMouse) {
                l = -1;
            }
            if (this.minecraft.options.cinematicMode) {
                f3 = this.cinematicPitchSmoother.smooth(f3, 0.05f * f2);
                f4 = this.cinematicYawSmoother.smooth(f4, 0.05f * f2);
            }
            this.minecraft.player.method_1362(f3, f4 * (float) l);
        }
        if (this.minecraft.skipGameRender) {
            return;
        }
        field_2340 = this.minecraft.options.anaglyph3d;
        ScreenScaler scaledresolution = new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        int k = Mouse.getX() * i / this.minecraft.actualWidth;
        int i1 = j - Mouse.getY() * j / this.minecraft.actualHeight - 1;
        int c = 200;
        if (this.minecraft.options.fpsLimit == 1) {
            c = 120;
        }
        if (this.minecraft.options.fpsLimit == 2) {
            c = 40;
        }
        if (this.minecraft.level != null) {
            long l1;
            if (this.minecraft.options.fpsLimit == 0) {
                this.method_1841(delta, 0L);
            } else {
                this.method_1841(delta, this.field_2335 + (long) (1000000000 / c));
            }
            if (this.minecraft.options.fpsLimit == 2 && (l1 = (this.field_2335 + (long) (1000000000 / c) - System.nanoTime()) / 1000000L) > 0L && l1 < 500L) {
                try {
                    Thread.sleep(l1);
                } catch (InterruptedException interruptedexception) {
                    interruptedexception.printStackTrace();
                }
            }
            this.field_2335 = System.nanoTime();
            if (!this.minecraft.options.hideHud || this.minecraft.currentScreen != null) {
                this.minecraft.overlay.render(delta, this.minecraft.currentScreen != null, k, i1);
            }
        } else {
            GL11.glViewport(0, 0, this.minecraft.actualWidth, this.minecraft.actualHeight);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            this.method_1843();
            if (this.minecraft.options.fpsLimit == 2) {
                long l2 = (this.field_2335 + (long) (1000000000 / c) - System.nanoTime()) / 1000000L;
                if (l2 < 0L) {
                    l2 += 10L;
                }
                if (l2 > 0L && l2 < 500L) {
                    try {
                        Thread.sleep(l2);
                    } catch (InterruptedException interruptedexception1) {
                        interruptedexception1.printStackTrace();
                    }
                }
            }
            this.field_2335 = System.nanoTime();
        }
        if (this.minecraft.currentScreen != null) {
            GL11.glClear(256);
            this.minecraft.currentScreen.render(k, i1, delta);
            if (this.minecraft.currentScreen != null && this.minecraft.currentScreen.smokeRenderer != null) {
                this.minecraft.currentScreen.smokeRenderer.render(delta);
            }
        } else if (this.minecraft.hitResult != null && this.minecraft.hitResult.type == HitType.TILE && this.minecraft.level.getTileId(this.minecraft.hitResult.x, this.minecraft.hitResult.y, this.minecraft.hitResult.z) == Blocks.store.id) {
            TileEntityStore storeObj = (TileEntityStore) this.minecraft.level.getTileEntity(this.minecraft.hitResult.x, this.minecraft.hitResult.y, this.minecraft.hitResult.z);
            if (storeObj.buySupplyLeft != 0) {
                ExMinecraft mc = (ExMinecraft) this.minecraft;
                mc.getStoreGUI().setBuyItem(storeObj.buyItemID, storeObj.buyItemAmount, storeObj.buyItemDamage);
                mc.getStoreGUI().setSellItem(storeObj.sellItemID, storeObj.sellItemAmount, storeObj.sellItemDamage);
                mc.getStoreGUI().setSupplyLeft(storeObj.buySupplyLeft);
                mc.updateStoreGUI();
                mc.getStoreGUI().render(k, i1, delta);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_1841(float f, long l) {
        GL11.glEnable(2884);
        GL11.glEnable(2929);
        if (((ExMinecraft) this.minecraft).isCameraActive() && ((ExMinecraft) this.minecraft).getCutsceneCamera().isEmpty()) {
            ((ExMinecraft) this.minecraft).setCameraActive(false);
        }
        if (((ExMinecraft) this.minecraft).isCameraActive()) {
            CutsceneCameraPoint p = ((ExMinecraft) this.minecraft).getCutsceneCamera().getCurrentPoint(f);
            this.minecraft.field_2807 = ((ExMinecraft) this.minecraft).getCutsceneCameraEntity();
            this.minecraft.field_2807.prevRenderX = this.minecraft.field_2807.prevX = p.posX;
            this.minecraft.field_2807.x = this.minecraft.field_2807.prevX;
            this.minecraft.field_2807.prevRenderY = this.minecraft.field_2807.prevY = p.posY;
            this.minecraft.field_2807.y = this.minecraft.field_2807.prevY;
            this.minecraft.field_2807.prevRenderZ = this.minecraft.field_2807.prevZ = p.posZ;
            this.minecraft.field_2807.z = this.minecraft.field_2807.prevZ;
            this.minecraft.field_2807.yaw = this.minecraft.field_2807.prevYaw = p.rotYaw;
            this.minecraft.field_2807.pitch = this.minecraft.field_2807.prevPitch = p.rotPitch;
        } else {
            this.minecraft.field_2807 = this.minecraft.player;
            if (((ExEntity) this.minecraft.player).getStunned() != 0) {
                this.minecraft.player.prevRenderX = this.minecraft.player.prevX = this.minecraft.player.x;
                this.minecraft.player.prevRenderY = this.minecraft.player.prevY = this.minecraft.player.y;
                this.minecraft.player.prevRenderZ = this.minecraft.player.prevZ = this.minecraft.player.z;
                this.minecraft.player.field_1634 = this.minecraft.player.field_1635;
            }
        }
        this.method_1838(f);
        LivingEntity entityliving = this.minecraft.field_2807;
        WorldRenderer renderglobal = this.minecraft.worldRenderer;
        ParticleManager effectrenderer = this.minecraft.particleManager;
        double d = entityliving.prevRenderX + (entityliving.x - entityliving.prevRenderX) * (double) f;
        double d1 = entityliving.prevRenderY + (entityliving.y - entityliving.prevRenderY) * (double) f;
        double d2 = entityliving.prevRenderZ + (entityliving.z - entityliving.prevRenderZ) * (double) f;
        LevelSource ichunkprovider = this.minecraft.level.getLevelSource();
        if (ichunkprovider instanceof ClientChunkCache) {
            ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
            int j = MathsHelper.floor((int) d) >> 4;
            int k = MathsHelper.floor((int) d2) >> 4;
            chunkproviderloadorgenerate.setSpawnChunk(j, k);
        }
        for (int i = 0; i < 2; ++i) {
            ItemInstance curItem;
            if (this.minecraft.options.anaglyph3d) {
                field_2341 = i;
                if (field_2341 == 0) {
                    GL11.glColorMask(false, true, true, false);
                } else {
                    GL11.glColorMask(true, false, false, false);
                }
            }
            GL11.glViewport(0, 0, this.minecraft.actualWidth, this.minecraft.actualHeight);
            this.renderSkyBase(f);
            GL11.glClear(16640);
            GL11.glEnable(2884);
            this.method_1840(f, i);
            class_598.method_1973();
            if (this.minecraft.options.viewDistance < 3) {
                this.method_1842(-1, f);
                renderglobal.renderSky(f);
            }
            GL11.glEnable(2912);
            this.method_1842(1, f);
            if (this.minecraft.options.ao) {
                GL11.glShadeModel(7425);
            }
            class_573 frustrum = new class_573();
            frustrum.method_2006(d, d1, d2);
            this.minecraft.worldRenderer.method_1550(frustrum, f);
            if (i == 0) {
                long l1;
                while (!this.minecraft.worldRenderer.method_1549(entityliving, false) && l != 0L && (l1 = l - System.nanoTime()) >= 0L && l1 <= 1000000000L) {
                }
            }
            GL11.glPushMatrix();
            if (DebugMode.editMode && DebugMode.mapEditing != null) {
                DebugMode.mapEditing.updateCursor(entityliving, this.method_1848(f), f);
            }
            this.method_1842(0, f);
            GL11.glEnable(2912);
            GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/terrain.png"));
            RenderHelper.disableLighting();
            renderglobal.method_1548(entityliving, 0, f);
            GL11.glShadeModel(7424);
            RenderHelper.enableLighting();
            renderglobal.method_1544(entityliving.method_931(f), frustrum, f);
            effectrenderer.method_327(entityliving, f);
            RenderHelper.disableLighting();
            this.method_1842(0, f);
            effectrenderer.method_324(entityliving, f);
            if (this.minecraft.hitResult != null && entityliving.isInFluid(Material.WATER) && entityliving instanceof Player) {
                Player entityplayer = (Player) entityliving;
                GL11.glDisable(3008);
                renderglobal.method_1547(entityplayer, this.minecraft.hitResult, 0, entityplayer.inventory.getHeldItem(), f);
                renderglobal.method_1554(entityplayer, this.minecraft.hitResult, 0, entityplayer.inventory.getHeldItem(), f);
                GL11.glEnable(3008);
            }
            GL11.glBlendFunc(770, 771);
            this.method_1842(0, f);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/terrain.png"));
            if (this.minecraft.options.fancyGraphics) {
                if (this.minecraft.options.ao) {
                    GL11.glShadeModel(7425);
                }
                GL11.glColorMask(false, false, false, false);
                int i1 = renderglobal.method_1548(entityliving, 1, f);
                if (this.minecraft.options.anaglyph3d) {
                    if (field_2341 == 0) {
                        GL11.glColorMask(false, true, true, true);
                    } else {
                        GL11.glColorMask(true, false, false, true);
                    }
                } else {
                    GL11.glColorMask(true, true, true, true);
                }
                if (i1 > 0) {
                    renderglobal.method_1540(1, f);
                }
                GL11.glShadeModel(7424);
            } else {
                renderglobal.method_1548(entityliving, 1, f);
            }
            if (DebugMode.editMode && DebugMode.mapEditing != null) {
                DebugMode.mapEditing.render(f);
            }
            if ((curItem = this.minecraft.player.inventory.getHeldItem()) != null && curItem.itemId == Items.paste.id) {
                if (DebugMode.mapEditing == null) {
                    DebugMode.mapEditing = new MapEditing(this.minecraft, this.minecraft.level);
                } else {
                    DebugMode.mapEditing.updateWorld(this.minecraft.level);
                }
                DebugMode.mapEditing.renderSelection(f);
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            if (!DebugMode.editMode && this.field_2331 == 1.0 && entityliving instanceof Player && this.minecraft.hitResult != null && !entityliving.isInFluid(Material.WATER)) {
                Player entityplayer1 = (Player) entityliving;
                GL11.glDisable(3008);
                renderglobal.method_1547(entityplayer1, this.minecraft.hitResult, 0, entityplayer1.inventory.getHeldItem(), f);
                renderglobal.method_1554(entityplayer1, this.minecraft.hitResult, 0, entityplayer1.inventory.getHeldItem(), f);
                GL11.glEnable(3008);
            }
            GL11.glDisable(3008);
            ((ExWorldRenderer) renderglobal).drawCursorSelection(entityliving, ((Player) entityliving).inventory.getHeldItem(), f);
            if (DebugMode.active && ((ExMinecraft) this.minecraft).getActiveCutsceneCamera() != null) {
                ((ExMinecraft) this.minecraft).getActiveCutsceneCamera().drawLines(entityliving, f);
            }
            if (DebugMode.active || DebugMode.renderPaths) {
                for (Object o : this.minecraft.level.entities) {
                    Entity e = (Entity) o;
                    ((ExWorldRenderer) renderglobal).drawEntityPath(e, entityliving, f);
                }
            }
            if (DebugMode.active || DebugMode.renderFov) {
                for (Object obj : this.minecraft.level.entities) {
                    Entity e = (Entity) obj;
                    if (!(e instanceof LivingEntity)) continue;
                    ((ExWorldRenderer) renderglobal).drawEntityFOV((LivingEntity) e, entityliving, f);
                }
            }
            GL11.glEnable(3008);
            GL11.glDisable(2912);
            if (this.field_2352 == null) {
            }
            this.method_1842(0, f);
            GL11.glEnable(2912);
            renderglobal.method_1552(f);
            GL11.glDisable(2912);
            GL11.glPopMatrix();
            this.renderWeather(f);
            this.method_1842(1, f);
            if (this.field_2331 == 1.0) {
                GL11.glClear(256);
                this.method_1845(f, i);
            }
            if (this.minecraft.options.anaglyph3d) continue;
            return;
        }
        GL11.glColorMask(true, true, true, false);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    private void method_1846() {
        float f = this.minecraft.level.getRainGradient(1.0f);
        if (!this.minecraft.options.fancyGraphics) {
            f /= 2.0f;
        }

        if (f != 0.0f) {
            this.random.setSeed((long) this.field_2351 * 312987231L);
            LivingEntity entityliving = this.minecraft.field_2807;
            Level world = this.minecraft.level;
            int i = MathsHelper.floor(entityliving.x);
            int j = MathsHelper.floor(entityliving.y);
            int k = MathsHelper.floor(entityliving.z);
            int byte0 = 10;
            double d = 0.0;
            double d1 = 0.0;
            double d2 = 0.0;
            int l = 0;

            for (int i1 = 0; i1 < (int) (100.0f * f * f); ++i1) {
                int j1 = i + this.random.nextInt(byte0) - this.random.nextInt(byte0);
                int k1 = k + this.random.nextInt(byte0) - this.random.nextInt(byte0);
                int l1 = world.getOceanFloorHeight(j1, k1);
                int i2 = world.getTileId(j1, l1 - 1, k1);
                if (l1 <= j + byte0 && l1 >= j - byte0 && !(((ExLevel) world).getTemperatureValue(j1, k1) < 0.5)) {
                    float f1 = this.random.nextFloat();
                    float f2 = this.random.nextFloat();
                    if (i2 <= 0) continue;
                    if (Tile.BY_ID[i2].material == Material.LAVA) {
                        this.minecraft.particleManager.addParticle(new SmokeParticle(world, j1 + f1, (l1 + 0.1f) - Tile.BY_ID[i2].minY, k1 + f2, 0.0, 0.0, 0.0));
                    } else {
                        if (this.random.nextInt(++l) == 0) {
                            d = (float) j1 + f1;
                            d1 = (double) ((float) l1 + 0.1f) - Tile.BY_ID[i2].minY;
                            d2 = (float) k1 + f2;
                        }
                        this.minecraft.particleManager.addParticle(new UnknownParticle(world, j1 + f1, (l1 + 0.1f) - Tile.BY_ID[i2].minY, k1 + f2));
                    }
                }
            }

            if (l > 0 && this.random.nextInt(3) < this.field_2337++) {
                this.field_2337 = 0;
                if (d1 > entityliving.y + 1.0 && world.getOceanFloorHeight(MathsHelper.floor(entityliving.x), MathsHelper.floor(entityliving.z)) > MathsHelper.floor(entityliving.y)) {
                    this.minecraft.level.playSound(d, d1, d2, "ambient.weather.rain", 0.1f, 0.5f);
                } else {
                    this.minecraft.level.playSound(d, d1, d2, "ambient.weather.rain", 0.2f, 1.0f);
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void renderWeather(float tickDelta) {
        float f1 = this.minecraft.level.getRainGradient(tickDelta);
        if (f1 <= 0.0f) {
            return;
        }
        LivingEntity entityliving = this.minecraft.field_2807;
        Level world = this.minecraft.level;
        int i = MathsHelper.floor(entityliving.x);
        int j = MathsHelper.floor(entityliving.y);
        int k = MathsHelper.floor(entityliving.z);
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glDisable(2884);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glAlphaFunc(516, 0.01f);
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/environment/snow.png"));
        double d = entityliving.prevRenderX + (entityliving.x - entityliving.prevRenderX) * (double) tickDelta;
        double d1 = entityliving.prevRenderY + (entityliving.y - entityliving.prevRenderY) * (double) tickDelta;
        double d2 = entityliving.prevRenderZ + (entityliving.z - entityliving.prevRenderZ) * (double) tickDelta;
        int l = MathsHelper.floor(d1);
        int i1 = 5;
        if (this.minecraft.options.fancyGraphics) {
            i1 = 10;
        }
        for (int k1 = i - i1; k1 <= i + i1; ++k1) {
            for (int i2 = k - i1; i2 <= k + i1; ++i2) {
                int i3;
                if (((ExLevel) world).getTemperatureValue(k1, i2) >= 0.5) continue;
                int k2 = world.getOceanFloorHeight(k1, i2);
                if (k2 < 0) {
                    k2 = 0;
                }
                if ((i3 = k2) < l) {
                    i3 = l;
                }
                int k3 = j - i1;
                int i4 = j + i1;
                if (k3 < k2) {
                    k3 = k2;
                }
                if (i4 < k2) {
                    i4 = k2;
                }
                float f3 = 1.0f;
                if (k3 == i4) continue;
                this.random.setSeed(k1 * k1 * 3121L + k1 * 45238971L + i2 * i2 * 418711L + i2 * 13761L);
                float f5 = this.field_2351 + tickDelta;
                float f6 = ((this.field_2351 & 0x1FF) + tickDelta) / 512.0f;
                float f7 = this.random.nextFloat() + f5 * 0.01f * (float) this.random.nextGaussian();
                float f8 = this.random.nextFloat() + f5 * (float) this.random.nextGaussian() * 0.001f;
                double d5 = (k1 + 0.5f) - entityliving.x;
                double d6 = (i2 + 0.5f) - entityliving.z;
                float f11 = MathsHelper.sqrt(d5 * d5 + d6 * d6) / (float) i1;
                tessellator.start();
                float f12 = world.getBrightness(k1, i3, i2);
                GL11.glColor4f(f12, f12, f12, ((1.0f - f11 * f11) * 0.3f + 0.5f) * f1);
                tessellator.prevPos(-d, -d1, -d2);
                tessellator.vertex(k1, k3, i2 + 0.5, f7, (float) k3 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex(k1 + 1, k3, i2 + 0.5, f3 + f7, (float) k3 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex(k1 + 1, i4, i2 + 0.5, f3 + f7, (float) i4 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex(k1, i4, i2 + 0.5, f7, (float) i4 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex(k1 + 0.5, k3, i2, f7, (float) k3 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex(k1 + 0.5, k3, i2 + 1, f3 + f7, (float) k3 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex(k1 + 0.5, i4, i2 + 1, f3 + f7, (float) i4 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex(k1 + 0.5, i4, i2, f7, (float) i4 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.prevPos(0.0, 0.0, 0.0);
                tessellator.draw();
            }
        }
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/environment/rain.png"));
        if (this.minecraft.options.fancyGraphics) {
            i1 = 10;
        }

        for (int l1 = i - i1; l1 <= i + i1; ++l1) {
            for (int j2 = k - i1; j2 <= k + i1; ++j2) {
                if (((ExLevel) world).getTemperatureValue(l1, j2) < 0.5) continue;
                int l2 = world.getOceanFloorHeight(l1, j2);
                int j3 = j - i1;
                int l3 = j + i1;
                if (j3 < l2) {
                    j3 = l2;
                }
                if (l3 < l2) {
                    l3 = l2;
                }
                float f2 = 1.0f;
                if (j3 == l3) continue;
                this.random.setSeed(l1 * l1 * 3121L + l1 * 45238971L + j2 * j2 * 418711L + j2 * 13761L);
                float f4 = ((this.field_2351 + l1 * l1 * 3121 + l1 * 45238971 + j2 * j2 * 418711 + j2 * 13761 & 0x1F) + tickDelta) / 32.0f * (3.0f + this.random.nextFloat());
                double d3 = (l1 + 0.5f) - entityliving.x;
                double d4 = (j2 + 0.5f) - entityliving.z;
                float f9 = MathsHelper.sqrt(d3 * d3 + d4 * d4) / (float) i1;
                tessellator.start();
                float f10 = world.getBrightness(l1, 128, j2) * 0.85f + 0.15f;
                GL11.glColor4f(f10, f10, f10, ((1.0f - f9 * f9) * 0.5f + 0.5f) * f1);
                tessellator.prevPos(-d, -d1, -d2);
                tessellator.vertex(l1, j3, j2 + 0.5, 0.0f, (float) j3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex(l1 + 1, j3, j2 + 0.5, f2, (float) j3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex(l1 + 1, l3, j2 + 0.5, f2, (float) l3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex(l1, l3, j2 + 0.5, 0.0f, (float) l3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex(l1 + 0.5, j3, j2, 0.0f, (float) j3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex(l1 + 0.5, j3, j2 + 1, f2, (float) j3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex(l1 + 0.5, l3, j2 + 1, f2, (float) l3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex(l1 + 0.5, l3, j2, 0.0f, (float) l3 * f2 / 4.0f + f4 * f2);
                tessellator.prevPos(0.0, 0.0, 0.0);
                tessellator.draw();
            }
        }
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
    }

    @Redirect(method = "method_1843", at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/opengl/GL11;glClear(I)V",
            remap = false))
    private void doNotClear(int mask) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    private void method_1842(int i, float f) {
        LivingEntity entityliving = this.minecraft.field_2807;
        GL11.glFog(2918, this.method_1839(this.r, this.g, this.b, 1.0f));
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.field_2330) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.1f);
            //float f1 = 1.0f;
            //float f4 = 1.0f;
            //float f7 = 1.0f;
            //if (this.minecraft.options.anaglyph3d) {
            //    float f10 = (f1 * 30.0f + f4 * 59.0f + f7 * 11.0f) / 100.0f;
            //    float f13 = (f1 * 30.0f + f4 * 70.0f) / 100.0f;
            //    float f16 = (f1 * 30.0f + f7 * 70.0f) / 100.0f;
            //    f1 = f10;
            //    f4 = f13;
            //    f7 = f16;
            //}
        } else if (entityliving.isInFluid(Material.WATER)) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.1f);
            float f2 = 0.4f;
            float f5 = 0.4f;
            float f8 = 0.9f;
            //if (this.minecraft.options.anaglyph3d) {
            //    float f11 = (f2 * 30.0f + f5 * 59.0f + f8 * 11.0f) / 100.0f;
            //    float f14 = (f2 * 30.0f + f5 * 70.0f) / 100.0f;
            //    float f17 = (f2 * 30.0f + f8 * 70.0f) / 100.0f;
            //    f2 = f11;
            //    f5 = f14;
            //    f8 = f17;
            //}
        } else if (entityliving.isInFluid(Material.LAVA)) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 2.0f);
            float f3 = 0.4f;
            float f6 = 0.3f;
            float f9 = 0.3f;
            //if (this.minecraft.options.anaglyph3d) {
            //    float f12 = (f3 * 30.0f + f6 * 59.0f + f9 * 11.0f) / 100.0f;
            //    float f15 = (f3 * 30.0f + f6 * 70.0f) / 100.0f;
            //    float f18 = (f3 * 30.0f + f9 * 70.0f) / 100.0f;
            //    f3 = f12;
            //    f6 = f15;
            //    f9 = f18;
            //}
        } else {
            Level world = this.minecraft.level;
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, ((ExLevel) world).getFogStart(this.field_2350 * 0.25f, f));
            GL11.glFogf(2916, ((ExLevel) world).getFogEnd(this.field_2350, f));
            if (i < 0) {
                GL11.glFogf(2915, ((ExLevel) world).getFogStart(0.0f, f));
                GL11.glFogf(2916, ((ExLevel) world).getFogEnd(0.8f * this.field_2350, f));
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi(34138, 34139);
            }
            if (this.minecraft.level.dimension.hasFog) {
                GL11.glFogf(2915, ((ExLevel) world).getFogStart(0.0f, f));
            }
        }
        GL11.glEnable(2903);
        GL11.glColorMaterial(1028, 4608);
    }

    @Override
    public HandItemRenderer getOffHandItemRenderer() {
        return this.offHandItemRenderer;
    }
}
