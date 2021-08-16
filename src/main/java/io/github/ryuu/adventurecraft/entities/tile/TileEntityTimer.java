package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;

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

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.getBoolean("resetOnTrigger");
        this.timeActive = nbttagcompound.getInt("timeActive");
        this.timeDeactive = nbttagcompound.getInt("timeDeactive");
        this.timeDelay = nbttagcompound.getInt("timeDelay");
        this.ticks = nbttagcompound.getInt("ticks");
        this.ticksDelay = nbttagcompound.getInt("ticksDelay");
        this.active = nbttagcompound.getBoolean("active");
        this.canActivate = nbttagcompound.getBoolean("canActivate");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("resetOnTrigger", this.resetOnTrigger);
        nbttagcompound.put("timeActive", this.timeActive);
        nbttagcompound.put("timeDeactive", this.timeDeactive);
        nbttagcompound.put("timeDelay", this.timeDelay);
        nbttagcompound.put("ticks", this.ticks);
        nbttagcompound.put("ticksDelay", this.ticksDelay);
        nbttagcompound.put("active", this.active);
        nbttagcompound.put("canActivate", this.canActivate);
    }
}
