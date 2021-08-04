package io.github.ryuu.adventurecraft.gui;

import java.util.Random;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.class_520;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.Textbox;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.input.Keyboard;

public class GuiCreateNewMap extends Screen {
    private final Screen parent;

    private Textbox textboxMapName;

    private Textbox textboxSeed;

    private String folderName;

    private boolean createClicked;

    public double mapSize;

    public int waterLevel;

    public double fractureHorizontal;

    public double fractureVertical;

    public double maxAvgDepth;

    public double maxAvgHeight;

    public double volatility1;

    public double volatility2;

    public double volatilityWeight1;

    public double volatilityWeight2;

    GuiSlider2 sliderMapSize;

    GuiSlider2 sliderWaterLevel;

    GuiSlider2 sliderFracHorizontal;

    GuiSlider2 sliderFracVertical;

    GuiSlider2 sliderMaxAvgDepth;

    GuiSlider2 sliderMaxAvgHeight;

    GuiSlider2 sliderVolatility1;

    GuiSlider2 sliderVolatility2;

    GuiSlider2 sliderVolatilityWeight1;

    GuiSlider2 sliderVolatilityWeight2;

    public GuiCreateNewMap(Screen guiscreen) {
        this.mapSize = 250.0D;
        this.waterLevel = 64;
        this.fractureHorizontal = 1.0D;
        this.fractureVertical = 1.0D;
        this.maxAvgDepth = 0.0D;
        this.maxAvgHeight = 0.0D;
        this.volatility1 = 1.0D;
        this.volatility2 = 1.0D;
        this.volatilityWeight1 = 0.0D;
        this.volatilityWeight2 = 1.0D;
        this.parent = guiscreen;
    }

    @Override
    public void tick() {
        this.textboxMapName.tick();
        this.textboxSeed.tick();
    }

    @Override
    public void init() {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.buttons.clear();
        this.buttons.add(new Button(0, this.width / 2 - 205, 200, "Create Map"));
        this.buttons.add(new Button(1, this.width / 2 + 5, 200, stringtranslate.translate("gui.cancel")));
        this.textboxMapName = new Textbox(this, this.textManager, this.width / 2 - 100, 38, 200, 20, stringtranslate.translate("selectWorld.newWorld"));
        this.textboxMapName.field_2420 = true;
        this.textboxMapName.method_1878(32);
        this.textboxSeed = new Textbox(this, this.textManager, this.width / 2 - 100, 62, 200, 20, "");
        int xPos1 = this.width / 2 - 4 - 150;
        int xPos2 = this.width / 2 + 4;
        this.sliderMapSize = new GuiSlider2(2, xPos1, 88, 10, "", (float) (this.mapSize - 100.0D) / 650.0F);
        this.sliderWaterLevel = new GuiSlider2(2, xPos2, 88, 10, "", this.waterLevel / 128.0F);
        this.sliderFracHorizontal = new GuiSlider2(2, xPos1, 109, 10, "", (float) this.fractureHorizontal / 2.0F);
        this.sliderFracVertical = new GuiSlider2(2, xPos2, 109, 10, "", (float) this.fractureVertical / 2.0F);
        this.sliderMaxAvgDepth = new GuiSlider2(2, xPos1, 130, 10, "", (float) (this.maxAvgDepth + 5.0D) / 10.0F);
        this.sliderMaxAvgHeight = new GuiSlider2(2, xPos2, 130, 10, "", (float) (this.maxAvgHeight + 5.0D) / 10.0F);
        this.sliderVolatility1 = new GuiSlider2(2, xPos1, 151, 10, "", (float) this.volatility1 / 5.0F);
        this.sliderVolatility2 = new GuiSlider2(2, xPos2, 151, 10, "", (float) this.volatility2 / 5.0F);
        this.sliderVolatilityWeight1 = new GuiSlider2(2, xPos1, 172, 10, "", (float) (this.volatilityWeight1 + 0.5D));
        this.sliderVolatilityWeight2 = new GuiSlider2(2, xPos2, 172, 10, "", (float) (this.volatilityWeight2 - 0.5D));
        updateSliders();
        this.buttons.add(this.sliderMapSize);
        this.buttons.add(this.sliderWaterLevel);
        this.buttons.add(this.sliderFracHorizontal);
        this.buttons.add(this.sliderFracVertical);
        this.buttons.add(this.sliderMaxAvgDepth);
        this.buttons.add(this.sliderMaxAvgHeight);
        this.buttons.add(this.sliderVolatility1);
        this.buttons.add(this.sliderVolatility2);
        this.buttons.add(this.sliderVolatilityWeight1);
        this.buttons.add(this.sliderVolatilityWeight2);
    }

