package io.github.ryuu.adventurecraft.overrides;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.ryuu.adventurecraft.scripting.ScriptUIContainer;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TerrainImage;
import io.github.ryuu.adventurecraft.util.Version;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class uq extends ub {
    public uq(Minecraft minecraft) {
        this.hudEnabled = true;
        this.e = new ArrayList();
        this.f = new Random();
        this.a = null;
        this.h = 0;
        this.i = "";
        this.j = 0;
        this.l = false;
        this.c = 1.0F;
        this.g = minecraft;
        this.scriptUI = new ScriptUIContainer(0.0F, 0.0F, null);
    }

    public void a(float f, boolean flag, int i, int j) {
        qq scaledresolution = new qq(this.g.z, this.g.d, this.g.e);
        int scaledWidth = scaledresolution.a();
        int scaledHeight = scaledresolution.b();
        sj fontrenderer = this.g.q;
        this.g.t.b();
        GL11.glEnable(3042);
        if (Minecraft.u())
            a(this.g.h.a(f), scaledWidth, scaledHeight);
        iz itemstack = this.g.h.c.d(3);
        if (!this.g.z.A && !this.g.cameraActive && itemstack != null && itemstack.c == Tile.bb.bn)
            a(scaledWidth, scaledHeight);
        if (this.g.f != null && !this.g.f.x.overlay.isEmpty())
            renderOverlay(scaledWidth, scaledHeight, this.g.f.x.overlay);
        float f1 = this.g.h.C + (this.g.h.B - this.g.h.C) * f;
        if (f1 > 0.0F)
            b(f1, scaledWidth, scaledHeight);
        if (this.hudEnabled) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glBindTexture(3553, this.g.p.b("/gui/gui.png"));
            ix inventoryplayer = this.g.h.c;
            this.k = -90.0F;
            b(scaledWidth / 2 - 91, scaledHeight - 22, 0, 0, 182, 22);
            b(scaledWidth / 2 - 91 - 1 + inventoryplayer.offhandItem * 20, scaledHeight - 22 - 1, 24, 22, 48, 22);
            b(scaledWidth / 2 - 91 - 1 + inventoryplayer.c * 20, scaledHeight - 22 - 1, 0, 22, 24, 22);
            GL11.glBindTexture(3553, this.g.p.b("/gui/icons.png"));
            GL11.glEnable(3042);
            GL11.glBlendFunc(775, 769);
            b(scaledWidth / 2 - 7, scaledHeight / 2 - 7, 0, 0, 16, 16);
            GL11.glDisable(3042);
            boolean flag1 = (this.g.h.by / 3 % 2 == 1);
            if (this.g.h.by < 10)
                flag1 = false;
            int health = this.g.h.Y;
            int prevHealth = this.g.h.Z;
            this.f.setSeed((this.h * 312871));
            if (this.g.c.d()) {
                int armorValue = this.g.h.s();
                for (int index = 0; index < 10; index++) {
                    int yPos = scaledHeight - 32;
                    if (armorValue > 0) {
                        int j5 = scaledWidth / 2 + 91 - index * 8 - 9;
                        if (index * 2 + 1 < armorValue)
                            b(j5, yPos, 34, 9, 9, 9);
                        if (index * 2 + 1 == armorValue)
                            b(j5, yPos, 25, 9, 9, 9);
                        if (index * 2 + 1 > armorValue)
                            b(j5, yPos, 16, 9, 9, 9);
                    }
                    int xPos = scaledWidth / 2 - 91 + index * 8;
                    if (health <= 8)
                        yPos += this.f.nextInt(2);
                    for (int healthRow = 0; healthRow <= (this.g.h.maxHealth - 1) / 40; healthRow++) {
                        if ((index + 1 + healthRow * 10) * 4 <= this.g.h.maxHealth) {
                            int k5 = 0;
                            if (flag1)
                                k5 = 1;
                            b(xPos, yPos, 16 + k5 * 9, 0, 9, 9);
                            if (flag1)
                                if (index * 4 + 3 + healthRow * 40 < prevHealth) {
                                    b(xPos, yPos, 70, 0, 9, 9);
                                } else if (index * 4 + 3 + healthRow * 40 == prevHealth) {
                                    b(xPos, yPos, 105, 0, 9, 9);
                                } else if (index * 4 + 2 + healthRow * 40 == prevHealth) {
                                    b(xPos, yPos, 79, 0, 9, 9);
                                } else if (index * 4 + 1 + healthRow * 40 == prevHealth) {
                                    b(xPos, yPos, 114, 0, 9, 9);
                                }
                            if (index * 4 + 3 + healthRow * 40 < health) {
                                b(xPos, yPos, 52, 0, 9, 9);
                            } else if (index * 4 + 3 + healthRow * 40 == health) {
                                b(xPos, yPos, 87, 0, 9, 9);
                            } else if (index * 4 + 2 + healthRow * 40 == health) {
                                b(xPos, yPos, 61, 0, 9, 9);
                            } else if (index * 4 + 1 + healthRow * 40 == health) {
                                b(xPos, yPos, 96, 0, 9, 9);
                            }
                        }
                        yPos -= 9;
                    }
                }
            }
            if (this.g.h.a(ln.g)) {
                int yOffset = -9 * (this.g.h.maxHealth - 1) / 40;
                int k2 = (int)Math.ceil((this.g.h.bz - 2) * 10.0D / 300.0D);
                int l3 = (int)Math.ceil(this.g.h.bz * 10.0D / 300.0D) - k2;
                for (int l5 = 0; l5 < k2 + l3; l5++) {
                    if (l5 < k2) {
                        b(scaledWidth / 2 - 91 + l5 * 8, scaledHeight - 32 - 9 + yOffset, 16, 18, 9, 9);
                    } else {
                        b(scaledWidth / 2 - 91 + l5 * 8, scaledHeight - 32 - 9 + yOffset, 25, 18, 9, 9);
                    }
                }
            }
        }
        GL11.glDisable(3042);
        if (this.hudEnabled) {
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
            u.b();
            GL11.glPopMatrix();
            for (int l1 = 0; l1 < 9; l1++) {
                int i3 = scaledWidth / 2 - 90 + l1 * 20 + 2;
                int i4 = scaledHeight - 16 - 3;
                a(l1, i3, i4, f);
            }
            u.a();
            GL11.glDisable(32826);
        }
        if (this.g.h.P() > 0) {
            GL11.glDisable(2929);
            GL11.glDisable(3008);
            int i2 = this.g.h.P();
            float f3 = i2 / 100.0F;
            if (f3 > 1.0F)
                f3 = 1.0F - (i2 - 100) / 10.0F;
            int j4 = (int)(220.0F * f3) << 24 | 0x101020;
            a(0, 0, scaledWidth, scaledHeight, j4);
            GL11.glEnable(3008);
            GL11.glEnable(2929);
        }
        if (this.g.z.B) {
            GL11.glPushMatrix();
            if (Minecraft.H > 0L)
                GL11.glTranslatef(0.0F, 32.0F, 0.0F);
            fontrenderer.a("Minecraft Beta 1.7.3 (" + this.g.K + ")", 2, 2, 16777215);
            fontrenderer.a(this.g.o(), 2, 12, 16777215);
            fontrenderer.a(this.g.p(), 2, 22, 16777215);
            fontrenderer.a(this.g.r(), 2, 32, 16777215);
            fontrenderer.a(this.g.q(), 2, 42, 16777215);
            long l2 = Runtime.getRuntime().maxMemory();
            long l4 = Runtime.getRuntime().totalMemory();
            long l6 = Runtime.getRuntime().freeMemory();
            long l7 = l4 - l6;
            String s = "Used memory: " + (l7 * 100L / l2) + "% (" + (l7 / 1024L / 1024L) + "MB) of " + (l2 / 1024L / 1024L) + "MB";
            b(fontrenderer, s, scaledWidth - fontrenderer.a(s) - 2, 2, 14737632);
            s = "Allocated memory: " + (l4 * 100L / l2) + "% (" + (l4 / 1024L / 1024L) + "MB)";
            b(fontrenderer, s, scaledWidth - fontrenderer.a(s) - 2, 12, 14737632);
            b(fontrenderer, "x: " + this.g.h.aM, 2, 64, 14737632);
            b(fontrenderer, "y: " + this.g.h.aN, 2, 72, 14737632);
            b(fontrenderer, "z: " + this.g.h.aO, 2, 80, 14737632);
            b(fontrenderer, "f: " + (in.b((this.g.h.aS * 4.0F / 360.0F) + 0.5D) & 0x3), 2, 88, 14737632);
            b(fontrenderer, String.format("Use Terrain Images: %b", new Object[] { Boolean.valueOf(this.g.f.x.useImages) }), 2, 96, 14737632);
            b(fontrenderer, String.format("Collide X: %d Z: %d", new Object[] { Integer.valueOf(this.g.h.collisionX), Integer.valueOf(this.g.h.collisionZ) }), 2, 104, 14737632);
            if (this.g.f.x.useImages) {
                int x = (int)this.g.h.aM;
                int z = (int)this.g.h.aO;
                int terrainHeight = TerrainImage.getTerrainHeight(x, z);
                int waterHeight = TerrainImage.getWaterHeight(x, z);
                double temperature = TerrainImage.getTerrainTemperature(x, z);
                double humidity = TerrainImage.getTerrainHumidity(x, z);
                b(fontrenderer, String.format("T: %d W: %d Temp: %.2f Humid: %.2f", new Object[] { Integer.valueOf(terrainHeight), Integer.valueOf(waterHeight), Double.valueOf(temperature), Double.valueOf(humidity) }), 2, 112, 14737632);
            }
            GL11.glPopMatrix();
        } else {
            fontrenderer.a(Version.shortVersion, 2, 2, 16777215);
            int offset = 12;
            if (DebugMode.active) {
                fontrenderer.a("Debug Active", 2, offset, 16777215);
                offset += 10;
            }
            if (DebugMode.levelEditing)
                fontrenderer.a("Map Editing", 2, offset, 16777215);
        }
        if (this.j > 0) {
            float f2 = this.j - f;
            int j3 = (int)(f2 * 256.0F / 20.0F);
            if (j3 > 255)
                j3 = 255;
            if (j3 > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef((scaledWidth / 2), (scaledHeight - 48), 0.0F);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                int k4 = 16777215;
                if (this.l)
                    k4 = Color.HSBtoRGB(f2 / 50.0F, 0.7F, 0.6F) & 0xFFFFFF;
                fontrenderer.b(this.i, -fontrenderer.a(this.i) / 2, -4, k4 + (j3 << 24));
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
        }
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glDisable(2929);
        this.scriptUI.render(fontrenderer, this.g.p, f);
        GL11.glEnable(2929);
        byte byte0 = 10;
        boolean flag2 = false;
        if (this.g.r instanceof gc) {
            byte0 = 20;
            flag2 = true;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, (scaledHeight - 48), 0.0F);
        for (int i5 = 0; i5 < this.e.size() && i5 < byte0; i5++) {
            if (((sw)this.e.get(i5)).b < 200 || flag2) {
                double d = ((sw)this.e.get(i5)).b / 200.0D;
                d = 1.0D - d;
                d *= 10.0D;
                if (d < 0.0D)
                    d = 0.0D;
                if (d > 1.0D)
                    d = 1.0D;
                d *= d;
                int j6 = (int)(255.0D * d);
                if (flag2)
                    j6 = 255;
                if (j6 > 0) {
                    byte byte1 = 2;
                    int k6 = -i5 * 9;
                    String s1 = ((sw)this.e.get(i5)).a;
                    a(byte1, k6 - 1, byte1 + 320, k6 + 8, j6 / 2 << 24);
                    GL11.glEnable(3042);
                    fontrenderer.a(s1, byte1, k6, 16777215 + (j6 << 24));
                }
            }
        }
        GL11.glPopMatrix();
        GL11.glEnable(3008);
        GL11.glDisable(3042);
    }

    private void a(int i, int j) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3008);
        GL11.glBindTexture(3553, this.g.p.b("%blur%/misc/pumpkinblur.png"));
        nw tessellator = nw.a;
        tessellator.b();
        tessellator.a(0.0D, j, -90.0D, 0.0D, 1.0D);
        tessellator.a(i, j, -90.0D, 1.0D, 1.0D);
        tessellator.a(i, 0.0D, -90.0D, 1.0D, 0.0D);
        tessellator.a(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        tessellator.a();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void a(float f, int i, int j) {
        f = 1.0F - f;
        if (f < 0.0F)
            f = 0.0F;
        if (f > 1.0F)
            f = 1.0F;
        this.c = (float)(this.c + (f - this.c) * 0.01D);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(0, 769);
        GL11.glColor4f(this.c, this.c, this.c, 1.0F);
        GL11.glBindTexture(3553, this.g.p.b("%blur%/misc/vignette.png"));
        nw tessellator = nw.a;
        tessellator.b();
        tessellator.a(0.0D, j, -90.0D, 0.0D, 1.0D);
        tessellator.a(i, j, -90.0D, 1.0D, 1.0D);
        tessellator.a(i, 0.0D, -90.0D, 1.0D, 0.0D);
        tessellator.a(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        tessellator.a();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(770, 771);
    }

    private void b(float f, int i, int j) {
        if (f < 1.0F) {
            f *= f;
            f *= f;
            f = f * 0.8F + 0.2F;
        }
        GL11.glDisable(3008);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
        GL11.glBindTexture(3553, this.g.p.b("/terrain.png"));
        float f1 = (Tile.bf.bm % 16) / 16.0F;
        float f2 = (Tile.bf.bm / 16) / 16.0F;
        float f3 = (Tile.bf.bm % 16 + 1) / 16.0F;
        float f4 = (Tile.bf.bm / 16 + 1) / 16.0F;
        nw tessellator = nw.a;
        tessellator.b();
        tessellator.a(0.0D, j, -90.0D, f1, f4);
        tessellator.a(i, j, -90.0D, f3, f4);
        tessellator.a(i, 0.0D, -90.0D, f3, f2);
        tessellator.a(0.0D, 0.0D, -90.0D, f1, f2);
        tessellator.a();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderOverlay(int i, int j, String overlay) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3008);
        GL11.glBindTexture(3553, this.g.p.b("/overlays/" + overlay));
        nw tessellator = nw.a;
        tessellator.b();
        tessellator.a(0.0D, j, -90.0D, 0.0D, 1.0D);
        tessellator.a(i, j, -90.0D, 1.0D, 1.0D);
        tessellator.a(i, 0.0D, -90.0D, 1.0D, 0.0D);
        tessellator.a(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        tessellator.a();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void a(int i, int j, int k, float f) {
        iz itemstack = this.g.h.c.a[i];
        if (itemstack == null)
            return;
        float f1 = itemstack.b - f;
        if (f1 > 0.0F) {
            GL11.glPushMatrix();
            float f2 = 1.0F + f1 / 5.0F;
            GL11.glTranslatef((j + 8), (k + 12), 0.0F);
            GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
            GL11.glTranslatef(-(j + 8), -(k + 12), 0.0F);
        }
        d.a(this.g.q, this.g.p, itemstack, j, k);
        if (f1 > 0.0F)
            GL11.glPopMatrix();
        d.b(this.g.q, this.g.p, itemstack, j, k);
    }

    public void a() {
        if (this.j > 0)
            this.j--;
        this.h++;
        for (int i = 0; i < this.e.size(); i++)
            ((sw)this.e.get(i)).b++;
        this.scriptUI.onUpdate();
    }

    public void b() {
        this.e.clear();
    }

    public void a(String s) {
        for (; this.g.q.a(s) > 320; s = s.substring(i)) {
            int i;
            for (i = 1; i < s.length() && this.g.q.a(s.substring(0, i + 1)) <= 320; i++);
            a(s.substring(0, i));
        }
        this.e.add(0, new sw(s));
        for (; this.e.size() > 50; this.e.remove(this.e.size() - 1));
    }

    public void b(String s) {
        this.i = "Now playing: " + s;
        this.j = 60;
        this.l = true;
    }

    public void c(String s) {
        nh stringtranslate = nh.a();
        String s1 = stringtranslate.a(s);
        a(s1);
    }

    private static ItemRenderer d = new ItemRenderer();

    private List e;

    private Random f;

    private Minecraft g;

    public String a;

    private int h;

    private String i;

    private int j;

    private boolean l;

    public float b;

    float c;

    public ScriptUIContainer scriptUI;

    public boolean hudEnabled;
}