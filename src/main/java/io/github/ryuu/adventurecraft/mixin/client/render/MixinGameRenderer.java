package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityStore;
import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.CutsceneCameraPoint;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.MapEditing;
import net.minecraft.class_537;
import net.minecraft.class_573;
import net.minecraft.class_598;
import net.minecraft.client.GLAllocator;
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
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Shadow()
    public static boolean field_2340 = false;

    public static int field_2341;
    private final Minecraft minecraft;
    private final Smoother cinematicPitchSmoother = new Smoother();
    private final Smoother cinematicYawSmoother = new Smoother();
    private final Smoother field_2355 = new Smoother();
    private final Smoother field_2356 = new Smoother();
    private final Smoother field_2357 = new Smoother();
    private final Smoother field_2358 = new Smoother();
    private final float field_2359 = 4.0f;
    private final float field_2361 = 0.0f;
    private final float field_2363 = 0.0f;
    private final float field_2328 = 0.0f;
    private final double field_2332 = 0.0;
    private final double field_2333 = 0.0;
    private final Random random = new Random();
    public HandItemRenderer handItemRenderer;
    public float field_2365 = 0.0f;
    public HandItemRenderer offHandItemRenderer;
    volatile int field_2343 = 0;
    volatile int field_2344 = 0;
    FloatBuffer field_2345 = GLAllocator.createFloatBuffer(16);
    float r;
    float g;
    float b;
    float farClipAdjustment;
    private float field_2350 = 0.0f;
    private int field_2351;
    private Entity field_2352 = null;
    private float field_2360 = 4.0f;
    private float field_2362 = 0.0f;
    private float field_2364 = 0.0f;
    private float field_2327 = 0.0f;
    private float field_2329 = 0.0f;
    private boolean field_2330 = false;
    private double field_2331 = 1.0;
    private long field_2334 = System.currentTimeMillis();
    private long field_2335 = 0L;
    private int field_2337 = 0;
    private float field_2338;
    private float field_2339;

    public MixinGameRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.handItemRenderer = new HandItemRenderer(minecraft);
        this.offHandItemRenderer = new HandItemRenderer(minecraft);
        this.farClipAdjustment = 1.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
        this.minecraft.player.inventory.swapOffhandWithMain();
        this.offHandItemRenderer.method_1859();
        this.minecraft.player.inventory.swapOffhandWithMain();
        this.method_1846();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1838(float f) {
        if (this.minecraft.field_2807 == null) {
            return;
        }
        if (this.minecraft.level == null) {
            return;
        }
        double d = this.minecraft.interactionManager.method_1715();
        this.minecraft.hitResult = this.minecraft.field_2807.method_929(d, f);
        double d1 = d;
        Vec3f vec3d = this.minecraft.field_2807.method_931(f);
        if (this.minecraft.hitResult != null) {
            d1 = this.minecraft.hitResult.field_1988.method_1294(vec3d);
        }
        if (this.minecraft.interactionManager instanceof class_537) {
            d = 32.0;
            d1 = 32.0;
        } else {
            if (d1 > 3.0) {
                d1 = 3.0;
            }
            d = d1;
        }
        Vec3f vec3d1 = this.minecraft.field_2807.method_926(f);
        Vec3f vec3d2 = vec3d.method_1301(vec3d1.x * d, vec3d1.y * d, vec3d1.z * d);
        this.field_2352 = null;
        float f1 = 1.0f;
        List list = this.minecraft.level.getEntities(this.minecraft.field_2807, this.minecraft.field_2807.boundingBox.add(vec3d1.x * d, vec3d1.y * d, vec3d1.z * d).expand(f1, f1, f1));
        double d2 = 0.0;
        for (Object o : list) {
            double d3;
            Entity entity = (Entity) o;
            if (!entity.method_1356()) continue;
            float f2 = entity.method_1369();
            Box axisalignedbb = entity.boundingBox.expand(f2, f2, f2);
            HitResult movingobjectposition = axisalignedbb.method_89(vec3d, vec3d2);
            if (axisalignedbb.method_88(vec3d)) {
                if (!(0.0 < d2) && d2 != 0.0) continue;
                this.field_2352 = entity;
                d2 = 0.0;
                continue;
            }
            if (movingobjectposition == null || !((d3 = vec3d.method_1294(movingobjectposition.field_1988)) < d2) && d2 != 0.0)
                continue;
            this.field_2352 = entity;
            d2 = d3;
        }
        if (this.field_2352 != null && !(this.minecraft.interactionManager instanceof class_537)) {
            this.minecraft.hitResult = new HitResult(this.field_2352);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private float method_1848(float f) {
        LivingEntity entityliving = this.minecraft.field_2807;
        float f1 = 70.0f;
        if (entityliving.isInFluid(Material.WATER)) {
            f1 = 60.0f;
        }
        if (entityliving.health <= 0) {
            float f2 = (float) entityliving.deathTime + f;
            f1 /= (1.0f - 500.0f / (f2 + 500.0f)) * 2.0f + 1.0f;
        }
        return f1 + this.field_2327 + (this.field_2365 - this.field_2327) * f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1849(float f) {
        LivingEntity entityliving = this.minecraft.field_2807;
        float f1 = (float) entityliving.hurtTime - f;
        if (entityliving.health <= 0) {
            float f2 = (float) entityliving.deathTime + f;
            GL11.glRotatef(40.0f - 8000.0f / (f2 + 200.0f), 0.0f, 0.0f, 1.0f);
        }
        if (f1 < 0.0f) {
            return;
        }
        f1 /= (float) entityliving.field_1039;
        f1 = MathsHelper.sin(f1 * f1 * f1 * f1 * 3.141593f);
        float f3 = entityliving.field_1040;
        GL11.glRotatef(-f3, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-f1 * 14.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(f3, 0.0f, 1.0f, 0.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1850(float f) {
        if (!(this.minecraft.field_2807 instanceof Player) ||
                ((ExMinecraft) this.minecraft).isCameraActive() ||
                ((ExLivingEntity) this.minecraft.field_2807).getStunned() != 0) {
            return;
        }
        Player entityplayer = (Player) this.minecraft.field_2807;
        float f1 = entityplayer.field_1635 - entityplayer.field_1634;
        float f2 = -(entityplayer.field_1635 + f1 * f);
        float f3 = entityplayer.field_524 + (entityplayer.field_525 - entityplayer.field_524) * f;
        float f4 = entityplayer.field_1043 + (entityplayer.field_1044 - entityplayer.field_1043) * f;
        GL11.glTranslatef(MathsHelper.sin(f2 * 3.141593f) * f3 * 0.5f, -Math.abs(MathsHelper.cos(f2 * 3.141593f) * f3), 0.0f);
        GL11.glRotatef(MathsHelper.sin(f2 * 3.141593f) * f3 * 3.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(Math.abs(MathsHelper.cos(f2 * 3.141593f - 0.2f) * f3) * 5.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(f4, 1.0f, 0.0f, 0.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1851(float f) {
        LivingEntity entityliving = this.minecraft.field_2807;
        float f1 = entityliving.standingEyeHeight - 1.62f;
        double d = entityliving.prevX + (entityliving.x - entityliving.prevX) * (double) f;
        double d1 = entityliving.prevY + (entityliving.y - entityliving.prevY) * (double) f - (double) f1;
        double d2 = entityliving.prevZ + (entityliving.z - entityliving.prevZ) * (double) f;
        GL11.glRotatef(this.field_2329 + (this.field_2328 - this.field_2329) * f, 0.0f, 0.0f, 1.0f);
        if (entityliving.isSleeping()) {
            f1 = (float) ((double) f1 + 1.0);
            GL11.glTranslatef(0.0f, 0.3f, 0.0f);
            if (!this.minecraft.options.field_1447) {
                int i = this.minecraft.level.getTileId(MathsHelper.floor(entityliving.x), MathsHelper.floor(entityliving.y), MathsHelper.floor(entityliving.z));
                if (i == Tile.BED.id) {
                    int j = this.minecraft.level.getTileMeta(MathsHelper.floor(entityliving.x), MathsHelper.floor(entityliving.y), MathsHelper.floor(entityliving.z));
                    int k = j & 3;
                    GL11.glRotatef((float) (k * 90), 0.0f, 1.0f, 0.0f);
                }
                GL11.glRotatef(entityliving.prevYaw + (entityliving.yaw - entityliving.prevYaw) * f + 180.0f, 0.0f, -1.0f, 0.0f);
                GL11.glRotatef(entityliving.prevPitch + (entityliving.pitch - entityliving.prevPitch) * f, -1.0f, 0.0f, 0.0f);
            }
        } else if (this.minecraft.options.thirdPerson) {
            double d3 = this.field_2360 + (this.field_2359 - this.field_2360) * f;
            if (this.minecraft.options.field_1447) {
                float f2 = this.field_2362 + (this.field_2361 - this.field_2362) * f;
                float f4 = this.field_2364 + (this.field_2363 - this.field_2364) * f;
                GL11.glTranslatef(0.0f, 0.0f, (float) (-d3));
                GL11.glRotatef(f4, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(f2, 0.0f, 1.0f, 0.0f);
            } else {
                float f3 = entityliving.yaw;
                float f5 = entityliving.pitch;
                double d4 = (double) (-MathsHelper.sin(f3 / 180.0f * 3.141593f) * MathsHelper.cos(f5 / 180.0f * 3.141593f)) * d3;
                double d5 = (double) (MathsHelper.cos(f3 / 180.0f * 3.141593f) * MathsHelper.cos(f5 / 180.0f * 3.141593f)) * d3;
                double d6 = (double) (-MathsHelper.sin(f5 / 180.0f * 3.141593f)) * d3;
                for (int l = 0; l < 8; ++l) {
                    double d7;
                    HitResult movingobjectposition;
                    float f6 = (l & 1) * 2 - 1;
                    float f7 = (l >> 1 & 1) * 2 - 1;
                    float f8 = (l >> 2 & 1) * 2 - 1;
                    if ((movingobjectposition = this.minecraft.level.raycast(Vec3f.from(d + (double) (f6 *= 0.1f), d1 + (double) (f7 *= 0.1f), d2 + (double) (f8 *= 0.1f)), Vec3f.from(d - d4 + (double) f6 + (double) f8, d1 - d6 + (double) f7, d2 - d5 + (double) f8))) == null || !((d7 = movingobjectposition.field_1988.method_1294(Vec3f.from(d, d1, d2))) < d3))
                        continue;
                    d3 = d7;
                }
                GL11.glRotatef(entityliving.pitch - f5, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(entityliving.yaw - f3, 0.0f, 1.0f, 0.0f);
                GL11.glTranslatef(0.0f, 0.0f, (float) (-d3));
                GL11.glRotatef(f3 - entityliving.yaw, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(f5 - entityliving.pitch, 1.0f, 0.0f, 0.0f);
            }
        } else {
            if (this.minecraft.cameraActive) {
                CutsceneCameraPoint p = this.minecraft.cutsceneCamera.getCurrentPoint(f);
                GL11.glRotatef(p.rotPitch, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(p.rotYaw + 180.0f, 0.0f, 1.0f, 0.0f);
                return;
            }
            GL11.glTranslatef(0.0f, 0.0f, -0.1f);
        }
        if (!this.minecraft.options.field_1447) {
            GL11.glRotatef(entityliving.prevPitch + (entityliving.pitch - entityliving.prevPitch) * f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(entityliving.prevYaw + (entityliving.yaw - entityliving.prevYaw) * f + 180.0f, 0.0f, 1.0f, 0.0f);
        }
        GL11.glTranslatef(0.0f, f1, 0.0f);
        d = entityliving.prevX + (entityliving.x - entityliving.prevX) * (double) f;
        d1 = entityliving.prevY + (entityliving.y - entityliving.prevY) * (double) f - (double) f1;
        d2 = entityliving.prevZ + (entityliving.z - entityliving.prevZ) * (double) f;
        this.field_2330 = this.minecraft.worldRenderer.method_1538(d, d1, d2, f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void resetZoom() {
        this.field_2331 = 1.0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getFarPlane() {
        if (!this.minecraft.options.autoFarClip) {
            return 512 >> this.minecraft.options.viewDistance;
        }
        long avgTime = ((ExMinecraft)this.minecraft).getAvgFrameTime();
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
    @Overwrite()
    private void method_1840(float f, int i) {
        float f2;
        this.field_2350 = this.getFarPlane();
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        float f1 = 0.07f;
        if (this.minecraft.options.anaglyph3d) {
            GL11.glTranslatef((float) (-(i * 2 - 1)) * f1, 0.0f, 0.0f);
        }
        if (this.field_2331 != 1.0) {
            GL11.glTranslatef((float) this.field_2332, (float) (-this.field_2333), 0.0f);
            GL11.glScaled(this.field_2331, this.field_2331, 1.0);
            GLU.gluPerspective(this.method_1848(f), (float) this.minecraft.actualWidth / (float) this.minecraft.actualHeight, 0.05f, this.field_2350);
        } else {
            GLU.gluPerspective(this.method_1848(f), (float) this.minecraft.actualWidth / (float) this.minecraft.actualHeight, 0.05f, this.field_2350);
        }
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        if (this.minecraft.options.anaglyph3d) {
            GL11.glTranslatef((float) (i * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        this.method_1849(f);
        if (this.minecraft.options.bobView) {
            this.method_1850(f);
        }
        if ((f2 = this.minecraft.player.field_505 + (this.minecraft.player.field_504 - this.minecraft.player.field_505) * f) > 0.0f) {
            float f3 = 5.0f / (f2 * f2 + 5.0f) - f2 * 0.04f;
            f3 *= f3;
            GL11.glRotatef(((float) this.field_2351 + f) * 20.0f, 0.0f, 1.0f, 1.0f);
            GL11.glScalef(1.0f / f3, 1.0f, 1.0f);
            GL11.glRotatef(-((float) this.field_2351 + f) * 20.0f, 0.0f, 1.0f, 1.0f);
        }
        this.method_1851(f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1845(float f, int i) {
        GL11.glLoadIdentity();
        if (this.minecraft.options.anaglyph3d) {
            GL11.glTranslatef((float) (i * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        GL11.glPushMatrix();
        this.method_1849(f);
        if (this.minecraft.options.bobView && !this.minecraft.cameraActive) {
            this.method_1850(f);
        }
        if (!(this.minecraft.options.thirdPerson || this.minecraft.cameraActive || this.minecraft.field_2807.isSleeping() || this.minecraft.options.hideHud)) {
            this.handItemRenderer.renderItemInFirstPerson(f, this.minecraft.player.method_930(f), this.minecraft.player.getSwingOffhandProgress(f));
            if (this.offHandItemRenderer.hasItem()) {
                GL11.glScalef(-1.0f, 1.0f, 1.0f);
                GL11.glFrontFace(2304);
                this.offHandItemRenderer.renderItemInFirstPerson(f, this.minecraft.player.getSwingOffhandProgress(f), this.minecraft.player.method_930(f));
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
    @Overwrite()
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
                this.minecraft.storeGUI.setBuyItem(storeObj.buyItemID, storeObj.buyItemAmount, storeObj.buyItemDamage);
                this.minecraft.storeGUI.setSellItem(storeObj.sellItemID, storeObj.sellItemAmount, storeObj.sellItemDamage);
                this.minecraft.storeGUI.setSupplyLeft(storeObj.buySupplyLeft);
                this.minecraft.updateStoreGUI();
                this.minecraft.storeGUI.render(k, i1, delta);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1841(float f, long l) {
        GL11.glEnable(2884);
        GL11.glEnable(2929);
        if (((ExMinecraft)this.minecraft).isCameraActive() && ((ExMinecraft)this.minecraft).getCutsceneCamera().isEmpty()) {
            ((ExMinecraft)this.minecraft).setCameraActive(false);
        }
        if (((ExMinecraft)this.minecraft).isCameraActive()) {
            CutsceneCameraPoint p = ((ExMinecraft)this.minecraft).getCutsceneCamera().getCurrentPoint(f);
            this.minecraft.field_2807 = ((ExMinecraft)this.minecraft).getCutsceneCameraEntity();
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
            if (((ExLivingEntity)this.minecraft.player).getStunned() != 0) {
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
            renderglobal.drawCursorSelection(entityliving, ((Player) entityliving).inventory.getHeldItem(), f);
            if (DebugMode.active && ((ExMinecraft) this.minecraft).getActiveCutsceneCamera() != null) {
                ((ExMinecraft) this.minecraft).getActiveCutsceneCamera().drawLines(entityliving, f);
            }
            if (DebugMode.active || DebugMode.renderPaths) {
                for (Object o : this.minecraft.level.entities) {
                    Entity e = (Entity) o;
                    renderglobal.drawEntityPath(e, entityliving, f);
                }
            }
            if (DebugMode.active || DebugMode.renderFov) {
                for (Object obj : this.minecraft.level.entities) {
                    Entity e = (Entity) obj;
                    if (!(e instanceof LivingEntity)) continue;
                    renderglobal.drawEntityFOV((LivingEntity) e, entityliving, f);
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
    @Overwrite()
    private void method_1846() {
        float f = this.minecraft.level.getRainGradient(1.0f);
        if (!this.minecraft.options.fancyGraphics) {
            f /= 2.0f;
        }
        if (f == 0.0f) {
            return;
        }
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
            if (l1 > j + byte0 || l1 < j - byte0 || ((ExLevel) world).getTemperatureValue(j1, k1) < 0.5) continue;
            float f1 = this.random.nextFloat();
            float f2 = this.random.nextFloat();
            if (i2 <= 0) continue;
            if (Tile.BY_ID[i2].material == Material.LAVA) {
                this.minecraft.particleManager.addParticle(new SmokeParticle(world, (float) j1 + f1, (double) ((float) l1 + 0.1f) - Tile.BY_ID[i2].minY, (float) k1 + f2, 0.0, 0.0, 0.0));
                continue;
            }
            if (this.random.nextInt(++l) == 0) {
                d = (float) j1 + f1;
                d1 = (double) ((float) l1 + 0.1f) - Tile.BY_ID[i2].minY;
                d2 = (float) k1 + f2;
            }
            this.minecraft.particleManager.addParticle(new UnknownParticle(world, (float) j1 + f1, (double) ((float) l1 + 0.1f) - Tile.BY_ID[i2].minY, (float) k1 + f2));
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void renderWeather(float tickDelta) {
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
        boolean j1 = false;
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
                this.random.setSeed(k1 * k1 * 3121 + k1 * 45238971 + i2 * i2 * 418711 + i2 * 13761);
                float f5 = (float) this.field_2351 + tickDelta;
                float f6 = ((float) (this.field_2351 & 0x1FF) + tickDelta) / 512.0f;
                float f7 = this.random.nextFloat() + f5 * 0.01f * (float) this.random.nextGaussian();
                float f8 = this.random.nextFloat() + f5 * (float) this.random.nextGaussian() * 0.001f;
                double d5 = (double) ((float) k1 + 0.5f) - entityliving.x;
                double d6 = (double) ((float) i2 + 0.5f) - entityliving.z;
                float f11 = MathsHelper.sqrt(d5 * d5 + d6 * d6) / (float) i1;
                tessellator.start();
                float f12 = world.getBrightness(k1, i3, i2);
                GL11.glColor4f(f12, f12, f12, ((1.0f - f11 * f11) * 0.3f + 0.5f) * f1);
                tessellator.prevPos(-d * 1.0, -d1 * 1.0, -d2 * 1.0);
                tessellator.vertex(k1 + 0, k3, (double) i2 + 0.5, 0.0f * f3 + f7, (float) k3 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex(k1 + 1, k3, (double) i2 + 0.5, 1.0f * f3 + f7, (float) k3 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex(k1 + 1, i4, (double) i2 + 0.5, 1.0f * f3 + f7, (float) i4 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex(k1 + 0, i4, (double) i2 + 0.5, 0.0f * f3 + f7, (float) i4 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex((double) k1 + 0.5, k3, i2 + 0, 0.0f * f3 + f7, (float) k3 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex((double) k1 + 0.5, k3, i2 + 1, 1.0f * f3 + f7, (float) k3 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex((double) k1 + 0.5, i4, i2 + 1, 1.0f * f3 + f7, (float) i4 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.vertex((double) k1 + 0.5, i4, i2 + 0, 0.0f * f3 + f7, (float) i4 * f3 / 4.0f + f6 * f3 + f8);
                tessellator.prevPos(0.0, 0.0, 0.0);
                tessellator.draw();
            }
        }
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/environment/rain.png"));
        if (this.minecraft.options.fancyGraphics) {
            i1 = 10;
        }
        j1 = false;
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
                this.random.setSeed(l1 * l1 * 3121 + l1 * 45238971 + j2 * j2 * 418711 + j2 * 13761);
                float f4 = ((float) (this.field_2351 + l1 * l1 * 3121 + l1 * 45238971 + j2 * j2 * 418711 + j2 * 13761 & 0x1F) + tickDelta) / 32.0f * (3.0f + this.random.nextFloat());
                double d3 = (double) ((float) l1 + 0.5f) - entityliving.x;
                double d4 = (double) ((float) j2 + 0.5f) - entityliving.z;
                float f9 = MathsHelper.sqrt(d3 * d3 + d4 * d4) / (float) i1;
                tessellator.start();
                float f10 = world.getBrightness(l1, 128, j2) * 0.85f + 0.15f;
                GL11.glColor4f(f10, f10, f10, ((1.0f - f9 * f9) * 0.5f + 0.5f) * f1);
                tessellator.prevPos(-d * 1.0, -d1 * 1.0, -d2 * 1.0);
                tessellator.vertex(l1 + 0, j3, (double) j2 + 0.5, 0.0f * f2, (float) j3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex(l1 + 1, j3, (double) j2 + 0.5, 1.0f * f2, (float) j3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex(l1 + 1, l3, (double) j2 + 0.5, 1.0f * f2, (float) l3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex(l1 + 0, l3, (double) j2 + 0.5, 0.0f * f2, (float) l3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex((double) l1 + 0.5, j3, j2 + 0, 0.0f * f2, (float) j3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex((double) l1 + 0.5, j3, j2 + 1, 1.0f * f2, (float) j3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex((double) l1 + 0.5, l3, j2 + 1, 1.0f * f2, (float) l3 * f2 / 4.0f + f4 * f2);
                tessellator.vertex((double) l1 + 0.5, l3, j2 + 0, 0.0f * f2, (float) l3 * f2 / 4.0f + f4 * f2);
                tessellator.prevPos(0.0, 0.0, 0.0);
                tessellator.draw();
            }
        }
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1843() {
        ScreenScaler scaledresolution = new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, scaledresolution.scaledWidth, scaledresolution.scaledHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void renderSkyBase(float tickDelta) {
        float f7;
        Level world = this.minecraft.level;
        LivingEntity entityliving = this.minecraft.field_2807;
        float f1 = 1.0f / (float) (5 - this.minecraft.options.viewDistance);
        f1 = 1.0f - (float) Math.pow(f1, 0.25);
        Vec3f vec3d = world.method_279(this.minecraft.field_2807, tickDelta);
        float f2 = (float) vec3d.x;
        float f3 = (float) vec3d.y;
        float f4 = (float) vec3d.z;
        Vec3f vec3d1 = world.getSkyColour(tickDelta);
        this.r = (float) vec3d1.x;
        this.g = (float) vec3d1.y;
        this.b = (float) vec3d1.z;
        this.r += (f2 - this.r) * f1;
        this.g += (f3 - this.g) * f1;
        this.b += (f4 - this.b) * f1;
        float f5 = world.getRainGradient(tickDelta);
        if (f5 > 0.0f) {
            float f6 = 1.0f - f5 * 0.5f;
            float f8 = 1.0f - f5 * 0.4f;
            this.r *= f6;
            this.g *= f6;
            this.b *= f8;
        }
        if ((f7 = world.getThunderGradient(tickDelta)) > 0.0f) {
            float f9 = 1.0f - f7 * 0.5f;
            this.r *= f9;
            this.g *= f9;
            this.b *= f9;
        }
        if (this.field_2330) {
            Vec3f vec3d2 = world.method_282(tickDelta);
            this.r = (float) vec3d2.x;
            this.g = (float) vec3d2.y;
            this.b = (float) vec3d2.z;
        } else if (entityliving.isInFluid(Material.WATER)) {
            this.r = 0.02f;
            this.g = 0.02f;
            this.b = 0.2f;
        } else if (entityliving.isInFluid(Material.LAVA)) {
            this.r = 0.6f;
            this.g = 0.1f;
            this.b = 0.0f;
        }
        float f10 = this.field_2338 + (this.field_2339 - this.field_2338) * tickDelta;
        this.r *= f10;
        this.g *= f10;
        this.b *= f10;
        if (this.minecraft.options.anaglyph3d) {
            float f11 = (this.r * 30.0f + this.g * 59.0f + this.b * 11.0f) / 100.0f;
            float f12 = (this.r * 30.0f + this.g * 70.0f) / 100.0f;
            float f13 = (this.r * 30.0f + this.b * 70.0f) / 100.0f;
            this.r = f11;
            this.g = f12;
            this.b = f13;
        }
        GL11.glClearColor(this.r, this.g, this.b, 0.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1842(int i, float f) {
        LivingEntity entityliving = this.minecraft.field_2807;
        GL11.glFog(2918, this.method_1839(this.r, this.g, this.b, 1.0f));
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.field_2330) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.1f);
            float f1 = 1.0f;
            float f4 = 1.0f;
            float f7 = 1.0f;
            if (this.minecraft.options.anaglyph3d) {
                float f10 = (f1 * 30.0f + f4 * 59.0f + f7 * 11.0f) / 100.0f;
                float f13 = (f1 * 30.0f + f4 * 70.0f) / 100.0f;
                float f16 = (f1 * 30.0f + f7 * 70.0f) / 100.0f;
                f1 = f10;
                f4 = f13;
                f7 = f16;
            }
        } else if (entityliving.isInFluid(Material.WATER)) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.1f);
            float f2 = 0.4f;
            float f5 = 0.4f;
            float f8 = 0.9f;
            if (this.minecraft.options.anaglyph3d) {
                float f11 = (f2 * 30.0f + f5 * 59.0f + f8 * 11.0f) / 100.0f;
                float f14 = (f2 * 30.0f + f5 * 70.0f) / 100.0f;
                float f17 = (f2 * 30.0f + f8 * 70.0f) / 100.0f;
                f2 = f11;
                f5 = f14;
                f8 = f17;
            }
        } else if (entityliving.isInFluid(Material.LAVA)) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 2.0f);
            float f3 = 0.4f;
            float f6 = 0.3f;
            float f9 = 0.3f;
            if (this.minecraft.options.anaglyph3d) {
                float f12 = (f3 * 30.0f + f6 * 59.0f + f9 * 11.0f) / 100.0f;
                float f15 = (f3 * 30.0f + f6 * 70.0f) / 100.0f;
                float f18 = (f3 * 30.0f + f9 * 70.0f) / 100.0f;
                f3 = f12;
                f6 = f15;
                f9 = f18;
            }
        } else {
            Level world = this.minecraft.level;
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, ((ExLevel)world).getFogStart(this.field_2350 * 0.25f, f));
            GL11.glFogf(2916, ((ExLevel)world).getFogEnd(this.field_2350, f));
            if (i < 0) {
                GL11.glFogf(2915, ((ExLevel)world).getFogStart(0.0f, f));
                GL11.glFogf(2916, ((ExLevel)world).getFogEnd(0.8f * this.field_2350, f));
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi(34138, 34139);
            }
            if (this.minecraft.level.dimension.hasFog) {
                GL11.glFogf(2915, ((ExLevel)world).getFogStart(0.0f, f));
            }
        }
        GL11.glEnable(2903);
        GL11.glColorMaterial(1028, 4608);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private FloatBuffer method_1839(float f, float f1, float f2, float f3) {
        this.field_2345.clear();
        this.field_2345.put(f).put(f1).put(f2).put(f3);
        this.field_2345.flip();
        return this.field_2345;
    }
}
