package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public class MixinTextureBinder {
    public byte[] grid = new byte[262144];
    public int field_1412;
    public boolean render3d = false;
    public int textureId = 0;
    public int field_1415 = 1;
    public int renderMode = 0;

    public MixinTextureBinder(int i) {
        this.field_1412 = i;
    }

    public void onTick(Vec2 texRes) {
    }

    public void bindTexture(TextureManager manager) {
        GL11.glBindTexture(3553, manager.getTextureId(this.getTexture()));
    }

    public String getTexture() {
        if (this.renderMode == 0) {
            return "/terrain.png";
        }
        if (this.renderMode == 1) {
            return "/gui/items.png";
        }
        return "/gui/items.png";
    }
}