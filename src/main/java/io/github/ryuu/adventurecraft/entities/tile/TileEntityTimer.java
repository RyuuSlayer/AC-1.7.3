package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.tile.Tile;

public class TileEntityTimer extends TileEntityMinMax {
    public boolean resetOnTrigger;

    public boolean canActivate = true;

    public boolean active = false;

    public int ticksDelay;

    public int timeDelay;

    public int timeDeactive;

    public int timeActive;

    public int ticks;

    public void startActive() {
        this.active = true;
        this.ticks = this.timeActive;
        this.ticksDelay = this.timeDelay + 1;
    }

    public void n_() {
        if (this.ticksDelay > 0) {
            this.ticksDelay--;
            if (this.ticksDelay == 0) {
                if (!this.resetOnTrigger) {
                    this.d.triggerManager.addArea(this.e, this.f, this.g, new TriggerArea(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ));
                } else {
                    Tile.resetArea(this.d, this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
                }
            } else {
                return;
            }
        }
        if (this.ticks == 0) {
            if (this.active) {
                this.active = false;
                this.canActivate = false;
                this.ticks = this.timeDeactive;
                if (!this.resetOnTrigger)
                    this.d.triggerManager.removeArea(this.e, this.f, this.g);
            } else {
                this.canActivate = true;
            }
        } else {
            this.ticks--;
        }
    }

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.m("resetOnTrigger");
        this.timeActive = nbttagcompound.e("timeActive");
        this.timeDeactive = nbttagcompound.e("timeDeactive");
        this.timeDelay = nbttagcompound.e("timeDelay");
        this.ticks = nbttagcompound.e("ticks");
        this.ticksDelay = nbttagcompound.e("ticksDelay");
        this.active = nbttagcompound.m("active");
        this.canActivate = nbttagcompound.m("canActivate");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("resetOnTrigger", this.resetOnTrigger);
        nbttagcompound.a("timeActive", this.timeActive);
        nbttagcompound.a("timeDeactive", this.timeDeactive);
        nbttagcompound.a("timeDelay", this.timeDelay);
        nbttagcompound.a("ticks", this.ticks);
        nbttagcompound.a("ticksDelay", this.ticksDelay);
        nbttagcompound.a("active", this.active);
        nbttagcompound.a("canActivate", this.canActivate);
    }
}
