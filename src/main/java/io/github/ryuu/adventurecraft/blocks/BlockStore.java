package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityStore;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;

public class BlockStore extends TileWithEntity {
    protected BlockStore(int i, int j) {
        super(i, j, Material.GLASS);
    }

    protected TileEntity a_() {
        return new TileEntityStore();
    }

    public boolean c() {
        return false;
    }

    public int b_() {
        return 1;
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        TileEntityStore store = (TileEntityStore)world.b(i, j, k);
        if (DebugMode.active) {
            GuiStoreDebug.showUI(store);
            return true;
        }
        if (store.buySupplyLeft != 0) {
            if (store.sellItemID == 0 || entityplayer.c.consumeItemAmount(store.sellItemID, store.sellItemDamage, store.sellItemAmount)) {
                if (store.buyItemID != 0)
                    entityplayer.c.a(new iz(store.buyItemID, store.buyItemAmount, store.buyItemDamage));
                store.buySupplyLeft--;
                if (store.tradeTrigger != null) {
                    world.triggerManager.addArea(i, j, k, store.tradeTrigger);
                    world.triggerManager.removeArea(i, j, k);
                }
            } else {
                Minecraft.minecraftInstance.v.a("Don't have enough to trade.");
            }
            return true;
        }
        return false;
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityStore store = (TileEntityStore)world.b(i, j, k);
        store.buySupplyLeft = store.buySupply;
    }
}