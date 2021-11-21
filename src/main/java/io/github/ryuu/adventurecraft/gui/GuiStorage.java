package io.github.ryuu.adventurecraft.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;

public class GuiStorage extends Screen {

    private TileEntityStorage storage;

    public GuiStorage(TileEntityStorage storageClicked) {
        this.storage = storageClicked;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.buttons.add((Object) new OptionButton(0, 4, 40, "Use Current Selection"));
        this.buttons.add((Object) new OptionButton(1, 4, 60, "Resave Set Selection"));
        this.buttons.add((Object) new OptionButton(2, 4, 80, "Load Saved Data"));
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            this.storage.setArea();
        } else if (button.id == 1) {
            this.storage.saveCurrentArea();
        } else if (button.id == 2) {
            this.storage.loadCurrentArea();
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.drawTextWithShadow(this.textManager, String.format((String) "Min: (%d, %d, %d)", (Object[]) new Object[] { this.storage.minX, this.storage.minY, this.storage.minZ }), 4, 4, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format((String) "Max: (%d, %d, %d)", (Object[]) new Object[] { this.storage.maxX, this.storage.maxY, this.storage.maxZ }), 4, 24, 0xE0E0E0);
        super.render(mouseX, mouseY, delta);
    }

    public static void showUI(TileEntityStorage storageClicked) {
        Minecraft.minecraftInstance.openScreen(new GuiStorage(storageClicked));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
