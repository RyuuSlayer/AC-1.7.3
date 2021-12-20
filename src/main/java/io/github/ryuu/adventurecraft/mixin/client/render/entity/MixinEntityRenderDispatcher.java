package io.github.ryuu.adventurecraft.mixin.client.render.entity;

import io.github.ryuu.adventurecraft.entities.*;
import io.github.ryuu.adventurecraft.models.ModelBat;
import io.github.ryuu.adventurecraft.models.ModelCamera;
import io.github.ryuu.adventurecraft.models.ModelRat;
import io.github.ryuu.adventurecraft.rendering.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.client.render.entity.model.SkeletonModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public abstract class MixinEntityRenderDispatcher {

    @Shadow
    private Map<Class<?>, EntityRenderer> renderers;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        Map<Class<?>, EntityRenderer> renderers = new HashMap<>();

        renderers.put(EntitySkeletonBoss.class, new RenderBipedScaled(new SkeletonModel(), 0.5f, 2.5f));
        renderers.put(EntityBoomerang.class, new RenderBoomerang());
        renderers.put(EntityHookshot.class, new RenderHookshot());
        renderers.put(EntityBomb.class, new RenderBomb());
        renderers.put(EntityBat.class, new LivingEntityRenderer(new ModelBat(), 0.3f));
        renderers.put(EntityRat.class, new LivingEntityRenderer(new ModelRat(), 0.0f));
        renderers.put(EntityCamera.class, new RenderCamera(new ModelCamera(), 0.0f));
        renderers.put(EntityNPC.class, new RenderNPC(new BipedModel()));
        renderers.put(EntityLivingScript.class, new RenderBipedScaledScripted(new BipedModel()));

        for (EntityRenderer render : renderers.values()) {
            render.setDispatcher((EntityRenderDispatcher) (Object) this);
        }
        this.renderers.putAll(renderers);
    }
}
