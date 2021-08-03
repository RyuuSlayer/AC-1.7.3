package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityHealDamage;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;

public class GuiHealDamage extends da {
    private final TileEntityHealDamage tileEnt;

    GuiSlider2 healDamage;

    public GuiHealDamage(TileEntityHealDamage t) {
        this.tileEnt = t;
    }

    public void a() {
    }

    public void b() {
        this.healDamage = new GuiSlider2(4, 4, 4, 10, String.format("Heal: %d", Integer.valueOf(this.tileEnt.healDamage)), (this.tileEnt.healDamage + 40) / 80.0F);
        if (this.tileEnt.healDamage < 0)
            this.healDamage.e = String.format("Damage: %d", new Object[]{Integer.valueOf(-this.tileEnt.healDamage)});
        this.e.add(this.healDamage);
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        this.tileEnt.healDamage = (int) (this.healDamage.sliderValue * 80.0D - 40.0D);
        if (this.tileEnt.healDamage < 0) {
            this.healDamage.e = String.format("Damage: %d", new Object[]{Integer.valueOf(-this.tileEnt.healDamage)});
        } else {
            this.healDamage.e = String.format("Heal: %d", new Object[]{Integer.valueOf(this.tileEnt.healDamage)});
        }
        super.a(i, j, f);
        this.tileEnt.d.b(this.tileEnt.e, this.tileEnt.g).g();
    }

    public static void showUI(Level worldArg, TileEntityHealDamage w) {
        Minecraft.minecraftInstance.a(new GuiHealDamage(w));
    }

    public boolean c() {
        return false;
    }
}
