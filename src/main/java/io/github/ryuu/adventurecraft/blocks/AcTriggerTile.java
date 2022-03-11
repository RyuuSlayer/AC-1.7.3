package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;

public interface AcTriggerTile {

    boolean canBeTriggered(); // default: return false

    void onTriggerActivated(Level world, int i, int j, int k);

    void onTriggerDeactivated(Level world, int i, int j, int k);
}
