package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.gui.GuiNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemNPCStick extends ItemType {
    public ItemNPCStick(int itemIndex) {
        super(itemIndex);
        setTexturePosition(5, 3);
        method_466();
    }

    @Override
    public boolean useOnTile(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        EntityNPC npc = new EntityNPC(world);
        npc.method_1338(i + 0.5D, (j + 1), k + 0.5D, entityplayer.yaw + 180.0F, 0.0F);
        npc.field_1012 = npc.yaw;
        world.spawnEntity(npc);
        return true;
    }

    @Override
    public boolean postHit(ItemInstance itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        if (entityliving instanceof EntityNPC) {
            GuiNPC.showUI((EntityNPC) entityliving);
            return true;
        }
        return false;
    }
}
