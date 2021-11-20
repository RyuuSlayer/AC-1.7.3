package io.github.ryuu.adventurecraft.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.level.Level;

public class GuiHealDamage extends MixinScreen {

    private TileEntityHealDamage tileEnt;

    GuiSlider2 healDamage;

    public GuiHealDamage(TileEntityHealDamage t) {
        this.tileEnt = t;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.healDamage = new GuiSlider2(4, 4, 4, 10, String.format((String) "Heal: %d", (Object[]) new Object[] { this.tileEnt.healDamage }), (float) (this.tileEnt.healDamage + 40) / 80.0f);
        if (this.tileEnt.healDamage < 0) {
            this.healDamage.text = String.format((String) "Damage: %d", (Object[]) new Object[] { -this.tileEnt.healDamage });
        }
        this.buttons.add((Object) this.healDamage);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.tileEnt.healDamage = (int) ((double) this.healDamage.sliderValue * 80.0 - 40.0);
        this.healDamage.text = this.tileEnt.healDamage < 0 ? String.format((String) "Damage: %d", (Object[]) new Object[] { -this.tileEnt.healDamage }) : String.format((String) "Heal: %d", (Object[]) new Object[] { this.tileEnt.healDamage });
        super.render(mouseX, mouseY, delta);
        this.tileEnt.level.getChunk(this.tileEnt.x, this.tileEnt.z).method_885();
    }

    public static void showUI(MixinLevel worldArg, TileEntityHealDamage w) {
        Minecraft.minecraftInstance.openScreen(new GuiHealDamage(w));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
