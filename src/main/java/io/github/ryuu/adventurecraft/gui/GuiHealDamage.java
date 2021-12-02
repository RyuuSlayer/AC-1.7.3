package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityHealDamage;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.level.Level;

public class GuiHealDamage extends Screen {

    private final TileEntityHealDamage tileEnt;
    GuiSlider2 healDamage;

    public GuiHealDamage(TileEntityHealDamage t) {
        this.tileEnt = t;
    }

    public static void showUI(Level level, TileEntityHealDamage w) {
        AccessMinecraft.getInstance().openScreen(new GuiHealDamage(w));
    }

    @Override
    public void init() {
        this.healDamage = new GuiSlider2(4, 4, 4, 10, String.format("Heal: %d", this.tileEnt.healDamage), (float) (this.tileEnt.healDamage + 40) / 80.0f);
        if (this.tileEnt.healDamage < 0) {
            this.healDamage.text = String.format("Damage: %d", -this.tileEnt.healDamage);
        }
        this.buttons.add(this.healDamage);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.tileEnt.healDamage = (int) ((double) this.healDamage.sliderValue * 80.0 - 40.0);
        this.healDamage.text = this.tileEnt.healDamage < 0 ? String.format("Damage: %d", -this.tileEnt.healDamage) : String.format("Heal: %d", this.tileEnt.healDamage);
        super.render(mouseX, mouseY, delta);
        this.tileEnt.level.getChunk(this.tileEnt.x, this.tileEnt.z).method_885();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
