package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;

public class GuiCameraBlock extends Screen {
    private final TileEntityCamera cam;

    public GuiCameraBlock(TileEntityCamera c) {
        this.cam = c;
    }

    public static void showUI(TileEntityCamera c) {
        Minecraft.minecraftInstance.a(new GuiCameraBlock(c));
    }

    @Override
    public void init() {
        Button b = new Button(0, 4, 4, 160, 18, "Skip to first point");
        if (this.cam.type == 1) {
            b.text = "Linear Interpolation";
        } else if (this.cam.type == 2) {
            b.text = "Quadratic Interpolation";
        }
        this.buttons.add(b);
        b = new Button(1, 4, 24, 160, 18, "Pause Game");
        if (!this.cam.pauseGame)
            b.text = "Game Runs";
        this.buttons.add(b);
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0) {
            this.cam.type = (this.cam.type + 1) % 3;
            if (this.cam.type == 1) {
                guibutton.text = "Linear Interpolation";
            } else if (this.cam.type == 2) {
                guibutton.text = "Quadratic Interpolation";
            } else {
                guibutton.text = "Skip to first point";
            }
        } else if (guibutton.id == 1) {
            this.cam.pauseGame = !this.cam.pauseGame;
            guibutton.text = "Pause Game";
            if (!this.cam.pauseGame)
                guibutton.text = "Game Runs";
        }
        this.cam.level.getChunk(this.cam.x, this.cam.z).method_885();
    }

    @Override
    public void render(int i, int j, float f) {
        renderBackground();
        super.render(i, j, f);
    }
}