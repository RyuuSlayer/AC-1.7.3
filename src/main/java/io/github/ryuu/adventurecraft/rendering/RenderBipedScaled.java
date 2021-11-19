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
import io.github.ryuu.adventurecraft.mixin.item.MixinLivingEntity;

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
