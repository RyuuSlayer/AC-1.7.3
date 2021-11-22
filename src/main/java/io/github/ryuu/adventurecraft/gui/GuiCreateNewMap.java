package io.github.ryuu.adventurecraft.gui;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.input.Keyboard;
import io.github.ryuu.adventurecraft.util.DebugMode;

public class GuiCreateNewMap extends Screen {

    private Screen parent;

    private Textbox textboxMapName;

    private Textbox textboxSeed;

    private String folderName;

    private boolean createClicked;

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
        Keyboard.enableRepeatEvents((boolean) true);
        this.buttons.clear();
        this.buttons.add((Object) new Button(0, this.width / 2 - 205, 200, "Create Map"));
        this.buttons.add((Object) new Button(1, this.width / 2 + 5, 200, stringtranslate.translate("gui.cancel")));
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
        this.buttons.add((Object) this.sliderMapSize);
        this.buttons.add((Object) this.sliderWaterLevel);
        this.buttons.add((Object) this.sliderFracHorizontal);
        this.buttons.add((Object) this.sliderFracVertical);
        this.buttons.add((Object) this.sliderMaxAvgDepth);
        this.buttons.add((Object) this.sliderMaxAvgHeight);
        this.buttons.add((Object) this.sliderVolatility1);
        this.buttons.add((Object) this.sliderVolatility2);
        this.buttons.add((Object) this.sliderVolatilityWeight1);
        this.buttons.add((Object) this.sliderVolatilityWeight2);
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
        this.sliderMapSize.text = String.format((String) "Map Size: %.1f", (Object[]) new Object[] { this.mapSize });
        this.sliderWaterLevel.text = String.format((String) "Water Level: %d", (Object[]) new Object[] { this.waterLevel });
        this.sliderFracHorizontal.text = String.format((String) "Fracture Horizontal: %.2f", (Object[]) new Object[] { this.fractureHorizontal });
        this.sliderFracVertical.text = String.format((String) "Fracture Vertical: %.2f", (Object[]) new Object[] { this.fractureVertical });
        this.sliderMaxAvgDepth.text = String.format((String) "Max Avg Depth: %.2f", (Object[]) new Object[] { this.maxAvgDepth });
        this.sliderMaxAvgHeight.text = String.format((String) "Max Avg Height: %.2f", (Object[]) new Object[] { this.maxAvgHeight });
        this.sliderVolatility1.text = String.format((String) "Volatility 1: %.2f", (Object[]) new Object[] { this.volatility1 });
        this.sliderVolatility2.text = String.format((String) "Volatility 2: %.2f", (Object[]) new Object[] { this.volatility2 });
        this.sliderVolatilityWeight1.text = String.format((String) "Volatility Weight 1: %.2f", (Object[]) new Object[] { this.volatilityWeight1 });
        this.sliderVolatilityWeight2.text = String.format((String) "Volatility Weight 2: %.2f", (Object[]) new Object[] { this.volatilityWeight2 });
    }

    @Override
    public void onClose() {
        Keyboard.enableRepeatEvents((boolean) false);
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
                    long l1 = Long.parseLong((String) s);
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
        ((Button) this.buttons.get((int) 0)).active = this.textboxMapName.method_1876().length() > 0;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        this.textboxMapName.method_1879(mouseX, mouseY, button);
        this.textboxSeed.method_1879(mouseX, mouseY, button);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
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
