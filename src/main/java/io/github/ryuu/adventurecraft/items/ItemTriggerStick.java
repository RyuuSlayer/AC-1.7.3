package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class ItemTriggerStick extends ItemType {

    protected ItemTriggerStick(int id) {
        super(id);
        this.setTexturePosition(5, 3);
    }

    @Override
    public boolean onItemUseLeftClick(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Triggering (%d, %d, %d)", new Object[]{i, j, k}));
        world.triggerManager.addArea(0, -1, 0, new TriggerArea(i, j, k, i, j, k));
        world.triggerManager.removeArea(0, -1, 0);
        return false;
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Checking (%d, %d, %d)", new Object[]{x, y, z}));
        level.triggerManager.outputTriggerSources(x, y, z);
        return false;
    }
}
