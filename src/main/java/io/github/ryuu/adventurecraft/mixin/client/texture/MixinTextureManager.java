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
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
public abstract class MixinTextureManager implements AccessTextureManager, ExTextureManager {

    @Shadow
    public static boolean field_1245;

    @Shadow
    private HashMap<String, int[]> IMAGE_GRID_CACHE;

    @Shadow
    private HashMap<Integer, BufferedImage> INT_TO_IMAGE;

    @Shadow
    private IntBuffer atlasBuffer;

    @Shadow
    private List<TextureBinder> textureBinders;

    @Shadow
    private Map<String, ImageDownloader> ID_TO_DOWNLOADER;

    @Shadow
    private GameOptions gameOptions;

    @Shadow
    private TexturePackManager texturePackManager;

    @Shadow
    private BufferedImage defaultImage;

    private HashMap<Integer, Vec2> textureResolutions;
    private HashMap<String, TextureAnimated> textureAnimations;

    @Shadow
    private HashMap<String, Integer> TEXTURE_ID_MAP;

    public File mapDir;

    public ArrayList<String> replacedTextures;

    @Shadow
    private ByteBuffer textureGridBuffer;

    @Shadow
    private boolean clamped;

    @Shadow
    private boolean blurred;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(TexturePackManager texturePackManager, GameOptions gameOptions, CallbackInfo ci) {
        this.replacedTextures = new ArrayList<>();
        this.textureResolutions = new HashMap<>();
        this.textureAnimations = new HashMap<>();
    }

    @Shadow
    public abstract void glLoadImageWithId(BufferedImage bufferedImage, int i);

    @Shadow
    protected abstract BufferedImage readStream(InputStream inputStream);

    @Shadow
    protected abstract int[] getRGBPixels(BufferedImage bufferedImage);

    @Shadow
    protected abstract int[] getRGBPixels(BufferedImage bufferedImage, int[] is);

    @Shadow
    protected abstract BufferedImage method_1101(BufferedImage bufferedImage);

