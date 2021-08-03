package io.github.ryuu.adventurecraft.overrides;

import java.awt.Color;
import java.awt.Graphics;
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

import io.github.ryuu.adventurecraft.util.TextureAnimated;
import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class ji {
    public ji(ik texturepacklist, kv gamesettings) {
        this.b = new HashMap<>();
        this.c = new HashMap<>();
        this.d = new HashMap<>();
        this.e = ge.d(1);
        this.f = ge.c(1048576);
        this.g = new ArrayList();
        this.h = new HashMap<>();
        this.j = false;
        this.k = false;
        this.m = new BufferedImage(64, 64, 2);
        this.l = texturepacklist;
        this.i = gamesettings;
        Graphics g = this.m.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 64, 64);
        g.setColor(Color.BLACK);
        g.drawString("missingtex", 1, 10);
        g.dispose();
        this.replacedTextures = new ArrayList<>();
        this.textureResolutions = new HashMap<>();
        this.textureAnimations = new HashMap<>();
    }

    public int[] a(String s) {
        i texturepackbase = this.l.a;
        int[] ai = (int[]) this.c.get(s);
        if (ai != null)
            return ai;
        try {
            int[] ai1 = null;
            if (s.startsWith("##")) {
                ai1 = b(c(a(texturepackbase.a(s.substring(2)))));
            } else if (s.startsWith("%clamp%")) {
                this.j = true;
                ai1 = b(a(texturepackbase.a(s.substring(7))));
                this.j = false;
            } else if (s.startsWith("%blur%")) {
                this.k = true;
                ai1 = b(a(texturepackbase.a(s.substring(6))));
                this.k = false;
            } else {
                InputStream inputstream = texturepackbase.a(s);
                if (inputstream == null) {
                    ai1 = b(this.m);
                } else {
                    ai1 = b(a(inputstream));
                }
            }
            this.c.put(s, ai1);
            return ai1;
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            int[] ai2 = b(this.m);
            this.c.put(s, ai2);
            return ai2;
        }
    }

    private int[] b(BufferedImage bufferedimage) {
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int[] ai = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, ai, 0, i);
        return ai;
    }

    private int[] a(BufferedImage bufferedimage, int[] ai) {
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        bufferedimage.getRGB(0, 0, i, j, ai, 0, i);
        return ai;
    }

    public int b(String s) {
        i texturepackbase = this.l.a;
        Integer integer = (Integer) this.b.get(s);
        if (integer != null)
            return integer.intValue();
        this.e.clear();
        ge.a(this.e);
        int i = this.e.get(0);
        loadTexture(i, s);
        this.b.put(s, Integer.valueOf(i));
        return i;
    }

    public void replaceTexture(String texToReplace, String replacement) {
        int texID = b(texToReplace);
        loadTexture(texID, replacement);
        if (!this.replacedTextures.contains(texToReplace))
            this.replacedTextures.add(texToReplace);
    }

    public void revertTextures() {
        for (String replacement : this.replacedTextures) {
            Integer integer = (Integer) this.b.get(replacement);
            if (integer != null)
                loadTexture(integer.intValue(), replacement);
        }
    }

    public void loadTexture(int texID, String texName) {
        String origTexName = texName;
        try {
            i texturepackbase = this.l.a;
            if (texName.startsWith("##")) {
                texName = texName.substring(2);
            } else if (texName.startsWith("%clamp%")) {
                this.j = true;
                texName = texName.substring(7);
            } else if (texName.startsWith("%blur%")) {
                this.k = true;
                texName = texName.substring(6);
            }
            BufferedImage image = null;
            if (Minecraft.minecraftInstance.f != null)
                image = Minecraft.minecraftInstance.f.loadMapTexture(texName);
            if (image == null) {
                InputStream inputstream = texturepackbase.a(texName);
                if (inputstream == null) {
                    File f = new File(texName);
                    if (f.exists())
                        image = ImageIO.read(f);
                    if (image == null)
                        image = this.m;
                } else {
                    image = a(inputstream);
                }
            }
            if (origTexName.startsWith("##"))
                image = c(image);
            a(image, texID);
            this.textureResolutions.put(new Integer(texID), new Vec2(image.getWidth(), image.getHeight()));
            if (origTexName.startsWith("%clamp%")) {
                this.j = false;
            } else if (origTexName.startsWith("%blur%")) {
                this.k = false;
            }
            return;
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            a(this.m, texID);
            return;
        }
    }

    public BufferedImage getTextureImage(String texName) throws IOException {
        i texturepackbase = this.l.a;
        return a(texturepackbase.a(texName));
    }

    private BufferedImage c(BufferedImage bufferedimage) {
        int i = bufferedimage.getWidth() / 16;
        BufferedImage bufferedimage1 = new BufferedImage(16, bufferedimage.getHeight() * i, 2);
        Graphics g = bufferedimage1.getGraphics();
        for (int j = 0; j < i; j++)
            g.drawImage(bufferedimage, -j * 16, j * bufferedimage.getHeight(), null);
        g.dispose();
        return bufferedimage1;
    }

    public int a(BufferedImage bufferedimage) {
        this.e.clear();
        ge.a(this.e);
        int i = this.e.get(0);
        a(bufferedimage, i);
        this.d.put(Integer.valueOf(i), bufferedimage);
        return i;
    }

    public void a(BufferedImage bufferedimage, int i) {
        GL11.glBindTexture(3553, i);
        if (a) {
            GL11.glTexParameteri(3553, 10241, 9986);
            GL11.glTexParameteri(3553, 10240, 9728);
        } else {
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
        }
        if (this.k) {
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
        }
        if (this.j) {
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
        } else {
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
        }
        int j = bufferedimage.getWidth();
        int k = bufferedimage.getHeight();
        int[] ai = new int[j * k];
        byte[] abyte0 = new byte[j * k * 4];
        bufferedimage.getRGB(0, 0, j, k, ai, 0, j);
        for (int l = 0; l < ai.length; l++) {
            int j1 = ai[l] >> 24 & 0xFF;
            int l1 = ai[l] >> 16 & 0xFF;
            int j2 = ai[l] >> 8 & 0xFF;
            int l2 = ai[l] & 0xFF;
            if (this.i != null && this.i.g) {
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
        if (this.f.capacity() < j * k * 4)
            this.f = ge.c(j * k * 4);
        this.f.clear();
        this.f.put(abyte0);
        this.f.position(0).limit(abyte0.length);
        GL11.glTexImage2D(3553, 0, 6408, j, k, 0, 6408, 5121, this.f);
        if (a)
            for (int i1 = 1; i1 <= 4; i1++) {
                int k1 = j >> i1 - 1;
                int i2 = j >> i1;
                int k2 = k >> i1;
                for (int i3 = 0; i3 < i2; i3++) {
                    for (int k3 = 0; k3 < k2; k3++) {
                        int i4 = this.f.getInt((i3 * 2 + 0 + (k3 * 2 + 0) * k1) * 4);
                        int k4 = this.f.getInt((i3 * 2 + 1 + (k3 * 2 + 0) * k1) * 4);
                        int l4 = this.f.getInt((i3 * 2 + 1 + (k3 * 2 + 1) * k1) * 4);
                        int i5 = this.f.getInt((i3 * 2 + 0 + (k3 * 2 + 1) * k1) * 4);
                        int j5 = b(b(i4, k4), b(l4, i5));
                        this.f.putInt((i3 + k3 * i2) * 4, j5);
                    }
                }
                GL11.glTexImage2D(3553, i1, 6408, i2, k2, 0, 6408, 5121, this.f);
            }
    }

    public void a(int[] ai, int i, int j, int k) {
        GL11.glBindTexture(3553, k);
        if (a) {
            GL11.glTexParameteri(3553, 10241, 9986);
            GL11.glTexParameteri(3553, 10240, 9728);
        } else {
            GL11.glTexParameteri(3553, 10241, 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
        }
        if (this.k) {
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
        }
        if (this.j) {
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
        } else {
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
        }
        byte[] abyte0 = new byte[i * j * 4];
        for (int l = 0; l < ai.length; l++) {
            int i1 = ai[l] >> 24 & 0xFF;
            int j1 = ai[l] >> 16 & 0xFF;
            int k1 = ai[l] >> 8 & 0xFF;
            int l1 = ai[l] & 0xFF;
            if (this.i != null && this.i.g) {
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
        this.f.clear();
        this.f.put(abyte0);
        this.f.position(0).limit(abyte0.length);
        GL11.glTexSubImage2D(3553, 0, 0, 0, i, j, 6408, 5121, this.f);
    }

    public void a(int i) {
        this.d.remove(Integer.valueOf(i));
        this.e.clear();
        this.e.put(i);
        this.e.flip();
        GL11.glDeleteTextures(this.e);
    }

    public int a(String s, String s1) {
        ek threaddownloadimagedata = (ek) this.h.get(s);
        if (threaddownloadimagedata != null && threaddownloadimagedata.a != null && !threaddownloadimagedata.d) {
            if (threaddownloadimagedata.c < 0) {
                threaddownloadimagedata.c = a(threaddownloadimagedata.a);
            } else {
                a(threaddownloadimagedata.a, threaddownloadimagedata.c);
            }
            threaddownloadimagedata.d = true;
        }
        if (threaddownloadimagedata == null || threaddownloadimagedata.c < 0) {
            if (s1 == null)
                return -1;
            return b(s1);
        }
        return threaddownloadimagedata.c;
    }

    public ek a(String s, nf imagebuffer) {
        ek threaddownloadimagedata = (ek) this.h.get(s);
        if (threaddownloadimagedata == null) {
            this.h.put(s, new ek(s, imagebuffer));
        } else {
            threaddownloadimagedata.b++;
        }
        return threaddownloadimagedata;
    }

    public void c(String s) {
        ek threaddownloadimagedata = (ek) this.h.get(s);
        if (threaddownloadimagedata != null) {
            threaddownloadimagedata.b--;
            if (threaddownloadimagedata.b == 0) {
                if (threaddownloadimagedata.c >= 0)
                    a(threaddownloadimagedata.c);
                this.h.remove(s);
            }
        }
    }

    public void a(TextureBinder texturefx) {
        this.g.add(texturefx);
        Vec2 texRes = getTextureResolution(texturefx.getTexture());
        if (texRes == null) {
            b(texturefx.getTexture());
            texRes = getTextureResolution(texturefx.getTexture());
        }
        texturefx.onTick(texRes);
    }

    public void a() {
        for (int i = 0; i < this.g.size(); i++) {
            TextureBinder texturefx = this.g.get(i);
            Vec2 texRes = getTextureResolution(texturefx.getTexture());
            texturefx.c = this.i.g;
            texturefx.onTick(texRes);
            this.f.clear();
            this.f.put(texturefx.a);
            this.f.position(0).limit(texturefx.a.length);
            texturefx.a(this);
            int w = texRes.x / 16;
            int h = texRes.y / 16;
            for (int k = 0; k < texturefx.e; k++) {
                for (int i1 = 0; i1 < texturefx.e; i1++) {
                    GL11.glTexSubImage2D(3553, 0, texturefx.b % 16 * w + k * w, texturefx.b / 16 * h + i1 * h, w, h, 6408, 5121, this.f);
                    if (a) {
                        int k1 = 1;
                        while (k1 <= 4) {
                            int i2 = 16 >> k1 - 1;
                            int k2 = 16 >> k1;
                            for (int i3 = 0; i3 < k2; i3++) {
                                for (int k3 = 0; k3 < k2; k3++) {
                                    int i4 = this.f.getInt((i3 * 2 + 0 + (k3 * 2 + 0) * i2) * 4);
                                    int k4 = this.f.getInt((i3 * 2 + 1 + (k3 * 2 + 0) * i2) * 4);
                                    int i5 = this.f.getInt((i3 * 2 + 1 + (k3 * 2 + 1) * i2) * 4);
                                    int k5 = this.f.getInt((i3 * 2 + 0 + (k3 * 2 + 1) * i2) * 4);
                                    int l5 = a(a(i4, k4), a(i5, k5));
                                    this.f.putInt((i3 + k3 * k2) * 4, l5);
                                }
                            }
                            GL11.glTexSubImage2D(3553, k1, texturefx.b % 16 * k2, texturefx.b / 16 * k2, k2, k2, 6408, 5121, this.f);
                            k1++;
                        }
                    }
                }
            }
        }
        for (int j = 0; j < this.g.size(); j++) {
            TextureBinder texturefx1 = this.g.get(j);
            if (texturefx1.d > 0) {
                this.f.clear();
                this.f.put(texturefx1.a);
                this.f.position(0).limit(texturefx1.a.length);
                GL11.glBindTexture(3553, texturefx1.d);
                GL11.glTexSubImage2D(3553, 0, 0, 0, 16, 16, 6408, 5121, this.f);
                if (a) {
                    int l = 1;
                    while (l <= 4) {
                        int j1 = 16 >> l - 1;
                        int l1 = 16 >> l;
                        for (int j2 = 0; j2 < l1; j2++) {
                            for (int l2 = 0; l2 < l1; l2++) {
                                int j3 = this.f.getInt((j2 * 2 + 0 + (l2 * 2 + 0) * j1) * 4);
                                int l3 = this.f.getInt((j2 * 2 + 1 + (l2 * 2 + 0) * j1) * 4);
                                int j4 = this.f.getInt((j2 * 2 + 1 + (l2 * 2 + 1) * j1) * 4);
                                int l4 = this.f.getInt((j2 * 2 + 0 + (l2 * 2 + 1) * j1) * 4);
                                int j5 = a(a(j3, l3), a(j4, l4));
                                this.f.putInt((j2 + l2 * l1) * 4, j5);
                            }
                        }
                        GL11.glTexSubImage2D(3553, l, 0, 0, l1, l1, 6408, 5121, this.f);
                        l++;
                    }
                }
            }
        }
        updateTextureAnimations();
    }

    private int a(int i, int j) {
        int k = (i & 0xFF000000) >> 24 & 0xFF;
        int l = (j & 0xFF000000) >> 24 & 0xFF;
        return (k + l >> 1 << 24) + ((i & 0xFEFEFE) + (j & 0xFEFEFE) >> 1);
    }

    private int b(int i, int j) {
        int k = (i & 0xFF000000) >> 24 & 0xFF;
        int l = (j & 0xFF000000) >> 24 & 0xFF;
        char c = ';
        if (k + l == 0) {
            k = 1;
            l = 1;
            c = Character.MIN_VALUE;
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

    public void b() {
        i texturepackbase = this.l.a;
        for (Iterator<Integer> iterator = this.d.keySet().iterator(); iterator.hasNext(); a(bufferedimage, i)) {
            int i = iterator.next().intValue();
            BufferedImage bufferedimage = (BufferedImage) this.d.get(Integer.valueOf(i));
        }
        for (Iterator<ek> iterator1 = this.h.values().iterator(); iterator1.hasNext(); ) {
            ek threaddownloadimagedata = iterator1.next();
            threaddownloadimagedata.d = false;
        }
        for (Iterator<String> iterator2 = this.b.keySet().iterator(); iterator2.hasNext(); ) {
            String s = iterator2.next();
            int j = ((Integer) this.b.get(s)).intValue();
            loadTexture(j, s);
        }
        for (Iterator<String> iterator3 = this.c.keySet().iterator(); iterator3.hasNext(); ) {
            String s1 = iterator3.next();
            try {
                BufferedImage bufferedimage2;
                if (s1.startsWith("##")) {
                    bufferedimage2 = c(a(texturepackbase.a(s1.substring(2))));
                } else if (s1.startsWith("%clamp%")) {
                    this.j = true;
                    bufferedimage2 = a(texturepackbase.a(s1.substring(7)));
                } else if (s1.startsWith("%blur%")) {
                    this.k = true;
                    bufferedimage2 = a(texturepackbase.a(s1.substring(6)));
                } else {
                    bufferedimage2 = a(texturepackbase.a(s1));
                }
                a(bufferedimage2, (int[]) this.c.get(s1));
                this.k = false;
                this.j = false;
            } catch (IOException ioexception1) {
                ioexception1.printStackTrace();
            }
        }
    }

    private BufferedImage a(InputStream inputstream) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(inputstream);
        inputstream.close();
        return bufferedimage;
    }

    public void b(int i) {
        if (i < 0)
            return;
        GL11.glBindTexture(3553, i);
    }

    public Vec2 getTextureResolution(String texName) {
        Integer i = (Integer) this.b.get(texName);
        if (i != null)
            return this.textureResolutions.get(i);
        return null;
    }

    public void clearTextureAnimations() {
        this.textureAnimations.clear();
    }

    public void registerTextureAnimation(String name, TextureAnimated animTex) {
        this.textureAnimations.put(name, animTex);
    }

    public void unregisterTextureAnimation(String name) {
        this.textureAnimations.remove(name);
    }

    public void updateTextureAnimations() {
        for (TextureAnimated animTex : this.textureAnimations.values()) {
            animTex.onTick();
            this.f.clear();
            this.f.put(animTex.imageData);
            this.f.position(0).limit(animTex.imageData.length);
            GL11.glBindTexture(3553, b(animTex.getTexture()));
            GL11.glTexSubImage2D(3553, 0, animTex.x, animTex.y, animTex.width, animTex.height, 6408, 5121, this.f);
        }
    }

    public static boolean a = false;

    public HashMap b;

    private final HashMap c;

    private final HashMap d;

    private final IntBuffer e;

    private ByteBuffer f;

    private final List g;

    private final Map h;

    private final kv i;

    private boolean j;

    private boolean k;

    private final ik l;

    private final BufferedImage m;

    public File mapDir;

    public ArrayList<String> replacedTextures;

    private final HashMap<Integer, Vec2> textureResolutions;

    private final HashMap<String, TextureAnimated> textureAnimations;
}