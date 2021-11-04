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

    @Override
    public void tick() {
        if (this.ticksDelay > 0) {
            this.ticksDelay--;
            if (this.ticksDelay == 0) {
                if (!this.resetOnTrigger) {
                    this.level.triggerManager.addArea(this.x, this.y, this.z, new TriggerArea(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ));
                } else {
                    Tile.resetArea(this.level, this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
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
                    this.levle.triggerManager.removeArea(this.x, this.y, this.z);
            } else {
                this.canActivate = true;
            }
        } else {
            this.ticks--;
        }
    }

    @Override
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.getBoolean("resetOnTrigger");
        this.timeActive = nbttagcompound.getInt("timeActive");
        this.timeDeactive = nbttagcompound.getInt("timeDeactive");
        this.timeDelay = nbttagcompound.getInt("timeDelay");
        this.ticks = nbttagcompound.getInt("ticks");
        this.ticksDelay = nbttagcompound.getInt("ticksDelay");
        this.active = nbttagcompound.getBoolean("active");
        this.canActivate = nbttagcompound.getBoolean("canActivate");
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
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
