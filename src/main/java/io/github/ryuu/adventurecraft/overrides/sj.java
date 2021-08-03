package io.github.ryuu.adventurecraft.overrides;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public class sj {
    private int[] b;

    public int a;

    private int c;

    private IntBuffer d;

    public sj(kv gamesettings, String s, ji renderengine) {
        BufferedImage bufferedimage;
        this.b = new int[256];
        this.a = 0;
        this.d = ge.d(1024);
        try {
            bufferedimage = ImageIO.read(ji.class.getResourceAsStream(s));
        } catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
        }
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int[] ai = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, ai, 0, i);
        for (int k = 0; k < 256; k++) {
            int l = k % 16;
            int k1 = k / 16;
            int j2 = 7;
            while (j2 >= 0) {
                int i3 = l * 8 + j2;
                boolean flag = true;
                for (int l3 = 0; l3 < 8 && flag; l3++) {
                    int i4 = (k1 * 8 + l3) * i;
                    int k4 = ai[i3 + i4] & 0xFF;
                    if (k4 > 0)
                        flag = false;
                }
                if (!flag)
                    break;
                j2--;
            }
            if (k == 32)
                j2 = 2;
            this.b[k] = j2 + 2;
        }
        this.a = renderengine.a(bufferedimage);
        this.c = ge.a(288);
        nw tessellator = nw.a;
        for (int i1 = 0; i1 < 256; i1++) {
            GL11.glNewList(this.c + i1, 4864);
            tessellator.b();
            int l1 = i1 % 16 * 8;
            int k2 = i1 / 16 * 8;
            float f = 7.99F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            tessellator.a(0.0D, (0.0F + f), 0.0D, (l1 / 128.0F + f1), ((k2 + f) / 128.0F + f2));
            tessellator.a((0.0F + f), (0.0F + f), 0.0D, ((l1 + f) / 128.0F + f1), ((k2 + f) / 128.0F + f2));
            tessellator.a((0.0F + f), 0.0D, 0.0D, ((l1 + f) / 128.0F + f1), (k2 / 128.0F + f2));
            tessellator.a(0.0D, 0.0D, 0.0D, (l1 / 128.0F + f1), (k2 / 128.0F + f2));
            tessellator.a();
            GL11.glTranslatef(this.b[i1], 0.0F, 0.0F);
            GL11.glEndList();
        }
        for (int j1 = 0; j1 < 32; j1++) {
            int i2 = (j1 >> 3 & 0x1) * 85;
            int l2 = (j1 >> 2 & 0x1) * 170 + i2;
            int j3 = (j1 >> 1 & 0x1) * 170 + i2;
            int k3 = (j1 >> 0 & 0x1) * 170 + i2;
            if (j1 == 6)
                l2 += 85;
            boolean flag1 = (j1 >= 16);
            if (gamesettings.g) {
                int j4 = (l2 * 30 + j3 * 59 + k3 * 11) / 100;
                int l4 = (l2 * 30 + j3 * 70) / 100;
                int i5 = (l2 * 30 + k3 * 70) / 100;
                l2 = j4;
                j3 = l4;
                k3 = i5;
            }
            if (flag1) {
                l2 /= 4;
                j3 /= 4;
                k3 /= 4;
            }
            GL11.glNewList(this.c + 256 + j1, 4864);
            GL11.glColor3f(l2 / 255.0F, j3 / 255.0F, k3 / 255.0F);
            GL11.glEndList();
        }
    }

    public void a(String s, int i, int j, int k) {
        a(s, i + 1, j + 1, k, true);
        b(s, i, j, k);
    }

    public void drawStringWithShadow(String s, float i, float j, int k) {
        renderString(s, i + 1.0F, j + 1.0F, k, true);
        drawString(s, i, j, k);
    }

    public void b(String s, int i, int j, int k) {
        a(s, i, j, k, false);
    }

    public void drawString(String s, float i, float j, int k) {
        renderString(s, i, j, k, false);
    }

    public void a(String s, int i, int j, int k, boolean flag) {
        renderString(s, i, j, k, flag);
    }

    public void renderString(String s, float x, float y, int k, boolean flag) {
        if (s == null)
            return;
        if (flag) {
            int l = k & 0xFF000000;
            k = (k & 0xFCFCFC) >> 2;
            k += l;
        }
        GL11.glBindTexture(3553, this.a);
        float f = (k >> 16 & 0xFF) / 255.0F;
        float f1 = (k >> 8 & 0xFF) / 255.0F;
        float f2 = (k & 0xFF) / 255.0F;
        float f3 = (k >> 24 & 0xFF) / 255.0F;
        if (f3 == 0.0F)
            f3 = 1.0F;
        GL11.glColor4f(f, f1, f2, f3);
        this.d.clear();
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        for (int i1 = 0; i1 < s.length(); i1++) {
            for (; s.length() > i1 + 1 && s.charAt(i1) == '; i1 += 2) {
            int j1 = "0123456789abcdef".indexOf(s.toLowerCase().charAt(i1 + 1));
            if (j1 < 0 || j1 > 15)
                j1 = 15;
            this.d.put(this.c + 256 + j1 + (flag ? 16 : 0));
            if (this.d.remaining() == 0) {
                this.d.flip();
                GL11.glCallLists(this.d);
                this.d.clear();
            }
        }
        if (i1 < s.length()) {
            int k1 = fp.a.indexOf(s.charAt(i1));
            char c = s.charAt(i1);
            if (k1 >= 0 && c < ') {
            this.d.put(this.c + k1 + 32);
        } else if (c < ') {
        this.d.put(this.c + c);
    }
}
      if (this.d.remaining() == 0) {
              this.d.flip();
              GL11.glCallLists(this.d);
              this.d.clear();
              }
              }
              this.d.flip();
              GL11.glCallLists(this.d);
              GL11.glPopMatrix();
              }

