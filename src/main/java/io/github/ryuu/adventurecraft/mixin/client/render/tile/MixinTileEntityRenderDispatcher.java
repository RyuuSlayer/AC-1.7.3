package io.github.ryuu.adventurecraft.mixin.client.render.tile;

import java.util.HashMap;
import java.util.Map;

import io.github.ryuu.adventurecraft.entities.tile.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.tile.MobSpawnerRenderer;
import net.minecraft.client.render.entity.tile.PistonRenderer;
import net.minecraft.client.render.entity.tile.SignRenderer;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.client.render.tile.TileEntityRenderDispatcher;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.tile.entity.MobSpawner;
import net.minecraft.tile.entity.Piston;
import net.minecraft.tile.entity.Sign;
import net.minecraft.tile.entity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TileEntityRenderDispatcher.class)
public class MixinTileEntityRenderDispatcher {

    @Shadow()
    private Map renderers = new HashMap();

    public static TileEntityRenderDispatcher INSTANCE = new TileEntityRenderDispatcher();

    private TextRenderer textRenderer;

    public static double renderOffsetX;

    public static double renderOffsetY;

    public static double renderOffsetZ;

    public TextureManager textureManager;

    public Level level;

    public LivingEntity cameraEntity;

    public float yaw;

    public float pitch;

    public double x;

    public double y;

    public double z;

    private MixinTileEntityRenderDispatcher() {
        this.renderers.put(Sign.class, (Object) new SignRenderer());
        this.renderers.put(MobSpawner.class, (Object) new MobSpawnerRenderer());
        this.renderers.put(Piston.class, (Object) new PistonRenderer());
        this.renderers.put(TileEntityTrigger.class, (Object) new TileEntityMinMaxRenderer(1.0f, 0.5882f, 0.0f));
        this.renderers.put(TileEntityTriggerInverter.class, (Object) new TileEntityMinMaxRenderer(1.0f, 1.0f, 0.0f));
        this.renderers.put(TileEntityTriggerMemory.class, (Object) new TileEntityMinMaxRenderer(0.0f, 1.0f, 0.0f));
        this.renderers.put(TileEntityTimer.class, (Object) new TileEntityMinMaxRenderer(0.4f, 0.17647f, 0.56863f));
        this.renderers.put(TileEntityRedstoneTrigger.class, (Object) new TileEntityMinMaxRenderer(1.0f, 0.0f, 0.0f));
        this.renderers.put(TileEntityMobSpawner.class, (Object) new TileEntityMobSpawnerRenderer());
        this.renderers.put(TileEntityStore.class, (Object) new TileEntityStoreRenderer());
        this.renderers.put(TileEntityEffect.class, (Object) new TileEntityEffectRenderer());
        for (TileEntityRenderer tileentityspecialrenderer : this.renderers.values()) {
            tileentityspecialrenderer.setRenderDispatcher(this);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public TileEntityRenderer getRenderer(Class clazz) {
        TileEntityRenderer tileentityspecialrenderer = (TileEntityRenderer) this.renderers.get((Object) clazz);
        if (tileentityspecialrenderer == null && clazz != TileEntity.class) {
            tileentityspecialrenderer = this.getRenderer(clazz.getSuperclass());
            this.renderers.put((Object) clazz, (Object) tileentityspecialrenderer);
        }
        return tileentityspecialrenderer;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void refresh(Level level, TextureManager textureManager, TextRenderer textRenderer, LivingEntity cameraEntity, float tickDelta) {
        if (this.level != level) {
            this.refreshLevel(level);
        }
        this.textureManager = textureManager;
        this.cameraEntity = cameraEntity;
        this.textRenderer = textRenderer;
        this.yaw = cameraEntity.prevYaw + (cameraEntity.yaw - cameraEntity.prevYaw) * tickDelta;
        this.pitch = cameraEntity.prevPitch + (cameraEntity.pitch - cameraEntity.prevPitch) * tickDelta;
        this.x = cameraEntity.prevRenderX + (cameraEntity.x - cameraEntity.prevRenderX) * (double) tickDelta;
        this.y = cameraEntity.prevRenderY + (cameraEntity.y - cameraEntity.prevRenderY) * (double) tickDelta;
        this.z = cameraEntity.prevRenderZ + (cameraEntity.z - cameraEntity.prevRenderZ) * (double) tickDelta;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderTileEntity(TileEntity tileEntity, float tickDelta) {
        if (tileEntity.squaredDistanceTo(this.x, this.y, this.z) < 4096.0) {
            float f1 = this.level.getBrightness(tileEntity.x, tileEntity.y, tileEntity.z);
            GL11.glColor3f((float) f1, (float) f1, (float) f1);
            this.renderTileEntity(tileEntity, (double) tileEntity.x - renderOffsetX, (double) tileEntity.y - renderOffsetY, (double) tileEntity.z - renderOffsetZ, tickDelta);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderTileEntity(TileEntity tileEntity, double x, double y, double z, float tickDelta) {
        TileEntityRenderer tileentityspecialrenderer = this.getRenderer(tileEntity);
        if (tileentityspecialrenderer != null) {
            tileentityspecialrenderer.render(tileEntity, x, y, z, tickDelta);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void refreshLevel(Level level) {
        this.level = level;
        for (TileEntityRenderer tileentityspecialrenderer : this.renderers.values()) {
            if (tileentityspecialrenderer == null)
                continue;
            tileentityspecialrenderer.refreshLevel(level);
        }
    }
}
