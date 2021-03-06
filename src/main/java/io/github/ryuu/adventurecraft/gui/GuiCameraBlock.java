package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityCamera;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;

public class GuiCameraBlock extends Screen {

    private final TileEntityCamera cam;

    public GuiCameraBlock(TileEntityCamera c) {
        this.cam = c;
    }

    public static void showUI(TileEntityCamera c) {
        AccessMinecraft.getInstance().openScreen(new GuiCameraBlock(c));
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
        if (!this.cam.pauseGame) {
            b.text = "Game Runs";
        }
        this.buttons.add(b);
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            this.cam.type = (this.cam.type + 1) % 3;
            button.text = this.cam.type == 1 ? "Linear Interpolation" : (this.cam.type == 2 ? "Quadratic Interpolation" : "Skip to first point");
        } else if (button.id == 1) {
            this.cam.pauseGame = !this.cam.pauseGame;
            button.text = "Pause Game";
            if (!this.cam.pauseGame) {
                button.text = "Game Runs";
            }
        }
        this.cam.level.getChunk(this.cam.x, this.cam.z).markDirty();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        super.render(mouseX, mouseY, delta);
    }
}
