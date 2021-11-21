package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class ItemQuill extends ItemType {

    protected ItemQuill(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        double yToUse = 128.0;
        for (int y2 = y; y2 <= 128; ++y2) {
            if (level.getTileId(x, y2, z) != 0)
                continue;
            yToUse = (float) y2 + player.standingEyeHeight;
            break;
        }
        Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "Teleporting to (%.1f, %.1f %.1f)", (Object[]) new Object[] { (double) x + 0.5, yToUse, (double) z + 0.5 }));
        player.setPosition((double) x + 0.5, yToUse, (double) z + 0.5);
        return false;
    }
}
