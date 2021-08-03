package io.github.ryuu.adventurecraft.gui;

import java.io.File;
import java.util.List;

import io.github.ryuu.adventurecraft.util.MapInfo;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiMapSelect extends da {
    protected da parent;

    private int field_6460_h;

    private int field_6459_i;

    private int field_6458_j;

    private int field_6457_l;

    private int field_6456_m;

    private int field_6455_n;

    private int field_6454_o;

    private String fileLocation;

    private String saveName;

    private MapInfo selectedMap;

    public GuiMapSelect(da guiscreen, String save) {
        this.field_6460_h = 0;
        this.field_6459_i = 32;
        this.field_6458_j = this.d - 55 + 4;
        this.field_6457_l = 0;
        this.field_6456_m = this.c;
        this.field_6455_n = -2;
        this.field_6454_o = -1;
        this.fileLocation = "";
        this.parent = guiscreen;
        this.saveName = save;
    }

    public void b() {
        this.b.mapList.findMaps();
        nh stringtranslate = nh.a();
        if (this.saveName == null) {
            this.e.add(new ab(6, this.c / 2 + 5, this.d - 48, stringtranslate.a("gui.done")));
            this.e.add(new ke(7, this.c / 2 - 155, this.d - 48, 150, 20, "New Map"));
        } else {
            this.e.add(new ab(6, this.c / 2 - 75, this.d - 48, stringtranslate.a("gui.done")));
        }
        this.b.D.a();
        this.fileLocation = (new File(Minecraft.b(), "texturepacks")).getAbsolutePath();
        this.field_6459_i = 32;
        this.field_6458_j = this.d - 58 + 4;
        this.field_6457_l = 0;
        this.field_6456_m = this.c;
    }

    protected void a(ke guibutton) {
        if (!guibutton.g)
            return;
        if (guibutton.f == 6) {
            if (this.selectedMap == null) {
                this.b.a(this.parent);
            } else {
                if (this.saveName != null) {
                    if (this.saveName.equals("")) {
                        File worldDir, mcDir = Minecraft.b();
                        File saveDir = new File(mcDir, "saves");
                        int i = 1;
                        do {
                            this.saveName = String.format("%s - Save %d", new Object[] { this.selectedMap.name, Integer.valueOf(i) });
                            worldDir = new File(saveDir, this.saveName);
                            i++;
                        } while (worldDir.exists());
                    }
                    this.b.saveMapUsed(this.saveName, this.selectedMap.name);
                }
                this.b.c = new os(this.b);
                this.b.startWorld(this.saveName, this.saveName, 0L, this.selectedMap.name);
            }
        } else if (guibutton.f == 7) {
            this.b.a((da)new GuiCreateNewMap(this));
        }
    }

    protected void a(int i, int j, int k) {
        super.a(i, j, k);
    }

    protected void b(int i, int j, int k) {
        super.b(i, j, k);
    }

    public void a(int i, int j, float f) {
        i();
        if (this.field_6454_o <= 0) {
            this.b.D.a();
            this.field_6454_o += 20;
        }
        List<MapInfo> list = this.b.mapList.availableMaps();
        if (Mouse.isButtonDown(0)) {
            if (this.field_6455_n == -1) {
                if (j >= this.field_6459_i && j <= this.field_6458_j) {
                    int k = this.c / 2 - 110;
                    int i1 = this.c / 2 + 110;
                    int j1 = (j - this.field_6459_i + this.field_6460_h - 2) / 36;
                    if (i >= k && i <= i1 && j1 >= 0 && j1 < list.size())
                        this.selectedMap = (MapInfo)list.get(j1);
                    this.field_6455_n = j;
                } else {
                    this.field_6455_n = -2;
                }
            } else if (this.field_6455_n >= 0) {
                this.field_6460_h -= j - this.field_6455_n;
                this.field_6455_n = j;
            }
        } else {
            if (this.field_6455_n >= 0)
                if (this.field_6455_n != j);
            this.field_6455_n = -1;
        }
        int l = list.size() * 36 - this.field_6458_j - this.field_6459_i - 4;
        if (l < 0)
            l /= 2;
        if (this.field_6460_h < 0)
            this.field_6460_h = 0;
        if (this.field_6460_h > l)
            this.field_6460_h = l;
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        nw tessellator = nw.a;
        GL11.glBindTexture(3553, this.b.p.b("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f1 = 32.0F;
        tessellator.b();
        tessellator.b(2105376);
        tessellator.a(this.field_6457_l, this.field_6458_j, 0.0D, (this.field_6457_l / f1), ((this.field_6458_j + this.field_6460_h) / f1));
        tessellator.a(this.field_6456_m, this.field_6458_j, 0.0D, (this.field_6456_m / f1), ((this.field_6458_j + this.field_6460_h) / f1));
        tessellator.a(this.field_6456_m, this.field_6459_i, 0.0D, (this.field_6456_m / f1), ((this.field_6459_i + this.field_6460_h) / f1));
        tessellator.a(this.field_6457_l, this.field_6459_i, 0.0D, (this.field_6457_l / f1), ((this.field_6459_i + this.field_6460_h) / f1));
        tessellator.a();
        for (int k1 = 0; k1 < list.size(); k1++) {
            MapInfo mInfo = (MapInfo)list.get(k1);
            int l1 = this.c / 2 - 92 - 16;
            int i2 = 36 + k1 * 36 - this.field_6460_h;
            byte byte1 = 32;
            byte byte2 = 32;
            if (mInfo == this.selectedMap) {
                int j2 = this.c / 2 - 110;
                int k2 = this.c / 2 + 110;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(3553);
                tessellator.b();
                tessellator.b(8421504);
                tessellator.a(j2, (i2 + byte1 + 2), 0.0D, 0.0D, 1.0D);
                tessellator.a(k2, (i2 + byte1 + 2), 0.0D, 1.0D, 1.0D);
                tessellator.a(k2, (i2 - 2), 0.0D, 1.0D, 0.0D);
                tessellator.a(j2, (i2 - 2), 0.0D, 0.0D, 0.0D);
                tessellator.b(0);
                tessellator.a((j2 + 1), (i2 + byte1 + 1), 0.0D, 0.0D, 1.0D);
                tessellator.a((k2 - 1), (i2 + byte1 + 1), 0.0D, 1.0D, 1.0D);
                tessellator.a((k2 - 1), (i2 - 1), 0.0D, 1.0D, 0.0D);
                tessellator.a((j2 + 1), (i2 - 1), 0.0D, 0.0D, 0.0D);
                tessellator.a();
                GL11.glEnable(3553);
            }
            mInfo.bindTexture(this.b);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            tessellator.b();
            tessellator.b(16777215);
            tessellator.a(l1, (i2 + byte1), 0.0D, 0.0D, 1.0D);
            tessellator.a((l1 + byte2), (i2 + byte1), 0.0D, 1.0D, 1.0D);
            tessellator.a((l1 + byte2), i2, 0.0D, 1.0D, 0.0D);
            tessellator.a(l1, i2, 0.0D, 0.0D, 0.0D);
            tessellator.a();
            b(this.g, mInfo.name, l1 + byte2 + 2, i2 + 1, 16777215);
            b(this.g, mInfo.description1, l1 + byte2 + 2, i2 + 12, 8421504);
            b(this.g, mInfo.description2, l1 + byte2 + 2, i2 + 12 + 10, 8421504);
        }
        byte byte0 = 4;
        drawBackground(0, this.field_6459_i, 255, 255);
        drawBackground(this.field_6458_j, this.d, 255, 255);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        tessellator.b();
        tessellator.a(0, 0);
        tessellator.a(this.field_6457_l, (this.field_6459_i + byte0), 0.0D, 0.0D, 1.0D);
        tessellator.a(this.field_6456_m, (this.field_6459_i + byte0), 0.0D, 1.0D, 1.0D);
        tessellator.a(0, 255);
        tessellator.a(this.field_6456_m, this.field_6459_i, 0.0D, 1.0D, 0.0D);
        tessellator.a(this.field_6457_l, this.field_6459_i, 0.0D, 0.0D, 0.0D);
        tessellator.a();
        tessellator.b();
        tessellator.a(0, 255);
        tessellator.a(this.field_6457_l, this.field_6458_j, 0.0D, 0.0D, 1.0D);
        tessellator.a(this.field_6456_m, this.field_6458_j, 0.0D, 1.0D, 1.0D);
        tessellator.a(0, 0);
        tessellator.a(this.field_6456_m, (this.field_6458_j - byte0), 0.0D, 1.0D, 0.0D);
        tessellator.a(this.field_6457_l, (this.field_6458_j - byte0), 0.0D, 0.0D, 0.0D);
        tessellator.a();
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        nh stringtranslate = nh.a();
        a(this.g, stringtranslate.a("mapList.title"), this.c / 2, 16, 16777215);
        super.a(i, j, f);
    }

    public void a() {
        super.a();
        this.field_6454_o--;
    }

    public void drawBackground(int top, int bottom, int topAlpha, int bottomAlpha) {
        nw tessellator = nw.a;
        GL11.glBindTexture(3553, this.b.p.b("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        tessellator.b();
        tessellator.a(4210752, bottomAlpha);
        tessellator.a(0.0D, bottom, 0.0D, 0.0D, (bottom / f));
        tessellator.a(this.c, bottom, 0.0D, (this.c / f), (bottom / f));
        tessellator.a(4210752, topAlpha);
        tessellator.a(this.c, top, 0.0D, (this.c / f), (top / f));
        tessellator.a(0.0D, top, 0.0D, 0.0D, (top / f));
        tessellator.a();
    }
}
