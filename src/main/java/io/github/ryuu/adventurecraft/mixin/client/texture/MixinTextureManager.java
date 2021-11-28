package io.github.ryuu.adventurecraft.mixin.client.texture;

import io.github.ryuu.adventurecraft.extensions.client.render.ExTextureBinder;
import io.github.ryuu.adventurecraft.extensions.client.texture.ExTextureManager;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.TextureAnimated;
import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.*;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.client.resource.TexturePack;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.*;

@Mixin(TextureManager.class)
public abstract class MixinTextureManager implements ExTextureManager {

    @Shadow
    public static boolean field_1245 = false;

    @Shadow
    private final HashMap IMAGE_GRID_CACHE = new HashMap();

    @Shadow
    private final HashMap INT_TO_IMAGE = new HashMap();

    @Shadow
    private final IntBuffer atlasBuffer = GLAllocator.createIntBuffer(1);

    @Shadow
    private final List textureBinders = new ArrayList();

    @Shadow
    private final Map ID_TO_DOWNLOADER = new HashMap();

    @Shadow
    private GameOptions gameOptions;

    @Shadow
    private TexturePackManager texturePackManager;

    @Shadow
    private final BufferedImage defaultImage = new BufferedImage(64, 64, 2);

    @Shadow
    private HashMap TEXTURE_ID_MAP = new HashMap();

    @Shadow
    private ByteBuffer textureGridBuffer = GLAllocator.createByteBuffer(0x100000);

    @Shadow
    private boolean clamped = false;

    @Shadow
    private boolean blurred = false;

    @Shadow public abstract int getTextureId(String string);

    @Shadow protected abstract BufferedImage readStream(InputStream inputStream);

    @Shadow public abstract void glLoadImageWithId(BufferedImage bufferedImage, int i);

    @Shadow protected abstract BufferedImage method_1101(BufferedImage bufferedImage);

    private HashMap<Integer, Vec2> textureResolutions;
    private HashMap<String, TextureAnimated> textureAnimations;
    public File mapDir;
    public ArrayList<String> replacedTextures;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void TextureManager(TexturePackManager arg1, GameOptions par2, CallbackInfo ci) {
        this.replacedTextures = new ArrayList();
        this.textureResolutions = new HashMap();
        this.textureAnimations = new HashMap();
    }


    @Inject(method = "getTextureId(Ljava/lang/String;)I", at = @At(value = "INVOKE", target = "Ljava/nio/IntBuffer;get(I)I", ordinal = 0), cancellable = true)
    public void getTextureId(String stringId, CallbackInfoReturnable<Integer> cir) {
        int i = this.atlasBuffer.get(0);
        this.adventurecraft$loadTexture(i, stringId);
        this.TEXTURE_ID_MAP.put(stringId, i);
        cir.setReturnValue(i);
    }



    @Override
    public void adventurecraft$replaceTexture(String texToReplace, String replacement) {
        int texID = this.getTextureId(texToReplace);
        this.adventurecraft$loadTexture(texID, replacement);
        if (!this.replacedTextures.contains(texToReplace)) {
            this.replacedTextures.add(texToReplace);
        }
    }


    @Override
    public void adventurecraft$revertTextures() {
        for (String replacement : this.replacedTextures) {
            Integer integer = (Integer) this.TEXTURE_ID_MAP.get(replacement);
            if (integer == null) continue;
            this.adventurecraft$loadTexture(integer, replacement);
        }
    }



