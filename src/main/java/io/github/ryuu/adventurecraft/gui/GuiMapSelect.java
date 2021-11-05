package io.github.ryuu.adventurecraft.gui;

import java.io.File;
import java.util.List;

import io.github.ryuu.adventurecraft.util.MapInfo;
import net.minecraft.class_520;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.resource.language.TranslationStorage;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiMapSelect extends Screen {
    protected Screen parent;

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

    public GuiMapSelect(Screen guiscreen, String save) {
        this.field_6460_h = 0;
        this.field_6459_i = 32;
        this.field_6458_j = this.height - 55 + 4;
        this.field_6457_l = 0;
        this.field_6456_m = this.width;
        this.field_6455_n = -2;
        this.field_6454_o = -1;
        this.fileLocation = "";
        this.parent = guiscreen;
        this.saveName = save;
    }

    @Override
    public void init() {
        this.minecraft.mapList.findMaps();
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        if (this.saveName == null) {
            this.buttons.add(new OptionButton(6, this.width / 2 + 5, this.height - 48, stringtranslate.translate("gui.done")));
            this.buttons.add(new Button(7, this.width / 2 - 155, this.height - 48, 150, 20, "New Map"));
        } else {
            this.buttons.add(new OptionButton(6, this.width / 2 - 75, this.height - 48, stringtranslate.translate("gui.done")));
        }
        this.minecraft.texturePackManager.findTexturePacks();
        this.fileLocation = (new File(Minecraft.getGameDirectory(), "texturepacks")).getAbsolutePath();
        this.field_6459_i = 32;
        this.field_6458_j = this.height - 58 + 4;
        this.field_6457_l = 0;
        this.field_6456_m = this.width;
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (!guibutton.active)
            return;
        if (guibutton.id == 6) {
            if (this.selectedMap == null) {
                this.minecraft.openScreen(this.parent);
            } else {
                if (this.saveName != null) {
                    if (this.saveName.equals("")) {
                        File worldDir, mcDir = Minecraft.getGameDirectory();
                        File saveDir = new File(mcDir, "saves");
                        int i = 1;
                        do {
                            this.saveName = String.format("%s - Save %d", this.selectedMap.name, i);
                            worldDir = new File(saveDir, this.saveName);
                            i++;
                        } while (worldDir.exists());
                    }
                    this.minecraft.saveMapUsed(this.saveName, this.selectedMap.name);
                }
                this.minecraft.interactionManager = new class_520(this.minecraft);
                this.minecraft.startWorld(this.saveName, this.saveName, 0L, this.selectedMap.name);
            }
        } else if (guibutton.id == 7) {
            this.minecraft.openScreen(new GuiCreateNewMap(this));
        }
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
    }

    @Override
    protected void mouseReleased(int i, int j, int k) {
        super.mouseReleased(i, j, k);
    }

    @Override
    public void render(int i, int j, float f) {
        renderBackground();
        if (this.field_6454_o <= 0) {
            this.minecraft.texturePackManager.findTexturePacks();
            this.field_6454_o += 20;
        }
        List<MapInfo> list = this.minecraft.mapList.availableMaps();
        if (Mouse.isButtonDown(0)) {
            if (this.field_6455_n == -1) {
                if (j >= this.field_6459_i && j <= this.field_6458_j) {
                    int k = this.width / 2 - 110;
                    int i1 = this.width / 2 + 110;
                    int j1 = (j - this.field_6459_i + this.field_6460_h - 2) / 36;
                    if (i >= k && i <= i1 && j1 >= 0 && j1 < list.size())
                        this.selectedMap = list.get(j1);
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
                if (this.field_6455_n != j) ;
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
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f1 = 32.0F;
        tessellator.start();
        tessellator.colour(2105376);
        tessellator.vertex(this.field_6457_l, this.field_6458_j, 0.0D, (this.field_6457_l / f1), ((this.field_6458_j + this.field_6460_h) / f1));
        tessellator.vertex(this.field_6456_m, this.field_6458_j, 0.0D, (this.field_6456_m / f1), ((this.field_6458_j + this.field_6460_h) / f1));
        tessellator.vertex(this.field_6456_m, this.field_6459_i, 0.0D, (this.field_6456_m / f1), ((this.field_6459_i + this.field_6460_h) / f1));
        tessellator.vertex(this.field_6457_l, this.field_6459_i, 0.0D, (this.field_6457_l / f1), ((this.field_6459_i + this.field_6460_h) / f1));
        tessellator.draw();
        for (int k1 = 0; k1 < list.size(); k1++) {
            MapInfo mInfo = list.get(k1);
            int l1 = this.width / 2 - 92 - 16;
            int i2 = 36 + k1 * 36 - this.field_6460_h;
            byte byte1 = 32;
            byte byte2 = 32;
            if (mInfo == this.selectedMap) {
                int j2 = this.width / 2 - 110;
                int k2 = this.width / 2 + 110;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(3553);
                tessellator.start();
                tessellator.colour(8421504);
                tessellator.vertex(j2, (i2 + byte1 + 2), 0.0D, 0.0D, 1.0D);
                tessellator.vertex(k2, (i2 + byte1 + 2), 0.0D, 1.0D, 1.0D);
                tessellator.vertex(k2, (i2 - 2), 0.0D, 1.0D, 0.0D);
                tessellator.vertex(j2, (i2 - 2), 0.0D, 0.0D, 0.0D);
                tessellator.colour(0);
                tessellator.vertex((j2 + 1), (i2 + byte1 + 1), 0.0D, 0.0D, 1.0D);
                tessellator.vertex((k2 - 1), (i2 + byte1 + 1), 0.0D, 1.0D, 1.0D);
                tessellator.vertex((k2 - 1), (i2 - 1), 0.0D, 1.0D, 0.0D);
                tessellator.vertex((j2 + 1), (i2 - 1), 0.0D, 0.0D, 0.0D);
                tessellator.draw();
                GL11.glEnable(3553);
            }
            mInfo.bindTexture(this.minecraft);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            tessellator.start();
            tessellator.colour(16777215);
            tessellator.vertex(l1, (i2 + byte1), 0.0D, 0.0D, 1.0D);
            tessellator.vertex((l1 + byte2), (i2 + byte1), 0.0D, 1.0D, 1.0D);
            tessellator.vertex((l1 + byte2), i2, 0.0D, 1.0D, 0.0D);
            tessellator.vertex(l1, i2, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
            drawTextWithShadow(this.textManager, mInfo.name, l1 + byte2 + 2, i2 + 1, 16777215);
            drawTextWithShadow(this.textManager, mInfo.description1, l1 + byte2 + 2, i2 + 12, 8421504);
            drawTextWithShadow(this.textManager, mInfo.description2, l1 + byte2 + 2, i2 + 12 + 10, 8421504);
        }
        byte byte0 = 4;
        drawBackground(0, this.field_6459_i, 255, 255);
        drawBackground(this.field_6458_j, this.height, 255, 255);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        tessellator.start();
        tessellator.colour(0, 0);
        tessellator.vertex(this.field_6457_l, (this.field_6459_i + byte0), 0.0D, 0.0D, 1.0D);
        tessellator.vertex(this.field_6456_m, (this.field_6459_i + byte0), 0.0D, 1.0D, 1.0D);
        tessellator.colour(0, 255);
        tessellator.vertex(this.field_6456_m, this.field_6459_i, 0.0D, 1.0D, 0.0D);
        tessellator.vertex(this.field_6457_l, this.field_6459_i, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        tessellator.start();
        tessellator.colour(0, 255);
        tessellator.vertex(this.field_6457_l, this.field_6458_j, 0.0D, 0.0D, 1.0D);
        tessellator.vertex(this.field_6456_m, this.field_6458_j, 0.0D, 1.0D, 1.0D);
        tessellator.colour(0, 0);
        tessellator.vertex(this.field_6456_m, (this.field_6458_j - byte0), 0.0D, 1.0D, 0.0D);
        tessellator.vertex(this.field_6457_l, (this.field_6458_j - byte0), 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        drawTextWithShadowCentred(this.textManager, stringtranslate.translate("mapList.title"), this.width / 2, 16, 16777215);
        super.render(i, j, f);
    }

    @Override
    public void tick() {
        super.tick();
        this.field_6454_o--;
    }

    public void drawBackground(int top, int bottom, int topAlpha, int bottomAlpha) {
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        tessellator.start();
        tessellator.colour(4210752, bottomAlpha);
        tessellator.vertex(0.0D, bottom, 0.0D, 0.0D, (bottom / f));
        tessellator.vertex(this.width, bottom, 0.0D, (this.width / f), (bottom / f));
        tessellator.colour(4210752, topAlpha);
        tessellator.vertex(this.width, top, 0.0D, (this.width / f), (top / f));
        tessellator.vertex(0.0D, top, 0.0D, 0.0D, (top / f));
        tessellator.draw();
    }
}
