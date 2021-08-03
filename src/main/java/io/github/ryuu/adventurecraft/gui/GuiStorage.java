package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityStorage;
import net.minecraft.client.Minecraft;

public class GuiStorage extends da {
    private TileEntityStorage storage;

    public GuiStorage(TileEntityStorage storageClicked) {
        this.storage = storageClicked;
    }

    public void a() {}

    public void b() {
        this.e.add(new ab(0, 4, 40, "Use Current Selection"));
        this.e.add(new ab(1, 4, 60, "Resave Set Selection"));
        this.e.add(new ab(2, 4, 80, "Load Saved Data"));
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            this.storage.setArea();
        } else if (guibutton.f == 1) {
            this.storage.saveCurrentArea();
        } else if (guibutton.f == 2) {
            this.storage.loadCurrentArea();
        }
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        b(this.g, String.format("Min: (%d, %d, %d)", new Object[] { Integer.valueOf(this.storage.minX), Integer.valueOf(this.storage.minY), Integer.valueOf(this.storage.minZ) }), 4, 4, 14737632);
        b(this.g, String.format("Max: (%d, %d, %d)", new Object[] { Integer.valueOf(this.storage.maxX), Integer.valueOf(this.storage.maxY), Integer.valueOf(this.storage.maxZ) }), 4, 24, 14737632);
        super.a(i, j, f);
    }

    public static void showUI(TileEntityStorage storageClicked) {
        Minecraft.minecraftInstance.a(new GuiStorage(storageClicked));
    }

    public boolean c() {
        return false;
    }
}