    @Override
    public void adventurecraft$loadTexture(int texID, String texName) {
        String origTexName = texName;
        try {
            TexturePack texturepackbase = this.texturePackManager.texturePack;
            if (texName.startsWith("##")) {
                texName = texName.substring(2);
            } else if (texName.startsWith("%clamp%")) {
                this.clamped = true;
                texName = texName.substring(7);
            } else if (texName.startsWith("%blur%")) {
                this.blurred = true;
                texName = texName.substring(6);
            }
            BufferedImage image = null;
            if (AccessMinecraft.getInstance().level != null) {
                image = ((ExLevel) AccessMinecraft.getInstance().level).loadMapTexture(texName);
            }
            if (image == null) {
                InputStream inputstream = texturepackbase.getResource(texName);
                if (inputstream == null) {
                    File f = new File(texName);
                    if (f.exists()) {
                        image = ImageIO.read(f);
                    }
                    if (image == null) {
                        image = this.defaultImage;
                    }
                } else {
                    image = this.readStream(inputstream);
                }
            }
            if (origTexName.startsWith("##")) {
                image = this.method_1101(image);
            }
            this.glLoadImageWithId(image, texID);
            this.textureResolutions.put(texID, new Vec2(image.getWidth(), image.getHeight()));
            if (origTexName.startsWith("%clamp%")) {
                this.clamped = false;
            } else if (origTexName.startsWith("%blur%")) {
                this.blurred = false;
            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            this.glLoadImageWithId(this.defaultImage, texID);
        }
    }


    @Override
    public BufferedImage adventurecraft$getTextureImage(String texName) {
        TexturePack texturepackbase = this.texturePackManager.texturePack;
        return this.readStream(texturepackbase.getResource(texName));
    }



    @Inject(method = "glLoadImageWithId", at = @At(value = "INVOKE", target = "Ljava/nio/ByteBuffer;clear()Ljava/nio/Buffer;"))
    private void glLoadImageWithId(BufferedImage image, int par2, CallbackInfo ci) {
        int j = image.getWidth();
        int k = image.getHeight();
        if (this.textureGridBuffer.capacity() < j * k * 4) {
            this.textureGridBuffer = GLAllocator.createByteBuffer(j * k * 4);
        }
    }

    @Redirect(method = "add", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextureBinder;setup()V"))
    private void removeArgSetupCall(TextureBinder instance) {

    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTexSubImage2D(IIIIIIIILjava/nio/ByteBuffer;)V", ordinal = 0))
    private void glTexSubImage2DRedirectNothing(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) {

    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTexSubImage2D(IIIIIIIILjava/nio/ByteBuffer;)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void fixSize(CallbackInfo ci, int var1, int var3, int var4) {
        TextureBinder texturefx = (TextureBinder) textureBinders.get(var1);
        Vec2 texRes = this.adventurecraft$getTextureResolution(((ExTextureBinder) texturefx).adventurecraft$getTexture());
        int w = texRes.x / 16;
        int h = texRes.y / 16;
        GL11.glTexSubImage2D(3553, 0, texturefx.field_1412 % 16 * w + var3 * w, texturefx.field_1412 / 16 * h + var4 * h, w, h, 6408, 5121, this.textureGridBuffer);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        this.adventurecraft$updateTextureAnimations();
    }

    @Override
    public Vec2 adventurecraft$getTextureResolution(String texName) {
        Integer i = (Integer) this.TEXTURE_ID_MAP.get(texName);
        if (i != null) {
            return this.textureResolutions.get(i);
        }
        return null;
    }

    @Override
    public void adventurecraft$clearTextureAnimations() {
        this.textureAnimations.clear();
    }


    @Override
    public void adventurecraft$registerTextureAnimation(String name, TextureAnimated animTex) {
        this.textureAnimations.put(name, animTex);
    }

    @Override
    public void adventurecraft$unregisterTextureAnimation(String name) {
        this.textureAnimations.remove(name);
    }


    @Override
    public void adventurecraft$updateTextureAnimations() {
        for (TextureAnimated animTex : this.textureAnimations.values()) {
            animTex.onTick();
            this.textureGridBuffer.clear();
            this.textureGridBuffer.put(animTex.imageData);
            this.textureGridBuffer.position(0).limit(animTex.imageData.length);
            GL11.glBindTexture(3553, this.getTextureId(animTex.getTexture()));
            GL11.glTexSubImage2D(3553, 0, animTex.x, animTex.y, animTex.width, animTex.height, 6408, 5121, this.textureGridBuffer);
        }
    }
}
