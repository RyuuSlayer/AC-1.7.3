package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.util.Vec2;
import org.lwjgl.opengl.GL11;

public class TextureBinder {
    public byte[] a;

    public int b;

    public boolean c;

    public int d;

    public int e;

    public int f;

    public TextureBinder(int i) {
        this.a = new byte[262144];
        this.c = false;
        this.d = 0;
        this.e = 1;
        this.f = 0;
        this.b = i;
    }

    public void onTick(Vec2 texRes) {
    }

    public void a(TextureManager renderengine) {
        GL11.glBindTexture(3553, renderengine.b(getTexture()));
    }

    public String getTexture() {
        if (this.f == 0)
            return "/terrain.png";
        if (this.f == 1)
            return "/gui/items.png";
        return "/gui/items.png";
    }
}