    @Shadow
    protected abstract int method_1086(int i, int j);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public int[] getImageGrid(String id) {
        TexturePack texturepackbase = this.texturePackManager.texturePack;
        int[] ai = this.IMAGE_GRID_CACHE.get(id);
        if (ai != null) {
            return ai;
        }
        try {
            int[] ai1;
            if (id.startsWith("##")) {
                ai1 = this.getRGBPixels(this.method_1101(this.readStream(texturepackbase.getResource(id.substring(2)))));
            } else if (id.startsWith("%clamp%")) {
                this.clamped = true;
                ai1 = this.getRGBPixels(this.readStream(texturepackbase.getResource(id.substring(7))));
                this.clamped = false;
            } else if (id.startsWith("%blur%")) {
                this.blurred = true;
                ai1 = this.getRGBPixels(this.readStream(texturepackbase.getResource(id.substring(6))));
                this.blurred = false;
            } else {
                InputStream inputstream = texturepackbase.getResource(id);
                ai1 = inputstream == null ? this.getRGBPixels(this.defaultImage) : this.getRGBPixels(this.readStream(inputstream));
            }
            this.IMAGE_GRID_CACHE.put(id, ai1);
            return ai1;
        } catch (Exception ioexception) {
            ioexception.printStackTrace();
            int[] ai2 = this.getRGBPixels(this.defaultImage);
            this.IMAGE_GRID_CACHE.put(id, ai2);
            return ai2;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public int getTextureId(String stringId) {
        Integer integer = this.TEXTURE_ID_MAP.get(stringId);
        if (integer != null) {
            return integer;
        }
        this.atlasBuffer.clear();
        GLAllocator.addTexture(this.atlasBuffer);
        int i = this.atlasBuffer.get(0);
        this.loadTexture(i, stringId);
        this.TEXTURE_ID_MAP.put(stringId, i);
        return i;
    }

    @Override
    public void replaceTexture(String texToReplace, String replacement) {
        int texID = this.getTextureId(texToReplace);
        this.loadTexture(texID, replacement);
        if (!this.replacedTextures.contains(texToReplace)) {
            this.replacedTextures.add(texToReplace);
        }
    }

    @Override
    public void revertTextures() {
        for (String replacement : this.replacedTextures) {
            Integer integer = this.TEXTURE_ID_MAP.get(replacement);
            if (integer == null) continue;
            this.loadTexture(integer, replacement);
        }
    }

    @Override
    public void loadTexture(int texID, String texName) {
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
    public BufferedImage getTextureImage(String texName) {
        TexturePack texturepackbase = this.texturePackManager.texturePack;
        return this.readStream(texturepackbase.getResource(texName));
    }

    @Inject(method = "glLoadImageWithId", at = @At(
            value = "INVOKE",
            target = "Ljava/nio/ByteBuffer;clear()Ljava/nio/Buffer;",
            shift = At.Shift.BEFORE))
    public void glLoadImageWithId(BufferedImage image, int id, CallbackInfo ci) {
        int j = image.getWidth();
        int k = image.getHeight();

        if (this.textureGridBuffer.capacity() < j * k * 4) {
            this.textureGridBuffer = GLAllocator.createByteBuffer(j * k * 4);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void add(TextureBinder textureBinder) {
        this.textureBinders.add(textureBinder);
        ExTextureBinder exBinder = (ExTextureBinder) textureBinder;
        Vec2 texRes = this.getTextureResolution(exBinder.getTexture());
        if (texRes == null) {
            this.getTextureId(exBinder.getTexture());
            texRes = this.getTextureResolution(exBinder.getTexture());
        }
        exBinder.onTick(texRes);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void tick() {
        for (TextureBinder texturefx : this.textureBinders) {
            Vec2 texRes = this.getTextureResolution(((ExTextureBinder) texturefx).getTexture());
            texturefx.render3d = this.gameOptions.anaglyph3d;
            ((ExTextureBinder) texturefx).onTick(texRes);
            this.textureGridBuffer.clear();
            this.textureGridBuffer.put(texturefx.grid);
            this.textureGridBuffer.position(0).limit(texturefx.grid.length);
            texturefx.bindTexture((TextureManager) (Object) this);
            int w = texRes.x / 16;
            int h = texRes.y / 16;
            for (int k = 0; k < texturefx.field_1415; ++k) {
                for (int i1 = 0; i1 < texturefx.field_1415; ++i1) {
                    GL11.glTexSubImage2D(3553, 0, texturefx.field_1412 % 16 * w + k * w, texturefx.field_1412 / 16 * h + i1 * h, w, h, 6408, 5121, this.textureGridBuffer);
                    if (!field_1245) continue;
                    for (int k1 = 1; k1 <= 4; ++k1) {
                        int i2 = 16 >> k1 - 1;
                        int k2 = 16 >> k1;
                        for (int i3 = 0; i3 < k2; ++i3) {
                            for (int k3 = 0; k3 < k2; ++k3) {
                                int i4 = this.textureGridBuffer.getInt((i3 * 2 + (k3 * 2) * i2) * 4);
                                int k4 = this.textureGridBuffer.getInt((i3 * 2 + 1 + (k3 * 2) * i2) * 4);
                                int i5 = this.textureGridBuffer.getInt((i3 * 2 + 1 + (k3 * 2 + 1) * i2) * 4);
                                int k5 = this.textureGridBuffer.getInt((i3 * 2 + (k3 * 2 + 1) * i2) * 4);
                                int l5 = this.method_1086(this.method_1086(i4, k4), this.method_1086(i5, k5));
                                this.textureGridBuffer.putInt((i3 + k3 * k2) * 4, l5);
                            }
                        }
                        GL11.glTexSubImage2D(3553, k1, texturefx.field_1412 % 16 * k2, texturefx.field_1412 / 16 * k2, k2, k2, 6408, 5121, this.textureGridBuffer);
                    }
                }
            }
        }
        for (TextureBinder texturefx1 : this.textureBinders) {
            if (texturefx1.textureId <= 0) continue;
            this.textureGridBuffer.clear();
            this.textureGridBuffer.put(texturefx1.grid);
            this.textureGridBuffer.position(0).limit(texturefx1.grid.length);
            GL11.glBindTexture(3553, texturefx1.textureId);
            GL11.glTexSubImage2D(3553, 0, 0, 0, 16, 16, 6408, 5121, this.textureGridBuffer);
            if (!field_1245) continue;
            for (int l = 1; l <= 4; ++l) {
                int j1 = 16 >> l - 1;
                int l1 = 16 >> l;
                for (int j2 = 0; j2 < l1; ++j2) {
                    for (int l2 = 0; l2 < l1; ++l2) {
                        int j3 = this.textureGridBuffer.getInt((j2 * 2 + (l2 * 2) * j1) * 4);
                        int l3 = this.textureGridBuffer.getInt((j2 * 2 + 1 + (l2 * 2) * j1) * 4);
                        int j4 = this.textureGridBuffer.getInt((j2 * 2 + 1 + (l2 * 2 + 1) * j1) * 4);
                        int l4 = this.textureGridBuffer.getInt((j2 * 2 + (l2 * 2 + 1) * j1) * 4);
                        int j5 = this.method_1086(this.method_1086(j3, l3), this.method_1086(j4, l4));
                        this.textureGridBuffer.putInt((j2 + l2 * l1) * 4, j5);
                    }
                }
                GL11.glTexSubImage2D(3553, l, 0, 0, l1, l1, 6408, 5121, this.textureGridBuffer);
            }
        }
        this.updateTextureAnimations();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void reload() {
        TexturePack texturepackbase = this.texturePackManager.texturePack;
        for (int i : this.INT_TO_IMAGE.keySet()) {
            BufferedImage bufferedimage = this.INT_TO_IMAGE.get(i);
            this.glLoadImageWithId(bufferedimage, i);
        }
        for (ImageDownloader threaddownloadimagedata : this.ID_TO_DOWNLOADER.values()) {
            threaddownloadimagedata.loaded = false;
        }
        for (String s : this.TEXTURE_ID_MAP.keySet()) {
            int j = this.TEXTURE_ID_MAP.get(s);
            this.loadTexture(j, s);
        }
        for (String s1 : this.IMAGE_GRID_CACHE.keySet()) {
            try {
                BufferedImage bufferedimage2;
                if (s1.startsWith("##")) {
                    bufferedimage2 = this.method_1101(this.readStream(texturepackbase.getResource(s1.substring(2))));
                } else if (s1.startsWith("%clamp%")) {
                    this.clamped = true;
                    bufferedimage2 = this.readStream(texturepackbase.getResource(s1.substring(7)));
                } else if (s1.startsWith("%blur%")) {
                    this.blurred = true;
                    bufferedimage2 = this.readStream(texturepackbase.getResource(s1.substring(6)));
                } else {
                    bufferedimage2 = this.readStream(texturepackbase.getResource(s1));
                }
                this.getRGBPixels(bufferedimage2, this.IMAGE_GRID_CACHE.get(s1));
                this.blurred = false;
                this.clamped = false;
            } catch (Exception ioexception1) {
                ioexception1.printStackTrace();
            }
        }
    }

    @Override
    public Vec2 getTextureResolution(String texName) {
        Integer i = this.TEXTURE_ID_MAP.get(texName);
        if (i != null) {
            return this.textureResolutions.get(i);
        }
        return null;
    }

    @Override
    public void clearTextureAnimations() {
        this.textureAnimations.clear();
    }

    @Override
    public void registerTextureAnimation(String name, TextureAnimated animTex) {
        this.textureAnimations.put(name, animTex);
    }

    @Override
    public void unregisterTextureAnimation(String name) {
        this.textureAnimations.remove(name);
    }

    @Override
    public void updateTextureAnimations() {
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