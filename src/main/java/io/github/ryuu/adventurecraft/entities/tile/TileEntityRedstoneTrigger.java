package io.github.ryuu.adventurecraft.entities.tile;

public class TileEntityRedstoneTrigger extends TileEntityMinMax {
    public boolean isActivated = false;

    public boolean visited;

    public boolean resetOnTrigger;

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.m("ResetOnTrigger");
        this.isActivated = nbttagcompound.m("IsActivated");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("ResetOnTrigger", this.resetOnTrigger);
        nbttagcompound.a("IsActivated", this.isActivated);
    }
}
