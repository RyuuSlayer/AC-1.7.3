package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityCamera;
import net.minecraft.client.Minecraft;

public class GuiCameraBlock extends da {
    private TileEntityCamera cam;

    public GuiCameraBlock(TileEntityCamera c) {
        this.cam = c;
    }

    public void b() {
        ke b = new ke(0, 4, 4, 160, 18, "Skip to first point");
        if (this.cam.type == 1) {
            b.e = "Linear Interpolation";
        } else if (this.cam.type == 2) {
            b.e = "Quadratic Interpolation";
        }
        this.e.add(b);
        b = new ke(1, 4, 24, 160, 18, "Pause Game");
        if (!this.cam.pauseGame)
            b.e = "Game Runs";
        this.e.add(b);
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            this.cam.type = (this.cam.type + 1) % 3;
            if (this.cam.type == 1) {
                guibutton.e = "Linear Interpolation";
            } else if (this.cam.type == 2) {
                guibutton.e = "Quadratic Interpolation";
            } else {
                guibutton.e = "Skip to first point";
            }
        } else if (guibutton.f == 1) {
            this.cam.pauseGame = !this.cam.pauseGame;
            guibutton.e = "Pause Game";
            if (!this.cam.pauseGame)
                guibutton.e = "Game Runs";
        }
        this.cam.d.b(this.cam.e, this.cam.g).g();
    }

    public void a(int i, int j, float f) {
        i();
        super.a(i, j, f);
    }

    public static void showUI(TileEntityCamera c) {
        Minecraft.minecraftInstance.a(new GuiCameraBlock(c));
    }
}