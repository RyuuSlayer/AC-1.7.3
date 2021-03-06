package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.class_520;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.Textbox;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class GuiCreateNewMap extends Screen {

    private final Screen parent;

    public double mapSize = 250.0;
    public int waterLevel = 64;
    public double fractureHorizontal = 1.0;
    public double fractureVertical = 1.0;
    public double maxAvgDepth = 0.0;
    public double maxAvgHeight = 0.0;
    public double volatility1 = 1.0;
    public double volatility2 = 1.0;
    public double volatilityWeight1 = 0.0;
    public double volatilityWeight2 = 1.0;

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
    private Textbox textboxMapName;
    private Textbox textboxSeed;
    private String folderName;
    private boolean createClicked;

    public GuiCreateNewMap(Screen guiscreen) {
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
        this.sliderMapSize = new GuiSlider2(2, xPos1, 88, 10, "", (float) (this.mapSize - 100.0) / 650.0f);
        this.sliderWaterLevel = new GuiSlider2(2, xPos2, 88, 10, "", (float) this.waterLevel / 128.0f);
        this.sliderFracHorizontal = new GuiSlider2(2, xPos1, 109, 10, "", (float) this.fractureHorizontal / 2.0f);
        this.sliderFracVertical = new GuiSlider2(2, xPos2, 109, 10, "", (float) this.fractureVertical / 2.0f);
        this.sliderMaxAvgDepth = new GuiSlider2(2, xPos1, 130, 10, "", (float) (this.maxAvgDepth + 5.0) / 10.0f);
        this.sliderMaxAvgHeight = new GuiSlider2(2, xPos2, 130, 10, "", (float) (this.maxAvgHeight + 5.0) / 10.0f);
        this.sliderVolatility1 = new GuiSlider2(2, xPos1, 151, 10, "", (float) this.volatility1 / 5.0f);
        this.sliderVolatility2 = new GuiSlider2(2, xPos2, 151, 10, "", (float) this.volatility2 / 5.0f);
        this.sliderVolatilityWeight1 = new GuiSlider2(2, xPos1, 172, 10, "", (float) (this.volatilityWeight1 + 0.5));
        this.sliderVolatilityWeight2 = new GuiSlider2(2, xPos2, 172, 10, "", (float) (this.volatilityWeight2 - 0.5));
        this.updateSliders();
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
        this.mapSize = this.sliderMapSize.sliderValue * 650.0f + 100.0f;
        this.waterLevel = (int) (128.0f * this.sliderWaterLevel.sliderValue);
        this.fractureHorizontal = (double) this.sliderFracHorizontal.sliderValue * 2.0;
        this.fractureVertical = (double) this.sliderFracVertical.sliderValue * 2.0;
        this.maxAvgDepth = (double) this.sliderMaxAvgDepth.sliderValue * 10.0 - 5.0;
        this.maxAvgHeight = (double) this.sliderMaxAvgHeight.sliderValue * 10.0 - 5.0;
        this.volatility1 = (double) this.sliderVolatility1.sliderValue * 5.0;
        this.volatility2 = (double) this.sliderVolatility2.sliderValue * 5.0;
        this.volatilityWeight1 = (double) this.sliderVolatilityWeight1.sliderValue - 0.5;
        this.volatilityWeight2 = (double) this.sliderVolatilityWeight2.sliderValue + 0.5;
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
    protected void buttonClicked(Button button) {
        if (!button.active) {
            return;
        }
        if (button.id == 1) {
            this.minecraft.openScreen(this.parent);
        } else if (button.id == 0) {
            this.minecraft.openScreen(null);
            if (this.createClicked) {
                return;
            }
            this.createClicked = true;
            long l = new Random().nextLong();
            String s = this.textboxSeed.method_1876();
            if (!MathsHelper.isStringEmpty(s)) {
                try {
                    long l1 = Long.parseLong(s);
                    if (l1 != 0L) {
                        l = l1;
                    }
                } catch (NumberFormatException numberformatexception) {
                    l = s.hashCode();
                }
            }
            this.minecraft.interactionManager = new class_520(this.minecraft);
            DebugMode.levelEditing = true;
            String mapName = this.textboxMapName.method_1876().trim();
            ExMinecraft mc = (ExMinecraft) this.minecraft;
            mc.saveMapUsed(mapName, mapName);
            Level w = mc.getWorld(mapName, l, mapName);
            ExLevelProperties properties = (ExLevelProperties) w.getProperties();
            properties.setUseBiomeImages(false);
            properties.setMapSize(this.mapSize);
            properties.setWaterLevel(this.waterLevel);
            properties.setFractureHorizontal(this.fractureHorizontal);
            properties.setFractureVertical(this.fractureVertical);
            properties.setMaxAvgDepth(this.maxAvgDepth);
            properties.setMaxAvgHeight(this.maxAvgHeight);
            properties.setVolatility1(this.volatility1);
            properties.setVolatility2(this.volatility2);
            properties.setVolatilityWeight1(this.volatilityWeight1);
            properties.setVolatilityWeight2(this.volatilityWeight2);
            ((ExLevel) w).updateChunkProvider();
            this.minecraft.notifyStatus(w, "Generating level");
            this.minecraft.openScreen(null);
        }
    }

    @Override
    protected void keyPressed(char character, int key) {
        this.textboxMapName.method_1877(character, key);
        this.textboxSeed.method_1877(character, key);
        if (character == '\r') {
            this.buttonClicked((Button) this.buttons.get(0));
        }
        ((Button) this.buttons.get(0)).active = this.textboxMapName.method_1876().length() > 0;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        this.textboxMapName.method_1879(mouseX, mouseY, button);
        this.textboxSeed.method_1879(mouseX, mouseY, button);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        this.drawTextWithShadowCentred(this.textManager, "Create Random Map", this.width / 2, 20, 0xFFFFFF);
        String enterName = "Map Name:";
        String enterSeed = "Seed:";
        this.drawTextWithShadow(this.textManager, enterName, this.width / 2 - 110 - this.textManager.getTextWidth(enterName), 44, 0xA0A0A0);
        this.drawTextWithShadow(this.textManager, enterSeed, this.width / 2 - 110 - this.textManager.getTextWidth(enterSeed), 68, 0xA0A0A0);
        this.textboxMapName.method_1883();
        this.textboxSeed.method_1883();
        this.updateSliders();
        super.render(mouseX, mouseY, delta);
    }
}
