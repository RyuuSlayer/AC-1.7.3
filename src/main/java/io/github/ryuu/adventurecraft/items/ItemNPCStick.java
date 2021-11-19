package io.github.ryuu.adventurecraft.items;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemNPCStick extends ItemType {

    public ItemNPCStick(int id) {
        super(id);
        this.setTexturePosition(5, 3);
        this.method_466();
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        EntityNPC npc = new EntityNPC(level);
        npc.method_1338((double) x + 0.5, y + 1, (double) z + 0.5, player.yaw + 180.0f, 0.0f);
        npc.field_1012 = npc.yaw;
        level.spawnEntity(npc);
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
