package io.github.ryuu.adventurecraft.gui;

import java.util.Random;

import net.minecraft.client.gui.Screen;
import org.lwjgl.input.Keyboard;

public class GuiCreateNewMap extends Screen {
    private final Screen parent;

    private ro textboxMapName;

    private ro textboxSeed;

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

    public void a() {
        this.textboxMapName.b();
        this.textboxSeed.b();
    }

    public void b() {
        nh stringtranslate = nh.a();
        Keyboard.enableRepeatEvents(true);
        this.e.clear();
        this.e.add(new ke(0, this.c / 2 - 205, 200, "Create Map"));
        this.e.add(new ke(1, this.c / 2 + 5, 200, stringtranslate.a("gui.cancel")));
        this.textboxMapName = new ro(this, this.g, this.c / 2 - 100, 38, 200, 20, stringtranslate.a("selectWorld.newWorld"));
        this.textboxMapName.a = true;
        this.textboxMapName.a(32);
        this.textboxSeed = new ro(this, this.g, this.c / 2 - 100, 62, 200, 20, "");
        int xPos1 = this.c / 2 - 4 - 150;
        int xPos2 = this.c / 2 + 4;
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
        this.e.add(this.sliderMapSize);
        this.e.add(this.sliderWaterLevel);
        this.e.add(this.sliderFracHorizontal);
        this.e.add(this.sliderFracVertical);
        this.e.add(this.sliderMaxAvgDepth);
        this.e.add(this.sliderMaxAvgHeight);
        this.e.add(this.sliderVolatility1);
        this.e.add(this.sliderVolatility2);
        this.e.add(this.sliderVolatilityWeight1);
        this.e.add(this.sliderVolatilityWeight2);
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
        this.sliderMapSize.e = String.format("Map Size: %.1f", new Object[]{Double.valueOf(this.mapSize)});
        this.sliderWaterLevel.e = String.format("Water Level: %d", new Object[]{Integer.valueOf(this.waterLevel)});
        this.sliderFracHorizontal.e = String.format("Fracture Horizontal: %.2f", new Object[]{Double.valueOf(this.fractureHorizontal)});
        this.sliderFracVertical.e = String.format("Fracture Vertical: %.2f", new Object[]{Double.valueOf(this.fractureVertical)});
        this.sliderMaxAvgDepth.e = String.format("Max Avg Depth: %.2f", new Object[]{Double.valueOf(this.maxAvgDepth)});
        this.sliderMaxAvgHeight.e = String.format("Max Avg Height: %.2f", new Object[]{Double.valueOf(this.maxAvgHeight)});
        this.sliderVolatility1.e = String.format("Volatility 1: %.2f", new Object[]{Double.valueOf(this.volatility1)});
        this.sliderVolatility2.e = String.format("Volatility 2: %.2f", new Object[]{Double.valueOf(this.volatility2)});
        this.sliderVolatilityWeight1.e = String.format("Volatility Weight 1: %.2f", new Object[]{Double.valueOf(this.volatilityWeight1)});
        this.sliderVolatilityWeight2.e = String.format("Volatility Weight 2: %.2f", new Object[]{Double.valueOf(this.volatilityWeight2)});
    }

    public void h() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void a(ke guibutton) {
        if (!guibutton.g)
            return;
        if (guibutton.f == 1) {
            this.b.a(this.parent);
        } else if (guibutton.f == 0) {
            this.b.a(null);
            if (this.createClicked)
                return;
            this.createClicked = true;
            long l = (new Random()).nextLong();
            String s = this.textboxSeed.a();
            if (!in.a(s))
                try {
                    long l1 = Long.parseLong(s);
                    if (l1 != 0L)
                        l = l1;
                } catch (NumberFormatException numberformatexception) {
                    l = s.hashCode();
                }
            this.b.c = new os(this.b);
            AC_DebugMode.levelEditing = true;
            String mapName = this.textboxMapName.a().trim();
            this.b.saveMapUsed(mapName, mapName);
            fd w = this.b.getWorld(mapName, l, mapName);
            w.x.useImages = false;
            w.x.mapSize = this.mapSize;
            w.x.waterLevel = this.waterLevel;
            w.x.fractureHorizontal = this.fractureHorizontal;
            w.x.fractureVertical = this.fractureVertical;
            w.x.maxAvgDepth = this.maxAvgDepth;
            w.x.maxAvgHeight = this.maxAvgHeight;
            w.x.volatility1 = this.volatility1;
            w.x.volatility2 = this.volatility2;
            w.x.volatilityWeight1 = this.volatilityWeight1;
            w.x.volatilityWeight2 = this.volatilityWeight2;
            w.updateChunkProvider();
            this.b.a(w, "Generating level");
            this.b.a(null);
        }
    }

    protected void a(char c, int i) {
        this.textboxMapName.a(c, i);
        this.textboxSeed.a(c, i);
        if (c == '\r')
            a(this.e.get(0));
        ((ke) this.e.get(0)).g = (this.textboxMapName.a().length() > 0);
    }

    protected void a(int i, int j, int k) {
        super.a(i, j, k);
        this.textboxMapName.a(i, j, k);
        this.textboxSeed.a(i, j, k);
    }

    public void a(int i, int j, float f) {
        nh stringtranslate = nh.a();
        i();
        a(this.g, "Create Random Map", this.c / 2, 20, 16777215);
        String enterName = "Map Name:";
        String enterSeed = "Seed:";
        b(this.g, enterName, this.c / 2 - 110 - this.g.a(enterName), 44, 10526880);
        b(this.g, enterSeed, this.c / 2 - 110 - this.g.a(enterSeed), 68, 10526880);
        this.textboxMapName.c();
        this.textboxSeed.c();
        updateSliders();
        super.a(i, j, f);
    }
}
