package io.github.ryuu.adventurecraft.mixin.client.texture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.GLAllocator;
import net.minecraft.client.ImageDownloader;
import net.minecraft.client.ImageProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.TexturePackManager;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.client.resource.TexturePack;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextureManager.class)
public class MixinTextureManager {

    @Shadow()
    public static boolean field_1245 = false;

    public HashMap TEXTURE_ID_MAP = new HashMap();

    private HashMap IMAGE_GRID_CACHE = new HashMap();

    private HashMap INT_TO_IMAGE = new HashMap();

    private IntBuffer atlasBuffer = GLAllocator.createIntBuffer(1);

    private ByteBuffer textureGridBuffer = GLAllocator.createByteBuffer(0x100000);

    private List textureBinders = new ArrayList();

    private Map ID_TO_DOWNLOADER = new HashMap();

    private GameOptions gameOptions;

    private boolean clamped = false;

    private boolean blurred = false;

    private TexturePackManager texturePackManager;

    private BufferedImage defaultImage = new BufferedImage(64, 64, 2);

    public File mapDir;

    public ArrayList<String> replacedTextures;

    private HashMap<Integer, net.minecraft.src.Vec2> textureResolutions;

    private HashMap<String, net.minecraft.src.TextureAnimated> textureAnimations;

