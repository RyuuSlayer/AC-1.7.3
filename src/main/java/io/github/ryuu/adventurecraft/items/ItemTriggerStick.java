package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.extensions.items.LeftClickUseItemType;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class ItemTriggerStick extends ItemType implements LeftClickUseItemType {

    protected ItemTriggerStick(int id) {
        super(id);
        this.setTexturePosition(5, 3);
    }

    @Override
    public boolean onItemUseLeftClick(ItemInstance itemstack, Player entityplayer, Level level, int i, int j, int k, int l) {
        AccessMinecraft.getInstance().overlay.addChatMessage(String.format("Triggering (%d, %d, %d)", i, j, k));
        ((ExLevel) level).getTriggerManager().addArea(0, -1, 0, new TriggerArea(i, j, k, i, j, k));
        ((ExLevel) level).getTriggerManager().removeArea(0, -1, 0);
        return false;
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        AccessMinecraft.getInstance().overlay.addChatMessage(String.format("Checking (%d, %d, %d)", x, y, z));
        ((ExLevel) level).getTriggerManager().outputTriggerSources(x, y, z);
        return false;
    }
}
