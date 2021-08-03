package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerPushable;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import net.minecraft.client.Minecraft;

public class GuiTriggerPushable extends da {
    private TileEntityTriggerPushable trigger;

    public GuiTriggerPushable(TileEntityTriggerPushable triggerClicked) {
        this.trigger = triggerClicked;
    }

    public void a() {}

    public void b() {
        this.e.add(new ab(0, 4, 40, "Use Current Selection"));
        ab b = new ab(1, 4, 60, "Trigger Target");
        if (this.trigger.resetOnTrigger)
            b.e = "Reset Target";
        this.e.add(b);
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            this.trigger.minX = ItemCursor.minX;
            this.trigger.minY = ItemCursor.minY;
            this.trigger.minZ = ItemCursor.minZ;
            this.trigger.maxX = ItemCursor.maxX;
            this.trigger.maxY = ItemCursor.maxY;
            this.trigger.maxZ = ItemCursor.maxZ;
        } else if (guibutton.f == 1) {
            this.trigger.resetOnTrigger = !this.trigger.resetOnTrigger;
            if (this.trigger.resetOnTrigger) {
                guibutton.e = "Reset Target";
            } else {
                guibutton.e = "Trigger Target";
            }
        }
        this.trigger.d.b(this.trigger.e, this.trigger.g).g();
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        b(this.g, String.format("Min: (%d, %d, %d)", new Object[] { Integer.valueOf(this.trigger.minX), Integer.valueOf(this.trigger.minY), Integer.valueOf(this.trigger.minZ) }), 4, 4, 14737632);
        b(this.g, String.format("Max: (%d, %d, %d)", new Object[] { Integer.valueOf(this.trigger.maxX), Integer.valueOf(this.trigger.maxY), Integer.valueOf(this.trigger.maxZ) }), 4, 24, 14737632);
        super.a(i, j, f);
    }

    public static void showUI(TileEntityTriggerPushable triggerClicked) {
        Minecraft.minecraftInstance.a(new GuiTriggerPushable(triggerClicked));
    }

    public boolean c() {
        return false;
    }
}
