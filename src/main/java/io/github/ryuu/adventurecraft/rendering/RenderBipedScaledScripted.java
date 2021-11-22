package io.github.ryuu.adventurecraft.rendering;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL11;
import io.github.ryuu.adventurecraft.entities.EntityLivingScript;

public class RenderBipedScaledScripted extends BipedEntityRenderer {

    public RenderBipedScaledScripted(BipedModel modelbase) {
        super(modelbase, 0.5f);
    }

    @Override
    protected void method_823(LivingEntity entityliving, float f) {
        EntityLivingScript e = (EntityLivingScript) entityliving;
        float width = (1.0f - f) * e.prevWidth + f * e.width;
        float height = (1.0f - f) * e.prevHeight + f * e.height;
        this.field_2678 = (width /= 0.6f) * 0.5f;
        GL11.glScalef((float) width, (float) (height / 1.8f), (float) width);
    }
}
