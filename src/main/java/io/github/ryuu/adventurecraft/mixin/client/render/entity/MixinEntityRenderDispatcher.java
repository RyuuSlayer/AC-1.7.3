package io.github.ryuu.adventurecraft.mixin.client.render.entity;

import java.util.HashMap;
import java.util.Map;

import io.github.ryuu.adventurecraft.entities.*;
import io.github.ryuu.adventurecraft.models.ModelBat;
import io.github.ryuu.adventurecraft.models.ModelCamera;
import io.github.ryuu.adventurecraft.models.ModelRat;
import io.github.ryuu.adventurecraft.rendering.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.HandItemRenderer;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.client.render.entity.model.ChickenModel;
import net.minecraft.client.render.entity.model.CowModel;
import net.minecraft.client.render.entity.model.PigModel;
import net.minecraft.client.render.entity.model.SheepModel;
import net.minecraft.client.render.entity.model.SkeletonModel;
import net.minecraft.client.render.entity.model.SlimeModel;
import net.minecraft.client.render.entity.model.SquidModel;
import net.minecraft.client.render.entity.model.UnknownEntityModel2;
import net.minecraft.client.render.entity.model.WolfModel;
import net.minecraft.client.render.entity.model.ZombieModel;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Boat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingTile;
import net.minecraft.entity.FishHook;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Lightning;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Minecart;
import net.minecraft.entity.Painting;
import net.minecraft.entity.PrimedTnt;
import net.minecraft.entity.animal.Chicken;
import net.minecraft.entity.animal.Cow;
import net.minecraft.entity.animal.Pig;
import net.minecraft.entity.animal.Sheep;
import net.minecraft.entity.animal.Squid;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.monster.Creeper;
import net.minecraft.entity.monster.Ghast;
import net.minecraft.entity.monster.Giant;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.monster.Slime;
import net.minecraft.entity.monster.Spider;
import net.minecraft.entity.monster.Zombie;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.entity.projectile.Snowball;
import net.minecraft.entity.projectile.ThrownEgg;
import net.minecraft.entity.projectile.ThrownSnowball;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher {

    @Shadow()
    private Map renderers = new HashMap();

    public static EntityRenderDispatcher INSTANCE = new EntityRenderDispatcher();

    private TextRenderer font;

    public static double field_2490;

    public static double field_2491;

    public static double field_2492;

    public TextureManager textureManager;

    public HandItemRenderer field_2494;

    public Level level;

    public LivingEntity entity;

    public float field_2497;

    public float field_2498;

    public GameOptions options;

    public double field_2500;

    public double field_2501;

    public double field_2502;

    private MixinEntityRenderDispatcher() {
        this.renderers.put(Spider.class, (Object) new SpiderEyesRenderer());
        this.renderers.put(Pig.class, (Object) new PigRenderer(new PigModel(), new PigModel(0.5f), 0.7f));
        this.renderers.put(Sheep.class, (Object) new SheepRenderer(new SheepModel(), new UnknownEntityModel2(), 0.7f));
        this.renderers.put(Cow.class, (Object) new CowRenderer(new CowModel(), 0.7f));
        this.renderers.put(Wolf.class, (Object) new WolfRenderer(new WolfModel(), 0.5f));
        this.renderers.put(Chicken.class, (Object) new ChickenRenderer(new ChickenModel(), 0.3f));
        this.renderers.put(Creeper.class, (Object) new CreeperRenderer());
        this.renderers.put(Skeleton.class, (Object) new BipedEntityRenderer(new SkeletonModel(), 0.5f));
        this.renderers.put(EntitySkeletonBoss.class, (Object) new RenderBipedScaled(new SkeletonModel(), 0.5f, 2.5f));
        this.renderers.put(Zombie.class, (Object) new BipedEntityRenderer(new ZombieModel(), 0.5f));
        this.renderers.put(Slime.class, (Object) new SlimeRenderer(new SlimeModel(16), new SlimeModel(0), 0.25f));
        this.renderers.put(Player.class, (Object) new PlayerRenderer());
        this.renderers.put(Giant.class, (Object) new GiantRenderer(new ZombieModel(), 0.5f, 6.0f));
        this.renderers.put(Ghast.class, (Object) new GhastRenderer());
        this.renderers.put(Squid.class, (Object) new SquidRenderer(new SquidModel(), 0.7f));
        this.renderers.put(LivingEntity.class, (Object) new LivingEntityRenderer(new BipedModel(), 0.5f));
        this.renderers.put(Entity.class, (Object) new GenericRenderer());
        this.renderers.put(Painting.class, (Object) new PaintingRenderer());
        this.renderers.put(Arrow.class, (Object) new ArrowRenderer());
        this.renderers.put(ThrownSnowball.class, (Object) new ProjectileRenderer(ItemType.snowball.getTexturePosition(0)));
        this.renderers.put(ThrownEgg.class, (Object) new ProjectileRenderer(ItemType.egg.getTexturePosition(0)));
        this.renderers.put(Snowball.class, (Object) new SnowballRenderer());
        this.renderers.put(ItemEntity.class, (Object) new ItemRenderer());
        this.renderers.put(PrimedTnt.class, (Object) new PrimedTntRenderer());
        this.renderers.put(FallingTile.class, (Object) new FallingTileRenderer());
        this.renderers.put(Minecart.class, (Object) new MinecartRenderer());
        this.renderers.put(Boat.class, (Object) new BoatRenderer());
        this.renderers.put(FishHook.class, (Object) new FishHookRenderer());
        this.renderers.put(Lightning.class, (Object) new LightningRenderer());
        this.renderers.put(EntityBoomerang.class, (Object) new RenderBoomerang());
        this.renderers.put(EntityHookshot.class, (Object) new RenderHookshot());
        this.renderers.put(EntityBomb.class, (Object) new RenderBomb());
        this.renderers.put(EntityBat.class, (Object) new LivingEntityRenderer(new ModelBat(), 0.3f));
        this.renderers.put(EntityRat.class, (Object) new LivingEntityRenderer(new ModelRat(), 0.0f));
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
        EntityRenderer render = (EntityRenderer) this.renderers.get((Object) entityClass);
        if (render == null && entityClass != Entity.class) {
            render = this.get(entityClass.getSuperclass());
            this.renderers.put((Object) entityClass, (Object) render);
        }
        return render;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1917(Level world, TextureManager renderengine, TextRenderer fontrenderer, LivingEntity entityliving, GameOptions gamesettings, float f) {
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
    public void method_1921(Entity entity, float f) {
        double d = entity.prevRenderX + (entity.x - entity.prevRenderX) * (double) f;
        double d1 = entity.prevRenderY + (entity.y - entity.prevRenderY) * (double) f;
        double d2 = entity.prevRenderZ + (entity.z - entity.prevRenderZ) * (double) f;
        float f1 = entity.prevYaw + (entity.yaw - entity.prevYaw) * f;
        float f2 = entity.getBrightnessAtEyes(f);
        GL11.glColor3f((float) f2, (float) f2, (float) f2);
        this.method_1920(entity, d - field_2490, d1 - field_2491, d2 - field_2492, f1, f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1920(Entity entity, double x, double y, double z, float f, float f1) {
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
    public double method_1915(double d, double d1, double d2) {
        double d3 = d - this.field_2500;
        double d4 = d1 - this.field_2501;
        double d5 = d2 - this.field_2502;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }
}
