package io.github.ryuu.adventurecraft.rendering;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

public class RenderBipedScaled extends BipedEntityRenderer {
    private final float scaling;

    public RenderBipedScaled(BipedModel modelbase, float f, float f1) {
        super(modelbase, f * f1);
        this.scaling = f1;
    }

    protected void a(LivingEntity entityliving, float f) {
        GL11.glScalef(this.scaling, this.scaling, this.scaling);
    }
}
