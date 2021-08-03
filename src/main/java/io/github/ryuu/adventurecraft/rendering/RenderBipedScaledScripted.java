package io.github.ryuu.adventurecraft.rendering;

import io.github.ryuu.adventurecraft.entities.EntityLivingScript;
import org.lwjgl.opengl.GL11;

public class RenderBipedScaledScripted extends v {
    public RenderBipedScaledScripted(fh modelbase) {
        super(modelbase, 0.5F);
    }

    protected void a(ls entityliving, float f) {
        EntityLivingScript e = (EntityLivingScript)entityliving;
        float width = (1.0F - f) * e.prevWidth + f * e.bg;
        float height = (1.0F - f) * e.prevHeight + f * e.bh;
        width /= 0.6F;
        this.c = width * 0.5F;
        GL11.glScalef(width, height / 1.8F, width);
    }
}