    public MixinTextureManager(TexturePackManager texturePackManager, GameOptions gameOptions) {
        this.texturePackManager = texturePackManager;
        this.gameOptions = gameOptions;
        Graphics g = this.defaultImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 64, 64);
        g.setColor(Color.BLACK);
        g.drawString("missingtex", 1, 10);
        g.dispose();
        this.replacedTextures = new ArrayList();
        this.textureResolutions = new HashMap();
        this.textureAnimations = new HashMap();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int[] getImageGrid(String id) {
        TexturePack texturepackbase = this.texturePackManager.texturePack;
        int[] ai = (int[]) this.IMAGE_GRID_CACHE.get((Object) id);
        if (ai != null) {
            return ai;
        }
        try {
            int[] ai1 = null;
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
            this.IMAGE_GRID_CACHE.put((Object) id, (Object) ai1);
            return ai1;
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            int[] ai2 = this.getRGBPixels(this.defaultImage);
            this.IMAGE_GRID_CACHE.put((Object) id, (Object) ai2);
            return ai2;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int[] getRGBPixels(BufferedImage image) {
        int i = image.getWidth();
        int j = image.getHeight();
        int[] ai = new int[i * j];
        image.getRGB(0, 0, i, j, ai, 0, i);
        return ai;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int[] getRGBPixels(BufferedImage image, int[] pixels) {
        int i = image.getWidth();
        int j = image.getHeight();
        image.getRGB(0, 0, i, j, pixels, 0, i);
        return pixels;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTextureId(String stringId) {
        TexturePack texturepackbase = this.texturePackManager.texturePack;
        Integer integer = (Integer) this.TEXTURE_ID_MAP.get((Object) stringId);
        if (integer != null) {
            return integer;
        }
        this.atlasBuffer.clear();
        GLAllocator.addTexture(this.atlasBuffer);
        int i = this.atlasBuffer.get(0);
        this.loadTexture(i, stringId);
        this.TEXTURE_ID_MAP.put((Object) stringId, (Object) i);
        return i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void replaceTexture(String texToReplace, String replacement) {
        int texID = this.getTextureId(texToReplace);
        this.loadTexture(texID, replacement);
        if (!this.replacedTextures.contains((Object) texToReplace)) {
            this.replacedTextures.add((Object) texToReplace);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void revertTextures() {
        for (String replacement : this.replacedTextures) {
            Integer integer = (Integer) this.TEXTURE_ID_MAP.get((Object) replacement);
            if (integer == null)
                continue;
            this.loadTexture(integer, replacement);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
            if (Minecraft.minecraftInstance.level != null) {
                image = Minecraft.minecraftInstance.level.loadMapTexture(texName);
            }
            if (image == null) {
                InputStream inputstream = texturepackbase.getResource(texName);
                if (inputstream == null) {
                    File f = new File(texName);
                    if (f.exists()) {
                        image = ImageIO.read((File) f);
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
            this.textureResolutions.put((Object) new Integer(texID), (Object) new Vec2(image.getWidth(), image.getHeight()));
            if (origTexName.startsWith("%clamp%")) {
                this.clamped = false;
            } else if (origTexName.startsWith("%blur%")) {
                this.blurred = false;
            }
            return;
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            this.glLoadImageWithId(this.defaultImage, texID);
            return;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public BufferedImage getTextureImage(String texName) throws IOException {
        TexturePack texturepackbase = this.texturePackManager.texturePack;
        return this.readStream(texturepackbase.getResource(texName));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private BufferedImage method_1101(BufferedImage image) {
        int i = image.getWidth() / 16;
        BufferedImage bufferedimage1 = new BufferedImage(16, image.getHeight() * i, 2);
        Graphics g = bufferedimage1.getGraphics();
        for (int j = 0; j < i; ++j) {
            g.drawImage((Image) image, -j * 16, j * image.getHeight(), null);
        }
        g.dispose();
        return bufferedimage1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int glLoadImage(BufferedImage image) {
        this.atlasBuffer.clear();
        GLAllocator.addTexture(this.atlasBuffer);
        int i = this.atlasBuffer.get(0);
        this.glLoadImageWithId(image, i);
        this.INT_TO_IMAGE.put((Object) i, (Object) image);
        return i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void glLoadImageWithId(BufferedImage image, int id) {
        GL11.glBindTexture((int) 3553, (int) id);
        if (field_1245) {
            GL11.glTexParameteri((int) 3553, (int) 10241, (int) 9986);
            GL11.glTexParameteri((int) 3553, (int) 10240, (int) 9728);
        } else {
            GL11.glTexParameteri((int) 3553, (int) 10241, (int) 9728);
            GL11.glTexParameteri((int) 3553, (int) 10240, (int) 9728);
        }
        if (this.blurred) {
            GL11.glTexParameteri((int) 3553, (int) 10241, (int) 9729);
            GL11.glTexParameteri((int) 3553, (int) 10240, (int) 9729);
        }
        if (this.clamped) {
            GL11.glTexParameteri((int) 3553, (int) 10242, (int) 10496);
            GL11.glTexParameteri((int) 3553, (int) 10243, (int) 10496);
        } else {
            GL11.glTexParameteri((int) 3553, (int) 10242, (int) 10497);
            GL11.glTexParameteri((int) 3553, (int) 10243, (int) 10497);
        }
        int j = image.getWidth();
        int k = image.getHeight();
        int[] ai = new int[j * k];
        byte[] abyte0 = new byte[j * k * 4];
        image.getRGB(0, 0, j, k, ai, 0, j);
        for (int l = 0; l < ai.length; ++l) {
            int j1 = ai[l] >> 24 & 0xFF;
            int l1 = ai[l] >> 16 & 0xFF;
            int j2 = ai[l] >> 8 & 0xFF;
            int l2 = ai[l] & 0xFF;
            if (this.gameOptions != null && this.gameOptions.anaglyph3d) {
                int j3 = (l1 * 30 + j2 * 59 + l2 * 11) / 100;
                int l3 = (l1 * 30 + j2 * 70) / 100;
                int j4 = (l1 * 30 + l2 * 70) / 100;
                l1 = j3;
                j2 = l3;
                l2 = j4;
            }
            abyte0[l * 4 + 0] = (byte) l1;
            abyte0[l * 4 + 1] = (byte) j2;
            abyte0[l * 4 + 2] = (byte) l2;
            abyte0[l * 4 + 3] = (byte) j1;
        }
        if (this.textureGridBuffer.capacity() < j * k * 4) {
            this.textureGridBuffer = GLAllocator.createByteBuffer(j * k * 4);
        }
        this.textureGridBuffer.clear();
        this.textureGridBuffer.put(abyte0);
        this.textureGridBuffer.position(0).limit(abyte0.length);
        GL11.glTexImage2D((int) 3553, (int) 0, (int) 6408, (int) j, (int) k, (int) 0, (int) 6408, (int) 5121, (ByteBuffer) this.textureGridBuffer);
        if (field_1245) {
            for (int i1 = 1; i1 <= 4; ++i1) {
                int k1 = j >> i1 - 1;
                int i2 = j >> i1;
                int k2 = k >> i1;
                for (int i3 = 0; i3 < i2; ++i3) {
                    for (int k3 = 0; k3 < k2; ++k3) {
                        int i4 = this.textureGridBuffer.getInt((i3 * 2 + 0 + (k3 * 2 + 0) * k1) * 4);
                        int k4 = this.textureGridBuffer.getInt((i3 * 2 + 1 + (k3 * 2 + 0) * k1) * 4);
                        int l4 = this.textureGridBuffer.getInt((i3 * 2 + 1 + (k3 * 2 + 1) * k1) * 4);
                        int i5 = this.textureGridBuffer.getInt((i3 * 2 + 0 + (k3 * 2 + 1) * k1) * 4);
                        int j5 = this.method_1098(this.method_1098(i4, k4), this.method_1098(l4, i5));
                        this.textureGridBuffer.putInt((i3 + k3 * i2) * 4, j5);
                    }
                }
                GL11.glTexImage2D((int) 3553, (int) i1, (int) 6408, (int) i2, (int) k2, (int) 0, (int) 6408, (int) 5121, (ByteBuffer) this.textureGridBuffer);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1095(int[] grid, int i, int j, int textureId) {
        GL11.glBindTexture((int) 3553, (int) textureId);
        if (field_1245) {
            GL11.glTexParameteri((int) 3553, (int) 10241, (int) 9986);
            GL11.glTexParameteri((int) 3553, (int) 10240, (int) 9728);
        } else {
            GL11.glTexParameteri((int) 3553, (int) 10241, (int) 9728);
            GL11.glTexParameteri((int) 3553, (int) 10240, (int) 9728);
        }
        if (this.blurred) {
            GL11.glTexParameteri((int) 3553, (int) 10241, (int) 9729);
            GL11.glTexParameteri((int) 3553, (int) 10240, (int) 9729);
        }
        if (this.clamped) {
            GL11.glTexParameteri((int) 3553, (int) 10242, (int) 10496);
            GL11.glTexParameteri((int) 3553, (int) 10243, (int) 10496);
        } else {
            GL11.glTexParameteri((int) 3553, (int) 10242, (int) 10497);
            GL11.glTexParameteri((int) 3553, (int) 10243, (int) 10497);
        }
        byte[] abyte0 = new byte[i * j * 4];
        for (int l = 0; l < grid.length; ++l) {
            int i1 = grid[l] >> 24 & 0xFF;
            int j1 = grid[l] >> 16 & 0xFF;
            int k1 = grid[l] >> 8 & 0xFF;
            int l1 = grid[l] & 0xFF;
            if (this.gameOptions != null && this.gameOptions.anaglyph3d) {
                int i2 = (j1 * 30 + k1 * 59 + l1 * 11) / 100;
                int j2 = (j1 * 30 + k1 * 70) / 100;
                int k2 = (j1 * 30 + l1 * 70) / 100;
                j1 = i2;
                k1 = j2;
                l1 = k2;
            }
            abyte0[l * 4 + 0] = (byte) j1;
            abyte0[l * 4 + 1] = (byte) k1;
            abyte0[l * 4 + 2] = (byte) l1;
            abyte0[l * 4 + 3] = (byte) i1;
        }
        this.textureGridBuffer.clear();
        this.textureGridBuffer.put(abyte0);
        this.textureGridBuffer.position(0).limit(abyte0.length);
        GL11.glTexSubImage2D((int) 3553, (int) 0, (int) 0, (int) 0, (int) i, (int) j, (int) 6408, (int) 5121, (ByteBuffer) this.textureGridBuffer);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void removeTexture(int id) {
        this.INT_TO_IMAGE.remove((Object) id);
        this.atlasBuffer.clear();
        this.atlasBuffer.put(id);
        this.atlasBuffer.flip();
        GL11.glDeleteTextures((IntBuffer) this.atlasBuffer);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getTextureId(String downloaderId, String stringId) {
        ImageDownloader threaddownloadimagedata = (ImageDownloader) this.ID_TO_DOWNLOADER.get((Object) downloaderId);
        if (threaddownloadimagedata != null && threaddownloadimagedata.image != null && !threaddownloadimagedata.loaded) {
            if (threaddownloadimagedata.pointer < 0) {
                threaddownloadimagedata.pointer = this.glLoadImage(threaddownloadimagedata.image);
            } else {
                this.glLoadImageWithId(threaddownloadimagedata.image, threaddownloadimagedata.pointer);
            }
            threaddownloadimagedata.loaded = true;
        }
        if (threaddownloadimagedata == null || threaddownloadimagedata.pointer < 0) {
            if (stringId == null) {
                return -1;
            }
            return this.getTextureId(stringId);
        }
        return threaddownloadimagedata.pointer;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public ImageDownloader getImageDownloader(String stringId, ImageProcessor imageProcessor) {
        ImageDownloader threaddownloadimagedata = (ImageDownloader) this.ID_TO_DOWNLOADER.get((Object) stringId);
        if (threaddownloadimagedata == null) {
            this.ID_TO_DOWNLOADER.put((Object) stringId, (Object) new ImageDownloader(stringId, imageProcessor));
        } else {
            ++threaddownloadimagedata.downloading;
        }
        return threaddownloadimagedata;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void removeDownloaded(String stringId) {
        ImageDownloader threaddownloadimagedata = (ImageDownloader) this.ID_TO_DOWNLOADER.get((Object) stringId);
        if (threaddownloadimagedata != null) {
            --threaddownloadimagedata.downloading;
            if (threaddownloadimagedata.downloading == 0) {
                if (threaddownloadimagedata.pointer >= 0) {
                    this.removeTexture(threaddownloadimagedata.pointer);
                }
                this.ID_TO_DOWNLOADER.remove((Object) stringId);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void add(TextureBinder textureBinder) {
        this.textureBinders.add((Object) textureBinder);
        Vec2 texRes = this.getTextureResolution(textureBinder.getTexture());
        if (texRes == null) {
            this.getTextureId(textureBinder.getTexture());
            texRes = this.getTextureResolution(textureBinder.getTexture());
        }
        textureBinder.onTick(texRes);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void tick() {
        for (int i = 0; i < this.textureBinders.size(); ++i) {
            TextureBinder texturefx = (TextureBinder) this.textureBinders.get(i);
            Vec2 texRes = this.getTextureResolution(texturefx.getTexture());
            texturefx.render3d = this.gameOptions.anaglyph3d;
            texturefx.onTick(texRes);
            this.textureGridBuffer.clear();
            this.textureGridBuffer.put(texturefx.grid);
            this.textureGridBuffer.position(0).limit(texturefx.grid.length);
            texturefx.bindTexture(this);
            int w = texRes.x / 16;
            int h = texRes.y / 16;
            for (int k = 0; k < texturefx.field_1415; ++k) {
                for (int i1 = 0; i1 < texturefx.field_1415; ++i1) {
                    GL11.glTexSubImage2D((int) 3553, (int) 0, (int) (texturefx.field_1412 % 16 * w + k * w), (int) (texturefx.field_1412 / 16 * h + i1 * h), (int) w, (int) h, (int) 6408, (int) 5121, (ByteBuffer) this.textureGridBuffer);
                    if (!field_1245)
                        continue;
                    for (int k1 = 1; k1 <= 4; ++k1) {
                        int i2 = 16 >> k1 - 1;
                        int k2 = 16 >> k1;
                        for (int i3 = 0; i3 < k2; ++i3) {
                            for (int k3 = 0; k3 < k2; ++k3) {
                                int i4 = this.textureGridBuffer.getInt((i3 * 2 + 0 + (k3 * 2 + 0) * i2) * 4);
                                int k4 = this.textureGridBuffer.getInt((i3 * 2 + 1 + (k3 * 2 + 0) * i2) * 4);
                                int i5 = this.textureGridBuffer.getInt((i3 * 2 + 1 + (k3 * 2 + 1) * i2) * 4);
                                int k5 = this.textureGridBuffer.getInt((i3 * 2 + 0 + (k3 * 2 + 1) * i2) * 4);
                                int l5 = this.method_1086(this.method_1086(i4, k4), this.method_1086(i5, k5));
                                this.textureGridBuffer.putInt((i3 + k3 * k2) * 4, l5);
                            }
                        }
                        GL11.glTexSubImage2D((int) 3553, (int) k1, (int) (texturefx.field_1412 % 16 * k2), (int) (texturefx.field_1412 / 16 * k2), (int) k2, (int) k2, (int) 6408, (int) 5121, (ByteBuffer) this.textureGridBuffer);
                    }
                }
            }
        }
        for (int j = 0; j < this.textureBinders.size(); ++j) {
            TextureBinder texturefx1 = (TextureBinder) this.textureBinders.get(j);
            if (texturefx1.textureId <= 0)
                continue;
            this.textureGridBuffer.clear();
            this.textureGridBuffer.put(texturefx1.grid);
            this.textureGridBuffer.position(0).limit(texturefx1.grid.length);
            GL11.glBindTexture((int) 3553, (int) texturefx1.textureId);
            GL11.glTexSubImage2D((int) 3553, (int) 0, (int) 0, (int) 0, (int) 16, (int) 16, (int) 6408, (int) 5121, (ByteBuffer) this.textureGridBuffer);
            if (!field_1245)
                continue;
            for (int l = 1; l <= 4; ++l) {
                int j1 = 16 >> l - 1;
                int l1 = 16 >> l;
                for (int j2 = 0; j2 < l1; ++j2) {
                    for (int l2 = 0; l2 < l1; ++l2) {
                        int j3 = this.textureGridBuffer.getInt((j2 * 2 + 0 + (l2 * 2 + 0) * j1) * 4);
                        int l3 = this.textureGridBuffer.getInt((j2 * 2 + 1 + (l2 * 2 + 0) * j1) * 4);
                        int j4 = this.textureGridBuffer.getInt((j2 * 2 + 1 + (l2 * 2 + 1) * j1) * 4);
                        int l4 = this.textureGridBuffer.getInt((j2 * 2 + 0 + (l2 * 2 + 1) * j1) * 4);
                        int j5 = this.method_1086(this.method_1086(j3, l3), this.method_1086(j4, l4));
                        this.textureGridBuffer.putInt((j2 + l2 * l1) * 4, j5);
                    }
                }
                GL11.glTexSubImage2D((int) 3553, (int) l, (int) 0, (int) 0, (int) l1, (int) l1, (int) 6408, (int) 5121, (ByteBuffer) this.textureGridBuffer);
            }
        }
        this.updateTextureAnimations();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int method_1086(int grid, int grid2) {
        int k = (grid & 0xFF000000) >> 24 & 0xFF;
        int l = (grid2 & 0xFF000000) >> 24 & 0xFF;
        return (k + l >> 1 << 24) + ((grid & 0xFEFEFE) + (grid2 & 0xFEFEFE) >> 1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int method_1098(int i, int j) {
        int k = (i & 0xFF000000) >> 24 & 0xFF;
        int l = (j & 0xFF000000) >> 24 & 0xFF;
        int c = 255;
        if (k + l == 0) {
            k = 1;
            l = 1;
            c = 0;
        }
        int i1 = (i >> 16 & 0xFF) * k;
        int j1 = (i >> 8 & 0xFF) * k;
        int k1 = (i & 0xFF) * k;
        int l1 = (j >> 16 & 0xFF) * l;
        int i2 = (j >> 8 & 0xFF) * l;
        int j2 = (j & 0xFF) * l;
        int k2 = (i1 + l1) / (k + l);
        int l2 = (j1 + i2) / (k + l);
        int i3 = (k1 + j2) / (k + l);
        return c << 24 | k2 << 16 | l2 << 8 | i3;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void reload() {
        TexturePack texturepackbase = this.texturePackManager.texturePack;
        Iterator iterator = this.INT_TO_IMAGE.keySet().iterator();
        while (iterator.hasNext()) {
            int i = (Integer) iterator.next();
            BufferedImage bufferedimage = (BufferedImage) this.INT_TO_IMAGE.get((Object) i);
            this.glLoadImageWithId(bufferedimage, i);
        }
        for (ImageDownloader threaddownloadimagedata : this.ID_TO_DOWNLOADER.values()) {
            threaddownloadimagedata.loaded = false;
        }
        for (String s : this.TEXTURE_ID_MAP.keySet()) {
            int j = (Integer) this.TEXTURE_ID_MAP.get((Object) s);
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
                this.getRGBPixels(bufferedimage2, (int[]) this.IMAGE_GRID_CACHE.get((Object) s1));
                this.blurred = false;
                this.clamped = false;
            } catch (IOException ioexception1) {
                ioexception1.printStackTrace();
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private BufferedImage readStream(InputStream stream) throws IOException {
        BufferedImage bufferedimage = ImageIO.read((InputStream) stream);
        stream.close();
        return bufferedimage;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void bindTexture(int pointer) {
        if (pointer < 0) {
            return;
        }
        GL11.glBindTexture((int) 3553, (int) pointer);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Vec2 getTextureResolution(String texName) {
        Integer i = (Integer) this.TEXTURE_ID_MAP.get((Object) texName);
        if (i != null) {
            return (Vec2) this.textureResolutions.get((Object) i);
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void clearTextureAnimations() {
        this.textureAnimations.clear();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void registerTextureAnimation(String name, TextureAnimated animTex) {
        this.textureAnimations.put((Object) name, (Object) animTex);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void unregisterTextureAnimation(String name) {
        this.textureAnimations.remove((Object) name);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void updateTextureAnimations() {
        for (TextureAnimated animTex : this.textureAnimations.values()) {
            animTex.onTick();
            this.textureGridBuffer.clear();
            this.textureGridBuffer.put(animTex.imageData);
            this.textureGridBuffer.position(0).limit(animTex.imageData.length);
            GL11.glBindTexture((int) 3553, (int) this.getTextureId(animTex.getTexture()));
            GL11.glTexSubImage2D((int) 3553, (int) 0, (int) animTex.x, (int) animTex.y, (int) animTex.width, (int) animTex.height, (int) 6408, (int) 5121, (ByteBuffer) this.textureGridBuffer);
        }
    }
}
