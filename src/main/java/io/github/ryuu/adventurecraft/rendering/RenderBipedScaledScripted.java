package io.github.ryuu.adventurecraft.rendering;

import io.github.ryuu.adventurecraft.entities.EntityLivingScript;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

public class RenderBipedScaledScripted extends BipedEntityRenderer {
    public RenderBipedScaledScripted(BipedModel modelbase) {
        super(modelbase, 0.5F);
    }

    protected void a(LivingEntity entityliving, float f) {
        EntityLivingScript e = (EntityLivingScript) entityliving;
        float width = (1.0F - f) * e.prevWidth + f * e.width;
        float height = (1.0F - f) * e.prevHeight + f * e.height;
        width /= 0.6F;
        this.field_2678 = width * 0.5F;
        GL11.glScalef(width, height / 1.8F, width);
    }
}