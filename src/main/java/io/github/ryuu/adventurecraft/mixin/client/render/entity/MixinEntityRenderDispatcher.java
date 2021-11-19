package io.github.ryuu.adventurecraft.mixin.client.render.entity;

import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.entity.Boat;
import net.minecraft.entity.Lightning;
import net.minecraft.entity.Minecart;
import net.minecraft.entity.PrimedTnt;
import net.minecraft.entity.animal.*;
import net.minecraft.entity.monster.Giant;
import net.minecraft.entity.monster.Spider;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher {

    public static MixinEntityRenderDispatcher INSTANCE = new MixinEntityRenderDispatcher();
    public static double field_2490;
    public static double field_2491;
    public static double field_2492;
    public MixinTextureManager textureManager;
    public MixinHandItemRenderer field_2494;
    public MixinLevel level;
    public MixinLivingEntity entity;
    public float field_2497;
    public float field_2498;
    public MixinGameOptions options;
    public double field_2500;
    public double field_2501;
    public double field_2502;
    @Shadow()
    private Map renderers = new HashMap();
    private MixinTextRenderer font;

    private MixinEntityRenderDispatcher() {
        this.renderers.put(Spider.class, new SpiderEyesRenderer());
        this.renderers.put(Pig.class, new PigRenderer(new PigModel(), new PigModel(0.5f), 0.7f));
        this.renderers.put(Sheep.class, new SheepRenderer(new SheepModel(), new UnknownEntityModel2(), 0.7f));
        this.renderers.put(Cow.class, new CowRenderer(new CowModel(), 0.7f));
        this.renderers.put(MixinWolf.class, new WolfRenderer(new WolfModel(), 0.5f));
        this.renderers.put(Chicken.class, new ChickenRenderer(new ChickenModel(), 0.3f));
        this.renderers.put(MixinCreeper.class, new CreeperRenderer());
        this.renderers.put(MixinSkeleton.class, new BipedEntityRenderer(new SkeletonModel(), 0.5f));
        this.renderers.put(EntitySkeletonBoss.class, (Object) new RenderBipedScaled(new SkeletonModel(), 0.5f, 2.5f));
        this.renderers.put(MixinZombie.class, new BipedEntityRenderer(new ZombieModel(), 0.5f));
        this.renderers.put(MixinSlime.class, new SlimeRenderer(new SlimeModel(16), new SlimeModel(0), 0.25f));
        this.renderers.put(MixinPlayer.class, new MixinPlayerRenderer());
        this.renderers.put(Giant.class, new GiantRenderer(new ZombieModel(), 0.5f, 6.0f));
        this.renderers.put(MixinGhast.class, new GhastRenderer());
        this.renderers.put(Squid.class, new SquidRenderer(new SquidModel(), 0.7f));
        this.renderers.put(MixinLivingEntity.class, new LivingEntityRenderer(new BipedModel(), 0.5f));
        this.renderers.put(MixinEntity.class, new GenericRenderer());
        this.renderers.put(MixinPainting.class, new PaintingRenderer());
        this.renderers.put(MixinArrow.class, new ArrowRenderer());
        this.renderers.put(MixinThrownSnowball.class, new ProjectileRenderer(ItemType.snowball.getTexturePosition(0)));
        this.renderers.put(MixinThrownEgg.class, new ProjectileRenderer(ItemType.egg.getTexturePosition(0)));
        this.renderers.put(MixinSnowball.class, new SnowballRenderer());
        this.renderers.put(MixinItemEntity.class, new MixinItemRenderer());
        this.renderers.put(PrimedTnt.class, new PrimedTntRenderer());
        this.renderers.put(MixinFallingTile.class, new MixinFallingTileRenderer());
        this.renderers.put(Minecart.class, new MinecartRenderer());
        this.renderers.put(Boat.class, new BoatRenderer());
        this.renderers.put(MixinFishHook.class, new FishHookRenderer());
        this.renderers.put(Lightning.class, new LightningRenderer());
        this.renderers.put(EntityBoomerang.class, (Object) new RenderBoomerang());
        this.renderers.put(EntityHookshot.class, (Object) new RenderHookshot());
        this.renderers.put(EntityBomb.class, (Object) new RenderBomb());
        this.renderers.put(EntityBat.class, new LivingEntityRenderer(new ModelBat(), 0.3f));
        this.renderers.put(EntityRat.class, new LivingEntityRenderer(new ModelRat(), 0.0f));
        this.renderers.put(EntityCamera.class, (Object) new RenderCamera(new ModelCamera(), 0.0f));
        this.renderers.put(EntityNPC.class, (Object) new RenderNPC(new BipedModel()));
        this.renderers.put(EntityLivingScript.class, (Object) new RenderBipedScaledScripted(new BipedModel()));
        for (EntityRenderer render : this.renderers.values()) {
            render.setDispatcher(this);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public EntityRenderer get(Class entityClass) {
        EntityRenderer render = (EntityRenderer) this.renderers.get(entityClass);
        if (render == null && entityClass != MixinEntity.class) {
            render = this.get(entityClass.getSuperclass());
            this.renderers.put(entityClass, render);
        }
        return render;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public EntityRenderer get(MixinEntity entity) {
        return this.get(entity.getClass());
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1917(MixinLevel world, MixinTextureManager renderengine, MixinTextRenderer fontrenderer, MixinLivingEntity entityliving, MixinGameOptions gamesettings, float f) {
        this.level = world;
        this.textureManager = renderengine;
        this.options = gamesettings;
        this.entity = entityliving;
        this.font = fontrenderer;
        if (entityliving.isSleeping()) {
            int i = world.getTileId(MathsHelper.floor(entityliving.x), MathsHelper.floor(entityliving.y), MathsHelper.floor(entityliving.z));
            if (i == Tile.BED.id) {
                int j = world.getTileMeta(MathsHelper.floor(entityliving.x), MathsHelper.floor(entityliving.y), MathsHelper.floor(entityliving.z));
                int k = j & 3;
                this.field_2497 = k * 90 + 180;
                this.field_2498 = 0.0f;
            }
        } else {
            this.field_2497 = entityliving.prevYaw + (entityliving.yaw - entityliving.prevYaw) * f;
            this.field_2498 = entityliving.prevPitch + (entityliving.pitch - entityliving.prevPitch) * f;
        }
        this.field_2500 = entityliving.prevRenderX + (entityliving.x - entityliving.prevRenderX) * (double) f;
        this.field_2501 = entityliving.prevRenderY + (entityliving.y - entityliving.prevRenderY) * (double) f;
        this.field_2502 = entityliving.prevRenderZ + (entityliving.z - entityliving.prevRenderZ) * (double) f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1921(MixinEntity entity, float f) {
        double d = entity.prevRenderX + (entity.x - entity.prevRenderX) * (double) f;
        double d1 = entity.prevRenderY + (entity.y - entity.prevRenderY) * (double) f;
        double d2 = entity.prevRenderZ + (entity.z - entity.prevRenderZ) * (double) f;
        float f1 = entity.prevYaw + (entity.yaw - entity.prevYaw) * f;
        float f2 = entity.getBrightnessAtEyes(f);
        GL11.glColor3f(f2, f2, f2);
        this.method_1920(entity, d - field_2490, d1 - field_2491, d2 - field_2492, f1, f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1920(MixinEntity entity, double x, double y, double z, float f, float f1) {
        EntityRenderer render = this.get(entity);
        if (render != null) {
            render.render(entity, x, y, z, f, f1);
            render.method_2032(entity, x, y, z, f, f1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setLevel(MixinLevel level) {
        this.level = level;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double method_1915(double d, double d1, double d2) {
        double d3 = d - this.field_2500;
        double d4 = d1 - this.field_2501;
        double d5 = d2 - this.field_2502;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinTextRenderer getTextRenderer() {
        return this.font;
    }
}
