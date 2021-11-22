package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.util.TriggerArea;

public class TileEntityTimer extends TileEntityMinMax {

    public int ticks;

    public int timeActive;

    public int timeDeactive;

    public int timeDelay;

    public int ticksDelay;

    public boolean active = false;

    public boolean canActivate = true;

    public boolean resetOnTrigger;

    public void startActive() {
        this.active = true;
        this.ticks = this.timeActive;
        this.ticksDelay = this.timeDelay + 1;
    }

    @Override
    public void tick() {
        if (this.ticksDelay > 0) {
            --this.ticksDelay;
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
                if (!this.resetOnTrigger) {
                    this.level.triggerManager.removeArea(this.x, this.y, this.z);
                }
            } else {
                this.canActivate = true;
            }
        } else {
            --this.ticks;
        }
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.resetOnTrigger = tag.getBoolean("resetOnTrigger");
        this.timeActive = tag.getInt("timeActive");
        this.timeDeactive = tag.getInt("timeDeactive");
        this.timeDelay = tag.getInt("timeDelay");
        this.ticks = tag.getInt("ticks");
        this.ticksDelay = tag.getInt("ticksDelay");
        this.active = tag.getBoolean("active");
        this.canActivate = tag.getBoolean("canActivate");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("resetOnTrigger", this.resetOnTrigger);
        tag.put("timeActive", this.timeActive);
        tag.put("timeDeactive", this.timeDeactive);
        tag.put("timeDelay", this.timeDelay);
        tag.put("ticks", this.ticks);
        tag.put("ticksDelay", this.ticksDelay);
        tag.put("active", this.active);
        tag.put("canActivate", this.canActivate);
    }
}
