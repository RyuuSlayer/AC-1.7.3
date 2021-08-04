package io.github.ryuu.adventurecraft.rendering;

import io.github.ryuu.adventurecraft.entities.EntityCamera;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class RenderCamera extends LivingEntityRenderer {
    public RenderCamera(EntityModel modelbase, float f) {
        super(modelbase, f);
    }

    protected void a(LivingEntity entityliving, double d, double d1, double d2) {
        EntityCamera e = (EntityCamera) entityliving;
        a(entityliving, String.format("%.2f", new Object[]{Float.valueOf(e.time)}), d, d1 - 1.5D, d2, 64);
    }

    public void a(Entity entity, double d, double d1, double d2, float f, float f1) {
        if (DebugMode.active)
            super.a(entity, d, d1, d2, f, f1);
    }
}
