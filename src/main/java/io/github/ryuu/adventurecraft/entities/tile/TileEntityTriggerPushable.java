package io.github.ryuu.adventurecraft.entities.tile;

public class TileEntityTriggerPushable extends TileEntityMinMax {
    public boolean activated;

    public boolean resetOnTrigger;

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.m("ResetOnTrigger");
        this.activated = nbttagcompound.m("activated");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("ResetOnTrigger", this.resetOnTrigger);
        nbttagcompound.a("activated", this.activated);
    }
}
