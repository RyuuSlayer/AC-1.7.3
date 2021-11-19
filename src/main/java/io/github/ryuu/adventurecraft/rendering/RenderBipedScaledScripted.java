package io.github.ryuu.adventurecraft.rendering;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.opengl.GL11
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

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