    private void updateSliders() {
        this.mapSize = (this.sliderMapSize.sliderValue * 650.0F + 100.0F);
        this.waterLevel = (int) (128.0F * this.sliderWaterLevel.sliderValue);
        this.fractureHorizontal = this.sliderFracHorizontal.sliderValue * 2.0D;
        this.fractureVertical = this.sliderFracVertical.sliderValue * 2.0D;
        this.maxAvgDepth = this.sliderMaxAvgDepth.sliderValue * 10.0D - 5.0D;
        this.maxAvgHeight = this.sliderMaxAvgHeight.sliderValue * 10.0D - 5.0D;
        this.volatility1 = this.sliderVolatility1.sliderValue * 5.0D;
        this.volatility2 = this.sliderVolatility2.sliderValue * 5.0D;
        this.volatilityWeight1 = this.sliderVolatilityWeight1.sliderValue - 0.5D;
        this.volatilityWeight2 = this.sliderVolatilityWeight2.sliderValue + 0.5D;
        this.sliderMapSize.text = String.format("Map Size: %.1f", this.mapSize);
        this.sliderWaterLevel.text = String.format("Water Level: %d", this.waterLevel);
        this.sliderFracHorizontal.text = String.format("Fracture Horizontal: %.2f", this.fractureHorizontal);
        this.sliderFracVertical.text = String.format("Fracture Vertical: %.2f", this.fractureVertical);
        this.sliderMaxAvgDepth.text = String.format("Max Avg Depth: %.2f", this.maxAvgDepth);
        this.sliderMaxAvgHeight.text = String.format("Max Avg Height: %.2f", this.maxAvgHeight);
        this.sliderVolatility1.text = String.format("Volatility 1: %.2f", this.volatility1);
        this.sliderVolatility2.text = String.format("Volatility 2: %.2f", this.volatility2);
        this.sliderVolatilityWeight1.text = String.format("Volatility Weight 1: %.2f", this.volatilityWeight1);
        this.sliderVolatilityWeight2.text = String.format("Volatility Weight 2: %.2f", this.volatilityWeight2);
    }

    @Override
    public void onClose() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (!guibutton.active)
            return;
        if (guibutton.id == 1) {
            this.minecraft.openScreen(this.parent);
        } else if (guibutton.id == 0) {
            this.minecraft.openScreen(null); //TODO: Unsure about mappings (openScreen or setLevel)
            if (this.createClicked)
                return;
            this.createClicked = true;
            long l = (new Random()).nextLong();
            String s = this.textboxSeed.method_1876();
            if (!MathsHelper.isStringEmpty(s))
                try {
                    long l1 = Long.parseLong(s);
                    if (l1 != 0L)
                        l = l1;
                } catch (NumberFormatException numberformatexception) {
                    l = s.hashCode();
                }
            this.minecraft.interactionManager = new class_520(this.minecraft);
            DebugMode.levelEditing = true;
            String mapName = this.textboxMapName.method_1876().trim();
            this.minecraft.saveMapUsed(mapName, mapName);
            Level w = this.minecraft.getWorld(mapName, l, mapName);
            w.properties.useImages = false;
            w.properties.mapSize = this.mapSize;
            w.properties.waterLevel = this.waterLevel;
            w.properties.fractureHorizontal = this.fractureHorizontal;
            w.properties.fractureVertical = this.fractureVertical;
            w.properties.maxAvgDepth = this.maxAvgDepth;
            w.properties.maxAvgHeight = this.maxAvgHeight;
            w.properties.volatility1 = this.volatility1;
            w.properties.volatility2 = this.volatility2;
            w.properties.volatilityWeight1 = this.volatilityWeight1;
            w.properties.volatilityWeight2 = this.volatilityWeight2;
            w.updateChunkProvider();
            this.minecraft.notifyStatus(w, "Generating level");
            this.minecraft.openScreen(null); //TODO: Unsure about mappings (openScreen or setLevel; setLevel would nullify notifyStatus)
        }
    }

    @Override
    protected void keyPressed(char c, int i) {
        this.textboxMapName.method_1877(c, i);
        this.textboxSeed.method_1877(c, i);
        if (c == '\r')
            buttonClicked((Button)this.buttons.get(0));
        ((Button) this.buttons.get(0)).active = (this.textboxMapName.method_1876().length() > 0);
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        this.textboxMapName.method_1879(i, j, k);
        this.textboxSeed.method_1879(i, j, k);
    }

    @Override
    public void render(int i, int j, float f) {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        renderBackground();
        drawTextWithShadow(this.textManager, "Create Random Map", this.width / 2, 20, 16777215);
        String enterName = "Map Name:";
        String enterSeed = "Seed:";
        drawTextWithShadow(this.textManager, enterName, this.width / 2 - 110 - this.textManager.getTextWidth(enterName), 44, 10526880);
        drawTextWithShadow(this.textManager, enterSeed, this.width / 2 - 110 - this.textManager.getTextWidth(enterSeed), 68, 10526880);
        this.textboxMapName.method_1883();
        this.textboxSeed.method_1883();
        updateSliders();
        super.render(i, j, f);
    }
}
