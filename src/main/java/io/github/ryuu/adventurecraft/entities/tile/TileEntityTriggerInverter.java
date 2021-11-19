package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.blocks.Blocks;

public class TileEntityTriggerInverter extends TileEntityMinMax {
    public void set(int x1, int y1, int z1, int x2, int y2, int z2) {
        int metadata = this.level.getTileMeta(this.x, this.y, this.z);
        if (isSet())
            if (metadata <= 0)
                Blocks.triggerInverter.onTriggerActivated(this.level, this.x, this.y, this.z);
        this.minX = x1;
        this.minY = y1;
        this.minZ = z1;
        this.maxX = x2;
        this.maxY = y2;
        this.maxZ = z2;
        if (metadata <= 0)
            Blocks.triggerInverter.onTriggerDeactivated(this.level, this.x, this.y, this.z);
    }
}