public int a(String s) {
        if (s == null)
        return 0;
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
        if (s.charAt(j) == ') {
        j++;
        } else {
        int k = fp.a.indexOf(s.charAt(j));
        char c = s.charAt(j);
        if (k >= 0 && c < ') {
        i += this.b[k + 32];
        } else if (c < ') {
        i += this.b[c];
        }
        }
        }
        return i;
        }

public void a(String s, int i, int j, int k, int l) {
        String[] as = s.split("\n");
        if (as.length > 1) {
        for (int i1 = 0; i1 < as.length; i1++) {
        a(as[i1], i, j, k, l);
        j += a(as[i1], k);
        }
        return;
        }
        String[] as1 = s.split(" ");
        int j1 = 0;
        while (j1 < as1.length) {
        String s1;
        for (s1 = as1[j1++] + " "; j1 < as1.length && a(s1 + as1[j1]) < k; s1 = s1 + as1[j1++] + " ");
        for (; a(s1) > k; s1 = s1.substring(k1)) {
        int k1;
        for (k1 = 0; a(s1.substring(0, k1 + 1)) <= k; k1++);
        if (s1.substring(0, k1).trim().length() > 0) {
        b(s1.substring(0, k1), i, j, l);
        j += 8;
        }
        }
        if (s1.trim().length() > 0) {
        b(s1, i, j, l);
        j += 8;
        }
        }
        }

public int a(String s, int i) {
        String[] as = s.split("\n");
        if (as.length > 1) {
        int j = 0;
        for (int k = 0; k < as.length; k++)
        j += a(as[k], i);
        return j;
        }
        String[] as1 = s.split(" ");
        int l = 0;
        int i1 = 0;
        while (l < as1.length) {
        String s1;
        for (s1 = as1[l++] + " "; l < as1.length && a(s1 + as1[l]) < i; s1 = s1 + as1[l++] + " ");
        for (; a(s1) > i; s1 = s1.substring(j1)) {
        int j1;
        for (j1 = 0; a(s1.substring(0, j1 + 1)) <= i; j1++);
        if (s1.substring(0, j1).trim().length() > 0)
        i1 += 8;
        }
        if (s1.trim().length() > 0)
        i1 += 8;
        }
        if (i1 < 8)
        i1 += 8;
        return i1;
        }
        }
