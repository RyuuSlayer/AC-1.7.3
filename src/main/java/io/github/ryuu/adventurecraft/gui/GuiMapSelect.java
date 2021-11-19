package io.github.ryuu.adventurecraft.gui;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.List
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.src.MapInfo
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
import java.io.File;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_520;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.src.MapInfo;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiMapSelect extends Screen {

    protected Screen parent;

    private int field_6460_h = 0;

    private int field_6459_i = 32;

    private int field_6458_j = this.height - 55 + 4;

    private int field_6457_l = 0;

    private int field_6456_m = this.width;

    private int field_6455_n = -2;

    private int field_6454_o = -1;

    private String fileLocation = "";

    private String saveName;

    private MapInfo selectedMap;

    public GuiMapSelect(Screen guiscreen, String save) {
        this.parent = guiscreen;
        this.saveName = save;
    }

    @Override
    public void init() {
        this.minecraft.mapList.findMaps();
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        if (this.saveName == null) {
            this.buttons.add((Object) new OptionButton(6, this.width / 2 + 5, this.height - 48, stringtranslate.translate("gui.done")));
            this.buttons.add((Object) new Button(7, this.width / 2 - 155, this.height - 48, 150, 20, "New Map"));
        } else {
            this.buttons.add((Object) new OptionButton(6, this.width / 2 - 75, this.height - 48, stringtranslate.translate("gui.done")));
        }
        this.minecraft.texturePackManager.findTexturePacks();
        GuiMapSelect GuiMapSelect = this;
        this.fileLocation = new File(GuiMapSelect.minecraft.getGameDirectory(), "texturepacks").getAbsolutePath();
        this.field_6459_i = 32;
        this.field_6458_j = this.height - 58 + 4;
        this.field_6457_l = 0;
        this.field_6456_m = this.width;
    }

    @Override
    protected void buttonClicked(Button button) {
        if (!button.active) {
            return;
        }
        if (button.id == 6) {
            if (this.selectedMap == null) {
                this.minecraft.openScreen(this.parent);
            } else {
                if (this.saveName != null) {
                    if (this.saveName.equals((Object) "")) {
                        File worldDir;
                        File mcDir = Minecraft.getGameDirectory();
                        File saveDir = new File(mcDir, "saves");
                        int i = 1;
                        do {
                            this.saveName = String.format((String) "%s - Save %d", (Object[]) new Object[] { this.selectedMap.name, i });
                            worldDir = new File(saveDir, this.saveName);
                            ++i;
                        } while (worldDir.exists());
                    }
                    this.minecraft.saveMapUsed(this.saveName, this.selectedMap.name);
                }
                this.minecraft.interactionManager = new class_520(this.minecraft);
                this.minecraft.startWorld(this.saveName, this.saveName, 0L, this.selectedMap.name);
            }
        } else if (button.id == 7) {
            this.minecraft.openScreen(new GuiCreateNewMap(this));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        if (this.field_6454_o <= 0) {
            this.minecraft.texturePackManager.findTexturePacks();
            this.field_6454_o += 20;
        }
        List<MapInfo> list = this.minecraft.mapList.availableMaps();
        if (Mouse.isButtonDown((int) 0)) {
            if (this.field_6455_n == -1) {
                if (mouseY >= this.field_6459_i && mouseY <= this.field_6458_j) {
                    int k = this.width / 2 - 110;
                    int i1 = this.width / 2 + 110;
                    int j1 = (mouseY - this.field_6459_i + this.field_6460_h - 2) / 36;
                    if (mouseX >= k && mouseX <= i1 && j1 >= 0 && j1 < list.size()) {
                        this.selectedMap = (MapInfo) list.get(j1);
                    }
                    this.field_6455_n = mouseY;
                } else {
                    this.field_6455_n = -2;
                }
            } else if (this.field_6455_n >= 0) {
                this.field_6460_h -= mouseY - this.field_6455_n;
                this.field_6455_n = mouseY;
            }
        } else {
            if (this.field_6455_n < 0 || this.field_6455_n != mouseY) {
                // empty if block
            }
            this.field_6455_n = -1;
        }
        int l = list.size() * 36 - (this.field_6458_j - this.field_6459_i - 4);
        if (l < 0) {
            l /= 2;
        }
        if (this.field_6460_h < 0) {
            this.field_6460_h = 0;
        }
        if (this.field_6460_h > l) {
            this.field_6460_h = l;
        }
        GL11.glDisable((int) 2896);
        GL11.glDisable((int) 2912);
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glBindTexture((int) 3553, (int) this.minecraft.textureManager.getTextureId("/gui/background.png"));
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        float f1 = 32.0f;
        tessellator.start();
        tessellator.colour(0x202020);
        tessellator.vertex(this.field_6457_l, this.field_6458_j, 0.0, (float) this.field_6457_l / f1, (float) (this.field_6458_j + this.field_6460_h) / f1);
        tessellator.vertex(this.field_6456_m, this.field_6458_j, 0.0, (float) this.field_6456_m / f1, (float) (this.field_6458_j + this.field_6460_h) / f1);
        tessellator.vertex(this.field_6456_m, this.field_6459_i, 0.0, (float) this.field_6456_m / f1, (float) (this.field_6459_i + this.field_6460_h) / f1);
        tessellator.vertex(this.field_6457_l, this.field_6459_i, 0.0, (float) this.field_6457_l / f1, (float) (this.field_6459_i + this.field_6460_h) / f1);
        tessellator.draw();
        for (int k1 = 0; k1 < list.size(); ++k1) {
            MapInfo mInfo = (MapInfo) list.get(k1);
            int l1 = this.width / 2 - 92 - 16;
            int i2 = 36 + k1 * 36 - this.field_6460_h;
            int byte1 = 32;
            int byte2 = 32;
            if (mInfo == this.selectedMap) {
                int j2 = this.width / 2 - 110;
                int k2 = this.width / 2 + 110;
                GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
                GL11.glDisable((int) 3553);
                tessellator.start();
                tessellator.colour(0x808080);
                tessellator.vertex(j2, i2 + byte1 + 2, 0.0, 0.0, 1.0);
                tessellator.vertex(k2, i2 + byte1 + 2, 0.0, 1.0, 1.0);
                tessellator.vertex(k2, i2 - 2, 0.0, 1.0, 0.0);
                tessellator.vertex(j2, i2 - 2, 0.0, 0.0, 0.0);
                tessellator.colour(0);
                tessellator.vertex(j2 + 1, i2 + byte1 + 1, 0.0, 0.0, 1.0);
                tessellator.vertex(k2 - 1, i2 + byte1 + 1, 0.0, 1.0, 1.0);
                tessellator.vertex(k2 - 1, i2 - 1, 0.0, 1.0, 0.0);
                tessellator.vertex(j2 + 1, i2 - 1, 0.0, 0.0, 0.0);
                tessellator.draw();
                GL11.glEnable((int) 3553);
            }
            mInfo.bindTexture(this.minecraft);
            GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
            tessellator.start();
            tessellator.colour(0xFFFFFF);
            tessellator.vertex(l1, i2 + byte1, 0.0, 0.0, 1.0);
            tessellator.vertex(l1 + byte2, i2 + byte1, 0.0, 1.0, 1.0);
            tessellator.vertex(l1 + byte2, i2, 0.0, 1.0, 0.0);
            tessellator.vertex(l1, i2, 0.0, 0.0, 0.0);
            tessellator.draw();
            this.drawTextWithShadow(this.textManager, mInfo.name, l1 + byte2 + 2, i2 + 1, 0xFFFFFF);
            this.drawTextWithShadow(this.textManager, mInfo.description1, l1 + byte2 + 2, i2 + 12, 0x808080);
            this.drawTextWithShadow(this.textManager, mInfo.description2, l1 + byte2 + 2, i2 + 12 + 10, 0x808080);
        }
        int byte0 = 4;
        this.drawBackground(0, this.field_6459_i, 255, 255);
        this.drawBackground(this.field_6458_j, this.height, 255, 255);
        GL11.glEnable((int) 3042);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glDisable((int) 3008);
        GL11.glShadeModel((int) 7425);
        GL11.glDisable((int) 3553);
        tessellator.start();
        tessellator.colour(0, 0);
        tessellator.vertex(this.field_6457_l, this.field_6459_i + byte0, 0.0, 0.0, 1.0);
        tessellator.vertex(this.field_6456_m, this.field_6459_i + byte0, 0.0, 1.0, 1.0);
        tessellator.colour(0, 255);
        tessellator.vertex(this.field_6456_m, this.field_6459_i, 0.0, 1.0, 0.0);
        tessellator.vertex(this.field_6457_l, this.field_6459_i, 0.0, 0.0, 0.0);
        tessellator.draw();
        tessellator.start();
        tessellator.colour(0, 255);
        tessellator.vertex(this.field_6457_l, this.field_6458_j, 0.0, 0.0, 1.0);
        tessellator.vertex(this.field_6456_m, this.field_6458_j, 0.0, 1.0, 1.0);
        tessellator.colour(0, 0);
        tessellator.vertex(this.field_6456_m, this.field_6458_j - byte0, 0.0, 1.0, 0.0);
        tessellator.vertex(this.field_6457_l, this.field_6458_j - byte0, 0.0, 0.0, 0.0);
        tessellator.draw();
        GL11.glEnable((int) 3553);
        GL11.glShadeModel((int) 7424);
        GL11.glEnable((int) 3008);
        GL11.glDisable((int) 3042);
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        this.drawTextWithShadowCentred(this.textManager, stringtranslate.translate("mapList.title"), this.width / 2, 16, 0xFFFFFF);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        super.tick();
        --this.field_6454_o;
    }

    public void drawBackground(int top, int bottom, int topAlpha, int bottomAlpha) {
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glBindTexture((int) 3553, (int) this.minecraft.textureManager.getTextureId("/gui/background.png"));
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        float f = 32.0f;
        tessellator.start();
        tessellator.colour(0x404040, bottomAlpha);
        tessellator.vertex(0.0, bottom, 0.0, 0.0, (float) bottom / f);
        tessellator.vertex(this.width, bottom, 0.0, (float) this.width / f, (float) bottom / f);
        tessellator.colour(0x404040, topAlpha);
        tessellator.vertex(this.width, top, 0.0, (float) this.width / f, (float) top / f);
        tessellator.vertex(0.0, top, 0.0, 0.0, (float) top / f);
        tessellator.draw();
    }
}
