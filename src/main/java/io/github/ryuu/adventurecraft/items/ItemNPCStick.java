package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.gui.GuiNPC;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemNPCStick extends ItemType {
    public ItemNPCStick(int itemIndex) {
        super(itemIndex);
        a(5, 3);
        h();
    }

    public boolean a(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        EntityNPC npc = new EntityNPC(world);
        npc.b(i + 0.5D, (j + 1), k + 0.5D, entityplayer.aS + 180.0F, 0.0F);
        npc.H = npc.aS;
        world.b((sn) npc);
        return true;
    }

    public boolean a(ItemInstance itemstack, ls entityliving, ls entityliving1) {
        if (entityliving instanceof EntityNPC) {
            GuiNPC.showUI((EntityNPC) entityliving);
            return true;
        }
        return false;
    }
}
