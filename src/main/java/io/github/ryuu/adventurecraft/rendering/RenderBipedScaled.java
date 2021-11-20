package io.github.ryuu.adventurecraft.rendering;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

public class RenderBipedScaled extends BipedEntityRenderer {

    private float scaling;

    public RenderBipedScaled(BipedModel modelbase, float f, float f1) {
        super(modelbase, f * f1);
        this.scaling = f1;
    }

    @Override
    protected void method_823(MixinLivingEntity entityliving, float f) {
        GL11.glScalef((float) this.scaling, (float) this.scaling, (float) this.scaling);
    }
}
