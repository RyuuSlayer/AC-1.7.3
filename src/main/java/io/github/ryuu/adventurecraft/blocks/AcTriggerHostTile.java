package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;

public interface AcTriggerHostTile extends AcTriggerTile {

    void addTriggerActivation(Level world, int i, int j, int k);

    void removeTriggerActivation(Level world, int i, int j, int k);
}
