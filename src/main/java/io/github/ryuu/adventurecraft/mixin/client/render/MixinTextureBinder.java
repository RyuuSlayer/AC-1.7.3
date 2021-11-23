package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextureBinder.class)
public class MixinTextureBinder {

    @Shadow()
    public byte[] grid = new byte[262144];

    public int field_1412;

    public boolean render3d = false;

    public int textureId = 0;

    public int field_1415 = 1;

    public int renderMode = 0;

    public MixinTextureBinder(int i) {
        this.field_1412 = i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onTick(Vec2 texRes) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void bindTexture(TextureManager manager) {
        GL11.glBindTexture(3553, manager.getTextureId(this.getTexture()));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
