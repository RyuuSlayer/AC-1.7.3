package io.github.ryuu.adventurecraft.rendering;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import org.lwjgl.opengl.GL11;

public class RenderBipedScaledScripted extends BipedEntityRenderer {

    public RenderBipedScaledScripted(BipedModel modelbase) {
        super(modelbase, 0.5f);
    }

    @Override
    protected void method_823(MixinLivingEntity entityliving, float f) {
        EntityLivingScript e = (EntityLivingScript) entityliving;
        float width = (1.0f - f) * e.prevWidth + f * e.width;
        float height = (1.0f - f) * e.prevHeight + f * e.height;
        this.field_2678 = (width /= 0.6f) * 0.5f;
        GL11.glScalef(width, height / 1.8f, width);
    }
}
