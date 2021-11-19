package io.github.ryuu.adventurecraft.rendering;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinLivingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;

public class RenderCamera extends LivingEntityRenderer {

    public RenderCamera(EntityModel modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    protected void method_821(MixinLivingEntity entityliving, double d, double d1, double d2) {
        EntityCamera e = (EntityCamera) entityliving;
        this.method_818(entityliving, String.format((String) "%.2f", (Object[]) new Object[] { Float.valueOf((float) e.time) }), d, d1 - 1.5, d2, 64);
    }

    @Override
    public void render(MixinEntity entity, double x, double y, double z, float f, float f1) {
        if (DebugMode.active) {
            super.render(entity, x, y, z, f, f1);
        }
    }
}
