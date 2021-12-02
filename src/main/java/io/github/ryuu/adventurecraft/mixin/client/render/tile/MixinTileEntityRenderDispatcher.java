package io.github.ryuu.adventurecraft.mixin.client.render.tile;

import io.github.ryuu.adventurecraft.entities.tile.*;
import net.minecraft.client.render.entity.tile.MobSpawnerRenderer;
import net.minecraft.client.render.entity.tile.PistonRenderer;
import net.minecraft.client.render.entity.tile.SignRenderer;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.client.render.tile.TileEntityRenderDispatcher;
import net.minecraft.tile.entity.MobSpawner;
import net.minecraft.tile.entity.Piston;
import net.minecraft.tile.entity.Sign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(TileEntityRenderDispatcher.class)
public class MixinTileEntityRenderDispatcher {

    @Shadow
    public static TileEntityRenderDispatcher INSTANCE;

    @Shadow
    private Map<Class<?>, TileEntityRenderer> renderers;

    private MixinTileEntityRenderDispatcher() {
        Map<Class<?>, TileEntityRenderer> renderers = new HashMap<>();

        renderers.put(Sign.class, new SignRenderer());
        renderers.put(MobSpawner.class, new MobSpawnerRenderer());
        renderers.put(Piston.class, new PistonRenderer());
        renderers.put(TileEntityTrigger.class, new TileEntityMinMaxRenderer(1.0f, 0.5882f, 0.0f));
        renderers.put(TileEntityTriggerInverter.class, new TileEntityMinMaxRenderer(1.0f, 1.0f, 0.0f));
        renderers.put(TileEntityTriggerMemory.class, new TileEntityMinMaxRenderer(0.0f, 1.0f, 0.0f));
        renderers.put(TileEntityTimer.class, new TileEntityMinMaxRenderer(0.4f, 0.17647f, 0.56863f));
        renderers.put(TileEntityRedstoneTrigger.class, new TileEntityMinMaxRenderer(1.0f, 0.0f, 0.0f));
        renderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        renderers.put(TileEntityStore.class, new TileEntityStoreRenderer());
        renderers.put(TileEntityEffect.class, new TileEntityEffectRenderer());

        for (TileEntityRenderer renderer : renderers.values()) {
            renderer.setRenderDispatcher(INSTANCE);
        }
        this.renderers.putAll(renderers);
    }
}
