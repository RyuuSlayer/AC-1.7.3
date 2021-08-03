package io.github.ryuu.adventurecraft.rendering;

import org.lwjgl.opengl.GL11;

public class RenderBipedScaled extends v {
    private final float scaling;

    public RenderBipedScaled(fh modelbase, float f, float f1) {
        super(modelbase, f * f1);
        this.scaling = f1;
    }

    protected void a(ls entityliving, float f) {
        GL11.glScalef(this.scaling, this.scaling, this.scaling);
    }
}
