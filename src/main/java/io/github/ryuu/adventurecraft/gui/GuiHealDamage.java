package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityHealDamage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.level.Level;

public class GuiHealDamage extends Screen {
    private final TileEntityHealDamage tileEnt;

    GuiSlider2 healDamage;

    public GuiHealDamage(TileEntityHealDamage t) {
        this.tileEnt = t;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.healDamage = new GuiSlider2(4, 4, 4, 10, String.format("Heal: %d", this.tileEnt.healDamage), (this.tileEnt.healDamage + 40) / 80.0F);
        if (this.tileEnt.healDamage < 0)
            this.healDamage.text = String.format("Damage: %d", -this.tileEnt.healDamage);
        this.buttons.add(this.healDamage);
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        this.tileEnt.healDamage = (int) (this.healDamage.sliderValue * 80.0D - 40.0D);
        if (this.tileEnt.healDamage < 0) {
            this.healDamage.text = String.format("Damage: %d", -this.tileEnt.healDamage);
        } else {
            this.healDamage.text = String.format("Heal: %d", this.tileEnt.healDamage);
        }
        super.render(i, j, f);
        this.tileEnt.level.getChunk(this.tileEnt.x, this.tileEnt.z).method_885();
    }

    public static void showUI(Level worldArg, TileEntityHealDamage w) {
        Minecraft.minecraftInstance.a(new GuiHealDamage(w));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
