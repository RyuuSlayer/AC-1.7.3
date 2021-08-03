package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.client.Minecraft;

public class TileEntityTrigger extends TileEntityMinMax {
    public int activated = 0;

    public boolean visited;

    public boolean resetOnTrigger;

    public void n_() {
        if (this.activated > 0 && !Minecraft.minecraftInstance.cameraActive) {
            this.activated--;
            if (this.activated == 0 && this.d.a(this.e, this.f, this.g) == Blocks.triggerBlock.bn)
                Blocks.triggerBlock.deactivateTrigger(this.d, this.e, this.f, this.g);
        }
    }

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.m("ResetOnTrigger");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("ResetOnTrigger", this.resetOnTrigger);
    }
}
