package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class ItemTriggerStick extends ItemType {
    protected ItemTriggerStick(int i) {
        super(i);
        a(5, 3);
    }

    public boolean onItemUseLeftClick(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        Minecraft.minecraftInstance.v.a(String.format("Triggering (%d, %d, %d)", new Object[]{Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)}));
        world.triggerManager.addArea(0, -1, 0, new TriggerArea(i, j, k, i, j, k));
        world.triggerManager.removeArea(0, -1, 0);
        return false;
    }

    public boolean a(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        Minecraft.minecraftInstance.v.a(String.format("Checking (%d, %d, %d)", new Object[]{Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)}));
        world.triggerManager.outputTriggerSources(i, j, k);
        return false;
    }
}
