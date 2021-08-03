package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.client.Minecraft;

public class ItemTriggerStick extends gm {
    protected ItemTriggerStick(int i) {
        super(i);
        a(5, 3);
    }

    public boolean onItemUseLeftClick(iz itemstack, gs entityplayer, fd world, int i, int j, int k, int l) {
        Minecraft.minecraftInstance.v.a(String.format("Triggering (%d, %d, %d)", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) }));
        world.triggerManager.addArea(0, -1, 0, new TriggerArea(i, j, k, i, j, k));
        world.triggerManager.removeArea(0, -1, 0);
        return false;
    }

    public boolean a(iz itemstack, gs entityplayer, fd world, int i, int j, int k, int l) {
        Minecraft.minecraftInstance.v.a(String.format("Checking (%d, %d, %d)", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) }));
        world.triggerManager.outputTriggerSources(i, j, k);
        return false;
    }
}
